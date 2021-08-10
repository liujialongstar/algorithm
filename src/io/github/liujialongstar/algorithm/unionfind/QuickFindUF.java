package io.github.liujialongstar.algorithm.unionfind;

/**
 * @author liujialong
 * @date 2021/6/28
 */
@SuppressWarnings("all")
public class QuickFindUF implements UF{
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
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        count = N;
    }

    /**
     * 将p和q对并到相同的分量中
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        int pid  = find(p);
        int qid = find(q);
        //如果p和q已经在相同的分量中, 则不需要采取任何行动
        if(pid == qid) {
            return;
        }
        //将p的分量重命名为q的名称
        for (int i = 0; i < id.length; i++) {
            if(id[i] == pid) {
                id[i] = qid;
            }
        }
        count--;
    }

    /**
     * p所在的分量的标识符(0到N-1)
     *
     * @param p
     * @return
     */
    @Override
    public int find(int p) {
        return id[p];
    }

    /**
     * 快速查找
     * @param p
     * @param q
     * @return
     */
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
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
