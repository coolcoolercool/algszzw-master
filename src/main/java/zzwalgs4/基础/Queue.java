package zzwalgs4.基础;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * author: zzw5005
 * date: 2018/8/21 21:01
 */

/*
* 先进先出队列:(链表实现)
* */
public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first;    //指向队列的开头，最早被添加的元素
    private Node<Item> last;     //指向队列的末尾，最近被添加的元素
    private int n;               //队列中元素的个数

    private static class Node<Item>{
        private Item item;
        private Node<Item> next;
    }

    public Queue(){
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return n;
    }

    public Item peek(){
        return first.item;
    }

    /**
     * 向队列中添加一个元素，放在栈顶位置
     * @param item
     */
    public void enqueue(Item item){
        Node<Item> oldfirst = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if(isEmpty()) first = last;
        else          oldfirst.next = last;
        n++;
    }

    /**
     * 从队列中返回并删除栈顶元素
     * @return
     */
    public Item dequeue(){
        Item item = first.item;
        first = first.next;
        n--;
        if(isEmpty()) last = null;
        return item;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Item item : this){
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterable<Item>(first);
    }

    private class ListIterable<Item> implements Iterator<Item>{
        private Node<Item> current;

        public ListIterable(Node<Item> first){
            current = first;
        }

        public boolean hasNext(){
            return current != null;
        }

        public void remove(){}

        public Item next(){
            if(!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<String>();
        String[] array = {"sea","love","peach","tree","apple","banana"};
        for(int i = 0; i < array.length; i++){
            queue.enqueue(array[i]);
        }

        while(!queue.isEmpty())
            System.out.print(queue.dequeue() + " ");
    }
}
