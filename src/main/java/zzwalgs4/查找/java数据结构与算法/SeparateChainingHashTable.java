package zzwalgs4.查找.java数据结构与算法;

import java.util.LinkedList;
import java.util.List;

/**
 * author: zzw5005
 * date: 2018/8/18 19:45
 */

/*
* 解决哈希冲突的方法一:分离链接法
* 核心思想: 使用数组+链表的形式，数组的每个元素都是一个链表，如果通过哈希函数计算的数组索引相同，产生碰撞，
* 则将要插入的元素，添加在这个索引下的链表的末尾
* 典型代表: HashMap
* */
public class SeparateChainingHashTable {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private List<String>[] listArray;
    private int curSize;


    public SeparateChainingHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int size){
        listArray = new LinkedList[nextPrime(size)];
        for(int i = 0; i < listArray.length; i++){
            listArray[i] = new LinkedList<>();
        }
    }

    /**
     * 进行插入操作，先用myhash计算出给定的字符串在数组中的索引
     * 检查链表中是否包含了这个字符串，如果没有包含，则在链表中进行插入操作，如果包含，则说明都不做
     * @param x
     */
    public void insert(String x){
        List<String> whichList = listArray[myhash(x)];
        if(!whichList.contains(x)){
            whichList.add(x);
            if(++curSize > listArray.length){
                rehash();
            }
        }
    }

    public void remove(String x){
        List<String> whichList = listArray[myhash(x)];
        if(whichList.contains(x)){
            whichList.remove(x);
            curSize--;
        }
    }

    public boolean contains(String x){
        List<String> whichList = listArray[myhash(x)];
        return whichList.contains(x);
    }

    public void makeEmpty(){
        for(int i=0; i<listArray.length; i++){
            listArray[i].clear();
        }
        curSize = 0;
    }


    /**
     * 扩容，新建一个数组，容量为员数组的两倍，再将旧数组的值复制到新数组中，
     * 将旧数组引用改为指向新数组
     */
    private void rehash(){
        List<String>[] oldList = listArray;

        listArray = new List[nextPrime(2*listArray.length)];

        for(int j= 0; j < listArray.length; j++)
            listArray[j] = new LinkedList<>();

        curSize = 0;
        for(List<String> listArray : oldList)
            for(String item : listArray)
                insert(item);
    }

    /**
     * 根据输入的字符串计算它在数组中的索引
     * @param x
     * @return
     */
    private int myhash(String x){
        int hashVal = x.hashCode();
        hashVal %= listArray.length;
        //因为字符串的hashCode函数得出的可能是负数
        if(hashVal < 0){
            hashVal += listArray.length;
        }
        return hashVal;
    }

    /**
     * 根据给定的整数，输出以n为起点的下一个质数
     * @param n
     * @return
     */
    private static int nextPrime(int n){
       if(n % 2 == 0)
           n++;

       for(; !isPrime(n); n += 2)
           ;

       return n;
    }

    /**
     * 判断输入的整数是不是质数
     * @param n
     * @return
     */
    private static boolean isPrime(int n){
        if(n == 2 || n== 3)
            return true;

        if(n ==3 || n % 2 == 0)
            return false;

        for(int i=3; i * i <= n; i +=2){
            if(n % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SeparateChainingHashTable scht = new SeparateChainingHashTable();

        long startTime = System.currentTimeMillis();

        for(int i= 0; i < 1010; i++)
            scht.insert(i+"");

        for(int i = 1; i< 1010; i += 2)
            scht.remove(i+"");

        for(int i = 2; i < 1010; i += 2){
            if(scht.contains(i+""))
                System.out.println("I'm Ok");
        }

        for(int i = 1; i < 1010; i += 2){
            if(!scht.contains(i+""))
                System.out.println("I'm Sorry");
        }

        long endTime = System.currentTimeMillis();

        System.out.println("totalTime = " + (endTime - startTime));
    }
}
