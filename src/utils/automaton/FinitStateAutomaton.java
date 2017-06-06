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

    public FinitStateAutomaton(State initialState, ArrayList<State> finalStates, StateTransitionFunction transition){
        m_initialState = initialState;
        m_finalStates = finalStates;
        m_transitionFunction = transition;
    }

    public boolean run(String input){
        char[] charArray = input.toCharArray();

        // initial state
        State currentState = m_initialState;

        for (char c : charArray){
            currentState = m_transitionFunction.get_nextState(
                    new InstantDescription(
                        currentState,
                        new Character(c)));
        }

        State finalState = currentState;
        return m_finalStates.contains(finalState);
    }
}

