package simfinder;

/**
   Field header and value data type
   Contains the String field identifier and the field value.
*/
private class FieldValue {
	private String header;
	private String value;
	private FieldValue(String header,String value){
		this.header = header;
		this.value = value;
	}

//Overrides equals method to make the FieldValue objects equal if the header and values are equal.
	public boolean equals(Object o){
		return header.equals((FieldValue)o.getHeader()) && value.equals((FieldValue)o.getValue());
	}
//Overrides hashcode method
	public int hashCode(){
		return header.hashCode() + value.hashCode();`
	}
	
	public String getHeader(){
		return header;
	}
	public String getValue(){
		return value;
	}

    
}
