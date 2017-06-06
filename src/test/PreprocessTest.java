package test;

import utils.Preprocess;

import java.io.IOException;

public class PreprocessTest {
    public static void main(String[] args) throws IOException {
        Preprocess p = new Preprocess("source/2^21.txt");
        String result = p.clean();
        String[] sample = Preprocess.takeSample(result, true);
        System.out.print("  done [debug for inspection].");
    }
}
