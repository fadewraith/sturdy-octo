package arrays;

import java.util.Scanner;

public class DaysAboveAvgTemp {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        // Ask the user how many days' temperatures to input
        System.out.print("How many days' temperatures? ");
        int numDays = console.nextInt();

        // Create an array to store the temperatures
        int[] temps = new int[numDays];

        // Record temperatures and calculate the sum
        int sum = 0;
        for (int i = 0; i < numDays; i++) {
            System.out.print("Day " + (i + 1) + "'s high temp: ");
            temps[i] = console.nextInt();
            sum += temps[i];
        }

        // Calculate the average temperature
        double average = sum / numDays;

        // Count how many days had temperatures above the average
        int above = 0;
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] > average) {
                above++;
            }
        }

        // Display the results
        System.out.println();
        System.out.println("Average Temp = " + average);
        System.out.println(above + " days above average");
    }
}
