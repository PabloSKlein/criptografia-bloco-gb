package br.com.unisinos.encodergb.encoder;

import br.com.unisinos.encodergb.util.BinaryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.unisinos.encodergb.util.Constants.TAMANHO_BLOCO;
import static br.com.unisinos.encodergb.util.PrimitiveUtil.primitiveArrayToObjectStream;
import static java.lang.Math.min;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.stream.Collectors.joining;

public class Encoder {
    private static List<List<String>> montaBlocosDeBits(List<String> bits) {

        var blocos = new ArrayList<List<String>>();
        for (int i = 0; i < bits.size(); i = i + TAMANHO_BLOCO) {
            blocos.add(bits.subList(i, min(i + TAMANHO_BLOCO, bits.size())));
        }
        return blocos;
    }

    public static String criptografaArquivo(String chave, String arquivo) {

        return criptografaBlocos(chave,
                montaBlocosDeBits(primitiveArrayToObjectStream(arquivo.getBytes(US_ASCII))
                        .flatMap(BinaryUtil::toBinaryStringStream)
                        .collect(Collectors.toList())))
                .stream()
                .map(bloco -> String.join("", bloco))
                .collect(joining());
    }

    private static List<List<String>> criptografaBlocos(String chave, List<List<String>> blocos) {



        return new ArrayList<>();
    }
}
