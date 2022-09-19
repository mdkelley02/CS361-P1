****************
* Deterministic Finite Automata
* CS361
* 09/19/2021
* Matthew Kelley, Andrew Doering
****************

OVERVIEW:

    DFA is an implementation of a deterministic finite automa. The class accepts an initial state, a set of final states,
    and a set of transitions.

INCLUDED FILES:

    * DFADriver.java - Driver class that tests the DFA class with a given DFA configuration file.
    * State.java - Abstract class that represents a state in a DFA.
    * FAInterface.java - Interface that defines the methods to set the DFA configuration.
    * DFAInterface.java - Interface that extends FAInterface and defines the methods to 
    * DFA.java - Concrete class that implements the DFAInterface.
    * DFAState.java - Concrete class that extends the State class.



COMPILING AND RUNNING:

    Compile the program
    $ make build
    build:
        javac fa/*.java fa/dfa/*.java

    Compile and run the program with a given configuration file
    $ make run tests/p1tc1.txt
    run:
        make build && java fa.dfa.DFADriver $(FILE) && make clean

    Compile and run test program
    $ make test
    test:
        make build && java fa.dfa.DFATester && make clean

    Clean the build files
    $ make clean
    clean:
        rm -rf fa/*.class && rm -rf fa/dfa/*.class 



PROGRAM DESIGN AND IMPORTANT CONCEPTS:
    The program implements a DFA. DFA's are inherently a graph, as such the program uses a directed graph data structure to represent the DFA.
    Each DFAState object represents a node in the graph. The DFAState object contains a map of transitions that represent the edges of the graph.
    The DFAState object also contains a boolean value that represents whether or not the state is a final state.

    To determine if the DFA accepts a given string the program starts at the intitial state and traverses it's edges based on the current character of the input string.
    once it reaches the end of the string, if the current state is defined as a final state then the string is accepted, otherwise it is rejected.

TESTING:
    To test the DFA, we wrote a test suite program which can optionally read in all configuration files in a given directory and test them all.
    In addition to testing from configuration files we also wrote a configuration file model class that can be used to mock a configuration file.
    The test suite program tests the dfa class by mocking how the driver class provided to us would inject the configuration into the dfa class.

    The DFA configuration cases we tested for are:
    * alphabet of length 0, 1, 2, and 3
    * a cyclic DFA
    * a non-cyclic DFA
    * a DFA with a single state
    




