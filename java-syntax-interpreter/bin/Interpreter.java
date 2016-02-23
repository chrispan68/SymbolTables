//package jsi;

/**   
Compilation: javac Interpreter.java
Execution: java Interpreter

Java Syntax Interpreter
@author Alex Chen

Parser generates a syntax tree representing arithmetic expressions
Follows rules according to the Lexer's symbol table
Interpreter traverses the tree to evaluate the expression
Calculator supports all basic operations + - * / and parentheses ( )

See README.md for more details on input format and sample output.
 */

import java.util.Scanner;

public class Interpreter {
    private Parser p;

    public Interpreter(Parser par) {
	p = par;
    }
    
    public void error() {
	throw new Error("Syntax Error");
    }

    /** operate on left and right children
     */
    public int visitBinOpNode(BinOpNode bon) {
	return bon.root().symbol().operate(visit(bon.left()),visit(bon.right()));
    }

    /** operate on single child
     */
    public int visitUnOpNode(UnOpNode uon) {
	return uon.root().symbol().operate(visit(uon.child()));
    }

    /** return integer value of node
     */
    public int visitNumNode(NumNode nn) {
	return nn.value();
    }

    /** traverses and evaluates abstract syntax tree representation of arithmetic expression
     */
    public int visit(AST node) {	
	Token t = node.root();
	String class_name = node.getClass().getName();
	if(class_name.equals("UnOpNode")) {
	    return visitUnOpNode((UnOpNode)node);
	} else if(class_name.equals("BinOpNode")) {
	    return visitBinOpNode((BinOpNode)node);
	} else if(t.name().equals("INT")) {
	    return visitNumNode(new NumNode(new Token<Integer>(t.name(),(int)t.symbol())));
	} else {
	    return 0;
	}
    }

    public int interpret() {
	AST syntax_tree = p.eval();
	return visit(syntax_tree);
    }
    
    public static void main(String[]args) {	
	Scanner in = new Scanner(System.in);
	while(true) {
	    System.out.print(">>> ");
	    String text = in.nextLine();
	    Lexer lex = new Lexer(text);
	    Parser p = new Parser(lex);
	    Interpreter z = new Interpreter(p);
	    int res = z.interpret();
	    System.out.println(res);
	}
    }
}
