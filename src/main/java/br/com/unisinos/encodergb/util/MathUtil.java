package br.com.unisinos.encodergb.util;

public class MathUtil {
    //https://www.geeksforgeeks.org/how-to-calculate-log-base-2-of-an-integer-in-java/
    public static int log2(int N) {
        return (int) (Math.log(N) / Math.log(2));
    }
}
