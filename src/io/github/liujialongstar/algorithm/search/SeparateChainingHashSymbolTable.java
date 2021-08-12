package io.github.liujialongstar.algorithm.search;

/**
 * @author liujialong
 * @date 2021/8/12
 * 拉链法实现符号表 为M个元素分别创建符号表
 */
public class SeparateChainingHashSymbolTable<K, V> {
    /**
     * 键值对总数
     */
    private int N;

    /**
     * 散列表大小
     */
    private int M;

    /**
     * 存放链表对象的数组
     */
    private SequentialSearchSymbolTable<K, V>[] st;

    public SeparateChainingHashSymbolTable() {
        this(997);
    }

    /**
     * 创建M条链表
     * @param M
     */
    public SeparateChainingHashSymbolTable(int M) {
        this.M = M;
        st = (SequentialSearchSymbolTable<K, V>[]) new SequentialSearchSymbolTable[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchSymbolTable<>();
        }
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) / M;
    }

    public V get(K key) {
        return (V) st[hash(key)].get(key);
    }

    public void put(K key, V val) {
        st[hash(key)].put(key, val);
    }
}
