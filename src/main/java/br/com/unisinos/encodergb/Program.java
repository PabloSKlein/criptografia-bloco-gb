package br.com.unisinos.encodergb;

public class Program {
    public static void main(String args[]) {
        String texto = "123456ABCD13";
        String chave = "AABB09182736CCDD";
        System.out.println("\nTexto a cifrar: " + texto.toUpperCase());

        System.out.println("Crifrar:\n");
        texto = DES.cifra(texto, chave);
        System.out.println("\nTexto cifrado: " + texto.toUpperCase() + "\n");


        System.out.println("Decifrar\n");
        texto = DES.decifra(texto, chave);
        System.out.println("\nTexto Decifrado: " + texto.toUpperCase());
    }
}
