package spatial.geohash;

/**
 * GEOHASH
 *
 * WHAT IT IS:
 * Encodes a (latitude, longitude) point as a short string like "u4pruydqqvj".
 * Each character narrows the area: 5 chars ~ 4.9 km x 4.9 km, 7 chars ~ 153 m,
 * 9 chars ~ 4.8 m. Points that are close usually share a long common PREFIX.
 *
 * WHEN TO USE THIS:
 * - "Find restaurants / drivers near me": store each point's geohash in an ordinary
 *   sorted index (B-tree, Redis sorted set, DynamoDB sort key) and query by prefix.
 *   Redis GEO commands, Elasticsearch geo_point grid aggregations, Uber/Lyft-style
 *   dispatch (Uber later moved to hexagonal H3, same idea).
 * - Sharding location data by prefix.
 *
 * STRATEGY (binary search on both axes, interleaved):
 * Start with lon in [-180, 180] and lat in [-90, 90]. Alternately (longitude first)
 * halve the current interval: write 1 if the point is in the upper half, 0 if in the
 * lower half. Interleaving lon/lat bits like this is a Z-ORDER curve (see
 * SpaceFillingCurves.java). Every 5 bits become one base-32 character from
 * "0123456789bcdefghjkmnpqrstuvwxyz" (no a, i, l, o - easy to read aloud).
 * Decoding reverses the halving and returns the CELL (a bounding box); its centre
 * is the best point estimate.
 *
 * EDGE CASE - NEIGHBOURS:
 * Two points a metre apart can sit on opposite sides of a cell border and share NO
 * prefix (e.g. across the equator or the prime meridian). So a radius search must
 * look at the point's cell AND its 8 neighbours. Neighbours are found here by
 * stepping one cell width/height from the centre and re-encoding (wrapping
 * longitude around the 180 degree line).
 *
 * COMPLEXITY:
 * Encode / decode: O(precision). Neighbours: O(8 * precision).
 */
public class Geohash {

    public static final String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    /** {minLat, maxLat, minLon, maxLon} of a decoded cell. */
    public static double[] decodeBox(String hash) {
        double minLat = -90, maxLat = 90, minLon = -180, maxLon = 180;
        boolean evenBit = true; // even bits are longitude
        for (int i = 0; i < hash.length(); i++) {
            int value = BASE32.indexOf(hash.charAt(i));
            if (value < 0) throw new IllegalArgumentException("invalid geohash character '" + hash.charAt(i) + "'");
            for (int b = 4; b >= 0; b--) {
                int bit = (value >> b) & 1;
                if (evenBit) {
                    double mid = (minLon + maxLon) / 2;
                    if (bit == 1) minLon = mid; else maxLon = mid;
                } else {
                    double mid = (minLat + maxLat) / 2;
                    if (bit == 1) minLat = mid; else maxLat = mid;
                }
                evenBit = !evenBit;
            }
        }
        return new double[]{minLat, maxLat, minLon, maxLon};
    }

    /** Centre of the cell: {lat, lon}. */
    public static double[] decode(String hash) {
        double[] box = decodeBox(hash);
        return new double[]{(box[0] + box[1]) / 2, (box[2] + box[3]) / 2};
    }

    public static String encode(double lat, double lon, int precision) {
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) throw new IllegalArgumentException("coordinates out of range");
        if (precision < 1 || precision > 12) throw new IllegalArgumentException("precision must be 1..12");
        double minLat = -90, maxLat = 90, minLon = -180, maxLon = 180;
        StringBuilder sb = new StringBuilder();
        boolean evenBit = true;
        int bits = 0, value = 0;
        while (sb.length() < precision) {
            if (evenBit) {
                double mid = (minLon + maxLon) / 2;
                if (lon >= mid) { value = (value << 1) | 1; minLon = mid; } else { value <<= 1; maxLon = mid; }
            } else {
                double mid = (minLat + maxLat) / 2;
                if (lat >= mid) { value = (value << 1) | 1; minLat = mid; } else { value <<= 1; maxLat = mid; }
            }
            evenBit = !evenBit;
            if (++bits == 5) {                     // 5 bits -> one base-32 character
                sb.append(BASE32.charAt(value));
                bits = 0;
                value = 0;
            }
        }
        return sb.toString();
    }

    /** The adjacent cell in direction (dLat, dLon), each -1, 0 or +1. null past a pole. */
    public static String neighbour(String hash, int dLat, int dLon) {
        double[] box = decodeBox(hash);
        double height = box[1] - box[0], width = box[3] - box[2];
        double lat = (box[0] + box[1]) / 2 + dLat * height;
        double lon = (box[2] + box[3]) / 2 + dLon * width;
        if (lat > 90 || lat < -90) return null;    // nothing beyond the poles
        if (lon > 180) lon -= 360;                 // wrap around the antimeridian
        if (lon < -180) lon += 360;
        return encode(lat, lon, hash.length());
    }

    /** The 8 surrounding cells (N, NE, E, SE, S, SW, W, NW); entries may be null at the poles. */
    public static String[] neighbours(String hash) {
        int[][] dirs = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        String[] out = new String[8];
        for (int i = 0; i < 8; i++) out[i] = neighbour(hash, dirs[i][0], dirs[i][1]);
        return out;
    }

    /** Great-circle distance in metres (haversine), for checking "nearby" results. */
    public static double distanceMeters(double lat1, double lon1, double lat2, double lon2) {
        double r = 6_371_000;
        double dLat = Math.toRadians(lat2 - lat1), dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * r * Math.asin(Math.sqrt(a));
    }

    public static void main(String[] args) {
        System.out.println("--- GEOHASH DEMO ---");
        String h = encode(57.64911, 10.40744, 11);
        System.out.println("(57.64911, 10.40744) -> " + h); // u4pruydqqvj (the classic example)
        double[] c = decode(h);
        System.out.printf("decoded centre: (%.5f, %.5f)%n", c[0], c[1]);

        // Two cafes 30 m apart vs. one 5 km away: shared prefix length tells closeness
        String cafe1 = encode(48.85837, 2.29448, 9), cafe2 = encode(48.85860, 2.29470, 9), far = encode(48.89000, 2.34000, 9);
        System.out.println("\nEiffel Tower cafe: " + cafe1 + ", next door: " + cafe2 + ", Montmartre: " + far);

        // Border effect: 2 m apart across the equator, no common prefix at all
        System.out.println("Across the equator: " + encode(0.00001, 10, 6) + " vs " + encode(-0.00001, 10, 6)
                + " -> always search the 8 neighbours too");
        System.out.println("Neighbours of " + encode(0.00001, 10, 6) + ": " + String.join(" ", neighbours(encode(0.00001, 10, 6))));
    }
}
