package com.key4dream.sun.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheMapNeverDel {

    private Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Lock writeLock = lock.writeLock();

    private Lock readLock = lock.readLock();

    private static CacheMapNeverDel instance = new CacheMapNeverDel();

    private CacheMapNeverDel() {
    }

    public static CacheMapNeverDel instance() {
        return instance;
    }

    public void put(String key, Object value) {
        writeLock.lock();
        try {
            cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public Object get(String key) {
        readLock.lock();
        try {
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public void remove(String key) {
        writeLock.lock();
        try {
            cache.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

}
