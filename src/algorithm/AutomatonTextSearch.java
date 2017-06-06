package algorithm;


import utils.automaton.State;
import utils.automaton.StateTransitionFunction;

import java.util.Set;

public class AutomatonTextSearch {
    private String m_fullText;
    private String m_patternText;
    private Set<Character> m_alphabet;
    private StateTransitionFunction m_transitionFunction;

    public AutomatonTextSearch(String fullText, String pattern){
        m_fullText = fullText;
        m_patternText = pattern;
        char[] patternArray = pattern.toCharArray();

        State finalState = new State(patternArray.length);
        State initialState = new State(0);

        m_transitionFunction = new StateTransitionFunction();

        for (int q = 0; q < patternArray.length; q++){
            for (char a : m_alphabet){
                if (patternArray[q+1] == a){
                    return;
                }
            }

        }




    }

    private void makeAlphabet(char[] textAsArray){
        for (char c:textAsArray){
            m_alphabet.add(c);
        }
    }

}
