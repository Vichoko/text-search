package test;

import algorithm.Preprocess;

import java.io.IOException;

public class PreprocessTest {
    public static void main(String[] args) throws IOException {
        Preprocess p = new Preprocess("source/wiki.txt");
        String result = p.clean();
        System.out.print("  done [debug for inspection].");
    }
}
