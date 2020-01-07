package phonis.entitytracer.serializable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Serializable collection used to Map like plugin data
 *
 * @param <K> Key for HashMap
 * @param <V> Serializable type for value of HashMap
 */
public class HashMapData<K, V extends Serializable> {
    public HashMap<K, V> data = new HashMap<>();
    public String filename;

    /**
     * HashMapData constructor that sets filename for serialization
     *
     * @param filename String filename of out file
     */
    public HashMapData(String filename) {
        this.filename = filename;
    }
}
