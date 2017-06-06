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
     * Simplifica la definicion de transiciones para estados como int y caracteres como char.
     *
     * @param q int que codifica estado actual
     * @param a char que codifica caracter actual
     * @param p int que codifica estado siguiente
     */
    public void easy_set_transition(int q, char a, int p) {
        m_hashMap.put(
                new InstantDescription(new State(q), new AFDCharacter(a)),
                new State(p));
    }

    public State get_nextState(InstantDescription i) {
        return m_hashMap.get(i);
    }
}
