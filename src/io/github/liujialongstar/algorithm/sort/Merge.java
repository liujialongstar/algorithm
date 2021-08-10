package io.github.liujialongstar.algorithm.sort;

/**
 * @author liujialong
 * @date 2021/6/30
 */
@SuppressWarnings("all")
public class Merge {
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
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    /**
     * 将要排序的数组通过递归拆分并归并
     * @param a
     * @param low
     * @param high
     */
    private static void sort(Comparable[] a, int low, int high) {
        // 设置递归的基准
        if(high <= low) {
            return;
        }

        int mid = low + (high - low) / 2;
        // 将左半边排序
        sort(a, low, mid);
        // 将右半边排序
        sort(a, mid + 1, high);
        // 归并结果
        merge(a, low, mid, high);
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
