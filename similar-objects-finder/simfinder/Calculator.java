package simfinder;

public class Calculator {
    
    Map<FieldValue, Set<String>> data;
    Set<FieldValue> fields; // Fields with no value for getBlankFields
    
    /** Takes a mapping of object names/ids to mapping of
	Should fill out the fields and data variables. */
    public Calculator(InputObject[] input) {
	
    }

    /** Awards points to schools that appear in the data for the given
	FieldValues */
    public Map<String, Float> calculate(FieldValue[] target) {
	Map<String, Float> points = new HashMap<String, Float>();

	
	
	return points;
    }

    /** Return an array of blank FieldValues, copies from fields. */
    public FieldValue[] getBlankFields() {
	
    }
}
