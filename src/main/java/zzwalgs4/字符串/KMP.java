package zzwalgs4.字符串;

/**
 * author: zzw5005
 * date: 2018/8/20 17:23
 */

/*
*
*
* 用给定的模式字符串模拟KMP的变化:
* j=0: dfa[A][0] = 1
*      X = 0
*
* j=1; dfa[A][0] = 1           dfa[B][0] = 0
*            [1] = 1 ->  1     dfa[B][1] = 2
*      X = dfa[B][0] = 0
*
* j=2: dfa[A][0] = 1           dfa[B][0] = 0
*            [1] = 1                 [1] = 2
*            [2] = 1 -> 3            [2] = 0
*      X = dfa[A][0] = 1
*
* j=3: dfa[A][0] = 1           dfa[B][0] = 0
*            [1] = 1                 [1] = 2
*            [2] = 3                 [2] = 0
*            [3] = 1                 [4] = 2 -> 4
*      X = dfa[B][1] = 2
*
* j=4: dfa[A][0] = 1           dfa[B][0] = 0
*            [1] = 1                 [1] = 2
*            [2] = 3                 [2] = 0
*            [3] = 1                 [3] = 4
*            [4] = 3  -> 5           [4] = 0
*       X = dfa[A][2] = 3
*
* j=5: dfa[A][0] = 1            dfa[B][0] = 0       dfa[C][0] = 0
*            [1] = 1                  [1] = 2             [1] = 0
*            [2] = 3                  [2] = 0             [2] = 0
*            [3] = 1                  [3] = 4             [3] = 0
*            [4] = 5                  [4] = 0             [4] = 0
*            [5] = 1                  [5] = 4             [5] = 0 -> 6
*       X = dfa[C][0] = 0
* 核心思想:KMP字符串查找算法的构造函数根据模式字符串构造了一个确定有限状态的自动机，
* 再使用search方法在给定的文本字符串中查找模式字符串。
* 时间复杂度:对于长度为M的模式字符串和长度为N的文本字符串，KMP字符串查找算法访问的字符不会超过M+N个
*
* */
public class KMP {
    private String pattern;         //模式字符串
    private int[][] dfa;            //KMP自动机

    /**
     * 预处理模式字符串
     * @param pat
     */
    public KMP(String pat){
        //由模式字符串构造DFA
        this.pattern = pat;
        int M = pattern.length();
        int R = 68;                 //基数，ASCII的取值范围是0到255
        dfa = new int[R][M];

        /*
        * 下面的步骤都是在初始化dfa[][]状态机
        * */
        //当0状态遇到模式字符串的第一个字符的时候，转为1状态
        dfa[pattern.charAt(0)][0] = 1;

        for(int X = 0, j = 1; j < M; j++){
            //计算dfa[][j]
            for(int c = 0; c < R; c++){
                dfa[c][j] = dfa[c][X];            //复制匹配失败情况下的值
            }

            //将模式字符串从1到length-1的每个字符分别按照其位置索引+1的方式，依次设置状态
            //比如在匹配的过程，当第一个字符的下一个字符是模式字符串的第二个字符的时候，状态由1转到2

            //它由daf[pattern.charAt(j)][X] -> dfa[pattern.charAt(j)][j] = j+1
            dfa[pattern.charAt(j)][j] = j+1;      //设置匹配成功情况的值

            //X则保留了匹配的成功的之前的值，即daf[pattern.charAt(j)][X]
            X = dfa[pattern.charAt(j)][X];        //更新重启状态
        }
    }

    /**
     * 返回模式字符串在文本字符串中第一次出现的索引
     * 如果文本字符串中不存在模式字符串，则返回文本字符串的长度
     * @param txt 文本字符串
     * @return
     */
    public int search(String txt){
        //在txt上模拟DFA的运行
        int i, j;
        int N = txt.length(), M = pattern.length();
        /*
        * DFA在子字符串查找操作: 在查找任务的过程中，从文本的开头查找，起始状态为0.它停留在0状态扫描文本，
        * 直到找到一个匹配的时候，它会不断地匹配模式中的字符串与文本，自动机的状态会不断前进直到状态M。
        * 这个状态机是先看当前的字符，在根据转换状态的条件，进行状态转换。
        * */
        for(i = 0, j = 0; i < N && j < M; i++){
            j = dfa[txt.charAt(i)][j];
        }

        //如果j=M，则表示在文本字符串中匹配到了模式字符串的所有字符，匹配成功
        //如果未匹配成功，则返回N，文本字符串的长度
        if(j == M) return i-M;
        else       return N;
    }

    public static void main(String[] args) {
        String pat = "ABABAC";
        String txt = "BCBAABACAABABACAA";
        char[] pattern = pat.toCharArray();
        char[] text    = txt.toCharArray();

        //预处理
        KMP kmp1 = new KMP(pat);
        //进行查找匹配，返回第一次出现的索引
        int offset1 = kmp1.search(txt);

        //print results
        System.out.println("text:    " + txt);

          System.out.print("pattern: ");
        for (int i = 0; i < offset1; i++)
            System.out.print(" ");
        System.out.println(pat);


    }
}
