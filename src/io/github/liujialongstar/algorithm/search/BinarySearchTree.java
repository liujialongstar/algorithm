package io.github.liujialongstar.algorithm.search;

import io.github.liujialongstar.algorithm.LinkedQueue;

/**
 * @author liujialong
 * @date 2021/7/20
 * 二叉查找树
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V>{
    private Node root;

    /**
     * 将键值对存放再表中(若值为空则将键key从表中删除)
     *
     * @param key
     * @param val
     */
    @Override
    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if(x == null) {
            return new Node(key, val, 1);
        }

        int cmp = key.compareTo(x.key);

        if(cmp < 0) {
            x.left = put(x.left, key, val);
        }else if(cmp > 0) {
            x.right = put(x.right, key, val);
        }else {
            x.val = val;
        }
        x.N = size(x.left) + size(x.right) +1;
        return x;
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
     * 如果key小于结点x, 在x的左子树中递归调用delete()
     * 如果key大于结点x, 在x的右子树中递归调用delete()
     * 如果key等于结点, 删除结点x, 步骤如下:
     * 1. 将指向即将被删除的结点x的链接保存为t;
     * 2. 将x指向它的后继结点min(t.right), 也就是大于原本x结点的最小的结点, 此时左右链接均为空, t.left都是键大于x.key的结点, r.right都是键大于等于x.key的结点;
     * 3. 将x的右链接指向deleteMin(t.right), 也就是删除右子树中最小节点后的二叉查找树, 此时右子树的所有结点仍都大于x.key;
     * 4. 将x的左链接设置为t.left, 其下所有结点都小于x.key.
     * 5. 重置计数器
     * @param key
     */
    @Override
    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if(x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            x.left = delete(x.left, key);
        }else if (cmp > 0) {
            x.right = delete(x.right, key);
        }else {
            if(x.left == null) {
                return x.right;
            }
            if(x.right == null) {
                return x.left;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
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
        return size() == 0;
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
    }

    private Node deleteMin(Node x) {
        if(x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * 删除最大的键
     */
    @Override
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if(x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * 表中所有键的集合, 已排序
     *
     * @return
     */
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

    private void print(Node x) {
        if(x == null) return;
        print(x.left);
        System.out.println(x.key);
        print(x.right);
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

        public Node(K key, V val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public static void main(String[] args) {
        String[] a = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        BinarySearchTree<String, Integer> st = new BinarySearchTree<>();
        for (int i = 0; i < a.length; i++) {
            String key = a[i];
            st.put(key, i);
        }
        for(String s: st.keys()){
            System.out.println(s + ": " + st.get(s));
        }
    }
}
