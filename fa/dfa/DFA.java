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
        this.startStatekey = name;
        this.addState(name);
    }

    public void addState(String name) {
        this.states.put(name, new DFAState(name));
    }

    private DFAState getState(String name) {
        if (!this.states.containsKey(name)) {
            this.addState(name);
        }
        return this.states.get(name);
    }

    public void addFinalState(String name) {
        this.addState(name);

        DFAState state = this.getState(name);
        state.isFinal = true;
        this.finalStates.add(state);
    }

    public void addTransition(String fromState, char onSymb, String toState) {
        this.alphabet.add(onSymb);
        this.getState(fromState).addTransition(onSymb, this.states.get(toState));
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
        return currentState.isFinal;
    }

    public DFAState getToState(DFAState from, char onSymb) {
        return from.transitions.get(String.valueOf(onSymb));
    }

}
