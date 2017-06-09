package utils.automaton;

import java.util.ArrayList;

/**
 * Automata finito determinista.
 * Soporta estados en {0,...,N}
 * Soporta alfabeto ASCII (char)
 */
public class FinitStateAutomaton {
    private State m_initialState;
    private ArrayList<State> m_finalStates;
    private StateTransitionFunction m_transitionFunction;

    /**
     * AFD queda determinado por su estado inicial, estados finales y funcion de transicion.
     * El alphabeto esta definido en variable global ALPHABET, y se necesita poblar mediante metodo estatico makeAlphabet.
     *
     * @param initialState State incial.
     * @param finalStates  Lista de States finales.
     * @param transition   Funcion de transicion completamente definida para los caracteres de ALPHABET y sus estados.
     */
    public FinitStateAutomaton(State initialState, ArrayList<State> finalStates, StateTransitionFunction transition) {
        m_initialState = initialState;
        m_finalStates = finalStates;
        m_transitionFunction = transition;
    }

    /**
     * Ejecuta el automata con el input entregado,
     *
     * @param input Cadena de caracteres sobre el cual el AFD se ejecutara.
     * @return True si el AFD acepta el input. False si no.
     */
    public boolean run(String input) {
        char[] charArray = input.toCharArray();

        // initial state
        State currentState = m_initialState;

        for (char c : charArray) {
            currentState = m_transitionFunction.get_nextState(
                    new InstantDescription(
                            currentState,
                            new AFDCharacter(c)));
        }

        State finalState = currentState;
        return m_finalStates.contains(finalState);
    }

    /**
     * Ejecuta el automata con el input entregado.
     *
     * @param input Cadena de caracteres sobre el cual el AFD se ejecutara.
     * @return Cantidad de veces que el AFD paso por algun estado final.
     */
    public int run_n_count(String input) {
        int counter = 0;
        char[] charArray = input.toCharArray();

        // initial state
        State currentState = m_initialState;
        if (m_finalStates.contains(currentState)) {
            counter++;
        }

        for (char c : charArray) {
            currentState = m_transitionFunction.get_nextState(
                    new InstantDescription(
                            currentState,
                            new AFDCharacter(c)));
            if (m_finalStates.contains(currentState)) {
                counter++;
            }
        }
        return counter;
    }
}

