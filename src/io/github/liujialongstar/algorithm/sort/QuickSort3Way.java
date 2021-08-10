package io.github.liujialongstar.algorithm.sort;

/**
 * @author liujialong
 * @date 2021/7/1
 */
@SuppressWarnings("all")
public class QuickSort3Way {
    public static void sort(Comparable[] a){
        // 排序前可以将数组随机排列
        sort(a, 0, a.length-1);
    }

    /**
     * 递归调用排序算法
     * @param a
     * @param low
     * @param high
     */
    private static void sort(Comparable[] a, int low, int high){
        if(high <= low) {
            return;
        }

        Comparable v = a[low];
        // 边界值很重要, 一开始low要和它的右边第一个元素比较, 所以i是low+1
        int i = low + 1;
        int lt = low;
        int gt = high;

        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if(cmp < 0) {
                exchange(a, lt++, i++);
            }else if (cmp > 0){
                exchange(a, i, gt--);
            }else {
                i++;
            }
        }

        // 对a[j]左侧排序(递归调用)
        sort(a, low, lt-1);
        // 对a[j]右侧排序(递归调用)
        sort(a, gt+1, high);
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
