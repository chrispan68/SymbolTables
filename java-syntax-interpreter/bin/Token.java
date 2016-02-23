/**
   A chunk of special characters corresponds to a Token.
   The Token has an ID name and a value which the Parser uses to build an AST.
 */
public class Token<Value> {
    private String name;
    private Value symbol;

    public Token(String name, Value symbol) {
	this.name = name;
	this.symbol = symbol;
    }

    public String name() {
	return name;
    }

    public Value symbol() {
	return symbol;
    }

    public String toString() {
	return "{"+name+", "+symbol+"}";
    }

    public boolean equals(Token<Value> t) {
	return name.equals(t.name());
    }
}
