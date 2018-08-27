package zzwalgs4.图;


import zzwalgs4.基础.Queue;

/**
 * author: zzw5005
 * date: 2018/8/26 20:10
 */

/*
* 使用广度优先搜索查找图中的路径(可以解决单点最短路径)
* 它使用了一个队列来保存所有已经被标注过单其邻接表还未被检查过的顶点。先将起点加入队列，然后重复以下步骤直到队列为空:
* 1.取队列中的下一个顶点v，并标记它;
* 2.将于相邻的所有未被标记过的顶点加入队列
* 它的结果是一个edgeTo[]，一棵用父连接表示的根节点为s的树。它表示了s到与s连通的顶点的最短路径。
* */
public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;   //到达该顶点的最短路径是否已知
    private int[] edgeTo;       //到达该定点的已知路径上的最后一个顶点
    private int[] distTo;       //distTp[v]=最短路径s-v路径上的前一条边
    private final int s;        //起点

    public BreadthFirstPaths(Graph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s){
        //在广度搜索中，需要按照与起点的距离顺序来遍历所有的顶点，所以这里使用于栈的存取顺序相反的队列结构
        Queue<Integer> q = new Queue<>();
        for(int v = 0; v < G.V(); v++){    //初始化distTo[]数组，将元素全部设置为int的最大值
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;                  //标记起点
        q.enqueue(s);                      //将起点加入队列

        while(!q.isEmpty()){
            int v = q.dequeue();           //从队列中删除上一个顶点，然后把它的邻接顶点全部压入队列中
            for(int w : G.adj(v)){
                if(!marked[w]){            //对于每个未被标记的相邻顶点
                    edgeTo[w] = v;         //保存最短路径的最后一条边，每个相邻顶点的edgeTo[]值都是它的上一个结点
                    distTo[w] = distTo[v] + 1;   //这个是计算，起点到与起点相通的某一个顶点的距离
                    marked[w] = true;      //标记它，因为最短路径已知
                    q.enqueue(w);          //并将它添加到队列当中
                }
            }
        }
    }

    private void bfs(Graph G, Iterable<Integer> sources){
        Queue<Integer> q = new Queue<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * 检查单一来源的最佳条件
     * @param G
     * @param s
     * @return
     */
    private boolean check(Graph G, int s){
        if(distTo[s] != 0){
            System.out.println("distance of source " + s + " to itself = " + distTo[s]);
            return false;
        }

        for(int v = 0; v < G.V(); v++){
            for(int w : G.adj(v)){
                if(hasPathTo(v) != hasPathTo(w)){
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if(hasPathTo(v) && (distTo[w] > distTo[v] + 1)){
                    System.out.println("edge " + v + "-" + w);
                    System.out.println("distTo[" + v + "] = " + distTo[v]);
                    System.out.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }

        for(int w = 0; w < G.V(); w++){
            if(!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if(distTo[w] != distTo[v]+1){
                System.out.println("shortest path edge " + v + "-" + w);
                System.out.println("distTo[" + v + "] = " + distTo[v]);
                System.out.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return true;
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return marked[v];
    }

    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
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
        // System.out.println(G);

        int s = 0;
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                System.out.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) System.out.print(x);
                    else        System.out.print("-" + x);
                }
                System.out.println();
            }

            else {
                System.out.printf("%d to %d (-):  not connected\n", s, v);
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