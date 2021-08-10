package io.github.liujialongstar.algorithm.sort;

import io.github.liujialongstar.algorithm.LinkedStack;

/**
 * @author liujialong
 * @date 2021/7/6
 */
@SuppressWarnings("all")
public class OrderedMinPriorityQueue<T extends Comparable<T>> {
    private int size;

    private T[] t;

    public OrderedMinPriorityQueue() {
        t = (T[]) new Comparable[1];
        size = 0;
    }

    public OrderedMinPriorityQueue(int capacity) {
        t = (T[]) new Comparable[capacity];
        size = 0;
    }

    public OrderedMinPriorityQueue(T[] t) {
        this.t = t;
        size = t.length;
    }

    /**
     * 插入元素
     * @param v
     */
    public void insert(T v) {
        if (size == t.length) {
            resize(t.length * 2);
        }
        int i = size -1;
        while (i >= 0 && less(t[i], v)){
            t[i+1] = t[i];
            i--;
        }
        t[i+1] = v;
        size++;
    }

    public T min() {
        return t[size-1];
    }

    /**
     * 类似于栈的pop()方法, 删除边界元素
     * @return
     */
    public T delMin() {
        T item = t[--size];
        t[size] = null;
        if(size > 0 && size == t.length/4) {
            resize(t.length/2);
        }
        return item;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * 动态扩缩容
     * 新建临时数组, 将原数组元素按顺序赋值给临时数组, 最后将数组的引用指向临时数组
     * @param max
     */
    private void resize(int max) {
        T[] temp = (T[]) new Comparable[max];
        for (int i = 0; i < size; i++) {
            temp[i] = t[i];
        }
        t = temp;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int M = 3;
        String[] a = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        int N = a.length;
        OrderedMinPriorityQueue<String> pq = new OrderedMinPriorityQueue<>(M+1);
        for (int i = 0; i < N; i++) {
            pq.insert(a[i]);
            if(pq.size > M) {
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