package zzwalgs4.图;

/**
 * author: zzw5005
 * date: 2018/8/26 11:15
 */

/*
* 深度优先搜索(DFS)。他使用了boolean数组来记录和起点相连通的所有顶点(包括它自己)。
* 递归方法会标注给定的顶点来访问该定点列表中所有没有被标记过的顶点。如果图是连通的，
* 每个邻接链表中的元素都会被检查到
* */
public class DepthFirstSearch {
    private boolean[] marked;
    private int count;


    /**
     * 给定的顶点为图的起点，返回所有与起点相连通的顶点
     * @param G
     * @param s
     */
    public DepthFirstSearch(Graph G, int s){
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    private void dfs(Graph G, int v){
        count++;
        //标记此顶点已经被遍历过
        marked[v] = true;
        //遍历该顶点的所有邻接顶点
        for(int w : G.adj(v)){
            if(!marked[w]){
                //对于每个邻接顶点都是用递归遍历它的邻接顶点
                dfs(G, w);
            }
        }
    }

    /**
     * 如果一个顶点已经被遍历过，则标记为true。表示此顶点与给定的顶点是连通的
     * @param v
     * @return
     */
    public boolean marked(int v){
        validateVertex(v);
        return marked[v];
    }

    /**
     * 返回已经遍历的顶点的数目
     * @return
     */
    public int count(){
        return count;
    }

    private void validateVertex(int v){
        int V = marked.length;
        if(v < 0 || v >= V){
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }

    public static void main(String[] args){
        In in = new In();
        Graph G = new Graph(in);
        int s = 0;
        DepthFirstSearch search = new DepthFirstSearch(G, s);

        //打印给定图中的某个结点的所有邻接顶点
        for(int v = 0; v < G.V(); v++){
            if(search.marked(v)){
                System.out.print(v + " ");
            }
        }

        System.out.println();

        //如果所有的邻接顶点的数目小于图中的顶点的数目，则表示这个图不是一个连接图
        if(search.count() != G.V()) System.out.println("Not connected");
        else                        System.out.println("connected");
    }
}
