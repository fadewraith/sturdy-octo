package algorithms.approximation;

import java.util.Arrays;
import java.util.Random;

/**
 * METAHEURISTICS on the Travelling Salesman Problem:
 * 2-opt Hill Climbing, Simulated Annealing, Genetic Algorithm
 *
 * WHAT IT IS:
 * General-purpose search strategies for huge optimisation problems where exact
 * methods are hopeless (TSP with 1000 cities has ~10^2564 tours). No optimality
 * guarantee - but in practice they find very good solutions fast.
 *
 * WHEN TO USE THIS:
 * - Scheduling, timetabling, vehicle routing, chip layout, hyper-parameter tuning,
 *   any problem where you can (1) score a solution and (2) make small changes to it.
 *
 * 1. HILL CLIMBING with 2-OPT moves (local search)
 *    A 2-opt move removes two edges (a-b, c-d) and reconnects as (a-c, b-d), i.e.
 *    REVERSES the segment between them. It removes crossings. Repeat the best/first
 *    improving move until none improves: a LOCAL OPTIMUM. Fast, but gets stuck.
 *
 * 2. SIMULATED ANNEALING (inspired by slowly cooling metal)
 *    Try a random 2-opt move. Better -> accept. Worse by delta -> accept anyway with
 *    probability exp(-delta / T). Temperature T starts high (wander freely, escape
 *    local optima) and is multiplied by a cooling factor (e.g. 0.9995) each step
 *    (settle down). Keep the best tour ever seen.
 *
 * 3. GENETIC ALGORITHM (inspired by evolution)
 *    A POPULATION of tours. Each generation:
 *      SELECTION   - tournament: pick k random tours, the shortest becomes a parent
 *      CROSSOVER   - ORDER CROSSOVER (OX1): copy a slice of parent A, fill the rest
 *                    with the remaining cities in parent B's order (always valid tour)
 *      MUTATION    - with small probability, reverse a random segment
 *      ELITISM     - the best tours survive unchanged
 *    Good at combining partial solutions from different tours.
 *
 * Seeded Random everywhere so runs are reproducible.
 *
 * COMPLEXITY:
 * 2-opt pass: O(n^2) per sweep (delta of a move is O(1)).
 * Annealing: O(iterations * n) (segment reversal). GA: O(generations * population * n).
 */
public class Metaheuristics {

    public static double dist(double[][] p, int a, int b) { return Math.hypot(p[a][0] - p[b][0], p[a][1] - p[b][1]); }

    public static double length(double[][] p, int[] t) {
        double s = 0;
        for (int i = 0; i < t.length; i++) s += dist(p, t[i], t[(i + 1) % t.length]);
        return s;
    }

    private static void reverse(int[] t, int i, int j) {
        while (i < j) { int x = t[i]; t[i] = t[j]; t[j] = x; i++; j--; }
    }

    /** Change in length from reversing t[i..j] (edges (i-1, i) and (j, j+1) are replaced). */
    private static double twoOptDelta(double[][] p, int[] t, int i, int j) {
        int n = t.length;
        int a = t[(i - 1 + n) % n], b = t[i], c = t[j], d = t[(j + 1) % n];
        return dist(p, a, c) + dist(p, b, d) - dist(p, a, b) - dist(p, c, d);
    }

