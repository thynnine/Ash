public abstract class Operator implements FormulaBlock{

    // Operators
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String TIMES = "*";
    public static final String DIV = "/";
    public static final String MOD = "%";
    public static final String EQ = "=";
    public static final String LESS = "<";
    public static final String GTR = ">";
    public static final String AND = "&";
    public static final String OR = "|";
    public static final String POW = "^";
    public static final String LP = "(";
    public static final String RP = ")";
    public static final String LSP = "[";
    public static final String RSP = "]";
    public static final String LCP = "{";
    public static final String RCP = "}";
    public static final String NOT = "not";
    public static final String SIN = "sin";
    public static final String COS = "cos";
    public static final String TAN = "tan";
    public static final String ASIN = "asin";
    public static final String ACOS = "acos";
    public static final String ATAN = "atan";
    public static final String LOG = "ln";
    public static final String EXP = "exp";
    public static final String SQRT = "sqrt";
    public static final String ABS = "abs";
    public static final String RND = "rnd";
    public static final String FLOOR = "floor";
    public static final String FREEV = "free";
    public static final String PARTV = "coord";
    public static final String COL = "col";
    public static final String ROW = "row";
    public static final String ELEM = "elem";
    public static final String TR = "tp";
    public static final String UNIT = "unit";
    public static final String ZERO = "zero";
    public static final String SUBST = "insert";
    public static final String ARITH = "as";
    public static final String GEOM = "gs";
    public static final String NORM = "norm";
    public static final String DOT = "dot";
    public static final String CROSS = "cross";
    public static final String SINGLEOP = ":";
    public static final String COMMA = ",";
    public static final String ALL_DELIMS = PLUS+MINUS+TIMES+DIV+MOD+
	LP+RP+LSP+RSP+LCP+RCP+
	POW+EQ+LESS+GTR+AND+OR+SINGLEOP+COMMA;

    public static String[] OPERATORS = {PLUS,MINUS,TIMES,DIV,MOD,EQ,LESS,GTR,AND,OR,POW,
					NOT,SIN,COS,TAN,ASIN,ACOS,ATAN,LOG,EXP,SQRT,ABS,RND,
					FLOOR,FREEV,PARTV,COL,ROW,ELEM,TR,UNIT,ZERO,
					SUBST,ARITH,GEOM,NORM,DOT,CROSS};

    public static final int A_NOARG = 0;
    public static final int A_SINGLE = 1;
    public static final int A_DOUBLE = 2;

    protected String opType;
    protected int nArgs;
    protected int argType;
    protected String[] arguments;
    protected String name;
    protected String description;

    //protected Calculator master;
    //protected Structure geo;

    protected boolean isSingle = false;
    protected boolean isDouble = false;
    protected boolean isParenthesis = false;
    protected boolean isComparison = false;
    protected boolean isLogic = false;
    protected boolean isMultiplier = false;
    protected boolean isPower = false;
    protected boolean isAddition = false;

    public Operator(){
    }

    public boolean isOperator(){
	return true;
    }

    public boolean isCustomFunction(){
	return false;
    }

    public String getType(){
	return this.opType;
    }

    public int getArgType(){
	return this.argType;
    }
    
    public String toString(){
	return this.opType;
    }

    public int getArgumentCount(){
	return this.nArgs;
    }

    public abstract Variable operate(FormulaBlock arg);
    public abstract Variable operate(FormulaBlock a1, FormulaBlock a2);
    public boolean isSingleOperator(){return isSingle;}
    public boolean isDoubleOperator(){return isDouble;}
    public boolean isParenthesisOperator(){return isParenthesis;}
    public boolean isComparisonOperator(){return isComparison;}
    public boolean isLogicOperator(){return isLogic;}
    public boolean isMultiplierOperator(){return isMultiplier;}
    public boolean isPowerOperator(){return isPower;}
    public boolean isAdditionOperator(){return isAddition;}


    public static boolean isSingleOperator(String type){
	try{
	    return createOperator(type,1,null).isSingleOperator();
	} catch (Exception error){
	    return false;
	}
    }
    public static boolean isDoubleOperator(String type){
	try{
	    return createOperator(type,2,null).isDoubleOperator();
	} catch (Exception error){
	    return false;
	}
    }
    public static boolean isParenthesisOperator(String type){
	try{
	    return createOperator(type,0,null).isParenthesisOperator();
	} catch (Exception error){
	    return false;
	}
    }
    public static boolean isComparisonOperator(String type){
	try{
	    return createOperator(type,2,null).isComparisonOperator();
	} catch (Exception error){
	    return false;
	}
    }
    public static boolean isLogicOperator(String type){
	try{
	    return createOperator(type,2,null).isLogicOperator();
	} catch (Exception error){
	    return false;
	}
    }
    public static boolean isMultiplierOperator(String type){
	try{
	    return createOperator(type,2,null).isMultiplierOperator();
	} catch (Exception error){
	    return false;
	}
    }
    public static boolean isAdditionOperator(String type){
	try{
	    return createOperator(type,2,null).isAdditionOperator();
	} catch (Exception error){
	    return false;
	}
    }

    public static boolean isOperator(String type){
	try{
	    createOperator(type,1,null);
	    return true;
	} catch(Exception error){
	    return false;
	}
    }

    public static Operator createOperator(String type, int argcount, Calculator master)
	throws Exception{
	if(type.equals(PLUS)){
	    return new PlusOperator();
	} else if(type.equals(MINUS) && argcount == 1){
	    return new SingleMinusOperator();
	} else if(type.equals(MINUS) && argcount == 2){
	    return new MinusOperator();
	} else if(type.equals(TIMES)){
	    return new TimesOperator();
	} else if(type.equals(DIV)){
	    return new DividedOperator();
	} else if(type.equals(MOD)){
	    return new ModuloOperator();
	} else if(type.equals(POW)){
	    return new PowerOperator();
	} else if(type.equals(EQ)){
	    return new EqualsOperator();
	} else if(type.equals(LESS)){
	    return new LessThanOperator();
	} else if(type.equals(GTR)){
	    return new GreaterThanOperator();
	}  else if(type.equals(AND)){
	    return new LogicAndOperator();
	} else if(type.equals(OR)){
	    return new LogicOrOperator();
	} else if(type.equals(NOT)){
	    return new NotOperator();
	} else if(type.equals(SIN)){
	    return new SinOperator();
	} else if(type.equals(COS)){
	    return new CosOperator();
	} else if(type.equals(TAN)){
	    return new TanOperator();
	} else if(type.equals(ASIN)){
	    return new ArcsinOperator();
	} else if(type.equals(ACOS)){
	    return new ArccosOperator();
	} else if(type.equals(ATAN)){
	    return new ArctanOperator();
	} else if(type.equals(LOG)){
	    return new LogarithmOperator();
	} else if(type.equals(EXP)){
	    return new ExponentOperator();
	} else if(type.equals(SQRT)){
	    return new SquareRootOperator();
	} else if(type.equals(ABS)){
	    return new AbsoluteValueOperator();
	} else if(type.equals(RND)){
	    return new RandomVariableOperator();
	} else if(type.equals(TR)){
	    return new TransposeOperator();
	} else if(type.equals(FLOOR)){
	    return new FloorOperator();
	} else if(type.equals(PARTV)){
	    return new CoordinatesOperator(master.getMaster().getCurrentStructure());
	} else if(type.equals(FREEV)){
	    return new ConstraintsOperator(master.getMaster().getCurrentStructure());
	} else if(type.equals(COL)){
	    return new MatrixColumnOperator();
	} else if(type.equals(ROW)){
	    return new MatrixRowOperator();
	} else if(type.equals(UNIT)){
	    return new UnitMatrixOperator();
	} else if(type.equals(ZERO)){
	    return new ZeroMatrixOperator();
	} else if(type.equals(ELEM)){
	    return new MatrixElementOperator();
	} else if(type.equals(SUBST)){
	    return new MatrixSubstitutionOperator();
	} else if(type.equals(ARITH)){
	    return new ArithmeticSeriesOperator();
	} else if(type.equals(GEOM)){
	    return new GeometricSeriesOperator();
	} else if(type.equals(LP) ||
		  type.equals(RP) ||
		  type.equals(LSP) ||
		  type.equals(RSP) ||
		  type.equals(LCP) ||
		  type.equals(RCP) ||
		  type.equals(COMMA) ){
	    return new BracketOperator(type);
	} else if(type.equals(SINGLEOP)){
	    return new FunctionOperator();
	} else if(type.equals(NORM)){
	    return new VectorNormOperator();
	} else if(type.equals(DOT)){
	    return new VectorDotOperator();
	} else if(type.equals(CROSS)){
	    return new VectorCrossOperator();
	}
	throw new Exception();
    }

    public String getSyntax(){
	String info = "";
	if(argType == A_NOARG){
	    info += opType;
	} else if(argType == A_SINGLE){
	    if(opType.equals(MINUS)){
		info += opType+arguments[0];
	    } else {
		info += opType+SINGLEOP;
		if(nArgs > 1){
		    info += "{";
		    for(int i=0; i<nArgs; i++){
			info += arguments[i];
			if(i<nArgs-1){
			    info += ",";
			}
		    }
		    info += "}";
		} else {
		    info += arguments[0];
		}
	    }
	} else if(argType == A_DOUBLE){
	    info += arguments[0]+opType+arguments[1];
	}
	return info;
    }

    public String getName(){
	return this.name;
    }

    public String getUsage(){
	return this.description;
    }

}

