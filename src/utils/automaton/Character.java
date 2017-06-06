package utils.automaton;

/**
 * Caracter del alfabeto, en este caso un char nativo.
 */
public class Character {
    private char m_character;

    public Character(char c) {
        set_character(c);
    }

    public char get_character() {
        return m_character;
    }

    private void set_character(char m_character) {
        this.m_character = m_character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Character character = (Character) o;

        return m_character == character.m_character;
    }

    @Override
    public int hashCode() {
        return (int) m_character;
    }
}
