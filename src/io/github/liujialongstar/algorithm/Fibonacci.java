package io.github.liujialongstar.algorithm;

/**
 * @author liujialong
 * @date 2021/6/28
 */
public class Fibonacci {
    /**
     * 递归实现
     * @param n
     * @return
     */
    public static long recursion(int n) {
        // 定义边界值
        if(n == 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        //当n > 1时递归调用
        return recursion(n-1) + recursion(n-2);
    }

    /**
     * 尾递归调用
     * @param n
     * @return
     */
    public static long tailRecursion(int n) {
        // 定义边界值
        if(n == 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        // 当n > 1时尾递归调用
        return tailRecursion(n, 0, 1);
    }

    /**
     * 尾递归调用
     * @param n
     * @param first
     * @param second
     * @return
     */
    public static long tailRecursion(int n, int first, int second) {
        if(n < 1) {
            return first;
        }
        int sum = first + second;
        return tailRecursion(n-1, second, sum);
    }
}
