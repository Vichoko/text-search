package utils.automaton;

/**
 * Un par (q,e), donde q es un State y e es un Character
 */
public class InstantDescription {
    private State m_state;
    private Character m_character;

    public InstantDescription(State s, Character c) {
        set_state(s);
        set_character(c);
    }

    public State get_state() {
        return m_state;
    }

    public Character get_character() {
        return m_character;
    }

    private void set_state(State m_state) {
        this.m_state = m_state;
    }


    private void set_character(Character m_character) {
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
