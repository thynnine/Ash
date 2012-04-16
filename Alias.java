import java.util.StringTokenizer;

public class Alias{

    private int args;
    private String[] coms;
    private int[] argpos;

    //public static final String SPLITSYMBOL = "\\";

    public Alias(String combo){
	this.args = 0;
	this.coms = new String[1];
	this.coms[0] = combo;
	this.argpos = new int[0];
    }

    public Alias(String[] combo, int[] pos){
	this.args = combo.length-1;
	this.coms = combo;
	this.argpos = pos;
    }

    public Alias(String name, String definer)
	throws Exception{	
	/*
	StringTokenizer semicolons = new StringTokenizer(definer,SPLITSYMBOL);
	String combo = "";
	while(semicolons.hasMoreTokens()){
	    combo += semicolons.nextToken();
	    if(semicolons.hasMoreTokens()){
		combo += ";";
	    }
	}
	*/
	StringTokenizer arguments = new StringTokenizer(definer,"#");
	coms = new String[arguments.countTokens()];
	argpos = new int[coms.length-1];
	args = 0;
	for(int i=0; i<coms.length; i++){
	    String bit = arguments.nextToken();
	    if(i == 0){
		coms[i] = bit;
	    } else {
		coms[i] = bit.substring(1,bit.length());
		argpos[i-1] = Integer.parseInt(bit.substring(0,1))-1;
		if(argpos[i-1] < 0){
		    throw new Exception();
		}
		if(argpos[i-1]+1 > args){
		    args = argpos[i-1]+1;
		}
	    }
	}
	
    }

    public int getArgumentCount(){
	return args;
    }

    public int[] getArgumentPositions(){
	return argpos;
    }

    public String getCommand(String[] arguments){
	String alias = "";	
	for(int i=0; i<this.coms.length-1; i++){
	    alias += this.coms[i];
	    if(arguments.length > argpos[i]){
		alias += arguments[argpos[i]];
	    }
	}
	alias += this.coms[this.coms.length-1];
	int i=args;
	while(arguments.length > i){
	    alias += arguments[i]+" ";
	    i++;
	}
	return alias;
    }

}