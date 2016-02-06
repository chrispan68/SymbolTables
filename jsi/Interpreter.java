/**   
Compilation: javac Interpreter.java
Execution: java Interpreter
Dependencies: Token.java

Java Syntax Interpreter
@author Alex Chen

Output:
$ java Interpreter
>>> 2-2
0
>>> 2+2-2
2
>>> 2+4+3+2-2
9
>>> 22-7+  1
16
 */

import java.util.Scanner;

public class Interpreter {
    public static final String EOF = "EOF",INT="INT",PLUS="PLUS",MINUS="MINUS";
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
	throw new Error("input parsing error");	  // unchecked program cannot recover
	// may also use runtime error but this is discouraged

	//throw new Exception("parsing error");  // checked must be caught
	// program can recover through exception handling
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

	    if(curr_char=='+') {
		increment();
		return new Token<Character>(PLUS,'+');
	    }
	    
	    if(curr_char=='-') {
		increment();
		return new Token<Character>(MINUS,'-');
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
    
    // calculator evaluates arithmetic expressions
    public int eval() {
	update();
	int res = nextInt();

	// operation object
	/* while token is operation
	   eat operation
	   operate on result
	 */
	while(curr_token.name().equals(PLUS) || curr_token.name().equals(MINUS)) {
	    if(curr_token.name().equals(PLUS)) {
		eat(PLUS);
		res += nextInt();
	    } else {
		eat(MINUS);
		res -= nextInt();
	    }	       
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
