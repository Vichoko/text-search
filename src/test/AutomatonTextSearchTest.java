package test;

import algorithm.AutomatonTextSearch;
import utils.Preprocess;
import utils.Triple;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.HashMap;

import static utils.Global.*;


public class AutomatonTextSearchTest {
    public static void main(String[] args) throws Exception {
        //firstTest();
        completeTest(true);
    }
    private static void completeTest(boolean verbose) throws Exception {
        File statisticsFile = new File("./results/automaton_test_results.txt");
        PrintWriter pw = new PrintWriter(statisticsFile);


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

        for (String textFile : files){
            // key: M, value: CONSTRUCTION_TIME, SEARCH_TIME, COUNT
            HashMap<Integer, Triple<Long, Long, Integer>> map = new HashMap<>();

            Preprocess p = new Preprocess(textFile);
            String fullText = p.clean();
            makeAlphabet(fullText.toCharArray(), verbose);
            System.out.println("n: " + fullText.length());
            pw.println("n: " + fullText.length());
            String[] sample = Preprocess.takeSample(fullText, verbose);
            for (String pattern : sample) {
                if (pattern.length() < 3) continue;
                AutomatonTextSearch ats = new AutomatonTextSearch(fullText, pattern, verbose);
                ats.run();

                // Agregar resultados parciales a tabla de hash para calcular promedios
                if (!map.containsKey(M)){
                    map.put(M, new Triple<>(
                            CONSTRUCTION_TIME,
                            SEARCH_TIME,
                            1));
                } else {
                    Triple<Long, Long, Integer> previous = map.get(M);
                    map.put(M,
                            new Triple<>(
                                    CONSTRUCTION_TIME + previous.getFirst(),
                                    SEARCH_TIME + previous.getSecond(),
                                    previous.getThird() + 1));
                }
            }

            // calcular promedios y hacer output de estadisticas
            for (Integer m : map.keySet()) {
                Triple<Long, Long, Integer> val = map.get(m);
                pw.println("m: " + m + ", construction_time: " + val.getFirst() + ", search_time: " + val.getSecond() + ", counter: " + val.getThird());

            }
            pw.println("");
            pw.flush();
        }
        pw.close();

    }

    public static void firstTest() throws Exception {
        Preprocess p = new Preprocess("source/2^21.txt");
        String result = p.clean();
        String[] sample = Preprocess.takeSample(result, true);

        for (String pattern : sample) {
            if (pattern.length() < 3) continue;
            System.out.println("Searching: " + pattern);
            AutomatonTextSearch ats = new AutomatonTextSearch(result, pattern, true);
            System.out.println("AFD Found: " + ats.run() + " times.");
            System.out.println("Real Quantity: " + realCount(result, pattern) + " times.\n");

        }
    }

    private static int realCount(String src, String pattern) {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = src.indexOf(pattern, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += pattern.length();
            }
        }
        return count;
    }
}
