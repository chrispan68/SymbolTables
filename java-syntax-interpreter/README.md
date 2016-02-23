# Java Syntax Interpreter
Arithmetic expressions and general computer programs can be represented by trees often called *abstract syntax trees* (AST). This program is a Java-based interpreter that uses AST's to parse and evaluate user input. The interpreter breaks down the code into chunks called *tokens,* and the parser then arranges the tokens into a syntax tree. The interpreter traverses each node of the tree and uses a symbol table (HashMap) to pair each token to its respective value or function. For example, the token for '2' has the integer value 2, and a '+' character between two integer tokens will be mapped to the addition operation. After the tree is completely traversed, the code will have been executed.

## Compilation
$ javac Interpreter.java
## Execution
$ java Interpreter

## Input
The Interpreter.java program uses a Scanner to read user input. The interpreter acts like a basic calculator, so type a valid arithmetic expression into the Scanner using ( , ) , + , - , * , /. The interpreter will then print out the value of the expression. The program will run indefinetely until you exit (Ctrl+C in terminal).

## Sample Session
```java
$ java Interpreter
>>> 2/2
1
>>> 2*   7
14
>>> 2  /5 +1*4
4
>>> 30/  2 * 6-  1
89
>>> 78*14+   3/3
1093
>>> 1*(2+3)
5
>>> 1* (2/(9   -7))
1
>>> 10*(2/(33 -(4*(2-2)) +6) + 1)
10
>>> 2+-2
0
>>> 2++2
4
```

## Other Notes
I could have used regular expressions but those would defeat the purpose: I wanted to build as much from scratch as possible. In the future, one feature I may add is the ability to parse self-modifying code just as allowed by lisp macros or python's lambda expressions.