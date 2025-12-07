package com.multithreading.sectiontwothreadingcoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExThree {

    public static void main(String[] args) throws InterruptedException {

        List<Long> inputNumbers = Arrays.asList(1000000000000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5566L);
        // we want to calculate factorial

        List<FactorialThread> threads = new ArrayList<>();
        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        // to prevent race condition
        // what if any numbers is too large, in that case we dont know how long it will take to calculate
        // so we use join() with time limit
        for (Thread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("Calculation for " + inputNumbers.get(i) + " is not finished yet");
            }
        }
    }

    private static class FactorialThread extends Thread {

        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            result = factorial(inputNumber);
            isFinished = true;
        }

        private BigInteger factorial(long inputNumber) {
            BigInteger tempRes = BigInteger.ONE;
            for (long i = inputNumber; i > 0; i--) {
                tempRes = tempRes.multiply(new BigInteger(Long.toString(i)));
            }
            return tempRes;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
