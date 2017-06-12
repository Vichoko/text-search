/**
 * Created by constanzafierro on 11-06-17.
 */
public class Searches {
    public static void main(String[] args){
        SuffixArray sa = new SuffixArray("dogs and cats and dogs are really cute");
        int index = occurrencesPattern("dogs", sa.suffixStrings);
        System.out.println(index);
        System.out.println(binarySearchFirstOccurrence("dogs", sa.suffixStrings));
        System.out.println(binarySearchLastOccurrence("dogs", sa.suffixStrings));
    }

    public static int occurrencesPattern(String pattern, String[] suffixStrings){
        int first = binarySearchFirstOccurrence(pattern, suffixStrings);
        int last = binarySearchLastOccurrence(pattern, suffixStrings);
        return last-first+1;
    }

    public static int searchPattern(String pattern, String[] suffixStrings){
        return binarySearchFirstOccurrence(pattern, suffixStrings);
    }

    private static int binarySearchFirstOccurrence(String pattern, String[] suffixStrings) {
        int l = 0, r = suffixStrings.length-1;
        while (l < r){
            int middle = (r+l)/2;
            int compare = comparePattern(pattern, suffixStrings[middle]);
            if(compare <= 0)
                r = middle;
            else
                l = middle+1;
        }
        if (comparePattern(pattern, suffixStrings[l]) == 0)
            return l;
        return -1;
    }

    private static int binarySearchLastOccurrence(String pattern, String[] suffixStrings) {
        int l = 0, r = suffixStrings.length-1;
        while (l < r){
            int middle = (r+l+1)/2;
            int compare = comparePattern(pattern, suffixStrings[middle]);
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
     * @param suffixString
     * @return              0 if pattern is contains, less than 0 if pattern is lexicographically smaller, 1 otherwise
     */
    private static int comparePattern(String pattern, String suffixString) {
        char[] pattern_char = pattern.toCharArray();
        for (int i = 0; i < pattern_char.length; i++) {
            if (suffixString.charAt(i) != pattern_char[i])
                return Character.compare(pattern_char[i], suffixString.charAt(i));
        }
        return 0;
    }
}
