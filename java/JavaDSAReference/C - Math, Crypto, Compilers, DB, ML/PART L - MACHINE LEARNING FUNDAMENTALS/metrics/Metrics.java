package ml.metrics;

import java.util.Arrays;

/**
 * EVALUATION METRICS (classification and regression)
 *
 * WHAT IT IS:
 * How to measure whether a model is any good - and why accuracy alone lies.
 *
 * BINARY CLASSIFICATION - the CONFUSION MATRIX:
 *                 predicted 1        predicted 0
 *   actual 1      TP (true pos)      FN (missed)
 *   actual 0      FP (false alarm)   TN
 *   accuracy  = (TP + TN) / all       - misleading on IMBALANCED data: a fraud
 *                                       model that always says "no fraud" is 99.9%
 *                                       accurate and useless
 *   precision = TP / (TP + FP)        "of what I flagged, how much was right?"
 *   recall    = TP / (TP + FN)        "of what was there, how much did I find?"
 *   F1        = 2PR / (P + R)         harmonic mean - high only if BOTH are high
 *
 * ROC AUC (threshold-free):
 * The ROC curve plots recall vs false-positive rate for every threshold; AUC is
 * the area under it. Equivalent and easier: AUC = probability that a random
 * positive gets a HIGHER score than a random negative (ties count 1/2) - the
 * Mann-Whitney U statistic. Computed here by sorting scores and summing ranks:
 * O(n log n) instead of comparing all pairs. 0.5 = random, 1.0 = perfect.
 *
 * LOG LOSS (cross-entropy): -mean(y log p + (1 - y) log(1 - p)); punishes confident
 * wrong predictions hard. What logistic regression and NNs minimise.
 *
 * REGRESSION:
 *   MSE = mean (y - yhat)^2,  RMSE = sqrt(MSE) (same units as y),
 *   MAE = mean |y - yhat| (less sensitive to outliers),
 *   R^2 = 1 - SS_res / SS_tot (1 = perfect, 0 = no better than predicting the mean).
 *
 * COMPLEXITY:
 * All O(n), except AUC O(n log n).
 */
public class Metrics {

    /** {TP, FP, FN, TN} for 0/1 labels. */
    public static int[] confusion(int[] actual, int[] predicted) {
        int tp = 0, fp = 0, fn = 0, tn = 0;
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] == 1 && predicted[i] == 1) tp++;
            else if (actual[i] == 0 && predicted[i] == 1) fp++;
            else if (actual[i] == 1) fn++;
            else tn++;
        }
        return new int[]{tp, fp, fn, tn};
    }

    public static double accuracy(int[] actual, int[] predicted) {
        int[] c = confusion(actual, predicted);
        return (double) (c[0] + c[3]) / actual.length;
    }

    public static double precision(int[] actual, int[] predicted) {
        int[] c = confusion(actual, predicted);
        return c[0] + c[1] == 0 ? 0 : (double) c[0] / (c[0] + c[1]);
    }

    public static double recall(int[] actual, int[] predicted) {
        int[] c = confusion(actual, predicted);
        return c[0] + c[2] == 0 ? 0 : (double) c[0] / (c[0] + c[2]);
    }

    public static double f1(int[] actual, int[] predicted) {
        double p = precision(actual, predicted), r = recall(actual, predicted);
        return p + r == 0 ? 0 : 2 * p * r / (p + r);
    }

    /** ROC AUC via ranks (Mann-Whitney U); tied scores get their average rank. */
    public static double rocAuc(int[] actual, double[] scores) {
        int n = actual.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Double.compare(scores[a], scores[b]));
        double[] rank = new double[n];
        for (int i = 0; i < n; ) {
            int j = i;
            while (j + 1 < n && scores[order[j + 1]] == scores[order[i]]) j++;
            double avg = (i + j) / 2.0 + 1;                  // ranks are 1-based
            for (int k = i; k <= j; k++) rank[order[k]] = avg;
            i = j + 1;
        }
        long pos = 0, neg;
        double rankSumPos = 0;
        for (int i = 0; i < n; i++) if (actual[i] == 1) { pos++; rankSumPos += rank[i]; }
        neg = n - pos;
        if (pos == 0 || neg == 0) throw new IllegalArgumentException("AUC needs both classes");
        return (rankSumPos - pos * (pos + 1) / 2.0) / ((double) pos * neg);
    }

    public static double logLoss(int[] actual, double[] probabilities) {
        double s = 0;
        for (int i = 0; i < actual.length; i++) {
            double p = Math.min(Math.max(probabilities[i], 1e-15), 1 - 1e-15);
            s += actual[i] == 1 ? -Math.log(p) : -Math.log(1 - p);
        }
        return s / actual.length;
    }

    public static double mse(double[] y, double[] yhat) {
        double s = 0;
        for (int i = 0; i < y.length; i++) s += (y[i] - yhat[i]) * (y[i] - yhat[i]);
        return s / y.length;
    }

    public static double rmse(double[] y, double[] yhat) { return Math.sqrt(mse(y, yhat)); }

    public static double mae(double[] y, double[] yhat) {
        double s = 0;
        for (int i = 0; i < y.length; i++) s += Math.abs(y[i] - yhat[i]);
        return s / y.length;
    }

    public static double r2(double[] y, double[] yhat) {
        double mean = Arrays.stream(y).average().orElse(0), ssRes = 0, ssTot = 0;
        for (int i = 0; i < y.length; i++) { ssRes += (y[i] - yhat[i]) * (y[i] - yhat[i]); ssTot += (y[i] - mean) * (y[i] - mean); }
        return 1 - ssRes / ssTot;
    }

    public static void main(String[] args) {
        System.out.println("--- METRICS DEMO ---");
        // 1000 transactions, 10 frauds. Model A always says "not fraud"; model B catches 8 with 12 false alarms.
        int[] actual = new int[1000];
        for (int i = 0; i < 10; i++) actual[i] = 1;
        int[] modelA = new int[1000];
        int[] modelB = new int[1000];
        for (int i = 0; i < 8; i++) modelB[i] = 1;
        for (int i = 10; i < 22; i++) modelB[i] = 1;
        System.out.printf("Always-no model: accuracy %.3f, precision %.2f, recall %.2f, F1 %.2f  <- useless%n",
                accuracy(actual, modelA), precision(actual, modelA), recall(actual, modelA), f1(actual, modelA));
        System.out.printf("Real model:      accuracy %.3f, precision %.2f, recall %.2f, F1 %.2f%n",
                accuracy(actual, modelB), precision(actual, modelB), recall(actual, modelB), f1(actual, modelB));
        System.out.println("Confusion {TP, FP, FN, TN}: " + Arrays.toString(confusion(actual, modelB)));

        int[] y = {1, 1, 0, 1, 0, 0};
        double[] s = {0.9, 0.8, 0.7, 0.6, 0.2, 0.1};
        System.out.printf("%nAUC %.3f (8 of 9 positive-negative pairs ranked correctly), log loss %.3f%n", rocAuc(y, s), logLoss(y, s));

        double[] truth = {3, -0.5, 2, 7}, pred = {2.5, 0.0, 2, 8};
        System.out.printf("Regression: MSE %.3f, RMSE %.3f, MAE %.3f, R^2 %.3f%n", mse(truth, pred), rmse(truth, pred), mae(truth, pred), r2(truth, pred));
        // MSE 0.375, MAE 0.5, R^2 0.949
    }
}
