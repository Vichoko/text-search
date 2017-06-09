package utils.automaton;

/**
 * Un par (q,e), donde q es un State y e es un AFDCharacter
 */
public class InstantDescription {
    private State m_state;
    private AFDCharacter m_character;

    /**
     * Descripcion instantanea de un AFD es un par (Estado, Caracter) que indica:
     * 1. En que estado estoy.
     * 2. Que caracter estoy leyendo del input.
     *
     * @param s Estado actual.
     * @param c Caracter actual.
     */
    public InstantDescription(State s, AFDCharacter c) {
        set_state(s);
        set_character(c);
    }

    private void set_state(State m_state) {
        this.m_state = m_state;
    }

    private void set_character(AFDCharacter m_character) {
        this.m_character = m_character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstantDescription that = (InstantDescription) o;

        return m_state.equals(that.m_state) && m_character.equals(that.m_character);
    }

    @Override
    public int hashCode() {
        int result = m_state.hashCode();
        result = 31 * result + m_character.hashCode();
        return result;
    }
}
