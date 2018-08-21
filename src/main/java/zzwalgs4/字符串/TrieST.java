package zzwalgs4.字符串;

/**
 * author: zzw5005
 * date: 2018/8/19 21:52
 */

/*
* 单词查找树
* 单词查找树的链表结构与键的插入或者删除的顺序无关:对于任意给定的一组键，期单词查找树都是唯一的
* 字母表的大小为R，在一棵由N个随机键构造的单词查找树，未命中查找平均所有需要的减产的结点数量为 log(R)N
* 一棵单词查找树中的链接总数在RN到RNw之间，其中w为键的平均长度
* */
public class TrieST {

    private static int R = 256;
    private Node root;
    private int number;          //字符查找树种的键的数量

    /**
     * 初始化一个空的字符查找树
     */
    public TrieST() {
    }

    /**
     * 每一个结点都包含一个链接数组和一个值
     * 每个结点都含有一个相应的值，可以是空也可以是符号表中某个键所关联的值
     * 其实就是将每个键所关联的值保存在该键的最后一个字母所对应的结点中。
     * 值为空的结点在符号表中没有对应的键，它们的存在是为了简化单词查找数中的查找操作
     * curNode.next[element] 就是当前结点中的element元素指向下一个节点某个元素的所有链接
     */
    private static class Node{
        //因为java不支持泛型数组，所以Node中的值必须为Object，可以在get方法中进行类型转换
        private Object val;
        private Node[] next = new Node[R];
    }

    /**
     * 返回给定键关联的值
     * @param key 给定的键
     * @return
     */
    public String get(String key){
        if(key == null) throw new IllegalArgumentException("argument tp get() is null");
        Node curNode = get(root, key, 0);
        if(curNode == null) return null;
        return (String) curNode.val;
    }

    /**
     * 单词查找树中的每个结点都包含了下一个可能出现的所有字符的链接。从根节点开始，
     * 首先经过的是键的首字母所对应的链接; 在下一个结点中沿着第二个字符所对应的链接继续前进;
     * 在第二个结点中沿着第三个字符所对应的链接向前，如此这般直达到之间最后一个字母所指向的结点或是遇到一个空连接
     * 可能会出现以下三种情况:
     * 1.键的最后一个字符所对应的结点中的值非空。则说明这是一次成功的查找，键所对应的值就是键的最后一个字符所对应的结点中保存的值
     * 2.键的最后一个字符所对应的结点中的值为空，则说明这是一次失败的查找，尽管我们查找到这个键，但是这个键我们没有存入过，
     *   也就是符号表中不存在这个键
     * 3.查找结束于一条空连接。这也是一次失败的查找。这种失败很容易理解，因为我们根本找不到这个键
     * @param curNode 根节点
     * @param key     需要查找的键
     * @param d       与键相关的值
     * @return
     */
    private Node get(Node curNode, String key, int d){
        //如果当前结点为空，则返回空
        if(curNode == null) return null;
        if(d == key.length()) return curNode;
        char c = key.charAt(d);
        return get(curNode.next[c], key, d+1);
    }

    /**
     * 向字符查找树中插入一个键和它对应的值
     * @param key
     * @param val
     */
    public void put(String key, String val){
        root = put(root, key, val, 0);
    }

    /**
     * 在插入之前首先要进行一次查找，在单词查找树中意味着沿着被查找的键的所有字符达到树中表示最后一个字符的结点或者一个空连接
     * 可能会出现以下两种情况:
     * 1.在到达键的最后一个字符之前就遇到了一个空连接。这种情况，字符查找树中不存在于键的最后一个字符对应的结点，
     *   因此只需要接着为键中还未被检查的每个字符创建一个对应的结点并将键的值保存到最后一个字符的结点中
     * 2.在遇到空连接之前就到达了键的最后一个字符。在这种情况下，键该结点的值设为键所对应的的值(如果该节点已经有值，则直接覆盖它，
     *   这样就能使得我们每次查找的键都是唯一的，只要是该节点对应的值是非空，即可成功查找)
     * @param curNode  当前结点
     * @param key      需要插入的键
     * @param val      键所对应的值
     * @param d        0到length
     * @return
     */
    private Node put(Node curNode, String key, String val, int d){
        //第一种情况
        if(curNode == null) curNode = new Node();

        //第二种情况:这里为什么是key.length()，因为根节点不存储任何键的字符
        if(d == key.length()){
            if(curNode.val == null) number++;
            curNode.val = val;
            return curNode;
        }

        //d的值初始为0，下面的递归是依次处理键的所有字符
        char c = key.charAt(d);

        curNode.next[c] = put(curNode.next[c], key, val ,d+1);
        return curNode;
    }

    /**
     * 这里前缀是""，其实列出所有的队列，即返回所有存入的单词
     * @return
     */
    public Iterable<String> keys(){
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre){
        //这里使用的队列，是自己实现的，java中的队列并没有enqueue的方法
        Queue<String> results = new Queue<>();

        Node curNode = get(root, pre, 0);
        //因为这里需要频繁的增删字符串是，所以使用了StringBuilder来代替String
        collect(curNode, new StringBuilder(pre), results);
        return results;
    }

