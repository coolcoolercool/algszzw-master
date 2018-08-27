package zzwalgs4.图;

import zzwalgs4.StdOut;

/**
 * author: zzw5005
 * date: 2018/8/27 11:27
 */


public class DepthFirstDirectedPaths {
    private boolean[] marked;      //marked[v] = true,表示从起点s可以到达顶点v
    private int[] edgeTo;          //edgeTo[v]=从s到v的路径上的最后一条边
    private final int s;           //起点

    public DepthFirstDirectedPaths(Digraph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    /**
     * 根据给定的顶点为起点，遍历其与其连通的所有顶点，遍历顺序是遍历每一个顶点的邻接顶点
     * 计算从起点到其他的顶点的有向路径
     * @param G
     * @param v
     */
    private void dfs(Digraph G, int v){
        marked[v] = true;
        for(int w : G.adj(v)){
            if(!marked[w]){
                //每一个edgeTo[w]=v，w是v的邻接顶点，距离s更近。比如s起点的所有邻接顶点的edgeTo值都是s
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    /**
     * 返回从起点到顶点的有向路径，如果没有则返回null
     * @param v 图中的一个顶点v
     * @return 从起点出发的有向路径的顶点序列
     */
    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();

        /*
        * 输入一个有向从起点出发的有向路径中的一个顶点，从这个顶点逆着原来的路径一个一个顶点走回去，
        * 直到走到s的一个邻接顶点，停下，(edgeTo[s] 为null，不停下会抛出空指针异常)，最后
        * 再将起点s压入站内。等遍历输入的时候，就是从s到给定结点的正常有向路径到达的。
        * */
        for(int x = v; x != s; x = edgeTo[x]){
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        In in = new In();
        Digraph G = new Digraph(in);
        // StdOut.println(G);

        int s = 0;
        DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }
    }

}


/*
* 数据 在控制台输入即可
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
