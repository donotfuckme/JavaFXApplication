package ua.com.meral.util;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public final class CacheUtil {

    private static CacheUtil instance;

    @Getter
    private Map<String,Object> cache;

    public static CacheUtil getInstance() {
        if (instance == null) {
            instance = new CacheUtil();
        }
        return instance;
    }

    private CacheUtil() {
        cache = new HashMap<>();
    }
}
