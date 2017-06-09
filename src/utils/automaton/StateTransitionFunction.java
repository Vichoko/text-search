package utils.automaton;

import java.util.HashMap;

/**
 * Clase que codifica la tabla de transiciones (Q x E) -> Q
 */
public class StateTransitionFunction {
    private HashMap<InstantDescription, State> m_hashMap;

    /**
     * Funcion de transicion se modela com un HashMap (Q x E) -> Q.
     * i.e. de InstantDescription a State.
     */
    public StateTransitionFunction() {
        m_hashMap = new HashMap<>();
    }

    /**
     * Registrar una transicion en el AFD.
     *
     * @param i Descripcion instantanea del AFD.
     * @param q Estado al cual debe moverse el AFD.
     */
    public void set_transition(InstantDescription i, State q) {
        m_hashMap.put(i, q);
    }

    /**
     * Simplifica la definicion de transiciones para estados como int y caracteres como char. Para evitar tanta
     * verbosidad.
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

    /**
     * Dado una InstantDescription, retornar estado siguiente que se espera.
     * Este metodo se supone que es llamado siempre que exista dicha transicion. Esto dado que al ser una funcion, se espera que toda transicion este bien definida.
     *
     * @param i Descripcion instantea del AFD.
     * @return Estado al cual se movera el AFD dado i.
     */
    public State get_nextState(InstantDescription i) {
        return m_hashMap.get(i);
    }
}
