import java.util.*;

public class Calculator{

    private ASH caller;
    private Hashtable<String,CustomFunction> functions;
    private static int recursionDepth = 0;

    public Calculator(ASH master){
	caller = master;
	functions = new Hashtable<String,CustomFunction>();
    }

    public ASH getMaster(){
	return caller;
    }

    public void defineFunction(String name, CustomFunction expression)
	throws Exception{
	if(Operator.isOperator(name)){
	    throw new Exception();
	} else {
	    functions.put(name,expression);
	}
    }

    public void removeFunction(String name)
	throws Exception{
	if(Operator.isOperator(name)){
	    throw new Exception();
	} else {
	    functions.remove(name);
	}
    }

    public CustomFunction getFunction(String name)
	throws Exception{
	if(functions.containsKey(name)){
	    return functions.get(name);
	} else {
	    throw new Exception();
	}
    }
    
    public Operator[] listOperators(){
	Operator[] oList = new Operator[Operator.OPERATORS.length];
	for(int i=0; i<Operator.OPERATORS.length; i++){
	    try{
		oList[i] = Operator.createOperator(Operator.OPERATORS[i],2,this);
	    } catch(Exception error){
		oList[i] = null;
	    }
	}
	return oList;
    }

    public CustomFunction[] listFunctions(){
	Enumeration<String> fList = functions.keys();
	String[] fNames = new String[0];
	int ind = 0;
	while(fList.hasMoreElements()){
	    String nextKey = fList.nextElement();
	    String[] keyAsList = {nextKey};
	    fNames = StringCombiner.combine(fNames,keyAsList);
	}
	Arrays.sort(fNames);
	CustomFunction[] fs = new CustomFunction[fNames.length];
	for(int i=0; i<fs.length; i++){
	    try{
		fs[i] = getFunction(fNames[i]);
	    } catch(Exception error){
		fs[i] = null;
	    }
	}
	return fs;
    }

    public ArrayList<FormulaBlock> splitExpression(String formula)
	throws Exception{
	
	StringTokenizer formulator = new StringTokenizer(formula,Operator.ALL_DELIMS,true);
	String[] bits = new String[formulator.countTokens()];
	for(int i=0; i<bits.length; i++){
	    bits[i] = formulator.nextToken();
	}


	ArrayList<FormulaBlock> maxblock = new ArrayList<FormulaBlock>();
	int blocks = 0;

	for(int i=0; i<bits.length; i++){
	    if( bits[i].equals(Operator.MINUS) ){
		if(i == 0){
		    // leading minus sign
		    //System.out.println("lead minus");
		    maxblock.add(Operator.createOperator(bits[i],1,this));
		    blocks++;
		    // minus sign starting a parentheses
		} else if( bits[i-1].equals(Operator.LP) ){
		    //System.out.println("parenthesis minus");
		    maxblock.add(Operator.createOperator(bits[i],1,this));
		    blocks++;
		    // minus following an operator -> must be something like 1*-1
		} else if( Operator.isOperator(bits[i-1]) ){
		    //System.out.println("operator following minus");
		    maxblock.add(Operator.createOperator(bits[i],1,this));
		    blocks++;
		    // minus sign following a single argument operator -> like, sin:-pi
		} else if( bits[i-1].equals(Operator.SINGLEOP) ){
		    //System.out.println("single operator argument minus");
		    maxblock.add(Operator.createOperator(bits[i],1,this));
		    blocks++;
		    // scientific format numbers such as 1.0E-1 with a minus in the exponent
		} else if( bits[i-1].substring(bits[i-1].length()-1,bits[i-1].length()).equalsIgnoreCase("E") ){
		    //System.out.println("E minus");
		    maxblock.remove(blocks-1);
		    maxblock.add(caller.parseVariable(bits[i-1]+bits[i]+bits[i+1]));
		    i++;
		    // if none of the above, it's actully a two-argument minus operator, like 2-1
		} else {
		    //System.out.println("two-arg minus");
		    maxblock.add(Operator.createOperator(bits[i],2,this));
		    blocks++;
		}
	    } else if( Operator.isDoubleOperator(bits[i]) ){
		maxblock.add(Operator.createOperator(bits[i],2,this));
		blocks++;
	    } else if( i<bits.length-2 && bits[i+1].equals(Operator.SINGLEOP) ){
		try{ // function?
		    Operator sop = Operator.createOperator(bits[i],1,this);
		    maxblock.add(sop);
		    blocks++;
		} catch (Exception error){
		    try{ // custom function?
			CustomFunction fnc = getFunction(bits[i]);
			maxblock.add(fnc);
			blocks++;
		    } catch(Exception error2){ // typo?	    
			System.out.println("unknown function "+bits[i]);
			throw new Exception();
		    }
		}
	    } else {		
		if(!bits[i].equals(Operator.SINGLEOP)){
		    if(Operator.isParenthesisOperator(bits[i])){
			maxblock.add(Operator.createOperator(bits[i],0,this));
			blocks++;
		    } else {
			maxblock.add(caller.parseVariable(bits[i]));
			blocks++;
		    }		    
		}
	    }
	}

	return maxblock;

    }

