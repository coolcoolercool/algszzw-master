package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/15 15:11
 */
/*
* 核心思想:
* 使数组中的任意间隔h的元素都是有序的，这样的数组成为h有序数组。
* 在初始的时候，先将h设置好，最小为1，然后将数组变成h有序数组，再同样缩小h为h1，再将h有序数组变为h1有序数组，
* 由于h最小为1，所以最后会变成1有序数组，即数组排列完成。
* 希尔排列可以理解成先对数组进行了预处理，再进行插入排序。
* 预处理使得原数组成为子数组很短，子数组是部分有序的，这样的数组很适合使用插入排序,相比直接使用插入排序，减少了
* 比较和移动的次数。
*
* 时间复杂度: O(N^(3/2))
* */
public class Shell {
    /**
     * 将数组array按升序排列
     * @param array
     */
    public static void sort(int[] array){
        int length = array.length;
        //h称为增量序列的增量值
        int h = 1;
        //这里a是一个影响因子，取值不同会对希尔排序的性能产生影响
        int a = 3;
        while(h < length/a) {
            h = a * h + 1;
        }

        //先将数组变得h有序，然后再缩小h为h2，继续排列数组成为h2有序，直到最后排列成1有序，即排列完成
        while(h >= 1){
            for(int i=h; i<length; i++){
                //将a[i]插入到 a[i-h]，a[i-2*h]，a[i-3*h]……之中，
                //就是先将a[i]，a[i-h]，a[i-2*h]，a[i-3*h]……的顺序排好，这样数组就变成h有序了
                for(int j=i; j>=h && array[j]<array[j-h]; j -=h){
                    exch(array, j, j-h);
                }
            }
            h = h/a;
        }
    }

    private static void exch(int[] array, int i, int j){
        int swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    public static void main(String[] args) {
        int[] array = {9, 0, 5, 6, 8, 7, 4, 1, 2, 3};
        sort(array);
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + " ");
        }
    }
}
