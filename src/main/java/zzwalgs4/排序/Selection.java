package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/14 21:42
 */

/*
* 选择排序:
* 首先，找到数组中最小的那个元素，其次，将它和数组的第一个元素交换位置（如果第一个元素就是最小元素那么它就和自己交换）。
* 再次，在剩下的元素中找到最小的元素，将它与数组的第二个元素交换位置。如此往复，直到将整个数组排序。
* 这种方法叫做选择排序，因为它在不断地选择剩余元素之中的最小者。
* 时间复杂度大约为 N^2/2 + N，所以O(N^2)
* */
public class Selection {
    /**
     * 将数组升序排列
     * @param array
     */
    public static void sort(int[] array){
        int length = array.length;
        //将a[i]与数组的a[i+1]...a[length-1]最小的元素交换
        for(int i=0; i<length; i++){
            int min = i;                        //最小元素的索引
            //如果数组数组的a[i+1]...a[length-1]最小的元素,比min的小的，就进行交换；
            //如果都不小于array[min],则i与i交换，等于不变
            for(int j=i+1; j<length; j++){
                if(array[j]<array[min]){
                    min = j;
                }
            }
            exch(array, i, min);
        }
    }

    /**
     * 将数组的的给定索引的元素交换
     * @param array
     * @param i
     * @param j
     */
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
