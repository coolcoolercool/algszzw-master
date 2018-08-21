package zzwalgs4.查找.java数据结构与算法;

/**
 * author: zzw5005
 * date: 2018/8/17 19:20
 */


public class BinarySearchTree {

    private BinaryNode root;

    private static class BinaryNode{
        BinaryNode(String val){
            this(val, null, null);   //todo
        }

        BinaryNode(String val, BinaryNode leftChild, BinaryNode rightChild){
            value = val;
            left = leftChild;
            right = rightChild;
        }

        String value;
        BinaryNode left;
        BinaryNode right;
    }

    public BinarySearchTree(){
        root = null;
    }

    public void makeEmpty(){
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(String val){
        return contains(val, root);
    }

    private boolean contains(String val, BinaryNode curNode){
        if(curNode == null) return false;

        int compareResult = val.compareTo(curNode.value);

        //二分查找
        if(compareResult < 0){
            return contains(val, curNode.left);
        }else if(compareResult > 0){
            return contains(val, curNode.right);
        }else{
            return true;              //配置成功
        }
    }

    public String findMin(){
        if(isEmpty()) throw new IllegalArgumentException("argument to BST is null");
        return findMin(root).value;
    }

    private BinaryNode findMin(BinaryNode curNode){
        if(curNode == null) return null;
        else if(curNode.left == null) return curNode;
        return findMin(curNode.left);
    }

    public String findMax(){
        if(isEmpty()) throw new IllegalArgumentException("argument to BST is null");
        return findMax(root).value;
    }

    private BinaryNode findMax(BinaryNode curNode){
        if(curNode != null){
            while(curNode.right != null){
                curNode = curNode.right;
            }
        }
        return curNode;
    }

    public void insert(String val){
        root = insert(val, root);
    }

    private BinaryNode insert(String val, BinaryNode curNode){
        if(curNode == null) return new BinaryNode(val, null, null);

        int compareResult = val.compareTo(curNode.value);

        if(compareResult < 0){
            curNode.left = insert(val, curNode.left);
        }else if(compareResult > 0){
            curNode.right = insert(val, curNode.right);
        }else{
        }
        return curNode;
    }

    public void remove(String val){
        root = remove(val, root);
    }

    private BinaryNode remove(String val, BinaryNode curNode){
        if(curNode == null) return curNode;

        int compareResult = val.compareTo(curNode.value);

        if(compareResult < 0){
            curNode.left = remove(val, curNode.left);
        }else if(compareResult > 0){
            curNode.right = remove(val, curNode.right);
        }else if(curNode.left != null && curNode.right != null){
            curNode.value = findMin(curNode.right).value;
            curNode.right = remove(curNode.value, curNode.right);
        } else{
            curNode = (curNode.left != null) ? curNode.left : curNode.right;
        }
        return curNode;
    }

    public void printTree(){
        if(isEmpty()){
            System.out.println("empty Tree");
        }else{
            printTree(root);
        }
    }

    private void printTree(BinaryNode curNode){
        if(curNode != null){
            printTree(curNode.left);
            System.out.print(curNode.value + " ");
            printTree(curNode.right);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert("x");
        bst.insert("k");
        bst.insert("d");
        bst.insert("c");
        bst.insert("a");
        bst.insert("f");
        bst.insert("b");
        bst.insert("y");
        bst.insert("e");

        System.out.println(bst.findMin());
        System.out.println(bst.findMax());

        System.out.println("-------------------------------");

        System.out.println(bst.contains("a"));
        System.out.println(bst.contains("v"));

        bst.printTree();

        bst.remove("a");
        bst.remove("d");

        System.out.println("\n");

        bst.printTree();



    }


}
























