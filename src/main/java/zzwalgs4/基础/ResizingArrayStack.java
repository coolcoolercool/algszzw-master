package zzwalgs4.基础;

import java.util.Iterator;

/**
 * author: zzw5005
 * date: 2018/8/21 16:55
 */

/*
* 可调整大小，后进先出的栈:(数组实现栈d)
* 可以进行压栈和弹栈操作，查看顶端元素，测试栈是否为空，可以迭代栈中的所有元素
* */
public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] a;       //栈元素
    private int n;          //栈元素的数量

    public ResizingArrayStack(){
        //在java中不允许创建泛型数组，所以这里使用类型转换，将数组类型转换为Item
        a = (Item[]) new Object[1];
        n = 0;
    }

    /**
     * 压栈，向栈中添加一个元素，放到栈顶(放到数组末尾)
     * @param item
     */
    public void push(Item item){
        if(n == a.length) resize(2*a.length);
        a[n++] = item;
    }

    /**
     * 弹栈，将栈顶元素弹出(最近添加的元素删除)
     * @return
     */
    public Item pop(){
        Item item = a[--n];
        a[n] = null;       //避免对象游离
        //当栈的大小小于数组大小的四分之一的时候，将数组大小减半
        if(n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }

    /**
     * 重新设置栈(数组)的容量
     * @param max
     */
    private void resize(int max){
        //将栈移动到一个大小为max的新数组中
        Item[] temp = (Item[]) new Object[max];
        for(int i=0; i < n; i++){
            temp[i] = a[i];
        }
        a = temp;
    }

    public int size(){
        return n;
    }

    public boolean isEmpty(){
        return n == 0;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterable();
    }

    /**
     * 迭代器，没有实现remove()方法
     * 作用:逆序迭代遍历数组
     */
    private class ReverseArrayIterable implements Iterator<Item>{
        private int i = n;
        public boolean hasNext(){
            return i > 0;
        }
        public Item next(){
            return a[--i];
        }
        public void remove(){}
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        String[] array = {"sea","love","peach","tree","apple","banana"};
        for(int i = 0; i < array.length; i++){
            stack.push(array[i]);
        }

        while(!stack.isEmpty())
            System.out.print(stack.pop() + " ");


    }
}
