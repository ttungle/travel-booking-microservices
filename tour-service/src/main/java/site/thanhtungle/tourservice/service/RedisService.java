package site.thanhtungle.tourservice.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    /**
     * Write key value pair into Redis
     * */
    void set(String key, String value);

    /**
     * Set expires time
     * */
    void setTimeToLive(String key, long timeoutInDays);

    /**
     * Write the hash value into Redis
     * */
    void hashSet(String key, String field, Object value);

    /**
     * Check if hash field exists
     * */
    boolean hashExists(String key, String field);

    /**
     * Check if key exists
     * */
    boolean keyExists(String key);

    /**
     * Get a value of the key from Redis
     * */
    Object get(String key);

    /**
     * Get hash value of the key from Redis
     * */
    public Map<String, Object> getField(String key);

    /**
     * Get value of hash field from Redis
     * */
    Object hashGet(String key, String field);

    /**
     * Get list of values of hash field with the field prefix
     * */
    List<Object> hashGetByFieldPrefix(String key, String fieldPrefix);

    /**
     * Get a list of values of the key from Redis
     * */
    Set<String> getFieldPrefixes(String key);

    void delete(String key);

    void delete(String key, String field);

    void delete(String key, List<String> fields);
}
