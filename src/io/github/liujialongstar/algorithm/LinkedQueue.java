package io.github.liujialongstar.algorithm;


import java.util.Iterator;

/**
 * @author liujialong
 * @date 2021/6/23
 */
@SuppressWarnings("all")
public class LinkedQueue<T> implements Iterable<T> {
    /**
     * 指向最早添加的节点的链接
     */
    private Node first;

    /**
     * 指向最新添加的节点的链接
     */
    private Node last;

    /**
     * 队列的大小
     */
    private int n = 0;

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    /**
     * 向队列添加元素
     * 如果队列为空, 则首节点就是尾节点
     * @param item
     */
    public void enqueue(T item) {
        Node oldLast = last;
        last = new Node();
        last.t = item;
        last.next = null;
        if(isEmpty()) {
            first = last;
        }else {
            oldLast.next = last;
        }
        n++;
    }

    /**
     * 从队列删除元素
     * 如果删除元素后, 队列为空, 则将last节点置空
     * @return
     */
    public T dequeue() {
        if(isEmpty()) {
            // 或者抛出异常
            return null;
        }
        T item = first.t;
        first = first.next;
        n--;
        if(isEmpty()) {
            last = null;
        }
        return item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedQueueIterator();
    }

    private class LinkedQueueIterator implements Iterator<T> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T item = current.t;
            current = current.next;
            return item;
        }
    }

    private class Node {
        T t;
        Node next;
    }

    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        System.out.println("当前队列大小: " + queue.size());
        Iterator<String> it = queue.iterator();
        System.out.println("遍历队列开始:");
        while (it.hasNext()){
            System.out.println(it.next());
        }
        System.out.println("遍历队列结束.");
        System.out.println("弹出队列中元素:");
        while (queue.size() > 0){
            System.out.println(queue.dequeue());
        }
        System.out.println("弹出队列中元素结束.");
        System.out.println("当前队列是否为空: " + queue.isEmpty());
    }
}
