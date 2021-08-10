package io.github.liujialongstar.algorithm;

import java.util.Iterator;

/**
 * @author liujialong
 * @date 2021/6/22
 */
@SuppressWarnings("all")
public class ArrayStack<T> implements Iterable<T> {
    /**
     * 栈元素
     */
    private T[] t = (T[]) new Object[1];

    /**
     * 栈元素数量
     */
    private int n = 0;

    /**
     * 栈是否为空, 当栈中元素数量为0时, 栈为空
     * @return
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * 获取栈的大小, 即栈中元素数量
     * @return
     */
    public int size() {
        return n;
    }

    /**
     * 动态扩缩容
     * 新建临时数组, 将原数组元素按顺序赋值给临时数组, 最后将数组的引用指向临时数组
     * @param max
     */
    private void resize(int max) {
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < n; i++) {
            temp[i] = t[i];
        }
        t = temp;
    }

    /**
     * 入栈: 向栈中添加元素
     * 1. 检查栈的大小, 如果栈的大小等于栈元素长度, 则扩容至2倍
     * 2. 向数组添加一个元素
     * 3. 栈大小+1
     * @param item
     */
    public void push(T item) {
        if (n == t.length) {
            resize(t.length * 2);
        }
        t[n++] = item;
    }

    /**
     * 出栈: 弹出最后入栈的元素
     * 1. 将数组最后一个元素赋值给变量, 以便最后返回, 同时栈大小-1
     * 2. 将数组最后一个元素置空, 以便垃圾回收
     * 3. 检查栈大小, 如果栈大小为数组长度的1/4, 则缩容至原数组长度的1/2
     * 4. 返回被弹出栈的元素
     * @return
     */
    public T pop() {
        T item = t[--n];
        t[n] = null;
        if(n > 0 && n == t.length/4) {
            resize(t.length/2);
        }
        return item;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<T> {

        private int i = n;
        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public T next() {
            return t[--i];
        }
    }

    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>();
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
