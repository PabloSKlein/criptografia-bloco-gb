package br.com.unisinos.encodergb;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.unisinos.encodergb.util.Constantes.*;
import static br.com.unisinos.encodergb.util.Conversao.binToHex;
import static br.com.unisinos.encodergb.util.Conversao.hexToBin;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DES {

    public static String cifra(String texto, String chave) {
        // Chave das rodadas
        var subChaves = geraSubChaves(chave);

        int i;
        // Permutacao
        texto = permutar(PERMUTACAO, texto);

        System.out.println("Primeira permutacao: " + texto.toUpperCase());
        System.out.println("Bloco L/R: L0=" + texto.substring(0, TAMANHO_BLOCO).toUpperCase() + " R0="
                + texto.substring(TAMANHO_BLOCO, TAMANHO_BLOCO * 2).toUpperCase() + "\n");

        for (i = 0; i < TAMANHO_BLOCO * 2; i++) {
            texto = round(texto, subChaves[i], i);
        }

        // Swap
        texto = texto.substring(TAMANHO_BLOCO, TAMANHO_BLOCO * 2) + texto.substring(0, TAMANHO_BLOCO);

        texto = permutar(PERMUTACAO_INVERSA, texto);
        return texto;
    }

    public static String decifra(String texto, String chave) {
        var keys = geraSubChaves(chave);

        texto = permutar(PERMUTACAO, texto);

        System.out.println("Primeira permutacao: " + texto.toUpperCase());
        System.out.println("Bloco L/R: L0=" + texto.substring(0, TAMANHO_BLOCO).toUpperCase() + " R0="
                + texto.substring(TAMANHO_BLOCO, TAMANHO_BLOCO * 2).toUpperCase() + "\n");

        //Inverte rounds
        for (int i = 11; i > -1; i--) {
            texto = round(texto, keys[i], 11 - i);
        }

        // Swap
        texto = texto.substring(TAMANHO_BLOCO, TAMANHO_BLOCO * 2) + texto.substring(0, TAMANHO_BLOCO);

        texto = permutar(PERMUTACAO_INVERSA, texto);
        return texto;
    }

    private static String permutar(int[] tabela, String texto) {
        var output = new StringBuilder();

        texto = hexToBin(texto);

        for (int i : tabela) {
            output.append(texto.charAt(i - 1));
        }

        return binToHex(output.toString());
    }

    // xor
    private static String xor(String a, String b) {
        // hexadecimal to decimal(base 10)
        var hexaA = Long.parseUnsignedLong(a, 16);
        // hexadecimal to decimal(base 10)
        var hexaB = Long.parseUnsignedLong(b, 16);
        // xor
        var builder = new StringBuilder(Long.toHexString(hexaA ^ hexaB));

        //padding zeros
        while (builder.length() < b.length()) {
            builder.insert(0, "0");
        }
        a = builder.toString();
        return a;
    }

    private static String leftCircularShift(String texto, int numBits) {
        var n = texto.length() * 4;
        var permutacao = new int[n];
        for (int i = 0; i < n - 1; i++) {
            permutacao[i] = (i + 2);
        }
        permutacao[n - 1] = 1;
        while (numBits-- > 0) {
            texto = permutar(permutacao, texto);
        }
        return texto;
    }

    private static String[] geraSubChaves(String chave) {
        var subChaves = new String[TAMANHO_BLOCO * 2];

        chave = permutar(PERMUTACAO_CHAVE, chave);
        for (int i = 0; i < TAMANHO_BLOCO * 2; i++) {
            chave = leftCircularShift(chave.substring(0, 4), SHIFT_BITS[i])
                    + leftCircularShift(chave.substring(4, 8), SHIFT_BITS[i]);
            subChaves[i] = permutar(PERMUTACAO_CHAVE_2, chave);
        }
        return subChaves;
    }

    private static String sBox(String texto) {
        var output = new StringBuilder();
        texto = hexToBin(texto);
        for (int i = 0; i < 24; i += TAMANHO_BLOCO) {
            var temp = texto.substring(i, i + TAMANHO_BLOCO);
            int num = i / TAMANHO_BLOCO;
            int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
            int col = Integer.parseInt(temp.substring(1, 5), 2);
            output.append(Integer.toHexString(SBOX[num][row][col]));
        }
        return output.toString();
    }

    private static String round(String texto, String chave, int round) {
        var left = texto.substring(0, TAMANHO_BLOCO);
        var temp = texto.substring(TAMANHO_BLOCO, TAMANHO_BLOCO * 2);
        var right = temp;

        temp = xor(temp, chave);
        temp = sBox(temp);
        left = xor(left, temp);

        System.out.println("Round " + (round + 1) + " " + right.toUpperCase() + " " + left.toUpperCase() + " " + chave.toUpperCase());

        // Swap
        return right + left;
    }
}
