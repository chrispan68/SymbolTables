package simfinder;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String[] args) throws IOException {
		File f = new File(args[0]);
		Calculator c = new Calculator(Parser.parseFile(f));
		FieldValue[] blankFields = c.getBlankFields();
		System.out.println("Please choose your preference for the following attribute.");
		for(FieldValue v: blankFields){
			String[] options = c.getOptionsFor(v.getHeader());
			System.out.println("Options for "+v.getHeader()+": ");
			for(String option: options){
				System.out.println(option);
			}
			v.setValue(getUserInput());
		}
		System.out.println("Number of returned results?");
		int numOfResults = Integer.parseInt(getUserInput());

		ArrayList<String> results = c.calculate(blankFields,numOfResults);
		print(results);
	}

	public static String getUserInput(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();

    }
    public static void print(ArrayList<String> res){
    	for(int i =0;i<res.size();i++){
    		System.out.println((i+1)+". "+res.get(i));
    	}
    }
}
