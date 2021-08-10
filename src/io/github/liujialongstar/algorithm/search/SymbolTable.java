package io.github.liujialongstar.algorithm.search;


/**
 * @author liujialong
 * @date 2021/7/8
 */
public interface SymbolTable<K, V>{
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
     * 表中所有键的集合
     * @return
     */
    Iterable<K> keys();
}
