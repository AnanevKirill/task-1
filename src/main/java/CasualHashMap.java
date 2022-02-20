import java.util.Objects;

public class CasualHashMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private Node<K, V>[] table;
    private int tableSize = 16;
    private int size = 0;

    public CasualHashMap(int tableSize) {
        table = new Node[tableSize];
        this.tableSize = tableSize;
    }

    public CasualHashMap() {
        table = new Node[tableSize];
    }

    static class Node<K, V> {
        public Node(int hashkey, K key, V value, Node<K, V> next) {
            this.hashkey = hashkey;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        int hashkey;
        K key;
        V value;
        private Node<K, V> next;
    }

    public int size() {
        return this.size;
    }

    public V put(K key, V value) {
        int hashCode = getHashCode(key);
        Node<K, V> newNode = new Node<>(hashCode, key, value, null);
        resize();
        V preValue = putNode(newNode);
        if (preValue == null) {
            size++;
        }
        resize();
        return preValue;
    }

    private int getHashCode(K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode();
    }

    private void resize() {
        if (size > tableSize * LOAD_FACTOR || tableSize == 0) {
            Node<K, V>[] oldTable = this.table;
            tableSize = ((tableSize * 3 / 2) + 1);
            table = new Node[tableSize];

            for (int i = 0; i < oldTable.length; i++) {
                if (oldTable[i] != null) {
                    for (Node<K, V> current = oldTable[i]; current != null; current = current.next) {
                        putNode(current);

                    }
                }
            }
        }
    }

    private V putNode(Node<K, V> node) {
        V preValue = null;
        int index = node.hashkey % Math.max(1, (table.length - 1));
        Node<K, V> tableNode = table[index];
        if (tableNode == null) {
            table[index] = node;
            return null;
        }
        for (Node<K, V> current = tableNode; ; current = current.next) {
            if (current.hashkey == node.hashkey && current.key.equals(node.key)) {
                preValue = current.value;
                current.value = node.value;
                break;
            }
            if (current.next == null) {
                current.next = node;

            }
        }


        return preValue;
    }

    public V get(K key) {
        int hashCode = getHashCode(key);
        int index = hashCode % (table.length - 1);
        Node<K, V> tableNode = table[index];
        if (tableNode == null) {
            return null;
        }
        for (Node<K, V> current = tableNode; current != null; current = current.next) {
            if (current.hashkey == hashCode && Objects.equals(current.key,key)) {
                return current.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int hashCode = getHashCode(key);
        int index = hashCode % (table.length - 1);
        Node<K, V> tableNode = table[index];
        if (tableNode == null) {
            return null;
        }
        Node<K, V> prevValue = null;
        for (Node<K, V> current = tableNode; current != null; current = current.next) {
            if (current.hashkey == hashCode && current.key.equals(key)) {
                if (prevValue == null) {
                    table[index] = current.next;
                } else {
                    prevValue.next = current.next;
                }
                size--;
                return current.value;
            }
            prevValue = current;
        }
        return null;
    }
}

