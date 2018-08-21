package zzwalgs4.排序;

/**
 * author: zzw5005
 * date: 2018/8/16 11:22
 */
/*
 * 优先队列:实现插入元素和返回并删除最小元素的功能(最小堆构成的优先队列)
 * 优先队列是由一个基于堆的完全二叉树表示，存储于数组pq[1,..,N]中，pq[0]没有使用。
* */

public class MinPQ {
    private static int[] pq;
    private static int N = 0;  //存储在pq[1...N]中，pq[0]没有使用

    public MinPQ(int maxN){
        pq = new int[maxN+1];
    }

    public static void insert(int value){
        if(N == pq.length - 1) resize(2 * pq.length);
        pq[++N] = value;
        swim(N);                            //插入新元素，进行调整，使之满足堆的要求
    }

    public static int delMin(){
        if(isEmpty()){
            System.out.println("最小堆已空");
            return pq[0];
        }

        int max = pq[1];                    //从根节点得到最大元素
        exch(1, N--);                       //将其和最后一个结点交换

        //这一步是为了将数组最后一个位置的置为空，防止越界
        /*Integer flag = new Integer(pq[N+1]);
        flag = null;*/

        sink(1);                             //恢复堆的有序性
        return max;
    }

    /*
     * 当某个结点的优先级上升的时候，我们需要由下至上恢复堆的有序性。
     * 位置为k的结点的父节点的位置是k/2(这里进行了直接取整，不四舍五入)
     * 如果某个结点比它的父节点更小，就需要交换它和它的父节点来修复堆。如果交换后，它仍比父节结点小，就继续交换，
     * 直到它比它的父节点要大为止，这时候堆的有序状态就恢复了。
     * */
    private static void swim(int k){
        while(k > 1&& pq[k]<pq[k/2]){
            exch(k, k/2);
            k = k/2;
        }
    }

    /*
     * 当某个结点的优先级下降的时候，我们需要由上至下恢复堆的顺序。
     * 位置为k的结点的子节点是2*k和2*k+1(这里进行了直接取整，不四舍五入)
     * 如果某个结点比它的两个子节点或者其中之一还要大的时候，我们可以通过将它和它的两个子节点中的较小者进行交换。
     * 如果交换后，它仍比两个子节点或者其中之一还要大，就继续进行交换，直到它比它的两个子节点都要小的时候为止，
     * 这时候堆的有序状态就恢复了。
     * */
    private static void sink(int k){
        while(2*k <= N){
            int j = 2*k;
            if(j < N && pq[j]>pq[j+1]) j++;
            if(pq[k]<=pq[j]) break;
            exch(k, j);
            k=j;
        }
    }

    //将数组给定的两个索引的元素进行交换
    private static void exch(int i, int j){
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    public static boolean isEmpty(){
        return N ==0;
    }

    //对原数组进行扩容
    public static void resize(int capacity){
        int[] temp = new int[capacity];
        for(int i=1; i <= N; i++){
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public static void main(String[] args) {
        int maxN = 9;
        MinPQ pq = new MinPQ(maxN+1);
        pq.insert(60);
        pq.insert(40);
        pq.insert(90);
        pq.insert(70);

        pq.insert(20);
        pq.insert(30);
        pq.insert(50);
        pq.insert(80);

        pq.insert(10);

        while(!pq.isEmpty()){
            System.out.println(pq.delMin());
        }
    }
}
