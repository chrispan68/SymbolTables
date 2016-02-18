package simfinder;

import java.util.*;

public class Calculator {
    
    private Map<FieldValue, Set<String>> data;
    private Map<String, Set<String>> options;
    private String[] fields;
    
    /** Takes a mapping of object names/ids to mapping of
	Should fill out the fields and data variables. */
    public Calculator(ArrayList<InputObject> input) {
	data = new HashMap<FieldValue, Set<String>>();
	options = new HashMap<String, Set<String>>();
	Set<String> fieldset = new HashSet<String>(input.get(0).getFields().length);
	for(InputObject i : input) {
	    for (FieldValue f : i.getFields()) {
		if (!data.containsKey(f)) {
		    data.put(f, new HashSet<String>(input.size() / 2));
		}
		data.get(f).add(i.getId());
		fieldset.add(f.getHeader());
		// Add to options Map
		if (!options.containsKey(f.getHeader())) {
		    options.put(f.getHeader(), new HashSet<String>());
		}
		options.get(f.getHeader()).add(f.getValue());
	    }
	}
	fields = new String[fieldset.size()];
	fieldset.toArray(fields);
    }

    /** Awards points to schools that appear in the data for the given
	FieldValues */
    public String[] calculate(FieldValue[] target,int numOfResults) {
    	Map<String, Float> points = new HashMap<String, Float>();
        String [] results = new String[numOfResults];
        for(int i =0; i<target.length;i++){
            Set<String> matchedIds = data.get(target[i]);
            for(String currentId: matchedIds){
                if(points.containsKey(currentId)){
                        points.put(currentId,points.get(currentId)+1.0f); 
                    }else{                                               
                        points.put(currentId,1.0f);
                    }
                    setMaxes(results,points,currentId);
            }
        }
    	return results;
    }

    private void setMaxes(String[] currentResults,Map<String,Float> points,String currentId){
        for(String r: currentResults){
            if(points.get(r)!=null && points.get(currentId)>points.get(r)){
                r = currentId;
                break;

            }
        }
    }

    /** Return an array of blank FieldValues, copies from fields. */
    public FieldValue[] getBlankFields() {
	FieldValue[] a = new FieldValue[fields.length];
	for ( int i = 0; i < a.length; i++ ) {
	    a[i] = new FieldValue(fields[i]);
	}
	return a;
    }

    public String[] getOptionsFor(String header) {
	String[] values = new String[options.get(header).size()];
	options.get(header).toArray(values);
	return values;
    }
}