    public void substitute(ArrayList<FormulaBlock> formula,
			   FormulaBlock value, int lowRange, int highRange){
	for(int j=lowRange; j<highRange; j++){
	    formula.remove(lowRange);
	}
	formula.add(lowRange,value);
    }

    public void substitute(ArrayList<FormulaBlock> formula,
			   ArrayList<FormulaBlock> value, int lowRange, int highRange){
	for(int j=lowRange; j<highRange; j++){
	    formula.remove(lowRange);
	}
	for(int i=0; i<value.size(); i++){
	    formula.add(lowRange,value.get(i));
	}
    }

    public ArrayList<FormulaBlock> subExpression(ArrayList<FormulaBlock> formula,
						 int lowRange, int highRange){

	ArrayList<FormulaBlock> eval = new ArrayList<FormulaBlock>();
	for(int j=lowRange; j<highRange; j++){
	    eval.add(formula.get(j));
	}
	return eval;
    }

    public void writeFormula(ArrayList<FormulaBlock> formula){
	for(int i=0; i<formula.size(); i++){
	    System.out.print(formula.get(i).toString()+" ");
	}/**
	for(int i=0; i<formula.size(); i++){
	    if(formula.get(i).isOperator()){
		System.out.print(((Operator)formula.get(i)).getArgumentCount()+" ");
	    } else {
		System.out.print("x ");
	    }
	}
	 */
	System.out.println();
    }

    public VariableSet evaluateSet(ArrayList<FormulaBlock> bits)
	throws Exception{

	int elems = 1;
	int depth = 0;
	// count elements in the set
	for(int i=0; i<bits.size(); i++){
	    
	    if(bits.get(i).isOperator()){
		Operator op = (Operator)bits.get(i);
		if(op.getType().equals(Operator.LCP)){
		    depth++;
		} else if(op.getType().equals(Operator.RCP)){
		    depth--;
		} else if(op.getType().equals(Operator.COMMA)){
		    if(depth == 0){
			elems++;
		    }
		}
	    }
	}
	if(elems == 0){
	    System.out.println("empty sets not allowed");
	    throw new Exception();
	}
	
	Variable[] theset = new Variable[elems];
	int[][] cells = new int[elems][2];
	cells[0][0] = 0;
	cells[elems-1][1] = bits.size();

	// find the blocks defining the elements
	int index = 0;
	depth = 0;
	for(int i=0; i<bits.size(); i++){
	    if(bits.get(i).isOperator()){
		Operator op = (Operator)bits.get(i);
		if(op.getType().equals(Operator.LCP)){
		    depth++;
		} else if(op.getType().equals(Operator.RCP)){
		    depth--;
		} else if(op.getType().equals(Operator.COMMA)){
		    if(depth == 0){
			cells[index][1] = i;
			index++;
			cells[index][0] = i+1;
		    }
		}
	    }
	}

	// evaluate the elements
	for(int i=0; i<elems; i++){
	    theset[i] = evaluateExpression(subExpression(bits,cells[i][0],cells[i][1]));
	}
	
	VariableSet newset = new VariableSet("set",theset);
	return newset;

    }

