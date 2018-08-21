package zzwalgs4.字符串;

/**
 * author: zzw5005
 * date: 2018/8/19 15:30
 */

/*
* MSD: 高位优先的字符串排序(一个通用字符串排序算法，字符串长度不一样相同)
* 使用MSD基数排序的扩展ASCII字符串或整数数组
* 核心思想: 首先使用键索引技术法将所有字符串的按照首字母排序，然后递归地再将所有字符串的首字母
* 所对应的子数组进行排序(递归排序的过程不包括首字母，因为每一类中的所有字符串的首字母都是相同的)
* 类似于快排，高位优先的字符串排序会将数组切分为能够独立排序的子数组来完成排序任务
* 小型子数组对于高为优先的字符串排序性能至关重要
* 时间复杂度: 要将基于大小为R的字母的N个字符串排序，高为优先的字符串排序算法平均需要检查Nlog_N (N)
* 不适用场合: 等值键，有较长公共前缀的键，取值范围较小的键和小数组
* 使用场景:随机字符串
* */
public class MSD {

    private static int R = 256;                 //基数，扩展的ASCII字符大小
    private static final int CUTOFF = 15;       //小数组的切换阈值
    private static final int BITS_PER_BYTE = 8;
    private static final int BITS_PER_INT = 32; //java中Int类型都是32位的

    private MSD(){}

    /**
     * 返回字符串的第d个字符，如果d等于字符串的长度，则返回-1
     * 这里其实进行了转换，s.charAt(d)返回类型是一个char类型
     * 这里强制转换成了int类型，转换成了对应的ASCII值
     * 这里是将字符串中字符索引转化位数组索引，当指定的位置超过了字符串的末尾时候，该方法返回-1
     * 然后在使用这个方法的返回值的时候，将所有返回值+1，得到一个非负的int值并用它作为count[]的索引
     * @param s
     * @param d
     * @return
     */
    private static int charAt(String s, int d){
        if(d >=0 && d < s.length())
            return s.charAt(d);
        else
            return -1;
    }

    /**
     * 按升序重新排列扩展ASCII字符串数组
     * @param a
     */
    public static void sort(String[] a){
        int n = a.length;
        String[] aux = new String[n];           //数据分类的辅助数组
        sort(a, 0, n-1, 0, aux);
    }

    /**
     * 从a[low]到a[high]排序，从第d个字符开始
     * @param a
     * @param low
     * @param high
     * @param d
     * @param aux
     */
    private static void sort(String[] a, int low, int high, int d, String[] aux){
        //以第d个字符位键将a[low]到a[high]排序
        //如果满足hig <= low + CUTOFF，这个条件，就切换到小数组排序，小数组排序这里使用插入排序
        //这里是为了提高性能
        if(high <= low + CUTOFF){
            insertion(a, low, high, d);
            return;
        }

        /*
        * 关于这里数组长度为什么是R+2
        * 使用charAt方法可能会产生-1，使用它的返回值的时候需要+1，这样count[]中所有的索引都是非负的
        * 这样意味着每个字符串中的每个字符都可能产生R+1个不同的值，
        * 0代表着字符串结尾，1代表着字母表的第一个字符，2代表着字母表的第二个字符。以此类推
        * 因为键索引技术法还需要一个额外的位置，即count[0]不放数据，所以这里的数组长度为R+2
        * */
        int[] count = new int[R+2];
        //计算每个数字出现的频率
        for(int i = low; i <=high; i++){
            //这里的charAt方法将从字符串提取出来的第d位字符转换成了对应的ASCII值，代表字符
            int c = charAt(a[i], d);
            count[c+2]++;
        }

        //将频率转换位索引
        for(int r = 0; r < R+1; r++){
            count[r+1] += count[r];
        }

        //将元素进行分类
        for(int i=low; i <= high; i++){
            int c = charAt(a[i], d);
            aux[count[c+1]++] = a[i];
        }

        //回写数据，将排序好的数据写回a[i]中
        for(int i = low; i <= high; i++){
            a[i] = aux[i-low];
        }

        /*
        * 递归排序每个字符(不包括sentinel-1)，
        * 原先这里使用的是循环，现在使用递归，因为每个字符串的长度不一样了
        * low + count[r]到low+count[r+1]-1刚好是同一个首字母的字符串，
        * 这样就切分出来每个首字母所对应的字符串的子数组，然后分别对这些子数组进行递归排序，
        * 即可得到最终排序好的结果。
        * */
        for(int r=0; r < R; r++){
            sort(a, low + count[r], low+count[r+1]-1, d+1, aux);
        }
    }

    public static void sort(int[] a) {
        int n = a.length;
        int[] aux = new int[n];
        sort(a, 0, n-1, 0, aux);
    }

    // MSD sort from a[lo] to a[hi], starting at the dth byte
    private static void sort(int[] a, int lo, int hi, int d, int[] aux) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        // compute frequency counts (need R = 256)
        int[] count = new int[R+1];
        int mask = R - 1;   // 0xFF; 0000 1111
        // 32 - 8 * d - 8
        int shift = BITS_PER_INT - BITS_PER_BYTE*d - BITS_PER_BYTE;
        for (int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            count[c + 1]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];

        /************* BUGGGY CODE.
         // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
         if (d == 0) {
         int shift1 = count[R] - count[R/2];
         int shift2 = count[R/2];
         for (int r = 0; r < R/2; r++)
         count[r] += shift1;
         for (int r = R/2; r < R; r++)
         count[r] -= shift2;
         }
         ************************************/
        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            aux[count[c]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // no more bits
        if (d == 4) return;

        // recursively sort for each character
        if (count[0] > 0)
            sort(a, lo, lo + count[0] - 1, d+1, aux);
        for (int r = 0; r < R; r++)
            if (count[r+1] > count[r])
                sort(a, lo + count[r], lo + count[r+1] - 1, d+1, aux);
    }


    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }

    // TODO: insertion sort a[lo..hi], starting at dth character
    private static void insertion(int[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && a[j] < a[j-1]; j--)
                exch(a, j, j-1);
    }


    private static boolean less(String v, String w, int d) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        String[] a = {"she","by","sea","are","surely","sell","buy","car"};
        int n = a.length;

        sort(a);

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
