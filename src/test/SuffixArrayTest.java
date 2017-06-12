package test;

import utils.Preprocessor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by constanzafierro on 12-06-17.
 */
public class SuffixArrayTest {
    public static void main(String[] args) throws IOException {
        File statisticsFile = new File("./results/automaton_test_results.txt");
        PrintWriter resultsWriter = new PrintWriter(statisticsFile);
        String[] files = {"source/2^15.txt",
                "source/2^16.txt",
                "source/2^17.txt",
                "source/2^18.txt",
                "source/2^19.txt",
                "source/2^20.txt",
                "source/2^21.txt",
                "source/2^22.txt",
                "source/2^23.txt",
                "source/2^24.txt",
        };
        for (String textFile : files) {
            Preprocessor p = new Preprocessor(textFile);
            String fullText = p.clean();
            resultsWriter.println("n: " + fullText.length());
            // Patterns samples
            String[] samples = Preprocessor.takeSample(fullText, false);
            for (String pattern : samples) {
                if (pattern.length() < 3) continue;

            }
        }
    }

}
