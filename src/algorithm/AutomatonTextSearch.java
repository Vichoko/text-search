package algorithm;


import utils.automaton.*;

import static utils.Global.*;


import java.util.ArrayList;
import java.util.TreeSet;


public class AutomatonTextSearch {
    private String m_fullText;
    private String m_patternText;
    private StateTransitionFunction m_transitionFunction;
    private FinitStateAutomaton m_afd;
    private boolean m_verbose;

    /**
     * Construye automata para busqueda de texto
     *
     * @param fullText Fuente de texto sobre el cual se hara la busqueda
     * @param pattern  Patron de texto el cual se debe buscar
     * @throws Exception En caso de pedir un cache invalido se lanza esta excepcion.
     */
    public AutomatonTextSearch(String fullText, String pattern, boolean verbose) throws Exception {
        m_verbose = verbose;
        m_fullText = fullText;
        m_patternText = pattern;
        char[] patternArray = pattern.toCharArray();


        // AFD Construction
        if (m_verbose) System.out.println("AutomatonTextSearch :: Constructing AFD");
        long t0 = System.currentTimeMillis();
        State finalState = new State(patternArray.length);
        ArrayList<State> finalStates = new ArrayList<>();
        finalStates.add(finalState);
        State initialState = new State(0);

        m_transitionFunction = new StateTransitionFunction();

        // base case 1
        int q = 0;
        for (char a : ALPHABET) {
            if (patternArray[q] == a) {
                m_transitionFunction.easy_set_transition(q, a, q + 1);
            } else {
                m_transitionFunction.easy_set_transition(q, a, q);
            }
        }

        if (patternArray.length > 1){
        //base case 2
        q = 1;
        for (char a : ALPHABET) {
            if (patternArray[q] == a) {
                m_transitionFunction.easy_set_transition(q, a, q + 1);
            } else {
                // compute the state of sigma(0,a)
                m_transitionFunction.set_transition(
                        new InstantDescription(new State(q), new AFDCharacter(a)),
                        m_transitionFunction.get_nextState(
                                new InstantDescription(initialState, new AFDCharacter(a))));
            }
        }

            // now i have all transitions from state 0 and 1.
            AFDCache cache = new AFDCache(m_transitionFunction, patternArray);

        // iteration
        for (q = 2; q < patternArray.length; q++) {
            for (char a : ALPHABET) {
                if (patternArray[q] == a) {
                    m_transitionFunction.easy_set_transition(q, a, q + 1);
                } else {
                    m_transitionFunction.set_transition(
                            new InstantDescription(new State(q), new AFDCharacter(a)),
                            m_transitionFunction.get_nextState(
                                    new InstantDescription(cache.get(q - 1), new AFDCharacter(a))));

                }
            }
        }
        // final case
        q = patternArray.length;
        for (char a : ALPHABET) {
            m_transitionFunction.set_transition(
                    new InstantDescription(new State(q), new AFDCharacter(a)),
                    m_transitionFunction.get_nextState(
                            new InstantDescription(cache.get(q - 1), new AFDCharacter(a))));

            }
        }
        long t1 = System.currentTimeMillis();
        CONSTRUCTION_TIME = t1 - t0;
        M = patternArray.length;
        if (m_verbose) System.out.println("    done construction. Pattern length: " + M + " in " + CONSTRUCTION_TIME);
        m_afd = new FinitStateAutomaton(initialState, finalStates, m_transitionFunction);
    }

    public int run() {
        long t0 = System.currentTimeMillis();
        int result = m_afd.run_n_count(m_fullText);
        long t1 = System.currentTimeMillis();
        SEARCH_TIME = t1 - t0;
        if (m_verbose) System.out.println("Finished AFD run in " + SEARCH_TIME);
        return result;
    }

}

class AFDCache {
    private int m_index;
    private State m_currentState;
    private StateTransitionFunction m_stf;
    private char[] m_pattern;

    /**
     * Cache mantiene valor de variable X (enunciado) tal que: X = sigma(...sigma(sigma(0, P[2]), P[3])..., P[q]).
     *
     * @param stf     Referencia a funcion de transicion del automata en construccion
     * @param pattern Patron sobre el cual se construye el automata
     */
    AFDCache(StateTransitionFunction stf, char[] pattern) {
        m_stf = stf;
        m_pattern = pattern;
        // caso base
        m_index = 1;
        m_currentState = stf.get_nextState(
                new InstantDescription(
                        new State(0),
                        new AFDCharacter(pattern[m_index])));
    }

    /**
     * Actualiza cache si es necesario, retornando valor correcto dependiendo de indice.
     *
     * @param patternIndex Sub-indice del patron que indica el valor del cache esperado.
     * @return Estado correspondiente a llamar al AFD recursivamente sobre el patron desde 0 hasta el sub-indice indicado.
     * @throws Exception En caso que se pida un cache mas antiguo que el actual.
     */
    State get(int patternIndex) throws Exception {
        if (patternIndex < m_index) {
            throw new Exception("AFDCache :: Asked for older cache");
        }
        while (patternIndex > m_index) {
            m_index++;
            m_currentState = m_stf.get_nextState(new InstantDescription(
                    m_currentState,
                    new AFDCharacter(m_pattern[m_index])));

        }
        return m_currentState;
    }
}