package io.github.liujialongstar.algorithm.sort;

import io.github.liujialongstar.algorithm.LinkedStack;

/**
 * @author liujialong
 * @date 2021/7/6
 */
@SuppressWarnings("all")
public class HeapMinPriorityQueue<T extends Comparable<T>> {
    private int N;

    private T[] pq;

    public HeapMinPriorityQueue() {
        pq = (T[]) new Comparable[1];
        N = 0;
    }

    /**
     * 因为位置0不适用, 创建N+1的容量
     * @param capacity
     */
    public HeapMinPriorityQueue(int capacity) {
        pq = (T[]) new Comparable[capacity+1];
        N = 0;
    }

    public HeapMinPriorityQueue(T[] pq) {
        this.pq = pq;
        N = pq.length;
    }

    /**
     * 插入元素(位置0不使用)
     * @param v
     */
    public void insert(T v) {
        pq[++N] = v;
        swim(N);
    }

    /**
     * 删除最小元素
     * @return
     */
    public T delMin() {
        T item = pq[1];
        exchange(1, N--);
        pq[N+1] = null;
        sink(1);
        return item;
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    public int size() {
        return N;
    }

    /**
     * 上浮操作, 当节点k元素小于于父节点k/2元素时, 交换两个节点的元素, 直到遇到更小的父节点, 或者k是根节点
     *@param k
     */
    private void swim(int k){
        while (k > 1 && less(k, k/2)) {
            exchange(k, k/2);
            k = k/2;
        }
    }

    /**
     * 下沉操作, 交换k节点和它的子节点2k与2k+1中的较小一个, 直到k小于所有子节点, 或者到达堆底
     * @param k
     */
    private void sink(int k) {
        while (k*2 <= N) {
            int j = k*2;
            // 如果存在两个子节点, 比较两个子节点, 得到较小的一个
            if(j < N && less(j+1, j)) {
                j++;
            }
            // 如果k小于子节点中较小的一个, 跳出循环
            if(less(k, j)){
                break;
            }
            // 否则交换节点
            exchange(k, j);
            k = j;
        }
    }
    /**
     * 比较
     * @param i
     * @param j
     * @return
     */
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    /**
     * 交换
     * @param i
     * @param j
     */
    private void exchange(int i, int j) {
        T t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public static void main(String[] args) {
        int M = 3;
        String[] a = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        int N = a.length;
        HeapMinPriorityQueue<String> pq = new HeapMinPriorityQueue<>(M+1);
        for (int i = 0; i < N; i++) {
            pq.insert(a[i]);
            if(pq.N > M) {
                pq.delMin();
            }
        }
        LinkedStack<String> stack = new LinkedStack<>();
        while (!pq.isEmpty()) {
            stack.push(pq.delMin());
        }
        for(String s: stack) {
            System.out.println(s);
        }
    }
}