package zzwalgs4.查找;

/**
 * author: zzw5005
 * date: 2018/8/16 19:47
 */

/*
* 基于有序数组的二分查找的相关操作操作(这里键不重复)
*
* 时间复杂度: 查找 O(lgN)   插入O(N)
* */
public class BinarySearchST {
    private static String[] keys;
    private static String[] vals;
    private static int N;

    //对键的数组进行二分查找给定的键
    //先将给定键与中间的键比较，如果相等则返回其索引，如果小于中间键则再左半部分查找，大于就在右半部分查找。
    //如果最终没有在数组中找到相应的键，则返回low的索引
    public int rank(String key){
        if(key == null) throw new IllegalArgumentException("argument to rank() is null");

        int low = 0, high = N-1;
        while(low <= high){
            int mid = low + (high - low)/2;
            int cmp = key.compareTo(keys[mid]);
            if(cmp < 0) high = mid -1;
            else if(cmp > 0) low = mid + 1;
            else return mid;
        }
        return low;
    }

    public BinarySearchST(int capacity){
        keys = new String[capacity];
        vals = new String[capacity];
    }

    //通过键获取值
    private String get(String key){
        if(key == null){
            throw new IllegalArgumentException("argument to get() is null");
        }

        if(isEmpty()) return null;

        //通过二分查找数组找到给定的键的位置
        int i = rank(key);
        //如果在有序数组中找到给定的键，则返回该键对应的值
        if(i < N && keys[i].compareTo(key) == 0) return vals[i];
        return null;
    }

    //插入一个键值对
    private void put(String key, String val){
        if(key == null){
            throw new IllegalArgumentException("argument to put() is null");
        }

        if(val == null){
            delete(key);
            return;
        }

        int i= rank(key);

        //这里是插入或者更新操作，如果键存在，值也存在，就是更新操作，如果键存在，值不存在，就是插入操作
        //如果键不存在，则直接返回
        if(i < N && keys[i].compareTo(key) == 0){
            vals[i] = val;
            return;
        }

        //如果在插入操作之前，数组就已经满了，就先扩容再进行插入的操作
        if(N == keys.length) resize(2*keys.length);

        for(int j = N; j >i; j--){
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }

        keys[i] = key;
        vals[i] = val;
        N++;
    }

    private void delete(String key){
        if(key == null){
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if(isEmpty()) return;

        //二分查找如果最后没有找到数组中对应的键，则会返回low的索引
        int i = rank(key);

        //如果查找完数组也没有找到给定的键，则直接返回
        if(i == N || keys[i].compareTo(key) != 0) return;

        //这里其实是将low = low + 1，将数组的原来的low排除在搜索范围
        //将原来的键数组和值数组都将low索引去除
        for(int j=i; j < N-1; j++){
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }

        N--;
        keys[N] = null;
        vals[N] = null;

        if(N > 0 && N == keys.length/2) resize(keys.length/2);
    }

    //对原数组进行扩容
    public static void resize(int capacity){
        String[] tempK = new String[capacity];
        String[] tempV = new String[capacity];
        for(int i=0; i<N; i++){
            tempK[i] = keys[i];
            tempV[i] = vals[i];
        }
        vals = tempV;
        keys = tempK;
    }

    public static int size(){
        return N;
    }

    public static boolean isEmpty(){
        return size() ==0;
    }

    public static void main(String[] args) {
        String[] keys = {"0", "1","2", "3","4","5","6"};
        String[] vals = {"a", "b","c","d","e","f","g"};

        BinarySearchST bst = new BinarySearchST(7);
        bst.put("0","a");
        bst.put("1","b");
        //bst.put("2","c");
        bst.put("3","d");
        bst.put("4","e");
        bst.put("5","f");
        bst.put("6","g");

        //System.out.println(bst.get("0"));
        //System.out.println(bst.get("1"));


        bst.delete("2");

        System.out.println("---------------------------------------------");
        /*for(String s : keys){
            System.out.println(s + "  " + bst.get(s));
        }*/


    }
}
