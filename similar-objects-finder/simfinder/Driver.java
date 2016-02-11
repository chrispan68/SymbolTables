package simfinder;

public class Driver {
	public static void main(String[] args) {
		File f = new File(args[0]);
		Calculator c = new Calculator(Parser.parseFile(f));
	}
}