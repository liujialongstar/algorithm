package io.github.liujialongstar.algorithm.sort;

/**
 * @author liujialong
 * @date 2021/6/30
 */
@SuppressWarnings("all")
public class MergeBU {
    /**
     * 归并所需的辅助数组
     */
    private static Comparable[] aux;

    /**
     * 将a[low..mid]和a[mid+1..hi]归并
     * @param a
     * @param low
     * @param mid
     * @param high
     */
    public static void merge(Comparable[] a, int low, int mid, int high) {
        int i = low;
        int j = mid + 1;

        // 将a[low..high]赋值到aux[low..high]
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        // 将aux归并到a中
        for (int k = low; k <= high; k++) {
            if(i > mid) {
                // 如果左边用尽, 只取右边元素
                a[k] = aux[j++];
            }else if(j > high) {
                //如果右边用尽, 只取左边元素
                a[k] = aux[i++];
            }else if(less(aux[j], aux[i])) {
                // 比较左右两边的元素, 将较小的元素赋值给a[]
                a[k] = aux[j++];
            }else {
                a[k] = aux[i++];
            }
        }
    }

    /**
     * 排序
     * @param a
     */
    public static void sort(Comparable[] a){
        int N = a.length;
        aux = new Comparable[N];
        // 从大小为1的数组开始进行归并, 然后数组大小翻倍
        for (int sz = 1; sz < N; sz = sz+sz) {
            // 从第0个元素开始, 按双倍sz大小步进, 进行归并
            for(int low = 0; low < N-sz; low += sz+sz) {
                // 中值为归并的起始位置加上步进大小-1, 终值为起始位置加上双倍的步进大小-1与数组长度-1的最小值
                merge(a, low, low+sz-1, Math.min(low+sz+sz-1, N-1));
            }
        }
    }

    /**
     * 如果v小于w, 返回true
     * @param v
     * @param w
     * @return
     */
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * 交换素组中i元素和j元素的位置
     * @param a
     * @param i
     * @param j
     */
    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 打印数组元素
     * @param a
     */
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    /**
     * 判定数组是否已经排序
     * @param a
     * @return
     */
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if(less(a[i], a[i-1])){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
