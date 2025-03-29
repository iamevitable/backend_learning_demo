package demo_test;

import demo.LRUCache;

public class lru_test {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(1);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        int i = lruCache.get(2);
        System.out.println(i);
        int x = lruCache.get(1);
        System.out.println(x);
    }
}
