package test;

import algorithm.AutomatonTextSearch;
import com.sun.xml.internal.ws.util.StringUtils;
import utils.Preprocess;


public class AutomatonTextSearchTest {
    public static void main(String[] args) throws Exception {
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
