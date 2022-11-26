package database;

public interface Datasource<K, V> {
    public void set(K key, V value);

    public V get(K key);

    public void delete(K key);
}
