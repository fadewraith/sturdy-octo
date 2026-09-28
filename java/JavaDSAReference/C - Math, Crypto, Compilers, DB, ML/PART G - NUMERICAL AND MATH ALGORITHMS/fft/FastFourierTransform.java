package math.fft;

/**
 * WHAT IT IS: Fast Fourier Transform (FFT). Computes Discrete Fourier Transform.
 * STRATEGY: Divide and conquer (Cooley-Tukey). Split sequence into even and odd indices recursively.
 * TIME/SPACE COMPLEXITY: Time: O(N log N), Space: O(N).
 * REAL-WORLD ANALOGY / USE CASE: Signal processing, audio compression, polynomial multiplication.
 * WHEN TO USE / COMBINATION: When frequency domain analysis is needed.
 * 
 * PSEUDOCODE:
 * if N == 1 return x
 * even = FFT(x[0, 2, ...])
 * odd = FFT(x[1, 3, ...])
 * for k = 0 to N/2-1:
 *   t = exp(-2i * PI * k / N) * odd[k]
 *   X[k] = even[k] + t
 *   X[k + N/2] = even[k] - t
 */
public class FastFourierTransform {
    public static class Complex {
        public double re, im;
        public Complex(double re, double im) { this.re = re; this.im = im; }
        public Complex add(Complex b) { return new Complex(this.re + b.re, this.im + b.im); }
        public Complex sub(Complex b) { return new Complex(this.re - b.re, this.im - b.im); }
        public Complex mul(Complex b) { return new Complex(this.re * b.re - this.im * b.im, this.re * b.im + this.im * b.re); }
        public String toString() { return String.format("%.2f + %.2fi", re, im); }
    }

    public static Complex[] fft(Complex[] x) {
        int n = x.length;
        if (n == 1) return new Complex[]{x[0]};

        Complex[] even = new Complex[n / 2];
        Complex[] odd = new Complex[n / 2];
        for (int k = 0; k < n / 2; k++) {
            even[k] = x[2 * k];
            odd[k] = x[2 * k + 1];
        }

        Complex[] q = fft(even);
        Complex[] r = fft(odd);
        Complex[] y = new Complex[n];

        for (int k = 0; k < n / 2; k++) {
            double kth = -2 * k * Math.PI / n;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            Complex t = wk.mul(r[k]);
            y[k] = q[k].add(t);
            y[k + n / 2] = q[k].sub(t);
        }
        return y;
    }

    public static void main(String[] args) {
        Complex[] x = {
            new Complex(1, 0), new Complex(1, 0),
            new Complex(1, 0), new Complex(1, 0)
        };
        Complex[] y = fft(x);
        for (Complex c : y) {
            System.out.println(c);
        }
    }
}
