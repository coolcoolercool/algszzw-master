package zzwalgs4.图;

import java.util.NoSuchElementException;

/**
 * author: zzw5005
 * date: 2018/8/27 9:57
 */


public class Digraph {
    private static final String NEWLINE = System.getProperty("line.separator");


    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    private int[] indegree;

    /**
     * 初始化图，将图构造成一个只有顶点没有边的图
     * @param V
     */
    public Digraph(int V){
        this.V = V;
        this.E = 0;
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];

        for(int v = 0; v < V; v++){
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * 根据出入的数据构造图
     * @param in
     */
    public Digraph(In in){
        try {
            this.V = in.readInt();
            if(V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");

            //每个顶点的入度
            indegree = new int[V];
            adj = (Bag<Integer>[]) new Bag[V];

            for(int v = 0; v < V; v++){
                adj[v] = new Bag<>();
            }

            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of edges in a Digraph must be nonnegative");

            for(int i = 0; i < E; i++){
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }

    }

    public int V() {return V;}
    public int E() {return E;}

    /**
     * 根据给定的顶点添加一条有向边
     * 这个有向体现在，他只添加了一条边，就是从v指向w的一条边，而不是无向图中添加了两次，v与w必须双方都在对方的
     * 链表中。有向图只需要w在v的链表中即可。
     * @param v
     * @param w
     */
    public void addEdge(int v ,int w){
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public Digraph reverse(){
        Digraph R = new Digraph(V);
        for(int v = 0; v < V; v++){
            for(int w : adj(v))
                R.addEdge(w, v);
        }
        return R;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In();
        Digraph G = new Digraph(in);
        //有向图的反向图
        Digraph R = G.reverse();
        System.out.println(G);

        System.out.println("\n");
        System.out.println(R);
    }
}


/*
输入图的数据
13
22
 4 2
 2 3
 3 2
 6 0
 0 1
 2 0
11 12
12 9
 9 10
 9 11
 8 9
10 12
11 4
 4 3
 3 5
 7 8
 8 7
 5 4
 0 5
 6 4
 6 9
 7 6
* */