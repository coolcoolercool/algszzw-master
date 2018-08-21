package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/14 22:09
 */

/*
* 插入排序:
* 对于0到N-1之间的每个i，将a[i]与a[0]到a[i-1]中比它小的所有元素依次有序地交换(每次都是比较和交换相邻的位置的元素)
* 在索引i由左向右变换的过程中，它左侧的元素总是有序的，所以当i到达数组的右端的时候排序就完成了
* 时间复杂度: 平均情况需要N^2/4次比较和N^2/4交换，所以时间复杂度为O(N^2)
* 插入排序比选择排序快一倍
* */
public class Insertion {

    /**
     * 将数组array的元素进行升序排列
     * @param array
     */
    public static void sort(int[] array){
        int length = array.length;
        for(int i=0; i<length; i++){
            for(int j=i; j>0&&(array[j]<array[j-1]); j--){
                exch(array, j, j-1);
            }
        }
    }

    private static void exch(int[] array, int i, int j){
        int swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    public static void main(String[] args) {
        int[] array = {10, 0, 9, 5, 6, 8, 7, 4, 1, 2, 3};
        sort(array);
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + " ");
        }
    }
}
