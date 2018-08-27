package zzwalgs4.图;

import zzwalgs4.基础.Queue;

/**
 * author: zzw5005
 * date: 2018/8/27 9:03
 */

/*
* 使用深度优先所搜找出图中所有的连通分量
* 这里是基于一个由顶点索引的数组id[]。如果v属于第i个连通分量，则id[v]的值为i。构造函数会找出一个未被标记的顶点
* 并调用递归函数dfs()来标记并区分出所有和他联通的顶点，如此反复直到所有的顶点都被标记并区分
* */
public class CC {
    private boolean[] marked;
    private int[] id;
    private int count;

    public CC(Graph G){
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for(int s = 0; s < G.V(); s++){
            if(!marked[s]){           //每一次dfs都会找出与s起点连通的顶点，并标记。所以下一次if语句执行是与s不连通的顶点
                dfs(G, s);
                count++;
            }
        }
    }

    /**
     * 连通分量就是一个分支内的顶点都是互通的
     * 递归的找出与传入顶点连通的顶点，并标记已经遍历过，而且属于哪个连通分支
     * @param G
     * @param v
     */
    private void dfs(Graph G, int v){
        marked[v] = true;
        id[v] = count;                  //标记顶点v是属于第几个连通分量，
        for(int w : G.adj(v)){
            if(!marked[w]){
                dfs(G,w);
            }
        }
    }

    /**
     * 给定的两个顶点是否连通
     * @param v
     * @param w
     * @return
     */
    public boolean connected(int v, int w){
        return id[v] == id[w];         //如果在id中的值相等，则代表v和w是属于同一个连通分支，即是连通的
    }

    /**
     * 返回给定顶点属于第几个连通分支
     * @param v
     * @return
     */
    public int id(int v){
        return id[v];
    }

    /**
     * 返回图中的连通分支数量
     * @return
     */
    public int count(){
        return count;
    }

    public static void main(String[] args) {
        In in = new In();
        Graph G = new Graph(in);
        CC cc = new CC(G);

        // 连通分支的数量
        int m = cc.count();
        System.out.println(m + " components");

        //将每一个连通分支都设为一个队列，所有的连通分支成为一个队列数组
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        //将连通分量序号值相同的顶点放进都一个队列
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        //打印结果
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
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
