build:
	javac fa/*.java fa/dfa/*.java

run:
	make build && java fa.dfa.DFADriver $(FILE) && make clean

test:
	make build && java fa.dfa.DFATester && make clean

clean:
	rm -rf fa/*.class && rm -rf fa/dfa/*.class 