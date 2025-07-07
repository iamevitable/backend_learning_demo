package demo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class readFiles {
    public static void main(String[] args) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src/main/resources/text.txt"));
        String s = new String(bufferedInputStream.readAllBytes());
        System.out.println(s);
    }
}
