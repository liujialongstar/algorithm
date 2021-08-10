package io.github.liujialongstar.algorithm;

import java.util.Iterator;

/**
 * @author liujialong
 * @date 2021/6/24
 */
@SuppressWarnings("all")
public class Deque<T> implements Iterable<T> {

    /**
     * 首节点
     */
    private DoubleNode left;

    /**
     * 尾节点
     */
    private DoubleNode right;

    private int n = 0;

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * 从头部插入元素
     */
    public void pushLeft(T item) {
        DoubleNode oldLeft = left;
        left = new DoubleNode();
        left.t = item;

        if(isEmpty()) {
            right = left;
        }else {
            left.next = oldLeft;
            oldLeft.forward = left;
        }
        n++;
    }

    /**
     * 从头部删除元素
     * @return
     */
    public T popLeft() {
        if(isEmpty()) {
            return null;
        }
        T item = left.t;
        left = left.next;
        n--;
        if(isEmpty()){
            right = null;
        }else {
            left.forward = null;
        }
        return item;
    }

    /**
     * 从尾部插入元素
     * @param item
     */
    public void pushRight(T item) {
        DoubleNode oldRight = right;
        right = new DoubleNode();
        right.t = item;
        if(isEmpty()) {
            left = right;
        }else {
            right.forward = oldRight;
            oldRight.next = right;
        }
        n++;
    }

    /**
     * 从尾部删除元素
     * @return
     */
    public T popRight() {
        if(isEmpty()) {
            return null;
        }
        T item = right.t;
        right = right.forward;
        n--;
        if(isEmpty()){
            left = null;
        }else {
            right.next = null;
        }
        return item;
    }

    // 下面的三个方法将破坏双向队列的封装性
    /**
     * 在指定位置之前插入元素
     * @param index
     * @param item
     */
    public void insertBefore(int index, T item) {
        DoubleNode node = find(index);
        if(node == null) {
            return;
        }else if(node.forward == null) {
            pushLeft(item);
        }else {
            DoubleNode forwordNode = node.forward;
            DoubleNode newNode = new DoubleNode();
            newNode.t = item;
            forwordNode.next = newNode;
            newNode.forward = forwordNode;
            newNode.next = node;
            node.forward = newNode;
            n++;
        }

    }

    /**
     * 在指定节点之后插入元素
     * @param index
     * @param item
     */
    public void insertAfter(int index, T item) {
        DoubleNode node = find(index);

        if(node == null) {
            return;
        }else if(node.next == null){
            pushRight(item);
        }else {
            DoubleNode nextNode = node.next;
            DoubleNode newNode = new DoubleNode();
            newNode.t = item;
            node.next = newNode;
            newNode.forward = node;
            newNode.next = nextNode;
            nextNode.forward = newNode;
            n++;
        }
    }

    /**
     * 移除指定位置的节点, 返回被移除元素
     * @param index
     * @return
     */
    public T remove(int index) {
        DoubleNode node = find(index);
        T item;
        if(node == null){
            return null;
        }else if(node.forward == null){
            item = popLeft();
        }else if(node.next == null) {
            item = popRight();
        }else {
            item = node.t;
            DoubleNode forwardNode = node.forward;
            DoubleNode nextNode = node.next;
            forwardNode.next = nextNode;
            nextNode.forward = forwardNode;
            node.forward = null;
            node.next = null;
            n--;
        }
        return item;
    }

    /**
     * 查找指定位置的节点
     * @param index
     * @return
     */
    private DoubleNode find(int index){
        if(index < 0 || index > n) {
            return null;
        }

        DoubleNode targetNode;
        if (index > n/2){
            targetNode = right;
            for(int i = n; i != index; i --){
                targetNode = targetNode.forward;
            }
        }else {
            targetNode = left;
            for(int i = 1; i != index; i++){
                targetNode = targetNode.next;
            }
        }
        return targetNode;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator();
    }

    public Iterator<T> reverseIterator() {
        return new DoubleLinkedListReverseIterator();
    }

    public class DoubleLinkedListIterator implements Iterator<T> {

        DoubleNode current = left;

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

    public class DoubleLinkedListReverseIterator implements Iterator<T> {

        DoubleNode current = right;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T item = current.t;
            current = current.forward;
            return item;
        }
    }

    private class DoubleNode {
        /**
         * 双向链表中的元素
         */
        private T t;

        /**
         * 指向前一个节点的链接
         */
        private DoubleNode forward;

        /**
         * 指向后一个节点的链接
         */
        private DoubleNode next;
    }

    public static void main(String[] args) {
        Deque<String> list = new Deque<>();

        // 从头部方向添加元素
        list.pushLeft("a");
        list.pushLeft("b");
        list.pushLeft("c");
        list.pushLeft("d");

        System.out.println("正向遍历开始:");
        // 正向遍历
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("正向遍历结束.");
        System.out.println("反向遍历开始:");
        //反向遍历
        Iterator<String> rIt = list.reverseIterator();
        while (rIt.hasNext()){
            System.out.println(rIt.next());
        }
        System.out.println("反向遍历结束.");
        System.out.println("从尾部删除元素开始:");
        while (!list.isEmpty()) {
            System.out.println(list.popRight());
        }
        System.out.println("从尾部删除元素结束");

        // 从尾部添加元素
        list.pushRight("a");
        list.pushRight("b");
        list.pushRight("c");
        list.pushRight("d");

        System.out.println("从头部删除元素开始:");
        while (!list.isEmpty()) {
            System.out.println(list.popLeft());
        }
        System.out.println("从头部删除元素结束");
    }
}

