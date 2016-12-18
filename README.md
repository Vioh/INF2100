# INF2100 UiO (Autumn 2100)

Project for the INF2100 course at the University of Oslo (UiO). The assignment 
is to build a compiler for the Pascal2016 language (a "made-up" programming 
language that is a subset of the real Pascal language). The implementation 
language is Java. The entire progject is divided into 4 parts:
- Part 1: Build a scanner that creates a stream of tokens from the source file.
- Part 2: Build a parser which creates a syntax tree using object oriented programming style.
- Part 2: Build a checker which performs type checking and makes sure that the source code clearly follows the language grammars.
- Part 4: Build the code generator which generates Assembly code based on the abstract syntax tree.

To build the project from source, Apache Ant is required. The build process will
generate a `.jar` file. In the terminal, simply go to the `src` directory and type:
```bash
ant       # to build
ant clean # to clean the build
ant zip   # to zip the src directory
```

To test different parts of the compilers:
```bash
java -jar pascal2016.jar <pascal-src-file> -testscanner # test part 1
java -jar pascal2016.jar <pascal-src-file> -testparser  # test part 2
java -jar pascal2016.jar <pascal-src-file> -testchecker # test part 3
```

To run the compiler to compile a pascal source file:
```bash
java -jar pascal2016.jar <pascal-src-file>
```

Note that when not on IFI Linux machine at UiO, additional steps need to be done
in order to get the final binary file. If this is the case, make sure that you 
are sitting on a native Linux machine (cygwin does not work) with a gcc compiler
that can compile 32-bit x86 Assembly code. Then follow these steps:

- Inside the `src` directory, compile the library and the actual compiler like this:
```bash
ant
gcc -m32 -c libpas2016.c
ar rcs libpas2016.a libpas2016.o
```
	
- Let's say that you want to compile `test/primes.pas`, then inside the `test` 
directory, type the following:
```bash
java -jar ../src/pascal2016.jar primes.pas       # generate Assembly code
gcc -m32 -o primes primes.s -L ../src/ -lpas2016 # generate binary file
./primes                                         # running the pascal program
```