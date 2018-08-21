package zzwalgs4.字符串;

/**
 * author: zzw5005
 * date: 2018/8/21 9:27
 */

/*
* BoyerMoore字符串查找算法
* 核心思想:首先文本字符串需要支持回退(类似与缓存)。在查找算法的实现的构造函数根据模式字符串构造了一张每个字符在模式中出现的最右位置
* 的表格。查找算法会从右向左扫描模式字符串，并在匹配失败的时候通过跳跃，将文本中的字符和它在模式字符串中出现的最右位置对齐
* */
public class BoyerMoore {
    private final int R; //基数
    private int[] right;

    private String pat;  //模式字符串



    /**
     * 预处理模式字符串
     * @param pat
     */
    public BoyerMoore(String pat){
        this.R = 256;
        this.pat = pat;

        //模式字符串最右边出现c的位置，如果字符在模式字符串中不存在，则表示-1
        right = new int[R];
        //将right[]数组初始化，将素有元素的值都设为-1
        for(int c = 0; c < R; c++){
            right[c] = -1;
        }
        //对于0到M-1的j，right[pat.charAt(j)]设为j，
        //这样即使有相同的字符，最后也会被在最靠右的相同字符覆盖为最右边的位置
        for(int j = 0; j < pat.length(); j++){
            right[pat.charAt(j)] = j;
        }
    }

    /**
     * 返回模式字符串在文本字符串中的第一次出现的索引，如果不存在，则返回文本的字符串的长度
     * 这里详细描述内循环中匹配失败出现的三种的情况:
     * 1.如果造成匹配失败的字符串不包含在模式字符串的中，将模式字符串向右移动j+1个位置(即i增加j+1);
     * 2.如果造成匹配失败的字符串包含在模式字符串中，那就可以使用right[]数组来讲模式字符串和文本对齐，
     * 使得该字符和它在模式中的与它无法匹配的字符(比它出现的最右位置更靠右的字符)重叠。
     * 3.如果这种方法无法增大i，那就直接讲i加1来保证模式字符串至少向右移动一个位置。
     * @param txt
     * @return
     */
    public int search(String txt){
        int m = pat.length();
        int n = txt.length();
        int skip;
        //索引i在文本字符串中从左向右移动
        for(int i=0; i <= n - m; i += skip){
            skip = 0;
            //用另一个索引j，在模式字符串中从右向左移动
            for(int j = m-1; j >= 0; j--){
                //内循环会检查文本字符串的第i+j的字符与模式字符串的第j个字符是否相等
                if(pat.charAt(j) != txt.charAt(i+j)){
                    /*
                    * 如果txt.charAt(i+j)在模式字符串中，right.[txt.charAt(i+j)]为这个字符在模式字符串中最靠右的位置，
                    * j指向当前模式字符串中比较的字符，如果这个字符不是在模式字符串与这个字符相同的字符中最靠右边的，
                    * 而right[txt.charAt(i+j)]是最靠右边的字符的位置，j-right[txt.charAt(i+j)]就会是一个负数，
                    * 这样还要对齐，就会导致i的值变小，回退，这种情况是不允许的。这就是第三种情况，启发式方法没有起到作用，
                    * 只能i自己加1，进行下一个文本字符串的字符的比较
                    * 如果txt.charAt(i+j)不在模式字符串中，
                    * 则right[txt.charAt(i+j)]=-1，j-(-1)=j+1,则i增加了skip=j+1
                     * */
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if(skip == 0) return i;
        }
        return n;
    }

    public static void main(String[] args) {
        String pat = "NEEDLE";
        String txt = "FINDINAHAYSTACKNEEDLE";

        BoyerMoore boyermoore1 = new BoyerMoore(pat);
        int offset1 = boyermoore1.search(txt);

        // print results
        System.out.println("text:    " + txt);

        System.out.print("pattern: ");
        for (int i = 0; i < offset1; i++)
            System.out.print(" ");
        System.out.println(pat);
    }
}
