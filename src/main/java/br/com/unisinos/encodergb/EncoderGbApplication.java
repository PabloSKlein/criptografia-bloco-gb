package br.com.unisinos.encodergb;

import br.com.unisinos.encodergb.encoder.Encoder;
import br.com.unisinos.encodergb.file.FileReader;
import br.com.unisinos.encodergb.file.FileWriter;

public class EncoderGbApplication {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Infome a chave");
        }

        var chave = args[0];
        var arquivo = FileReader.readFile();

        FileWriter.write(Encoder.criptografaArquivo(chave, arquivo));
    }

}
