package org.example.services.cache;

public interface ICacheService {
    /**
     * Получить значение по ключу
     */
    String get(String key);

    /**
     * Сохранить значение с TTL (в секундах)
     */
    void set(String key, String value, int ttlSeconds);

    /**
     * Удалить значение из кэша
     */
    void delete(String key);
}
