package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    private Node root;

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private int size;

        public Node(K key, V value, int size) {
            this.key = key; this.value = value; this.size = size;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }
    private boolean containsKey(Node node, K key) {
        if (node == null) return false;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return true;
        else if (cmp < 0)
            return containsKey(node.left, key);
        else return containsKey(node.right,key);
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }
    private V get(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node.value;
        else if (cmp < 0) return get(node.left, key);
        else return get(node.right, key);
    }

    @Override
    public int size() {
        return size(root);
    }
    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    private Node put(Node node, K key, V value) {
        if (node == null) return new Node(key, value, 1);
        int cmp = key.compareTo(node.key);
        // if (cmp == 0) return null;
        if (cmp < 0) node.left = put(node.left, key, value);
        else node.right = put(node.right, key, value);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        keySet(set, root);
        return set;
    }
    private void keySet(Set<K> set, Node node) {
        if (node == null) return;
        set.add(node.key);
        keySet(set, node.left);
        keySet(set, node.right);
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        root = remove(root, key);
        return value;
    }
    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }
    private Node remove(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = remove(node.left, key);
        else if (cmp > 0) node.right = remove(node.right, key);
        else {
            if (node.left == null || node.right == null) {
                return node.left == null ? node.right : node.left;
            } else {
                Node t = node;
                node = min(node.right);
                node.right = deleteMin(t.right);
                node.left = t.left;
            }
        }
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    public void printInOrder() {
        // 中序遍历
        printInOrder(root);
    }
    private void printInOrder(Node node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.key);
        printInOrder(node.right);
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
