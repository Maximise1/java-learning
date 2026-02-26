package ru.aston.hometask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CrippledHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_BUCKET_AMOUNT = 100;
    private List<Pair<K, V>>[] container = new ArrayList[DEFAULT_BUCKET_AMOUNT];
    private int size = 0;

    public static class Pair<K, V> implements Map.Entry<K, V> {
        private final K first;
        private V second;

        public Pair(K f, V v) {
            first = f;
            second = v;
        }

        @Override
        public K getKey() {
            return first;
        }

        @Override
        public V getValue() {
            return second;
        }

        @Override
        public V setValue(V o) {
            second = o;
            return o;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Map.Entry<?, ?> entry)) return false;
            return Objects.equals(first, entry.getKey()) &&
                    Objects.equals(second, entry.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

    public CrippledHashMap() {
        for (int i = 0; i < DEFAULT_BUCKET_AMOUNT; i++) {
            container[i] = new ArrayList<>();
        }
    }

    private int getBucketIndex(Object key) {
        int hashCode;
        if (key == null) {
            hashCode = 0;
        } else {
            hashCode = key.hashCode();
        }
        return (hashCode & 0x7fffffff) % container.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        int bucketIndex = getBucketIndex(o);
        int bucketSize = container[bucketIndex].size();

        for (int i = 0; i < bucketSize; i++) {
            if (Objects.equals(container[bucketIndex].get(i).getKey(), o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        Collection<V> values = values();
        for (V value : values) {
            if (Objects.equals(value, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object o) {
        int bucketIndex = getBucketIndex(o);
        List<Pair<K, V>> bucket = container[bucketIndex];

        for (Pair<K, V> entry : bucket) {
            if (Objects.equals(entry.first, o)) {
                return entry.second;
            }
        }

        return null;
    }

    @Override
    public V put(K o, V o2) {
        int bucketIndex = getBucketIndex(o);
        List<Pair<K, V>> bucket = container[bucketIndex];

        for (int i = 0; i < bucket.size(); i++) {
            Pair<K, V> entry = bucket.get(i);
            if (Objects.equals(entry.first, o)) {
                V oldValue = entry.getValue();
                entry.setValue(o2);
                return oldValue;
            }
        }

        bucket.add(new Pair<>(o, o2));
        size++;
        return null;
    }

    @Override
    public V remove(Object o) {
        int bucketIndex = getBucketIndex(o);
        List<Pair<K, V>> bucket = container[bucketIndex];

        for (int i = 0; i < bucket.size(); i++) {
            Pair<K, V> entry = bucket.get(i);
            if (Objects.equals(entry.first, o)) {
                V value = entry.second;
                bucket.remove(i);
                size--;
                return value;
            }
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        container = new ArrayList[DEFAULT_BUCKET_AMOUNT];
        for (int i = 0; i < DEFAULT_BUCKET_AMOUNT; i++) {
            container[i] = new ArrayList<>();
        }
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();

        for (int i = 0; i < container.length; i++) {
            List<Pair<K, V>> bucket = container[i];
            for (int k = 0; k < bucket.size(); k++) {
                keySet.add(bucket.get(k).first);
            }
        }

        return keySet;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>(size);
        for (List<Pair<K, V>> bucket : container) {
            values.addAll(bucket.stream().map(Pair::getValue).toList());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = new HashSet<>();

        for (List<Pair<K, V>> bucket : container) {
            entrySet.addAll(bucket);
        }

        return entrySet;
    }
}
