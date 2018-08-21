package zzwalgs4.字符串;

/**
 * author: zzw5005
 * date: 2018/8/19 11:05
 */

/*
* 低位优先的字符串排序
* 核心思想: 数组中每个字符串的长为w，则从右向左以为每个位置的字符串作为键，用键索引计数法将字符串排序w遍。
* 第一次是以字符串的最后一位为键排序，第二次是以字符串的倒数第二位为键排序，一直到以字符串的第一位为键，进行排序。
* */
public class LSD {
    private static final int BITS_PER_BYTE = 8;

    private LSD(){}

    /**
     * 给定字符串数组和每个字符串的长度，返回排序好的字符串数组。
     * 简单来说就是按升序重新排列W长度的字符串数组
     * @param a 需要排序的数组
     * @param w 每个字符串的字符数(LSD适用于每个字符串的长度都相同的字符串数组排序)
     */
    public static void sort(String[] a, int w) {
        int n = a.length;
        int R = 256;
        String[] aux = new String[n];

        for (int d = w - 1; d >= 0; d--) {
            //下面四个for循环组合起来就是键索引计数法

            //count[0]始终为0，不存放数据
            int[] count = new int[R + 1];
            //计算每个字符出现的频率
            for (int i = 0; i < n; i++) {
                count[a[i].charAt(d) + 1]++;
            }

            //将频率转化成索引
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            //将元素进行分类
            for (int i = 0; i < n; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            //回写数据，将排序好的数据写回a[i]中
            for (int i = 0; i < n; i++) {
                a[i] = aux[i];
            }
        }
    }

    /**
     * 按升序重新排序32位整数数目,不需要数字是固定长度，但是好像如果第一位是0，会出现问题。
     * 这笔Array.sort()快2-3倍
     * @param a
     */
    public static void sort(int[] a){
        final int BITS = 32;                     //每一个整数是32bit
        final int R = 1 << BITS_PER_BYTE;        //每一个bit都是在0到255之间，左移一位相当于乘于2，为16位
        final int MASK = R-1;                    //0xFF  MASK=15 0 1111
        final int w = BITS / BITS_PER_BYTE;      //每一个整数都是4bit

        int n = a.length;
        int[] aux = new int[n];

        for(int d= 0; d < w; d++){

            //计算每个数字出现的频率
            int[] count = new int[R+1];
            for(int i=0; i < n; i++){
                int c = (a[i] >> BITS_PER_BYTE * d) & MASK;
                count[c+1]++;
            }

            //将频率转换位索引
            for(int r=0; r < R; r++){
                count[r+1] += count[r];
            }

            //对于最高位字节，0x80-0xFF位于0x00-0x7F之前
            if(d == w-1){
                int shift1 = count[R] - count[R/2];
                int shift2 = count[R/2];
                for(int r = 0; r < R/2; r++){
                    count[r] += shift1;
                }

                for(int r= R/2; r < R; r++){
                    count[r] -= shift2;
                }
            }

            //将数据进行分类
            for(int i = 0; i < n; i++){
                int c = (a[i] >> BITS_PER_BYTE * d) & MASK;
                aux[count[c]++] = a[i];
            }

            //将数据写回原来的数组
            for(int i=0; i < n; i++){
                a[i] = aux[i];
            }
        }

    }

    public static void main(String[] args) {
        String[] a = {"abcd","abdc" ,"abce","bacd","cabd","dabc","cdab","dcab","cadb"};

        sort(a, 4);

        for(String s : a){
            System.out.println(s);
        }
        System.out.println("\n");

        int[] array = {12345, 54321, 98701, 83019, 90472, 74621, 19375, 23847};

        sort(array);

        for(int date : array){
            System.out.println(date);
        }
    }
}
















