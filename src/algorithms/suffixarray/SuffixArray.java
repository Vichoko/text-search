package algorithms.suffixarray;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by constanzafierro on 10-06-17.
 */
public class SuffixArray {
    private int[] suffixArray;
    private String text;

    public SuffixArray(String text) {
        this.text = text;
        int n_text = text.length();
        // convert text to integers
        ArrayList<Integer> text_char = new ArrayList<>(n_text);
        for (int i = 0; i < n_text; i++) {
            // 0 padding, 1 spaces, 2 'a' ...
            if (text.charAt(i) == ' ') text_char.add(1);
            else text_char.add(text.charAt(i)-'a'+2);
        }
        this.suffixArray = buildSuffixArray(text_char);
    }

    public int[] getSuffixArray() {
        return suffixArray;
    }

    private int[] buildSuffixArray(ArrayList<Integer> s){
        int n = s.size();
        while(s.get(s.size()-3) != 0) s.add(0); // special character for padding
        // build SA12
        int[] SA12 = getSA12(s);
        // build SA0
        int[] SA0 = getSA0(s);
        // Merge
        int index_SA12 = 0, index_SA0 = 0;
        int[] SA = new int[SA0.length + SA12.length];
        int index_SA = 0;
        HashMap<Integer, Integer> pos_SA12 = new HashMap<>(SA12.length);
        for (int i = 0; i < SA12.length; i++) {
            pos_SA12.put(SA12[i], i);
        }
        while(index_SA12 < SA12.length && index_SA0 < SA0.length){
            int i = SA12[index_SA12];
            int j = SA0[index_SA0];
            if(i%3 == 1){
                if (s.get(i) < s.get(j)) SA[index_SA++] = SA12[index_SA12++];
                else if (s.get(i) > s.get(j)) SA[index_SA++] = SA0[index_SA0++];
                else{
                    if(!pos_SA12.containsKey(i+1)) SA[index_SA++] = SA12[index_SA12++];
                    else if(!pos_SA12.containsKey(j+1)) SA[index_SA++] = SA0[index_SA0++];
                    else if(pos_SA12.get(i+1) < pos_SA12.get(j+1)) SA[index_SA++] = SA12[index_SA12++];
                    else SA[index_SA++] = SA0[index_SA0++];
                }
            }
            else{
                if (s.get(i) < s.get(j) || (s.get(i) == s.get(j) && s.get(i+1) < s.get(j+1)))
                    SA[index_SA++] = SA12[index_SA12++];
                else if (s.get(i) > s.get(j) || (s.get(i) == s.get(j) && s.get(i+1) > s.get(j+1)))
                    SA[index_SA++] = SA0[index_SA0++];
                else{
                    if(!pos_SA12.containsKey(i+2)) SA[index_SA++] = SA12[index_SA12++];
                    else if(!pos_SA12.containsKey(j+2)) SA[index_SA++] = SA0[index_SA0++];
                    else if(pos_SA12.get(i+2) < pos_SA12.get(j+2)) SA[index_SA++] = SA12[index_SA12++];
                    else SA[index_SA++] = SA0[index_SA0++];
                }
            }
        }
        while(index_SA12 < SA12.length) SA[index_SA++] = SA12[index_SA12++];
        while(index_SA0 < SA0.length) SA[index_SA++] = SA0[index_SA0++];
        return SA;
    }

    private int[] getSA0(ArrayList<Integer> s) {
        // build array of triplets
        ArrayList<Triplet> triplets = new ArrayList<>();
        int max = 0;
        for (int i = 0; i+2 < s.size(); i++) {
            if (i % 3 == 0) {
                Triplet t = new Triplet(i, new int[]{s.get(i), s.get(i + 1), s.get(i + 2)});
                triplets.add(t);
                max = Math.max(max, Math.max(Math.max(s.get(i), s.get(i + 1)), s.get(i+2)));
            }
        }

        // getSorted
        ArrayList<Triplet> triplets_sorted = radixSort(triplets, max);
        int[] SA0 = new int[triplets_sorted.size()];
        // name triplets
        for (int i = 0; i < triplets_sorted.size(); i++) {
            SA0[i] = triplets_sorted.get(i).index;
        }
        return SA0;
    }

    private int[] getSA12(ArrayList<Integer> s) {
        // build array of triplets
        ArrayList<Triplet> triplets = new ArrayList<>();
        int max = 0;
        for (int i = 0; i+2 < s.size(); i++) {
            if (i % 3 == 1) {
                max = Math.max(max, Math.max(Math.max(s.get(i), s.get(i + 1)), s.get(i+2)));
                Triplet t = new Triplet(i, new int[]{s.get(i), s.get(i + 1), s.get(i + 2)});
                triplets.add(t);
            }
        }
        for (int i = 0; i+2 < s.size(); i++) {
            if (i % 3 == 2) {
                max = Math.max(max, Math.max(Math.max(s.get(i), s.get(i + 1)), s.get(i+2)));
                Triplet t = new Triplet(i, new int[]{s.get(i), s.get(i + 1), s.get(i + 2)});
                triplets.add(t);
            }
        }
        // getSorted
        ArrayList<Triplet> triplets_sorted = radixSort(triplets, max);
        int name = 0;
        HashSet<Triplet> setTriplets = new HashSet<>(); // count different
        // name triplets
        for(Triplet t: triplets_sorted){
            if (setTriplets.contains(t)) t.token_name = name;
            else {
                t.token_name = ++name;
                setTriplets.add(t);
            }
        }
        // base case
        int [] SA12 = new int[triplets_sorted.size()];
        if(setTriplets.size() == triplets_sorted.size()){
            for (int i = 0; i < triplets_sorted.size(); i++) {
                SA12[i] = triplets_sorted.get(i).index;
            }
        }
        else{
            ArrayList<Integer> newText = new ArrayList<>(triplets.size());
            for (Triplet t: triplets) {
                newText.add(t.token_name);
            }
            int n1 = (s.size()-1)/3;
            int n2 = (s.size()-2)/3;
            int[] SANewText = buildSuffixArray(newText);
            int j = 0;
            for (int i : SANewText) {
                // first half
                if (i < n1 && 1 + 3 * i < 1 + 3 * n1)
                    SA12[j++] = 1 + 3 * i;
                // second half
                else if (i >= n1 && 2 + 3 * (i - n1) < 2 + 3 * n2)
                    SA12[j++] = 2 + 3 * (i - n1);

            }
        }
        return SA12;
    }

    private ArrayList<Triplet> radixSort(ArrayList<Triplet> triplets, int size_alphabet) {
        ArrayList<Triplet>[] boxes = new ArrayList[size_alphabet+1];
        for (int i = 0; i < size_alphabet+1; i++)
            boxes[i] = new ArrayList<>();
        ArrayList<Triplet> triplets_sorted = new ArrayList<>(triplets.size());
        for (int i = triplets.size()-1; i >= 0; i--) {
            triplets_sorted.add(triplets.get(i));
        }
        for (int i = 2; i >= 0; i--) {
            for(Triplet t: triplets_sorted){
                boxes[t.triplet_values[i]].add(t);
            }
            triplets_sorted.clear();
            for (ArrayList<Triplet> a: boxes){
                triplets_sorted.addAll(a);
                a.clear();
            }
        }
        return triplets_sorted;
    }

}

class Triplet{
    int index;
    int[] triplet_values; // triplet
    int token_name;

    public Triplet(int index, int[] chars) {
        this.index = index;
        this.triplet_values = chars;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Triplet){
            Triplet t = (Triplet) obj;
            return Arrays.equals(t.triplet_values, this.triplet_values);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(triplet_values);
    }
}