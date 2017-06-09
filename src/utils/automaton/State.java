package utils.automaton;

/**
 * Estado corresponde a un numero entero entre 0 y el tamaño del patron.
 */
public class State {
    private int m_intState;

    /**
     * Estado de un AFD. Simplemente un Integer nativo.
     *
     * @param rawState Etiqueta del estado, correspondiente a un int.
     */
    public State(int rawState) {
        this.m_intState = rawState;
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
