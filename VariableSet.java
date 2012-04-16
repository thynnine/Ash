public class VariableSet implements FormulaBlock{

    private Variable[] set;
    private String name;

    public VariableSet(String name, Variable[] set){
	this.set = set;
	this.name = name;
    }

    public boolean isOperator(){
	return false;
    }

    public boolean isCustomFunction(){
	return false;
    }


    public String toString(){
	String info = "{ ";

	for(int i=0; i<set.length; i++){
	    info += set[i].toString();
	    if(i<set.length-1){
		info += ", ";
	    }
	}

	info += " }";
	return info;
    }

    public Variable[] getSet(){
	return this.set;
    }
    
    public int countElements(){
	return set.length;
    }

    public Variable getElement(int index){
	return set[index];
    }

}