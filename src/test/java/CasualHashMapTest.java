import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class CasualHashMapTest {


    @Test
    void testPut() {
        CasualHashMap<String, String> map = new CasualHashMap<>();

        String prevValue = map.put("abc", "def");

        Assertions.assertNull(prevValue);

        prevValue = map.put("abc", "jkl");

        Assertions.assertEquals("def", prevValue);
    }

    @Test
    void testGet() {
        CasualHashMap<String, String> map = new CasualHashMap<>();

        map.put("abc", "def");

        Assertions.assertEquals("def", map.get("abc"));
    }

    @Test
    void testGetWithSameHashHode() {
        CasualHashMap<TestKey, String> map = new CasualHashMap<TestKey, String>();

        map.put(new TestKey("abc"), "cda");
        map.put(new TestKey("cca"), "frd");

        Assertions.assertEquals("cda", map.get(new TestKey("abc")));
        Assertions.assertEquals("frd", map.get(new TestKey("cca")));
    }

    @Test
    void remove() {
        CasualHashMap<String, String> map = new CasualHashMap<>();

        map.put("abc", "def");
        String deleteValue = map.remove("abc");

        Assertions.assertNull(map.get("abc"));
        Assertions.assertEquals("def", deleteValue);
    }

    @Test
    void testResize() {
        CasualHashMap<String, String> map = new CasualHashMap<>(0);

        map.put("abc", "def");
        map.put("ghk", "def");

        Assertions.assertEquals("def", map.get("abc"));
        Assertions.assertEquals("def", map.get("ghk"));


    }
    @Test
    void testPutNull() {
        CasualHashMap<String, String> map = new CasualHashMap<>();

        String prevValue = map.put(null, "null");

        Assertions.assertEquals("null", map.get(null));
    }

    static class TestKey {
        final String data;

        TestKey(String data) {
            this.data = data;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestKey testKey = (TestKey) o;
            return Objects.equals(data, testKey.data);
        }
    }
}