package io.github.liujialongstar.algorithm.search;

import io.github.liujialongstar.algorithm.LinkedQueue;

/**
 * @author liujialong
 * @date 2021/8/5
 * 红黑树
 */
public class RedBlackBinarySearchTree<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V> {
    private Node root;

    private static final boolean RED = true;

    private static final boolean BLACK = false;

    /**
     * 将键值对存放再表中(若值为空则将键key从表中删除)
     *
     * @param key
     * @param val
     */
    @Override
    public void put(K key, V val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, K key, V val){
        if(h == null) {
            return new Node(key, val, 1, RED);
        }

        int cmp = key.compareTo(h.key);
        if(cmp < 0) {
            h.left = put(h.left, key, val);
        }else if(cmp > 0) {
            h.right = put(h.right, key, val);
        }else {
            h.val = val;
        }

        if(isRed(h.right) && !isRed(h.left)){
            h = rotateLeft(h);
        }
        if(isRed(h.left) && isRed(h.left.left)){
            h = rotateRight(h);
        }
        if(isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.N = 1 + size(h.left) + size(h.right);
        return h;
    }

    /**
     * 获取键key对应的值(若键key不存在则返回null)
     *
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if(x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            return get(x.left, key);
        }else if (cmp > 0) {
            return get(x.right, key);
        }else {
            return x.val;
        }
    }

    /**
     * 从表中删除键key及其对应的值
     *
     * @param key
     */
    @Override
    public void delete(K key) {
        if(!contains(key)){
            return;
        }
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, K key) {
        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    /**
     * 键key再表中是否存在对应的值
     *
     * @param key
     * @return
     */
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    /**
     * 表是否为空
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 表中键值对的数量
     *
     * @return
     */
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.N;
    }

    /**
     * 最小的键
     *
     * @return
     */
    @Override
    public K min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if(x.left == null) {
            return x;
        }else {
            return min(x.left);
        }
    }

    /**
     * 最大的键
     *
     * @return
     */
    @Override
    public K max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if(x.right == null) {
            return x;
        }else {
            return max(x.right);
        }
    }

    /**
     * 小于等于key的最大键
     *
     * @param key
     * @return
     */
    @Override
    public K floor(K key) {
        Node x = floor(root, key);
        if(x == null) {
            return null;
        }
        return x.key;
    }

    private Node floor(Node x, K key) {
        if(x == null){
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            return floor(x.left, key);
        }else if(cmp > 0) {
            Node t = floor(x.right, key);
            if(t != null) {
                return t;
            }else {
                return x;
            }
        }else {
            return x;
        }
    }

    /**
     * 大于等于key的最小键
     *
     * @param key
     * @return
     */
    @Override
    public K ceiling(K key) {
        Node x = ceiling(root, key);
        if(x == null) {
            return null;
        }
        return x.key;
    }

    private Node ceiling(Node x, K key) {
        if(x == null) {
            return null;
        }
        int cmp = x.key.compareTo(key);
        if(cmp < 0) {
            return ceiling(x.right, key);
        }else if(cmp > 0) {
            Node t = ceiling(x.left, key);
            if(t != null) {
                return t;
            }else {
                return x;
            }
        }else {
            return x;
        }
    }

    /**
     * 小于key的键的数量
     * 这里的1是根结点
     * @param key
     * @return
     */
    @Override
    public int rank(K key) {
        return rank(root, key);
    }

    private int rank(Node x, K key) {
        if(x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            return rank(x.left, key);
        }else if(cmp > 0) {
            return 1 + size(x.left) +rank(x.right, key);
        }else {
            return size(x.left);
        }
    }

    /**
     * 排名为k的键
     * 这里的1是根结点
     * @param k
     * @return
     */
    @Override
    public K select(int k) {
        return select(root , k);
    }

    private K select(Node x, int k) {
        if(x == null) {
            return null;
        }
        int t = size(x.left);
        if(t > k) {
            return select(x.left, k);
        }else if(t < k) {
            return select(x.right, k - t - 1);
        }else {
            return x.key;
        }
    }

    /**
     * 删除最小的键
     */
    @Override
    public void deleteMin() {
        root = deleteMin(root);
        if(!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * 删除最大的键
     */
    @Override
    public void deleteMax() {
        root = deleteMax(root);
        if(!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node deleteMax(Node h) {
        if(isRed(h.left)) {
            h = rotateRight(h);
        }

        if(h.right == null){
            return null;
        }

        if(!isRed(h.right) && !isRed(h.right.left)){
            h = moveRedRight(h);
        }

        h.right = deleteMax(h.right);
        return balance(h);
    }

    @Override
    public Iterable<K> keys() {
        return keys(min(), max());
    }

    /**
     * [low..high]之间所有键的集合, 已排序
     *
     * @param low
     * @param high
     * @return
     */
    @Override
    public Iterable<K> keys(K low, K high) {
        LinkedQueue<K> queue = new LinkedQueue<>();
        keys(root, queue, low, high);
        return queue;
    }

    private void keys(Node x, LinkedQueue<K> queue, K low, K high){
        if(x == null) {
            return;
        }
        int cmpLow = low.compareTo(x.key);
        int cmpHigh = high.compareTo(x.key);

        // 递归调用小于low的左子树
        if(cmpLow < 0) {
            keys(x.left, queue, low, high);
        }
        // 如果当前结点落在low..high的范围内, 将当前节点的键压入队列
        if(cmpLow <= 0 && cmpHigh >= 0) {
            queue.enqueue(x.key);
        }

        // 递归调用大于high的右子树
        if(cmpHigh > 0) {
            keys(x.right, queue, low, high);
        }
    }

    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    class Node {
        /**
         * 键
         */
        private K key;

        /**
         * 值
         */
        private V val;

        /**
         * 左链接
         */
        private Node left;

        /**
         * 右链接
         */
        private Node right;

        /**
         * 计数器, 以该节点为根的子树的结点总数
         */
        private int N;

        /**
         * 由其父结点指向它的链接的颜色
         */
        private boolean color;

        public Node(K key, V val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if(x == null) {
            return false;
        }
        return x.color == RED;
    }

    /**
     * 左旋转, 将红色右链接变换为红色左链接
     * 将结点h右链接提取为x, 节点x的左链接指向的节点链接到节点h的右链接, 节点x的左链接指向节点h, 转换完成
     * 重置节点颜色, 重置节点大小
     * @param h
     * @return
     */
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 右旋转, 左旋转的逆操作
     * @param h
     * @return
     */
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 颜色转换
     * @param h
     */
    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public static void main(String[] args) {
        String[] a = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        RedBlackBinarySearchTree<String, Integer> st = new RedBlackBinarySearchTree<>();
        for (int i = 0; i < a.length; i++) {
            String key = a[i];
            st.put(key, i);
        }
        for(String s: st.keys()){
            System.out.println(s + ": " + st.get(s));
        }
    }
}
