package zzwalgs4.查找;

import java.util.NoSuchElementException;

/**
 * author: zzw5005
 * date: 2018/8/18 9:35
 */

/*
* 红黑二叉查找树
* 定义: 1.红连接均为左连接
*      2.没有任何一个结点同时和两条红连接相连
*      3.该树是完美黑色平衡的，即任意空连接到根节点的路径上的黑链接数量相同
* */
public class RedBlackBST {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node{
        private String key;
        private String value;
        private Node left,right;
        private boolean color;
        private int size;

        public Node(String key, String value, boolean color, int size){
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackBST(){}

    private boolean isRed(Node curNode){
        if(curNode == null) return false;
        return curNode.color == RED;
    }

    private int size(Node curNode){
        if(curNode == null) return 0;
        return curNode.size;
    }

    public int size(){
        return size(root);
    }

    public boolean isEmpty(){
        return root == null;
    }

    public boolean contains(String key){
        return get(key) != null;
    }

    public String get(String key){
        if(key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    private String get(Node curNode, String key){
        while(curNode != null){
            int cmp = key.compareTo(curNode.key);
            if     (cmp < 0)  curNode = curNode.left;
            else if(cmp > 0)  curNode = curNode.right;
            else              return curNode.value;
        }
        return null;
    }

    /**
     * 查找key，如果找到则更新它的值，如果没有没有找到，就新建一个结点
     * @param key
     * @param val
     */
    public void put(String key, String val){
        if(key == null) throw new IllegalArgumentException("first argument to put() is null");
        if(val == null){
            delete(key);
            return;
        }

        root = put(root,key, val);
        root.color = BLACK;
    }

    private Node put(Node curNode, String key, String val){
        //以当前结点为根节点的树为空树
        //标准的插入操作，和父节点用红连接相连
        if(curNode == null) return new Node(key, val, RED,1);

        //二分递归查找给定的键key所对应的结点
        int cmp = key.compareTo(curNode.key);
        if      (cmp < 0) curNode.left = put(curNode.left, key, val);
        else if (cmp > 0) curNode.right = put(curNode.right, key, val);
        else              curNode.value = val;         //如果找到给定的key的对应结点，则更新它的值

        /*
        * 这三条if语句保证了在查找路径上红黑树和2-3树一一对应的关系，使得树的平衡性接近完美
        * 第一条if语句，将任意含有红色右链接的3-结点或者临时的4-结点向左旋转;
        * 第二条if语句，将临时的4-结点中两条连续红色链接的上层链接向右旋转;
        * 第三条if语句，进行颜色转换并将红连接在树中向上传递
        * */
        if(isRed(curNode.right) && !isRed(curNode.left))  curNode = rotateLeft(curNode);
        if(isRed(curNode.left)  && isRed(curNode.left.left)) curNode = rotateRight(curNode);
        if(isRed(curNode.left)  && isRed(curNode.right))  flipColors(curNode);

        curNode.size = size(curNode.left) + size(curNode.right);

        return curNode;
    }

    /**
     * 如果一个结点的左右连接都是红色链接的时候，就将左右连接的红色变为黑色，并将指向该节点的链接变为红色
     * @param h
     */
    private void flipColors(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    /**
     * 假设结点h为红色，h.left和h.left.left都是黑色
     * 将h.left或者h.left的子节点之一变红
     * @param h
     * @return
     */
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    public void deleteMin(){
        if(isEmpty()) throw new NoSuchElementException("BST underflow");

        //如果根节点的左右子节点都是黑色，就设置根节点为红色
        if(!isRed(root.left) && !isRed(root.right)) root.color = RED;

        root = deleteMin(root);
        //如果删除完最小键的结点，红黑树不为空，则将根节点重新设置成黑色
        if(!isEmpty()) root.color = BLACK;
    }

    /**
     * 首先删除最小键的结点，不能从2-结点中删除，，会留下一个空连接，破坏了红黑树的平衡，只能从3-结点或者临时的4-结点中删除
     * 所以首先确保当前结点不是2-结点，第二个if语句，就是将最小键包含在一个3-结点或者4-结点的变换，
     * 然后直接从中删除，将3-结点变为2-结点或者将4-结点变为3-结点，最后在回头向上分解所有的临时4-结点，恢复红黑树的平衡性
     * @param curNode
     * @return
     */
    private Node deleteMin(Node curNode){
        if(curNode.left == null) return null;
        //这里其实对应了三种情况
        // &&为短路符号 a && b 如果a为false，直接为false，不进行b的判断;
        //b为false，也为false，只有a和b均为true，才为true
        //1.如果当前结点的左子节点不是2-结点，完成
        //2.如果当前结点的左子结点是2-结点而它的亲兄弟结点不是2-结点，将左子结点的兄弟结点中的
        //一个键移动到左子树中
        //3.如果当前结点的左子树结点和他的亲兄弟结点都是2-结点，将左子结点、父节点中的最小键和左子节点最近的兄弟结点
        //合并为一个4-结点，使得父节点由3-结点变为2-结点或者由4-结点变为3-结点
        if(!isRed(curNode.left) && !isRed(curNode.left.left))
            curNode = moveRedLeft(curNode);

        curNode.left = deleteMin(curNode.left);
        return balance(curNode);
    }

    public void delete(String key){
        if(key == null) throw new IllegalArgumentException("argument to delete() is null");
        if(!contains(key)) return;

        //如果根节点的左右子节点都是黑色，则将根节点的颜色设置为黑色
        if(!isRed(root.left) && !isRed(root.right)) root.color = BLACK;

        root = delete(root, key);
        if(!isEmpty()) root.color = BLACK;
    }


    /**
     * 根据给定的键，删除以给定的结点作为根节点的红黑树 的对应的结点
     * 通过与删除最小键的结点相同变换，将问题转换成一个根节点不是2-结点的子树中删除最小键
     * @param curNode
     * @param key
     * @return
     */
    private Node delete(Node curNode, String key){
        if(key.compareTo(curNode.key) <0){
            if(!isRed(curNode.left) && !isRed(curNode.left.left))
                curNode = moveRedLeft(curNode);
            curNode.left = delete(curNode.left, key);
        }
        else{ //key.compareTo(curNode.key) >= 0
            if(isRed(curNode.left))  curNode = rotateRight(curNode);
            if(key.compareTo(curNode.key) == 0 && (curNode.right == null))
                return null;
            if(!isRed(curNode.right) && !isRed(curNode.right.left))
                curNode = moveRedRight(curNode);
            if(key.compareTo(curNode.key) == 0){
                Node temp = min(curNode.right);
                curNode.key = temp.key;
                curNode.value = temp.value;
                curNode.right = deleteMin(curNode.right);
            }
            else curNode.right = delete(curNode.right, key);
        }
        return balance(curNode);
    }


    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BinarySearchTree underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    public String min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        // assert x != null;
        if (x.left == null) return x;
        else                return min(x.left);
    }


    public String max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) {
        // assert x != null;
        if (x.right == null) return x;
        else                 return max(x.right);
    }

    /**
     * 删除以给定结点为根节点的红黑树中键最大的结点
     * @param h
     * @return
     */
    private Node deleteMax(Node h) {
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.

    /**
     * 假设结点h是红色，h.right和h.right.left是黑色
     * 将h.right或者它的一个子节点变红
     * @param h
     * @return
     */
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant

    /**
     * 恢复红黑树的平衡性
     * @param h
     * @return
     */
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * 将一个左连接旋转为右链接
     * @param h
     * @return
     */
    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size  = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * 将一个右链接旋转为左连接
     * @param h
     * @return
     */
    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    public static void main(String[] args) {
        RedBlackBST bst = new RedBlackBST();

        String[] keys = {"0", "1","2", "3","4","5","6"};
        String[] vals = {"a", "b","c","d","e","f","g"};

        bst.put("0","a");
        bst.put("1","b");
        bst.put("2","c");
        bst.put("3","d");
        bst.put("4","e");
        bst.put("5","f");
        bst.put("6","g");

        System.out.println(bst.min());
        System.out.println(bst.max());

        bst.deleteMin();
        bst.deleteMax();

        System.out.println();
        for(String key : keys){
            System.out.println(bst.get(key));
        }
    }
}

































