import java.util.HashMap;

public class SymbolTable {
    public static final char[] symbols = {'+','-','*','/'};    
    public static final String[] names = {"PLUS","MINUS","MUL","DIV"};
    public static final BinaryOperation[] binary_ops = {
	new AddOp(),
	new SubtractOp(),
	new MulOp(),
	new DivOp()
    };

    private HashMap<Character,Token<BinaryOperation>> hm;
    private HashMap<String,BinaryOperation> tt;
    
    public SymbolTable() {
	hm = new HashMap<Character,Token<BinaryOperation>>();
	tt = new HashMap<String, BinaryOperation>();
	for(int i=0;i<names.length;i++) {
	    hm.put(symbols[i],new Token<BinaryOperation>(names[i],binary_ops[i]));
	    tt.put(names[i],binary_ops[i]);
	}
    }

    public HashMap<Character,Token<BinaryOperation>> getTable() {
	return hm;
    }

    public HashMap<String,BinaryOperation> tokenTable() {
	return tt;
    }
}