    public Variable evaluateMatrix(ArrayList<FormulaBlock> bits)
	throws Exception{

	int dimension = 1;
	int width = 1;
	int height = 0;
	int depth = 1;
	int tempwidth = 1;
	boolean widthKnown = false;
	for(int i=0; i<bits.size(); i++){
	    if(bits.get(i).isOperator()){
		Operator op = (Operator)bits.get(i);
		if(op.getType().equals(Operator.LSP)){
		    depth++;
		    height++;
		    tempwidth = 1;
		    if(depth == 2){
			dimension = 2;
		    } else if(depth > 2){
			System.out.println("Matrices of more than 2 dimensions not supported.");
			throw new Exception();
		    }
		} else if(op.getType().equals(Operator.RSP)){
		    depth--;
		    if(depth < 1){
			System.out.println("Parenthesis mismatch in matrix definition.");
			throw new Exception();
		    }
		    if(widthKnown){
			if(tempwidth != width){
			    System.out.println("Inconsistent row lengths in matrix definition.");
			    throw new Exception();
			}
		    } else {
			width = tempwidth;
			widthKnown = true;
		    }
		} else if(op.getType().equals(Operator.COMMA)){
		    tempwidth++;
		}
	    }
	}
	if(!widthKnown){
	    width = tempwidth;
	    widthKnown = true;
	}
	// both [x,y] and [[x,y]] are valid notations for vectors, 
	// so we correct to latter if only single parentheses are used
	depth = 1;
	int heightstart = -1;
	if(dimension == 1){
	    height = 1;
	    heightstart = 0;
	}

	// create the new matrix variable
	double[][] dummy = new double[height][width];
	int[][][] cells = new int[height][width][2];

	//System.out.println("I think the matrix should be of size "+height+" x "+width);

	if(heightstart == 0){
	    cells[0][0][0] = 0;
	    cells[height-1][width-1][1] = bits.size();
	}
	
	// find and parse the cells
	int hindex = heightstart;
	int windex = 0;
	for(int i=0; i<bits.size(); i++){
	    if(bits.get(i).isOperator()){
		Operator op = (Operator)bits.get(i);
		if(op.getType().equals(Operator.LSP)){
		    windex = 0;
		    depth++;
		    hindex++;
		    cells[hindex][windex][0] = i+1;
		} else if(op.getType().equals(Operator.RSP)){
		    depth--;
		    cells[hindex][windex][1] = i;
		    windex++;
		} else if(op.getType().equals(Operator.COMMA)){
		    if(depth == dimension){
			cells[hindex][windex][1] = i;
			windex++;
			cells[hindex][windex][0] = i+1;
		    }
		}
	    }
	}
	for(int i=0; i<height; i++){
	    for(int j=0; j<width; j++){
		//System.out.println("I think cell ("+i+","+j+") is defined by blocks "+
		//cells[i][j][0]+" - "+cells[i][j][1]);
		Variable cellvar = evaluateExpression(subExpression(bits,cells[i][j][0],cells[i][j][1]));
		dummy[i][j] = cellvar.getValue();
	    }
	}

	Variable matrix = new Variable("mat",dummy);
	return matrix;
    }

    public void resetRecursionDepth(){
	recursionDepth = 0;
	//System.out.println("RD: "+recursionDepth);
    }

