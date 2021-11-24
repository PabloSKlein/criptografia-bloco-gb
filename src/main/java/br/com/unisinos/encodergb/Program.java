package br.com.unisinos.encodergb;

public class Program {
    public static void main(String args[]) {
        //String texto = "4D21C5BA77D06212F39E16BF2756E9811125F7FC";
        var texto = "123456ABCD1A";
        String chave = "A07DA07D";
        System.out.println("\nTexto a cifrar: " + texto.toUpperCase());

        System.out.println("Crifrar:\n");
        texto = DES.cifra(texto, chave);
        System.out.println("\nTexto cifrado: " + texto.toUpperCase() + "\n");


        System.out.println("Decifrar\n");
        texto = DES.decifra(texto, chave);
        System.out.println("\nTexto Decifrado: " + texto.toUpperCase());
    }
}
