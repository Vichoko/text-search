package test;

import algorithms.Automaton.AutomatonTextSearch;
import algorithms.suffixarray.PatternSearcherSA;
import algorithms.suffixarray.SuffixArray;
import utils.Global;
import utils.Preprocessor;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * Created by constanzafierro on 12-06-17.
 */
public class SuffixArrayTest {
    public static void main(String[] args) throws IOException {
        File statisticsFile = new File("./results/suffix_array_tests.txt");
        PrintWriter resultsWriter = new PrintWriter(statisticsFile);
        for (int i = 15; i <= 25; i++) {
            String textFile = "source/2^"+i+".txt";
            String fullText = readAndProcess(textFile);
            System.out.println(">>> "+i);
            resultsWriter.println(">>> n: " + fullText.length());
            // create suffix array
            Instant start = Instant.now();
            SuffixArray sa = new SuffixArray(fullText);
            Instant end = Instant.now();
            System.out.println("Seconds to create suffix array: "+ Duration.between(start, end).getSeconds());
            resultsWriter.println("Seconds to create suffix array: "+ Duration.between(start, end).getSeconds());
            PatternSearcherSA searcher = new PatternSearcherSA(sa.getSuffixArray(), fullText);
            // Patterns samples
            String[] samples = Preprocessor.takeSample(fullText, false);
            System.out.println("Total patterns to search: "+samples.length);
            start = Instant.now();
            for (String pattern : samples) {
                if (pattern.length() < 3) continue;
                int count = searcher.countOccurrencesPattern(pattern);
            }
            end = Instant.now();
            System.out.println("Seconds searching: "+ Duration.between(start, end).getSeconds());
            resultsWriter.println("Seconds searching: "+ Duration.between(start, end).getSeconds());
        }
        resultsWriter.close();
    }

    private static String readAndProcess(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()) {
            sb.append(in.nextLine());
        }
        in.close();
        return sb.toString().replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
