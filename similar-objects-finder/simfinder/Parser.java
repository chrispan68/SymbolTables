package simfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/** I'm not good at java input, so change the API if necessary. */

public class Parser {
	
    public static ArrayList<InputObject> parseFile(File file) {
        ArrayList<InputObject> data = new ArrayList<InputObject>();
    	FileReader in = new FileReader(file);
    	BufferedReader br = new BufferedReader(in);
    	data = new InputObject[numOfItems];
    	String [] headers = br.readLine().split("\t")
    	String [] currentLine;
    	while (currentLine = br.readLine().split("\t") != null) {
    		String id = currentLine[0];
    		String [] values = Arrays.copyOfRange(currentLine,1,currentLine.length());
       		data.add(InputObject(id,headers,values));
   		}
    	in.close();

        return data;

    } 


}
