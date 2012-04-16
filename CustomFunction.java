import java.util.StringTokenizer;
import java.util.ArrayList;

public class CustomFunction implements FormulaBlock{

    private String name ;
    private int args;
    private String[] coms;
    private int[] argpos;
    private Calculator calc;

    public CustomFunction(String name, String definer, Calculator master)
	throws Exception{	

	StringTokenizer arguments = new StringTokenizer(definer,"#");
	int start = 0;
	if(definer.substring(0,1).equals("#")){
	    coms = new String[arguments.countTokens()+1];
	    argpos = new int[coms.length];
	    start = 1;
	    coms[0] = "";
	    argpos[0] = Integer.parseInt(definer.substring(1,2))-1;
	    if(argpos[0] < 0){
		throw new Exception();
	    }
	    if(argpos[0]+1 > args){
		args = argpos[0]+1;
	    }
	} else {
	    coms = new String[arguments.countTokens()];
	    argpos = new int[coms.length-1];
	}
	args = 0;
	for(int i=start; i<coms.length; i++){
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
	this.name = name;
	this.calc = master;

	//System.out.println(definer+"   "+this.toString());

	// test the validity, if expanding fails, it throws an exception
	Variable[] dummies = new Variable[args];
	for(int i=0; i<args; i++){
	    dummies[i] = new Variable("0",0);
	}
	expand(new VariableSet("x",dummies));
	
    }

    public String getName(){
	return name;
    }

    public int getArgumentCount(){
	return args;
    }

    public int[] getArgumentPositions(){
	return argpos;
    }

    public String getExpression(String[] arguments){
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

    public String toString(){
	String[] dummies = new String[args];
	for(int i=0; i<args; i++){
	    dummies[i] = "#"+i;
	}
	return getExpression(dummies);
    }

    public boolean isOperator(){
	return false;
    }

    public boolean isCustomFunction(){
	return true;
    }


    public ArrayList<FormulaBlock> expand(FormulaBlock arg)
	throws Exception{

	Variable[] av = new Variable[args];
	try{
	    if(args != 1){
		throw new Exception();
	    }
	    av[0] = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == args){
		for(int i=0; i<args; i++){
		    av[i] = list.getElement(i);
		}
	    } else {
		System.out.println(this.name+" cannot operate on "+list);
		throw new Exception();
	    }
	}

	String[] dummies = new String[args];
	for(int i=0; i<args; i++){
	    dummies[i] = "00000000000"+i;
	}
	ArrayList<FormulaBlock> expression = calc.splitExpression(this.getExpression(dummies));
	for(int i=0; i<expression.size(); i++){
	    try{
		Variable tested = (Variable)(expression.get(i));
		for(int j=0; j<args; j++){
		    if(tested.getName().equals("00000000000"+j)){
			expression.set(i,av[j].copy());
		    }
		}
	    } catch(Exception error){
	    }
	}
	return expression;

    }

    public String getSyntax(String[] arguments){
	String info = "";
	info += name+Operator.SINGLEOP;
	if(args > 1){
	    info += "{";
	    for(int i=0; i<args; i++){
		info += arguments[i];
		if(i<args-1){
		    info += ",";
		}
	    }
	    info += "}";
	} else {
	    info += arguments[0];
	}
    	return info;
    }

}