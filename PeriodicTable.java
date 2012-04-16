public class PeriodicTable{

    private Element[] elements;
    private boolean[] signed;
    public static final int SPACE = 200;
    public static final int UNKNOWN = 120;
    public static final String UNKNOWNNAME = "Atom";

    public PeriodicTable(){
	elements = new Element[SPACE];
	signed = new boolean[SPACE];
	for(int i=0; i<SPACE; i++){
	    elements[i] = new Element(i);
	    if(i < UNKNOWN){
		signed[i] = true;
	    } else {
		signed[i] = false;
	    }
	}
    }

    public void expand(){
	int newspace = elements.length+100;
	Element[] tempelements = new Element[newspace];
	boolean[] tempsign = new boolean[newspace];
	for(int i=0; i<newspace; i++){
	    if(i<elements.length){
		tempelements[i] = elements[i];
		tempsign[i] = signed[i];
	    } else {
		tempelements[i] = new Element(i);
		tempsign[i] = false;
	    }
	}
	elements = new Element[newspace];
	signed = new boolean[newspace];
	for(int i=0; i<newspace; i++){
	    elements[i] = tempelements[i];
	    signed[i] = tempsign[i];
	}
    }

    public Element getElement(int index){
	return elements[index];
    }

    public Element getElement(String symbol){
	for(int i=1; i<elements.length; i++){ // predefined
	    if(elements[i].getName().equalsIgnoreCase(symbol) ||
	       elements[i].getSymbol().equalsIgnoreCase(symbol)){
		//System.out.println("found old "+i+" "+symbol);
		return elements[i];
	    }
	}
	// new element
	if(signed[elements.length-1]){
	    this.expand();
	}
	for(int i=UNKNOWN; i<elements.length; i++){
	    if(!signed[i]){
		//System.out.println("found new "+i+" "+symbol);
		elements[i].setName(symbol);
		elements[i].setSymbol(symbol);
		signed[i] = true;
		return elements[i];
	    }
	}
	// should not happen
	return elements[elements.length-1];
    }

    public int fill(){
	for(int i=UNKNOWN; i<elements.length; i++){
	    if(!signed[i]){
		return i;
	    }
	}
	return elements.length;
    }

    public boolean isAssigned(int ind){
	return signed[ind];
    }

    public int size(){
	return elements.length;
    }

    public int findNumber(String name){
	for(int i=1; i<elements.length; i++){
	    if(elements[i].getName().equalsIgnoreCase(name) ||
	       elements[i].getSymbol().equalsIgnoreCase(name)){
		return i;
	    }
	}
	return -1;
    }

    public void substituteElement(int index, Element ele){
	elements[index] = ele;
    }

}