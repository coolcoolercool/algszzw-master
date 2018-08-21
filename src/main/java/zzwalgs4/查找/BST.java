package zzwalgs4.查找;

/**
 * author: zzw5005
 * date: 2018/8/16 22:10
 */

/*
* 二叉查找树:
* 树由Node对象组成，每个对象都含有一对键值，两条链接和一个结点计数器N。
* 每个Node对象都是一棵含有含有N个结点的子树的根节点，它的左链接指向一个由小于该结点的所有键组成的二叉查找树，
* 右链接指向以一棵由大于该结点的所有键组成的二叉查找树。root变量指向二叉查找树的根节点Node对象
* 二叉查找树的特点: 每个父节点都大于左子树，小于右子树
* 时间复杂度:  O(lgN)
* */
public class BST {
    private static Node root;

    private static class Node{
        private String key;           //键
        private String val;           //值
        private Node left, right;     //指向子树的链接
        private int N;                //以该结点为跟的子树中的结点的总数

        public Node(String key, String val, int N){
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public static int size(){
        return size(root);
    }

    private static int size(Node curNode){
        if(curNode == null) return 0;
        else return curNode.N;
    }

    public static String get(String key){
        return get(root, key);
    }

    private static String get(Node curNode, String key){
        //在以curNode根节点的子树中查找并返回key所对应的值
        //如果找不到，则返回null
        if(curNode == null) return null;
        int cmp = key.compareTo(curNode.key);
        if(cmp < 0) return get(curNode.left, key);
        else if(cmp >0) return get(curNode.right, key);
        else return curNode.val;
    }

    public static void put(String key, String val){
        //查找key，找到则更新它的值，否则为它创建一个新的结点

        root = put(root, key, val);
    }

    public static Node put(Node curNode, String key, String val){
        //如果keys存在于以curNode为根节点的子树中，则更新它的值
        //否则将以key和val为键值对的新节点插入到该子树中
        if(curNode == null) return new Node(key, val ,1);
        int cmp = key.compareTo(curNode.key);
        if(cmp <0) curNode.left = put(curNode.left, key, val);
        else if(cmp > 0) curNode.right = put(curNode.right, key, val);
        else curNode.val = val;

        curNode.N = size(curNode.left) + size(curNode.right) + 1;
        return curNode;
    }

    public static String min(){
        return min(root).key;
    }
    //如果根节点的左连接为空，那么一棵二叉查找树中最小的键就是根节点;
    //如果左连接非空，那么树中最小的键就是左子树中的最小键。

    /**
     * 返回以给定结点为根节点的二叉查找树的最小键所对应结点
     * @param curNode
     * @return
     */
    public static Node min(Node curNode){
        if(curNode.left == null) return curNode;
        return min(curNode.left);
    }

    public static String max(){
        return max(root).key;
    }

    public static Node max(Node curNode){
        if(curNode.right == null) return curNode;
        return max(curNode.right);
    }

    /**
     * 给定在二叉查找树中的排名，返回对应排名的结点的键
     * @param k
     * @return
     */
    private static Node select(int k){
        return select(root, k);
    }

    private static Node select(Node curNode, int k){
        //返回排名为k的结点
        if(curNode == null) return null;
        int t = size(curNode.left);
        if       (t>k) return select(curNode.left, k);
        else if  (t<k) return select(curNode.right, k-t-1);
        else           return curNode;
    }

    /**
     * 根据给定的键，返回它在二叉查找树中的排名
     * @param key
     * @return
     */
    private static int rank(String key){
        return rank(key, root);
    }

    //如果给定的键和根节点的键相等，就返回左子树中的结点总数t;
    //如果给定的键小于根节点的键，就返回该键在左子树中的排名(递归计算)
    //如果给定的键大于根节点的键，就返回t+1(根节点)加上它在右子树中的排名(递归计算)
    private static int rank(String key, Node curNode){
        //返回以curNode为根节点的子树中小于curNode.key的键的数量
        if(curNode == null) return 0;
        int cmp = key.compareTo(curNode.key);
        if       (cmp < 0) return rank(key, curNode.left);           //小于
        else if  (cmp > 0) return 1 + size(curNode.left) + rank(key, curNode.right);  //大于
        else               return size(curNode.left);                 //相等
    }

    /**
     * 删除二叉查找树的最小键
     * @return
     */
    private static Node deleteMin(){
        root = deleteMin(root);
        return root;
    }

    /**
     * 删除以给定结点为根节点的二叉查找树的最小键
     * 不断递归寻找根节点的左子树，直到找到一个空连接(即curNode.left = null)，然后将指向该结点的链接指向该节点的右子树
     * 举个例子: 结点A的左子树为null，右子树为C，E的左子树为A，上面的操作就是将E的左子树变成C
     * (只需要在递归调用中返回它的右连接即可)。此时已经没有链接指向要被删除的结点，因此它会被GC清理掉。
     * 然后再设置它的父节点的链接并更新它到根节点的路径上的所有结点的计数器的值
     * @param curNode
     * @return
     */
    private static Node deleteMin(Node curNode){
        if(curNode.left == null) return curNode.right;     //返回该结点的右链接，其实就是将指向该节点的链接改为指向该节点的右子树
        curNode.left = deleteMin(curNode.left);            //curNode != null，继续递归它的左子树
        curNode.N = size(curNode.left) + size(curNode.right) + 1; //重新设置以该结点为根节点的子树中的结点总数
        return curNode;
    }

    private static void deleteMax(){
        root = deleteMax(root);
    }

    private static Node deleteMax(Node curNode){
        if(curNode.right == null) return curNode.left;
        curNode.right = deleteMax(curNode.right);
        curNode.N = size(curNode.left) + size(curNode.right) + 1;
        return curNode;
    }

    /**
     * 根据给定的键，删除对应的结点
     * @param key
     */
    private static void delete(String key){
        root = delete(root, key);
    }

    /**
     * 根据给定的键和结点，删除给定键所对应的结点
     * 核心思想: 找到要删除结点的后继节点或者前继节点，用这个结点覆盖要删除的结点，然后再删除覆盖了要删除结点的后继节点或者前继节点
     * 前继节点就是以要删除结点为根节点的左子树中值最接近要删除的结点值
     * 后继节点就是以要删除结点为根节点的左子树中值最接近要删除的结点值
     *                       8
     *             4                   12
     *       2          6        10             14
     *   1       3    5
     *   如果要删除的结点是4，那么前继节点就是3，后继节点就是5
     * @param curNode
     * @param key
     * @return
     */
    private static Node delete(Node curNode, String key){
        if(curNode == null) return null;

        int cmp = key.compareTo(curNode.key);
        if(cmp < 0)  curNode.left = delete(curNode.left, key);
        if(cmp > 0)  curNode.right = delete(curNode.right, key);
        else{                                                     //cmp = curNode.key
            if(curNode.right == null) return curNode.left;    //返回该结点的右链接，其实就是将指向该节点的链接改为指向该节点的左子树
            if(curNode.left == null)  return curNode.right;

            Node temp = curNode;                     //找到了要删除的结点
            curNode.val = min(curNode).val;          //这里是用以curNode为根节点的二叉查找树的最小键对应的结点，这里称它为要删除的结点的后继节点，覆盖要删除的结点的键值对
            curNode.key = min(curNode).key;
            /*curNode.right = */
            deleteMin(curNode.right);  //此时就有了两个后继节点的键值对，这里删除在原来位置的后继节点
            //curNode.left = temp.left;
        }
        curNode.N = size(curNode.left) + size(curNode.right) + 1;
        return curNode;

    }

    public static void main(String[] args) {
        BST bst = new BST();
        String[] keys = {"1","2","3","4","5","6","8","910","912","914"};
        String[] vals = {"a","b","c","d","e","f","g","h","i","j","k"};
        bst.put(keys[0], vals[0]);
        bst.put(keys[1], vals[1]);
        bst.put(keys[2], vals[2]);
        bst.put(keys[3], vals[3]);
        bst.put(keys[4], vals[4]);
        bst.put(keys[5], vals[5]);
        bst.put(keys[6], vals[6]);
        bst.put(keys[7], vals[7]);
        bst.put(keys[8], vals[8]);
        bst.put(keys[9], vals[9]);

        System.out.println(bst.get("912"));

        System.out.println(bst.min());
        System.out.println(bst.max());
        System.out.println(bst.select(6).val);
        System.out.println(bst.rank("3"));
        System.out.println("----------------------------");


        Node root = bst.select(6);

       /* Node curNode = deleteMin();
        System.out.println(curNode.key +"  "+ curNode.val);
        deleteMax();
        delete("3");*/

        System.out.println("----------------------------");

        for(String str : keys){
            System.out.println(str + "  " + bst.get(str));
        }
    }
}

























