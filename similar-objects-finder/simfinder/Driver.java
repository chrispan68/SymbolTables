package simfinder;

import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String[] args) throws IOException {
		File f = new File(args[0]);
		Calculator c = new Calculator(Parser.parseFile(f));
	}
}
