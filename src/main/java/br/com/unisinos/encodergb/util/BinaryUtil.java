package br.com.unisinos.encodergb.util;

import java.util.stream.Stream;

import static br.com.unisinos.encodergb.util.PrimitiveUtil.primitiveArrayToObjectStream;
import static java.util.stream.Collectors.joining;

public class BinaryUtil {
    public static Byte[] toBinaryCharArray(int number) {
        return primitiveArrayToObjectStream(Integer.toBinaryString(number).toCharArray())
                .map(character -> Byte.parseByte(character.toString()))
                .toArray(Byte[]::new);
    }

    public static Stream<String> toBinaryStringStream(int number) {
        return primitiveArrayToObjectStream(Integer.toBinaryString(number).toCharArray())
                .map(character -> Byte.parseByte(character.toString()))
                .map(String::valueOf);
    }

    public static byte[] getBinaryFormated(int intValue, int degree) {
        var formatedBytes = new byte[degree];
        var binaryBytes = toBinaryCharArray(intValue);

        for (int i = 0; i < binaryBytes.length; i++) {
            formatedBytes[formatedBytes.length - (i + 1)] = binaryBytes[binaryBytes.length - (i + 1)];
        }

        return formatedBytes;
    }

    public static String parseToString(byte[] bytes) {
        return primitiveArrayToObjectStream(bytes)
                .map(String::valueOf)
                .collect(joining());
    }
}
