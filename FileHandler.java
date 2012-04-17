import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FileHandler{

    public static File CWD;
    public static File[] PATH;

    // file formats
    public static final int F_XYZ = 1;
    public static final int F_POSCAR = 2;
    public static final int F_SCRIPT = 3;
    public static final int F_POSCAR4 = 4;
    public static final int F_ASE = 5;

    public static final int F_PNG = -1;
    public static final int F_JPG = -2;
    
    public static final int DECIMALS = 6;

    public static final String ELEM_SEP = "_";

    public FileHandler(){
    }

    public static void readPath(){
	String paths = System.getenv("PATH");
	StringTokenizer pathsep = new StringTokenizer(paths,":");
	PATH = new File[pathsep.countTokens()];
	int index = 0;
	while(pathsep.hasMoreTokens()){
	    try{
		PATH[index] = new File(pathsep.nextToken());
		//System.out.println(PATH[index]);
		index++;
	    } catch(Exception error){
		PATH[index] = null;
		index++;
	    }
	}
    }

    public Structure[] readXYZ(String filename)
	throws Exception{
	
	ArrayList<String> lines = readLines(filename);
	int unknowns = 0;
	ArrayList<Structure> geos = new ArrayList<Structure>();
	int lineCount = lines.size();
	int currentLine = 0;
	int startatoms = lineCount;

	while(currentLine < lineCount){
	    int atoms = 0;
	    try{
		atoms = Integer.parseInt(lines.get(currentLine).trim());
		startatoms = atoms;
		currentLine++;
	    } catch(Exception error){
		atoms = startatoms;
	    }
	    try{ // Check if the first line is a comment or not
		StringTokenizer splitter;
		splitter = new StringTokenizer(lines.get(currentLine).trim()," \t");
		String ele = splitter.nextToken();
		double x = Double.parseDouble(splitter.nextToken());
		double y = Double.parseDouble(splitter.nextToken());
		double z = Double.parseDouble(splitter.nextToken());		
	    } catch(Exception error){
		currentLine++;
	    }
	    //if(atoms < 1){
	    //System.out.println("Invalid xyz file");
	    //throw new Exception();
	    //}
	    Particle[] parts = new Particle[atoms];
	    for(int i=0; i<atoms; i++){
		StringTokenizer splitter;
		splitter = new StringTokenizer(lines.get(currentLine).trim()," \t");
		String ele = splitter.nextToken();
		//int eleIndex = ASH.elementTable.findNumber(ele);
		double x = Double.parseDouble(splitter.nextToken());
		double y = Double.parseDouble(splitter.nextToken());
		double z = Double.parseDouble(splitter.nextToken());
		Atom newAtom = new Atom(new Vector(x,y,z));

		Element theElement = ASH.elementTable.getElement(ele);
		newAtom.setElement(theElement);
		
		parts[i] = newAtom;
		currentLine++;
	    }
	    Structure newGeo = new Structure(parts);
	    newGeo.setName(filename);
	    geos.add(newGeo);
	}
	Structure[] geoList = new Structure[geos.size()];
	for(int i=0; i<geoList.length; i++){
	    geoList[i] = geos.get(i);
	}
	return geoList;
    }

    public Structure[] readPoscar(String filename)
	throws Exception{

	ArrayList<String> lines = readLines(filename);
	int lineCount = lines.size();
	double scaler = 0.0;
	int currentLine = 0;
	Vector[] axes = new Vector[3];
	String namer = filename;
	try{
	    scaler = Double.parseDouble((String)lines.get(currentLine));
	    currentLine++;
	} catch (Exception error){
	    namer = lines.get(currentLine);
	    currentLine++;
	    scaler = Double.parseDouble((String)lines.get(currentLine));
	    currentLine++;
	}
	for(int i=0; i<3; i++){
	    StringTokenizer cellSplitter = new StringTokenizer((String)lines.get(currentLine)," \t");
	    double[] axis = new double[3];
	    for(int j=0; j<3; j++){
		axis[j] = scaler*Double.parseDouble(cellSplitter.nextToken());		
	    }
	    axes[i] = new Vector(axis);
	    currentLine++;
	}
	boolean[] pbc = new boolean[3];
	for(int i=0; i<3; i++){
	    pbc[i] = true;
	}

	Element[] atomTypes = new Element[0];
	try{ // test for symbols (vasp 5)
	    StringTokenizer typeSplitter = new StringTokenizer((String)lines.get(currentLine)," \t");
	    Integer.parseInt(typeSplitter.nextToken());
	} catch(Exception error){ // vasp 5 here
	    StringTokenizer typeSplitter = new StringTokenizer((String)lines.get(currentLine)," \t");
	    atomTypes = new Element[typeSplitter.countTokens()];
	    int[] taken = new int[ASH.elementTable.size()]; // count reoccurring elements
	    for(int i=0; i<taken.length; i++){
		taken[i] = 0;
	    }
	    for(int i=0; i<atomTypes.length; i++){
		try{
		    String elem = typeSplitter.nextToken();
		    int index = ASH.elementTable.findNumber(elem);
		    //System.out.println(elem+" "+index);
		    if(index >= 0 && taken[index] > 0){
			elem = elem + ELEM_SEP + (taken[index]+1);
			atomTypes[i] = ASH.elementTable.getElement(elem);
			taken[index]++;
		    } else {
			atomTypes[i] = ASH.elementTable.getElement(elem);
			taken[atomTypes[i].getNumber()]++;
		    }
		} catch(Exception error2){
		    atomTypes[i] = ASH.elementTable.getElement(PeriodicTable.UNKNOWNNAME+i);
		}
	    }	
	    currentLine++;
	}

	StringTokenizer typeSplitter = new StringTokenizer((String)lines.get(currentLine)," \t");
	int[] atomCounts = new int[typeSplitter.countTokens()];
	int nAtoms = 0;
	for(int i=0; i<atomCounts.length; i++){
	    atomCounts[i] = Integer.parseInt(typeSplitter.nextToken());
	    nAtoms += atomCounts[i];
	}	
	currentLine++;

	if(atomTypes.length != atomCounts.length){
	    atomTypes = new Element[atomCounts.length];
	    for(int i=0; i<atomCounts.length; i++){
		atomTypes[i] = ASH.elementTable.getElement(PeriodicTable.UNKNOWNNAME+i);
	    }
	}

	boolean selective;
	boolean cartesian;

	if(((String)lines.get(currentLine)).substring(0,1).equalsIgnoreCase("S")){
	    selective = true;
	    currentLine ++;
	} else {
	    selective = false;
	}
	if(((String)lines.get(currentLine)).substring(0,1).equalsIgnoreCase("C")){
	    cartesian = true;
	    currentLine ++;
	} else {
	    cartesian = false;
	    currentLine ++;
	}

	Atom[] atoms = new Atom[nAtoms];
	int type = 0;
	Element theElement = atomTypes[type];
	int typeCount = 0;
	for(int i=0; i<nAtoms; i++){
	    StringTokenizer coordSplitter = new StringTokenizer((String)lines.get(currentLine)," \t");
	    double x = Double.parseDouble(coordSplitter.nextToken());
	    double y = Double.parseDouble(coordSplitter.nextToken());
	    double z = Double.parseDouble(coordSplitter.nextToken());
	    if(cartesian){
		atoms[i] = new Atom((new Vector(x,y,z)).times(scaler));
	    } else {
		atoms[i] = new Atom(axes[0].times(x).plus(axes[1].times(y)).plus(axes[2].times(z)));
	    }
	    if(selective){
		boolean[] frozen = new boolean[3];
		if(coordSplitter.countTokens() > 2){
		    for(int k=0; k<3; k++){
			if(coordSplitter.nextToken().substring(0,1).equalsIgnoreCase("T")){
			    frozen[k] = true;
			} else {
			    frozen[k] = false;
			}
		    }
		}
		atoms[i].setFrozenCoordinates(frozen);
	    }
	    try{
		//Element theElement = ASH.elementTable.getElement(PeriodicTable.UNKNOWN+type%ASH.elementTable.size());
		atoms[i].setElement(theElement);
	    } catch (Exception error){
	    }
	    typeCount++;
	    if(typeCount == atomCounts[type]){
		typeCount = 0;
		type++;
		if(type < atomTypes.length){
		    theElement = atomTypes[type];		    
		}
	    }
	    currentLine++;
	}	

	Structure theSystem = new Structure(atoms);
	theSystem.setName(namer);
	theSystem.setCell(axes,pbc);
	Structure[] lister = {theSystem};
	return lister;
    }

    public double[][] readData(String filename, int width)
	throws Exception{

	ArrayList<String> lines = readLines(filename);
	int lineCount = lines.size();

	double[][] data = new double[lineCount][width];
	
	for(int i=0; i<lineCount; i++){
	    StringTokenizer cells = new StringTokenizer(lines.get(i)," \t");
	    for(int j=0; j<width; j++){
		try{
		    data[i][j] = Double.parseDouble(cells.nextToken());
		} catch(Exception error){
		    data[i][j] = Double.NaN;
		}
	    }
	}

	return data;
	
    }

    public String[] formatASE(Structure geo){
	int nAtoms = geo.countAllAtoms();
	String[] lines = new String[15+2*nAtoms];
	lines[0] = "#! /usr/bin/env python";
	lines[1] = "import ase";
	lines[2] = "coordinates = [ \\";
	lines[3+nAtoms] = "symbols = [ \\";

	Atom[] all = geo.getAllAtoms();
	for(int i=0; i<nAtoms; i++){
	    lines[3+i] = "  [ ";
 	    for(int j=0; j<3; j++){
		lines[3+i] += formattedDouble(all[i].getCoordinates().element(j),DECIMALS+9,DECIMALS);
		if(j < 2){
		    lines[3+i] += ", ";
		} else {
		    lines[4+i+nAtoms] = "  '" + all[i].getName() + "'";
		    if(i < nAtoms-1){
			lines[3+i] += " ], \\";
			lines[4+i+nAtoms] += ", \\";
		    } else {
			lines[3+i] += " ] ] ";
			lines[4+i+nAtoms] += " ] ";
		    }
		}
	    }	    
	}

	lines[4+2*nAtoms] = "cell = [ \\";
	for(int i=0; i<3; i++){
	    lines[5+i+2*nAtoms] = "   [ ";
	    for(int j=0; j<3; j++){
		lines[5+i+2*nAtoms] += formattedDouble(geo.getCell()[i].element(j),DECIMALS+9,DECIMALS);
		if(j < 2){
		    lines[5+i+2*nAtoms] += ", ";		
		} else {
		    lines[5+i+2*nAtoms] += " ]";
		}
	    }
	    if(i < 2){
		lines[5+i+2*nAtoms] += ", \\";		
	    } else {
		lines[5+i+2*nAtoms] += " ] ";
	    }
	}

	lines[8+2*nAtoms] = "pbc = [ ";
	for(int i=0; i<3; i++){
	    if(geo.getBoundaryConditions()[i]){
		lines[8+2*nAtoms] += "True";
	    } else {
		lines[8+2*nAtoms] += "False";
	    }
	    if(i < 2){
		lines[8+2*nAtoms] += ", ";		
	    } else {
		lines[8+2*nAtoms] += " ] ";
	    }
	}

	lines[9+2*nAtoms] = "def system(use_symbols=True):";
	lines[10+2*nAtoms] = "  global coordinates, symbols";
	lines[11+2*nAtoms] = "  if use_symbols:";
	lines[12+2*nAtoms] = "    return ase.Atoms(symbols=symbols,positions=coordinates,cell=cell,pbc=pbc)";
	lines[13+2*nAtoms] = "  else:";
	lines[14+2*nAtoms] = "    return ase.Atoms(numbers=[1]*"+nAtoms+",positions=coordinates,cell=cell,pbc=pbc)";
	
	return lines;
    }

    public String[] formatXYZ(Structure geo){
	int nAtoms = geo.countAllAtoms();
	String[] lines = new String[2+nAtoms];
	lines[1] = " ";
	lines[0] = "   "+nAtoms;
	Atom[] all = geo.getAllAtoms();
	for(int i=0; i<nAtoms; i++){
	    String symbol = all[i].getName();
	    if(symbol.indexOf(ELEM_SEP) > 0){
		symbol = symbol.substring(0,symbol.indexOf(ELEM_SEP));
	    }
	    lines[2+i] = symbol+"     ";
 	    for(int j=0; j<3; j++){
		lines[2+i] += formattedDouble(all[i].getCoordinates().element(j),DECIMALS+9,DECIMALS)+" ";
	    }
	}
	return lines;
    }

    public String[] formatPoscar(String title, Structure geo, boolean ver5, String[] order){
	int delta = 0;
	if(ver5){
	    delta++;
	}
	String[] lines = new String[8+delta+geo.countAllAtoms()];
	lines[0] = geo.getName();
	lines[1] = "  1.000000";
	for(int i=0; i<3; i++){
	    lines[2+i] = "    ";
 	    for(int j=0; j<3; j++){
		lines[2+i] += formattedDouble(geo.getCell()[i].element(j),DECIMALS+9,DECIMALS)+" ";
	    }
	}
	int[] listElems = geo.countElements();
	int[] typeElems = geo.findElements();

	if(order.length > 0){
	    try{
		// override the default order
		if(order.length != typeElems.length){
		    throw new Exception();
		}
		// check that all the elements listed match those in the system
		// and that no element is duplicated
		for(int i=0; i<order.length; i++){
		    int ind = ASH.elementTable.findNumber(order[i]);
		    if(ind < 1){
			throw new Exception();
		    }
		    for(int j=0; j<order.length; j++){
			if(i != j && order[j].equalsIgnoreCase(order[i])){
			    throw new Exception();
			}
		    }
		    boolean validList = false;
		    for(int j=0; j<typeElems.length; j++){
			if(typeElems[j] == ind){
			    validList = true;
			}
		    }
		    if(!validList){
			throw new Exception();
		    }
		}
		// reorder elemCounts
		int[] tempCounts = new int[listElems.length];
		
		for(int i=0; i<order.length; i++){
		    int ind = ASH.elementTable.findNumber(order[i]);
		    for(int j=0; j<typeElems.length; j++){
			if(typeElems[j] == ind){
			    tempCounts[i] = listElems[j];
			}
		    }
		}
		
		// set the new element lists
		for(int i=0; i<order.length; i++){
		    typeElems[i] = ASH.elementTable.findNumber(order[i]);
		}
		listElems = tempCounts;
		
	    } catch(Exception error){
		System.out.println("Invalid list of elements, ignoring.");
	    }
	}

	if(ver5){
	    lines[5] = "  ";
	    for(int i=0; i<typeElems.length; i++){
		String symbol = ASH.elementTable.getElement(typeElems[i]).getSymbol();
		if(symbol.indexOf(ELEM_SEP) > 0){
		    symbol = symbol.substring(0,symbol.indexOf(ELEM_SEP));
		}
		lines[5] += symbol+"  ";
	    }
	}
	lines[5+delta] = "  ";
	for(int i=0; i<listElems.length; i++){
	    lines[5+delta] += listElems[i]+"  ";
	}
	lines[6+delta] = "Selective";
	lines[7+delta] = "Cartesian";

	Atom[] all = geo.getAllAtoms();
	int linecount = 8+delta;
	for(int k=0; k<typeElems.length; k++){
	    for(int i=0; i<all.length; i++){
		if(all[i].getElement() == typeElems[k]){
		    lines[linecount] = "  ";
		    for(int j=0; j<3; j++){
			lines[linecount] += formattedDouble(all[i].getCoordinates().element(j),DECIMALS+9,DECIMALS)+" ";
		    }
		    for(int j=0; j<3; j++){
			if(all[i].getFrozenCoordinates()[j]){
			    lines[linecount] += "T  ";
			} else {
			    lines[linecount] += "F  ";
			}
		    }
		    linecount++;
		}
	    }
	}
	return lines;
    }

    public boolean findsFile(String filename){
	File thefile = new File(CWD,filename); // current dir
	if(!thefile.isFile()){
	    thefile = new File(filename); // absolute path
	    if(!thefile.isFile()){ // path
		if(PATH != null){
		    for(int i=0; i<PATH.length; i++){
			if(PATH[i] != null){
			    thefile = new File(PATH[i],filename);
			    if(thefile.isFile()){
				break;
			    }
			}
		    }
		}
	    }
	}
	return thefile.isFile();
    }

    /**
     * Read lines from a given file until the end of the file
     * or an empty line is reached.
     * @param filename the file to read
     * @return the lines that were read
     * @exception the exception is thrown if the file is not suitable
     */
    protected ArrayList<String> readLines(String filename)
	throws Exception{

	//try{

	File thefile = new File(CWD,filename); // current dir
	if(!thefile.isFile()){
	    thefile = new File(filename); // absolute path
	    if(!thefile.isFile()){ // path
		if(PATH != null){
		    for(int i=0; i<PATH.length; i++){
			if(PATH[i] != null){
			    thefile = new File(PATH[i],filename);
			    if(thefile.isFile()){
				break;
			    }
			}
		    }
		}
	    }
	}
	if(!thefile.isFile()){
	    throw new Exception();
	}
	

	Reader stream = new FileReader(thefile.getPath());
	//Reader stream = new FileReader(filename);
	    BufferedReader bufStream = new BufferedReader(stream);
	    ArrayList<String> lines = new ArrayList<String>();


	    for(String nextLine = bufStream.readLine();
		!(nextLine == null);		
		nextLine = bufStream.readLine() ){

		if(nextLine.trim().equals("")){
		} else if(nextLine == null ){
		    break;
		} else {
		    lines.add(nextLine);
		}
	    }
	    
	    bufStream.close();
	    stream.close();
	    return lines;

	    //} catch (Exception anException){
	    //throw new Exception();
	    //}
    }



    /**
     * Writes the given Strings into a file on separate lines
     * @param lines the text to be written
     * @param filename the file where we write
     */
    public void writeFile(String[] lines,
			  String filename)
	throws Exception{

	File thefile = new File(CWD,filename);
	if(filename.substring(0,1).equals("/")){
	    thefile = new File(filename);
	} else {
	    //thefile = new File(CWD,filename);
	}
	Writer stream = new FileWriter(thefile.getPath());
	BufferedWriter bufStream = new BufferedWriter(stream);

	for(int i = 0; i < lines.length; i++){
	    bufStream.write(lines[i]);
	    bufStream.newLine();
	}

	bufStream.close();
	stream.close();
    }


    /**
     * Writes the given Strings into a file on separate lines
     * @param lines the text to be written
     * @param filename the file where we write
     */
    public void appendFile(String[] lines,
			  String filename)
	throws Exception{

	File thefile = new File(CWD,filename);
	if(!thefile.isFile()){
	    thefile = new File(filename);
	}
	Writer stream = new FileWriter(thefile.getPath(),true);
	BufferedWriter bufStream = new BufferedWriter(stream);

	for(int i = 0; i < lines.length; i++){
	    bufStream.write(lines[i]);
	    bufStream.newLine();
	}

	bufStream.close();
	stream.close();
    }

    public void writeFile(BufferedImage graph, String filename, int format)
	throws Exception{

	File thefile = new File(CWD,filename);
	ImageIO.write(graph, "PNG", thefile);

    }

    public static String formattedString(String str, int space){
	String output = "";
	while(output.length()+str.length() < space){
	    output += " ";
	}
	output += str;
	return output;
    }

    public static String formattedDouble(double inValue, int space, int decimals){

	StringWriter sw = new StringWriter(); 
	PrintWriter pw = new PrintWriter(sw); 
	pw.printf("%"+space+"."+decimals+"f", inValue); 
	String result = sw.toString();
	// Make sure the decimal separator is a dot
	return result.replace(',','.');
    }

    public static String formattedInt(int inValue, int space){

	StringWriter sw = new StringWriter(); 
	PrintWriter pw = new PrintWriter(sw); 
	pw.printf("%"+space+"d", inValue); 
	return sw.toString(); 

    }

}