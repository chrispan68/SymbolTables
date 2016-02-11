package simfinder;

import java.util.HashMap;
import java.util.HashSet;

public class Calculator {
    
    private Map<FieldValue, Set<String>> data;
    private String[] fields;
    
    /** Takes a mapping of object names/ids to mapping of
	Should fill out the fields and data variables. */
    public Calculator(ArrayList<InputObject> input) {
	data = new HashMap<FieldValue, Set<String>>();
	Set<FieldValue> fieldset = new HashSet<FieldValue>(input[0].getFields().length);
	for(InputObject i : input) {
	    for (FieldValue f : i.getFields()) {
		if (!data.containsKey(f)) {
		    data.put(f, new Set<String>(input.length / 2));
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
    public Map<String, Float> calculate(FieldValue[] target) {
    	Map<String, Float> points = new HashMap<String, Float>();

    	
    	
    	return points;
    }

    /** Return an array of blank FieldValues, copies from fields. */
    public FieldValue[] getBlankFields() {
	a = new FieldValue[fields.length];
	for ( int i = 0; i < a.length; i++ ) {
	    a[i] = new FieldValue(fields[i].getHeader());
	}
	return a;
    }
}
