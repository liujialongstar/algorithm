package io.github.liujialongstar.algorithm.search;

/**
 * @author liujialong
 * @date 2021/8/12
 * 基于线性探测法实现的符号表
 */
public class LinearProbingHashSymbolTable<K, V> {
    /**
     * 符号表中键值对总数
     */
    private int N;

    /**
     * 线性探测表的大小
     */
    private int M = 16;

    private K[] keys;

    private V[] vals;

    public LinearProbingHashSymbolTable() {
        keys = (K[]) new Object[M];
        vals = (V[]) new Object[M];
    }

    public LinearProbingHashSymbolTable(int cap) {
        this.M = cap;
        keys = (K[]) new Object[M];
        vals = (V[]) new Object[M];
    }

    private int hash(K key) {
        return (key.hashCode() & 0xfffffff) %M;
    }

    private void resize(int cap) {
        LinearProbingHashSymbolTable<K, V> t = new LinearProbingHashSymbolTable<>(cap);
        for (int i = 0; i < M; i++) {
            if(keys[i] != null){
                t.put(keys[i], vals[i]);
            }
        }
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    public void put(K key, V val) {
        if(N >= M/2) {
            resize(2 * M);
        }
        int i;
        for(i = hash(key); keys[i] != null; i = (i+1) % M){
            if(keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public V get(K key) {
        for(int i = hash(key); keys[i] != null; i = (i+1) % M){
            if(keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    public void delete(K key) {
        if(!contains(key)) {
            return;
        }
        int i = hash(key);
        while(!key.equals(keys[i])){
            i = (i + 1) % M;
        }
        keys[i] = null;
        vals[i] = null;

        //重新插入空位之后的键
        i = (i + 1) % M;
        while (keys[i] != null) {
            K keyToRedo = keys[i];
            V valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            //删除当前元素, 减1, 下面put()会加回来
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        //删除key之后, 数组元素减1
        N--;
        if(N > 0 && N == M/8) {
            resize(M/2);
        }
    }

    public boolean contains(K key) {
        return get(key) != null;
    }
}
