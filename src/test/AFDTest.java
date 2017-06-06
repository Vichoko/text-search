package test;


import utils.automaton.*;
import utils.automaton.Character;

import java.util.ArrayList;

public class AFDTest {
    public static void main(String[] args){
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
    }

    /**
     * Acepta si string solo tiene una a'a antes de una b.
     */
    private static boolean a_before_b(String input){

        StateTransitionFunction stf = new StateTransitionFunction();
        stf.set_transition(
                new InstantDescription(new State(0), new Character('a')),
                new State(1));
        stf.set_transition(
                new InstantDescription(new State(1), new Character('b')),
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
    private static boolean i_as_then_j_bs(String input){

        StateTransitionFunction stf = new StateTransitionFunction();
        stf.set_transition(
                new InstantDescription(new State(0), new Character('a')),
                new State(0));
        stf.set_transition(
                new InstantDescription(new State(0), new Character('b')),
                new State(1));

        stf.set_transition(
                new InstantDescription(new State(1), new Character('a')),
                new State(2));
        stf.set_transition(
                new InstantDescription(new State(1), new Character('b')),
                new State(1));

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

    private static void asserto(Boolean result){
        if (result){
            System.out.println("    pass.");
            return;
        }
        throw new AssertionError();
    }
}
