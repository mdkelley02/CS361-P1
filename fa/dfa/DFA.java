package fa.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DFA implements DFAInterface {
    private HashMap<String, DFAState> states = new HashMap<String, DFAState>();
    private Set<Character> alphabet = new HashSet<Character>();
    private Set<DFAState> finalStates = new HashSet<DFAState>();
    private String startStatekey;

    public void addStartState(String name) {
        if (!states.containsKey(name)) {
            this.addState(name);
        }
        this.startStatekey = name;
    }

    public void addState(String name) {
        this.states.put(name, new DFAState(name));
    }

    public void addState(DFAState state) {
        this.states.put(state.getName(), state);
    }

    private DFAState getState(String name) {
        if (!this.states.containsKey(name)) {
            this.addState(name);
        }
        return this.states.get(name);
    }

    public void addFinalState(String name) {
        DFAState state = this.getState(name);
        state.isFinal = true;
        this.finalStates.add(state);
    }

    public void addTransition(String fromState, char onSymb, String toState) {
        DFAState from = this.getState(fromState);
        from.addTransition(onSymb, this.getState(toState));
        this.alphabet.add(onSymb);
    }

    public Set<DFAState> getStates() {
        Set<DFAState> states = new HashSet<DFAState>(this.states.values());
        return states;
    }

    public Set<DFAState> getFinalStates() {
        return this.finalStates;
    }

    public DFAState getStartState() {
        return this.states.get(this.startStatekey);
    }

    public Set<Character> getABC() {
        return this.alphabet;
    }

    public boolean accepts(String s) {

        DFAState currentState = this.states.get(this.startStatekey);
        String[] inputs = s.split("");

        for (int i = 0; i < s.length(); i++) {
            if (currentState.transitions.containsKey(inputs[i])) {
                currentState = currentState.transitions.get(inputs[i]);
            } else {
                return false;
            }
        }
        return this.finalStates.contains(currentState);
    }

    public DFAState getToState(DFAState from, char onSymb) {
        return from.transitions.get(String.valueOf(onSymb));
    }

    private <T> String formatDeltaLine(T[] from) {
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

        deltaSb.append(String.format("\t%5s", "") + this.formatDeltaLine(this.alphabet.toArray()));
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
