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
    public ArrayList<String> calculate(FieldValue[] target,int numOfResults) {
    	Map<String, Float> points = new HashMap<String, Float>();
	// Goes through each of the fieldValues for the client's
	// target attributes
        for(int i =0; i<target.length;i++){
	    // Gets the set of IDs of places which have the current fieldValue 
            Set<String> matchedIds = data.get(target[i]);
            // value (for example, all which have temperature value of cold.)
            for(String currentId: matchedIds){
		// If the college has already been added, adds on 1 point
                if(points.containsKey(currentId)){
                        points.put(currentId,points.get(currentId)+1.0f); 
                }else{
		    // if it hasn't creates it with 1 point.
                    points.put(currentId,1.0f);
                }
                
            }
        }
    	return setMaxes(points,numOfResults);
    }

    private ArrayList<String> setMaxes(Map<String,Float> points,int numOfResults){
	// changes all the keys of the hashmap into a String array
        String [] allIds = (String[])points.keySet().toArray(new String[points.size()]);
        ArrayList<String> results = new ArrayList<String>();
	// going through the IDs from the point hashmap
        for(int j =0;j<points.size();j++){
            Float rPoints = 0f, cPoints = points.get(allIds[j]);
	    // sets the current point value from the current item on
	    // the points hashmap
            int count = 0;
            boolean stillNeedsToAdd = true;
	    // makes it so if we have the number of results requested, it
	    // stop checking if it's greater.
            while(count+1<numOfResults && stillNeedsToAdd){
		// If the results is < the required num and we reach the
		// end of the results,just add it to the end.
                if(count==results.size()){
                    results.add(allIds[j]);
                    stillNeedsToAdd = false;
                }
		// if we aren't at the end of results, get the result points
		// to compare
                rPoints = points.get(results.get(count));
                if(cPoints >= rPoints){
		    // if the current points are greater than res points, add
		    // current points to the spot.
                    results.add(count,allIds[j]);
                    stillNeedsToAdd = false;
                }
		// Trims it down so we don't have more than the requested
		// number of results in the arrayList
                if(results.size()>numOfResults){
                    results.remove(results.size()-1);
                }
                count++;
            }   
        }
	//Just a little code to make sure it's working, adds on the results
	// to the points. Can remove later.
        for(int i =0;i<results.size();i++){
            results.set(i,results.get(i)+" - "+points.get(results.get(i)));
        }

        return results;
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
