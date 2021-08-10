package io.github.liujialongstar.algorithm.unionfind;

/**
 * @author liujialong
 * @date 2021/6/28
 */
@SuppressWarnings("all")
public class WeightedQuickUnionUF implements UF{
    /**
     * 节点数组
     */
    private int[] id;

    /**
     * 各个根节点所对应的分量的大小
     */
    private int[] sz;

    private int count;

    /**
     * 初始化, 设置数组值等于数组下标
     * @param N
     */
    public WeightedQuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            sz[i] = 1;
        }
        count = N;
    }

    /**
     * 比较两个节点根节点下的节点数量
     * 将数量少的根节点挂载到数量多的根节点下
     * 数量多的根节点下的节点数量增加
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if(sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[pRoot] += sz[qRoot];
        }else {
            id[qRoot] = pRoot;
            sz[qRoot] += sz[pRoot];
        }
        count--;
    }

    /**
     * 比较两个节点的根节点是否相同
     * @param p
     * @param q
     * @return
     */
    public boolean isConnected(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        return pRoot == qRoot;
    }

    /**
     * 连通分量的数量
     *
     * @return
     */
    @Override
    public int count() {
        return count;
    }

    /**
     * 查找指定节点的根节点
     * 如果id[i]的值不指向自身
     * 则使id[i]指向父节点的父节点(展平/路径压缩)
     * i的值为父节点的值
     * 依次遍历各个节点
     * @param i
     * @return
     */
    public int find(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}
