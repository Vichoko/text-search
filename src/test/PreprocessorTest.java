package test;

import utils.Preprocessor;

import java.io.IOException;

public class PreprocessorTest {
    public static void main(String[] args) throws IOException {
        Preprocessor p = new Preprocessor("source/2^21.txt");
        String result = p.clean();
        String[] sample = Preprocessor.takeSample(result, true);
        System.out.print("  done [debug for inspection].");
    }
}
