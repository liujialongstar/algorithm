package io.github.liujialongstar.algorithm.unionfind;

/**
 * @author liujialong
 * @date 2021/6/28
 */
@SuppressWarnings("all")
public class QuickUnionUF implements UF{
    /**
     * 以触点为索引的数组
     */
    private int[] id;

    /**
     * 分量数量, 初始为N, 每次执行union()操作, 减一
     */
    private int count;

    /**
     * 初始化, 设置数组值等于数组下标
     * @param N
     */
    public QuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        count = N;
    }

    /**
     * 将p的根节点的父节点设置为q的根节点
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot){
            return;
        }
        id[pRoot] = qRoot;
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
     * 查找指定节点的根节点
     * @param i
     * @return
     */
    public int find(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
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

}
