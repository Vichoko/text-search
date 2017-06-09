package utils.automaton;

/**
 * Caracter del alfabeto, en este caso un char nativo.
 */
public class AFDCharacter {
    private char m_character;

    public AFDCharacter(char c) {
        this.m_character = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AFDCharacter character = (AFDCharacter) o;

        return m_character == character.m_character;
    }

    @Override
    public int hashCode() {
        return (int) m_character;
    }
}
