package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/16 15:31
 */

/*
* 堆排序:
* 核心思想:先将数组建成一个最大堆，然后不断交换堆中的最大元素与a[k]的元素，再修复堆，直到只剩下一个元素，排序完成
* 时间复杂度: O(n log(n))
* */
public class HeapSort {
    public static void sort(int[] array){
        int i,k;
        int n = array.length;

        //将array[0, n-1]构建成一个最大堆
        //此时array ={7,6,5,0,4,1,2,3}
        for(i = n/2-1; i >=0; i--){
            sink(array, i, n);
        }

        //将最大元素a[0]和a[k]进行交换，并修复堆的有序性
        //比如第一次将a[0]=7与a[7]=3进行交换，然后修复堆，此时array = {6,4,5,3,0,1,2,3,  7}
        //然后k--，相当于7已经排序好了，只需要排列子数组{6,4,5,3,0,1,2,3}
        //第二次将a[0]=6与a[6]=3，进行交换，然后修复堆，如此循环，直到k=1，修复堆结束，此时排序完成
        for(k= n-1; k > 0; k--){
            exch(array, 0, k);
            sink(array, 0, k);
        }
    }

    /*
     * 位置为k的结点的左右子节点是2*k和2*k+1(这里进行了直接取整，不四舍五入)
     * 如果某个结点比它的两个子节点或者其中之一还要小的时候，我们可以通过将它和它的两个子节点中的较大者进行交换。
     * 如果交换后，它仍比两个子节点或者其中之一还要小，就继续进行交换，直到它比它的两个子节点都要大的时候为止，
     * 这时候堆的有序状态就恢复了。
     * */
    private static void sink(int[] array, int i , int n){
        //第i个结点的右子树，i就是本次循环的根节点
        while(2*i+1 < n){
            int  j = 2*i + 1;
            if((j+1)<n){
                //左子树的结点值小于右子树的结点值，则父节点与两个子节点中的较大者进行交换
                if(array[j] < array[j+1]) j++;
            }
            //如果此时父节点仍然小于两个子节点或者其中的一个，则进行交换；不然则满足堆的有序性，跳出循环
            if(array[i]< array[j]){
                exch(array, i, j);
                i = j;
            }else{
                break;
            }
        }
    }

    private static void exch(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {7, 0, 5, 6, 4, 1, 2, 3};
        sort(array);
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + " ");
        }
    }
}
