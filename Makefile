build:
	javac fa/*.java fa/dfa/*.java

run:
	make build && java fa.dfa.DFADriver $(FILE)

test:
	make build && java fa.dfa.DFATester

clean:
	rm -rf fa/*.class && rm -rf fa/dfa/*.class 