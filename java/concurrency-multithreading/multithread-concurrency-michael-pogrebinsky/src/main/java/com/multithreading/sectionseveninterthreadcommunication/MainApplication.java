package com.multithreading.sectionseveninterthreadcommunication;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringJoiner;

public class MainApplication {

    private static final String INPUT_DIR = "out";
    private static final String INPUT_FILE = "matrices";
    private static final String OUTPUT_DIR = "out";
    private static final String OUTPUT_FILE = "matrices_results.txt";
    private static final int N = 10;

    public static void main(String[] args) throws IOException {

        String projectRoot = System.getProperty("user.dir");
        File inputDir = new File(projectRoot, INPUT_DIR);
        File outputDir = new File(projectRoot, OUTPUT_DIR);

        // Create output directory if it doesn't exist
        if (!inputDir.exists()) {
            inputDir.mkdirs();
        }

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        ThreadSafeQueue threadSafeQueue = new ThreadSafeQueue();
        File inputFile = new File(inputDir, INPUT_FILE);
        File outputFile = new File(outputDir, OUTPUT_FILE);

        MatricesReaderProducer matricesReader = new MatricesReaderProducer(new FileReader(inputFile), threadSafeQueue);
        MatricesMultiplierConsumer matricesConsumer = new MatricesMultiplierConsumer(new FileWriter(outputFile), threadSafeQueue);

        matricesConsumer.start();
        matricesReader.start();

        System.out.println("Matrices taken from: " + inputFile.getAbsolutePath());
        System.out.println("Matrices generated at: " + outputFile.getAbsolutePath());
    }

    private static class MatricesMultiplierConsumer extends Thread {
        private ThreadSafeQueue queue;
        private FileWriter fileWriter;

        public MatricesMultiplierConsumer(FileWriter fileWriter, ThreadSafeQueue queue) {
            this.fileWriter = fileWriter;
            this.queue = queue;
        }

        private static void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
            for (int r = 0; r < N; r++) {
                StringJoiner stringJoiner = new StringJoiner(", ");
                for (int c = 0; c < N; c++) {
                    stringJoiner.add(String.format("%.2f", matrix[r][c]));
                }
                fileWriter.write(stringJoiner.toString());
                fileWriter.write('\n');
            }
            fileWriter.write('\n');
        }

        @Override
        public void run() {
            while (true) {
                MatricesPair matricesPair = queue.remove();
                if (matricesPair == null) {
                    System.out.println("No more matrices to read from the queue, consumer is terminating");
                    break;
                }

                float[][] result = multiplyMatrices(matricesPair.matrix1, matricesPair.matrix2);

                try {
                    saveMatrixToFile(fileWriter, result);
                } catch (IOException e) {
                }
            }

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private float[][] multiplyMatrices(float[][] m1, float[][] m2) {
            float[][] result = new float[N][N];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    for (int k = 0; k < N; k++) {
                        result[r][c] += m1[r][k] * m2[k][c];
                    }
                }
            }
            return result;
        }
    }

    private static class MatricesReaderProducer extends Thread {
        private Scanner scanner;
        private ThreadSafeQueue queue;

        public MatricesReaderProducer(FileReader reader, ThreadSafeQueue queue) {
            this.scanner = new Scanner(reader);
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                float[][] matrix1 = readMatrix();
                float[][] matrix2 = readMatrix();
                if(matrix1 == null || matrix2 == null) {
                    queue.terminate();
                    System.out.println("No more matrices to read. Producer thread is terminating.");
                    return;
                }
                MatricesPair matricesPair = new MatricesPair();
                matricesPair.matrix1 = matrix1;
                matricesPair.matrix2 = matrix2;
                queue.add(matricesPair);
            }
        }

        private float[][] readMatrix() {
            float[][] matrix = new float[N][N];
            for(int r = 0; r < N; r++) {
                if(!scanner.hasNext()) return null;
                String[] row = scanner.nextLine().split(",");
                for(int c = 0; c < N; c++) {
                    // matrix[r][c] = Float.parseFloat(row[c]);
                    matrix[r][c] = Float.valueOf(row[c]);
                }
            }
            scanner.nextLine(); // move scanner to the next position
            return matrix;
        }


    }

    private static class ThreadSafeQueue {
        private Queue<MatricesPair> queue = new LinkedList<>();
        private boolean isEmpty = true;
        private boolean isTerminate = false;
        // for back pressure
        private static final int CAPACITY = 5;

        public synchronized void add(MatricesPair matricesPair) {
            // while is for back pressure
            while(queue.size() == CAPACITY) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            queue.add(matricesPair);
            isEmpty = false;
            notify();
        }

        public synchronized MatricesPair remove() {
            MatricesPair matricesPair = null; // for back pressure
            while(isEmpty && !isTerminate) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            if(queue.size() == 1) {
                isEmpty = true;
            }

            if(queue.size() == 0 && isTerminate) {
                return null;
            }

            System.out.println("Queue size: " + queue.size());
            // return queue.remove();
            // for backpressure
            matricesPair = queue.remove();
            if(queue.size() == CAPACITY - 1) {
                notifyAll();
            }
            return matricesPair;
        }

        public synchronized void terminate() {
            isTerminate = true;
            notifyAll();
        }
    }

    private static class MatricesPair {
        public float[][] matrix1;
        public float[][] matrix2;
    }
}
