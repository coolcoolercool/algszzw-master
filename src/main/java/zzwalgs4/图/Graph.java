package zzwalgs4.图;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * author: zzw5005
 * date: 2018/8/26 9:29
 */

/*
* 这份Graph的实现使用了一个由顶点索引的整型链表数组。每条边都会出现两次，即当存在一条链接v与w的边时候，
* w会出现在v的链表中，v也会出现w的链表中。第二个构造函数，是从输入流中读取一幅图，开头是V(图中点的数目),
* 然后是E(途中边的数目)，再然后是一列整数对，大小在0到V-1之间。
* */
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;               //顶点数目
    private int E;                     //边的数目
    private Bag<Integer>[] adj;        //邻接表


    //初始化图，将图初始化为只有给的给定数目的顶点，没有边
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * 根据输入的数据，将图进行构造，连接顶点构成边
     * @param in
     */
    public Graph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }


    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    /**
     * 在图中添加一条无向边v-w
     * @param v 边的一个顶点
     * @param w 边的另一个顶点
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);        //将w添加到v的链表中
        adj[w].add(v);        //将v添加到w的链表中
    }

    /**
     * 返回图中顶点的数目
     * @return
     */
    public int V(){
        return V;
    }

    /**
     * 返回图中边的数目
     */
    public int E(){
        return E;
    }

    //判断输入的顶点是否有效
    private void validateVertex(int v){
        if(v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * 返回与给定顶点相邻的顶点
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
    }

    /**
     * 返回途中一个顶点的度数
     * @param v
     * @return
     */
    public int degree(int v){
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 返回此图的字符串表示形式
     * @return
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for(int v = 0; v < V; v++){
            s.append(v + ": ");
            for(int w : adj[v]){
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In();
        Graph G = new Graph(in);
        System.out.println(G);
    }

}

/*
* 数据 在控制台输入即可
13
13
0 5
4 3
0 1
9 12
6 4
5 4
0 2
11 12
9 10
0 6
7 8
9 11
5 3
* */
