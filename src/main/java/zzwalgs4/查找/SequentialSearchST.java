package zzwalgs4.查找;

import java.util.Queue;

/**
 * author: zzw5005
 * date: 2018/8/16 21:31
 */

/*
* 基于无序链表的顺序查找(链表的每个结点都是一个键值对，从头开始遍历链表，如果找到给定的键，就返回该键对应的值)
* */
public class SequentialSearchST {
    private static int N;
    private static Node head;
    
    public SequentialSearchST(){}
    
    private static String get(String key){
        if(key == null) throw new IllegalArgumentException("argument to get() is null"); 
        for(Node curNode = head; curNode != null; curNode = curNode.next){
            if(key.equals(curNode.key)){
                return curNode.val;
            }
        }
        return null;
    }
    
    private static void put(String key, String val){
        if(key == null) throw new IllegalArgumentException("head argument to put() is null");
        if(val == null){
            delete(key);
            return;
        }
        
        for(Node curNode = head; curNode != null; curNode = curNode.next){
            if(key.equals(curNode.key)){
                curNode.val = val;
                return;
            }
        }
        
        head = new Node(key, val, head);
        N++;
    } 
    
    private static void delete(String key){
        if(key == null)throw new IllegalArgumentException("argument to delete() is null");
        head = delete(head, key);
    }
    
    private static Node delete(Node head, String key){
        if(head == null) return null;
        if(key.equals(head.key)){
            N--;
            return head.next;
        }
        head.next = delete(head.next, key);
        return head;
    }
    
    private static int size(){
        return N;
    }
    
    private static boolean isEmpty(){
        return size() == 0;
    }
    
    private static class Node{
        private String key;
        private String val;
        private Node next;
        
        public Node(String key, String val, Node next){
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }



    public static void main(String[] args) {
        String[] keys = {"0", "1","2", "3","4","5","6"};
        String[] vals = {"a", "b","c","d","e","f","g"};

        SequentialSearchST sst = new SequentialSearchST();
        System.out.println("---------------------------------------------");
        sst.put(keys[0],"a");
        sst.put("1","b");
        sst.put("2","c");
        sst.put("3","d");
        sst.put("4","e");
        sst.put("5","f");
        sst.put("6","g");


        System.out.println(sst.get("3"));
        System.out.println(sst.get("5"));


        sst.delete("2");

        System.out.println("---------------------------------------------");
        System.out.println(sst.toString());

        for(String str : keys){
            System.out.println(str + "  " + sst.get(str));
        }

        System.out.println("---------------------------------------------");

    }
    
    
}
