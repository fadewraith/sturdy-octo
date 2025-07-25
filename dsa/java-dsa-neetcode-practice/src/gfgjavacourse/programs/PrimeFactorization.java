package gfgjavacourse.programs;

public class PrimeFactorization {

    public boolean isPrime(int n) {
        for(int i = 2; i < n; i++) {
            if(n % i == 0) return false;
        }
        return true;
    }

    public void printFactors(int n) {
        for(int i = 2; i < n; i++) {
            if(isPrime(i)) {
                int x = i;
                while(n % x == 0) {
                    System.out.print(i);
                    x *= i;
                }
            }
        }
    }
}
