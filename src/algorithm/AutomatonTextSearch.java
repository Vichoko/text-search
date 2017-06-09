package algorithm;


import utils.automaton.*;

import static utils.Global.*;


import java.util.ArrayList;

/**
 * Clase que maneja el sistema de busqueda en texto mediante el uso de un Automata Finito Determinista.
 */
public class AutomatonTextSearch {
    private String m_fullText;
    private FinitStateAutomaton m_afd;
    private boolean m_verbose;

    /**
     * Construye automata (su funcion de transicion) para busqueda de texto. Almacena fullText para posterior uso.
     *
     * @param fullText Fuente de texto sobre el cual se hara la busqueda
     * @param pattern  Patron de texto el cual se debe buscar
     * @throws Exception En caso de pedir un cache invalido se lanza esta excepcion.
     */
    public AutomatonTextSearch(String fullText, String pattern, boolean verbose) throws Exception {
        m_verbose = verbose;
        m_fullText = fullText;
        char[] patternArray = pattern.toCharArray();


        // AFD Construction
        if (m_verbose) System.out.println("AutomatonTextSearch :: Constructing AFD");
        long t0 = System.currentTimeMillis();
        // Estados iniciales y finales
        State finalState = new State(patternArray.length);
        ArrayList<State> finalStates = new ArrayList<>();
        finalStates.add(finalState);
        State initialState = new State(0);

        // Construccion de funcion transicion de coste O(m)
        StateTransitionFunction m_transitionFunction = new StateTransitionFunction();

        // caso base 1
        int q = 0;
        for (char a : ALPHABET) {
            if (patternArray[q] == a) {
                m_transitionFunction.easy_set_transition(q, a, q + 1);
            } else {
                m_transitionFunction.easy_set_transition(q, a, q);
            }
        }

        if (patternArray.length > 1) {
            //caso base 2
            q = 1;
            for (char a : ALPHABET) {
                if (patternArray[q] == a) {
                    m_transitionFunction.easy_set_transition(q, a, q + 1);
                } else {
                    // computar el estado de sigma(0,a)
                    m_transitionFunction.set_transition(
                            new InstantDescription(new State(q), new AFDCharacter(a)),
                            m_transitionFunction.get_nextState(
                                    new InstantDescription(initialState, new AFDCharacter(a))));
                }
            }
            // ahora tengo todas las transiciones del estado 0 y 1, puedo definir el cache de transiciones parciales
            AFDCache cache = new AFDCache(m_transitionFunction, patternArray);

            // caso iterativo
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
            // caso final
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

    /**
     * Ejecuta el AFD como se requiere. Contando la cantidad de veces que este pasa por un estado final.
     *
     * @return Cantidad de veces que el AFD paso por un estado final, i.e. cuantas veces el patron aparecio en el texto.
     */
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
     * i.e. resultado de ejecucion parcial del AFD para optimizar la construccion de la funcion de transicion.
     *
     * @param stf     Referencia a funcion de transicion del automata, en construccion
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