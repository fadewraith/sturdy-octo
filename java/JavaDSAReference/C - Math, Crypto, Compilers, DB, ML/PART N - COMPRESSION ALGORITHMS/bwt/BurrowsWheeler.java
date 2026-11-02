package compression.bwt;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * BURROWS-WHEELER TRANSFORM (BWT) + MOVE-TO-FRONT (MTF)
 *
 * WHAT IT IS:
 * BWT rearranges the bytes of a block so that equal characters CLUSTER together
 * ("banana" -> "nnbaaa"), and it is fully REVERSIBLE given one extra number. It
 * doesn't compress anything by itself - it makes the data easy to compress.
 * MTF then turns those clusters into runs of small numbers (mostly 0s), which
 * run-length + Huffman/arithmetic coding squeeze very well.
 *
 * WHEN TO USE THIS:
 * - bzip2 is exactly BWT -> MTF -> RLE -> Huffman.
 * - The BWT is also the heart of the FM-INDEX, used by DNA read aligners (BWA,
 *   Bowtie) to search a 3-billion-letter genome in compressed space.
 *
 * FORWARD TRANSFORM:
 * Write all n ROTATIONS of the input, sort them, and output the LAST column plus
 * the row number where the original string ended up (primary index).
 *   banana: sorted rotations abanan, anaban, ananab, banana, nabana, nanaba
 *   last column "nnbaaa", original is row 3.
 * Characters that are followed by the same context ("an..." -> preceded by n)
 * end up next to each other - hence the clustering.
 * Sorting rotations naively is O(n^2 log n); here we use PREFIX DOUBLING: sort by
 * the first 1, 2, 4, 8, ... characters, reusing the previous ranks -> O(n log^2 n).
 *
 * INVERSE (the surprising part) - the LF MAPPING:
 * The first column is just the last column SORTED. The k-th occurrence of char c in
 * the last column is the same text character as the k-th occurrence of c in the
 * first column. Following that mapping from the primary row walks the original
 * text backwards. O(n) with counting.
 *
 * MOVE-TO-FRONT: keep a list of all 256 byte values; output each byte's current
 * POSITION in the list, then move it to the front. Repeated bytes become 0s.
 *
 * COMPLEXITY:
 * BWT: O(n log^2 n) time, O(n) space. Inverse BWT: O(n). MTF: O(256 n) worst case.
 */
public class BurrowsWheeler {

    public static final class Result {
        public final byte[] lastColumn;
        public final int primaryIndex;
        Result(byte[] lastColumn, int primaryIndex) { this.lastColumn = lastColumn; this.primaryIndex = primaryIndex; }
    }

    /** Sorted order of the cyclic rotations (prefix doubling). */
    static int[] sortRotations(byte[] s) {
        int n = s.length;
        Integer[] idx = new Integer[n];
        int[] rank = new int[n], tmp = new int[n];
        for (int i = 0; i < n; i++) { idx[i] = i; rank[i] = s[i] & 0xff; }
        for (int len = 1; ; len <<= 1) {
            final int L = len;
            final int[] r = rank;
            // compare by (rank of first L chars, rank of the next L chars) - cyclically
            java.util.Comparator<Integer> cmp = (a, b) -> r[a] != r[b] ? Integer.compare(r[a], r[b])
                    : Integer.compare(r[(a + L) % n], r[(b + L) % n]);
            Arrays.sort(idx, cmp);
            tmp[idx[0]] = 0;
            for (int i = 1; i < n; i++) tmp[idx[i]] = tmp[idx[i - 1]] + (cmp.compare(idx[i - 1], idx[i]) < 0 ? 1 : 0);
            System.arraycopy(tmp, 0, rank, 0, n);
            if (tmp[idx[n - 1]] == n - 1 || len >= n) break; // all distinct, or fully compared (periodic input)
        }
        int[] out = new int[n];
        for (int i = 0; i < n; i++) out[i] = idx[i];
        return out;
    }

    public static Result transform(byte[] input) {
        int n = input.length;
        if (n == 0) return new Result(new byte[0], 0);
        int[] order = sortRotations(input);
        byte[] last = new byte[n];
        int primary = -1;
        for (int i = 0; i < n; i++) {
            int start = order[i];
            last[i] = input[(start + n - 1) % n];     // character before the rotation's start
            if (start == 0) primary = i;
        }
        return new Result(last, primary);
    }

    public static byte[] inverse(byte[] last, int primaryIndex) {
        int n = last.length;
        if (n == 0) return new byte[0];
        // count[c] = how many chars < c; occurrence = rank of last[i] among equal chars above it
        int[] count = new int[257];
        for (byte b : last) count[(b & 0xff) + 1]++;
        for (int c = 0; c < 256; c++) count[c + 1] += count[c];
        int[] lf = new int[n];
        int[] seen = new int[256];
        for (int i = 0; i < n; i++) {
            int c = last[i] & 0xff;
            lf[i] = count[c] + seen[c]++;              // row of this char in the first column
        }
        byte[] out = new byte[n];
        int row = primaryIndex;
        for (int i = n - 1; i >= 0; i--) {             // walk backwards through the text
            out[i] = last[row];
            row = lf[row];
        }
        return out;
    }

    public static int[] moveToFrontEncode(byte[] data) {
        int[] list = new int[256];
        for (int i = 0; i < 256; i++) list[i] = i;
        int[] out = new int[data.length];
        for (int k = 0; k < data.length; k++) {
            int c = data[k] & 0xff, pos = 0;
            while (list[pos] != c) pos++;
            out[k] = pos;
            System.arraycopy(list, 0, list, 1, pos);   // shift the ones before it back
            list[0] = c;
        }
        return out;
    }

    public static byte[] moveToFrontDecode(int[] codes) {
        int[] list = new int[256];
        for (int i = 0; i < 256; i++) list[i] = i;
        byte[] out = new byte[codes.length];
        for (int k = 0; k < codes.length; k++) {
            int pos = codes[k], c = list[pos];
            out[k] = (byte) c;
            System.arraycopy(list, 0, list, 1, pos);
            list[0] = c;
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("--- BURROWS-WHEELER + MOVE-TO-FRONT DEMO ---");
        Result r = transform("banana".getBytes(StandardCharsets.UTF_8));
        System.out.println("BWT(banana) = \"" + new String(r.lastColumn, StandardCharsets.UTF_8) + "\", primary index " + r.primaryIndex); // nnbaaa, 3
        System.out.println("inverse     = \"" + new String(inverse(r.lastColumn, r.primaryIndex), StandardCharsets.UTF_8) + "\"");

        String text = "she sells sea shells by the sea shore, the shells she sells are sea shells for sure. ".repeat(4);
        byte[] raw = text.getBytes(StandardCharsets.UTF_8);
        Result bwt = transform(raw);
        int[] mtfPlain = moveToFrontEncode(raw), mtfBwt = moveToFrontEncode(bwt.lastColumn);
        System.out.printf("%nText of %d bytes. Zeros after MTF: plain text %d, after BWT %d%n",
                raw.length, Arrays.stream(mtfPlain).filter(x -> x == 0).count(), Arrays.stream(mtfBwt).filter(x -> x == 0).count());
        System.out.println("BWT block start: \"" + new String(Arrays.copyOf(bwt.lastColumn, 60), StandardCharsets.UTF_8) + "...\"");
        byte[] back = inverse(moveToFrontDecode(mtfBwt), bwt.primaryIndex);
        System.out.println("Round trip OK: " + Arrays.equals(raw, back));
    }
}
