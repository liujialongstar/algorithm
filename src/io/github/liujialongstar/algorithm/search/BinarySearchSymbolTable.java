package io.github.liujialongstar.algorithm.search;

import io.github.liujialongstar.algorithm.LinkedQueue;

/**
 * @author liujialong
 * @date 2021/7/9
 * 二分查找符号表
 */
public class BinarySearchSymbolTable<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V>{
    private int N;

    private K[] keys;

    private V[] vals;

    public BinarySearchSymbolTable(int capacity) {
        int N = 0;
        keys = (K[]) new Comparable[capacity];
        vals = (V[]) new Object[capacity];
    }

    /**
     * 添加元素, 如果key存在, 替换key对应的val, 如果key不存在, 在rank(key)的位置添加元素(数组右移)
     * @param key
     * @param val
     */
    public void put(K key, V val) {
        if(val == null) {
            delete(key);
            return;
        }
        int k = rank(key);
        if(k < N && keys[k].compareTo(key) == 0){
            vals[k] = val;
            return;
        }

        if (N == keys.length) resize(2*keys.length);
        for(int i = N; i > k; i--) {
            keys[i] = keys[i-1];
            vals[i] = vals[i-1];
        }
        keys[k] = key;
        vals[k] = val;
        N++;

        assert check();
    }

    /**
     * 获取指定键的值, 如果不存在, 返回空
     * @param key
     * @return
     */
    public V get(K key){
        if(isEmpty()) {
            return null;
        }
        int k = rank(key);
        if(k < N && keys[k].compareTo(key) == 0){
            return vals[k];
        }
        return null;
    }

    /**
     * 删除元素, key不存在直接返回, key存在, 则key右边的元素左移
     * @param key
     */
    public void delete(K key) {
        if(isEmpty()) {
            return;
        }

        int k = rank(key);
        if(k == N || keys[k].compareTo(key) != 0) {
            return;
        }
        for (int i = k; i < N-1; i++) {
            keys[i] = keys[i+1];
            vals[i] = vals[i+1];
        }
        N--;
        keys[N] = null;
        vals[N] = null;
        if (N > 0 && N == keys.length/4) {
            resize(keys.length/2);
        }

        assert check();
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    public int size(K low, K high) {
        if(high.compareTo(low) < 0) {
            return 0;
        }else if (contains(high)) {
            return rank(high) - rank(low) + 1;
        }else {
            return rank(high) - rank(low);
        }
    }

    public K min() {
        if(isEmpty()) {
            return null;
        }
        return keys[0];
    }

    public K max() {
        if(isEmpty()) {
            return null;
        }
        return keys[N-1];
    }

    /**
     * 返回小于等于key的最大键
     * @param key
     * @return
     */
    public K floor(K key) {
        int k = rank(key);
        if(k < N && key.compareTo(keys[k]) == 0) {
            return keys[k];
        }
        if(k == 0) {
            return null;
        }else {
            return keys[k-1];
        }
    }

    /**
     * 大于等于key的最小键
     * @param key
     * @return
     */
    public K ceiling(K key) {
        int k = rank(key);
        if(k == N) {
            return null;
        }else {
            return keys[k];
        }
    }

    /**
     * 二分查找, 返回小于key的键的数量
     * @param key
     * @return
     */
    public int rank(K key){
        int low = 0;
        int high = N-1;
        return rank(key, low, high);
    }

    private int rank(K key, int low, int high) {
        if(high < low) {
            return low;
        }
        int mid = low + (high - low) / 2;
        int cmp = key.compareTo(keys[mid]);
        if(cmp < 0) {
            return rank(key, low, mid - 1);
        }else if(cmp > 0) {
            return rank(key, mid + 1, high);
        }else {
            return mid;
        }
    }

    public K select(int k) {
        if(k < 0 || k >= size()) {
            return null;
        }
        return keys[k];
    }

    public void deleteMin() {
        delete(min());
    }

    public void deleteMax() {
        delete(max());
    }

    public Iterable<K> keys(K low, K high) {
        LinkedQueue<K> queue = new LinkedQueue<>();
        if(low.compareTo(high) > 0) {
            return queue;
        }
        for (int i = rank(low); i < rank(high); i++) {
            queue.enqueue(keys[i]);
        }
        if(contains(high)){
            queue.enqueue(keys[rank(high)]);
        }
        return queue;
    }

    public Iterable<K> keys() {
        return keys(min(), max());
    }

    private void resize(int capacity) {
        assert capacity >= N;
        K[]   tempk = (K[])   new Comparable[capacity];
        V[] tempv = (V[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    /**
     * 检查符号表是否有序
     * @return
     */
    private boolean check() {
        return isSorted() && rankCheck();
    }

    /**
     * 校验keys是否有序(由小到大)
     * @return
     */
    private boolean isSorted() {
        for(int i = 1; i < size(); i++) {
            if(keys[i].compareTo(keys[i-1]) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验vals是否有序
     * @return
     */
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++) {
            if(i != rank(select(i))) {
                return false;
            }
        }
        for (int i = 0; i < size(); i++) {
            if(keys[i].compareTo(select(rank(keys[i]))) != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        BinarySearchSymbolTable<String, Integer> st = new BinarySearchSymbolTable<>(8);
        for (int i = 0; i < a.length; i++) {
            String key = a[i];
            st.put(key, i);
        }
        for(String s: st.keys()){
            System.out.println(s + ": " + st.get(s));
        }
    }
}