class PlusOperator extends Operator{
    
    public PlusOperator(){
	super();
	this.opType = PLUS;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isAddition = true;

	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "addition";
	this.description = "Adds the values of two variables.";
    }

    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]+b[i][j];
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    result[i][j] = a[0][0]+b[i][j];
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]+b[0][0];
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix addition.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;    
	}
	
	return new Variable("r",result);
    }

}



class MinusOperator extends Operator{
    
    public MinusOperator(){
	super();
	this.opType = MINUS;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isAddition = true;

	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "subtraction";
	this.description = "Subtracts the value of one variable from the other.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]-b[i][j];
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    result[i][j] = a[0][0]-b[i][j];
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]-b[0][0];
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix subtraction.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;    
	}
	
	return new Variable("r",result);
    }

}



class TimesOperator extends Operator{
    
    public TimesOperator(){
	super();
	this.opType = TIMES;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isMultiplier = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "multiplication";
	this.description = "Multiplication of two variables. For matrices, normal matrix multiplication is done.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw == bh){ // matrix multiplication
	    result = new double[ah][bw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<bw; j++){
		    result[i][j] = 0;
		    for(int k=0; k<aw; k++){
			result[i][j] += a[i][k]*b[k][j];
		    }
		}
	    }
	} else if(aw==bw && ah==bh && aw != ah){ // element-wise multiplication
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]*b[i][j];
		}
	    }
	} else if(aw==1 && ah == 1){ // scalar multiplication
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    result[i][j] = a[0][0]*b[i][j];
		}
	    }
	} else if(bw==1 && bh == 1){ // scalar multiplication
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]*b[0][0];
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix multiplication.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}
	
	return new Variable("r",result);
    }

}


