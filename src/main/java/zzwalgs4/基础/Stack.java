package zzwalgs4.基础;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * author: zzw5005
 * date: 2018/8/21 20:36
 */

/*
* 下压堆栈:(链表实现)
* 可调整大小，后进先出的栈
* */
public class Stack<Item> implements Iterable<Item>{
    private Node<Item> first;
    private int n;

    private static class Node<Item>{
        private Item item;
        private Node<Item> next;

    }

    public Stack(){
        first = null;
        n = 0;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void push(Item item){
        Node<Item> oldfirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Item pop(){
        if(isEmpty()) throw new NoSuchElementException("Stack underflow下溢");
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    public Item peek(){
        if(isEmpty()) throw new NoSuchElementException("Stack underflow下溢");
        return first.item;
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
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item>{
        private Node<Item> current;

        public ListIterator(Node<Item> first){
            current = first;
        }


        @Override
        public void remove() {

        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        String[] array = {"sea","love","peach","tree","apple","banana"};
        for(int i = 0; i < array.length; i++){
            stack.push(array[i]);
        }

        while(!stack.isEmpty())
            System.out.print(stack.pop() + " ");

    }
}
