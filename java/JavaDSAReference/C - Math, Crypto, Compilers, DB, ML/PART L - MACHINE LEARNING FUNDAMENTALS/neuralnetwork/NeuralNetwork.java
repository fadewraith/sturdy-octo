package ml.neuralnetwork;

import java.util.Random;

/**
 * NEURAL NETWORK FROM SCRATCH (multi-layer perceptron + backpropagation)
 *
 * WHAT IT IS:
 * Layers of "neurons": each computes  activation(weights . inputs + bias).
 * Stacking layers with NON-LINEAR activations lets the network learn functions a
 * single linear model can't - the classic example is XOR, which no straight line
 * can separate (LogisticRegression.java fails on it; one hidden layer solves it).
 *
 * WHEN TO USE THIS:
 * - Understanding what PyTorch/TensorFlow do under the hood: forward pass, loss,
 *   backward pass (chain rule), gradient descent.
 *
 * ARCHITECTURE HERE:
 *   input (n) -> hidden layers with TANH -> output layer with SIGMOID
 *   loss = binary cross-entropy  L = -[y log p + (1 - y) log(1 - p)]
 *
 * BACKPROPAGATION (the chain rule, layer by layer from the output back):
 *   output delta:  dL/dz_out = p - y        (sigmoid + cross-entropy simplify to this)
 *   hidden delta:  dL/dz_h   = (W_next^T . delta_next) * tanh'(z_h),  tanh' = 1 - a^2
 *   gradients:     dL/dW = delta . (previous activation)^T,  dL/db = delta
 *   update:        W -= learningRate * dL/dW      (stochastic gradient descent)
 * The test suite checks these analytic gradients against NUMERICAL ones
 * (L(w + h) - L(w - h)) / 2h - the standard "gradient check".
 *
 * INITIALISATION: small random weights scaled by 1/sqrt(fan-in) (Xavier-style),
 * so signals neither explode nor vanish; all-zero weights would make every hidden
 * neuron identical forever (symmetry).
 *
 * COMPLEXITY:
 * One forward/backward pass: O(total number of weights). Training: that times
 * epochs * samples.
 */
public class NeuralNetwork {

    private final int[] sizes;            // neurons per layer, including input and output
    public final double[][][] w;          // (public for gradient checking) w[l][j][k]: weight from neuron k in layer l to neuron j in layer l+1
    public final double[][] b;            // b[l][j]: bias of neuron j in layer l+1

    public NeuralNetwork(long seed, int... sizes) {
        if (sizes.length < 2 || sizes[sizes.length - 1] != 1) throw new IllegalArgumentException("need >= 2 layers and 1 output");
        this.sizes = sizes.clone();
        Random rng = new Random(seed);
        w = new double[sizes.length - 1][][];
        b = new double[sizes.length - 1][];
        for (int l = 0; l < sizes.length - 1; l++) {
            w[l] = new double[sizes[l + 1]][sizes[l]];
            b[l] = new double[sizes[l + 1]];
            double scale = 1.0 / Math.sqrt(sizes[l]);
            for (double[] row : w[l]) for (int k = 0; k < row.length; k++) row[k] = rng.nextGaussian() * scale;
        }
    }

    private static double sigmoid(double z) { return 1 / (1 + Math.exp(-z)); }

    /** Forward pass; returns the activations of every layer (a[0] = input). */
    private double[][] forward(double[] x) {
        double[][] a = new double[sizes.length][];
        a[0] = x;
        for (int l = 0; l < w.length; l++) {
            a[l + 1] = new double[sizes[l + 1]];
            boolean output = (l == w.length - 1);
            for (int j = 0; j < sizes[l + 1]; j++) {
                double z = b[l][j];
                for (int k = 0; k < sizes[l]; k++) z += w[l][j][k] * a[l][k];
                a[l + 1][j] = output ? sigmoid(z) : Math.tanh(z);
            }
        }
        return a;
    }

    public double predict(double[] x) { return forward(x)[sizes.length - 1][0]; }

    public double loss(double[] x, double y) {
        double p = Math.min(Math.max(predict(x), 1e-12), 1 - 1e-12);
        return -(y * Math.log(p) + (1 - y) * Math.log(1 - p));
    }

    /** Gradients of the loss for one sample: {dW[l][j][k]}, {db[l][j]} via backprop. */
    public double[][][][] gradients(double[] x, double y) {
        double[][] a = forward(x);
        int L = w.length;
        double[][][] gw = new double[L][][];
        double[][] gb = new double[L][];
        double[] delta = {a[L][0] - y};                       // output layer: p - y
        for (int l = L - 1; l >= 0; l--) {
            gw[l] = new double[sizes[l + 1]][sizes[l]];
            gb[l] = delta.clone();
            for (int j = 0; j < sizes[l + 1]; j++)
                for (int k = 0; k < sizes[l]; k++) gw[l][j][k] = delta[j] * a[l][k];
            if (l > 0) {                                      // push the error back through tanh
                double[] prev = new double[sizes[l]];
                for (int k = 0; k < sizes[l]; k++) {
                    double s = 0;
                    for (int j = 0; j < sizes[l + 1]; j++) s += w[l][j][k] * delta[j];
                    prev[k] = s * (1 - a[l][k] * a[l][k]);
                }
                delta = prev;
            }
        }
        return new double[][][][]{gw, {gb}};
    }

    /** One epoch of stochastic gradient descent over the data (in the given order). */
    public void trainEpoch(double[][] xs, double[] ys, double learningRate) {
        for (int i = 0; i < xs.length; i++) {
            double[][][][] g = gradients(xs[i], ys[i]);
            double[][][] gw = g[0];
            double[][] gb = g[1][0];
            for (int l = 0; l < w.length; l++) {
                for (int j = 0; j < w[l].length; j++) {
                    for (int k = 0; k < w[l][j].length; k++) w[l][j][k] -= learningRate * gw[l][j][k];
                    b[l][j] -= learningRate * gb[l][j];
                }
            }
        }
    }

    public double meanLoss(double[][] xs, double[] ys) {
        double s = 0;
        for (int i = 0; i < xs.length; i++) s += loss(xs[i], ys[i]);
        return s / xs.length;
    }

    public static void main(String[] args) {
        System.out.println("--- NEURAL NETWORK (BACKPROP) DEMO: learning XOR ---");
        double[][] xs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[] ys = {0, 1, 1, 0};
        NeuralNetwork net = new NeuralNetwork(1, 2, 4, 1);
        for (int epoch = 0; epoch <= 2000; epoch++) {
            if (epoch % 500 == 0) System.out.printf("epoch %4d  loss %.4f%n", epoch, net.meanLoss(xs, ys));
            net.trainEpoch(xs, ys, 0.3);
        }
        for (int i = 0; i < 4; i++) {
            System.out.printf("XOR(%d, %d) -> %.3f%n", (int) xs[i][0], (int) xs[i][1], net.predict(xs[i]));
        }
        // outputs close to 0, 1, 1, 0
    }
}