    /** 2-opt hill climbing from the given tour (modified in place); returns it. */
    public static int[] twoOpt(double[][] p, int[] tour) {
        int n = tour.length;
        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 1; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (twoOptDelta(p, tour, i, j) < -1e-12) { reverse(tour, i, j); improved = true; }
                }
            }
        }
        return tour;
    }

    public static int[] simulatedAnnealing(double[][] p, int iterations, double startTemp, double cooling, long seed) {
        Random rng = new Random(seed);
        int n = p.length;
        int[] cur = identity(n);
        if (n < 4) return cur;
        double curLen = length(p, cur);
        int[] best = cur.clone();
        double bestLen = curLen, temp = startTemp;
        for (int it = 0; it < iterations; it++) {
            int i = 1 + rng.nextInt(n - 1), j = 1 + rng.nextInt(n - 1);
            if (i == j) continue;
            if (i > j) { int x = i; i = j; j = x; }
            double delta = twoOptDelta(p, cur, i, j);
            if (delta < 0 || rng.nextDouble() < Math.exp(-delta / temp)) {  // accept: always if better, sometimes if worse
                reverse(cur, i, j);
                curLen += delta;
                if (curLen < bestLen - 1e-12) { bestLen = curLen; best = cur.clone(); }
            }
            temp *= cooling;
        }
        return best;
    }

    public static int[] geneticAlgorithm(double[][] p, int populationSize, int generations, double mutationRate, long seed) {
        Random rng = new Random(seed);
        int n = p.length;
        int[][] pop = new int[populationSize][];
        for (int i = 0; i < populationSize; i++) { pop[i] = identity(n); shuffle(pop[i], rng); }
        int elite = Math.max(1, populationSize / 10);
        for (int g = 0; g < generations; g++) {
            Arrays.sort(pop, (a, b) -> Double.compare(length(p, a), length(p, b)));
            int[][] next = new int[populationSize][];
            for (int i = 0; i < elite; i++) next[i] = pop[i].clone();           // elitism
            for (int i = elite; i < populationSize; i++) {
                int[] child = orderCrossover(tournament(pop, p, rng), tournament(pop, p, rng), rng);
                if (rng.nextDouble() < mutationRate && n >= 2) {               // mutation: reverse a segment
                    int a = rng.nextInt(n), b = rng.nextInt(n);
                    reverse(child, Math.min(a, b), Math.max(a, b));
                }
                next[i] = child;
            }
            pop = next;
        }
        int[] best = pop[0];
        for (int[] t : pop) if (length(p, t) < length(p, best)) best = t;
        return best;
    }

    private static int[] tournament(int[][] pop, double[][] p, Random rng) {
        int[] best = null;
        for (int k = 0; k < 3; k++) {
            int[] c = pop[rng.nextInt(pop.length)];
            if (best == null || length(p, c) < length(p, best)) best = c;
        }
        return best;
    }

    /** OX1: keep parent A's slice [i..j], fill the rest with B's cities in B's order. */
    static int[] orderCrossover(int[] a, int[] b, Random rng) {
        int n = a.length;
        int[] child = new int[n];
        Arrays.fill(child, -1);
        boolean[] used = new boolean[n];
        int i = rng.nextInt(n), j = rng.nextInt(n);
        if (i > j) { int x = i; i = j; j = x; }
        for (int k = i; k <= j; k++) { child[k] = a[k]; used[a[k]] = true; }
        int pos = (j + 1) % n;
        for (int k = 0; k < n; k++) {
            int city = b[(j + 1 + k) % n];
            if (used[city]) continue;
            child[pos] = city;
            used[city] = true;
            pos = (pos + 1) % n;
        }
        return child;
    }

    public static int[] identity(int n) { int[] t = new int[n]; for (int i = 0; i < n; i++) t[i] = i; return t; }

    private static void shuffle(int[] t, Random rng) {
        for (int i = t.length - 1; i > 0; i--) { int j = rng.nextInt(i + 1); int x = t[i]; t[i] = t[j]; t[j] = x; }
    }

    public static void main(String[] args) {
        System.out.println("--- METAHEURISTICS DEMO (TSP, 60 random cities) ---");
        Random rng = new Random(3);
        double[][] cities = new double[60][];
        for (int i = 0; i < cities.length; i++) cities[i] = new double[]{rng.nextDouble() * 1000, rng.nextDouble() * 1000};

        int[] start = identity(cities.length);
        System.out.printf("Initial tour (random order):  %.0f%n", length(cities, start));
        System.out.printf("2-opt hill climbing:          %.0f%n", length(cities, twoOpt(cities, start.clone())));
        System.out.printf("Simulated annealing:          %.0f%n", length(cities, simulatedAnnealing(cities, 400_000, 1000, 0.99997, 1)));
        System.out.printf("Genetic algorithm:            %.0f%n", length(cities, geneticAlgorithm(cities, 120, 600, 0.3, 1)));
        System.out.printf("Christofides (1.5-approx):    %.0f%n", ChristofidesTSP.solve(cities).length);
        System.out.printf("Christofides + 2-opt polish:  %.0f%n", length(cities, twoOpt(cities, ChristofidesTSP.solve(cities).tour)));
    }
}
