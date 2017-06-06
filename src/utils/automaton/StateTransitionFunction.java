package utils.automaton;

import java.util.HashMap;

/**
 * Clase que codifica la tabla de transiciones (Q x E) -> Q
 */
public class StateTransitionFunction {
    private HashMap<InstantDescription, State> m_hashMap;

    public StateTransitionFunction() {
        m_hashMap = new HashMap<>();
    }

    public void set_transition(InstantDescription i, State q) {
        m_hashMap.put(i, q);
    }

    /**
     * wraps the object instantiation-.
     * @param q current state
     * @param a current character
     * @param p next state
     */
    public void easy_set_transition(int q, char a, int p) {
        m_hashMap.put(
                new InstantDescription(new State(q), new Character(a)),
                new State(p));
    }

    State get_nextState(InstantDescription i) {
        return m_hashMap.get(i);
    }
}
