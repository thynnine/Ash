public class StringCombiner{
    
    public StringCombiner(){}

    public static String[] comboPermute(String[] first, String[] second, String delim){
	String[] fin = new String[first.length*second.length];

	for(int i=0; i<first.length; i++){
	    for(int j=0; j<second.length; j++){
		fin[i*second.length+j] = first[i]+delim+second[j];
	    }
	}
	return fin;
    }

    public static String[] combine(String[] first, String[] second){
	String[] fin = new String[first.length+second.length];

	for(int i=0; i<first.length; i++){
	    fin[i] = first[i];
	}
	for(int i=0; i<second.length; i++){
	    fin[first.length+i] = second[i];
	}
	return fin;
    }

}