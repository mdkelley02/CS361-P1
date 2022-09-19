package fa.dfa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

class DFATestString {
    boolean isAccepted;
    String string;

    public DFATestString(boolean isAccepted, String string) {
        this.isAccepted = isAccepted;
        this.string = string;
    }
}

class DFATestCase {
    public String startState;
    public String[] finalStates;
    public String[] remainingStates;
    public String[] transitions;
    public DFATestString[] testStrings;

    DFATestCase(String startState, String[] finalStates, String[] remainingStates, String[] transitions,
            DFATestString[] testStrings) {
        this.startState = startState;
        this.finalStates = finalStates;
        this.remainingStates = remainingStates;
        this.transitions = transitions;
        this.testStrings = testStrings;
    }
}

class DFATestCases {
    public static DFATestCase TestEmptyAlphabet() {
        String startState = "A";
        String[] finalStates = new String[] { "B" };
        String[] remainingStates = new String[] {};
        String[] transitions = new String[] {};
        DFATestString[] testStrings = new DFATestString[] {
                new DFATestString(false, ""),
                new DFATestString(false, "a"),
                new DFATestString(false, "b"),
                new DFATestString(false, "c"),
                new DFATestString(false, "ab"),
                new DFATestString(false, "ac"),
                new DFATestString(false, "bc"),
                new DFATestString(false, "abc"),
        };
        return new DFATestCase(startState, finalStates, remainingStates, transitions, testStrings);
    }

    public static DFATestCase TestThreeAlphabet() {
        return new DFATestCase(
                "A",
                new String[] { "B", "C" },
                new String[] { "D" },
                new String[] { "A0B", "A1A", "A2D", "B0A", "B0B", "B1C", "C0C", "C1B", "D0D", "D1D", "D2D" },
                new DFATestString[] {
                        new DFATestString(true, "0"),
                        new DFATestString(true, "00"),
                        new DFATestString(true, "000"),
                        new DFATestString(true, "0000"),
                        new DFATestString(true, "111110"),
                // new DFATestString(false, "2")
                });
    }

    public static DFATestCase TestTwoAlphabet() {
        return new DFATestCase(
                "A",
                new String[] { "B" },
                new String[] { "C" },
                new String[] { "A0B", "A1C", "B0A", "B1B", "C0C", "C1A" },
                new DFATestString[] {
                        new DFATestString(true, "0"),
                        new DFATestString(false, "00"),
                        new DFATestString(true, "000"),
                        new DFATestString(false, "0000"),
                // new DFATestString(false, "111110"),
                // new DFATestString(false, "2")
                });
    }

    public static DFATestCase TestOneAlphabet() {
        return new DFATestCase(
                "A",
                new String[] { "B" },
                new String[] { "C" },
                new String[] { "A0B", "B0A", "C0C" },
                new DFATestString[] {
                        new DFATestString(true, "0"),
                        new DFATestString(true, "00"),
                        new DFATestString(true, "000"),
                        new DFATestString(true, "0000"),
                        new DFATestString(true, "111110"),
                        new DFATestString(false, "2")
                });
    }

    public static DFATestCase TestSingleState() {
        return new DFATestCase(
                "A",
                new String[] { "A" },
                new String[] {},
                new String[] { "A0A", "A1A" },
                new DFATestString[] {
                        new DFATestString(true, "0"),
                        new DFATestString(true, "00"),
                        new DFATestString(true, "000"),
                        new DFATestString(true, "0000"),
                        new DFATestString(true, "111110"),
                        new DFATestString(false, "2")
                });
    }

}

public class DFATester {
    public HashMap<String, DFATestCase> testCases = new HashMap<String, DFATestCase>();

    public DFATester() {
        this.testCases.put("TestEmptyAlphabet", DFATestCases.TestEmptyAlphabet());
        this.testCases.put("TestThreeAlphabet", DFATestCases.TestThreeAlphabet());
        this.testCases.put("TestTwoAlphabet", DFATestCases.TestTwoAlphabet());
    }

    private DFATestCase parseTestFile(File testFile) {
        // first line is start state
        // second line is final states
        // third line is remaining states
        // fourth line is transitions
        // remaining lines are test strings
        Scanner scan = null;

        try {
            scan = new Scanner(testFile);
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }

        String startState = scan.nextLine();
        String[] finalStates = scan.nextLine().split(" ");
        String[] remainingStates = scan.nextLine().split(" ");
        String[] transitions = scan.nextLine().split(" ");

        ArrayList<DFATestString> testStrings = new ArrayList<DFATestString>();
        while (scan.hasNextLine()) {
            String string = scan.nextLine();
            boolean isAccepted = false;
            testStrings.add(new DFATestString(isAccepted, string));
        }

        scan.close();

        DFATestCase testCase = new DFATestCase(startState, finalStates, remainingStates, transitions,
                testStrings.toArray(new DFATestString[testStrings.size()]));

        return testCase;
    }

    public void run(boolean readFromFiles) {
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String RESET = "\033[0m";

        if (readFromFiles) {
            File testsDir = new File(
                    "/Users/matthewkelley/Desktop/CS361/P1/tests"); // navigate ../../tests
            File[] testFiles = testsDir.listFiles();
            for (File testFile : testFiles) {
                if (testFile.isFile()) {
                    String fileName = testFile.getName();
                    System.out.println("Running test: " + fileName);
                    this.testCases.put(fileName, this.parseTestFile(testFile));
                }
            }
        }

        for (Map.Entry<String, DFATestCase> pair : this.testCases.entrySet()) {
            int totalTests = 0;
            int totalPassed = 0;
            DFA dfa = new DFA();
            DFATestCase testCase = pair.getValue();
            for (String finalState : testCase.finalStates) {
                dfa.addFinalState(finalState);
            }
            dfa.addStartState(testCase.startState);
            for (String transition : testCase.transitions) {
                dfa.addTransition(
                        transition.substring(0, 1),
                        transition.charAt(1),
                        transition.substring(2, 3));
            }
            System.out.println(pair.getKey());

            for (DFATestString testString : testCase.testStrings) {
                totalTests++;
                boolean actual = dfa.accepts(testString.string);
                boolean failed = actual != testString.isAccepted;
                if (!failed) {
                    totalPassed++;
                }
                System.out.println(
                        String.format(
                                "String: %10s, Result: %10s, %5s",
                                testString.string,
                                this.formatResult(actual),
                                String.format("%s%s%s", failed ? RED : GREEN, failed ? "FAIL" : "PASS", RESET)));
            }
            System.out.println(
                    String.format(
                            "%d/%d passed\n",
                            totalPassed,
                            totalTests));
        }
    }

    private String formatResult(boolean result) {
        return result ? "ACCEPTED" : "REJECTED";
    }

    public static void main(String[] args) {
        DFATester tester = new DFATester();
        tester.run(true);
    }
}
