package zzwalgs4.查找.java数据结构与算法;

/**
 * author: zzw5005
 * date: 2018/8/18 21:02
 */

/*
* 解决哈希冲突的方法一:探测法
* 核心思想: 当通过哈希函数计算数组的索引相同，产生了碰撞。则对计算的索引进行增加，再探测，新索引是否被占用，
* 如果被占用，则继续增加，再探测，直到找到未被占用的索引位置; 如果未被占用，则将元素插入到这个位置。
* 这里两种增加索引的方式:
* 1.线性探测法 如果索引第i次产生碰撞，则在索引使用线性函数(比如为i+1)，继续探测
* 2.平方探测法 如果索引第i次产生碰撞，则在索引使用平方函数(比如为i^2)，继续探测
* 平方探测法可以解决线性探测中一次聚集问题
* 下面的实现的方式选择的是平方探测法
* */
public class QuadraticProbingHashTable {
    private static final int DEFAULT_TABLE_SIZE = 101;

    private HashEntry[ ] array;    //存放元素的数组
    private int occupied;          //数组中位置被占有的数量
    private int theSize;           //当前数组的大小(因为数组可能会扩容)

    //初始化构造哈希表
    public QuadraticProbingHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }

    public QuadraticProbingHashTable(int size){
        allocateArray(size);
        doClear();
    }

    /**
     * 在哈希表中插入一个元素，如果这个元素已经存在，则什么也不做，返回false
     * 如果插入成功，返回true
     * @param x
     * @return
     */
    public boolean insert(String x){
        //探测工作，在findPosition方法中执行
        int curPosition = findPosition(x);
        if(isActive(curPosition))
            return false;

        //如果当前HashEntry为空，则代表可以将元素插入到这里，被占的数组位置大小+1
        if(array[curPosition] == null)
            ++occupied;

        //在当前数组位置新建一个HashEntry，插入到这里
        array[curPosition] = new HashEntry(x, true);
        theSize++;

        //这里是要求散列表至少为该表中元素个数的两倍大，这样平方探测解决方案总可以实现
        //源自于一个定理: 使用平方探测的方法，且表的大小是素数，那么当表至少一半是空的时候，总能插入一个新元素。
        if(occupied > array.length / 2)
            rehash();

        return true;
    }

    public boolean contains(String x){
        int curPosition = findPosition(x);
        return isActive(curPosition);
    }

    public boolean remove(String x){
        int curPosition = findPosition(x);
        if(isActive(curPosition)){
            //将isActive标志设置false，表示这个索引位置的HashEntry中的元素已经被删除了，实际上是一种懒惰删除，并没有立刻删除
            array[curPosition].isActive = false;
            theSize--;
            return true;
        }else{
            return false;
        }
    }

    /**
     * 得到当前数组的存在Active的HashEntry的数量
     * @return
     */
    public int size(){
        return theSize;
    }

    /**
     * 返回内部表的长度
     * @return
     */
    public int capacity(){
        return array.length;
    }


    private int findPosition(String x){
        int offset = 1;
        int curPosition = myhash(x);

        //这里的平方函数的定义为 f(i)=f(i-1)+2i-1
        //这里很巧妙的使用了递归的加法代替了平方
        //如果当前索引位置的元素不为空，且存放的元素不是要插入的元素
        while(array[curPosition] != null && !array[curPosition].value.equals(x)){
            curPosition += offset;
            offset += 2;
            //如果到了数组的末尾仍未检测到数组中可用位置，则回到数组开头，继续探测
            if(curPosition >= array.length)
                curPosition -= array.length;
        }
        return curPosition;
    }

    /**
     * 扩容
     */
    private void rehash(){
        HashEntry[] oldArray = array;
        //扩容大小为以员数组大小长度的2倍为起点的下一个质数
        allocateArray(2 * oldArray.length);

        occupied = 0;
        theSize = 0;

        for(HashEntry entry : oldArray){
            if(entry != null && entry.isActive)
                insert(entry.value);
        }
    }

    /**
     * 重新设置数组大小，因为数组要求必须是质数
     * 根据输入的整数位起点，返回下一个质数
     * @param arraySize
     */
    private void allocateArray(int arraySize){
        array = new HashEntry[nextPrime(arraySize)];
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

    /**
     * 根据输入的字符串计算它在数组中的索引
     * @param x
     * @return
     */
    private int myhash(String x){
        int hashVal = x.hashCode();
        hashVal %= array.length;

        //因为字符串的hashCode函数得出的可能是负数
        if(hashVal < 0){
            hashVal += array.length;
        }
        return hashVal;
    }


    /**
     * 根据给定的索引位置，判断数组在这个索引是否为空和是否可用
     * 如果这个HashEntry已经存放了元素，元素不为空，而且isActive为true，表示这个HashEntry是良好的，
     * 其他数据不能插入到这个位置。
     * @param curPosition
     * @return
     */
    private boolean isActive(int curPosition){
        return array[curPosition] != null && array[curPosition].isActive;
    }

    private void doClear(){
        occupied = 0;
        for(int i=0; i < array.length; i++){
            array[i] = null;
        }
    }

    private static class HashEntry{
        public String value;
        public boolean isActive;            //标为已删除的时候，则返回false

        public HashEntry(String val){
            this(val, true);
        }

        public HashEntry(String val, boolean flag){
            value = val;
            isActive = flag;
        }
    }

    public static void main(String[] args) {
        QuadraticProbingHashTable hTable = new QuadraticProbingHashTable();

        long startTime = System.currentTimeMillis( );

        final int NUMS = 2000000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );


        for (int i = 0; i < 1010; i++)
            hTable.insert("" + i);


        for (int i = 1; i < 1010; i += 2)
            hTable.remove("" + i);

        if (!hTable.insert("" + 0))
            System.out.println("Sorry, fail insert " + 0);

        if (hTable.contains("" + 0))
            System.out.println("OK, find " + 1);


        if (!hTable.contains("" + 1))
            System.out.println("Sorry, find fali " + 1);

        long endTime = System.currentTimeMillis();

        System.out.println("Totaltime: " + (endTime - startTime));
    }
}
