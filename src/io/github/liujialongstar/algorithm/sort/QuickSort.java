package io.github.liujialongstar.algorithm.sort;

/**
 * @author liujialong
 * @date 2021/7/1
 */
@SuppressWarnings("all")
public class QuickSort {
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
        // 将数组切分成左侧元素均小于等于a[j], 右侧元素均大于等于a[j]的两个数组
        int j = partition(a, low, high);
        // 对a[j]左侧排序(递归调用)
        sort(a, low, j-1);
        // 对a[j]右侧排序(递归调用)
        sort(a, j+1, high);
    }

    /**
     * 将数组切分成左侧元素均小于等于a[j], 右侧元素均大于等于a[j]的两个数组, 并返回j
     * @param a
     * @param low
     * @param high
     * @return
     */
    private static int partition(Comparable[] a, int low, int high) {
        int i = low;
        int j = high + 1;
        // 取出数组第一个元素v
        Comparable v = a[low];
        while (true) {
            // 从左侧第二个元素开始, 依次和v比较, 找到第一个大于或等于v的元素
            while (less(a[++i], v)){
                if(i == high) {
                    break;
                }
            }
            // 从右侧第一个元素开始, 依次和v比较, 找到第一个小于或等于v的元素
            while (less(v, a[--j])){
                if(j == low) {
                    break;
                }
            }
            // i和j相遇, 主动退出循环
            if(i >= j) {
                break;
            }
            // 原本a[i]>=v>=a[j], 交换后a[i]<=v<=a[j]
            exchange(a, i, j);
        }
        // 指针i和j相遇时, v>=a[j], 交换v和a[j], 此时, a[j]左侧元素全部小于等于a[j], a[j]右侧元素全部大于等于a[j]
        exchange(a, low, j);
        return j;
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
