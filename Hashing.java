package sample;

//  Sukhamrit Singh
//  Hashing
/*
The program has a new concrete class that implements MyMap (Listing 27.1)
using open addressing with quadratic probing. For simplicity, I used
f(key) = key % size as the hash function, where size is the hash-table size.
Initially, the hash-table size is 4. The table size is doubled whenever
the load factor exceeds the threshold (0.5). I wrote a driver program
to test your class.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Hashing {
    public static void main(String[] args) {

        //  Creating a map and putting keys and values
        MyMap<String, Integer> map = new MyHashMap<>();
        map.put("Sukhamrit", 25);
        map.put("Bob", 27);
        map.put("Joe", 20);
        map.put("Josh", 22);
        map.put("Messi", 21);
        map.put("Virgil", 20);
        map.put("Andre", 30);
        map.put("Luis", 21);
        map.put("Felix", 54);
        map.put("Angela", 97);


        //  Printing out the number of entries in the map
        System.out.println("Entries in map: " + map);

        //  Printing value for Messi
        System.out.println("The age of Messi is " +
                map.get("Messi"));

        //  Checking is alex is contained in map
        System.out.println("Is Alex in the map? " +
                map.containsKey("Alex"));

        //  Checking if any keys have value of 29
        System.out.println("Is age 29 in the map? " +
                map.containsValue(29));

        //  Printing out all keys in map
        System.out.print("Keys in map: ");
        for (String key : map.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();

        //  Printing out all values in map
        System.out.print("Values in map: ");
        for (int value : map.values()) {
            System.out.print(value + " ");
        }
        System.out.println();

        //  Removing key Luis
        map.remove("Luis");
        System.out.println("Entries in map " + map);
    }
}

interface MyMap<K, V> {
    /** Remove all of the entries from this map */
    public void clear();

    /** Return true if the specified key is in the map */
    public boolean containsKey(K key);

    /** Return true if this map contains the specified value */
    public boolean containsValue(V value);

    /** Return a set of entries in the map */
    public java.util.Set<Entry<K, V>> entrySet();

    /** Return the first value that matches the specified key */
    public V get(K key);

    /** Return true if this map contains no entries */
    public boolean isEmpty();

    /** Return a set consisting of the keys in this map */
    public java.util.Set<K> keySet();

    /** Add an entry (key, value) into the map */
    public V put(K key, V value);

    /** Remove the entries for the specified key */
    public Entry<K, V> remove(K key);

    /** Return the number of mappings in this map */
    public int size();

    /** Return a set consisting of the values in this map */
    public java.util.Set<V> values();

    /** Define inner class for Entry */
    public static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }
}

class MyHashMap<K, V> implements MyMap<K, V> {
    // Define the default hash-table size. Must be a power of 2
    private static int DEFAULT_INITIAL_CAPACITY = 4;
    private static int MAXIMUM_CAPACITY = 1 << 30;
    private static float DEFAULT_MAX_LOAD_FACTOR = 0.5f;
    Entry<K, V>[] table;
    private int capacity;
    private float loadFactorThreshold;
    private int size = 0;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactorThreshold) {
        if (initialCapacity > MAXIMUM_CAPACITY)
            this.capacity = MAXIMUM_CAPACITY;
        else
            this.capacity = trimToPowerOf2(initialCapacity);
        this.loadFactorThreshold = loadFactorThreshold;
        table = new Entry[capacity];
    }

    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Return a power of 2 for initialCapacity
     */
    private int trimToPowerOf2(int initialCapacity) {
        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1; // Same as capacity *= 2. <= is more efficient
        }
        return capacity;
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                table[i] = null;
            }
        }
    }

    @Override
    /** Return true if this map contains the value */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                if (table[i].getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> set = new HashSet<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                set.add(entry);
            }
        }
        return set;
    }

    /**
     * Hash function
     */
    private int hash(int hashCode) {
        return supplementalHash(hashCode) & (capacity - 1);
    }

    @Override
    public V get(K key) {
        int hash = hash(key.hashCode()) % capacity;

        int n = 0;
        while (table[hash] != null && !table[hash].getKey().equals(key)) {
            hash = (hash + n * n) % capacity;
            n++;
        }

        if (table[hash] == null) {
            return null;
        }

        return table[hash].getValue();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                set.add(entry.key);
            }
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        if (((double) size) / capacity >= loadFactorThreshold) {
            ArrayList<Entry<K, V>> items = new ArrayList<>();
            for (K key_ : keySet()) {
                Entry<K, V> entry = new Entry<>(key_, get(key_));
                items.add(entry);
                remove(key);
            }

            capacity *= 2;

            table = new Entry[capacity];
            for (Entry<K, V> item : items) {
                put(item.getKey(), item.getValue());
            }
        }

        int hash = hash(key.hashCode()) % capacity;

        int n = 0;
        while (table[hash] != null && table[hash].getKey() != key) {
            hash = (hash + n * n) % capacity;
            n++;
        }

        table[hash] = new Entry<>(key, value);
        size++;
        return table[hash].getValue();
    }

    @Override
    public Entry<K, V> remove(K key) {
        int hash = hash(key.hashCode()) % capacity;

        int n = 0;
        while (table[hash] != null && !table[hash].getKey().equals(key)) {
            hash = (hash + n * n) % capacity;
            n++;
        }

        Entry<K, V> item = table[hash];
        table[hash] = null;
        size--;

        return item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<V> values() {
        HashSet<V> set = new HashSet<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                set.add(entry.value);
            }
        }
        return set;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                builder.append(table[i]);
                builder.append("  ");
            }
        }

        return builder.toString();
    }
}