package simfinder;

import java.util.*;

public class Calculator {
    
    private Map<FieldValue, Set<String>> data;
    private Map<String, Set<String>> options;
    private String[] fields;
    private String[] ids;
    
    /** Takes a mapping of object names/ids to mapping of
	Should fill out the fields and data variables. */
    public Calculator(ArrayList<InputObject> input) {
	data = new HashMap<FieldValue, Set<String>>();
	options = new HashMap<String, Set<String>>();
	Set<String> fieldset = new HashSet<String>(input.get(0).getFields().length);
	ids = new String[input.size()];
	// For each line of input
	for(int j = 0; j < input.size(); j++) {
	    InputObject i = input.get(j);
	    ids[j] = input.get(j).getId();
	    // For each item on that line
	    for (FieldValue f : i.getFields()) {
		// If the data-value pair is new, create it
		if (!data.containsKey(f)) {
		    data.put(f, new HashSet<String>(input.size() / 2));
		}
		// Add the InputObject id to the Set for the data-value pair
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
    public Map.Entry[] calculate(FieldValue[] target,int numOfResults) {
    	Map<String, Float> points = new HashMap<String, Float>();
	// Goes through each of the fieldValues for the client's
	// target attributes
	for (String id : ids) {
	    points.put(id, 0.0f);
	}
        for(int i =0; i<target.length;i++){
	    // Gets the set of IDs of places which have the current fieldValue 
            Set<String> matchedIds = data.get(target[i]);
            // value (for example, all which have temperature value of cold.)
            for(String currentId: matchedIds){
		// adds on 1 point
		points.put(currentId,points.get(currentId)+1.0f);
            }
        }
    	return getMaxes(points,numOfResults);
    }

    private class PointsComparator implements Comparator<Map.Entry<String, Float>> {
	public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
	    return (int)(o2.getValue() - o1.getValue());
	}
    }
    
    private Map.Entry[] getMaxes(Map<String,Float> points){
	Map.Entry<String, Float>[] entries = new Map.Entry[points.size()];
	points.entrySet().toArray(entries);
	Arrays.sort(entries, new PointsComparator());
	return entries;
    }

    private Map.Entry[] getMaxes(Map<String, Float> points, int numOfResults) {
	return Arrays.copyOf(getMaxes(points), Math.min(numOfResults, points.size()));
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
