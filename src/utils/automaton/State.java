package utils.automaton;

/**
 * Estado corresponde a un numero entero entre 0 y el tamaño del patron.
 */
public class State {
    private int m_intState;

    public int get_state() {
        return m_intState;
    }

    private void set_state(int m_state) {
        this.m_intState = m_state;
    }

    public State(int rawState) {
        set_state(rawState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return m_intState == state.m_intState;
    }

    @Override
    public int hashCode() {
        return m_intState;
    }
}
