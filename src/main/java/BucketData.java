class BucketData<K, V> {
    private int hash;
    private K key;
    private V value;

    public BucketData(int hash, K key, V value) {
        this.hash = hash;
        this.key = key;
        this.value = value;
    }

    public int getHash() {
        return hash;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
