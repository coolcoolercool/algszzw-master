package zzwalgs4.图;



/**
 * author: zzw5005
 * date: 2018/8/26 15:17
 */

/*
* 使用深度优先搜索查找图中的路径
* 重点是edgeTo[]整型数组。这个数组可以找到从每个与s联通发的顶点回到s的路径，他会记录每个顶点到起点的路径，
* 而不是记录当前顶点到起点的路径。
* */
public class DepthFirstPaths {
    private boolean[] marked;    //这个顶点是否调用过dfs
    //从起点到一个顶点的已知路径上的最后一个顶点。edgeTo[w]=v 表示v-w从v到w第一次访问w时候经过的边。
    //edgeTo[]数组是一棵用父连接表示以s起点为跟且含有所有与s连通的顶点的树
    private int[] edgeTo;
    private final int s;         //起点


    /**
     * 构造函数接受一个起点s作为参数，计算与s连通的的每个顶点与s之间的路径。
     * @param G
     * @param s
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }
    /**
     * 从顶点v开始进行深度优先搜索
     * @param G
     * @param v
     */
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                //注意这里是将上一个结点的邻接结点作为起点，进行递归
                //每一个邻接edgeTo[邻接结点] = 起点 这样就会递归的构造一棵树
                dfs(G, w);
            }
        }
    }

    /**
     * 返回是否有一条从起点到给定结点的路径
     * @param v
     * @return
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 返回起点与和顶点之间的路径，如果没有，则返回null
     * @param v
     * @return
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        //这个栈是它自己是实现的，与jdk自带的栈有点不一样，如果替换，输出的结果不一样
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public static void main(String[] args) {
        In in = new In();
        Graph G = new Graph(in);
        int s = 0;
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                System.out.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) System.out.print(x);
                    else        System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d to %d:  not connected\n", s, v);
            }

        }
    }
}

/*
6
8
0 5
2 4
2 3
1 2
0 1
3 4
3 5
0 2
* */
