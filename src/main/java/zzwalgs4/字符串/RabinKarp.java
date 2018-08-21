package zzwalgs4.字符串;

import java.math.BigInteger;
import java.util.Random;

/**
 * author: zzw5005
 * date: 2018/8/21 11:14
 */

/*
* RabinKarp算法
* 核心思想:
* RabinKarp算法的基础是对所有位置i，该校计算文本中i+1位置的子字符串的散列值。
* 由以下数学公式推导可得。t_(i)表示txt.charAt(i)，那么文本txt中起始于位置i的含有M个字符串的子字符串所对应的数即为
* 这里M设置为6
* x_(i)   = t_(i)*R^(M-1) + t_(i+1)*R^(M-2) + t_(i+2)*R^(M-3) + t_(i+3)*R^(M-4) + t_(i+4)*R^(M-5) + t_(i+M-1)*R^(0)
* x_(i+1) = t_(i+1)*R^(M-1) + t_(i+2)*R^(M-2) + t_(i+3)*R^(M-3) + t_(i+4)*R^(M-4) + t_(i+5)*R^(M-5) + t_(i+M)*R^(0)
* 则x_(i+1) = ( x_(i) - t_(i)R^(M-1) )R + t_(i+M)
* 模式字符串的右移一位的散列值，等于没有移动之前的散列值减去第一个数字的值，乘以R，再加上最后一个数字的值
*
* 而且不需要保存这些数的值，而只需要保存它们除以q以后的余数。
* 取余操作的一个基本的性质是如果在每次计算操作以后都将结果处以q并取余，等价于在完成了所有计算操作以后再将最后的结果除以q并取余
* 以上的计算散列值就可以在常熟时间内高效地不窜向右一个一个地移动
* */
public class RabinKarp {
    private long patHash; //模式字符串的散列值
    private int m;        //模式字符串的长度
    private long q;       //一个很大的素数
    private int R = 256;  //字母表的大小 ASCII的取值范围 0到255
    private long RM;      //R^(m-1) % q

    public RabinKarp(String pat){
        this.m = pat.length();
        q = longRandomPrime();         //一个很大随机的素数

        RM = 1;
        for(int i=1; i <= m-1; i++){   //计算R^(m-1) % q
            RM = (R * RM) % q;         //用于减去第一个数字时候的计算
        }
        //计算pat模式字符串的hash值
        patHash = hash(pat,m);
    }


    // a random 31-bit prime
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    // Compute hash for key[0..m-1].

    /**
     * 计算key[0,...,M-1]的散列值，可以计算模式字符串的散列值，也可以计算文本字符串的字符串的散列值
     * Horner方法，用于保留余数计算散列值
     * @param key
     * @param m
     * @return
     */
    private long hash(String key, int m) {
        long h = 0;
        for (int j = 0; j < m; j++)
            //对于传入字符串的每一位数字，将散列值乘以R，加上这个数字，处以q并取其余数，
            //这里字符会自动转换成int类型计算计算
            h = (R * h + key.charAt(j)) % q;
        return h;
    }

    public boolean check(int i){  //蒙特卡洛算法，一直都是返回true
        return true;
    }

    /**
     * 返回模式字符串在文本字符串中第一次出现的索引，如果不存在则返回文本字符串的长度
     * @param txt
     * @return
     */
    private int search(String txt){
        int n = txt.length();
        //计算文本字符串的子字符串的hash值
        long txtHash = hash(txt, m);

        //检查偏移量为0的匹配情况
        if(patHash == txtHash && check((0))) return 0;  //一开始就匹配成功

        //检查哈希是是否匹配，如果匹配，则检查完全匹配
        for(int i= m; i < n; i++){
            /*
            * 减去第一个数字，加上最后一个数字，再次检查匹配
            * 这里运用了该算法的核心公式 x_(i+1) = ( x_(i) - t_(i)R^(M-1) )R + t_(i+M)
            * txt.charAt(i) 等于 t_(i+m), txt.charAt(i-m) 等于 t_(i)
            * 相比于原来的递推公式中 多了一个部分，t_(i)*q*R 虽然说对这个部分直接求余会是0，对结果好像没有扫描干扰
            * 但是如果直接去掉，结果就会不正确
            * 这个额外加上的q的部分，是为了保证所有的数都为正，这样取余操作才能够得到预期结果
            * */
            /*txtHash = (txtHash + q - RM * txt.charAt(i-m) % q) % q;

            txtHash = (txtHash * R + txt.charAt(i) ) % q;*/

            txtHash = ((txtHash + txt.charAt(i-m) * (q - RM)) * R + txt.charAt(i)) % q;
            if(patHash == txtHash){
                if(check(i - m + 1))
                    return i - m + 1;                  //找到匹配
            }
        }
        return n;                                      //未找到匹配
    }

    public static void main(String[] args) {
        String pat = "26535";
        String txt = "3141592653589793";

        RabinKarp searcher = new RabinKarp(pat);
        int offset = searcher.search(txt);

        // print results
        System.out.println("text:    " + txt);

        // from brute force search method 1
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++)
            System.out.print(" ");
        System.out.println(pat);
    }
}
