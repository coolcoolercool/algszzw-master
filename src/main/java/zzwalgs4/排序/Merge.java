package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/15 16:58
 */

/*
* 归并排序:
* 核心思想：要对数组a[low,...,high]进行排序，先将它分为a[low，...,mid]和a[mid+1，...,high]两部分，
* 分别通过递归调用单独排序，最后将有序的子数组归并为最终排序的结果
* 采用的是分治思想
* 时间复杂度:  O(NlgN)  归并排序的所需要的时间与NlgN成正比
* 空间复杂度: O(N) 需要使用辅助空间，辅助空间和N的大小成正比
* */
public class Merge {
    private static int[] aux;  //归并所需要的辅助数组

    public static void sort(int[] array){
        //给辅助数组分配空间
        aux = new int[array.length];
        sort(array, 0, array.length-1);
    }

    private static void sort(int[] array, int low, int high){
        //将数组array[low....high]排序
        if(high <= low)
            return;

        int mid = low + (high - low)/2;
        sort(array, low, mid);          //将左半边排序
        sort(array, mid+1, high);       //将右半边排序
        merge(array, low, mid, high);   //归并结果
    }

    private static void merge(int[] array, int low, int mid, int high){
        //a[low...mid] 和a[mid+1, ... high]归并
        int i = low, j = mid+1;

        //将a[low...high]复制到aux[low...high]
        for(int k = low; k<=high; k++){
            aux[k] = array[k];
        }

        //归并回到a[low,..., high]
        for(int k = low; k <= high; k++){
            if(i > mid)                //左半边元素用完了(取右半边的元素)
                array[k] = aux[j++];
            else if(j > high)          //右半边元素用完了(取左半边的元素)
                array[k] = aux[i++];
            else if(aux[j]<aux[i])     //右半边的当前元素小于左半边的当前元素(取右半边的元素)
                array[k] = aux[j++];
            else                       //右半边的当前元素大等于左半边的当前元素(取左半边的元素)
                array[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        int[] array = {9, 0, 5, 6, 8, 7, 4, 1, 2, 3};
        sort(array);
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + " ");
        }
    }

}
