package demo;


import java.util.HashMap;

public class LRUCache {
    //内部存储的结构
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

    //队列容量
    private final int capacity;

    //虚拟头节点
    private final Node dummy = new Node(0, 0);

    //内部存储的hash表，表示key和node节点之间的关系
    private final HashMap<Integer, Node> keyToNode = new HashMap<>();

    //构造函数
    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy.next = dummy;
        dummy.prev = dummy;
    }

    //获得节点的值，存在返回值，不存在返回-1
    public int get(int key) {
        Node node = getNode(key);
        return node != null ? node.value : -1;
    }

    //加入节点
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

    //获取节点，存在返回节点，不存在返回null
    private Node getNode(int key) {
        if (!keyToNode.containsKey(key)) {
            return null;
        }
        Node node = keyToNode.get(key);
        remove(node);
        pushFront(node);
        return node;
    }

    //移除节点，既是双向链表移除节点的做法
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    //将节点放到双向链表的开始，通过虚拟头节点来实现
    private void pushFront(Node node) {
        node.prev = dummy;
        node.next = dummy.next;
        node.prev.next = node;
        node.next.prev = node;
    }
}