class DividedOperator extends Operator{
    
    public DividedOperator(){
	super();
	this.opType = DIV;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isMultiplier = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "addition";
	this.description = "Divides the value of one variable by the other.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]/b[i][j];
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    result[i][j] = a[0][0]/b[i][j];
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = a[i][j]/b[0][0];
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix division.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}
	
	return new Variable("r",result);
    }
}



class ModuloOperator extends Operator{
    
    public ModuloOperator(){
	super();
	this.opType = MOD;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isMultiplier = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "modulus";
	this.description = "Calculates the remainder of the integer division x/y.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = (int)a[i][j]%(int)b[i][j];
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    result[i][j] = (int)a[0][0]%(int)b[i][j];
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = (int)a[i][j]%(int)b[0][0];
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix modulus.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}

	return new Variable("r",result);
    }
}




class PowerOperator extends Operator{
    
    public PowerOperator(){
	super();
	this.opType = POW;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isPower = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "power";
	this.description = "Calculates the power function for arbitrary real base and exponent.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = Math.pow(a[i][j],b[i][j]);
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    result[i][j] = Math.pow(a[0][0],b[i][j]);
		}
	    }
	} else if(bw==1 && bh == 1 && aw != ah){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    result[i][j] = Math.pow(a[i][j],b[0][0]);
		}
	    }
	    // Matrix power
	} else if(bw==1 && bh == 1 && aw == ah){
	    result = new double[ah][aw];
	    double[][] c = new double[ah][aw];
	    // unit matrix
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(i==j){
			c[i][j] = 1;
		    } else {
			c[i][j] = 0;
		    }
		}
	    }
	    int power = (int)b[0][0];
	    for(int n=0; n<power; n++){
		    
		// Matrix multiplication
		for(int i=0; i<ah; i++){
		    for(int j=0; j<aw; j++){
			result[i][j] = 0;
			for(int k=0; k<aw; k++){
			    result[i][j] += a[i][k]*c[k][j];
			}
		    }
		}
		if(n < power){
		    // copy result to c for the next multiplication
		    for(int i=0; i<ah; i++){
			for(int j=0; j<aw; j++){
			    c[i][j] = result[i][j];
			}
		    }
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix power.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}

	return new Variable("r",result);
    }
}


class EqualsOperator extends Operator{
    
    public EqualsOperator(){
	super();
	this.opType = EQ;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isComparison = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "equality";
	this.description = "Compares two values. If the values are equal, the equality is given value 1, and 0 otherwise.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[1][1];
	    result[0][0] = 1;
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] != b[i][j]){
			result[0][0] = 0;
		    }
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    if(a[0][0] == b[i][j]){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    }
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] == b[0][0]){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    } 
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix comparison.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}

	return new Variable("r",result);
    }
}


class LessThanOperator extends Operator{
    
    public LessThanOperator(){
	super();
	this.opType = LESS;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isComparison = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "less than";
	this.description = "Compares two values. If the first is strictly less than the second, the comparison is given the value 1, and 0 otherwise.";
    }

    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[1][1];
	    result[0][0] = 1;
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] >= b[i][j]){
			result[0][0] = 0;
		    }
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    if(a[0][0] < b[i][j]){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    }
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] < b[0][0]){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    } 
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix comparison.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}

	return new Variable("r",result);
    }
}



class GreaterThanOperator extends Operator{
    
    public GreaterThanOperator(){
	super();
	this.opType = GTR;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isComparison = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "greater than";
	this.description = "Compares two values. If the first is strictly greater than the first, the expression is given value 1, and 0 otherwise.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[1][1];
	    result[0][0] = 1;
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] <= b[i][j]){
			result[0][0] = 0;
		    }
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    if(a[0][0] > b[i][j]){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    }
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] > b[0][0]){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    } 
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix comparison.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}

	return new Variable("r",result);
    }
}



