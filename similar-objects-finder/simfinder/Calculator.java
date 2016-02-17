package simfinder;

import java.util.*;

public class Calculator {
    
    private Map<FieldValue, Set<String>> data;
    private String[] fields;
    
    /** Takes a mapping of object names/ids to mapping of
	Should fill out the fields and data variables. */
    public Calculator(ArrayList<InputObject> input) {
	data = new HashMap<FieldValue, Set<String>>();
	Set<String> fieldset = new HashSet<String>(input.get(0).getFields().length);
	for(InputObject i : input) {
	    for (FieldValue f : i.getFields()) {
		if (!data.containsKey(f)) {
		    data.put(f, new HashSet<String>(input.size() / 2));
		}
		data.get(f).add(i.getId());
		fieldset.add(f.getHeader());
	    }
	}
	fields = new String[fieldset.size()];
	fieldset.toArray(fields);
    }

    /** Awards points to schools that appear in the data for the given
	FieldValues */

    //Goes through each of the 'i' keys in data and through each of their 'j' attributes checking if the target is equal to it
    //If it is, it adds a point to the hashmap, depending on whether the Id exists in the hashmap or not it will put it new at 1 point, or
    //add onto the previous existing points.
    public Map<String, Float> calculate(FieldValue[] target) {
    	Map<String, Float> points = new HashMap<String, Float>();
        for(int i =0;i<data.size();i++){
            String currentId = data.get(i).getId(); //INCORRECT, did incorrectly assuming data was an arrayList
            FieldValue [] currentData = data.get(i).getFields(); //INCORRECT, did incorrectly assuming data was an arrayList
            for(int j=0;j<data.get(i).getFields().length;j++){ //INCORRECT, did incorrectly assuming data was an arrayList
                if(currentData[j].equals(target[j])){
                    if(points.containsKey(currentId)){
                        points.put(currentId,points.get(currentId)+1); //1 is random number of points that is added if it matches, not sure what 
                    }else{                                               //we want to do
                        points.put(currentId,1);
                    }
                }
            }
        }
    	
    	
    	return points;
    }

    /** Return an array of blank FieldValues, copies from fields. */
    public FieldValue[] getBlankFields() {
	FieldValue[] a = new FieldValue[fields.length];
	for ( int i = 0; i < a.length; i++ ) {
	    a[i] = new FieldValue(fields[i]);
	}
	return a;
    }
}
