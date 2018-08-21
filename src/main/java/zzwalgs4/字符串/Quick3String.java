package zzwalgs4.字符串;

/**
 * author: zzw5005
 * date: 2018/8/19 20:09
 */

/*
* 三向字符串快速排列
* 核心思想:将字符串数组a[]排序时候，根据他们的首字母进行三向切分，然后(递归地)将得到三个子数组排序；
* 一个含有所有首字母小于切分字符的字符串子数组，一个含有首字母等于切分字符的字符串的子数组(排序的时候忽略他们的首字母)，
* 一个含有所有首字母大于切分字符的字符串的子数组
*
* 时间复杂度: 要将含有N个随机字符串的数组排序，三向字符串快速排序，平均需要比较字符 2NlgN次
* 使用场景: 特是适合有加长的公共前缀的键
* */
public class Quick3String {

    public static void sort(String[] a){
         sort(a, 0, a.length-1, 0);
    }

    private static void sort(String[] a, int low, int high, int d){
        if(high <= low)
            return;

        int lt = low, gt = high;

        //需要排列的字符串数组的第一个字符串的的首字母
        int first = charAt(a[low], d);

        int i = low + 1;

        /*
        * 这个循环就是将字符串数组中的元素，按照与第一个字符串的首字母和其他字符串的首字母进行比较分成三个子数组
        * */
        while(i <= gt){
            int next = charAt(a[i], d);
            /*
            * 每一次比较和交换都会排好一个字符串
            * */
            //如果字符串数组下一个字符串的首字母比第一个字符串的首字母要小，则交换两个字符串在数组中的位置
            if(next < first)
                exch(a, lt++, i++);
            //如果字符串的下一个字符串的首字母比第一个字符串的首字母要大，则交换这个字符与数组中最后一个字符串的位置
            else if (next > first)
                exch(a, i, gt--);
            //如果相等，则进行下一个字符串的首字母与第一个字符串的首字母的比较
            else    i++;
        }

        //递归小于首字母的子数组
        sort(a, low, lt-1, d);
        //递归等于首字母的子数组，其中d+1是为了忽略首字母
        if(first >= 0) sort(a, lt, gt, d+1);
        //递归大于首字母的子数组
        sort(a, gt+1, high, d);
    }

    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int charAt(String s, int d){
        if(d < s.length())
            return s.charAt(d);
        else
            return -1;
    }

    public static void main(String[] args) {
        String[] a = {"da","sh","by","se","ar","ha","de"};
        int n = a.length;

        sort(a);

        for(String s : a){
            System.out.println(s);
        }
    }

}
