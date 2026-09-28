// These are simplified, from-scratch educational implementations to understand the underlying math. Real-world ML work uses libraries (scikit-learn, TensorFlow, etc.) — these exist here purely for conceptual completeness, not as production advice.
/**
 * WHAT IT IS: Supervised learning algorithm used for binary classification.
 * STRATEGY: Apply sigmoid function to linear output to map predictions to probabilities between 0 and 1. Update weights using gradient descent on log-loss cost.
 * TIME/SPACE COMPLEXITY: Time: O(I * N * d). Space: O(d).
 * REAL-WORLD ANALOGY / USE CASE: Email spam detection (Spam or Not Spam), disease prediction.
 * WHEN TO USE / COMBINATION: For binary classification problems providing probabilistic outputs.
 * 
 * PSEUDOCODE:
 * Initialize weights and bias to 0
 * Loop for num_iterations:
 *   linear_model = dot_product(X, weights) + bias
 *   predictions = sigmoid(linear_model)
 *   error = predictions - y
 *   gradient_w = (1/N) * dot_product(X.T, error)
 *   gradient_b = (1/N) * sum(error)
 *   weights -= learning_rate * gradient_w
 *   bias -= learning_rate * gradient_b
 */
package ml.logisticregression;

import java.util.Arrays;

public class LogisticRegression {

    public static void main(String[] args) {
        System.out.println("--- Logistic Regression ---");
        
        // Dataset: points > 5 are class 1, otherwise 0
        double[][] X = { {1.0}, {2.0}, {3.0}, {7.0}, {8.0}, {9.0} };
        double[] y = { 0.0, 0.0, 0.0, 1.0, 1.0, 1.0 };
        
        Model model = train(X, y, 0.1, 1000);
        
        System.out.println("Trained Weights: " + Arrays.toString(model.weights));
        System.out.println("Trained Bias: " + model.bias);
        
        double[] query1 = { 2.5 };
        double[] query2 = { 7.5 };
        
        System.out.println("Predict prob for x=2.5: " + predictProb(model, query1) + " -> Class: " + predictClass(model, query1));
        System.out.println("Predict prob for x=7.5: " + predictProb(model, query2) + " -> Class: " + predictClass(model, query2));
    }

    static class Model {
        double[] weights;
        double bias;
        
        Model(int dimensions) {
            weights = new double[dimensions];
            bias = 0.0;
        }
    }

    public static Model train(double[][] X, double[] y, double learningRate, int iterations) {
        int n = X.length;
        int d = X[0].length;
        Model model = new Model(d);
        
        for (int i = 0; i < iterations; i++) {
            double[] predictions = new double[n];
            for (int j = 0; j < n; j++) {
                predictions[j] = predictProb(model, X[j]);
            }
            
            double[] error = new double[n];
            for (int j = 0; j < n; j++) {
                error[j] = predictions[j] - y[j];
            }
            
            double[] dw = new double[d];
            double db = 0;
            
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < d; k++) {
                    dw[k] += error[j] * X[j][k];
                }
                db += error[j];
            }
            
            for (int k = 0; k < d; k++) {
                dw[k] /= n;
                model.weights[k] -= learningRate * dw[k];
            }
            db /= n;
            model.bias -= learningRate * db;
        }
        
        return model;
    }
    
    public static double predictProb(Model model, double[] x) {
        double linear = model.bias;
        for (int i = 0; i < x.length; i++) {
            linear += model.weights[i] * x[i];
        }
        return sigmoid(linear);
    }
    
    public static int predictClass(Model model, double[] x) {
        return predictProb(model, x) >= 0.5 ? 1 : 0;
    }
    
    private static double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }
}
