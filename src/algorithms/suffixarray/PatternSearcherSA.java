package algorithms.suffixarray;

/**
 * Created by constanzafierro on 11-06-17.
 */
public class PatternSearcherSA{
    private int[] suffixArray;
    private String text;

    public PatternSearcherSA(int[] suffixArray, String text) {
        this.suffixArray = suffixArray;
        this.text = text;
    }

    public int countOccurrencesPattern(String pattern){
        int first = binarySearchFirstOccurrence(pattern);
        int last = binarySearchLastOccurrence(pattern);
        return last-first+1;
    }

    private int binarySearchFirstOccurrence(String pattern) {
        int l = 0, r = suffixArray.length-1;
        while (l < r){
            int middle = (r+l)/2;
            int compare = comparePattern(pattern, middle);
            if(compare <= 0)
                r = middle;
            else
                l = middle+1;
        }
        if (comparePattern(pattern, l) == 0)
            return l;
        return -1;
    }

    private int binarySearchLastOccurrence(String pattern) {
        int l = 0, r = suffixArray.length-1;
        while (l < r){
            int middle = (r+l+1)/2;
            int compare = comparePattern(pattern, middle);
            if(compare >= 0)
                l = middle;
            else
                r = middle-1;
        }
        return l;
    }

    /**
     *
     * @param pattern
     * @return              0 if pattern is contains, less than 0 if pattern is lexicographically smaller, 1 otherwise
     */
    private int comparePattern(String pattern, int index) {
        String suffixString = text.substring(index);
        char[] pattern_char = pattern.toCharArray();
        if(suffixString.length() == 0 && pattern_char.length!=0) return 1;
        for (int i = 0; i < pattern_char.length; i++) {
            if (suffixString.charAt(i) != pattern_char[i])
                return Character.compare(pattern_char[i], suffixString.charAt(i));
        }
        return 0;
    }
}
