package fa.dfa;

import fa.State;
import java.util.HashMap;

public class DFAState extends State {
    public boolean isFinal;
    public String fromState;
    public char onSymb;
    public HashMap<String, DFAState> transitions;

    DFAState(String name) {
        super();
        this.name = name;
        this.isFinal = false;
        this.transitions = new HashMap<String, DFAState>();
    }

    public void addTransition(char input, DFAState toState) {
        this.transitions.put(String.valueOf(input), toState);
    }
}
