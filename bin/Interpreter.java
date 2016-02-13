/**   
Compilation: javac Interpreter.java
Execution: java Interpreter

Java Syntax Interpreter
@author Alex Chen

Sample Output:
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
 */

import java.util.HashMap;
import java.util.Scanner;

public class Interpreter {
    public static final String EOF = "EOF",INT="INT",LPAR="LPAR",RPAR="RPAR";
    public static final char EMPTY = '\0';
    private Token curr_token;
    private Lexer lex;

    public Interpreter(String text) {
	lex = new Lexer(text);
	curr_token = lex.next();	
    }
    
    public void error() {
	throw new Error("Syntax Error");
    }

    public void update() {
	curr_token = lex.next();
    }

    public void eat(String name) {
	if(curr_token.name().equals(name)) {
	    update();
	} else {
	    error();
	}
    }

    public int nextInt() {
	if(curr_token.name().equals(INT)) {	    
	    Token<Integer> curr = (Token<Integer>)curr_token;
	    eat(INT);
	    return curr.symbol();
	}

	else if(curr_token.name().equals(LPAR)) {
	    eat(LPAR);
	    int res = eval();
	    eat(RPAR);
	    return res;
	}

	else {
	    error();
	    return 0;
	}
    }

    public int nextTerm() {
	int res = nextInt();

	while(curr_token.equals(Lexer.ops.get('*')) || curr_token.equals(Lexer.ops.get('/'))) {
	    BinaryOperation bop = (BinaryOperation)curr_token.symbol();	    
	    eat(curr_token.name());
	    res = bop.operate(res,nextInt());
	}	
	
	return res;
    }
    
    // calculator evaluates arithmetic expressions
    public int eval() {
	int res = nextTerm();
	
	while(curr_token.equals(Lexer.ops.get('+')) || curr_token.equals(Lexer.ops.get('-'))) {
	    BinaryOperation bop = (BinaryOperation)curr_token.symbol();	    
	    eat(curr_token.name());
	    res = bop.operate(res,nextTerm());
	}	
	
	return res;
    }

    public static void main(String[]args) {	
	Scanner in = new Scanner(System.in);
	while(true) {
	    System.out.print(">>> ");
	    String text = in.nextLine();
	    Interpreter z = new Interpreter(text);
	    int res = z.eval();
	    System.out.println(res);
	}
    }
}