class LogicAndOperator extends Operator{
    
    public LogicAndOperator(){
	super();
	this.opType = AND;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isLogic = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "logic and";
	this.description = "Logical comparison. If the two values both equal 1, the expression is given value 1, and 0 otherwise.";
    }

    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] != 1 || b[i][j] != 1){
			result[i][j] = 0;
		    } else {
			result[i][j] = 1;
		    }
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    if(a[0][0] != 1 || b[i][j] != 1){
			result[i][j] = 0;
		    } else {
			result[i][j] = 1;
		    }
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] != 1 || b[0][0] != 1){
			result[i][j] = 0;
		    } else {
			result[i][j] = 1;
		    }
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix logic.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}

	return new Variable("r",result);
    }
}



class LogicOrOperator extends Operator{
    
    public LogicOrOperator(){
	super();
	this.opType = OR;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
	this.isDouble = true;
	this.isLogic = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "x";
	this.arguments[1] = "y";
	this.name = "logic or";
	this.description = "Logical comparison. If either of the two values equals 1, the expression is given value 1, and 0 otherwise.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	
	double[][] a = ((Variable)a1).getMatrixValue();
	double[][] b = ((Variable)a2).getMatrixValue();
	int ah = a.length;
	int aw = a[0].length;
	int bh = b.length;
	int bw = b[0].length;

	double[][] result = {{Double.NaN}};

	if(aw==bw && ah==bh){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] == 1 || b[i][j] == 1){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    }
		}
	    }
	} else if(aw==1 && ah == 1){
	    result = new double[bh][bw];
	    for(int i=0; i<bh; i++){
		for(int j=0; j<bw; j++){
		    if(a[0][0] == 1 || b[i][j] == 1){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    }
		}
	    }
	} else if(bw==1 && bh == 1){
	    result = new double[ah][aw];
	    for(int i=0; i<ah; i++){
		for(int j=0; j<aw; j++){
		    if(a[i][j] == 1 || b[0][0] == 1){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    }
		}
	    }
	} else {
	    System.out.println("Incompatible dimensions in matrix logic.");
	    result = new double[0][0];
	    result[0][0] = Double.NaN;
	}

	return new Variable("r",result);
    }
}



class SingleMinusOperator extends Operator{
    
