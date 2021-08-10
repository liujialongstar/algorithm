package io.github.liujialongstar.algorithm.search;


import java.util.Iterator;

/**
 * @author liujialong
 * @date 2021/7/9
 * 无序链表实现的顺序查找的符号表
 */
public class SequentialSearchSymbolTable<K, V> implements SymbolTable<K, V> {

    private int N = 0;

    private Node first;

    public SequentialSearchSymbolTable() {

    }

    public void put(K key, V val) {
        // 如果val为空, 则调用delete()
        if (val == null) {
            delete(key);
            return;
        }

        // 顺序查找, 如果命中, 则更新值
        for(Node current = first; current != null; current = current.next) {
            if(key.equals(current.key)){
                current.value = val;
            }
        }
        // 未命中, 则在头部创建新的节点
        first = new Node(key, val, first);
        N++;
    }

    public V get(K key){
        for(Node current = first; current != null; current = current.next) {
            if(key.equals(current.key)){
                return current.value;
            }
        }
        return null;
    }

    public void delete(K key) {
        if(isEmpty()) {
            return;
        }
        if(key.equals(first.key)){
            first = first.next;
            N--;
            return;
        }
        for (Node pre = first, current = first.next; current != null; pre = pre.next, current = current.next) {
            if(key.equals(current.key)){
                pre.next = current.next;
                N--;
                return;
            }
        }
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    public Iterable<K> keys() {
        return new KeySet();
    }

    private class KeySet implements Iterable<K> {
        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>() {
                private Node current = first;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public K next() {
                    K item = current.key;
                    current = current.next;
                    return item;
                }
            };
        }
    }

    private class Node {
        K key;
        V value;
        Node next;

        public Node(K key, V value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        String[] a = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        SequentialSearchSymbolTable<String, Integer> st = new SequentialSearchSymbolTable<>();
        for (int i = 0; i < a.length; i++) {
            String key = a[i];
            st.put(key, i);
        }
        for(String s: st.keys()){
            System.out.println(s + ": " + st.get(s));
            st.delete(s);
        }

        System.out.println(st.get("q"));
        System.out.println(st.size());

        st.put("q",100);
        System.out.println(st.get("q"));
    }
}
