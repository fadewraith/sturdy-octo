package algorithms.realworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MAPREDUCE PARADIGM (Conceptual Simulation)
 * 
 * WHAT IT IS:
 * A programming model for processing massively large data sets with a parallel, 
 * distributed algorithm on a cluster. 
 * 
 * STRATEGY:
 * 1. MAP: Takes raw input and outputs key-value pairs.
 * 2. SHUFFLE/SORT (Framework level): Groups all values associated with the same key together.
 * 3. REDUCE: Takes a key and its list of values, and aggregates them into a smaller set of values.
 * 
 * This file simulates a distributed word-count task on a single machine.
 */
public class MapReduceDemo {

    // 1. MAPPER: Emits (word, 1) for every word found
    public static List<Map.Entry<String, Integer>> mapFunction(String document) {
        List<Map.Entry<String, Integer>> mapped = new ArrayList<>();
        String[] words = document.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");
        
        for (String word : words) {
            if (!word.isEmpty()) {
                mapped.add(new java.util.AbstractMap.SimpleEntry<>(word, 1));
            }
        }
        return mapped;
    }

    // 2. REDUCER: Sums all 1s for a specific key
    public static Map.Entry<String, Integer> reduceFunction(String key, List<Integer> values) {
        int sum = 0;
        for (int val : values) {
            sum += val;
        }
        return new java.util.AbstractMap.SimpleEntry<>(key, sum);
    }

    public static void main(String[] args) {
        System.out.println("--- MAPREDUCE SIMULATION DEMO ---");
        
        List<String> documents = Arrays.asList(
            "Hello world this is a test",
            "Hello mapreduce this is just a test",
            "mapreduce is powerful"
        );
        
        // --- PHASE 1: MAP ---
        System.out.println("1. Running Map Phase...");
        List<Map.Entry<String, Integer>> mapOutput = new ArrayList<>();
        for (String doc : documents) {
            mapOutput.addAll(mapFunction(doc));
        }
        
        // --- PHASE 2: SHUFFLE & SORT ---
        // Grouping by key (The Hadoop/Spark framework does this automatically over the network)
        System.out.println("2. Running Shuffle Phase...");
        Map<String, List<Integer>> shuffled = new HashMap<>();
        for (Map.Entry<String, Integer> entry : mapOutput) {
            shuffled.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(entry.getValue());
        }
        
        // --- PHASE 3: REDUCE ---
        System.out.println("3. Running Reduce Phase...");
        Map<String, Integer> finalOutput = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : shuffled.entrySet()) {
            Map.Entry<String, Integer> reduced = reduceFunction(entry.getKey(), entry.getValue());
            finalOutput.put(reduced.getKey(), reduced.getValue());
        }
        
        // Output Results
        System.out.println("\nFinal Word Count Results:");
        finalOutput.entrySet().stream()
                   .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                   .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));
    }
}
