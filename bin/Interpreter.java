/**   
Compilation: javac Interpreter.java
Execution: java Interpreter

Java Syntax Interpreter
@author Alex Chen

Parser generates a syntax tree representing arithmetic expressions
Follows rules according to the Lexer's symbol table
Interpreter traverses the tree to evaluate the expression
Calculator supports all basic operations + - * / and parentheses ( )

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
    private Parser p;

    public Interpreter(Parser par) {
	p = par;
    }
    
    public void error() {
	throw new Error("Syntax Error");
    }

    public int visitBinOpNode(BinOpNode bon) {
	return bon.root().symbol().operate(visit(bon.left()),visit(bon.right()));
    }

    public int visitNumNode(NumNode nn) {
	return nn.value();
    }

    // generalize visit method
    public int visit(AST node) {
	Token t = node.root();
	if(Lexer.ops.containsValue(t)) {
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
