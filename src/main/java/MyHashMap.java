import java.util.LinkedList;
import java.util.List;

/**
 * <p>Реалізувати свою HashMap, а саме методи `put(K key, V value)`, `getValue()` та `getSize()`.
 * Дотриматися основних вимог щодо реалізації мапи (initial capacity, load factor, resize...)
 * За бажанням можна реалізувати інші методи інтрефейсу Map.</p>
 */
public class MyHashMap<K, V> implements MyMap<K, V> {
    private int capacity = 16;
    private double loadFactor = 0.75;
    private int size = 0;
    private List[] table = new LinkedList[capacity];

    @Override
    public void put(K key, V value) {
        int currentHash;
        int index;

        if (key == null) {
            index = 0;
            currentHash = 0;
        } else {
            currentHash = key.hashCode();
            index = Math.abs(currentHash % (capacity - 1)) + 1;
        }

        if (table[index] == null) {
            table[index] = new LinkedList();
            table[index].add(new BucketData(currentHash, key, value));
            size++;
        } else {
            boolean isValueReplaced = false;

            for (int i = 0; i < table[index].size(); i++) {
                BucketData<K, V> currentNode = (BucketData<K, V>) table[index].get(i);

                if (currentNode != null) {
                    if ((currentNode.getHash() == currentHash) && (key == currentNode.getKey()
                            || key.equals(currentNode.getKey()))) {
                        currentNode.setValue(value);
                        isValueReplaced = true;
                        break;
                    }
                }
            }

            if (!isValueReplaced) {
                table[index].add(new BucketData<K, V>(currentHash, key, value));
                size++;
            }
        }

        resize();
    }

    public void resize() {
        if (size / (double)capacity > loadFactor) {
            List[] oldTable = table;
            size = 0;
            int oldCapacity = capacity;
            capacity *= 2;
            table = new LinkedList[capacity];

            for (int i = 0; i < oldCapacity; i++) {
                if (oldTable[i] == null) {
                    continue;
                }
                for (int j = 0; j < oldTable[i].size(); j++) {
                    BucketData<K, V> currentNode = (BucketData<K, V>) oldTable[i].get(j);

                    if (currentNode != null) {
                        put(currentNode.getKey(), currentNode.getValue());
                    }
                }
            }
        }
    }

    @Override
    public V getValue(K key) {
        int currentHash;
        int index;

        if (key == null) {
            index = 0;
            currentHash = 0;
        } else {
            currentHash = key.hashCode();
            index = Math.abs(currentHash % (capacity - 1)) + 1;
        }

        V value = null;

        if (table[index] != null) {
            for (int i = 0; i < table[index].size(); i++) {
                BucketData<K, V> currentNode = (BucketData<K, V>) table[index].get(i);

                if (currentNode != null) {
                    if ((currentNode.getHash() == currentHash) && (key == currentNode.getKey()
                            || key.equals(currentNode.getKey()))) {
                        value = currentNode.getValue();
                        break;
                    }
                }
            }
        }

        return value;
    }

    @Override
    public int getSize() {
        return size;
    }
}
