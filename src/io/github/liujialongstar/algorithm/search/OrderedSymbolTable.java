package io.github.liujialongstar.algorithm.search;

/**
 * @author liujialong
 * @date 2021/7/8
 */
public interface OrderedSymbolTable<K extends Comparable<K>, V>{
    /**
     * 将键值对存放再表中(若值为空则将键key从表中删除)
     * @param key
     * @param val
     */
    void put(K key, V val);

    /**
     * 获取键key对应的值(若键key不存在则返回null)
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 从表中删除键key及其对应的值
     * @param key
     */
    void delete(K key);

    /**
     * 键key再表中是否存在对应的值
     * @param key
     * @return
     */
    boolean contains(K key);

    /**
     * 表是否为空
     * @return
     */
    boolean isEmpty();

    /**
     * 表中键值对的数量
     * @return
     */
    int size();

    /**
     * 最小的键
     * @return
     */
    K min() ;

    /**
     * 最大的键
     * @return
     */
    K max();

    /**
     * 小于等于key的最大键
     * @param key
     * @return
     */
    K floor(K key);

    /**
     * 大于等于key的最小键
     * @param key
     * @return
     */
    K ceiling(K key);

    /**
     * 小于key的键的数量
     * @param key
     * @return
     */
    int rank(K key);

    /**
     * 排名为k的键
     * @param k
     * @return
     */
    K select(int k);

    /**
     * 删除最小的键
     */
    void deleteMin();

    /**
     * 删除最大的键
     */
    void deleteMax();

    /**
     * [low..high]之间所有键的集合, 已排序
     * @param low
     * @param high
     * @return
     */
    Iterable<K> keys(K low, K high);

    /**
     * 表中所有键的集合, 已排序
     * @return
     */
    Iterable<K> keys();
}