    public Variable evaluateExpression(ArrayList<FormulaBlock> bits)
	throws Exception{

	recursionDepth++;
	//System.out.println("rd: "+recursionDepth);
	if(recursionDepth > 1000){
	    System.out.println("Max recursion depth reached in function evaluation.");
	    throw new Exception();
	}

	//writeFormula(bits);
		
	if(bits.size() == 0){
	    throw new Exception();
	} else if(bits.size() == 1){
	    return (Variable)bits.get(0);
	}

	int lp = 0;
	int rp = 0;
	int depth = 0;
	// normal parentheses
	for(int i=0; i<bits.size(); i++){
	    if(bits.get(i).isOperator()){
		Operator op = (Operator)bits.get(i);
		if(op.getType().equals(Operator.LP)){
		    depth++;
		    if(depth == 1){
			lp = i; // left (
		    }
		} else if(op.getType().equals(Operator.RP)){
		    depth--;
		    if(depth == 0){
			rp = i; // right )
			substitute(bits,evaluateExpression(subExpression(bits,lp+1,rp)),lp,rp+1);
			i -= rp-lp+1;
		    }
		}
	    }
	}
	if(depth != 0){
	    System.out.println("mismatched parenthesis");
	    throw new Exception();
	}
	
	lp = 0;
	rp = 0;
	depth = 0;
	// square parentheses - these define vectors and matrices
	for(int i=0; i<bits.size(); i++){
	    if(bits.get(i).isOperator()){
		Operator op = (Operator)bits.get(i);
		if(op.getType().equals(Operator.LSP)){
		    depth++;
		    if(depth == 1){
			lp = i; // left [
		    }
		} else if(op.getType().equals(Operator.RSP)){
		    depth--;
		    if(depth == 0){
			rp = i; // right ]
			substitute(bits,evaluateMatrix(subExpression(bits,lp+1,rp)),lp,rp+1);
			i -= rp-lp+1;
		    }
		}
	    }
	}
	if(depth != 0){
	    System.out.println("mismatched square brackets.");
	    throw new Exception();
	}


	lp = 0;
	rp = 0;
	depth = 0;
	// curly parentheses - these sets (of arguments)
	for(int i=0; i<bits.size(); i++){
	    
	    if(bits.get(i).isOperator()){
		Operator op = (Operator)bits.get(i);
		if(op.getType().equals(Operator.LCP)){
		    depth++;
		    if(depth == 1){
			lp = i; // left {			
		    }
		    
		} else if(op.getType().equals(Operator.RCP)){
		    depth--;
		    
		    if(depth == 0){
			rp = i; // right }
			substitute(bits,evaluateSet(subExpression(bits,lp+1,rp)),lp,rp+1);
			i -= rp-lp+1;
		    }
		}
	    }
	}
	if(depth != 0){
	    System.out.println("mismatched curly brackets.");
	    throw new Exception();
	}
	
	if(bits.get(0).isOperator()){
	    Operator op = (Operator) bits.get(0);
	    if(op.isDoubleOperator()){
		throw new Exception();
	    }
	}

	// Single ops and powers
        int operators = 0;
	while(operators < 2){
	    
	    if(bits.size() == 1){
		return (Variable)bits.get(0);
	    }
	    for(int i=bits.size()-2; i>=0; i--){ // search backwards in the expression (2^3^2 = 2^9 etc.)
		if(bits.get(i).isOperator()){
		    Operator op = (Operator) bits.get(i);
		    switch(operators){
		    case 0: // single operators
			if(op.isSingleOperator()){
			    substitute(bits,op.operate(bits.get(i+1)), i,i+2);
			    i = -1; // Force the loop to restart
			} // power operators
			if(op.isPowerOperator()){			    
			    substitute(bits,op.operate(bits.get(i-1),bits.get(i+1)),
				       i-1,i+2);
			    i = -1;
			}
			break;
		    case 1: // power operators --- skipped now, done with single operators
			if(op.isPowerOperator()){			    
			    substitute(bits,op.operate(bits.get(i-1),bits.get(i+1)),
				       i-1,i+2);
			    i = -1;
			}
			break;
		    }
		} else if(bits.get(i).isCustomFunction()){ // custom function
		    CustomFunction fnc = (CustomFunction) bits.get(i);
		    if(operators == 0){
			ArrayList<FormulaBlock> expanded = fnc.expand(bits.get(i+1));
			substitute(bits, evaluateExpression(expanded), i,i+2);
			i = -1;
		    }
		} else {
		    if(i == 0){
			operators+=2;
		    }
		}
	    }
	}


	// multiplication, addition, comparison, logic
	while(operators < 6){
	    if(bits.size() == 1){
		return (Variable)bits.get(0);
	    }
	    if(bits.size() == 2){
		throw new Exception();
	    }
	    for(int i=1; i<bits.size()-1; i++){ // search forwards
		if(bits.get(i).isOperator()){
		    Operator op = (Operator) bits.get(i);
		    switch(operators){
		    case 2: // multi operators
			if(op.isMultiplierOperator()){			    
			    substitute(bits,op.operate(bits.get(i-1),bits.get(i+1)),
				       i-1,i+2);
			    i = -1;
			} else if(i == bits.size()-2){
			    operators++;
			}
			break;
		    case 3: // addition operators
			if(op.isAdditionOperator()){			    
			    substitute(bits,op.operate(bits.get(i-1),bits.get(i+1)),
				       i-1,i+2);
			    i = -1;
			} else if(i == bits.size()-2){
			    operators++;
			}
			break;
		    case 4: // comparison operators
			if(op.isComparisonOperator()){			    
			    substitute(bits,op.operate(bits.get(i-1),bits.get(i+1)),
				       i-1,i+2);
			    i = -1;
			} else if(i == bits.size()-2){
			    operators++;
			}
			break;
		    case 5: // logic operators
			if(op.isLogicOperator()){			    
			    substitute(bits,op.operate(bits.get(i-1),bits.get(i+1)),
				       i-1,i+2);
			    i = -1;
			} else if(i == bits.size()-2){
			    operators++;
			}
			break;
		    }
		} else {
		    if(i == bits.size()-2){
			operators++;
		    }
		}
	    }

	}

	return (Variable)bits.get(0);

    }


    public void accessManual(String com){
	try{
	    Operator mand = Operator.createOperator(com,2,this);
	    System.out.println("\nCalculator manual for '"+mand.getType()+"' ("+mand.getName()+")\n");
	    System.out.println("Syntax: "+mand.getSyntax());
	    System.out.println("\n"+mand.getUsage()+"\n");
	} catch(Exception error){
	    // check for custom function
	    try{
		CustomFunction alias = getFunction(com);		
		String[] args = new String[alias.getArgumentCount()];
		for(int i=0; i<args.length; i++){
		    args[i] = "#"+(i+1);
		}
		System.out.println("Calculator manual for '"+com+"'\n");
	    System.out.println("Syntax: "+alias.getSyntax(args));
		System.out.println("\nCustom function "+alias.getExpression(args)+"");
	    } catch(Exception error2){
		System.out.println("unknown function "+com);
	    }
	}	

    }

}