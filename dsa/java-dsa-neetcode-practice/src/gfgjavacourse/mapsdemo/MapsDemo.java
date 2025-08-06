package gfgjavacourse.mapsdemo;

public class MapsDemo {


    /**
     * k = key, v = value
     * put(k, v), get(k), isEmpty(), remove(k)
     * containsKey(k), containsValue(v), replace(k, v), size()
     * keySet() - returns a set view of the key
     * values() - returns a collection view of the values
     * Set<Map.Entry<K, V>> entrySet() - returns a set view of the mapping
     * getOrDefault(k, default)
     * clear()
     * */

    /**
     * getOrDefault(key, defaultValue)
     * Returns the value for the key, or the default value if the key is not found.
     * putIfAbsent(key, value)
     * Puts the key-value pair in the map only if the key is not already present.
     * compute(key, remappingFunction)
     * Computes a new value for the specified key and its current mapped value.
     * computeIfAbsent(key, mappingFunction)
     * Computes a value if the specified key is not already associated with a value (or is mapped to null).
     * computeIfPresent(key, remappingFunction)
     * Computes a new value for the specified key if the key is already associated with a non-null value.
     * merge(key, value, remappingFunction)
     * If the specified key is not already associated with a value or is associated with null, associates it with the given non-null value.
     * forEach(BiConsumer)
     * Performs the given action for each entry in the map.
     * replaceAll(BiFunction)
     * Replaces each entry's value with the result of invoking the given function on that entry
     * */

    /**
     * LinkedHashMap<> m = new LinkedHashMap<>(initial_capacity(4), load_factor(0.6f), access_order(true))
     * */
}
