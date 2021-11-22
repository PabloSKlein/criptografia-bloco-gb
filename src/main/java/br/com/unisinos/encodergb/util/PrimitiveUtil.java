package br.com.unisinos.encodergb.util;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveUtil {
    public static Stream<Byte> primitiveArrayToObjectStream(byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> bytes[i]);
    }

    public static Stream<Character> primitiveArrayToObjectStream(char[] chars) {
        return IntStream.range(0, chars.length)
                .mapToObj(i -> chars[i]);
    }

    public static Stream<Boolean> primitiveArrayToObjectStream(boolean[] chars) {
        return IntStream.range(0, chars.length)
                .mapToObj(i -> chars[i]);
    }
}
