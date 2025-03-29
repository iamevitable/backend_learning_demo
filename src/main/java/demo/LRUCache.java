package demo;

import jdk.jshell.spi.SPIResolutionException;

import java.util.HashMap;
import java.util.HashSet;

public class LRUCache {
    private static class Node{
        int key;
        int value;
        Node prev;
        Node next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    private final int capacity;
    private final Node dummy = new Node(0, 0);
    private final HashMap<Integer, Node> keyToNode = new HashMap<>();
    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy.next = dummy;
        dummy.prev = dummy;
    }
    public int get(int key) {
        Node node = getNode(key);
        return node != null ? node.value : -1;
    }
    public void put(int key, int value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return ;
        }
        node = new Node(key, value);
        keyToNode.put(key, node);
        pushFront(node);
        if (keyToNode.size() > capacity) {
            Node backNode = dummy.prev;
            keyToNode.remove(backNode.key);
            remove(backNode);
        }
    }
    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) {
            return null;
        }
        Node node = keyToNode.get(key);
        remove(node);
        pushFront(node);
        return node;
    }
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    private void pushFront(Node node) {
        node.prev = dummy;
        node.next = dummy.next;
        node.prev.next = node;
        node.next.prev = node;
    }
}
