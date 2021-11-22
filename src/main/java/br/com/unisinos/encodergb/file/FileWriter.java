package br.com.unisinos.encodergb.file;

import java.io.File;
import java.io.FileOutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileWriter {
    public static void write(String toWrite) {
        var outputFile = new File("encodedFile.txt");
        createIfDoesntExists(outputFile);
        try (var outputStream = new FileOutputStream(outputFile, false)) {
            outputStream.write(toWrite.getBytes(UTF_8));
        } catch (Exception e) {
            System.out.println("Deu ruim!");
        }
    }

    private static void createIfDoesntExists(File outputFile) {
        try {
            outputFile.createNewFile();
        } catch (Exception e) {
            System.out.println("Arquivo já existe");
        }
    }
}
