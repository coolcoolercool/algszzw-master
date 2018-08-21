package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/15 20:24
 */

/*
* 核心思想:
* 快速排序递归地将数组a[low,...,high]排序，先用partition()方法将a[j]返高一个合适的位置，
* 然后再用递归调用将其他位置的元素排序。如果左子数组和右子数组都是有序的，那么左子数组(有序并且没有元素大于切分元素)、
* 切分元素和有子数组(有序且没有元素小于切分元素)组成的结果数组也一定是有序的
* 时间复杂度: O(NlgN)
* */
public class Quick {

    public static void qucikSort(int[] array){
        sort(array, 0, array.length-1);
    }

    private static void sort(int[] array, int low, int high){
        if(high <= low)
            return;

        int j = partition(array, low, high);
        sort(array, low, j-1);           //将左半部分a[low,..., j-1]排序
        sort(array, j+1, high);          //将左半部分a[j+1,...,high]排序
    }

    //快排的切分
    //我们一开始选择的值是array[low]进行切分
    //当指针i和j相遇时候主循环推出。再循环中a[i]小于index时候，我们增大i，a[j]大于index的时候我们减小j
    //然后交换a[i]和a[j]来保证i左侧的元素都不大于index，j右侧的元素都不小于index。当指针相遇交换a[i]和a[j]，
    //切分结束，切分值就留在了a[j]中了
    private static int partition(int[] array, int low, int high){
        int i = low;             //左右扫描指针
        int j = high+1;
        int index = array[low];  //切分元素

        while(true){
            while(array[++i]<index){
                if(i == high) break;
            }
            while(index<array[--j]){
                if(j == low) break;
            }
            if(i >= j) break;
            //如果在i与j指针没有相遇之前，就结束了两个while循环，则交换此时array[i]和array[j]的元素，
            //继续进行while循环
            exch(array, i, j);
        }
        exch(array, low, j);     //将index=array[j]放入正确的位置
        return j;                //达成    a[low,...,j-1] <= a[j] <= a[j+1,...,high]
    }

    private static void exch(int[] array, int i, int j){
        int swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    public static void main(String[] args) {
        int[] array = {7, 0, 5, 6, 4, 1, 2, 3};
        qucikSort(array);
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + " ");
        }
    }
}
