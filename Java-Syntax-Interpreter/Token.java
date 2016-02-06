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
}
