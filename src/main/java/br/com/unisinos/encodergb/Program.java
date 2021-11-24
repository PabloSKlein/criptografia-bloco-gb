package br.com.unisinos.encodergb;

public class Program {
    public static void main(String[] args) {
        var texto = "ABCDEF123456CD2077";
        var chave = "A07DA07D";
        System.out.println("\nTexto a cifrar: " + texto.toUpperCase());

        System.out.println("Crifrar:\n");
        texto = DES.cifra(texto, chave);
        System.out.println("\nTexto cifrado: " + texto.toUpperCase() + "\n");


        System.out.println("Decifrar\n");
        texto = DES.decifra(texto, chave);
        System.out.println("\nTexto Decifrado: " + texto.toUpperCase());
    }
}