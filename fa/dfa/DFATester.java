package fa.dfa;

class DFATestString {
    boolean accepted;
    String input;

    public DFATestString(boolean accepted, String input) {
        this.accepted = accepted;
        this.input = input;
    }
}

class DFATestCase {
    public String startState;
    public String[] finalStates;
    public String[] remainingStates;
    public String[] transitions;
    public DFATestString[][] testCases;

    DFATestCase(String startState, String[] finalStates, String[] remainingStates, String[] transitions,
            DFATestString[][] testCases) {
        this.startState = startState;
        this.finalStates = finalStates;
        this.remainingStates = remainingStates;
        this.transitions = transitions;
        this.testCases = testCases;
    }
}

public class DFATester {
    public DFATestCase[] testCases;

    public DFATester() {
        this.testCases = new DFATestCase[] {
                new DFATestCase(
                        "A",
                        new String[] { "B" },
                        new String[] {},
                        new String[] { "A0B", "A1A", "B0A", "B0B" },
                        new DFATestString[][] {
                                new DFATestString[] {
                                        new DFATestString(true, "0"),
                                        new DFATestString(true, "00"),
                                        new DFATestString(true, "000"),
                                        new DFATestString(true, "0000"),
                                        new DFATestString(true, "111110"),
                                        new DFATestString(false, "2")
                                },
                        }),
                new DFATestCase(
                        "A",
                        new String[] { "B", },
                        new String[] { "C" },
                        new String[] { "A0B", "A1A", "B0A", "B0C", "C0C", "C1B" },
                        new DFATestString[][] {
                                new DFATestString[] {
                                        new DFATestString(true, "0"),
                                        new DFATestString(true, "00"),
                                        new DFATestString(true, "000"),
                                        new DFATestString(true, "0000"),
                                        new DFATestString(true, "111110"),
                                        new DFATestString(false, "2")
                                },
                        }),

        };
    }

    public void run() {
        for (DFATestCase testCase : this.testCases) {
            DFA dfa = new DFA();
            for (String finalStae : testCase.finalStates) {
                dfa.addFinalState(finalStae);
            }
            dfa.addStartState(testCase.startState);
            for (String transition : testCase.transitions) {
                dfa.addTransition(
                        transition.substring(0, 1),
                        transition.charAt(1),
                        transition.substring(2, 3));
            }
            for (DFATestString[] testStrings : testCase.testCases) {
                for (DFATestString testString : testStrings) {
                    boolean accepted = dfa.accepts(testString.input);
                    if (accepted != testString.accepted) {
                        System.out.println("Test failed: " + testString.input);
                        System.out.println("Expected: " + testString.accepted);
                        System.out.println("Actual: " + accepted);
                        System.out.println(dfa);
                        return;
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        DFATester tester = new DFATester();
        tester.run();
    }
}
