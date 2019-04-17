package kz.pashim;

import java.util.HashMap;

public class MadnessMap<K, V> extends HashMap<K, V> {

    public V putVal(K key, V value) {
        return super.put(key, value);
    }

    public void putAll(MadnessMap<? extends K, ? extends V> map) {
        super.putAll(map);
    }

    public V getVal(K key) {
        return super.get(key);
    }

}
