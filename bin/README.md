# Java Syntax Interpreter
This is a Java-based interpreter that uses abstract syntax trees to parse and evaluate user input. The interpreter breaks down the code into chunks called *tokens,* and the parser then arranges the tokens into a syntax tree. The interpreter traverses each node of the tree and uses a symbol table (HashMap) to pair each token to its respective function. For example, a "+" character will be mapped to the addition operation. After the tree is completely traversed, the code will have been executed.

## Compilation
$ javac Interpreter.java
## Execution
$ java Interpreter

## Input
The Interpreter.java program uses a Scanner which takes in user input. At present time, the interpreter acts like a basic calculator, so type a valid arithmetic expression into the Scanner (using ( , ) , + , - , * , / ). The interpreter will then print out the value of the expression. The program will run indefinetely until you exit (Ctrl+C in terminal).

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