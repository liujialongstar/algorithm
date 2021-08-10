package io.github.liujialongstar.algorithm.sort;

/**
 * @author liujialong
 * @date 2021/6/29
 */
@SuppressWarnings("all")
public class Selection {

    /**
     * 排序
     * @param a
     */
    public static void sort(Comparable[] a){
        int N = a.length;
        // 遍历数组
        for (int i = 0; i < N; i++) {
            // 设定当前元素为最小的元素
            int min = i;
            // 遍历其他元素
            for (int j = i+1; j < N; j++) {
                // 如果其他元素存在比最小元素还小的元素, 将这个元素设为最小元素, 并继续比较, 最终找到最小元素
                if(less(a[j], a[min])){
                    min = j;
                }
            }
            // 在外层循环交换当前元素与最小元素
            exchange(a, i, min);
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