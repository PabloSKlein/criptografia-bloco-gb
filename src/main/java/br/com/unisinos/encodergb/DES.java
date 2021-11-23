package br.com.unisinos.encodergb;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.unisinos.encodergb.util.Constantes.*;
import static br.com.unisinos.encodergb.util.Conversao.binToHex;
import static br.com.unisinos.encodergb.util.Conversao.hextoBin;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DES {

    public static String cifra(String texto, String chave) {
        int i;
        // Chave das rodadas
        var subChaves = geraSubChaves(chave);

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
        texto = hextoBin(texto);

        for (int i : tabela) {
            output.append(texto.charAt(i - 1));
        }

        return binToHex(output.toString());
    }

    // xor 2 hexadecimal strings
    private static String xor(String a, String b) {
        // hexadecimal to decimal(base 10)
        var t_a = Long.parseUnsignedLong(a, 16);
        // hexadecimal to decimal(base 10)
        var t_b = Long.parseUnsignedLong(b, 16);
        // xor
        t_a = t_a ^ t_b;
        // decimal to hexadecimal
        // prepend 0's to maintain length
        StringBuilder aBuilder = new StringBuilder(Long.toHexString(t_a));
        while (aBuilder.length() < b.length())
            aBuilder.insert(0, "0");
        a = aBuilder.toString();
        return a;
    }

    // left Circular Shifting bits
    private static String leftCircularShift(String input, int numBits) {
        int n = input.length() * 4;
        int[] perm = new int[n];
        for (int i = 0; i < n - 1; i++)
            perm[i] = (i + 2);
        perm[n - 1] = 1;
        while (numBits-- > 0)
            input = permutar(perm, input);
        return input;
    }

    private static String[] geraSubChaves(String key) {
        String[] keys = new String[12];

        key = permutar(PERMUTACAO_CHAVE, key);
        for (int i = 0; i < 12; i++) {
            key = leftCircularShift(key.substring(0, 7), SHIFT_BITS[i])
                    + leftCircularShift(key.substring(7, 14), SHIFT_BITS[i]);
            keys[i] = permutar(PERMUTACAO_CHAVE_2, key);
        }
        return keys;
    }

    private static String sBox(String input) {
        StringBuilder output = new StringBuilder();
        input = hextoBin(input);
        for (int i = 0; i < 24; i += TAMANHO_BLOCO) {
            String temp = input.substring(i, i + TAMANHO_BLOCO);
            int num = i / TAMANHO_BLOCO;
            int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
            int col = Integer.parseInt(temp.substring(1, 5), 2);
            output.append(Integer.toHexString(SBOX[num][row][col]));
        }
        return output.toString();
    }

    private static String round(String texto, String chave, int num) {
        var left = texto.substring(0, TAMANHO_BLOCO);
        var temp = texto.substring(TAMANHO_BLOCO, TAMANHO_BLOCO * 2);
        var right = temp;

        // xor temp and round chave
        temp = xor(temp, chave);
        // lookup in s-box table
        temp = sBox(temp);
        // xor
        left = xor(left, temp);

        System.out.println("Round " + (num + 1) + " " + right.toUpperCase() + " " + left.toUpperCase() + " " + chave.toUpperCase());

        // Swap
        return right + left;
    }
}
