package fa.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * The class implements the DFAInterface.java interface.
 * 
 * It represents a DFA and provides methods to add states, transitions, and
 * final states.
 * 
 * It also provides methods to check if a DFA is valid and if a string is in the
 * DFA's language.
 * 
 * @author Matthew Kelley, Andrew Doering
 * 
 */
public class DFA implements DFAInterface {
    private HashMap<String, DFAState> states = new HashMap<String, DFAState>();
    private Set<Character> alphabet = new HashSet<Character>();
    private Set<DFAState> finalStates = new HashSet<DFAState>();
    private String startStatekey;

    /**
     * Adds the initial state to the DFA instance
     * 
     * @param name is the label of the start state
     */
    public void addStartState(String name) {
        if (!states.containsKey(name)) {
            this.addState(name);
        }
        this.startStatekey = name;
    }

    /**
     * Adds a non-final, not initial state to the DFA instance
     * 
     * @param name is the label of the state
     */
    public void addState(String name) {
        this.states.put(name, new DFAState(name));
    }

    /**
     * Adds a state to the DFA
     * 
     * @param name is the label of the state
     */
    public void addState(DFAState state) {
        this.states.put(state.getName(), state);
    }

    /**
     * Gets or creates and returns a state from the states map
     * 
     * @param name is the label of the state
     * @return the state with the given name
     */
    private DFAState getState(String name) {
        if (!this.states.containsKey(name)) {
            this.addState(name);
        }
        return this.states.get(name);
    }

    /**
     * Adds a final state to the DFA
     * 
     * @param name is the label of the state
     */
    public void addFinalState(String name) {
        DFAState state = this.getState(name);
        state.isFinal = true;
        this.finalStates.add(state);
    }

    /**
     * Adds the transition to the DFA's delta data structure
     * 
     * @param fromState is the label of the state where the transition starts
     * @param onSymb    is the symbol from the DFA's alphabet.
     * @param toState   is the label of the state where the transition ends
     */
    public void addTransition(String fromState, char onSymb, String toState) {
        DFAState from = this.getState(fromState);
        from.addTransition(onSymb, this.getState(toState));
        this.alphabet.add(onSymb);
    }

    /**
     * Getter for Q
     * 
     * @return a set of states that FA has
     */
    public Set<DFAState> getStates() {
        Set<DFAState> states = new HashSet<DFAState>(this.states.values());
        return states;
    }

    /**
     * Getter for F
     * 
     * @return a set of final states that FA has
     */
    public Set<DFAState> getFinalStates() {
        return this.finalStates;
    }

    /**
     * Getter for q0
     * 
     * @return the start state of FA
     */
    public DFAState getStartState() {
        return this.states.get(this.startStatekey);
    }

    /**
     * Getter for Sigma
     * 
     * @return the alphabet of FA
     */
    public Set<Character> getABC() {
        return this.alphabet;
    }

    /**
     * Checks if the DFA is valid
     * 
     * @return true if the DFA is valid, false otherwise
     */
    public boolean accepts(String s) {

        DFAState currentState = this.states.get(this.startStatekey);
        String[] inputs = s.split("");

        for (int i = 0; i < s.length(); i++) {
            if (currentState.transitions.containsKey(inputs[i])) {
                // set current state to the next state
                currentState = currentState.transitions.get(inputs[i]);
            } else {
                // character is not a member of the alphabet
                return false;
            }
        }

        return currentState.isFinal;
    }

    /**
     * returns the next state from a given state and input
     * 
     * @param fromState is the label of the state where the transition starts
     * @param onSymb    is the symbol from the DFA's alphabet.
     * @return the next state
     * 
     */
    public DFAState getToState(DFAState from, char onSymb) {
        return from.transitions.get(String.valueOf(onSymb));
    }

    /**
     * util method to format the header of the delta table
     *
     * @return the formatted header of the delta table
     *
     */
    private <T> String formatDeltaHeader(T[] from) {
        String line = "";
        for (T s : from) {
            line += String.format("%5s", s);
        }
        return line.trim();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder deltaSb = new StringBuilder();

        sb.append(String.format("Q = %s\n", this.states.keySet().toString()));

        sb.append(String.format("Sigma = %s\n", this.alphabet.toString()));

        deltaSb.append(String.format("\t%5s", "") + this.formatDeltaHeader(this.alphabet.toArray()));
        deltaSb.append("\n");
        for (DFAState state : this.states.values()) {
            deltaSb.append(String.format("\t%s", state.getName()));
            for (Character c : this.alphabet) {
                DFAState toState = this.getToState(state, c);
                if (toState != null) {
                    deltaSb.append(String.format("%5s", toState.getName()));
                } else {
                    deltaSb.append(String.format("%5s", "-"));
                }
            }
            deltaSb.append("\n");
        }

        sb.append(String.format("delta =\n%s", deltaSb.toString()));

        sb.append(String.format("q0 = %s\n", this.startStatekey));

        sb.append(String.format("F = %s\n", this.finalStates.toString()));

        return sb.toString().replace("[", "{").replace("]", "}");
    }

}
