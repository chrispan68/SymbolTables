package simfinder;

/**
   Contains parsed input for a single object of data.
   TODO: accessor methods
*/
private class InputObject {
    
    FieldValue[] attributes;
    String id;

    /* headers map to values by index */
    public InputObject(String oid, String[] headers, String[] values) {
	if (headers.length != values.length) {
	    // Sanity Check
	    throw new RuntimeException("Mismatched headers and values!");
	}
	attributes = new FieldValue[headers.length];
	id = oid;
	for (int i = 0; i < attributes.length; i++) {
	    attributes[i] = new FieldValue(headers[i], values[i]);
	}
    }
    
}
