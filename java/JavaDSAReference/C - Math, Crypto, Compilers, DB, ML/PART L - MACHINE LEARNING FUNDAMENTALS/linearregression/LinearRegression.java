// These are simplified, from-scratch educational implementations to understand the underlying math. Real-world ML work uses libraries (scikit-learn, TensorFlow, etc.) — these exist here purely for conceptual completeness, not as production advice.
/**
 * WHAT IT IS: Supervised learning algorithm that models relationship between a scalar response and explanatory variables.
 * STRATEGY: Use gradient descent to minimize Mean Squared Error (MSE). Iteratively update weights and bias using computed gradients.
 * TIME/SPACE COMPLEXITY: Time: O(I * N * d) for I iterations, N samples, d features. Space: O(d) for weights.
 * REAL-WORLD ANALOGY / USE CASE: Predicting house prices based on features like area, bedrooms.
 * WHEN TO USE / COMBINATION: When relationship between variables is assumed to be linear.
 * 
 * PSEUDOCODE:
 * Initialize weights and bias to 0
 * Loop for num_iterations:
 *   predictions = dot_product(X, weights) + bias
 *   error = predictions - y
 *   gradient_w = (1/N) * dot_product(X.T, error)
 *   gradient_b = (1/N) * sum(error)
 *   weights -= learning_rate * gradient_w
 *   bias -= learning_rate * gradient_b
 */
package ml.linearregression;

import java.util.Arrays;

public class LinearRegression {

    public static void main(String[] args) {
        System.out.println("--- Linear Regression ---");
        
        // simple dataset: y = 2x + 1
        double[][] X = { {1.0}, {2.0}, {3.0}, {4.0} };
        double[] y = { 3.0, 5.0, 7.0, 9.0 };
        
        Model model = train(X, y, 0.01, 1000);
        
        System.out.println("Trained Weights: " + Arrays.toString(model.weights));
        System.out.println("Trained Bias: " + model.bias);
        
        double[] query = { 5.0 };
        System.out.println("Predict for x=5: " + predict(model, query));
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
                predictions[j] = predict(model, X[j]);
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
    
    public static double predict(Model model, double[] x) {
        double result = model.bias;
        for (int i = 0; i < x.length; i++) {
            result += model.weights[i] * x[i];
        }
        return result;
    }
}
