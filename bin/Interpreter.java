/**   
Compilation: javac Interpreter.java
Execution: java Interpreter
Dependencies: Token.java

Java Syntax Interpreter
@author Alex Chen

Output:
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
 */

import java.util.HashMap;
import java.util.Scanner;

public class Interpreter {
    // binary operations
    public static final HashMap<Character,Token<BinaryOperation>> ops =
	(new SymbolTable()).getTable();
    public static final String EOF = "EOF",INT="INT";
    public static final char EMPTY = '\0';
    private String text;
    private int pos;
    private Token curr_token;
    private char curr_char;

    public Interpreter(String text) {
	this.text = text;
	pos = 0;
	curr_token = null;
	curr_char = text.charAt(pos);
    }
    
    public void error() {
	throw new Error("syntax error");  // later switch to Exceptions
	// because program can recover through exception handling
	// throw new Exception("syntax error");  // checked must be caught
	// "throws Exception" for each method that uses it
    }

    // increment position pointer
    public void increment() {
	pos++;
	if(pos > text.length() - 1) {
	    curr_char = EMPTY;	    
	} else {
	    curr_char = text.charAt(pos);
	}
    }
    
    public void skipSpace() {
	while(curr_char != EMPTY && Character.isSpace(curr_char))
	    increment();
    }

    public int extractInt() {
	String res = "";
	while(curr_char != EMPTY && Character.isDigit(curr_char)) {
	    res += curr_char;
	    increment();
	}

	return Integer.parseInt(res);
    }

    public Token next() {
	while(curr_char!=EMPTY) {
	    if(Character.isSpace(curr_char)) {
		skipSpace();
		continue;
	    }
	
	    if(Character.isDigit(curr_char)) {
		return new Token<Integer>(INT, extractInt());
	    }

	    if(ops.containsKey(curr_char)) {
		Token<BinaryOperation> res = ops.get(curr_char);
		increment();
		return res;
	    }	    	    
	}

	return new Token<Character>(EOF,EMPTY);
    }

    public void update() {
	curr_token = next();
    }

    public void eat(String name) {
	if(curr_token.name().equals(name)) {
	    update();
	} else {
	    error();
	}
    }

    public int nextInt() {
	Token<Integer> curr = (Token<Integer>)curr_token;
	eat(INT);
	return curr.symbol();
    }

    public int nextTerm() {
	int res = nextInt();

	while(curr_token.equals(ops.get('*')) || curr_token.equals(ops.get('/'))) {
	    BinaryOperation bop = (BinaryOperation)curr_token.symbol();	    
	    eat(curr_token.name());
	    res = bop.operate(res,nextInt());
	}	
	
	return res;
    }
    
    // calculator evaluates arithmetic expressions
    public int eval() {
	update();
	int res = nextTerm();

	//while(ops.containsValue(curr_token)) {	
	while(curr_token.equals(ops.get('+')) || curr_token.equals(ops.get('-'))) {
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