    public SingleMinusOperator(){
	super();
	this.opType = MINUS;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "sign change";
	this.description = "Changes the sign of the following expression. This is equivalent to '0-x', where the '-' is the subtraction operator.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = -a[i][j];
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class NotOperator extends Operator{
    
    public NotOperator(){
	super();
	this.opType = NOT;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "logic not";
	this.description = "The function 1-x. It changes a true value 1 to false 0, and vice versa.";
    }

    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = 1-a[i][j];
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}




class SinOperator extends Operator{
    
    public SinOperator(){
	super();
	this.opType = SIN;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "sine";
	this.description = "Evaluates the sine function for the given argument.";
    }

    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.sin(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class CosOperator extends Operator{
    
    public CosOperator(){
	super();
	this.opType = COS;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;	
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "cosine";
	this.description = "Evaluates the cosine function for the given argument.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.cos(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class TanOperator extends Operator{
    
    public TanOperator(){
	super();
	this.opType = TAN;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;	
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "tangent";
	this.description = "Evaluates the tangent function for the given argument.";

    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.tan(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class ArcsinOperator extends Operator{
    
    public ArcsinOperator(){
	super();
	this.opType = ASIN;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;	
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "inverse sine";
	this.description = "Evaluates the inverse sine function for the given argument.";

    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.asin(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class ArccosOperator extends Operator{
    
    public ArccosOperator(){
	super();
	this.opType = ACOS;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "inverse cosine";
	this.description = "Evaluates the inverse cosine function for the given argument.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.acos(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class ArctanOperator extends Operator{
    
    public ArctanOperator(){
	super();
	this.opType = ATAN;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "inverse tangent";
	this.description = "Evaluates the inverse tangent function for the given argument.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.atan(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class SquareRootOperator extends Operator{
    
    public SquareRootOperator(){
	super();
	this.opType = SQRT;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "square root";
	this.description = "Evaluates the square root for the given argument.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.sqrt(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class ExponentOperator extends Operator{
    
    public ExponentOperator(){
	super();
	this.opType = EXP;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "exponential";
	this.description = "Evaluates the exponential function for the given argument. This equivalent to 'e^x'.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.exp(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class AbsoluteValueOperator extends Operator{
    
    public AbsoluteValueOperator(){
	super();
	this.opType = ABS;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "absolute value";
	this.description = "Gives the absolute value of the given argument.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.abs(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class RandomVariableOperator extends Operator{
    
    public RandomVariableOperator(){
	super();
	this.opType = RND;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "random number";
	this.description = "Generates a uniformly distributed random number from the range [0,x]. If x is a negative integer, a random integer from {0,...,-x} is generated instead.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		if(a[i][j] > 0.0){
		    result[i][j] = a[i][j]*ASH.RNG.nextDouble();
		} else {
		    result[i][j] = ASH.RNG.nextInt((int)(-a[i][j]));
		}
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class FloorOperator extends Operator{
    
    public FloorOperator(){
	super();
	this.opType = FLOOR;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "floor";
	this.description = "Gives the largest integer smaller than the given argument. For positive numbers, this is just the integer part of the number.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.floor(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class LogarithmOperator extends Operator{
    
    public LogarithmOperator(){
	super();
	this.opType = LOG;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "x";
	this.name = "logarithm";
	this.description = "Gives the natural logarithm of the given argument.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a.length][a[0].length];
	
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[i][j] = Math.log(a[i][j]);
	    }
	}
	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class CoordinatesOperator extends Operator{

    private Structure geo;
    
    public CoordinatesOperator(Structure str){
	super();
	geo = str;
	this.opType = PARTV;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "particle";
	this.name = "coordinates";
	this.description = "Gives the coordinates of the particle of the given index, in the currently displayed geometry, as a column vector.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = {{Double.NaN}};

	if(a.length == 1){
	    result = new double[3][a[0].length];
	    for(int i=0; i<a[0].length; i++){
		try{
		    Vector coordinates = geo.getParticle((int)a[0][i]).getCoordinates();
		    result[0][i] = coordinates.element(0);
		    result[1][i] = coordinates.element(1);
		    result[2][i] = coordinates.element(2);
		} catch(Exception error){
		    for(int j=0; j<3; j++){
			result[j][i] = Double.NaN;
		    }
		}	    
	    }
	} else if(a[0].length == 1){
	    result = new double[a.length][3];
	    for(int i=0; i<a.length; i++){
		try{
		    Vector coordinates = geo.getParticle((int)a[i][0]).getCoordinates();
		    result[i][0] = coordinates.element(0);
		    result[i][1] = coordinates.element(1);
		    result[i][2] = coordinates.element(2);
		} catch(Exception error){
		    for(int j=0; j<3; j++){
			result[i][j] = Double.NaN;
		    }
		}
	    }
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class ConstraintsOperator extends Operator{

    private Structure geo;
    
    public ConstraintsOperator(Structure str){
	super();
	geo = str;
	this.opType = FREEV;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "particle";
	this.name = "constraints";
	this.description = "Gives the constraint flags of the particle of the given index, in the currently displayed geometry, as a row vector.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = {{Double.NaN}};

	if(a.length == 1){
	    result = new double[3][a[0].length];
	    for(int i=0; i<a[0].length; i++){
		try{
		    boolean[] freedom = ((Atom)geo.getParticle((int)a[0][i])).getFrozenCoordinates();
		    for(int j=0; j<3; j++){
			if(freedom[j]){
			    result[j][i] = 1.0;
			} else {
			    result[j][i] = 0.0;
			}
		    }
		} catch(Exception error){
		    for(int j=0; j<3; j++){
			result[j][i] = Double.NaN;
		    }
		}
	    }
	} else if(a[0].length == 1){
	    result = new double[a.length][3];
	    for(int i=0; i<a.length; i++){
		try{
		    boolean[] freedom = ((Atom)geo.getParticle((int)a[0][i])).getFrozenCoordinates();
		    for(int j=0; j<3; j++){
			if(freedom[j]){
			    result[i][j] = 1.0;
			} else {
			    result[i][j] = 0.0;
			}
		    }
		} catch(Exception error){
		    for(int j=0; j<3; j++){
			result[i][j] = Double.NaN;
		    }
		}	    
	    }
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}




class TransposeOperator extends Operator{
    
    public TransposeOperator(){
	super();
	this.opType = TR;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "matrix";
	this.name = "transpose";
	this.description = "Transposes the given matrix.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double[][] result = new double[a[0].length][a.length];
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result[j][i] = a[i][j];
	    }
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class MatrixColumnOperator extends Operator{
    
    public MatrixColumnOperator(){
	super();
	this.opType = COL;
	this.nArgs = 2;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "matrix";
	this.arguments[1] = "index";
	this.name = "column extraction";
	this.description = "Extracts the given column of the given matrix. If a vector is given as an index, a new matrix is formed from the specified columns.";
    }


    public Variable operate(FormulaBlock arg){

	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}
	
	double[][] a1 = av[0].getMatrixValue();
	double[][] a2 = av[1].getMatrixValue();

	double[][] result = new double[a2.length*a1.length][a2[0].length];
	for(int i=0; i<a2.length; i++){
	    for(int j=0; j<a2[0].length; j++){
		int col = (int)a2[i][j]-1;
		for(int k=0; k<a1.length; k++){
		    try{
			result[i*a1.length+k][j] = a1[k][col];
		    } catch(Exception error){
			result[i*a1.length+k][j] = Double.NaN;
		    }
		}
	    }
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class MatrixRowOperator extends Operator{
    
    public MatrixRowOperator(){
	super();
	this.opType = ROW;
	this.nArgs = 2;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "matrix";
	this.arguments[1] = "index";
	this.name = "row extraction";
	this.description = "Extracts the given row of the given matrix. If a vector is given as an index, a new matrix is formed from the specified rows.";
    }


    public Variable operate(FormulaBlock arg){

	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}
	
	double[][] a1 = av[0].getMatrixValue();
	double[][] a2 = av[1].getMatrixValue();

	double[][] result = new double[a2.length][a2[0].length*a1[0].length];
	for(int i=0; i<a2.length; i++){
	    for(int j=0; j<a2[0].length; j++){
		int row = (int)a2[i][j]-1;
		for(int k=0; k<a1[0].length; k++){
		    try{
			result[i][j*a1[0].length+k] = a1[row][k];
		    } catch(Exception error){
			result[i][j*a1[0].length+k] = Double.NaN;
		    }
		}
	    }
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}




class MatrixElementOperator extends Operator{
    
    public MatrixElementOperator(){
	super();
	this.opType = ELEM;
	this.nArgs = 3;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[3];
	this.arguments[0] = "matrix";
	this.arguments[1] = "row index";
	this.arguments[2] = "column index";
	this.name = "element extraction";
	this.description = "Extracts the given element of the given matrix. If a matrices is given as indices, a new matrix is formed from the specified elements.";
    }


    public Variable operate(FormulaBlock arg){

	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}
	
	double[][] a1 = av[0].getMatrixValue();
	double[][] a2 = av[1].getMatrixValue();
	double[][] a3 = av[2].getMatrixValue();

	int al = a2.length;
	int aw = a2[0].length;

	if(al != a3.length || aw != a3[0].length){
	    System.out.println("incompatible dimensions for matrix element extraction");
	    return new Variable("NaN",Double.NaN);	    
	}

	double[][] result = new double[al][aw];
	for(int i=0; i<al; i++){
	    for(int j=0; j<aw; j++){
		try{
		    result[i][j] = a1[(int)a2[i][j]-1][(int)a3[i][j]-1];
		} catch(Exception error){
		    result[i][j] = Double.NaN;
		}
	    }
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class MatrixSubstitutionOperator extends Operator{
    
    public MatrixSubstitutionOperator(){
	super();
	this.opType = SUBST;
	this.nArgs = 4;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[4];
	this.arguments[0] = "replacement";
	this.arguments[1] = "original";
	this.arguments[2] = "row index";
	this.arguments[3] = "column index";
	this.name = "element substitution";
	this.description = "Inserts the given value in an existing matrix at the specified position. Also matrices can be inserted, in which case the position specifies the cell where the upper left corner of the inserted matrix will go.";
    }


    public Variable operate(FormulaBlock arg){

	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}
	
	double[][] a1 = av[1].getMatrixValue(); // note, the argumet order has been swapped
	double[][] a2 = av[0].getMatrixValue();
	int a3 = av[2].getIntValue();
	int a4 = av[3].getIntValue();

	int al = a1.length;
	int aw = a1[0].length;

	double[][] result = new double[al][aw];
	for(int i=0; i<al; i++){
	    for(int j=0; j<aw; j++){
		if((i<a3-1 || i>=a3+a2.length-1) || 
		   (j<a4-1 || j>=a4+a2[0].length-1)){
		    result[i][j] = a1[i][j];
		} else {
		    result[i][j] = a2[i-a3+1][j-a4+1];
		}
	    }
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}




class UnitMatrixOperator extends Operator{
    
    public UnitMatrixOperator(){
	super();
	this.opType = UNIT;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "n";
	this.name = "unit matrix";
	this.description = "Creates a unit matrix of the given size, n x n.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}

	double[][] a = av.getMatrixValue();
	double[][] result = {{Double.NaN}};
	if(a.length == 1 && a[0].length == 1){
	    int aval = (int)a[0][0];
	    result = new double[aval][aval];
	    for(int i=0; i<aval; i++){
		for(int j=0; j<aval; j++){
		    if(i == j){
			result[i][j] = 1;
		    } else {
			result[i][j] = 0;
		    }
		}
	    }
	} else {
	    System.out.println(opType+" requires a scalar argument");
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}




class ZeroMatrixOperator extends Operator{
    
    public ZeroMatrixOperator(){
	super();
	this.opType = ZERO;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "n";
	this.name = "zero matrix";
	this.description = "Creates a zero matrix of the given size, n x n.";
    }


    public Variable operate(FormulaBlock arg){

	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}

	double[][] a = av.getMatrixValue();
	double[][] result = {{Double.NaN}};
	if(a.length == 1 && a[0].length == 1){
	    int aval = (int)a[0][0];
	    result = new double[aval][aval];
	    for(int i=0; i<aval; i++){
		for(int j=0; j<aval; j++){		    
		    result[i][j] = 0;
		}
	    }
	} else {
	    System.out.println(opType+" requires a scalar argument");
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class ArithmeticSeriesOperator extends Operator{
    
    public ArithmeticSeriesOperator(){
	super();
	this.opType = ARITH;
	this.nArgs = 3;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[3];
	this.arguments[0] = "start";
	this.arguments[1] = "delta";
	this.arguments[2] = "length";
	this.name = "arithmetic series";
	this.description = "Creates a vector of the given length, whose elements form an arithmetic series (start+i*delta)";
    }


    public Variable operate(FormulaBlock arg){

	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}

	double[][] a1 = av[0].getMatrixValue(); // start
	double[][] a2 = av[1].getMatrixValue(); // step
	int a3 = av[2].getIntValue(); // length
	if(a3 <= 0){
	    System.out.println("non-positive length for arithmetic series");
	    return new Variable("NaN",Double.NaN);	    
	}

	double[][] result = {{Double.NaN}};
	if(a1.length == 1){
	    if(a1[0].length == a2[0].length){
		result = new double[a3][a1[0].length];
		for(int i=0; i<a1[0].length; i++){
		    for(int j=0; j<a3; j++){
			result[j][i] = a1[0][i]+a2[0][i]*j;
		    }
		}
	    } else if(a2.length == 1 && a2[0].length == 1){
		result = new double[a3][a1[0].length];
		for(int i=0; i<a1[0].length; i++){
		    for(int j=0; j<a3; j++){
			result[j][i] = a1[0][i]+a2[0][0]*j;
		    }
		}
	    }
	} else if(a1[0].length == 1){
	    if(a1.length == a2.length){
		result = new double[a1.length][a3];
		for(int i=0; i<a1.length; i++){
		    for(int j=0; j<a3; j++){
			result[i][j] = a1[i][0]+a2[i][0]*j;
		    }
		}
	    } else if(a2.length == 1 && a2[0].length == 1){
		result = new double[a1.length][a3];
		for(int i=0; i<a1.length; i++){
		    for(int j=0; j<a3; j++){
			result[i][j] = a1[i][0]+a2[0][0]*j;
		    }
		}
	    }
	} else {
	    System.out.println("incorrect arguments for "+opType);
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}




class GeometricSeriesOperator extends Operator{
    
    public GeometricSeriesOperator(){
	super();
	this.opType = GEOM;
	this.nArgs = 3;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[3];
	this.arguments[0] = "start";
	this.arguments[1] = "ratio";
	this.arguments[2] = "length";
	this.name = "geometric series";
	this.description = "Creates a vector of the given length, whose elements form a geometric series (start*ratio^i)";
    }


    public Variable operate(FormulaBlock arg){

	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}

	double[][] a1 = av[0].getMatrixValue(); // start
	double[][] a2 = av[1].getMatrixValue(); // step
	int a3 = av[2].getIntValue(); // length
	if(a3 <= 0){
	    System.out.println("non-positive length for geometric series");
	    return new Variable("NaN",Double.NaN);	    
	}

	double[][] result = {{Double.NaN}};
	if(a1.length == 1){
	    if(a1[0].length == a2[0].length){
		result = new double[a3][a1[0].length];
		for(int i=0; i<a1[0].length; i++){
		    double factor = 1.0;
		    for(int j=0; j<a3; j++){
			factor *= a2[0][i];
			result[j][i] = a1[0][i]*factor;
		    }
		}
	    } else if(a2.length == 1 && a2[0].length == 1){
		result = new double[a3][a1[0].length];
		for(int i=0; i<a1[0].length; i++){
		    double factor = 1.0;
		    for(int j=0; j<a3; j++){
			factor *= a2[0][0];
			result[j][i] = a1[0][i]*factor;
		    }
		}
	    }
	} else if(a1[0].length == 1){
	    if(a1.length == a2.length){
		result = new double[a1.length][a3];
		for(int i=0; i<a1.length; i++){
		    double factor = 1.0;
		    for(int j=0; j<a3; j++){
			factor *= a2[i][0];
			result[i][j] = a1[i][0]*factor;
		    }
		}
	    } else if(a2.length == 1 && a2[0].length == 1){
		result = new double[a1.length][a3];
		for(int i=0; i<a1.length; i++){
		    double factor = 1.0;
		    for(int j=0; j<a3; j++){
			factor *= a2[0][0];
			result[i][j] = a1[i][0]*factor;
		    }
		}
	    }
	} else {
	    System.out.println("incorrect arguments for "+opType);
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}



class BracketOperator extends Operator{
    
    public BracketOperator(String type){
	super();
	this.opType = type;
	this.nArgs = 0;
	this.argType = A_NOARG;
	this.isParenthesis = true;
	// Info
	this.arguments = new String[0];
	this.name = "parenthesis";
	this.description = "Three kinds of parenthesis are distinguished: Normal ones '()' are used to guide the order of evaluation, square ones '[]' are used for defining matrices, and curly ones '{}' are used for defining lists of elements for functions.";
    }


    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}

class FunctionOperator extends Operator{
    
    public FunctionOperator(){
	super();
	this.opType = SINGLEOP;
	this.nArgs = 0;
	this.argType = A_NOARG;
	// Info
	this.arguments = new String[0];
	this.name = "function";
	this.description = "Not a function in itself, this symbol is used for separating function names from the arguments.";
    }

    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }
}


class VectorNormOperator extends Operator{
    
    public VectorNormOperator(){
	super();
	this.opType = NORM;
	this.nArgs = 1;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[1];
	this.arguments[0] = "vector";
	this.name = "vector norm";
	this.description = "Gives the euclidian norm of a vector.";
    }

    public boolean isSingleOperator(){return true;}

    public Variable operate(FormulaBlock arg){
	Variable av;

	try{
	    av = (Variable)arg;
	} catch (Exception error) {
	    VariableSet list = (VariableSet)arg;
	    if(list.countElements() == nArgs){
		av = list.getElement(0);
	    } else {
		System.out.println(opType+" cannot operate on "+list);
		return new Variable("NaN",Double.NaN);
	    }
	}
	
	double[][] a = av.getMatrixValue();
	double result = 0.0;
	for(int i=0; i<a.length; i++){
	    for(int j=0; j<a[0].length; j++){
		result += a[i][j]*a[i][j];
	    }
	}
	result = Math.sqrt(result);

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class VectorDotOperator extends Operator{
    
    public VectorDotOperator(){
	super();
	this.opType = DOT;
	this.nArgs = 2;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "vector1";
	this.arguments[1] = "vector2";
	this.name = "vector dot product";
	this.description = "Gives the standard dot product of two vectors.";
    }

    public boolean isSingleOperator(){return true;}

    public Variable operate(FormulaBlock arg){
	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}
	
	double[][] a1 = av[0].getMatrixValue();
	double[][] a2 = av[1].getMatrixValue();

	if(a1.length*a1[0].length != a2.length*a2[0].length){
	    System.out.println(opType+" can only operate on two equal sized vectors");
	    return new Variable("NaN",Double.NaN);
	}
	double[][] pairs = new double[a1.length*a1[0].length][2];
	int index = 0;
	for(int i=0; i<a1.length; i++){
	    for(int j=0; j<a1[0].length; j++){
		pairs[index][0] = a1[i][j];
		index++;
	    }
	}
	index = 0;
	for(int i=0; i<a2.length; i++){
	    for(int j=0; j<a2[0].length; j++){
		pairs[index][1] = a2[i][j];
		index++;
	    }
	}

	double result = 0.0;
	for(int i=0; i<pairs.length; i++){
	    result += pairs[i][0]*pairs[i][1];
	}

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}


class VectorCrossOperator extends Operator{
    
    public VectorCrossOperator(){
	super();
	this.opType = CROSS;
	this.nArgs = 2;
	this.argType = A_SINGLE;
	this.isSingle = true;
	// Info
	this.arguments = new String[2];
	this.arguments[0] = "vector1";
	this.arguments[1] = "vector2";
	this.name = "vector cross product";
	this.description = "Gives the standard cross product of two vectors.";
    }

    public boolean isSingleOperator(){return true;}

    public Variable operate(FormulaBlock arg){
	Variable[] av;

	VariableSet list = (VariableSet)arg;
	if(list.countElements() == nArgs){
	    av = list.getSet();
	} else {
	    System.out.println(opType+" cannot operate on "+list);
	    return new Variable("NaN",Double.NaN);
	}
	
	double[][] a1 = av[0].getMatrixValue();
	double[][] a2 = av[1].getMatrixValue();

	if(a1.length*a1[0].length != a2.length*a2[0].length ||
	   a1.length*a1[0].length != 3){
	    System.out.println(opType+" can only operate on two vectors of three elements");
	    return new Variable("NaN",Double.NaN);
	}
	double[][] pairs = new double[3][2];
	int index = 0;
	for(int i=0; i<a1.length; i++){
	    for(int j=0; j<a1[0].length; j++){
		pairs[index][0] = a1[i][j];
		index++;
	    }
	}
	index = 0;
	for(int i=0; i<a2.length; i++){
	    for(int j=0; j<a2[0].length; j++){
		pairs[index][1] = a2[i][j];
		index++;
	    }
	}

	double[][] result = new double[1][3];
	result[0][0] = pairs[1][0]*pairs[2][1] - pairs[2][0]*pairs[1][1];
	result[0][1] = pairs[2][0]*pairs[0][1] - pairs[0][0]*pairs[2][1];
	result[0][2] = pairs[0][0]*pairs[1][1] - pairs[1][0]*pairs[0][1];

	return new Variable("r",result);

    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}

/**
class DummyOperator extends Operator{
    
    public DummyOperator(){
	super();
	this.opType = PLUS;
	this.nArgs = 2;
	this.argType = A_DOUBLE;
    }

    public boolean isDoubleOperator(){return true;}
    public boolean isAdditionOperator(){return true;}

    public Variable operate(FormulaBlock arg){
	return new Variable("NaN",Double.NaN);
    }

    public Variable operate(FormulaBlock a1, FormulaBlock a2){
	return new Variable("NaN",Double.NaN);
    }

}
*/
