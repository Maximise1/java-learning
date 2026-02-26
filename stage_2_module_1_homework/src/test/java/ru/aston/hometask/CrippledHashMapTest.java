package ru.aston.hometask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CrippledHashMapTest {

    private CrippledHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new CrippledHashMap<>();
    }

    @Test
    void when_putAndGet_then_storeAndRetrieveValue() {
        map.put("one", 1);
        assertEquals(1, map.get("one"));
    }

    @Test
    void when_putExistingKey_then_shouldReturnOldValue() {
        map.put("one", 1);
        Integer oldValue = map.put("one", 2);

        assertEquals(1, oldValue);
        assertEquals(2, map.get("one"));
    }

    @Test
    void when_putNullKey_then_shouldWork() {
        map.put(null, 10);
        assertEquals(10, map.get(null));
        assertTrue(map.containsKey(null));
    }

    @Test
    void when_putNullValue_then_shouldWork() {
        map.put("key", null);

        assertTrue(map.containsKey("key"));
        assertNull(map.get("key"));
        assertTrue(map.containsValue(null));
    }

    @Test
    void when_removeExistingKey_then_shouldReturnValueAndDecreaseSize() {
        map.put("one", 1);
        map.put("two", 2);

        Integer removed = map.remove("one");

        assertEquals(1, removed);
        assertFalse(map.containsKey("one"));
        assertEquals(1, map.size());
    }

    @Test
    void when_removeNonExistingKey_then_shouldReturnNull() {
        assertNull(map.remove("unknown"));
    }

    @Test
    void when_sizeCalled_then_shouldReflectNumberOfEntries() {
        assertEquals(0, map.size());

        map.put("a", 1);
        map.put("b", 2);

        assertEquals(2, map.size());

        map.remove("a");

        assertEquals(1, map.size());
    }

    @Test
    void when_putAll_then_shouldInsertAllEntries() {
        Map<String, Integer> other = Map.of(
                "a", 1,
                "b", 2,
                "c", 3
        );

        map.putAll(other);

        assertEquals(3, map.size());
        assertEquals(2, map.get("b"));
    }

    @Test
    void when_clear_then_shouldRemoveAllEntries() {
        map.put("a", 1);
        map.put("b", 2);

        map.clear();

        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        assertNull(map.get("a"));
    }

    @Test
    void when_keySet_then_shouldContainAllKeys() {
        map.put("a", 1);
        map.put("b", 2);

        Set<String> keys = map.keySet();

        assertEquals(2, keys.size());
        assertTrue(keys.contains("a"));
        assertTrue(keys.contains("b"));
    }

    @Test
    void when_values_then_shouldContainAllValues() {
        map.put("a", 1);
        map.put("b", 2);

        Collection<Integer> values = map.values();

        assertEquals(2, values.size());
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));
    }

    @Test
    void when_entrySet_then_shouldContainAllEntries() {
        map.put("a", 1);
        map.put("b", 2);

        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        assertEquals(2, entries.size());

        assertTrue(entries.stream()
                .anyMatch(e -> e.getKey().equals("a") && e.getValue().equals(1)));
    }
}