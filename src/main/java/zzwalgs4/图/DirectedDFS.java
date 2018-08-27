package zzwalgs4.图;

/**
 * author: zzw5005
 * date: 2018/8/27 10:46
 */

/*
* 有向图的可达性
* 单点可达性 可以解决:给定一幅图和一个起点s，问题: 是否存在一条从s到达给定顶点v的有向路径
* 多点可达性 可以解决:给定一幅图和顶点的集合，问题: 是否存在一条从集合中的任意顶点到达给定
* 顶点v的有向路径
* */
public class DirectedDFS {
    private boolean[] marked;   //如果源顶点中的顶点可以到达顶点v,则标记为marked[v] = true
    private int count;          //从源顶点可到达的顶点数

    public DirectedDFS(Digraph G, int s){
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    /**
     * 计算有向图中是否可以从起点到达给定的顶点
     * @param G
     * @param sources
     */
    public DirectedDFS(Digraph G, Iterable<Integer> sources){
        marked = new boolean[G.V()];
        //遍历集合中的顶点，再对每个顶点调用dfs函数，遍历其邻接顶点
        for(int s : sources){
            if(!marked[s]) dfs(G, s);
        }
    }

    private void dfs(Digraph G, int v){
        count++;
        marked[v] = true;
        for(int w : G.adj(v)){
            if(!marked[w]) dfs(G, w);
        }
    }

    public boolean marked(int v){
        return marked[v];
    }

    /**
     * 返回从源顶点可到达的顶点总数
     * @return
     */
    public int count(){
        return count;
    }

    public static void main(String[] args) {
        In in = new In();
        Digraph G = new Digraph(in);

        /*
        * 0,1,2,3,4,5,6,7,8,9,10,11,12
        * */
        Integer[] array = {1,2};
        Bag<Integer> sources = new Bag<Integer>();
        for (int i = 0; i < array.length; i++) {
            int s = array[i];
            sources.add(s);
        }

        // multiple-source reachability
        DirectedDFS dfs = new DirectedDFS(G, sources);

        // print out vertices reachable from sources
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) System.out.print(v + " ");
        }
        System.out.println();
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
