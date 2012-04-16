public class Variable implements FormulaBlock{

    private double[][] value;
    private String name;

    public Variable(String name, double value){
	this.value = new double[1][1];
	this.value[0][0] = value;
	this.name = name;
    }

    public Variable(String name, double[][] value){
	this.value = value;
	this.name = name;
    }

    public Variable copy(){
	Variable copy = new Variable(this.name,0);
	copy.value = new double[this.value.length][this.value[0].length];
	for(int i=0; i<this.value.length; i++){
	    for(int j=0; j<this.value[0].length; j++){
		copy.value[i][j] = this.value[i][j];
	    }
	}
	return copy;
    }

    public int getHeight(){
	return this.value.length;
    }

    public int getWidth(){
	return this.value[0].length;
    }

    public double[][] getMatrixValue(){
	return this.value;
    }

    public double getValue(){
	return this.value[0][0];
    }
    
    public double getValue(int i1, int i2){
	if(i1 >= 0 && i1 < this.value.length){
	    if(i2 >= 0 && i2 < this.value[i1].length){
		return this.value[i1][i2];
	    }
	}
	return 0.0;
    }

    public int getIntValue(){
	return (int)(this.value[0][0]+0.0000001);
    }

    public void setValue(double newv){
	this.value[0][0] = newv;
    }

    public void setValue(int newv){
	this.value[0][0] = (double)newv;
    }

    public String getName(){
	return this.name;
    }

    public String toString(){
	//return this.name;
	//return Double.toString(this.value);
	String info = "";
	if(this.value.length > 1 || this.value[0].length > 1){
	    info += "[ ";
	}
	for(int i=0; i<this.value.length; i++){
	    if(this.value.length > 1){
		info += "[ ";
	    }
	    for(int j=0; j<this.value[i].length; j++){
		info += FileHandler.formattedDouble(this.value[i][j],12,6);
		if(j < this.value[i].length-1){
		    info += ",";
		}
		info += " ";		
	    }
	    if(this.value.length > 1){
		info += "] ";
	    }

	}
	if(this.value.length > 1 || this.value[0].length > 1){
	    info += "]";
	}
	return info;
    }

    public String getMatrixString(){
	//return this.name;
	//return Double.toString(this.value);
	String info = "";
	for(int i=0; i<this.value.length; i++){
	    for(int j=0; j<this.value[i].length; j++){
		info += FileHandler.formattedDouble(this.value[i][j],12,8)+" ";
	    }
	    if(this.value.length > 1){
		info += "\n";
	    }

	}
	return info;
    }

    public boolean isOperator(){
	return false;
    }

    public boolean isCustomFunction(){
	return false;
    }

}