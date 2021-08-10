package io.github.liujialongstar.algorithm.search;

/**
 * @author liujialong
 * @date 2021/8/5
 * 红黑树
 */
public class RedBlackBinarySearchTree<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V> {
    private Node root;

    /**
     * 将键值对存放再表中(若值为空则将键key从表中删除)
     *
     * @param key
     * @param val
     */
    @Override
    public void put(K key, V val) {
        put(root, key, val);
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
        return null;
    }

    /**
     * 从表中删除键key及其对应的值
     *
     * @param key
     */
    @Override
    public void delete(K key) {

    }

    /**
     * 键key再表中是否存在对应的值
     *
     * @param key
     * @return
     */
    @Override
    public boolean contains(K key) {
        return false;
    }

    /**
     * 表是否为空
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return false;
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
        return null;
    }

    /**
     * 最大的键
     *
     * @return
     */
    @Override
    public K max() {
        return null;
    }

    /**
     * 小于等于key的最大键
     *
     * @param key
     * @return
     */
    @Override
    public K floor(K key) {
        return null;
    }

    /**
     * 大于等于key的最小键
     *
     * @param key
     * @return
     */
    @Override
    public K ceiling(K key) {
        return null;
    }

    /**
     * 小于key的键的数量
     *
     * @param key
     * @return
     */
    @Override
    public int rank(K key) {
        return 0;
    }

    /**
     * 排名为k的键
     *
     * @param k
     * @return
     */
    @Override
    public K select(int k) {
        return null;
    }

    /**
     * 删除最小的键
     */
    @Override
    public void deleteMin() {

    }

    /**
     * 删除最大的键
     */
    @Override
    public void deleteMax() {

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
        return null;
    }

    /**
     * 表中所有键的集合, 已排序
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
        return null;
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

    private static final boolean RED = true;

    private static final boolean BLACK = false;

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
}
