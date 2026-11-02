package ml.decisiontree;

import java.util.Arrays;

/**
 * DECISION TREE CLASSIFIER (CART with Gini impurity)
 *
 * WHAT IT IS:
 * A tree of yes/no questions "is feature f <= threshold?". Each leaf predicts a
 * class. Learned greedily from labelled data, top-down.
 *
 * WHEN TO USE THIS:
 * - Interpretable models (you can print the rules), mixed feature scales (no
 *   normalisation needed), non-linear boundaries.
 * - The building block of RANDOM FORESTS and GRADIENT BOOSTING (XGBoost,
 *   LightGBM) - the strongest models for tabular data.
 *
 * STRATEGY (CART - Classification And Regression Trees):
 * GINI IMPURITY of a set: 1 - sum over classes of p_c^2.
 *   0 = pure (one class), 0.5 = 50/50 for two classes.
 * At each node try EVERY feature and EVERY threshold between consecutive distinct
 * sorted values; pick the split with the lowest WEIGHTED child impurity
 *   (nLeft * gini(left) + nRight * gini(right)) / n.
 * Recurse on both halves. Stop when the node is pure, too small (minSamples), at
 * maxDepth, or all its points are identical (no threshold separates them) -> leaf
 * predicts the majority class.
 * Note: the best split is taken even if it doesn't lower the impurity (like
 * scikit-learn). On XOR-shaped data every FIRST split has zero gain, yet the
 * second level separates the classes perfectly - stopping at "no gain" would miss it.
 * Sorting each feature and sweeping class counts makes the split search
 * O(d * n log n) per node instead of O(d * n^2).
 *
 * OVERFITTING: an unlimited tree memorises the training data (100% train accuracy,
 * worse test accuracy). maxDepth / minSamples (pre-pruning) or cost-complexity
 * pruning fix that; forests average many randomised trees.
 *
 * COMPLEXITY:
 * Training: O(d * n log n * depth). Prediction: O(depth).
 */
public class DecisionTree {

    private static final class Node {
        int feature = -1;          // -1 = leaf
        double threshold;
        Node left, right;          // left: x[feature] <= threshold
        int prediction;
    }

    private final int maxDepth, minSamples;
    private Node root;
    private int numClasses;

    public DecisionTree(int maxDepth, int minSamples) {
        this.maxDepth = maxDepth;
        this.minSamples = Math.max(2, minSamples);
    }

    public static double gini(int[] counts, int total) {
        if (total == 0) return 0;
        double s = 0;
        for (int c : counts) { double p = (double) c / total; s += p * p; }
        return 1 - s;
    }

    public DecisionTree fit(double[][] x, int[] y) {
        if (x.length == 0 || x.length != y.length) throw new IllegalArgumentException("need matching, non-empty x and y");
        numClasses = Arrays.stream(y).max().getAsInt() + 1;
        int[] idx = new int[x.length];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        root = build(x, y, idx, 0);
        return this;
    }

    private Node build(double[][] x, int[] y, int[] idx, int depth) {
        Node node = new Node();
        int[] counts = new int[numClasses];
        for (int i : idx) counts[y[i]]++;
        int majority = 0;
        for (int c = 1; c < numClasses; c++) if (counts[c] > counts[majority]) majority = c;
        node.prediction = majority;

        double parentGini = gini(counts, idx.length);
        if (parentGini == 0 || depth >= maxDepth || idx.length < minSamples) return node;

        int bestFeature = -1;
        double bestThreshold = 0, bestScore = Double.MAX_VALUE; // accept the best split, even with zero gain
        int d = x[0].length;
        Integer[] order = new Integer[idx.length];
        for (int f = 0; f < d; f++) {
            for (int k = 0; k < idx.length; k++) order[k] = idx[k];
            final int ff = f;
            Arrays.sort(order, (a, b) -> Double.compare(x[a][ff], x[b][ff]));
            int[] leftCounts = new int[numClasses], rightCounts = counts.clone();
            for (int k = 0; k < order.length - 1; k++) {        // move one sample left at a time
                int i = order[k];
                leftCounts[y[i]]++;
                rightCounts[y[i]]--;
                double cur = x[i][f], next = x[order[k + 1]][f];
                if (cur == next) continue;                      // can't split between equal values
                int nl = k + 1, nr = order.length - nl;
                double score = nl * gini(leftCounts, nl) + nr * gini(rightCounts, nr);
                if (score < bestScore - 1e-12) {
                    bestScore = score;
                    bestFeature = f;
                    bestThreshold = (cur + next) / 2;           // midpoint between the two values
                }
            }
        }
        if (bestFeature == -1) return node;                     // all points identical: can't split

        int nl = 0;
        for (int i : idx) if (x[i][bestFeature] <= bestThreshold) nl++;
        int[] li = new int[nl], ri = new int[idx.length - nl];
        int a = 0, b = 0;
        for (int i : idx) { if (x[i][bestFeature] <= bestThreshold) li[a++] = i; else ri[b++] = i; }
        node.feature = bestFeature;
        node.threshold = bestThreshold;
        node.left = build(x, y, li, depth + 1);
        node.right = build(x, y, ri, depth + 1);
        return node;
    }

    public int predict(double[] sample) {
        Node n = root;
        while (n.feature != -1) n = sample[n.feature] <= n.threshold ? n.left : n.right;
        return n.prediction;
    }

    public double accuracy(double[][] x, int[] y) {
        int ok = 0;
        for (int i = 0; i < x.length; i++) if (predict(x[i]) == y[i]) ok++;
        return (double) ok / x.length;
    }

    public int depth() { return depth(root); }
    private int depth(Node n) { return n.feature == -1 ? 0 : 1 + Math.max(depth(n.left), depth(n.right)); }

    /** Human-readable rules. */
    public String rules(String[] featureNames, String[] classNames) {
        StringBuilder sb = new StringBuilder();
        rules(root, "", featureNames, classNames, sb);
        return sb.toString();
    }

    private void rules(Node n, String indent, String[] fn, String[] cn, StringBuilder sb) {
        if (n.feature == -1) { sb.append(indent).append("-> ").append(cn[n.prediction]).append('\n'); return; }
        sb.append(indent).append("if ").append(fn[n.feature]).append(" <= ").append(String.format("%.2f", n.threshold)).append(":\n");
        rules(n.left, indent + "    ", fn, cn, sb);
        sb.append(indent).append("else:\n");
        rules(n.right, indent + "    ", fn, cn, sb);
    }

    public static void main(String[] args) {
        System.out.println("--- DECISION TREE (CART, GINI) DEMO ---");
        // Will the customer buy? features: age, income (k$)
        double[][] x = {{22, 25}, {25, 32}, {47, 80}, {52, 110}, {46, 40}, {56, 95}, {55, 30}, {60, 120}, {62, 35}, {61, 45}, {18, 15}, {28, 85}};
        int[] y = {0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1};
        DecisionTree tree = new DecisionTree(3, 2).fit(x, y);
        System.out.print(tree.rules(new String[]{"age", "income"}, new String[]{"no", "buys"}));
        System.out.println("Training accuracy: " + tree.accuracy(x, y) + ", depth " + tree.depth());
        System.out.println("Predict (30, 90): " + (tree.predict(new double[]{30, 90}) == 1 ? "buys" : "no"));
        System.out.println("Gini of a 50/50 node: " + gini(new int[]{5, 5}, 10) + ", of a pure node: " + gini(new int[]{10, 0}, 10));
    }
}
