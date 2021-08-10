package io.github.liujialongstar.algorithm.unionfind;

/**
 * @author liujialong
 * @date 2021/6/28
 */
@SuppressWarnings("all")
public interface UF {
    /**
     * 在p和q之间添加一条连接
     * @param p
     * @param q
     */
    void union(int p, int q);

    /**
     * p所在的分量的标识符(0到N-1)
     * @param p
     * @return
     */
    int find(int p);

    /**
     * 如果p和q存在于同一个分量中则返回true
     * @param p
     * @param q
     * @return
     */
    boolean isConnected(int p, int q);

    /**
     * 连通分量的数量
     * @return
     */
    int count();
}
