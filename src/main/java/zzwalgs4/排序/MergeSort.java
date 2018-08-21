package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/15 19:51
 */

/*
 * 归并排序:
 * 核心思想：要对数组a[low,...,high]进行排序，先将它分为a[low，...,mid]和a[mid+1，...,high]两部分，
 * 分别通过递归调用单独排序，最后将有序的子数组归并为最终排序的结果
 * 采用的是分治思想
 * 时间复杂度:  O(NlgN)  归并排序的所需要的时间与NlgN成正比
 * 缺点: 需要使用辅助空间，辅助空间和N的大小成正比
 *
 * 代码执行顺序:
 * Sort(a,0,7)
 *  Sort(a,0,3)
 *      Sort(a,0,1)
 *          merge(a,0,0,1)
 *      Sort(a,2,3)
 *          merge(a,2,2,3)
 *      merge(a,0,1,3)
 *  
 *  Sort(a,4,7)
 *      Sort(a,4,5)
 *          merge(a,4,4,1)
 *      Sort(a,6,7)
 *          merge(a,6,6,7)
 *      merge(a,4,5,7)
 * merge(a,0,7)
 * */
public class MergeSort {

    //        a = { 7, 0, 5, 6,  4, 1, 2, 3}
    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;//左指针
        int j = mid + 1;//右指针
        int k = 0;

        //把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[j] > a[i]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        //把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }

        //把右边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }

        //把新数组中的数覆盖muns数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            a[k2 + low] = temp[k2];
        }
    }

    public static void Sort(int[] a, int low, int high)
    {
        int mid = (low + high) / 2;
        if (low < high) {
            //左边归并
            Sort(a, low, mid);
            //右边归并
            Sort(a, mid + 1, high);
            //左右归并
            merge(a, low, mid, high);
        }
    }

    public static void main(String[] args) {
        int[] a = {7, 0, 5, 6, 4, 1, 2, 3};
        Sort(a, 0, a.length - 1);
        for(int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

}
