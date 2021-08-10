package io.github.liujialongstar.algorithm;

import java.util.Iterator;

/**
 * @author liujialong
 * @date 2021/6/23
 */
@SuppressWarnings("all")
public class LinkedStack<T> implements Iterable<T> {
    /**
     * 栈顶元素(最近添加的元素)
     */
    private Node first;

    /**
     * 栈元素数量
     */
    private int n = 0;

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    /**
     * 入栈
     * @param item
     */
    public void push(T item) {
        Node oldFirst = first;
        first = new Node();
        first.t = item;
        first.next = oldFirst;
        n++;
    }

    /**
     * 出栈
     * @return
     */
    public T pop() {
        T temp = first.t;
        first = first.next;
        n--;
        return temp;
    }


    @Override
    public Iterator<T> iterator() {
        return new LinkedStackIterator();
    }

    private class LinkedStackIterator implements Iterator<T> {
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
        LinkedStack<String> stack = new LinkedStack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        System.out.println("当前栈大小: " + stack.size());
        Iterator<String> it = stack.iterator();
        System.out.println("遍历栈开始:");
        while (it.hasNext()){
            System.out.println(it.next());
        }
        System.out.println("遍历栈结束.");
        System.out.println("弹出栈中元素:");
        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
        System.out.println("弹出栈中元素结束.");
        System.out.println("当前栈是否为空: " + stack.isEmpty());
    }
}
