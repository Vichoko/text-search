package test;

import utils.automaton.*;

import java.util.ArrayList;

/**
 * Esta prueba verifica que el simulador de AFD implementado en utils.automaton funciona correctamente.
 */
public class AFDTest {
    public static void main(String[] args) {
        // simple run
        asserto(a_before_b("ababab"));
        asserto(!a_before_b("ababa"));
        asserto(a_before_b("ab"));
        asserto(!a_before_b("aba"));
        asserto(a_before_b(""));
        asserto(!a_before_b("a"));

        asserto(i_as_then_j_bs("aaaaabbbb"));
        asserto(i_as_then_j_bs("aaaaa"));
        asserto(i_as_then_j_bs(""));
        asserto(!i_as_then_j_bs("aaaaabbbba"));
        asserto(!i_as_then_j_bs("ba"));
        asserto(i_as_then_j_bs("bbbb"));

        // run and count
        asserto(count_a_before_b("ababab") == 4);
        asserto(count_a_before_b("ababa") == 3);
        asserto(count_a_before_b("ab") == 2);
        asserto(count_a_before_b("aba") == 2);
        asserto(count_a_before_b("") == 1);
        asserto(count_a_before_b("a") == 1);

        asserto(count_i_as_then_j_bs("aaaaabbbb") == 10);
        asserto(count_i_as_then_j_bs("aaaaa") == 6);
        asserto(count_i_as_then_j_bs("") == 1);
        asserto(count_i_as_then_j_bs("aaaaabbbba") == 10);
        asserto(count_i_as_then_j_bs("ba") == 2);
        asserto(count_i_as_then_j_bs("bbbb") == 5);
    }

    /**
     * Acepta si string solo tiene una a'a antes de una b.
     */
    private static boolean a_before_b(String input) {

        StateTransitionFunction stf = new StateTransitionFunction();
        stf.set_transition(
                new InstantDescription(new State(0), new AFDCharacter('a')),
                new State(1));
        stf.set_transition(
                new InstantDescription(new State(1), new AFDCharacter('b')),
                new State(0));

        State initialState = new State(0);
        State finalState = new State(0);
        ArrayList<State> finalStates = new ArrayList<>();
        finalStates.add(finalState);

        FinitStateAutomaton afd = new FinitStateAutomaton(initialState, finalStates, stf);
        return afd.run(input);
    }

    /**
     * Acepta si no hay una a despues de una b.
     */
    private static boolean i_as_then_j_bs(String input) {

        StateTransitionFunction stf = new StateTransitionFunction();
        stf.set_transition(
                new InstantDescription(new State(0), new AFDCharacter('a')),
                new State(0));
        stf.set_transition(
                new InstantDescription(new State(0), new AFDCharacter('b')),
                new State(1));
        stf.easy_set_transition(1, 'a', 2);
        stf.easy_set_transition(1, 'b', 1);
        stf.easy_set_transition(2, 'a', 2);
        stf.easy_set_transition(2, 'b', 2);

        State initialState = new State(0);
        State finalState = new State(1);
        ArrayList<State> finalStates = new ArrayList<>();
        finalStates.add(new State(1));
        finalStates.add(new State(0));

        FinitStateAutomaton afd = new FinitStateAutomaton(initialState, finalStates, stf);
        return afd.run(input);
    }

    /**
     * Cuenta las ocurrencias de una a antes de una b, cuenta el caso vacio.
     */
    private static int count_a_before_b(String input) {

        StateTransitionFunction stf = new StateTransitionFunction();
        stf.set_transition(
                new InstantDescription(new State(0), new AFDCharacter('a')),
                new State(1));
        stf.set_transition(
                new InstantDescription(new State(1), new AFDCharacter('b')),
                new State(0));

        State initialState = new State(0);
        State finalState = new State(0);
        ArrayList<State> finalStates = new ArrayList<>();
        finalStates.add(finalState);

        FinitStateAutomaton afd = new FinitStateAutomaton(initialState, finalStates, stf);
        return afd.run_n_count(input);
    }

    /**
     * Cuenta cada caracter que cumple condicion a^i*b^j, cuenta el caso vacio
     */
    private static int count_i_as_then_j_bs(String input) {

        StateTransitionFunction stf = new StateTransitionFunction();
        stf.set_transition(
                new InstantDescription(new State(0), new AFDCharacter('a')),
                new State(0));
        stf.set_transition(
                new InstantDescription(new State(0), new AFDCharacter('b')),
                new State(1));
        stf.easy_set_transition(1, 'a', 2);
        stf.easy_set_transition(1, 'b', 1);
        stf.easy_set_transition(2, 'a', 2);
        stf.easy_set_transition(2, 'b', 2);

        State initialState = new State(0);
        State finalState = new State(1);
        ArrayList<State> finalStates = new ArrayList<>();
        finalStates.add(new State(1));
        finalStates.add(new State(0));

        FinitStateAutomaton afd = new FinitStateAutomaton(initialState, finalStates, stf);
        return afd.run_n_count(input);
    }

    private static void asserto(Boolean result) {
        if (result) {
            System.out.println("    pass.");
            return;
        }
        throw new AssertionError();
    }
}