    /**
     * 字符和键是被隐式地表示在单词查找树中，我们需要在数据结构中找到这些键，还需要显式的表示它们
     * 这里我们使用collect()方法来完成这些功能。它维护了一个字符串用来保存从根节点出发的路径上一系列的字符串。
     * @param curNode 该节点
     * @param pre     与该节点相关联的字符串(从根节点到该节点的路径上的所有字符)
     * @param q
     */
    private void collect(Node curNode, StringBuilder pre, Queue<String> q){
        if(curNode == null) return;
        /*
        * 访问当前结点的时候，如果它的值非空，就将和它关联的字符加入队列当中
        * 这里enqueue方法就是将pre.toString()字符串添加到队列当中
        * */
        if(curNode.val != null) q.enqueue(pre.toString());

        /*
        * 递归地访问它的链接数组所指向所有可能的字符结点
        * 循环+递归，将该结点的所有可能链接都找出来
        * */
        for(char c = 0; c < R; c++){
            pre.append(c);
            collect(curNode.next[c], pre, q);
            //这一步其实是回退一个结点，如果当前下的结点的链接已经查找完毕，则回退到上一个结点
            //进行上一个递归的查找
            pre.deleteCharAt(pre.length()-1);
        }
    }

    public int size() {
        return number;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 返回字符查找树中与输入字符匹配的所有键
     * 需要为collect()方法添加一个参数来指定匹配的模式。
     * 如果模式中含有通配符，就需要用递归调用处理所有的链接，否则只需要处理模式中指定字符的链接即可
     * @param pattern
     * @return
     */
    public Iterable<String> keysThatMatch(String pattern){
        Queue<String> results = new Queue<>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    private void collect(Node curNode, StringBuilder prefix, String pattern, Queue<String> results){
        if(curNode == null) return;
        //d为前缀的长度
        int d = prefix.length();

        if(d == pattern.length() && curNode != null){
            results.enqueue(prefix.toString());
        }

        if(d == pattern.length()) return;

        char c = pattern.charAt(d);

        if(c == '.'){
            for(char ch = 0; ch<R; ch++){
                prefix.append(ch);
                collect(curNode.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length()-1);
            }
        }else{
            prefix.append(c);
            collect(curNode.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    /**
     * 返回给定字符串最长键前缀，如果没有这样的字符串，则返回null
     * @param query
     * @return
     */
    public String longestPrefixOf(String query){
        if(query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if(length == -1) return null;
        else return query.substring(0, length);
    }

    /**
     * 返回subtrie的最长字符串键的长度
     * 以curNode为根，它是查询字符串的前缀，假设第一个d字符匹配，我们已经找到了给定长度的前缀匹配
     * 如果没有这样的匹配，则返回-1
     * @param curNode
     * @param query
     * @param d
     * @param length
     * @return
     */
    private int longestPrefixOf(Node curNode, String query, int d, int length){
        if(curNode == null) return length;
        if(curNode.val != null) length = d;
        if(d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(curNode.next[c], query, d+1, length);
    }

    /**
     * 根据给定的键，从字符查找树种删除这个键，并且删除和它相关联的值
     * @param key
     */
    public void delete(String key){
        root = delete(root, key, 0);
    }

    /**
     * 第一步:找到键所对应的结点并将它的值设置为null，
     * 第二步: 如果该结点含有一个非空的链接指向某个子节点，那么就不需要再进行其他操作了;
     * 如果它的所有链接均为空，那就需要从数据结构中删除这个结点，如果删除这个结点，
     * 使得它的父节点的所有链接也均为空，就需要继续删除它的父节点，以此类推
     * @param curNode
     * @param key
     * @param d
     * @return
     */
    private Node delete(Node curNode, String key, int d){
        if(curNode == null) return null;

        //找到键所对应的结点并将它的值设置为null
        if(d == key.length()) curNode.val = null;
        else{
            char c = key.charAt(d);
            curNode.next[c] = delete(curNode.next[c], key, d+1);
        }

        if(curNode.val != null) return curNode;

        for(char c = 0; c < R; c++){
            if(curNode.next[c] != null)
                return curNode;
        }
        return null;
    }

    public static void main(String[] args) {
        TrieST st = new TrieST();
        String[] keys = {"she","sells","sea","shells","by","the","sea","shore"};
        for(int i = 0; i < keys.length; i++){
            st.put(keys[i], i+"");
        }

        // print results
        if (st.size() < 100) {
            System.out.println("keys元素排序如下(\"\"):");
            for (String key : st.keys()) {
                System.out.println(key + " " + st.get(key));
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("longestPrefixOf(\"shellsort\"):");
        System.out.println(st.longestPrefixOf("shellsort"));
        System.out.println();

        System.out.println("longestPrefixOf(\"quicksort\"):");
        System.out.println(st.longestPrefixOf("quicksort"));
        System.out.println();

        System.out.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            System.out.println(s);
        System.out.println();

        System.out.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            System.out.println(s);

        st.delete("the");
        st.delete("sells");

        System.out.println("\n");

        if (st.size() < 100) {
            System.out.println("keys元素排序如下(\"\"):");
            for (String key : st.keys()) {
                System.out.println(key + " " + st.get(key));
            }
            System.out.println();
        }
    }
}
