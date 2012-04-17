import java.io.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
// replace jline>/< with comment start/end to create a jline-independent source
//jline>
import jline.*;
//jline<

/**
 * Atomic Structure Handler
 *
 * A tool for simultaneous editing and visualization of atomic structures.
 *
 * (c) Teemu Hynninen 2010
 *
 */

public class ASH{

    // commands
    public static final String C_PICK = "pick";
    public static final String C_UNPICK = "unpick";
    public static final String C_JOIN = "union";
    public static final String C_INTERSECT = "intersect";
    public static final String C_OVER = "replace";
    public static final String C_ALL = "all";
    public static final String C_XM = "xmore";
    public static final String C_XL = "xless";
    public static final String C_YM = "ymore";
    public static final String C_YL = "yless";
    public static final String C_ZM = "zmore";
    public static final String C_ZL = "zless";
    public static final String C_RANGE = "range";
    public static final String C_ELE = "element";
    public static final String C_PART = "particle";
    public static final String C_PARTS = "particles";
    public static final String C_LIST = "list";
    public static final String C_COMM = "command";
    public static final String C_SHIFT = "shift";
    public static final String C_ROT = "rotate";
    public static final String C_COPY = "copy";
    public static final String C_CUT = "cut";
    public static final String C_PASTE = "paste";
    public static final String C_CELL = "cell";
    public static final String C_DEL = "delete";
    public static final String C_ADD = "create";
    public static final String C_RM = "remove";
    public static final String C_DUPLO = "duplicates";
    public static final String C_NBOR = "neighbors";
    public static final String C_INTERP = "interpolate";
    public static final String C_REIND = "reindex";
    public static final String C_NEW = "new";
    public static final String C_NEXT = "next";
    public static final String C_JUMP = "switch";
    public static final String C_MOVE = "move";
    public static final String C_PREV = "previous";
    public static final String C_FIRST = "first";
    public static final String C_LAST = "last";
    public static final String C_FRAME = "frame";
    public static final String C_PROJ = "projection";
    public static final String C_PERSP = "perspective";
    public static final String C_ISOM = "isometric";
    public static final String C_LENS = "lenticular";
    public static final String C_WRITE = "write";
    public static final String C_OPEN = "open";
    public static final String C_XYZ = "xyz";
    public static final String C_POSCAR = "poscar";
    public static final String C_POSCAR4 = "poscar4";
    public static final String C_PNG = "png";
    public static final String C_SCRIPT = "script";
    public static final String C_SET = "define";
    public static final String C_DEFAULT = "default";
    public static final String C_ECHO = "echo";
    public static final String C_VALUE = "value";
    public static final String C_FUNC = "function";
    public static final String C_EXIT = "exit";
    public static final String C_SHOW = "show";
    public static final String C_HIDE = "hide";
    public static final String C_AXIS = "axis";
    public static final String C_VIEW = "view";
    public static final String C_RESET = "reset";
    public static final String C_ZOOM = "zoom";
    public static final String C_POINT = "point";
    public static final String C_ANGLE = "angle";
    public static final String C_COLOR = "recolor";
    public static final String C_BG = "bg";
    public static final String C_RAD = "resize";
    public static final String C_INV = "mirror";
    public static final String C_X = "x";
    public static final String C_Y = "y";
    public static final String C_Z = "z";
    public static final String C_PLANE = "plane";
    public static final String C_MOD = "cell";
    public static final String C_CELLX = "x";
    public static final String C_CELLY = "y";
    public static final String C_CELLZ = "z";
    public static final String C_EXP = "expand";
    public static final String C_CLU = "cluster";
    public static final String C_UNCLU = "uncluster";
    public static final String C_UNDO = "undo";
    public static final String C_ALIAS = "alias";
    public static final String C_MAN = "man";
    public static final String C_CALCMAN = "calcman";
    public static final String C_PRINT = "print";
    public static final String C_STRING = "string";
    public static final String C_SHRINK = "shrink";
    public static final String C_GROW = "grow";
    public static final String C_RENAME = "rename";
    public static final String C_SWITCH = "switch";
    public static final String C_MOUSE = "mouse";
    public static final String C_INFO = "info";
    public static final String C_DIR = "directory";
    public static final String C_SCALE = "scale";
    public static final String C_WAIT = "wait";
    public static final String C_BEND = "bend";
    public static final String C_SPHERE = "sphere";
    public static final String C_SCREEN = "screen";
    public static final String C_OLDFILE = "append";
    public static final String C_NEWFILE = "file";
    public static final String C_FREEZE = "constrain";
    public static final String C_DATA = "data";

    public static final String C_NOOPTION = "xxxxxxxx";

    public static final String C_IF = "if";
    public static final String C_ELSE = "else";
    public static final String C_ENDIF = "endif";
    public static final String C_WHILE = "while";
    public static final String C_ENDWHILE = "endwhile";

    public static final String INSET = "    ";
    public static final String COMMENT = "###";

    public static String[] mainCommands;
    private String[] commands;

    //jline>
    private SimpleCompletor complete;
    private ConsoleReader con;
    //jline<

    private static final String HISTORY_FILE = ".ash_history";
    private static final String LAUNCH_FILE = ".ash_launch";

    // key values
    public static final String K_CX = "cellx";
    public static final String K_CY = "celly";
    public static final String K_CZ = "cellz";
    public static final String K_BX = "pbcx";
    public static final String K_BY = "pbcy";
    public static final String K_BZ = "pbcz";

    public static final String K_FR = "frame";
    public static final String K_NFR = "nframes";
    public static final String K_NPART = "nparts";
    public static final String K_NATOM = "natoms";

    public static final String K_VIEW = "view";
    public static final String K_VUP = "viewup";
    public static final String K_VZOOM = "viewzoom";
    public static final String K_VDIR = "viewto";
    public static final String K_PI = "pi";
    public static final String K_E = "e";
    public static final String K_VERSION = "version";
    
    public static String[] keys = {K_CX,K_CY,K_CZ,
				   K_BX,K_BY,K_BZ,
				   K_FR,K_NFR,K_NPART,K_NATOM,
				   K_VIEW,K_VUP,
				   K_VZOOM,K_VDIR,
				   K_PI,K_E,K_VERSION};

    private static final String VERSION = "0.35";
    private static final String[] DEFAULT_LAUNCH = {
	"print \"This is Atomic Structure Handler $"+K_VERSION+"$\"",
	"print \" \"",
	"print \"- view the available commands by typing 'list command'\"",
	"print \"- view the manual by typing 'man' followed by the name of a command\"",
	"print \"- edit launch options in the file .ash_launch\""
    };

    public static Random RNG;
    public static Calculator adder;
    public static long startTime;
    public static PeriodicTable elementTable = new PeriodicTable();

    private GeoPainter painter;
    private boolean notFinished;
    public static boolean runningScript = false;

    private ArrayList<Structure> undos;
    private ArrayList<Structure> frames;    
    private int currentFrame;

    private Hashtable<String,Command> commandTable;
    private Hashtable<String,Variable> variables;
    private Hashtable<String,StringVariable> stringVariables;
    private Hashtable<String,Alias> aliases;

    private Structure[] clipboardAtoms;
    private static final int CLIPSIZE = 10;

    private boolean showDir = false;
    public static boolean defaultPick = true;
    private static String PROMPT = " >";

    public ASH(String[] args){
	startTime = System.currentTimeMillis();
	FileHandler.CWD = new File(".");
	FileHandler.readPath();
	variables = new Hashtable<String,Variable>();
	stringVariables = new Hashtable<String,StringVariable>();
	aliases = new Hashtable<String,Alias>();
	commandTable = new Hashtable<String,Command>();
	adder = new Calculator(this);

	commandTable.put(C_EXIT,new Exit(this));
	commandTable.put(C_PRINT,new Print(this));
	commandTable.put(C_PICK,new Pick(this));
	commandTable.put(C_UNPICK,new Unpick(this));
	commandTable.put(C_LIST,new List(this));
	commandTable.put(C_CLU,new Clusterize(this));
	commandTable.put(C_UNCLU,new Unclusterize(this));
	commandTable.put(C_ROT,new Rotate(this));
	commandTable.put(C_SHIFT,new Shift(this));
	commandTable.put(C_DEL,new Delete(this));
	commandTable.put(C_ADD,new Add(this));
	commandTable.put(C_COPY,new Copy(this));
	commandTable.put(C_CUT,new Cut(this));
	commandTable.put(C_PASTE,new Paste(this));
	commandTable.put(C_MOD,new SetCell(this));
	commandTable.put(C_EXP,new Expand(this));
	commandTable.put(C_RAD,new Resize(this));
	commandTable.put(C_COLOR,new Recolor(this));
	commandTable.put(C_INV,new Invert(this));
	commandTable.put(C_SHOW,new Show(this));
	commandTable.put(C_HIDE,new Hide(this));
	commandTable.put(C_VIEW,new Reposition(this));
	commandTable.put(C_INTERP,new Interpolate(this));
	commandTable.put(C_REIND,new Reindex(this));
	commandTable.put(C_FRAME,new FrameSwitch(this));
	commandTable.put(C_WRITE,new Write(this));
	commandTable.put(C_OPEN,new Open(this));
	commandTable.put(C_UNDO,new Undo(this));
	commandTable.put(C_SET,new Define(this));
	commandTable.put(C_VALUE,new Evaluate(this));
	commandTable.put(C_ALIAS,new MakeAlias(this));
	commandTable.put(C_ELE,new ElementSwitch(this));
	commandTable.put(C_MOUSE,new MouseSwitch(this));
	commandTable.put(C_DIR,new DirectorySwitch(this));
	commandTable.put(C_SCALE,new Scale(this));
	commandTable.put(C_BEND,new Bend(this));
	commandTable.put(C_WAIT,new Wait(this));
	commandTable.put(C_FREEZE,new Constrain(this));
	commandTable.put(C_CALCMAN,new CalcManual(this));
	Command manual = new Manual(this);
	commandTable.put(C_MAN,manual);

	//jline>
	try{
	    con = new ConsoleReader();
	    con.setUseHistory(true);
	    con.getHistory().setHistoryFile(new File(HISTORY_FILE));
	} catch(Exception error){}
	//jline<
	
	commands = new String[0];
	mainCommands = new String[0];
	Enumeration<String> commandList = commandTable.keys();
	while(commandList.hasMoreElements()){
	    String nextKey = commandList.nextElement();
	    String[] keyAsList = {nextKey};
	    mainCommands = StringCombiner.combine(mainCommands,keyAsList);
	}
	Arrays.sort(mainCommands);
	String[][] manOps = new String[1][];
	manOps[0] = mainCommands;
	manual.setOptions(manOps);
	commandList = commandTable.keys();
	while(commandList.hasMoreElements()){
	    String nextKey = commandList.nextElement();
	    String[] keyAsList = {nextKey};
	    commands = StringCombiner.combine(commands,
					      StringCombiner.comboPermute(keyAsList,commandTable.get(nextKey).getOptions(),""));
	}
	//Arrays.sort(keys);

	//jline>
	complete = new SimpleCompletor(commands);	
	con.addCompletor(complete);
	//jline<

	
	// This will print the man pages to a text file. 
	// The function should not be on usually, but if
	// you need to get the full manual easily, uncomment,
	// recompile, and run Ash and it will be generated.
	//printManual("Ash_manual_pages.txt");

	notFinished = true;
	runProgram(args);
    }

    private void runProgram(String[] args){

	GeoWindow window = new GeoWindow(this);
	window.startGraphics();
	window.pauseGraphics();
	this.painter = (GeoPainter)window.getPainter();
	this.painter.recordSize(GeoWindow.WINDOW_WIDTH,GeoWindow.WINDOW_HEIGHT);

	Atom[] noatoms = new Atom[0];
	Structure geo = new Structure(noatoms);
	frames = new ArrayList<Structure>();
	undos = new ArrayList<Structure>();
	currentFrame = 0;
	frames.add(geo);
	clipboardAtoms = new Structure[CLIPSIZE];
	for(int i=0; i<CLIPSIZE; i++){
	    clipboardAtoms[i] = new Structure(noatoms);
	}
	ViewPoint look = new ViewPoint(new Vector(0.0,0.0,50.0),
				       new Vector(0.0,0.0,-10.0),
				       new Vector(0.0,1.0,0.0) );
	painter.initGeo(geo,look);
	window.resumeGraphics();
	
	notFinished = true;

	// Execute initialization script
	try{
	    FileHandler io = new FileHandler();
	    if(io.findsFile(LAUNCH_FILE)){
		executeScript(LAUNCH_FILE);
	    /**
	    File launchfile = new File(LAUNCH_FILE);
	    if(launchfile.canRead()){
		executeScript(LAUNCH_FILE);
	    */
	    } else {
		io.writeFile(DEFAULT_LAUNCH,LAUNCH_FILE+"_default");
		executeScript(LAUNCH_FILE+"_default");
	    }
	} catch(Exception error){
	    //printMessage("");
	}

	// Execute command line arguments
	if(args.length > 0){
	    String command = "";
	    for(int i=0; i<args.length; i++){
		command += args[i]+" ";
	    }
	    executeCommand(command);
	}

	painter.startInteraction();

	while(notFinished){
	    String theprompt;
	    if(showDir){
		try{
		    theprompt = FileHandler.CWD.getCanonicalPath()+PROMPT;
		} catch(Exception error){
		    theprompt = PROMPT;
		}
	    } else {
		theprompt = PROMPT;
	    }
	    String command = readCommandLine(theprompt);
	    if(!command.equals("")){
		executeCommand(command);
	    }
	}

	System.exit(0);
    }

    public int[] findChars(String full, String delims, boolean ignoreInQuotes){
	int[] spots = new int[full.length()];
	int found = 0;
	boolean doubleQuoteOn = false;
	boolean singleQuoteOn = false;
	int depth = 0;
	for(int i=0; i<full.length(); i++){

	    if(full.charAt(i) == '"'){		
		//if(!singleQuoteOn){
		    if(doubleQuoteOn){
			depth--;
			doubleQuoteOn = false;
			if(depth > 0){
			    singleQuoteOn = true;
			}
		    } else {
			depth++;
			doubleQuoteOn = true;
			singleQuoteOn = false;
		    }
		    //} else {
		    //}
	    }
	    if(full.charAt(i) == '\''){
		//if(!doubleQuoteOn){
		    if(singleQuoteOn){
			depth--;
			singleQuoteOn = false;
			if(depth > 0){
			    doubleQuoteOn = true;
			}
		    } else {
			depth++;
			singleQuoteOn = true;
			doubleQuoteOn = false;
		    }
		    //}
	    }
	    for(int j=0; j<delims.length(); j++){
		if(full.charAt(i) == delims.charAt(j)){
		    if(!ignoreInQuotes || (!doubleQuoteOn && !singleQuoteOn)){
			spots[found] = i;
			found++;
		    }
		}
	    }
	}
	int[] chars = new int[found];
	for(int i=0; i<found; i++){
	    chars[i] = spots[i];
	}
	return chars;
    }

    public static String removeComment(String command){
	try{
	    return command.substring(0,command.indexOf(COMMENT));
	} catch(Exception error){
	    return command;
	}
    }

    public String[] splitCommand(String command){
	if(command.length() == 0){
	    return new String[0];
	}
	int[] semis = findChars(command,";",true);
	int[] splits = new int[semis.length+2];
	splits[0] = -1;
	splits[splits.length-1] = command.length();
	for(int i=1; i<splits.length-1; i++){
	    splits[i] = semis[i-1];
	}

	String[] coms = new String[semis.length+1];
	for(int i=0; i<coms.length; i++){
	    coms[i] = command.substring(splits[i]+1,splits[i+1]).trim();
	}
	return coms;
    }

    public String[] splitArgument(String command){
	if(command.length() < 1){
	    String[] empty = new String[1];
	    empty[0] = "";
	    return empty;
	}
	int[] semis = findChars(command," \t",true);
	int[] splits = new int[semis.length+2];
	splits[0] = -1;
	splits[splits.length-1] = command.length();
	for(int i=1; i<splits.length-1; i++){
	    splits[i] = semis[i-1];
	}

	String[] coms = new String[semis.length+1];

	//printMessage(semis.length+" "+splits.length+" "+coms.length);
	for(int i=0; i<coms.length; i++){
	    if( ( command.charAt(splits[i]+1) == '"' && command.charAt(splits[i+1]-1) == '"' ) ||
		( command.charAt(splits[i]+1) == '\'' && command.charAt(splits[i+1]-1) == '\'' ) ){
		coms[i] = command.substring(splits[i]+2,splits[i+1]-1);
	    } else {
		coms[i] = command.substring(splits[i]+1,splits[i+1]);
	    }
	}

	int empty = 0;
	for(int i=0; i<coms.length; i++){
	    try{
		coms[i] = parseS(coms[i]);
	    } catch(Exception error){
		coms[i] = "";
	    }
	    if(coms[i].length() == 0){
		empty++;
	    }
	}
	String[] tidycoms = new String[coms.length-empty];
	if(tidycoms.length == 0){
	    String[] emptycoms = {""};
	    return emptycoms;
	}
	empty = 0;
	for(int i=0; i<coms.length; i++){
	    if(coms[i].length() == 0){
		empty++;
	    } else {
		tidycoms[i-empty] = coms[i];
	    }
	}

	return tidycoms;

    }

    public void executeCommand(String command){
	/**
	StringTokenizer commandFinder = new StringTokenizer(command, ";");
	while(commandFinder.hasMoreTokens()){
	    executeSingleCommand(commandFinder.nextToken().trim());
	}
	*/
	String[] singles = splitCommand(ASH.removeComment(command).trim());
	for(int i=0; i<singles.length; i++){
	    executeSingleCommand(singles[i]);
	}
    }

    public void executeSingleCommand(String command){
	/**
	StringTokenizer commandSplitter = new StringTokenizer(command, " \t");
	String[] coms = new String[commandSplitter.countTokens()];
	*/
	String[] coms = splitArgument(command);
	try{
	    boolean inverse = false;
	    Command action = null;
	    try{
		action = commandTable.get(coms[0]);
		action.getName();
	    } catch(Exception error6){
		action = null;
	    }
	    // if the command didn't work, try to swap the first two words: e.g., new frame -> frame new
	    if(action == null){
		try{
		    action = commandTable.get(coms[1]);
		    action.getName();
		} catch(Exception error7){
		    action = null;
		}
		if(action == null){
		    throw new Exception();
		}
		inverse = true;
		String tempcom = coms[0];
		coms[0] = coms[1];
		coms[1] = tempcom;
	    }
	    String[] args = new String[coms.length-1];
	    for(int a=0; a<args.length; a++){
		args[a] = coms[a+1];
	    }
	    try{
		action.execute(args);
	    } catch(Exception error){
		printMessage("Invalid call for "+coms[0]+". Syntax for the command is:\n"+action.getUsage(),
			     "Invalid command while executing script: '"+command+"'");		
		//error.printStackTrace();
	    }
	    return;
	} catch(Exception error2){
	    //error2.printStackTrace();
	    // check for alias
	    Alias shorthand = null;
	    try{
		shorthand = getAlias(coms[0]);
		if(shorthand == null){
		    throw new Exception();
		}
		try{
		    String[] arguments = new String[coms.length-1];
		    for(int i=0; i<arguments.length; i++){
			arguments[i] = coms[i+1];
		    }
		    String commander = shorthand.getCommand(arguments);
		    //printMessage("alias command '"+commander+"'");
		    executeCommand(commander);
		} catch(Exception error3){
		    printMessage("invalid arguments for "+coms[0],
				 "Invalid command while executing script: '"+command+"'");
		    //error3.printStackTrace();
		}
	    } catch(Exception error4){
		//error4.printStackTrace();
		/*
		try{ // Try to execute a unix command
		    Process p = Runtime.getRuntime().exec(coms, null, FileHandler.CWD);
		    
		    String s = null;
		    int i = 0;
		    if (i == 0){
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
			    printMessage(s);
			}
		    } else {
			BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// read the output from the command
			while ((s = stdErr.readLine()) != null) {
			    printMessage(s);
			}
		    }
		    
		} catch(Exception error5){
		*/

		if(coms[0].length() > 0){		    
		    printMessage("unknown command "+coms[0],
				 "Invalid command while executing script: '"+command+"'");
		}

		//}
	    }
	}    
    }

    public void rememberStructure(){
	if(!runningScript){
	    undos.add(frames.get(currentFrame).copy());
	    if(undos.size() > 30){ // if the buffer is too big, forget the first structure
		undos.remove(0);
	    }
	}
    }

    public void forgetStructures(){
	undos.clear();
    }

    public Structure recallStructure(){
	if(undos.size() > 0){
	    //Structure memory = undos.get(undos.size()-1);
	    return undos.remove(undos.size()-1);
	    //return memory;
	}
	return frames.get(currentFrame);
    }
    
    public int makeCluster()
	throws Exception{
	Structure geo = frames.get(currentFrame);
	//int pickedOnes = 0;
	int pickedParts = 0;
	for(int i=0; i<geo.countParticles(); i++){
	    Particle part = geo.getParticle(i);
	    if(part.isPicked()){
		pickedParts++;
	    }
	}
	if(pickedParts == 0){
	    throw new Exception();
	}
	Particle[] cluster = new Particle[pickedParts];
	int index = 0;
	for(int i=0; i<geo.countParticles(); i++){
	    Particle part = geo.getParticle(i);
	    if(part.isPicked()){
		cluster[index] = part;
		index++;
		try{
		    geo.removeParticle(i);
		    i--;
		} catch(Exception error){}
	    }
	}
	geo.addParticle(new Cluster(cluster));
	return cluster.length;
    }

    public int breakCluster(){	
	Structure geo = frames.get(currentFrame);
	int ats = 0;
	for(int i=0; i<geo.countParticles(); i++){
	    Particle part = geo.getParticle(i);
	    if(part.isPicked() && !part.isAtomic()){
		Particle[] cluster = ((Cluster)part).getParticles();
		try{
		    geo.removeParticle(i);
		    i--;
		} catch(Exception error){}
		for(int j=0; j<cluster.length; j++){
		    i++;
		    geo.addParticle(i,cluster[j],false);
		    ats++;
		}
	    }
	}
	return ats;
    }

    public void addParticle(Particle newp){
	frames.get(currentFrame).addParticle(newp);
    }

    public void removeParticle(int index)
	throws Exception{
	frames.get(currentFrame).removeParticle(index);
    }

    public void removePicked(){
	try{
	    Structure geo = frames.get(currentFrame); 
	    for(int i=geo.countParticles()-1; i>=0; i--){
		if(geo.getParticle(i).isPicked()){
		    geo.removeParticle(i);
		}
	    }
	} catch(Exception error){}
    }

    public void addFrame(Structure geo){
	if(currentFrame == frames.size()-1){
	    frames.add(geo);
	} else {
	    frames.add(currentFrame+1,geo);
	}
    }

    public void deleteFrame(int index){
	if(index >= 0 && index < frames.size()){
	    try{
		if(currentFrame == index){ // deleting the current frame
		    if(index < frames.size()-1){ // not last frame, jump to next
			changeFrame(currentFrame+1);
			currentFrame--; // a frame will be deleted before this one, so subtract one from frame number
			this.painter.aimAtCenter();
			this.painter.updateGeo();
		    } else if(frames.size() > 1){ // last frame, but there are more than one
			changeFrame(currentFrame-1);
			this.painter.aimAtCenter();
			this.painter.updateGeo();
		    } else { // the only frame, replace by an empty one
			currentFrame = 0;
			Atom[] nullAtom = new Atom[0];
			Structure nullGeo = new Structure(nullAtom);
			frames.add(nullGeo);
			this.painter.aimAtCenter();
			this.painter.updateGeo(nullGeo);
		    }
		}
		frames.remove(index);
	    } catch(Exception error){
		printMessage("could not delete frame");
		//error.printStackTrace();
	    }
	} else {
	    printMessage("no such frame");
	}
    }

    public void printMessage(String words,boolean always){	
	if(!ASH.runningScript || always){
	    System.out.println(words);
	}

    }

    public void printMessage(String words){	
	if(ASH.runningScript){
	} else {
	    System.out.println(words);
	}
    }

    public void printMessage(String words, String scriptwords){	
	if(ASH.runningScript){
	    System.out.println(scriptwords);
	} else {
	    System.out.println(words);
	}
    }


    public void nextFrame(){
	try{
	    Structure newGeo = frames.get(currentFrame+1);
	    this.painter.setGeometry(newGeo);
	    currentFrame++;
	} catch(Exception error){
	    //printMessage("no such frame");
	}
    }

    public void previousFrame(){
	try{
	    Structure newGeo = frames.get(currentFrame-1);
	    this.painter.setGeometry(newGeo);
	    currentFrame--;
	} catch(Exception error){
	    //printMessage("no such frame");
	}
    }

    public void changeFrame(int index){
	try{
	    Structure newGeo = frames.get(index);
	    this.painter.setGeometry(newGeo);
	    currentFrame = index;
	    forgetStructures();
	} catch(Exception error){
	    printMessage("no such frame");
	}
    }

    public void pickAll(boolean yesno, boolean join){
	try{
	    if(!join){
		Structure newGeo = frames.get(currentFrame);
		int natoms = newGeo.countParticles();
		for(int i=0; i<natoms; i++){
		    newGeo.getParticle(i).pick(yesno);
		}
	    }
	} catch(Exception error){
	}
    }

    public void pickElement(int ele, boolean yesno, boolean join){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    int natoms = newGeo.countParticles();
	    for(int i=0; i<natoms; i++){
		if(newGeo.getParticle(i).isAtomic()){
		    Atom picky = (Atom)newGeo.getParticle(i);
		    if(!join){
			if(picky.getElement() == ele){
			    picky.pick(yesno);
			}
		    } else {
			if(picky.getElement() != ele){
			    picky.pick(!yesno);
			}
		    }
		}
	    }
	} catch(Exception error){
	}
    }

    public void pickAtom(int index, boolean yesno, boolean join){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    int natoms = newGeo.countParticles();
	    if(!join){
		newGeo.getParticle(index).pick(yesno);
	    } else {
		for(int i=0; i<newGeo.countParticles(); i++){
		    if(i != index){
			newGeo.getParticle(i).pick(!yesno);			
		    }
		}
	    }
	} catch(Exception error){
	}
    }

    public void pickSphere(Vector point, double radius, boolean yesno, boolean join){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    int natoms = newGeo.countParticles();
	    for(int i=0; i<natoms; i++){
		Particle check = newGeo.getParticle(i);
		Vector coord = check.getCoordinates();
		if(!join){
		    if(coord.minus(point).norm() < radius){
			check.pick(yesno);
		    }
		} else {
		    if(coord.minus(point).norm() >= radius){
			check.pick(!yesno);
		    }
		}
	    }
	} catch(Exception error){
	}

    }

    public void pickArea(Vector point, Vector direction, boolean yesno, boolean join){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    int natoms = newGeo.countParticles();
	    for(int i=0; i<natoms; i++){
		Particle check = newGeo.getParticle(i);
		Vector coord = check.getCoordinates();
		if(!join){
		    if(coord.minus(point).dot(direction) > 0.0){
			check.pick(yesno);
		    }
		} else {
		    if(coord.minus(point).dot(direction) <= 0.0){
			check.pick(!yesno);
		    }
		}
	    }
	} catch(Exception error){
	}
    }

    public void shiftAtoms(Vector shift){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    int natoms = newGeo.countParticles();
	    for(int i=0; i<natoms; i++){
		if(newGeo.getParticle(i).isPicked()){
		    newGeo.getParticle(i).shiftCoordinates(shift);
		}
	    }
	    newGeo.forcePeriodicBounds();
	} catch(Exception error){
	}
    }

    public void listCell(){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    for(int i=0; i<3; i++){
		Vector axis = newGeo.getCell()[i];
		printMessage("cell vector "+(i+1)+
				   "   ("+FileHandler.formattedDouble(axis.element(0),12,5)+
				   ", "+FileHandler.formattedDouble(axis.element(1),12,5)+
			     ", "+FileHandler.formattedDouble(axis.element(2),12,5)+")",true);
	    }
	} catch(Exception error){
	    //error.printStackTrace();
	}
    }

    public void listVariables(){
	try{
	    FileHandler format = new FileHandler();
	    printMessage("Pre-defined:",true);
	    for(int i=0; i<keys.length; i++){
		printMessage(format.formattedString(keys[i]+" = ",20)+parseVariable(keys[i]),true);		
	    }

	    printMessage("Custom:",true);
	    Enumeration<String> vkeys = variables.keys();
	    String[] varList = new String[variables.size()];
	    int vi = 0;
	    while(vkeys.hasMoreElements()){
		String nextKey = vkeys.nextElement();
		String value = variables.get(nextKey).toString();
		varList[vi] = format.formattedString(nextKey+" = ",20)+value;
		vi++;
	    }
	    Arrays.sort(varList);
	    for(int i=0; i<varList.length; i++){
		printMessage(varList[i],true);
	    }

	    // strings
	    vkeys = stringVariables.keys();
	    varList = new String[stringVariables.size()];
	    vi = 0;
	    while(vkeys.hasMoreElements()){
		String nextKey = vkeys.nextElement();
		String value = stringVariables.get(nextKey).toString();
		varList[vi] = format.formattedString(nextKey+" = ",20)+"'"+value+"'";
		vi++;
	    }
	    Arrays.sort(varList);
	    for(int i=0; i<varList.length; i++){
		printMessage(varList[i],true);
	    }

	} catch(Exception error){
	    //error.printStackTrace();
	}
    }

    public void listFunctions(){
	try{
	    FileHandler format = new FileHandler();
	    printMessage("Pre-defined:",true);
	    Operator[] mainFunctions = adder.listOperators();
	    for(int i=0; i<mainFunctions.length; i++){
		printMessage(format.formattedString(mainFunctions[i].getType(),15)+" "+
				   format.formattedString(mainFunctions[i].getSyntax(),40)+" ("+mainFunctions[i].getName()+")",true);
	    }
	    printMessage("Custom:",true);
	    CustomFunction[] functions = adder.listFunctions();
	    for(int i=0; i<functions.length; i++){		
		String[] args = new String[functions[i].getArgumentCount()];
		for(int j=0; j<args.length; j++){
		    args[j] = "#"+(j+1);
		}
		printMessage(format.formattedString(functions[i].getName(),20)+" "+
				   format.formattedString(functions[i].getSyntax(args)+" = ",30)+
				   functions[i].getExpression(args),true);
	    }
	} catch(Exception error){
	    error.printStackTrace();
	}
    }


    public void listCommands(){
	try{
	    for(int i=0; i<mainCommands.length; i++){
		printMessage(mainCommands[i],true);
	    }
	} catch(Exception error){
	    //error.printStackTrace();
	}
    }

    public void listAliases(){
	try{
	    Enumeration<String> keys = aliases.keys();
	    String[] aliasList = new String[aliases.size()];
	    int ai = 0;
	    while(keys.hasMoreElements()){
		String nextKey = keys.nextElement();
		Alias alias = aliases.get(nextKey);		
		String[] args = new String[alias.getArgumentCount()];
		for(int i=0; i<args.length; i++){
		    args[i] = "#"+(i+1);
		}
		String printer = nextKey;
		int keylength = nextKey.length();
		while(keylength < 20){
		    printer += " ";
		    keylength++;
		}
		aliasList[ai] = printer+" "+alias.getCommand(args);
		ai++;
	    }
	    Arrays.sort(aliasList);
	    for(int i=0; i<aliasList.length; i++){
		printMessage(aliasList[i],true);
	    }
	} catch(Exception error){
	    //error.printStackTrace();
	}
    }

    public void listElements(){	
	Structure geo = frames.get(currentFrame);
	int[] elementList = geo.findElements();
	int[] elementCount = geo.countElements();

	for(int i=0; i<elementCount.length; i++){
	    try{
		Element ele = parseElement(elementList[i]);
		printMessage("element "+FileHandler.formattedInt(ele.getNumber(),3)+", "+
				   ele.getSymbol()+":  "+elementCount[i]+" particles.   ",true);
	    } catch(Exception error){}
	}
    }

    public void listAll(){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    int natoms = newGeo.countParticles();
	    for(int i=0; i<natoms; i++){
		Particle listed = newGeo.getParticle(i);
		//double[] pos = listed.getCoordinates().toArray();
		printMessage(FileHandler.formattedInt(i,6)
				   +"  "+listed.toString(),true);
	    }
	} catch(Exception error){
	    //error.printStackTrace();
	}
    }

    public void pickByMouse(int x, int y, int number){
	int[] hit = this.painter.getView().coversScreenPoint(x,y);
	if(hit.length > 0){
	    Structure geo = frames.get(currentFrame);
	    Atom[] atoms = geo.getAllAtoms();
	    int[] particleMap = geo.mapAtomsToParticles();
	    boolean[] hasBeenDealt = new boolean[geo.countParticles()];	    
	    for(int i=0; i<hasBeenDealt.length; i++){
		hasBeenDealt[i] = false;
	    }

	    //printMessage("mouse pick");
	    if(number == 1){
		int tophit = particleMap[hit[hit.length-1]];
		Particle bullseye = geo.getParticle(tophit);
		bullseye.pick(!bullseye.isPicked());
	    } else if(number == 2){
		hasBeenDealt[hit[hit.length-1]] = true;
		for(int i=0; i<hit.length-1; i++){
		    int pInd = particleMap[hit[i]];
		    if(!hasBeenDealt[pInd]){
			Particle bullseye = geo.getParticle(pInd);
			bullseye.pick(!bullseye.isPicked());		
			hasBeenDealt[pInd] = true;
		    }
		}
	    } else if(number == 3){
		for(int i=0; i<hit.length; i++){
		    int pInd = particleMap[hit[i]];
		    if(!hasBeenDealt[pInd]){
			Particle bullseye = geo.getParticle(pInd);
			bullseye.pick(true);
			hasBeenDealt[pInd] = true;
		    }
		}
	    }
	    this.painter.updateGeo();
	    //System.out.print(" > ");
	}
    }

    public void listMouseInfo(int x, int y){
	int[] hit = this.painter.getView().coversScreenPoint(x,y);
	if(hit.length > 0){
	    Structure geo = frames.get(currentFrame);
	    Atom[] atoms = geo.getAllAtoms();
	    int[] particleMap = geo.mapAtomsToParticles();
	    
	    printMessage("mouse query",true);
	    for(int i=0; i<hit.length; i++){
		System.out.print(atoms[hit[i]].toString());
		Particle parent = geo.getParticle(particleMap[hit[i]]);
		if(parent.isAtomic()){
		    printMessage("  independent "+particleMap[hit[i]],true);
		} else {
		    printMessage("  in cluster  "+particleMap[hit[i]],true);
		}
	    }
	    System.out.print(PROMPT+" ");
	}
    }

    public void listPicked(){
	try{
	    Structure newGeo = frames.get(currentFrame);
	    int natoms = newGeo.countParticles();
	    for(int i=0; i<natoms; i++){
		Particle listed = newGeo.getParticle(i);
		if(listed.isPicked()){
		    //double[] pos = listed.getCoordinates().toArray();
		    printMessage(FileHandler.formattedInt(i,6)
				       +"  "+listed.toString(),true);
		}
	    }
	} catch(Exception error){
	}
    }

    public void bend(Vector axis, Vector point, Vector radius)
	throws Exception{

	Vector contact = radius.projectionOnPlane(axis);
	double contactnorm = contact.norm();
	contact = contact.unit();
	Vector unitaxis = axis.unit();
	Vector thirdunit = unitaxis.cross(contact);
	if(contactnorm < 0.001){
	    printMessage("cannot bend by a zero radius");
	    throw new Exception();
	}

	Structure geo = frames.get(currentFrame);
	rememberStructure();
	for(int i=0; i<geo.countParticles(); i++){
	    Particle dotty = geo.getParticle(i);
	    if(dotty.isPicked()){
		Vector initial = dotty.getCoordinates().minus(point);
		double axiscomp = initial.dot(unitaxis);
		double contactcomp = initial.dot(contact);
		double thirdcomp = initial.dot(thirdunit);
		double angle = 0.0;
		if(contactcomp > 0){
		    angle = thirdcomp/contactnorm;
		} else {
		    angle = -thirdcomp/contactnorm;
		}
		//if(i == 100){
		//printMessage(axiscomp+" "+contactcomp+" "+thirdcomp+" "+angle);
		//}
		Vector bended = contact.times(contactcomp).rotate(unitaxis,angle).plus(unitaxis.times(axiscomp));
		dotty.setCoordinates(bended.plus(point));
		if(!dotty.isAtomic()){
		    dotty.rotate(new Quaternion(unitaxis,angle));
		}
	    }
	}

	geo.forcePeriodicBounds();
	geo.calculateCenter();
	this.painter.aimAtCenter();
	this.painter.updateGeo();

    }

    public void scale(double factor, boolean scalecluster, int scalesystem)
	throws Exception{

	Structure geo = frames.get(currentFrame);
	rememberStructure();
	for(int i=0; i<geo.countParticles(); i++){
	    Particle dot = geo.getParticle(i);
	    if(dot.isPicked()){
		if(scalesystem == 3){
		    dot.setCoordinates(dot.getCoordinates().times(factor));
		} else if(scalesystem > -1){
		    Vector direct = geo.getDirectCoordinates(dot.getCoordinates());
		    direct.setElement(scalesystem,direct.element(scalesystem)*factor);
		    dot.setCoordinates(geo.getCartesianCoordinates(direct));
		}
		if(scalecluster){
		    if(!dot.isAtomic()){
			((Cluster)dot).scale(factor);
		    }
		}
	    }
	}
	if(scalesystem >= 0){
	    Vector[] cell = geo.getCell();
	    for(int i=0; i<3; i++){
		if(scalesystem == 3 || scalesystem == i){
		    cell[i] = cell[i].times(factor);
		}
	    }
	    geo.updateInverseCell();
	}
	geo.calculateCenter();
	this.painter.aimAtCenter();
	this.painter.updateGeo();

    }

    public void reIndex(int masterFrame)
	throws Exception{
	if(frames.size() > masterFrame && masterFrame >= 0){
	    this.frames.get(currentFrame).reIndex(this.frames.get(masterFrame));
	    this.painter.aimAtCenter();
	    this.painter.updateGeo();
	} else {
	    throw new Exception();
	}
    }

    public void updateElements(){
	for(int i=0; i<frames.size(); i++){
	    frames.get(i).updateElements();
	}
    }

    public void listNeighbors(int closest){
	Structure geo = frames.get(currentFrame);
	int n_picked = geo.countPickedParticles();
	int n_part = geo.countParticles();
	double[][] dists = new double[n_picked][closest];
	int[][] nbors = new int[n_picked][closest];
	for(int i=0; i<n_picked; i++){
	    for(int j=0; j<closest; j++){
		dists[i][j] = -1.0;
		nbors[i][j] = -1;
	    }
	}
	int index = -1;
	for(int i=0; i<n_part; i++){
	    Particle p1 = geo.getParticle(i);
	    if(p1.isPicked()){
		index++;
		for(int j=0; j<n_part; j++){
		    if(i != j){
			Particle p2 = geo.getParticle(j);
			double distance = geo.distance(p1.getCoordinates(),p2.getCoordinates());
			boolean placed = false;
			int candidate = 0;
			while(!placed){
			    // this is a neighboring pair
			    if(dists[index][candidate] < 0.0 ||
			       dists[index][candidate] > distance){
				placed = true;
				// shift the current nearest nbor list to fit the new neighbor
				for(int k=closest-1; k>candidate; k--){
				    dists[index][k] = dists[index][k-1];
				    nbors[index][k] = nbors[index][k-1];
				}
				dists[index][candidate] = distance;
				nbors[index][candidate] = j;
			    }
			    candidate++;
			    if(candidate == closest){
				placed = true;
			    }
			}
		    }	    
		}
		if(p1.isAtomic()){
		    System.out.print("atom    "+FileHandler.formattedInt(i,(int)(2+Math.log((double)n_part)/Math.log(10.0)))+
				     " el. "+FileHandler.formattedInt(((Atom)p1).getElement(),5)+" : ");
		} else {
		    System.out.print("cluster "+FileHandler.formattedInt(i,(int)(2+Math.log((double)n_part)/Math.log(10.0)))+
				     " of "+FileHandler.formattedInt(p1.countAtoms(),6)+" : ");
		}
		for(int j=0; j<closest; j++){
		    if(nbors[index][j] >= 0){
			if(j>0){
			    System.out.print(",");
			}
			System.out.print("  "+FileHandler.formattedInt(nbors[index][j],(int)(2+Math.log((double)n_part)/Math.log(10.0)))
					 +" "+FileHandler.formattedDouble(dists[index][j],8,3));
		    }
		}
		printMessage("");

	    }
	}

    }

    public void interpolate(int start, int end, int steps){
	System.out.println(start+" "+end+" "+steps);
	Structure ini;
	Structure fin;
	try{
	    ini = frames.get(start);
	    fin = frames.get(end);
	    for(int i=0; i<steps; i++){
		double ratio = (double)(steps-i)/(double)(steps+1);
		Structure interpolated = Structure.interpolate(ini,fin,ratio);
		addFrame(interpolated);
	    }
	} catch(Exception error){
	}
    }

    public String readCommandLine(String prompt){

	//jline>
	System.out.print(prompt+" ");
	try{
	    String line = con.readLine();
	    con.getHistory().addToHistory(line);
	    return line;
	} catch(Exception error){
	    printMessage("console error",true);
	}
	return "a";
	//jline<

	/*
	//jline<
	//  open up standard input
	System.out.print(prompt+" ");
	InputStreamReader in = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(in);
	
	String readLine = "";
	try {
	    readLine = br.readLine();
	} catch (IOException ioe) {
	    printMessage("command line error");
	}
	return readLine;
	//jline>
	*/

    }

    public void writeGeometry(String filename, Structure geo, int format, String[] args)
	throws Exception{
	FileHandler io = new FileHandler();
	String[] lines = {""};
	if(format == FileHandler.F_POSCAR){
	    lines = io.formatPoscar(filename,geo,true,args);
	    io.writeFile(lines,filename);	    
	} else if(format == FileHandler.F_POSCAR4){
	    lines = io.formatPoscar(filename,geo,false,args);
	    io.writeFile(lines,filename);	    
	} else if(format == FileHandler.F_XYZ){
	    lines = io.formatXYZ(geo);
	    io.writeFile(lines,filename);   
	} else if(format == FileHandler.F_PNG){
	    BufferedImage graph = this.painter.getPainting();
	    io.writeFile(graph,filename,format);
	} else {
	    throw new Exception();
	}
    }

    public void writeGeometries(String filename, Structure[] geo, int format)
	throws Exception{
	FileHandler io = new FileHandler();
	String[] lines = {""};
	if(format == FileHandler.F_XYZ){
	    for(int i=0; i<geo.length; i++){
		lines = io.formatXYZ(geo[i]);
		io.appendFile(lines,filename);
	    }
	} else {
	    throw new Exception();
	}
    }

    public Structure[] readGeometry(String filename, int format)
	throws Exception{
	FileHandler io = new FileHandler();
	if(format == FileHandler.F_XYZ){
	    return io.readXYZ(filename);
	} else if(format == FileHandler.F_POSCAR){
	    return io.readPoscar(filename);
	} else {
	    throw new Exception();
	}
    }

    public void executeScript(String filename)
	throws Exception{
	try{
	    this.frames.get(currentFrame).calculateCenter();
	    rememberStructure();
	    this.runningScript = true;
	    FileHandler io = new FileHandler();
	    ArrayList<String> commands = io.readLines(filename);
	    executeBlock(commands, 0, commands.size());
	    this.runningScript = false;
	    this.frames.get(currentFrame).calculateCenter();
	    //ViewPoint.resetViewPoint(this.frames.get(currentFrame));
	    this.painter.aimAtCenter();
	    this.painter.updateGeo();	    
	} catch(Exception error){
	    this.runningScript = false;	    
	    throw error;
	}
    }

    public void executeBlock(ArrayList<String> commands, int start, int end){
	for(int i=start; i<end; i++){
	    StringTokenizer logic = new StringTokenizer(commands.get(i)," \t");
	    if(logic.countTokens() > 0){
		String[] coms = new String[logic.countTokens()];
		for(int k=0; k<coms.length; k++){
		    coms[k] = logic.nextToken();
		}
		if(coms[0].equalsIgnoreCase(C_IF)){
		    int depth = 1;
		    String formula = "";
		    for(int k=1; k<coms.length; k++){
			formula += coms[k];
		    }
		    Variable cond = new Variable("if",0);
		    try{
			cond = evaluateExpression(formula);
		    } catch(Exception error){
		    }
		    boolean exec;
		    if(cond.getIntValue() == 1){
			exec = true;
		    } else {
			exec = false;
		    }
		    int j=i+1;
		    int endindex = -1;
		    int elseindex = -1;
		    while(endindex < i && j < end){
			StringTokenizer ender = new StringTokenizer(commands.get(j)," ");
			if(ender.hasMoreTokens()){
			    String issue = ender.nextToken();
			    if(issue.equalsIgnoreCase(C_ENDIF)){
				depth--;
				if(depth == 0){
				    endindex = j;
				}
			    } else if(issue.equalsIgnoreCase(C_IF)){
				depth++;
			    } else if(issue.equalsIgnoreCase(C_ELSE) && depth == 1){
				elseindex = j;
			    }
			}
			j++;
		    }
		    if(endindex < 0){
			printMessage("unclosed if structure found",true);
		    }
		    if(exec){
			if(elseindex > i){
			    executeBlock(commands,i+1,elseindex);
			} else {
			    executeBlock(commands,i+1,endindex);
			}
		    } else if(elseindex > i){
			executeBlock(commands,elseindex+1,endindex);
		    }
		    i=j-1;
		} else if(coms[0].equalsIgnoreCase(C_WHILE)){
		    int depth = 1;
		    String formula = "";
		    for(int k=1; k<coms.length; k++){
			formula += coms[k];
		    }
		    int j=i+1;
		    int endindex = -1;
		    while(endindex < i && j < end){
			StringTokenizer ender = new StringTokenizer(commands.get(j)," ");
			if(ender.hasMoreTokens()){
			    String commander = ender.nextToken();
			    if(commander.equalsIgnoreCase(C_ENDWHILE)){
				depth--;
				if(depth == 0){
				    endindex = j;
				}
			    } else if(commander.equalsIgnoreCase(C_WHILE)){
				depth++;
			    }
			}
			j++;
		    }
		    if(endindex < 0){
			printMessage("unclosed while structure found",true);
		    }
		    Variable cond = new Variable("if",0);
		    try{
			cond = evaluateExpression(formula);
		    } catch(Exception error){
		    }
		    while(cond.getIntValue() == 1){
			executeBlock(commands,i+1,j-1);
			try{
			    cond = evaluateExpression(formula);
			} catch(Exception error){
			}
		    }
		    i=j-1;
		} else {
		    //printMessage(commands.get(i));
		    executeCommand(commands.get(i));
		}
	    }
	}
    }

    public void changeProjectionType(int type){
	this.painter.getViewPoint().setProjectionType(type);
	this.painter.aimAtCenter();
	this.painter.updateGeo();
    }

    public Element parseElement(int index)
	throws Exception{
	if(index > 0 && index < ASH.elementTable.fill()){
	    return ASH.elementTable.getElement(index);
	} else {
	    System.out.println("Element "+index+" has not been defined yet. Try naming it first?");
	    throw new Exception();
	}
    }

    public Element parseElement(String symbol)
	throws Exception{
	int index = 0;
	try{
	    index = parseI(symbol);
	} catch(Exception error){
	    return ASH.elementTable.getElement(symbol);
	}
	if(index > 0 && index < ASH.elementTable.fill()){
	    return ASH.elementTable.getElement(index);
	} else {
	    throw new Exception();
	}
    }

    public double parseR(String number)
	throws Exception{
	try{
	    return Double.parseDouble(number);
	} catch(Exception error){
	    double[][] result = evaluateExpression(number).getMatrixValue();
	    if(result.length == 1 && result[0].length == 1){
		return result[0][0];
	    } else {
		throw new Exception();
	    }
	}
    }

    public double[] parseVector(String matrix)
	throws Exception{
	double[][] result = evaluateExpression(matrix).getMatrixValue();
	if(result.length == 1){
	    double[] vector = new double[result[0].length];
	    for(int i=0; i<vector.length; i++){
		vector[i] = result[0][i];
	    }
	    return vector;
	} else if(result[0].length == 1){
	    double[] vector = new double[result.length];
	    for(int i=0; i<vector.length; i++){
		vector[i] = result[i][0];
	    }
	    return vector;
	} else {
	    throw new Exception();
	}
    }

    public double[][] parseMatrix(String matrix)
	throws Exception{
	return evaluateExpression(matrix).getMatrixValue();
    }

    public int findVector(String[] args, int location, Vector output)
	throws Exception{

	int targetLength = output.dimension();
	try{
	    double[] result = parseVector(args[location]);
	    if(result.length == targetLength){
		for(int i=0; i< targetLength; i++){
		    output.setElement(i,result[i]);
		}
		return 1;
	    } else {
		throw new Exception();
	    }
	} catch (Exception error){
	    double[] result = new double[targetLength];
	    for(int i=0; i<targetLength; i++){
		result[i] = parseR(args[i+location]);
	    }
	    for(int i=0; i< targetLength; i++){
		output.setElement(i,result[i]);
	    }
	    return targetLength;
	}
    }

    public Variable parseVariable(String bit)
	throws Exception{
	try{ // parse
	    if(isKeyword(bit)){ // keyvalue
		return getKeyValue(bit);
	    } else { // number
		// if "bit" is not a number, this will result in an exception
		// which is caught and "bit" is checked against user variable names
		return new Variable(bit,Double.parseDouble(bit));
	    }
	} catch(Exception error){ // user variable
	    return getVariable(bit);
	}
    }

    public StringVariable parseStringVariable(String bit){
	try{ // parse
	    if(isKeyword(bit)){ // keyvalue
		return new StringVariable(bit,getKeyValue(bit).toString());
	    } else { // user string variable
		return getStringVariable(bit);
	    }
	} catch(Exception error){ // user variable
	    try{
		return new StringVariable(bit,getVariable(bit).toString());
	    } catch(Exception error2){ // plain string
		return new StringVariable(bit,bit);		
	    }
	}
    }

    public int parseI(String number)
	throws Exception{
	try{
	    return Integer.parseInt(number);
	} catch(Exception error){
	    double[][] result = evaluateExpression(number).getMatrixValue();
	    if(result.length == 1 && result[0].length == 1){
		return (int)result[0][0];
	    } else {
		throw new Exception();
	    }
	}
    }

    public String parseS(String string)
	throws Exception{
	String out = "";
	String var = "";
	boolean variable = false;
	boolean integer = false;
	for(int i=0; i<string.length(); i++){
	    String letter = string.substring(i,i+1);
	    if(letter.equals("$")){
		if(variable){
		    variable = false;
		    String value = "";
		    try{
			if(integer){
			    value = value+parseI(var);
			} else {
			    value = value+parseR(var);
			}
		    } catch(Exception error){
			try{
			    Variable wrapper = evaluateExpression(var);
			    double[][] matrix = wrapper.getMatrixValue();
			    if(integer){
				value = value+wrapper.getHeight()+" "+wrapper.getWidth();
			    } else {
				value = value+wrapper.getMatrixString();
			    }
			} catch(Exception error2){
			    value = parseStringVariable(var).toString();
			}
		    }
		    out = out+value;
		} else {
		    var="";
		    variable = true;
		    i++;
		    String typer = string.substring(i,i+1);
		    if(typer.equals("I")){
			integer = true;
		    } else if(typer.equals("R")){
			integer = false;
		    } else {
			integer = false;
			i--;
		    }
		}
	    } else {
		if(variable){
		    var = var+letter;
		} else {
		    out = out+letter;
		}
	    }
	}
	return out;
    }

    public void defineAlias(String name, Alias command)
	throws Exception{
	if(isCommand(name)){
	    throw new Exception();
	} else {
	    aliases.put(name,command);
	    //jline>
	    complete.addCandidateString(name);
	    //jline<
	}
    }

    public Alias getAlias(String name)
	throws Exception{
	if(aliases.containsKey(name)){
	    return aliases.get(name);
	} else {
	    throw new Exception();
	}
    }

    public void defineVariable(String name, Variable value)
	throws Exception{
	if(isKeyword(name)){
	    throw new Exception();
	} else { // makes a copy of the variable with new name
	    Variable x = new Variable(name,value.getMatrixValue());
	    variables.put(name,x);
	}
    }

    public void defineStringVariable(String name, StringVariable value)
	throws Exception{
	if(isKeyword(name)){
	    throw new Exception();
	} else {
	    StringVariable x = new StringVariable(name,value.toString());
	    stringVariables.put(name,x);
	}
    }

    public void removeVariable(String name)
	throws Exception{
	if(isKeyword(name)){
	    throw new Exception();
	} else {
	    variables.remove(name);
	}
    }

    public void removeStringVariable(String name)
	throws Exception{
	if(isKeyword(name)){
	    throw new Exception();
	} else {
	    stringVariables.remove(name);
	}
    }

    public void defineVariable(String name, double value)
	throws Exception{
	if(isKeyword(name)){
	    throw new Exception();
	} else {
	    Variable x = new Variable(name,value);
	    variables.put(name,x);
	}
    }

    public void defineStringVariable(String name, String value)
	throws Exception{
	if(isKeyword(name)){
	    throw new Exception();
	} else {
	    StringVariable x = new StringVariable(name,value);
	    stringVariables.put(name,x);
	}
    }

    public Variable getVariable(String name)
	throws Exception{
	if(variables.containsKey(name)){
	    return variables.get(name);
	} else {
	    throw new Exception();
	}
    }

    public StringVariable getStringVariable(String name)
	throws Exception{
	if(stringVariables.containsKey(name)){
	    return stringVariables.get(name);
	} else {
	    throw new Exception();
	}
    }

    public static String concatenate(String[] array, int ini, int fin){
	String cat = "";
	for(int i=ini; i<fin; i++){
	    cat += array[i];
	}
	return cat;
    }

    public boolean isCommand(String key){
	for(int i=0; i<commands.length; i++){
	    if(key.equalsIgnoreCase(commands[i])){
		return true;
	    }
	}
	return false;
    }

    public boolean isKeyword(String key){
	for(int i=0; i<keys.length; i++){
	    if(key.equalsIgnoreCase(keys[i])){
		return true;
	    }
	}
	for(int i=0; i<commands.length; i++){
	    if(key.equalsIgnoreCase(commands[i])){
		return true;
	    }
	}
	int number = ASH.elementTable.findNumber(key);
	if(number >= 0){
	    return true;
	}
	return false;
    }

    public Variable getKeyValue(String key)
	throws Exception{
	Variable result;
	if(key.equalsIgnoreCase(K_CX)){
	    result = new Variable(key,frames.get(currentFrame).getCell()[0].toHorizontalVector());
	} else if(key.equalsIgnoreCase(K_CY)){
	    result = new Variable(key,frames.get(currentFrame).getCell()[1].toHorizontalVector());
	} else if(key.equalsIgnoreCase(K_CZ)){
	    result = new Variable(key,frames.get(currentFrame).getCell()[2].toHorizontalVector());
	} else if(key.equalsIgnoreCase(K_BX)){
	    if(frames.get(currentFrame).getBoundaryConditions()[0]){
		result = new Variable(key,1);
	    } else {
		result = new Variable(key,0);
	    }
	} else if(key.equalsIgnoreCase(K_BY)){
	    if(frames.get(currentFrame).getBoundaryConditions()[1]){
		result = new Variable(key,1);
	    } else {
		result = new Variable(key,0);
	    }
	} else if(key.equalsIgnoreCase(K_BZ)){
	    if(frames.get(currentFrame).getBoundaryConditions()[2]){
		result = new Variable(key,1);
	    } else {
		result = new Variable(key,0);
	    }
	} else if(key.equalsIgnoreCase(K_NFR)){
	    result = new Variable(key,frames.size());
	} else if(key.equalsIgnoreCase(K_FR)){
	    result = new Variable(key,currentFrame+1);
	} else if(key.equalsIgnoreCase(K_NPART)){
	    result = new Variable(key,frames.get(currentFrame).countParticles());
	} else if(key.equalsIgnoreCase(K_NATOM)){
	    result = new Variable(key,frames.get(currentFrame).getAllAtoms().length);
	} else if(key.equalsIgnoreCase(K_VIEW)){
	    result = new Variable(key,this.painter.getViewPoint().getPosition().toVerticalVector());
	} else if(key.equalsIgnoreCase(K_VUP)){
	    result = new Variable(key,this.painter.getViewPoint().getUp().toVerticalVector());
	} else if(key.equalsIgnoreCase(K_VZOOM)){
	    result = new Variable(key,this.painter.getViewPoint().getZoom());
	} else if(key.equalsIgnoreCase(K_VDIR)){
	    result = new Variable(key,this.painter.getViewPoint().getDirection().toVerticalVector());
	} else if(key.equalsIgnoreCase(K_PI)){
	    result = new Variable("pi",Math.PI);
	} else if(key.equalsIgnoreCase(K_E)){
	    result = new Variable("e",Math.E);
	} else if(key.equalsIgnoreCase(K_VERSION)){
	    result = new Variable("version",Double.parseDouble(VERSION));
	} else {
	    int number = ASH.elementTable.findNumber(key);
	    if(number >= 0){
		return new Variable(key,number);
	    } else {
		return getVariable(key);
	    }
	}
	return result;
    }

    public Variable evaluateExpression(String formula)
	throws Exception{

	if(isKeyword(formula)){
	    return getKeyValue(formula);
	} else {
	    ArrayList<FormulaBlock> bits = adder.splitExpression(formula);
	    //adder.writeFormula(bits);
	    adder.resetRecursionDepth();
	    return adder.evaluateExpression(bits);
	}
    }

    public int getCurrentFrame(){
	return this.currentFrame;
    }

    public Structure getCurrentStructure(){
	return frames.get(currentFrame);
    }

    public String getCurrentFrameName(){
	try{
	    return frames.get(this.currentFrame).getName();
	} catch(Exception error){
	    return "";
	}
    }

    public int getNumberOfFrames(){
	return this.frames.size();
    }

    public void hidePicked(boolean yesno){
	Structure geo = frames.get(currentFrame);
	ArrayList<Particle> parts = geo.getParticles();
	for(int i=0; i<parts.size(); i++){
	    if(parts.get(i).isPicked()){
		parts.get(i).hide(yesno);
	    }
	}
	this.painter.updateGeo();
    }

    public void printManual(String filename){
	String[] manLines = new String[mainCommands.length];
	try{
	    for(int i=0; i<mainCommands.length; i++){
		Command mand = commandTable.get(mainCommands[i]);
		manLines[i] = "\nManual for '"+mand.getName()+"'\n"+
		    "\nUsage: > "+mand.getUsage()+"\n"+
		    "\n"+mand.getInstructions()+"\n\n";
	    }	    
	    new FileHandler().writeFile(manLines,filename);
	} catch(Exception error){
	}
    }

    public void accessCalculatorManual(String com){
	adder.accessManual(com);
    }


    public void accessManual(String com){
	try{
	    Command mand = commandTable.get(com);
	    printMessage("\nManual for '"+mand.getName()+"'\n",true);
	    printMessage("Usage: > "+mand.getUsage(),true);
	    printMessage("\n"+mand.getInstructions()+"\n",true);
	} catch(Exception error){
	    // check for alias
	    try{
		Alias alias = getAlias(com);		
		String[] args = new String[alias.getArgumentCount()];
		for(int i=0; i<args.length; i++){
		    args[i] = "#"+(i+1);
		}
		printMessage("Manual for '"+com+"'",true);
		printMessage(com+" is an alias for '"+alias.getCommand(args)+"'",true);
	    } catch(Exception error2){
		if(com.length() > 0){
		    printMessage("an unknown command "+com,true);
		}
	    }
	}	
    }

    public static void recolorBackground(int r, int g, int b, int a){
	View.bg = new Color(r,g,b,a);
    }

    public static void recolorPick(int r, int g, int b, int a){
	View.picked = new Color(r,g,b,a);
	View.hiddenPicked = new Color(r,g,b,a/6);
    }

    public static void recolorUnpick(int r, int g, int b, int a){
	View.unpicked = new Color(r,g,b,a);
	View.hiddenUnpicked = new Color(r,g,b,a/6);
    }

    public static void recolorElement(int ele, int r, int g, int b, int a){
	elementTable.getElement(ele%elementTable.size()).setColor(new Color(r,g,b,a));
    }

    public static void resizeElement(int ele, double rad){
	elementTable.getElement(ele%elementTable.size()).setRadius(rad);
    }

    public static void main(String[] args){
	RNG = new Random();
	ASH nano = new ASH(args);
    }

    private abstract class Command{
	protected String cName;
	protected String[][] cOptions;
	protected String cArgs;
	protected String cInstructions;
	protected ASH master;
	protected int minValues;
	protected int maxValues;

	protected Command(ASH owner){
	    this.master = owner;
	    this.minValues = 0;
	    this.maxValues = 0;
	    cOptions = new String[0][0];
	    cArgs = "";
	    cInstructions = "";
	    cName = "";
	}

	protected String getName(){
	    return cName;
	};

	protected String[] getOptions(){
	    String[] namer = {cName};
	    String[] fullOptions = {""};
	    for(int i=0; i<cOptions.length; i++){
		String[] nextOps = new String[cOptions[i].length];
		for(int j=0; j<nextOps.length; j++){
		    if(cOptions[i][j].equals(C_NOOPTION)){
			nextOps[j] = "";
		    } else {
			nextOps[j] = " "+cOptions[i][j];
		    }
		}
		fullOptions = StringCombiner.comboPermute(fullOptions,nextOps,"");
	    }
	    return fullOptions;
	};

	protected String getUsage(){
	    String use = cName;
	    for(int i=0; i<cOptions.length; i++){
		boolean optional = false;
		String nextOps = "";
		int nOps = 0;
		for(int j=0; j<cOptions[i].length; j++){
		    if(cOptions[i][j].equals(C_NOOPTION)){
			optional = true;
		    } else {
			nOps++;
			if(nOps > 1){
			    nextOps += " / ";
			}
			nextOps += cOptions[i][j];
		    }
		}
		if(optional){
		    use += "  [ "+nextOps+" ]";
		} else {
		    use += "  "+nextOps;
		}
	    }
	    if(!cArgs.equals("")){
		use += "  "+cArgs;
	    }
	    if(maxValues > 0){
		if(minValues > 0){
		    if(minValues != maxValues){
			use += "  ("+minValues+"-"+maxValues+" parameters)";
		    } else {
			use += "  ("+maxValues+" parameters)";
		    }
		} else {
		    use += "  [ (0-"+maxValues+" parameters) ]";
		}
	    }
	    return use;
	}

	protected void setOptions(String[][] ops){
	    this.cOptions = ops;
	}

	protected String getInstructions(){
	    return cInstructions;
	};
	protected abstract void execute(String[] args)
	    throws Exception;
    }

    private class Exit extends Command{
	protected Exit(ASH owner){
	    super(owner);
	    cName = C_EXIT;
	    cInstructions = "Terminates the program.";
	}
	protected void execute(String[] args)
	    throws Exception{
	    master.notFinished = false;
	}
    }

    private class Print extends Command{
	protected Print(ASH owner){
	    super(owner);
	    cName = C_PRINT;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_SCREEN,C_OLDFILE,C_NEWFILE};
	    cOptions[0] = opt0;
	    cArgs = "[ (filename) ]  (message)";
	    cInstructions = "Writes the given strings on screen or in a file. If several strings are given, separated by spaces, they are printed one by one. (This is different to "+C_VALUE+", which removes all white spaces.) Any leading spaces are ignored, however. To include a value of a variable or an expression in the print output, wrap the variable name in dollar signs ($), e.g., $pi$, $1+1$. For more details, see the manual for '"+C_VALUE+"'.\n"+
		INSET+"(message) - By default, the given strings are printed on screen. This is invoked if the first argument given is neither "+C_SCREEN+", "+C_OLDFILE+" nor "+C_NEWFILE+".\n"+
		INSET+C_SCREEN+" (message) - Writes the message on screen. Usually, the option "+C_SCREEN+" is redundant, but if you want to start the message with, say, "+C_OLDFILE+" you need to include "+C_SCREEN+" first - otherwise it will be interpreted as a command for printing to a file.\n"+
		INSET+C_OLDFILE+" (filename) (message) - Appends the given message to a file of the given name.\n"+
		INSET+C_NEWFILE+" (filename) (message) - Writes the given message to a new file of the given name. Note that if a file exist by that name, it will be overwritten!";
	}
	protected void execute(String[] args)
	    throws Exception{
	    boolean screen = true;
	    boolean newfile = false;
	    int start = 0;
	    String message = "";
	    String filename = "";
	    if(args[0].equalsIgnoreCase(C_SCREEN)){
		start = 1;
	    } else if(args[0].equalsIgnoreCase(C_OLDFILE)){
		filename = args[1];
		start = 2;
		screen = false;
	    } else if(args[0].equalsIgnoreCase(C_NEWFILE)){
		filename = args[1];
		start = 2;
		screen = false;
		newfile = true;
	    }

	    for(int i=start; i< args.length; i++){
		message += args[i]+" ";
	    }

	    if(screen){
		printMessage(message,true);
	    } else {
		if(newfile){
		    String[] lines = {message};
		    new FileHandler().writeFile(lines,filename);
		} else {
		    String[] lines = {message};
		    new FileHandler().appendFile(lines,filename);
		}
	    }

	}
    }

    private class Pick extends Command{

	protected Pick(ASH owner){
	    super(owner);

	    minValues = 0;
	    maxValues = 6;
	    cName = C_PICK;
	    cOptions = new String[2][];
	    String[] opt0 = {C_NOOPTION,C_JOIN,C_OVER,C_INTERSECT};
	    cOptions[0] = opt0;
	    String[] opt1 = {C_ALL,C_XM,C_XL,C_YM,C_YL,C_ZM,C_ZL,
			     C_RANGE,C_SPHERE,C_ELE,C_PART,C_PARTS,C_DEFAULT};
	    cOptions[1] = opt1;
	    cInstructions = "Selects a group of particles for further operations.\n"+
		"Options:\n"+
		INSET+C_JOIN+" - A union of the currently selected particles and those specified by the command will become active.\n"+
		INSET+C_OVER+" - The currently selected particles will become inactive, then the particles specified by the command will become active. (default)\n"+
		INSET+C_INTERSECT+" - An intersection of the currently selected particles and those specified by the command will become active.\n"+
		INSET+C_DEFAULT+" - Turns the default pick mode on. That is, new particles are by default selected when introduced.\n"+
		INSET+INSET+C_ALL+" - Select all particles.\n"+
		INSET+INSET+C_XM+" (x) - The particles whose x coordinate is greater than the given real are selected.\n"+
		INSET+INSET+C_XL+" (x) - The particles whose x coordinate is less than the given real are selected.\n"+
		INSET+INSET+C_YM+" (y) - The particles whose y coordinate is greater than the given real are selected.\n"+
		INSET+INSET+C_YL+" (y) - The particles whose y coordinate is less than the given real are selected.\n"+
		INSET+INSET+C_ZM+" (z) - The particles whose z coordinate is greater than the given real are selected.\n"+
		INSET+INSET+C_ZL+" (z) - The particles whose z coordinate is less than the given real are selected.\n"+
		INSET+INSET+C_RANGE+" (vx) (vy) (vz) (ux) (uy) (uz) - The vector u defines a point in space and the vector v a normal vector "+
		"for a plane. Placing this plane at the given point, the particles located on that side of the plane to where the vector "+
		"points are selected.\n"+
		INSET+INSET+C_SPHERE+" (ux) (uy) (uz) (r) - The vector u defines a point in space and r a radius so that the particles within this radius are selected. If r is a vector, the norm of the vector is used as the radius.\n"+
		INSET+INSET+C_ELE+" (e1) [(e2)] - If only one integer argument is given, all atomic particles of that element type are selected. "+
		"If two integers are given, all elements in the range e1, e1+1, ..., e2 are selected.\n"+
		INSET+INSET+C_PART+" (p1) [(p2) [(p3) ... ]] - The particles whose indices are given are selected.\n"+
		INSET+INSET+C_PARTS+" (p1) (p2) - The particles in the index range p1, p1+1, ..., p2 are selected.";
	}

	protected void execute(String[] args)
	    throws Exception{

	    boolean join = false;
	    boolean keep = false;
	    int delta = 0;
	    if(args[0].equalsIgnoreCase(C_JOIN)){
		delta = 1;
		join = false;
		keep = true;
	    } else if(args[0].equalsIgnoreCase(C_INTERSECT)){
		delta = 1;
		join = true;
		keep = true;
	    } else if(args[0].equalsIgnoreCase(C_OVER)){
		delta = 1;
		join = false;
		keep = false;
	    } else if(args[0].equalsIgnoreCase(C_DEFAULT) && args.length == 1){
		ASH.defaultPick = true;
		return;
	    }
	    boolean yesno = true;
	    if(!keep){
		pickAll(false,false);
	    }

	    if(args[0+delta].equalsIgnoreCase(C_ALL)){
		delta ++;
		if(args.length != delta){
		    throw new Exception();
		}
		master.pickAll(yesno,join);
		master.painter.updateGeo();
	    } else if(args[0+delta].equalsIgnoreCase(C_XM) ||
		      args[0+delta].equalsIgnoreCase(C_XL) ||
		      args[0+delta].equalsIgnoreCase(C_YM) ||
		      args[0+delta].equalsIgnoreCase(C_YL) ||
		      args[0+delta].equalsIgnoreCase(C_ZM) ||
		      args[0+delta].equalsIgnoreCase(C_ZL) ||
		      args[0+delta].equalsIgnoreCase(C_RANGE) ){
		
		Vector dir = new Vector(1.0,0.0,0.0);
		Vector lim = new Vector(0.0,0.0,0.0);
		if(args[0+delta].equalsIgnoreCase(C_XM)){
		    delta ++;
		    dir = new Vector(1.0,0.0,0.0);
		    lim = new Vector(parseR(args[delta]),0.0,0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_XL)){
		    delta ++;
		    dir = new Vector(-1.0,0.0,0.0);
		    lim = new Vector(parseR(args[delta]),0.0,0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_YM)){
		    delta ++;
		    dir = new Vector(0.0,1.0,0.0);
		    lim = new Vector(0.0,parseR(args[delta]),0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_YL)){
		    delta ++;
		    dir = new Vector(0.0,-1.0,0.0);
		    lim = new Vector(0.0,parseR(args[delta]),0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_ZM)){
		    delta ++;
		    dir = new Vector(0.0,0.0,1.0);
		    lim = new Vector(0.0,0.0,parseR(args[delta]));
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_ZL)){
		    delta ++;
		    dir = new Vector(0.0,0.0,-1.0);
		    lim = new Vector(0.0,0.0,parseR(args[delta]));
		    delta ++;
		} else {
		    delta ++;
		    delta += findVector(args,delta,dir);
		    delta += findVector(args,delta,lim);
		}
		if(args.length != delta){
		    throw new Exception();
		}
		master.pickArea(lim,dir,yesno,join);
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_ELE)){
		
		for(int j=1+delta; j<args.length; j++){
		    master.pickElement(parseI(args[j]),yesno,join);
		}
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_PART)){
		
		for(int j=1+delta; j<args.length; j++){
		    master.pickAtom(parseI(args[j]),yesno,join);
		}
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_PARTS)){
		
		if(args.length == 3+delta){
		    int first=parseI(args[1+delta]);
		    int second=parseI(args[2+delta]);
		    for(int j=first; j<second; j++){
			master.pickAtom(j,yesno,join);
		    }
		} else {
		    throw new Exception();
		}
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_SPHERE)){
		
		delta++;
		Vector point = new Vector(3);
		delta += findVector(args,delta,point);

		double radius = 0.0;
		try{
		    Vector rvec = new Vector(3);
		    int read = findVector(args,delta,rvec);
		    delta += read;
		    radius = rvec.norm();
		} catch(Exception error){
		    radius = parseR(args[delta]);
		    delta ++;
		}
		if(args.length != delta){
		    throw new Exception();
		}
		master.pickSphere(point,radius,yesno,join);
		master.painter.updateGeo();

	    } else {
		throw new Exception();
	    }
	}
    }



    private class Unpick extends Command{

	protected Unpick(ASH owner){
	    super(owner);

	    minValues = 0;
	    maxValues = 6;
	    cName = C_UNPICK;
	    cOptions = new String[2][];
	    String[] opt0 = {C_NOOPTION,C_JOIN,C_OVER,C_INTERSECT};
	    cOptions[0] = opt0;
	    String[] opt1 = {C_ALL,C_XM,C_XL,C_YM,C_YL,C_ZM,C_ZL,
			     C_RANGE,C_ELE,C_PART,C_PARTS,C_SPHERE};
	    cOptions[1] = opt1;
	    cInstructions = "Deselects a group of particles to be excluded from further operations.\n"+
		"Options:\n"+
		INSET+C_JOIN+" - A union of the currently selected particles and those specified by the command will become active.\n"+
		INSET+C_OVER+" - The currently selected particles will become inactive, then the particles specified by the command will become active.\n"+
		INSET+C_INTERSECT+" - An intersection of the currently selected particles and those specified by the command will become active. (default)\n"+
		INSET+C_DEFAULT+" - Turns the default pick mode off. That is, new particles are by default deselected when introduced.\n"+
		INSET+INSET+C_ALL+" - Deselect all particles.\n"+
		INSET+INSET+C_XM+" (x) - The particles whose x coordinate is greater than the given real are deselected.\n"+
		INSET+INSET+C_XL+" (x) - The particles whose x coordinate is less than the given real are deselected.\n"+
		INSET+INSET+C_YM+" (y) - The particles whose y coordinate is greater than the given real are deselected.\n"+
		INSET+INSET+C_YL+" (y) - The particles whose y coordinate is less than the given real are deselected.\n"+
		INSET+INSET+C_ZM+" (z) - The particles whose z coordinate is greater than the given real are deselected.\n"+
		INSET+INSET+C_ZL+" (z) - The particles whose z coordinate is less than the given real are deselected.\n"+
		INSET+INSET+C_RANGE+" (vx) (vy) (vz) (ux) (uy) (uz) - The vector u defines a point in space and the vector v a normal vector "+
		"for a plane. Placing this plane at the given point, the particles located on that side of the plane to where the vector "+
		"points are deselected.\n"+
		INSET+INSET+C_SPHERE+" (ux) (uy) (uz) (r) - The vector u defines a point in space and r a radius so that the particles within this radius are deselected. If r is a vector, the norm of the vector is used as the radius.\n"+
		INSET+INSET+C_ELE+" (e1) [(e2)] - If only one integer argument is given, all atomic particles of that element type are deselected. "+
		"If two integers are given, all elements in the range e1, e1+1, ..., e2 are deselected.\n"+
		INSET+INSET+C_PART+" (p1) [(p2) [(p3) ... ]] - The particles whose indices are given are deselected.\n"+
		INSET+INSET+C_PARTS+" (p1) (p2) - The particles in the index range p1, p1+1, ..., p2 are deselected.";
	}

	protected void execute(String[] args)
	    throws Exception{

	    boolean join = false;
	    boolean keep = true;
	    int delta = 0;
	    if(args[0].equalsIgnoreCase(C_JOIN)){
		delta = 1;
		join = true;
		keep = true;
	    } else if(args[0].equalsIgnoreCase(C_INTERSECT)){
		delta = 1;
		join = false;
		keep = true;
	    } else if(args[0].equalsIgnoreCase(C_OVER)){
		delta = 1;
		join = false;
		keep = false;
	    } else if(args[0].equalsIgnoreCase(C_DEFAULT) && args.length == 1){
		ASH.defaultPick = false;
		return;
	    }
	    boolean yesno = false;
	    if(!keep){
		pickAll(true,false);
	    }

	    if(args[0+delta].equalsIgnoreCase(C_ALL)){
		delta ++;
		if(args.length != delta){
		    throw new Exception();
		}
		master.pickAll(yesno,join);
		master.painter.updateGeo();	
	    } else if(args[0+delta].equalsIgnoreCase(C_XM) ||
		      args[0+delta].equalsIgnoreCase(C_XL) ||
		      args[0+delta].equalsIgnoreCase(C_YM) ||
		      args[0+delta].equalsIgnoreCase(C_YL) ||
		      args[0+delta].equalsIgnoreCase(C_ZM) ||
		      args[0+delta].equalsIgnoreCase(C_ZL) ||
		      args[0+delta].equalsIgnoreCase(C_RANGE) ){
		Vector dir = new Vector(1.0,0.0,0.0);
		Vector lim = new Vector(0.0,0.0,0.0);
		if(args[0+delta].equalsIgnoreCase(C_XM)){
		    delta ++;
		    dir = new Vector(1.0,0.0,0.0);
		    lim = new Vector(parseR(args[delta]),0.0,0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_XL)){
		    delta ++;
		    dir = new Vector(-1.0,0.0,0.0);
		    lim = new Vector(parseR(args[delta]),0.0,0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_YM)){
		    delta ++;
		    dir = new Vector(0.0,1.0,0.0);
		    lim = new Vector(0.0,parseR(args[delta]),0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_YL)){
		    delta ++;
		    dir = new Vector(0.0,-1.0,0.0);
		    lim = new Vector(0.0,parseR(args[delta]),0.0);
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_ZM)){
		    delta ++;
		    dir = new Vector(0.0,0.0,1.0);
		    lim = new Vector(0.0,0.0,parseR(args[delta]));
		    delta ++;
		} else if(args[0+delta].equalsIgnoreCase(C_ZL)){
		    delta ++;
		    dir = new Vector(0.0,0.0,-1.0);
		    lim = new Vector(0.0,0.0,parseR(args[delta]));
		    delta ++;
		} else {
		    delta ++;
		    delta += findVector(args,delta,dir);
		    delta += findVector(args,delta,lim);
		}
		if(args.length != delta){
		    throw new Exception();
		}
		master.pickArea(lim,dir,yesno,join);
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_ELE)){
		
		for(int j=1+delta; j<args.length; j++){
		    master.pickElement(parseI(args[j]),yesno,join);
		}
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_PART)){
		
		for(int j=1+delta; j<args.length; j++){
		    master.pickAtom(parseI(args[j]),yesno,join);
		}
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_PARTS)){
		
		if(args.length == 3+delta){
		    int first=parseI(args[1+delta]);
		    int second=parseI(args[2+delta]);
		    for(int j=first; j<second; j++){
			master.pickAtom(j,yesno,join);
		    }
		} else {
		    throw new Exception();
		}
		master.painter.updateGeo();
		
	    } else if(args[0+delta].equalsIgnoreCase(C_SPHERE)){
		
		delta++;
		Vector point = new Vector(3);
		delta += findVector(args,delta,point);

		double radius = 0.0;
		try{
		    Vector rvec = new Vector(3);
		    int read = findVector(args,delta,rvec);
		    delta += read;
		    radius = rvec.norm();
		} catch(Exception error){
		    radius = parseR(args[delta]);
		    delta ++;
		}
		if(args.length != delta){
		    throw new Exception();
		}
		master.pickSphere(point,radius,yesno,join);
		master.painter.updateGeo();
		
	    } else {
		throw new Exception();
	    }
	}
    }


    private class List extends Command{
	protected List(ASH owner){
	    super(owner);
	    cName = C_LIST;
	    minValues = 0;
	    maxValues = 1;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_PARTS,C_ELE,C_CELL,C_VALUE,C_ALIAS,C_COMM,C_FUNC,C_NBOR};
	    cOptions[0] = opt0;
	    cInstructions = "Prints data on screen.\n"+
		"Options:\n"+
		INSET+"- By default, the indices and coordinates of the currently selected particles are listed.\n"+
		INSET+C_PARTS+" - The indices and coordinates of all particles of the current frame are listed.\n"+
		INSET+C_ELE+" - The names and indices as well as the number of such particles in the current frame are listed.\n"+
		INSET+C_CELL+" - The components of the supercell vectors are listed (in Cartesian coordinates).\n"+
		INSET+C_VALUE+" - The names and stored values of the currently defined variables are listed.\n"+
		INSET+C_FUNC+" - The names and syntax of the currently defined functions are listed.\n"+
		INSET+C_ALIAS+" - The names and stored commands of the currently defined aliases are listed.\n"+
		INSET+C_COMM+" - The available commands are listed.\n"+
		INSET+C_NBOR+" [n] - Lists the n closest neighbors of all currently selected particles. By default, n = 8. Note that the lists are made for particles, not atoms, and they include all particles in the system, even unselected ones.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 0){
		master.listPicked();
	    } else if(args.length == 1){
		if(args[0].equalsIgnoreCase(C_PARTS)){
		    master.listAll();
		} else if(args[0].equalsIgnoreCase(C_ELE)){
		    master.listElements();
		} else if(args[0].equalsIgnoreCase(C_CELL)){
		    master.listCell();
		} else if(args[0].equalsIgnoreCase(C_VALUE)){
		    master.listVariables();
		} else if(args[0].equalsIgnoreCase(C_FUNC)){
		    master.listFunctions();
		} else if(args[0].equalsIgnoreCase(C_ALIAS)){
		    master.listAliases();
		} else if(args[0].equalsIgnoreCase(C_COMM)){
		    master.listCommands();
		} else if(args[0].equalsIgnoreCase(C_NBOR)){
		    master.listNeighbors(8);
		} else {
		    throw new Exception();
		}
	    } else if(args.length == 2){		
		if(args[0].equalsIgnoreCase(C_NBOR)){
		    master.listNeighbors(parseI(args[1]));
		}
	    } else {
		throw new Exception();
	    }
	}
    }



    private class Clusterize extends Command{
	protected Clusterize(ASH owner){
	    super(owner);
	    cName = C_CLU;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Joins selected particles to a cluster, which is then treated as a single particle with structure. The structure of smaller clusters are retained when joint to bigger clusters.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    master.rememberStructure();
	    try{
		int cluster = master.makeCluster();
		printMessage("joint "+cluster+" particles to a cluster");
	    } catch(Exception error){
		printMessage("empty selection");
	    }
	    master.painter.updateGeo();

	}
    }


    private class Unclusterize extends Command{
	protected Unclusterize(ASH owner){
	    super(owner);
	    cName = C_UNCLU;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Breaks all selected clusters to their constituent particles. Only one level of clusterization is removed, so a cluster containing smaller clusters is broken into these smaller clusters instead of atomic particles.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    master.rememberStructure();
	    int parts = master.breakCluster();
	    printMessage("broke clusters to "+parts+" particles");
	    master.painter.updateGeo();

	}
    }



    private class Rotate extends Command{
	protected Rotate(ASH owner){
	    super(owner);
	    cName = C_ROT;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_X,C_Y,C_Z,C_AXIS};
	    cOptions[0] = opt0;
	    cInstructions = "Rotates selected clusters around their centers. Atomic particles are not affected since they have no internal structure. If you wish to rotate the entire system, you should first join all particles in one cluster.\n"+
		"Options:\n"+
		INSET+C_X+" (angle) - Rotation around the x-axis by the given angle.\n"+
		INSET+C_Y+" (angle) - Rotation around the y-axis by the given angle.\n"+
		INSET+C_Z+" (angle) - Rotation around the z-axis by the given angle.\n"+
		INSET+C_AXIS+" (ax) (ay) (az) (angle) - Rotation around the vector [ax, ay, az] by the given angle.";
	    cArgs = "[(ax) (ay) (az)]  (angle)";
	}
	protected void execute(String[] args)
	    throws Exception{

	    Vector axis = new Vector(0,0,1);
	    int delta = 0;
	    if(args[0].equalsIgnoreCase(C_X)){
		axis = new Vector(1,0,0);
	    } else if(args[0].equalsIgnoreCase(C_Y)){
		axis = new Vector(0,1,0);
	    } else if(args[0].equalsIgnoreCase(C_Z)){
		axis = new Vector(0,0,1);
	    } else if(args[0].equalsIgnoreCase(C_AXIS)){
		delta = findVector(args,1,axis);
	    } else {
		throw new Exception();
	    }
	    if(args.length != 2+delta){
		throw new Exception();
	    }

	    double angle = parseR(args[1+delta]);
	    Quaternion rotator = new Quaternion(axis,angle);
	    rememberStructure();
	    Structure geo = frames.get(currentFrame);
	    for(int i=0; i<geo.countParticles(); i++){
		if(geo.getParticle(i).isPicked()){
		    geo.getParticle(i).rotate(rotator);
		}
	    }
	    frames.get(currentFrame).calculateCenter();
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();	    
	}
    }



    private class Shift extends Command{
	protected Shift(ASH owner){
	    super(owner);
	    cName = C_SHIFT;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Shifts selected particles by a given vector. If the system is periodic, periodic boundary conditions are automatically applied on atomic particles. Periodicity is not employed on single atoms within clusters, but on the entire cluster (as determined by the center of the cluster).";
	    cArgs = "(x)  (y)  (z)";
	}
	protected void execute(String[] args)
	    throws Exception{

	    /**
	       Vector shift = new Vector(parseR(args[0]),
	       parseR(args[1]),
	       parseR(args[2]));
	    */
	    Vector shift = new Vector(3);
	    int delta = findVector(args, 0, shift);
	    // excess arguments? we could just ignore them, but maybe it's better to notify the user
	    if(delta != args.length){ 
		throw new Exception();
	    }
	    rememberStructure();
	    shiftAtoms(shift);
	    frames.get(currentFrame).calculateCenter();
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();
	}
    }



    private class Constrain extends Command{
	protected Constrain(ASH owner){
	    super(owner);
	    cName = C_FREEZE;
	    cArgs = "[ (fx) (fy) (fz) ]";
	    cInstructions = "Manipulates atomic constraints. Often, one may wish to freeze certain atoms in a simulation. This command allows you to handle the flags for atomic constraints in three directions. These flags are meant to be printed and used by an external simulation software - they have no effect on how the atoms are handled in ASH.\n"+
		INSET+"- By default, the command sets the constraint flags of all selected particles to false.\n"+
		INSET+" (fx) (fy) (fz) - Sets the constraint flags of all selected particles to the given values. The values may be given using keywords 'f', 't', 'false', or 'true', or as variables for which the values 1 and 0 are interpreted as true and false, respectively. Other values are interpreted to mean that the corresponding flag should not be touched.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    int[] flags = {-1,-1,-1};
	    int delta = 0;
	    if(args.length > 0){
		try{
		    Vector froze = new Vector(3);
		    delta = findVector(args, 0, froze);
		    for(int i = 0; i<3; i++){
			if((int)(froze.element(i)) == 0){
			    flags[i] = 0;
			} else if((int)(froze.element(i)) == 1){
			    flags[i] = 1;
			}
		    }
		} catch(Exception error){
		    for(int i=0; i<3; i++){
			int formula = -1;
			try{
			    formula = parseI(args[i]);
			} catch(Exception error2){}
			if(args[i].equalsIgnoreCase("t") ||
			   args[i].equalsIgnoreCase("true")){
			    flags[i] = 1;
			} else if(args[i].equalsIgnoreCase("f") ||
			   args[i].equalsIgnoreCase("false") ){
			    flags[i] = 0;
			} else if(formula == 1){
			    flags[i] = 1;
			} else if(formula == 0){
			    flags[i] = 0;
			}
		    }
		}
		// excess arguments? we could just ignore them, but maybe it's better to notify the user
		if(delta != args.length){ 
		    throw new Exception();
		}
	    } else {
		flags[0] = 0;
		flags[1] = 0;
		flags[2] = 0;
	    }

	    rememberStructure();
	    Atom[] atoms = frames.get(currentFrame).getAllAtoms();
	    for(int i=0; i<atoms.length; i++){
		if(atoms[i].isPicked()){
		    boolean[] freedom = atoms[i].getFrozenCoordinates();
		    for(int j=0; j<3; j++){
			if(flags[j] == 1){
			    freedom[j] = true;
			} else if(flags[j] == 0){
			    freedom[j] = false;
			}
		    }
		    atoms[i].setFrozenCoordinates(freedom);
		}
	    }

	    frames.get(currentFrame).calculateCenter();
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();
	}
    }




    private class Delete extends Command{
	protected Delete(ASH owner){
	    super(owner);
	    cName = C_DEL;
	    minValues = 0;
	    maxValues = 2;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_DUPLO};
	    cOptions[0] = opt0;
	    cInstructions = "Deletes particles.\n"+
		"Options:\n"+
		INSET+"[(i1) [(i2)]] - By default, the selected particles are annihilated. If one integer argument is given, the particle with corresponding index is deleted. If two arguments are given, (i1) (i2), the particles within the range i1, i1+1, ..., i2 are deleted.\n"+
		INSET+C_DUPLO+" [(range)] - Duplicate particles are deleted. That is, if there are two similar particles at a distance of less than a certain range, the latter (one with greater index) is destroyed. By default the range is 0.1, but the user can specify any range.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 0){
		rememberStructure();
		removePicked();
	    } else if(args.length == 1){
		if(args[0].equalsIgnoreCase(C_DUPLO)){
		    rememberStructure();
		    frames.get(currentFrame).removeDuplicates(0.1);
		} else {
		    rememberStructure();
		    removeParticle(parseI(args[0]));		    
		}
	    } else if(args.length == 2){
		if(args[0].equalsIgnoreCase(C_DUPLO)){
		    rememberStructure();
		    frames.get(currentFrame).removeDuplicates(parseR(args[1]));
		} else {
		    int firstrm = parseI(args[0]);
		    int lastrm = parseI(args[1]);
		    for(int i=firstrm; i<lastrm+1; i++){
			rememberStructure();
			removeParticle(firstrm);
		    }
		}
	    } else {
		throw new Exception();
	    }
	    try{
		frames.get(currentFrame).calculateCenter();
		master.painter.aimAtCenter();
		master.painter.updateGeo();
	    } catch(Exception error){}
	}
    }


    private class Add extends Command{
	protected Add(ASH owner){
	    super(owner);
	    cName = C_ADD;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Creates an atom in the system at the given position. Also the element of the atom can be specified by an index or a chemical symbol. To introduce new types of atoms (say, 'oxygen+'), give the name of the new species as the element and a new index starting from "+PeriodicTable.UNKNOWN+" is automatically given. By default, the atom will be of element 'Atom0'.";
	    cArgs = "(x)  (y)  (z)  [ (element) ]";
	}
	protected void execute(String[] args)
	    throws Exception{

	    Vector position = new Vector(3);
	    int delta = findVector(args,0,position);
	    Atom create = new Atom(position);
	    Element theElement;
	    if(args.length == delta+1){	    
		theElement = parseElement(args[delta]);
	    } else if(args.length == delta){
		theElement = ASH.elementTable.getElement(PeriodicTable.UNKNOWNNAME+0);
	    } else {
		throw new Exception();
	    }
	    create.setElement(theElement);
	    
	    rememberStructure();
	    addParticle(create);
	    frames.get(currentFrame).calculateCenter();
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();

	}
    }




    private class Copy extends Command{
	protected Copy(ASH owner){
	    super(owner);
	    cName = C_COPY;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[2][];
	    String[] opt0 = {C_NOOPTION,C_JOIN,C_OVER};
	    cOptions[0] = opt0;
	    String[] opt1 = {C_NOOPTION,C_CELL};
	    cOptions[1] = opt1;
	    cArgs = "[ (slot) ]";
	    cInstructions = "Copies structures on the clipboard. Only currently selected particles are copied. By default, supercell information is not copied.\n"+
		"Options:\n"+
		INSET+C_JOIN+" - The structure already on the clipboard is appended.\n"+
		INSET+C_OVER+" - The structure already on the clipboard is discarded and replaced. (default)\n"+
		INSET+INSET+C_CELL+" - The supercell is also copied on the clipboard.\n"+
		INSET+INSET+INSET+" [(slot)] - There are "+CLIPSIZE+" slots for copies. By default, slot 0 is used, but this can be specified by giving an index.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    Structure geo = frames.get(currentFrame);
	    int delta = 0;
	    boolean overwrite = false;
	    boolean alsocell = false;
	    if(args.length > 0 && args[0+delta].equalsIgnoreCase(C_JOIN)){
		delta++;
	    } else {
		if(args.length > 0 && args[0+delta].equalsIgnoreCase(C_OVER)){
		    delta++;
		}
		overwrite = true;
	    }
	    if(args.length > 0+delta && args[0+delta].equalsIgnoreCase(C_CELL)){
		alsocell = true;
		delta++;
	    }
	    int slot = 0;
	    if(args.length > 0+delta){
		try{
		    slot = parseI(args[delta]);
		    delta++;
		    if(slot < 0 || slot >= CLIPSIZE){
			slot = 0;
			throw new Exception();
		    }
		} catch(Exception error){
		    printMessage("Invalid slot index (must be an integer between 0 and "+(CLIPSIZE-1)+"), using default slot 0.");
		}
	    }
	    int copied = clipboardAtoms[slot].copyPart(geo,overwrite,alsocell);
	    printMessage("copied "+copied+" particles");
	}
    }



    private class Cut extends Command{
	protected Cut(ASH owner){
	    super(owner);
	    cName = C_CUT;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[2][];
	    String[] opt0 = {C_NOOPTION,C_JOIN,C_OVER};
	    cOptions[0] = opt0;
	    String[] opt1 = {C_NOOPTION,C_CELL};
	    cOptions[1] = opt1;
	    cArgs = "[ (slot) ]";
	    cInstructions = "Copies structures on the clipboard and then deletes them. Only currently selected particles are copied. By default, supercell information is not copied.\n"+
		"Options:\n"+
		INSET+C_JOIN+" - The structure already on the clipboard is appended.\n"+
		INSET+C_OVER+" - The structure already on the clipboard is discarded and replaced. (default)\n"+
		INSET+INSET+C_CELL+" - The supercell is also copied on the clipboard.\n"+
		INSET+INSET+INSET+" [(slot)] - There are "+CLIPSIZE+" slots for copies. By default, slot 0 is used, but this can be specified by giving an index.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    Structure geo = frames.get(currentFrame);
	    int delta = 0;
	    boolean overwrite = false;
	    boolean alsocell = false;
	    if(args.length > 0 && args[0+delta].equalsIgnoreCase(C_JOIN)){
		delta++;
	    } else {
		if(args.length > 0 && args[0+delta].equalsIgnoreCase(C_OVER)){
		    delta++;
		}
		overwrite = true;
	    }
	    if(args.length > 0+delta && args[0+delta].equalsIgnoreCase(C_CELL)){
		alsocell = true;
		delta++;
	    }
	    int slot = 0;
	    if(args.length > 0+delta){
		try{
		    slot = parseI(args[delta]);
		    delta++;
		    if(slot < 0 || slot >= CLIPSIZE){
			slot = 0;
			throw new Exception();
		    }
		} catch(Exception error){
		    printMessage("Invalid slot index (must be an integer between 0 and "+(CLIPSIZE-1)+"), using default slot 0.");
		}
	    }
	    int copied = clipboardAtoms[slot].copyPart(geo,overwrite,alsocell);
	    printMessage("copied "+copied+" particles");
	    rememberStructure();
	    removePicked();
	}
    }


    private class Paste extends Command{
	protected Paste(ASH owner){
	    super(owner);
	    cName = C_PASTE;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[2][];
	    String[] opt0 = {C_NOOPTION,C_JOIN,C_OVER};
	    cOptions[0] = opt0;
	    String[] opt1 = {C_NOOPTION,C_CELL};
	    cOptions[1] = opt1;
	    cArgs = "[ (slot) ]";
	    cInstructions = "Pastes structures from the clipboard. By default, supercell information is not pasted.\n"+
		"Options:\n"+
		INSET+C_JOIN+" - The current structure is appended with the data on the clipboard. (default)\n"+
		INSET+C_OVER+" - The current structure is discarded and replaced by the data on the clipboard.\n"+
		INSET+INSET+C_CELL+" - The current supercell data is replaced by the data on the clipboard.\n"+
		INSET+INSET+INSET+" [(slot)] - There are "+CLIPSIZE+" slots for copies. By default, slot 0 is used, but this can be specified by giving an index.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    Structure geo = frames.get(currentFrame);
	    int delta = 0;
	    boolean overwrite = false;
	    boolean alsocell = false;
	    if(args.length > 0 && args[0+delta].equalsIgnoreCase(C_OVER)){
		delta++;
		overwrite = true;
	    } else {
		if(args.length > 0 && args[0+delta].equalsIgnoreCase(C_JOIN)){
		    delta++;
		}
	    }
	    if(args.length > 0+delta && args[0+delta].equalsIgnoreCase(C_CELL)){
		alsocell = true;
		delta++;
	    }
	    rememberStructure();
	    int slot = 0;
	    if(args.length > 0+delta){
		try{
		    slot = parseI(args[delta]);
		    delta++;
		    if(slot < 0 || slot >= CLIPSIZE){
			slot = 0;
			throw new Exception();
		    }
		} catch(Exception error){
		    printMessage("Invalid slot index (must be an integer between 0 and "+(CLIPSIZE-1)+"), using default slot 0.");
		}
	    }	    
	    int pasted = geo.copyPart(clipboardAtoms[slot],overwrite,alsocell);
	    printMessage("pasted "+pasted+" particles");
	    geo.forcePeriodicBounds();
	    geo.calculateCenter();
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();
	}
    }






    private class SetCell extends Command{
	protected SetCell(ASH owner){
	    super(owner);
	    cName = C_MOD;
	    minValues = 1;
	    maxValues = 4;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_X,C_Y,C_Z};
	    cOptions[0] = opt0;
	    cInstructions = "Sets supercell axis vectors. Note that the supercell vectors cannot be linearly dependent. If you try to define a vector such that the supercell becomes degenerate, the program will ignore the command.\n"+
		"Options:\n"+
		INSET+"(cell matrix) - By default, the command expects the full supercell in matrix format where the row vectors define the supercell vectors.\n"+
		INSET+C_X+" (cx) [(cy) (cz)] [(pbc)] - Reset the first cell vector. If only one scalar argument, (cx), is given, the vector is set to [cx, 0, 0]. Otherwise all three components of the vector are expected as arguments. In addition, one can specify if this direction is periodic. (For periodic bounds, give 't', 'true', 'pbc', or '1'; for free bounds, give 'f', 'false', 'free', or '0'.)\n"+
		INSET+C_Y+" [(cx)] (cy) [(cz)] [(pbc)] - Reset the second cell vector. If only one scalar argument, (cy), is given, the vector is set to [0, cy, 0]. Otherwise all three components of the vector are expected as arguments. In addition, one can specify if this direction is periodic. (For periodic bounds, give 't', 'true', 'pbc', or '1'; for free bounds, give 'f', 'false', 'free', or '0'.)\n"+
		INSET+C_Z+" [(cx) (cy)] (cz) [(pbc)] - Reset the first cell vector. If only one scalar argument, (cz), is given, the vector is set to [0, 0, cz]. Otherwise all three components of the vector are expected as arguments. In addition, one can specify if this direction is periodic. (For periodic bounds, give 't', 'true', 'pbc', or '1'; for free bounds, give 'f', 'false', 'free', or '0'.)\n";
	}
	protected void execute(String[] args)
	    throws Exception{

	    int cnate = -1;
	    if(args[0].equalsIgnoreCase(C_CELLX)){
		cnate = 0;
	    } else if(args[0].equalsIgnoreCase(C_CELLY)){
		cnate = 1;
	    } else if(args[0].equalsIgnoreCase(C_CELLZ)){
		cnate = 2;
	    }
	    Structure geo = frames.get(currentFrame); 
	    Vector[] cell = geo.getCellCopy();

	    boolean[] bounds = geo.getBoundaryConditions();
	    if(cnate >= 0){

		int delta = 1;
		Vector newv = new Vector(3);		
		try{
		    int read = findVector(args,delta,newv);
		    delta += read;
		} catch(Exception error){
		    newv.setElement(cnate,parseR(args[delta]));
		    delta ++;
		}
		cell[cnate] = newv;

		if(args.length == delta+1){
		    int formula = -1;
		    try{
			formula = parseI(args[4]);
		    } catch(Exception error){}
		    if(args[delta].equalsIgnoreCase("t") ||
		       args[delta].equalsIgnoreCase("true") ||
		       args[delta].equalsIgnoreCase("pbc") ){
			bounds[cnate] = true;
		    } else if(args[delta].equalsIgnoreCase("f") ||
		       args[delta].equalsIgnoreCase("false") ||
		       args[delta].equalsIgnoreCase("free") ){
			bounds[cnate] = false;
			// 'formula' is separated, because 'true' etc. override possible
			// variables with the same name
		    } else if(formula == 1){
			bounds[cnate] = true;
		    } else if(formula == 0){
			bounds[cnate] = false;
		    }
		} else if(args.length > delta+1){
		    throw new Exception();
		}
	    } else {
		double[][] matrix = parseMatrix(args[0]);
		if(matrix.length == 3 && matrix[0].length == 3){
		    for(int i=0; i<3; i++){
			cell[i] = new Vector(3);
			for(int j=0; j<3; j++){
			    cell[i].setElement(j,matrix[i][j]);
			}
		    }
		}
	    }
	    rememberStructure();
	    boolean nan = false;
	    for(int i=0; i<3; i++){
		for(int j=0; j<3; j++){
		    Double wrap = new Double(cell[i].element(j));
		    if(wrap.isNaN() ||
		       wrap.isInfinite() ){
			nan = true;
		    }
		}
	    }
	    if(nan){
		printMessage("NaN component in a cell vector, keeping old supercell",true);		
	    } else {
		boolean success = geo.setCell(cell,bounds);
		geo.forcePeriodicBounds();
		geo.calculateCenter();
		master.painter.aimAtCenter();
		master.painter.updateGeo();
		if(success){
		    printMessage("new supercell: ");
		    for(int i=0; i<3; i++){
			printMessage("cell vector "+(i+1)+
				     "   ("+FileHandler.formattedDouble(cell[i].element(0),12,5)+
				     ", "+FileHandler.formattedDouble(cell[i].element(1),12,5)+
				     ", "+FileHandler.formattedDouble(cell[i].element(2),12,5)+")");
		    }
		} else {
		    printMessage("linearly dependent cell vectors, keeping old supercell",true);
		}
	    }
	}
    }


    private class Expand extends Command{
	protected Expand(ASH owner){
	    super(owner);
	    cName = C_EXP;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_X,C_Y,C_Z};
	    cOptions[0] = opt0;
	    cArgs = "(multiplier)";
	    cInstructions = "Multiplies the structure in the given direction by the given number of times. This means that the particles are duplicated and shifted as defined by the supercell vectors. Finally, the supercell vectors are also stretched. Boundary conditions remain as they were; they can be changed using the command '"+C_MOD+"'.\n"+
		"Options:\n"+
		INSET+"(multiplier) - Expands the structure in all directions according to the multiplier. If the multiplier is a vector, the integer parts of the components are used as expansion factors for the corresponding directions.\n"+
		INSET+C_X+" (multiplier) - Expands the structure in the direction of the first supercell vector.\n"+
		INSET+C_Y+" (multiplier) - Expands the structure in the direction of the second supercell vector.\n"+
		INSET+C_Z+" (multiplier) - Expands the structure in the direction of the third supercell vector.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    boolean expand = true;
	    Structure geo = frames.get(currentFrame); 
	    Vector[] cell = geo.getCellCopy();
	    Vector[] shifter = geo.getCellCopy();
	    int factors[] = new int[3];
	    int delta = 1;
	    if(args[0].equalsIgnoreCase(C_X)){
		int multiplier = parseI(args[1]);
		factors[0] = multiplier;
		factors[1] = 1;
		factors[2] = 1;
		delta++;
		if(multiplier <= 0){
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_Y)){
		int multiplier = parseI(args[1]);
		factors[0] = 1;
		factors[1] = multiplier;
		factors[2] = 1;
		delta++;
		if(multiplier <= 0){
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_Z)){
		int multiplier = parseI(args[1]);
		factors[0] = 1;
		factors[1] = 1;
		factors[2] = multiplier;
		delta++;
		if(multiplier <= 0){
		    throw new Exception();
		}
	    } else {
		delta = 0;
		try{
		    Vector fact = new Vector(3);
		    int read = findVector(args,delta,fact);
		    delta += read;
		    for(int i=0; i<3; i++){
			factors[i] = (int)fact.element(i);
			if(factors[i] <= 0){
			    throw new Exception();
			}
		    }
		} catch(Exception error){
		    int exvalue = parseI(args[delta]);
		    delta++;
		    for(int i=0; i<3; i++){
			factors[i] = exvalue;
			if(factors[i] <= 0){
			    throw new Exception();
			}
		    }		    
		}
	    }
	    
	    if(args.length != delta){
		throw new Exception();
	    }

	    rememberStructure();
	    for(int i=0; i<3; i++){
		cell[i] = cell[i].times(factors[i]);
	    }
	    geo.setCell(cell,geo.getBoundaryConditions());
	    int initialCount = geo.countParticles();
	    for(int i=0; i<initialCount; i++){
		for(int ix=0; ix<factors[0]; ix++){
		    for(int iy=0; iy<factors[1]; iy++){
			for(int iz=0; iz<factors[2]; iz++){
			    if(ix > 0 || iy > 0 || iz > 0){
				Particle newp = geo.getParticle(i).copy();
				newp.shiftCoordinates(shifter[0].times(ix));
				newp.shiftCoordinates(shifter[1].times(iy));
				newp.shiftCoordinates(shifter[2].times(iz));
				geo.addParticle(newp);
			    }
			}
		    }
		}
	    }
	    geo.forcePeriodicBounds();
	    geo.calculateCenter();
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();
	
	}
    }


    private class Resize extends Command{
	protected Resize(ASH owner){
	    super(owner);
	    cName = C_RAD;
	    minValues = 0;
	    maxValues = 2;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_ELE,C_PART,C_SHRINK,C_GROW};
	    cOptions[0] = opt0;
	    cInstructions = "Changes the radii of atoms used for visualization. All the atoms have a display radius as an internal property, but there is also a global scaling factor affecting the actual radii of the atoms drawn in the graphics window. By default, this scaling factor is "+View.displayRadiusMultiplier+" and it can be adjusted with the options "+C_SHRINK+" and "+C_GROW+". The other options of the command operate on the display radii of the atoms.\n"+
		"Options:\n"+
		INSET+"(radius) - By default, the radii of the currently selected atoms are set to the given value.\n"+
		INSET+C_ELE+" (element) (radius) - Changes the radius of a certain element. This setting will be saved, so that any new atoms of that particular type will have this radius.\n"+
		INSET+C_PART+" (index) (radius) - Changes the radius of a given particle.\n"+
		INSET+C_SHRINK+" - Makes all atoms a bit smaller.\n"+
		INSET+C_GROW+" - Makes all atoms a bit bigger.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 3){
		if(args[0].equalsIgnoreCase(C_ELE)){
		    int element = parseI(args[1]);
		    double radius = parseR(args[2]);
		    for(int i=0; i<frames.size(); i++){
			Structure geo = frames.get(i);
			Atom[] ats = geo.getAllAtoms();
			for(int j=0; j<ats.length; j++){
			    if(ats[j].getElement() == element){
				ats[j].setRadius(radius);
			    }
			}
		    }
		    resizeElement(element,radius);
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_PART)){
		    int particle = parseI(args[1]);
		    double radius = parseR(args[2]);
		    Atom[] ats = frames.get(currentFrame).getParticle(particle).getAtoms();
		    for(int j=0; j<ats.length; j++){
			ats[j].setRadius(radius);
		    }
		    master.painter.updateGeo();
		} else  {
		    throw new Exception();
		}
	    } else if(args.length == 1){
		if(args[0].equalsIgnoreCase(C_SHRINK)){
		    View.displayRadiusMultiplier*=0.8; 
		} else if(args[0].equalsIgnoreCase(C_GROW)){
		    View.displayRadiusMultiplier*=1.25; 
		} else {
		    double radius = parseR(args[0]);
		    Structure geo = frames.get(currentFrame);
		    for(int i=0; i<geo.countParticles(); i++){
			if(geo.getParticle(i).isPicked()){
			    Atom[] ats = geo.getParticle(i).getAtoms();
			    for(int j=0; j<ats.length; j++){
				ats[j].setRadius(radius);
			    }
			}
		    }
		}
		master.painter.updateGeo();
	    } else  {
		throw new Exception();
	    }
	}
    }


    private class Recolor extends Command{
	protected Recolor(ASH owner){
	    super(owner);
	    cName = C_COLOR;
	    minValues = 0;
	    maxValues = 0;
	    cArgs = "[ (element) / (index) ]  (red)  (green)  (blue)  [ (alpha) ]";
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_ELE,C_PART,C_BG,C_PICK,C_UNPICK};
	    cOptions[0] = opt0;
	    cInstructions = "Changes the color of atoms. The new color should be given in RGBA format, as 4 integers between 0 and 255. If one omits the alpha parameter, a value of 255 is given (opaque).\n"+
		"Options:\n"+
		INSET+"(red) (green) (blue) [(alpha)] - By default, the color of the currently selected atoms are set to the given value.\n"+
		INSET+C_ELE+" (element) (red) (green) (blue) [(alpha)] - Changes the color of a certain element. This setting will be saved, so that any new atoms of that particular type will have this radius.\n"+
		INSET+C_PART+" (index) (red) (green) (blue) [(alpha)] - Changes the color of a given particle.\n"+
		INSET+C_BG+" (red) (green) (blue) [(alpha)] - Changes the color of the background.\n"+
		INSET+C_PICK+" (red) (green) (blue) [(alpha)] - Changes the color of the outline of selected particles.\n"+
		INSET+C_UNPICK+" (red) (green) (blue) [(alpha)] - Changes the color of the outline of deselected particles.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args[0].equalsIgnoreCase(C_ELE) ||
	       args[0].equalsIgnoreCase(C_PART) ){
		int cr = parseI(args[2]);
		int cg = parseI(args[3]);
		int cb = parseI(args[4]);
		int ca = 255;
		if(args.length == 6){
		    ca = parseI(args[5]);
		}
		if(args[0].equalsIgnoreCase(C_ELE)){
		    int element = parseI(args[1]);
		    recolorElement(element,cr,cg,cb,ca);
		    for(int i=0; i<frames.size(); i++){
			frames.get(i).recolorElements(element);
		    }
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_PART)){
		    int particle = parseI(args[1]);
		    frames.get(currentFrame).recolorParticle(particle,cr,cg,cb,ca);
		    master.painter.updateGeo();
		} else {
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_BG) ||
		      args[0].equalsIgnoreCase(C_PICK) ||
		      args[0].equalsIgnoreCase(C_UNPICK) ){
		int cr = parseI(args[1]);
		int cg = parseI(args[2]);
		int cb = parseI(args[3]);
		int ca = 255;
		if(args.length == 5){
		    ca = parseI(args[4]);
		}
		if(args[0].equalsIgnoreCase(C_BG)){
		    recolorBackground(cr,cg,cb,ca);
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_PICK)){
		    recolorPick(cr,cg,cb,ca);
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_UNPICK)){
		    recolorUnpick(cr,cg,cb,ca);
		    master.painter.updateGeo();
		}
	    } else if(args.length == 4 || args.length == 3){
		int cr = parseI(args[0]);
		int cg = parseI(args[1]);
		int cb = parseI(args[2]);
		int ca = 255;
		if(args.length == 4){
		    ca = parseI(args[3]);
		}
		Structure geo = frames.get(currentFrame);
		for(int i=0; i<geo.countParticles(); i++){
		    if(geo.getParticle(i).isPicked()){
			geo.recolorParticle(i,cr,cg,cb,ca);
		    }
		}
		master.painter.updateGeo();
	    } else {
		throw new Exception();
	    }
	}
    }



    private class Invert extends Command{
	protected Invert(ASH owner){
	    super(owner);
	    cName = C_INV;
	    minValues = 1;
	    maxValues = 6;
	    cOptions = new String[1][];
	    String[] opt0 = {C_X,C_Y,C_Z,C_PLANE};
	    cOptions[0] = opt0;
	    cInstructions = "Mirrors structures with respect to a given plane. Possible periodic boundaries are applied automatically. The internal structures of clusters are also mirrored. Note that the arguments are given in Cartesian coordinates, not with respect to the supercell vectors.\n"+
		"Options:\n"+
		INSET+C_X+" (x) - Mirrors the x-coordinates of all particles with respect to the plane passing through the point [x, 0, 0].\n"+
		INSET+C_Y+" (y) - Mirrors the y-coordinates of all particles with respect to the plane passing through the point [0, y, 0].\n"+
		INSET+C_Z+" (z) - Mirrors the z-coordinates of all particles with respect to the plane passing through the point [0, 0, z].\n"+
		INSET+C_PLANE+" (vx) (vy) (vz) (px) (py) (pz) - Mirrors the coordinates of all particles with respect to the plane normal to the vector [vx, vy, vz] passing through the point [px, py, pz].";
	}
	protected void execute(String[] args)
	    throws Exception{

	    int delta = 1;
	    Vector dv = new Vector(1,0,0);
	    Vector cp = new Vector(0,0,0);

	    if(args[0].equalsIgnoreCase(C_X)){
		dv = new Vector(1,0,0);
		cp = new Vector(parseR(args[1]),0,0);
		delta++;
	    } else if(args[0].equalsIgnoreCase(C_Y)){
		dv = new Vector(0,1,0);
		cp = new Vector(0,parseR(args[1]),0);
		delta++;
	    } else if(args[0].equalsIgnoreCase(C_Z)){
		dv = new Vector(0,0,1);
		cp = new Vector(0,0,parseR(args[1]));
		delta++;
	    } else if(args[0].equalsIgnoreCase(C_PLANE)){
		delta += findVector(args,delta,dv);
		delta += findVector(args,delta,cp);
	    } else {
		throw new Exception();
	    }
	    if(args.length != delta){
		throw new Exception();
	    }
	    rememberStructure();
	    Structure geo = frames.get(currentFrame);
	    geo.invertByPlane(dv,cp);
	    geo.forcePeriodicBounds();
	    geo.calculateCenter();
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();

	}
    }


    private class Show extends Command{
	protected Show(ASH owner){
	    super(owner);
	    cName = C_SHOW;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_AXIS,C_CELL,C_FRAME,C_DIR};
	    cOptions[0] = opt0;
	    cInstructions = "Displays additional information. By default, the currently active particles are made visible (if some were hidden).\n"+
		"Options:\n"+
		INSET+C_AXIS+" - The basis vectors of the Cartesian coordinate system are drawn in the visualization window as red (x), green (y) and blue (z) lines, meeting in the origin.\n"+
		INSET+C_CELL+" - The basis vectors of the supercell are drawn in the visualization window as red (x), green (y) and blue (z) lines, meeting in the origin of the Cartesian coordinate system.\n"+
		INSET+C_FRAME+" - The number of current frame and total number of frames is displayed in the visualization window.\n"+
		INSET+C_DIR+" - The current working directory is displayed in the prompt.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 0){
		hidePicked(false);
	    } else if(args.length == 1){
		if(args[0].equalsIgnoreCase(C_AXIS)){
		    master.painter.setShowAxis(true);
		} else if(args[0].equalsIgnoreCase(C_CELL)){
		    master.painter.setShowCell(true);
		} else if(args[0].equalsIgnoreCase(C_FRAME)){
		    master.painter.setShowFrame(true);
		} else if(args[0].equalsIgnoreCase(C_DIR)){
		    showDir = true;
		} else {
		    throw new Exception();
		}
		master.painter.updateGeo();
	    } else {
		throw new Exception();
	    }
	}
    }


    private class Hide extends Command{
	protected Hide(ASH owner){
	    super(owner);
	    cName = C_HIDE;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_AXIS,C_CELL,C_FRAME,C_DIR};
	    cOptions[0] = opt0;
	    cInstructions = "Hides excess information. By default, the currently active particles will be hidden.\n"+
		"Options:\n"+
		INSET+C_AXIS+" - The basis vectors of the Cartesian coordinate system are not drawn in the visualization window.\n"+
		INSET+C_CELL+" - The basis vectors of the supercell are not drawn in the visualization window.\n"+
		INSET+C_FRAME+" - The number of current frame and total number of frames is not displayed in the visualization window.\n"+
		INSET+C_DIR+" - The current working directory is not displayed in the prompt.";;
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 0){
		hidePicked(true);
	    } else if(args.length == 1){
		if(args[0].equalsIgnoreCase(C_AXIS)){
		    master.painter.setShowAxis(false);
		} else if(args[0].equalsIgnoreCase(C_CELL)){
		    master.painter.setShowCell(false);
		} else if(args[0].equalsIgnoreCase(C_FRAME)){
		    master.painter.setShowFrame(false);
		} else if(args[0].equalsIgnoreCase(C_DIR)){
		    showDir = false;
		} else {
		    throw new Exception();
		}
		master.painter.updateGeo();
	    } else {
		throw new Exception();
	    }
	}
    }


    private class Reposition extends Command{
	protected Reposition(ASH owner){
	    super(owner);
	    cName = C_VIEW;
	    minValues = 0;
	    maxValues = 3;
	    cOptions = new String[1][];
	    String[] opt0 = {C_RESET,C_ZOOM,C_ANGLE,C_POINT,C_PERSP,C_ISOM,C_LENS};
	    cOptions[0] = opt0;
	    cInstructions = "Changes the viewpoint. Note that you can also manipulate the view by dragging with the mouse (rotate) and pressing the arrow keys (left/right: rotate, up/down: zoom) while the visualization window has focus. The parameters defining the current viewpoint are always stored in predefined variables such as viewx, viewy, viewz, viewzoom, etc. For a list, enter the command 'list value'.\n"+
		"Options:\n"+
		INSET+C_RESET+" - Resets the viewpoint to a default: The viewpoint is moved above (in the z-direction of) the center of the system with the 'up' direction of the view set in the y-direction. The distance from the center of the system is determined according to the total number of particles.\n"+
		INSET+C_ZOOM+" (zoom) - Sets the zoom level to the given value. A larger value means a closer zoom.\n"+
		INSET+C_ANGLE+" (phi) (theta) [(distance)] - Defines a new viewpoint in spherical coordinates. Phi is the angle in the xy-plane with respect to the x-axis. Theta is the angle with respect to the z-axis.\n"+
		INSET+C_POINT+" (x) (y) (z) - Defines a new viewpoint in Cartesian coordinates.\n"+
		INSET+C_PERSP+" - A perspetive projection is applied.\n"+
		INSET+C_ISOM+" - An isometric projection is applied.\n"+
		INSET+C_LENS+" - A lenticular projection is applied.";
	}
	protected void execute(String[] args)
	    throws Exception{
	    
	    ViewPoint view = master.painter.getViewPoint();

	    int delta = 1;
	    // If we are running a script, the center of the structure
	    // is not automatically updated.
	    // We need it here, though, so we force an update.
	    if(runningScript){
		runningScript = false;
		frames.get(currentFrame).calculateCenter();
		runningScript = true;
	    }

	    if(args[0].equalsIgnoreCase(C_RESET)){
		master.painter.resetViewPoint();
		if(args.length != delta){
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_PERSP)){
		changeProjectionType(ViewPoint.PERSPECTIVE);
		if(args.length != delta){
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_ISOM)){
		changeProjectionType(ViewPoint.ISOMETRIC);
		if(args.length != delta){
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_LENS)){
		changeProjectionType(ViewPoint.LENS);
		if(args.length != delta){
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_ZOOM)){
		Vector dir = view.getDirection();
		double newzoom = parseR(args[1]);
		delta ++;
		if(newzoom <= 0.0){
		    throw new Exception();
		}
		if(args.length != delta){
		    throw new Exception();
		}
		dir = dir.unit().times(newzoom);
		view.setDirection(dir);
		master.painter.updateGeo(view);		
	    } else if(args[0].equalsIgnoreCase(C_ANGLE)){
		double phi = parseR(args[1]);
		double theta = parseR(args[2]);
		delta += 2;
		Vector center = frames.get(currentFrame).getCenter();
		double dist = 100.0;
		if(args.length == delta){
		    dist = center.minus(view.getPosition()).norm();
		} else {
		    dist = parseR(args[delta]);
		    delta ++;
		}
		if(args.length != delta){
		    throw new Exception();
		}
		Vector newpoint = new Vector(dist*Math.cos(phi)*Math.sin(theta),
					     dist*Math.sin(phi)*Math.sin(theta),
					     dist*Math.cos(theta));
		newpoint = newpoint.plus(center);
		view.setPosition(newpoint);
		view.aimAtCenter(frames.get(currentFrame));
		view.setUp(new Vector(-Math.cos(phi)*Math.cos(theta),
				      -Math.sin(phi)*Math.cos(theta),
				      Math.sin(theta)));
		master.painter.updateGeo(view);
	    } else if(args[0].equalsIgnoreCase(C_POINT)){
		Vector newpoint = new Vector(3);
		delta += findVector(args,delta,newpoint);
		if(args.length != delta){
		    throw new Exception();
		}
		view.setPosition(newpoint);
		view.setUp(new Vector(0,0,1));
		view.aimAtCenter(frames.get(currentFrame));
		master.painter.updateGeo(view);
	    } else {
		    throw new Exception();
	    }
	}
    }


    private class Interpolate extends Command{
	protected Interpolate(ASH owner){
	    super(owner);
	    cName = C_INTERP;
	    minValues = 0;
	    maxValues = 0;
	    cArgs = " [ [ (initial) ]  (final) ]  (frames)";
	    cInstructions = "Interpolates between two structures by linearly shifting atoms and rotating clusters. The command can only be used between two similar structures, that is, for structures that have the same number of particles of each type with the same indices.\n"+
		"Options:\n"+
		INSET+"[[(initial)] (final)] (frames) - If only one argument is given, it specifies the number of interpolated frames to be created. The interpolation is done by uniform steps. For example, a command of '3' frames will generate frames where the coordinates are shifted by 0.25, 0.5 and 0.75 from the initial to the final configuration. ( v = v_0 + a*(v_1-v_0) ) By default, the frames used for interpolation are the current (initial) and the next frame (final). These can be specified by additional arguments, however.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 1){
		if(frames.size()-1 > currentFrame){
		    interpolate(currentFrame,currentFrame+1,parseI(args[0]));
		} else {
		    throw new Exception();
		}
	    } else if(args.length == 2){		
		if(frames.size() > parseI(args[0])-1 &&
		   parseI(args[0]) > 0){
		    interpolate(currentFrame,parseI(args[0])-1,parseI(args[1]));
		} else {
		    throw new Exception();
		}
	    } else if(args.length == 3){		
		if(frames.size() > parseI(args[0])-1 &&
		   frames.size() > parseI(args[1])-1  &&
		   parseI(args[0]) > 0 &&
		   parseI(args[1]) > 0){
		    interpolate(parseI(args[0])-1,parseI(args[1])-1,parseI(args[2]));
		} else {
		    throw new Exception();
		}
	    } else {
		throw new Exception();
	    }
	    master.painter.updateGeo();
	}
    }



    private class Reindex extends Command{
	protected Reindex(ASH owner){
	    super(owner);
	    cName = C_REIND;
	    minValues = 0;
	    maxValues = 0;
	    cArgs = "(index)";
	    cInstructions = "Reorders the indices of particles according to another master structure, whose index must be given. The routine searches for similar particles close to each other in the two structures. If it finds that a particle from one structure is at a distance less than a certain match radius to a similar particle in the other structure, but they have different indices, the particle in the currently active frame will be allocated the index of the particle in the master frame. This is repeated with an expanding matching radius for the remaining particles until all have received a new index.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 1){
		int source = parseI(args[0])-1;
		if(frames.size() > source){
		    rememberStructure();
		    reIndex(source);
		} else {
		    throw new Exception();
		}
	    } else {
		throw new Exception();
	    }
	}
    }


    private class FrameSwitch extends Command{
	protected FrameSwitch(ASH owner){
	    super(owner);
	    cName = C_FRAME;
	    minValues = 0;
	    maxValues = 2;
	    cOptions = new String[1][];
	    String[] opt0 = {C_PREV,C_NEXT,C_FIRST,C_LAST,C_JUMP,C_NEW,C_RM,C_MOVE,C_RENAME};
	    cOptions[0] = opt0;
	    cInstructions = "Manipulates frames. Note that you can also browse frames using the 'n' (next) and 'p' (previous) keys when the visualization window is active.\n"+
		"Options:\n"+
		INSET+C_PREV+" - Switch to the previous frame.\n"+
		INSET+C_NEXT+" - Switch to the next frame.\n"+
		INSET+C_FIRST+" - Switch to the first frame.\n"+
		INSET+C_LAST+" - Switch to the last frame.\n"+
		INSET+C_JUMP+" (index) - Switch to the specified frame.\n"+
		INSET+C_NEW+" - Create a new frame after the current one.\n"+
		INSET+C_RM+" [(i1) [(i2)]] - For no arguments, deletes the current frame. For one argument, deletes the specified frame. For two arguments, deletes all frames in the range i1, i1+1, ... , i2.\n"+
		INSET+C_MOVE+" (index) - Moves the current frame to the given index position.\n"+
		INSET+C_RENAME+" (name) - Renames the frame. By default, the frames are named according to filenames.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 1){
		if(args[0].equalsIgnoreCase(C_PREV)){
		    changeFrame(currentFrame-1);
		    printMessage("frame "+(currentFrame+1));	    
		    master.painter.aimAtCenter();
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_NEXT)){
		    changeFrame(currentFrame+1);
		    printMessage("frame "+(currentFrame+1));
		    master.painter.aimAtCenter();
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_FIRST)){
		    changeFrame(0);
		    printMessage("frame "+(currentFrame+1));
		    master.painter.aimAtCenter();
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_LAST)){
		    changeFrame(frames.size()-1);
		    printMessage("frame "+(currentFrame+1));
		    master.painter.aimAtCenter();
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_NEW)){
		    Atom[] atoms = new Atom[0];
		    frames.add(currentFrame+1,new Structure(atoms));
		    changeFrame(currentFrame+1);
		    printMessage("frame "+(currentFrame+1));	    
		    master.painter.aimAtCenter();
		    master.painter.updateGeo();
		} else if(args[0].equalsIgnoreCase(C_RM)){
		    printMessage("deleting frame "+(currentFrame+1));
		    deleteFrame(currentFrame);
		    master.painter.updateGeo();	
		} else {
		    throw new Exception();
		}
	    } else if(args.length == 2){
		if(args[0].equalsIgnoreCase(C_JUMP)){
		    int jump = parseI(args[1])-1;
		    changeFrame(jump);
		    printMessage("frame "+(currentFrame+1));
		    master.painter.aimAtCenter();
		    master.painter.updateGeo();		    
		} else if(args[0].equalsIgnoreCase(C_MOVE)){
		    int newInd = parseI(args[1])-1;
		    frames.add(newInd,frames.remove(currentFrame));
		    changeFrame(newInd);
		    printMessage("frame "+(currentFrame+1));
		    master.painter.aimAtCenter();
		    master.painter.updateGeo();			
		} else if(args[0].equalsIgnoreCase(C_RM)){
		    int frameno = parseI(args[1])-1;
		    if(frameno >= 0 && frameno < frames.size()){
			printMessage("deleting frame "+(frameno+1));
			deleteFrame(frameno);
		    }	
		    master.painter.updateGeo();	
		} else if(args[0].equalsIgnoreCase(C_RENAME)){
		    Structure geo = frames.get(currentFrame);
		    geo.setName(args[1]);
		    master.painter.updateGeo();	
		} else {
		    throw new Exception();
		}
	    } else if(args.length == 3){
		if(args[0].equalsIgnoreCase(C_RM)){
		    int firstfr = parseI(args[1])-1;
		    int lastfr = parseI(args[2]);
		    int initframes = frames.size();
		    for(int i=firstfr; i<lastfr; i++){
			if(firstfr >= 0 && firstfr < frames.size() && i < initframes){
			    printMessage("deleting frame "+(i+1));
			    deleteFrame(firstfr);
			    if(currentFrame > firstfr){
				currentFrame--;
			    }
			} else { // a negative first frame needs to be increased
			    firstfr++;
			}
		    }
		    master.painter.updateGeo();	
		} else {
		    throw new Exception();
		}
	    } else {
		throw new Exception();
	    }
	}
    }




    private class Write extends Command{
	protected Write(ASH owner){
	    super(owner);
	    cName = C_WRITE;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[2][];
	    String[] opt0 = {C_NOOPTION,C_ALL};
	    cOptions[0] = opt0;
	    String[] opt1 = {C_POSCAR,C_POSCAR4,C_XYZ,C_PNG};
	    cOptions[1] = opt1;
	    cArgs = "(filename)  [ (type1) (type2) ... ]";
	    cInstructions = "Writes data to a file. This may mean writing the coordinates of the geometry in a text file or rendering an image of the current view. For data files, the poscar and xyz formats are currently implemented. Note that when the names of elements are written, if the name of the element contains the character '"+FileHandler.ELEM_SEP+"', it and anything following will be ignored when writing (e.g., 'Mg', 'Mg_2', and 'Mg_frozen' are all written as 'Mg'). This is done to allow the user to define groups of atoms which correspond to the same element but are handled separately in ASH. If you want the names of the groups to be different in the output as well, use some other delimiter (e.g., Mg2 or Mg.2). \n"+
		"Options:\n"+
		INSET+C_ALL+" - Writes the geometries of all frames in either one file, one after another (xyz), or each in its own file (poscar). By default only the geometry of the current frame is written.\n"+
		INSET+INSET+C_POSCAR+" (filename) [(type1) (type2) ...] - Writes the geometry in POSCAR format used by VASP 5. By default, elements are listed in order of atomic numbers. By giving a list of element symbols, e.g., Mg O Si, the user can force the order of the elements in the written file.\n"+
		INSET+INSET+C_POSCAR4+" (filename) [(type1) (type2) ...] - Writes the geometry in POSCAR format used by VASP 4. By default, elements are listed in order of atomic numbers. By giving a list of element symbols, e.g., Mg O Si, the user can force the order of the elements in the written file.\n"+
		INSET+INSET+C_XYZ+" (filename) - Writes the geometry in xyz format.\n"+
		INSET+INSET+C_PNG+" (filename) - Writes the geometry in png format as an image.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    boolean writeAll = false;
	    int delta = 0;
	    if(args[0].equalsIgnoreCase(C_ALL)){
		writeAll = true;
		delta = 1;
	    }
	    if(args.length >= 2+delta){
		if(args[delta].equalsIgnoreCase(C_POSCAR)){		    
		    String filename = args[1+delta];
		    String fileout = filename;
		    try{
			int firstone = currentFrame;
			int lastone = currentFrame+1;
			if(writeAll){
			    firstone = 0;
			    lastone = frames.size();
			}
			for(int i=firstone; i<lastone; i++){
			    int namelength = filename.length();
			    if(writeAll){
				fileout = filename+(i+1);
			    } else {
				fileout = filename;
			    }
			    if(!filename.substring(namelength-3,namelength).equalsIgnoreCase("CAR")){
				fileout = fileout+".POSCAR";
			    }
			    String[] order = new String[args.length-2-delta];
			    for(int k=2+delta; k<args.length; k++){
				order[k-2-delta] = args[k];
			    }
			    writeGeometry(fileout,frames.get(i),FileHandler.F_POSCAR,order);
			    printMessage("wrote "+fileout);
			}
		    } catch(Exception error){
			printMessage("could not write to "+fileout,true);
			//error.printStackTrace();
		    }
		} else if(args[delta].equalsIgnoreCase(C_POSCAR4)){		    
		    String filename = args[1+delta];
		    String fileout = filename;
		    try{
			int firstone = currentFrame;
			int lastone = currentFrame+1;
			if(writeAll){
			    firstone = 0;
			    lastone = frames.size();
			}
			for(int i=firstone; i<lastone; i++){
			    int namelength = filename.length();
			    if(writeAll){
				fileout = filename+(i+1);
			    } else {
				fileout = filename;
			    }
			    if(!filename.substring(namelength-3,namelength).equalsIgnoreCase("CAR")){
				fileout = fileout+".POSCAR";
			    }
			    String[] order = new String[args.length-2-delta];
			    for(int k=2+delta; k<args.length; k++){
				order[k-2-delta] = args[k];
			    }
			    writeGeometry(fileout,frames.get(i),FileHandler.F_POSCAR4,order);
			    printMessage("wrote "+fileout);
			}
		    } catch(Exception error){
			printMessage("could not write to "+fileout,true);
			//error.printStackTrace();
		    }
		} else if(args[delta].equalsIgnoreCase(C_PNG)){
		    String filename = args[1+delta];
		    String fileout = filename;
		    try{
			int firstone = currentFrame;
			int lastone = currentFrame+1;
			int iniframe = currentFrame;
			if(writeAll){
			    firstone = 0;
			    lastone = frames.size();
			}
			for(int i=firstone; i<lastone; i++){
			    int namelength = filename.length();
			    if(writeAll){
				fileout = filename+(i+1);
			    } else {
				fileout = filename;
			    }
			    if(!filename.substring(namelength-4,namelength).equalsIgnoreCase(".png")){
				fileout = fileout+".png";
			    }
			    Structure newGeo = frames.get(i);
			    master.painter.setGeometry(newGeo);
			    master.painter.updateGeo();
			    writeGeometry(fileout,newGeo,FileHandler.F_PNG,null);
			    printMessage("wrote "+fileout);
			}
			Structure newGeo = frames.get(iniframe);
			master.painter.setGeometry(newGeo);
			master.painter.updateGeo();
			
		    } catch(Exception error){
			printMessage("could not write to "+fileout,true);
			//error.printStackTrace();
		    }
		} else if(args[delta].equalsIgnoreCase(C_XYZ)){	    
		    String filename = args[1+delta];
		    int namelength = filename.length();
		    String fileout = filename;
		    if(!filename.substring(namelength-4,namelength).equalsIgnoreCase(".xyz")){
			fileout = filename+".xyz";
		    }
		    try{
			if(writeAll){
			    Structure[] geos = new Structure[frames.size()];
			    for(int i=0; i<geos.length; i++){
				geos[i] = frames.get(i);
			    }
			    writeGeometries(fileout,geos,FileHandler.F_XYZ);
			} else {
			    writeGeometry(fileout,frames.get(currentFrame),FileHandler.F_XYZ,null);
			}
			printMessage("wrote "+fileout);
		    } catch(Exception error){
			printMessage("could not write to "+fileout,true);
			//error.printStackTrace();
		    }
		} else {
		    throw new Exception();
		}
	    } else {
		throw new Exception();
	    }

	}
    }


    private class Open extends Command{
	protected Open(ASH owner){
	    super(owner);
	    cName = C_OPEN;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_XYZ,C_POSCAR,C_SCRIPT,C_DATA};
	    cArgs = "(filename) [ (width) (variable) ]";
	    cOptions[0] = opt0;
	    cInstructions = "Reads data from a file.\n"+
		"Options:\n"+
		INSET+C_XYZ+" (filename) - Reads geometry data from an XYZ format file.\n"+
		INSET+C_POSCAR+" (filename) - Reads geometry data from a POSCAR format file.\n"+
		INSET+C_SCRIPT+" (filename) - Reads a command script file.\n"+
		INSET+C_DATA+" (filename) (width) (variable) - Reads a data matrix and stores it in a matrix variable of the given width. The values should be separated by spaces and newlines. If non numerical values are met, NaN values are stored in the corresponding cells of the matrix.";
	    
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 2){
		if(args[0].equalsIgnoreCase(C_XYZ)){
		    String filename = args[1];
		    try{ 
			Structure[] geo = readGeometry(filename,FileHandler.F_XYZ);
			for(int i=geo.length-1; i>=0; i--){
			    addFrame(geo[i]);
			}
		    } catch(Exception error){
			printMessage("could not read from "+filename,true);
			//error.printStackTrace();
		    }
		    if(frames.get(currentFrame).countParticles() == 0){
			deleteFrame(currentFrame);
		    } else {
			changeFrame(currentFrame+1);
		    }
		    printMessage("frame "+(currentFrame+1));
		    if(currentFrame == 0){
			master.painter.resetViewPoint();
		    } else {
			master.painter.aimAtCenter();
			master.painter.updateGeo();
		    }
		} else if(args[0].equalsIgnoreCase(C_POSCAR)){
		    String filename = args[1];
		    try{ 
			Structure[] geo = readGeometry(filename,FileHandler.F_POSCAR);
			for(int i=geo.length-1; i>=0; i--){
			    addFrame(geo[i]);
			}
		    } catch(Exception error){
			printMessage("could not read from "+filename,true);
			//error.printStackTrace();
		    }
		    if(frames.get(currentFrame).countParticles() == 0){
			deleteFrame(currentFrame);
		    } else {
			changeFrame(currentFrame+1);
		    }
		    printMessage("frame "+(currentFrame+1));
		    if(currentFrame == 0){
			master.painter.resetViewPoint();
		    } else {
			master.painter.aimAtCenter();
			master.painter.updateGeo();
		    }
		} else if(args[0].equalsIgnoreCase(C_SCRIPT)){		    
		    String filename = args[1]; 
		    try{
			executeScript(filename);
		    } catch(Exception error){
			printMessage("could not read from "+filename,true);
			//error.printStackTrace();
		    }
		} else {
		    throw new Exception();
		}
	    } else if(args.length == 4){

		if(args[0].equalsIgnoreCase(C_DATA)){
		    String filename = args[1];
		    int width = parseI(args[2]);
		    String varname = args[3];
		    try{
			double[][] data = new FileHandler().readData(filename,width);
			Variable dummy = new Variable(varname,data);
			defineVariable(varname,dummy);
		    } catch(Exception error){
			printMessage("could not read from "+filename,true);
			//error.printStackTrace();
		    }
		}

	    } else {
		throw new Exception();
	    }
	}
    }



    private class Undo extends Command{
	protected Undo(ASH owner){
	    super(owner);
	    cName = C_UNDO;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Undos the previous operations. Note that the undo command only considers structural changes, not, e.g., changes in the graphical representation. The undo buffer exists only for the current frame and it is cleared every time a different frame is accessed.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    Structure memory = recallStructure();
	    frames.set(currentFrame,memory);
	    frames.get(currentFrame).calculateCenter();
	    master.painter.setGeometry(memory);
	    master.painter.aimAtCenter();
	    master.painter.updateGeo();
	}
    }



    private class Define extends Command{
	protected Define(ASH owner){
	    super(owner);
	    cName = C_SET;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[2][];
	    String[] opt0 = {C_NOOPTION,C_ECHO,C_RM};
	    cOptions[0] = opt0;
	    String[] opt1 = {C_NOOPTION,C_STRING};
	    cOptions[1] = opt1;
	    cInstructions = "Defines a variable or a function. For a variable, the definition can be a real number or an expression that can be evaluated immediately, or a string, in which case the option "+C_STRING+" is needed. In the case of an expression, the result of the evaluation is stored in the variable. When defining a string, evaluation of expressions can be included using dollar signs ($), as explained in the manual of "+C_VALUE+". A function on the other hand is an expression that is not evaluated immediately, but which can take arguments that affect its value. To define a function, include strings '#1', #2, ..., #9 in the definition to specify the arguments of the function. (The number of arguments is limited to 9, but a single argument can be a vector or a matrix of arbitrary size.) The syntax for calling the variables and functions are explained in the manual of '"+C_VALUE+"'.\nExample: > define var 1+2 (Stores 3.0 in the variable 'var'.)\n         > define f (var+#1)/#2 (If 'var' had the value 3.0, this defines the function f(x,y) = (3.0+x)/y; the definition is invalid if 'var' has not been defined.)\n"+
		"Options:\n"+
		INSET+"(variable) (value) - The first argument is the name of the variable or function, the second the number or expression which is stored.\n"+
		INSET+C_ECHO+" (variable) (value) - Same as the default command, but the value stored is shown.\n"+
		INSET+C_RM+" (variable) - Removes the variable associated to the given name.\n"+
		INSET+INSET+C_STRING+" (variable) (value) - Stores a string in the variable.";
	    cArgs = "(variable)  [ (value) ]";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length > 1){
		if(args[0].equalsIgnoreCase(C_RM)){
		    if(args[1].equalsIgnoreCase(C_STRING)){
			if(args.length > 2){
			    try{
				removeStringVariable(args[2]);
			    } catch(Exception error){
				printMessage(args[2]+" is a reserved keyword",true);
			    }
			} else {
			    throw new Exception();
			}
		    } else {
			try{
			    removeVariable(args[1]);
			    removeStringVariable(args[1]);
			    adder.removeFunction(args[1]);
			} catch(Exception error){
			    printMessage(args[1]+" is a reserved keyword",true);			
			}
		    }
		} else {
		    boolean echo = false;
		    boolean string = false;
		    int delta = 0;
		    if(args[0].equalsIgnoreCase(C_ECHO)){
			echo = true;
			delta++;
		    }
		    if(args[delta].equalsIgnoreCase(C_STRING)){
			string = true;
			delta++;
		    }
		    String varname = args[0+delta];
		    String formula = "";
		    for(int i=1+delta; i<args.length; i++){
			formula += args[i];
		    }
		    double value = 0;
		    
		    if(string){ // string variable
			try{
			    defineStringVariable(varname,parseS(formula));
			    removeVariable(varname);
			    if(echo){
				printMessage(varname+" = '"+getStringVariable(varname)+"'",true);
			    }
			} catch(Exception error){
			    printMessage(varname+" is a reserved keyword",true);			
			}
		    } else if(formula.indexOf("#") == -1){ // no arguments -> variable
			try{
			    Variable dummy = evaluateExpression(formula);
			    //value = parseR(formula);
			    try{
				defineVariable(varname,dummy);
				removeStringVariable(varname);
				if(echo){
				    printMessage(varname+" = "+dummy,true);
				}
			    } catch(Exception error){
				printMessage(varname+" is a reserved keyword",true);			
			    }
			} catch(Exception error){
			    printMessage("invalid definition",true);
			    //error.printStackTrace();
			}
		    } else { // a function
			try{
			    CustomFunction dummy = new CustomFunction(varname,formula,adder);
			    try{
				adder.defineFunction(varname,dummy);
				if(echo){
				    int acount = dummy.getArgumentCount();
				    String[] argStr = new String[acount];
				    System.out.print(varname+":{");				
				    for(int i=0; i<acount; i++){
					argStr[i] = "#"+(i+1);
					System.out.print(argStr[i]);
					if(i<acount-1){
					    System.out.print(",");
					} else {
					    System.out.print("} = ");
					}
				    }
				    printMessage(dummy.getExpression(argStr),true);
				}
			    } catch(Exception error){
				printMessage(varname+" is a reserved function",true);
			    }
			} catch(Exception error){
			    printMessage("invalid function definition",true);
			}
		    }
		}
	    } else {
		throw new Exception();
	    }
	}
    }


    private class Evaluate extends Command{
	protected Evaluate(ASH owner){
	    super(owner);
	    cName = C_VALUE;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Evaluates an expression. The expression can contain real numbers, variables, operators and custom functions. Normal operators such as '+', '-', '*', and '/' work, as well as a set of common functions such as 'sin', 'ln', and 'exp'. Note that functions are defined using a colon (:), as in 'sin:(2*pi)' or exp:-1. For a multi-valued function, the arguments must be wrapped in curly brackets, as in 'col:{mat,1}' Comparison and logic operators such as '=', '<', '>', '&' (and), and '|' (or) are also available. A true statement is given a value of 1, an untrue one 0. Furthermore, one may define matrices and vectors using square brackets, e.g., '[[1,0][0,2]]'. When operating on matrices, most functions operate on the individual matrix elements, for instance 'exp' just takes the exponential of the matrix elements, not the matrix exponential. Multiplication and power operators ('*' and '^') invoke true matrix multiplication if possible, but if dimensions are incompatible (say, you multiply a matrix and a scalar), they also try to do element-wise operations if possible.\n The command also allows evaluation of strings, similarly to the command "+C_PRINT+". In addition, one may wish to include evaluation within command arguments, for instance when scripting. This can be done by encasing a variable or an expression between two dollar signs ($), in which case the string within the signs is evaluated. By default, a double precision value is extracted, but whether a real or an integer value is printed may be requested by adding an 'R' or 'I' immediately after the first $ symbol. (If your expression begins with a variable with a name starting with I or R, always include the format specifier!). For a matrix valued variable, normal evaluation prints the values of all the cells in a one-line format, where the rows and columns are separated by square brackets. When evaluation is invoked by $'s, the integer valued printout just prints the dimensions of the matrix, while the real valued printout prints the values of all the cells in a multi-line format, with no brackets. \nExample: > "+C_VALUE+" 2.0+cos:pi. \n         > "+C_VALUE+" 'pi = $Rpi$, approximately $Ipi$'";
	    cArgs = "(expression)";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length > 0){
		String formula = "";
		for(int i=0; i<args.length; i++){
		    formula += args[i];
		}
		try{ // expression
		    printMessage(formula+" = "+evaluateExpression(formula),true);
		} catch(Exception error){
		    try{ // string
			printMessage(formula+" = '"+getStringVariable(formula)+"'",true);
		    } catch(Exception error2){
			printMessage(formula+" = '"+parseS(formula)+"'",true);
			//printMessage("invalid expression "+formula);
			//error.printStackTrace();
		    }
		}
	    } else {
		throw new Exception();
	    }
	}
    }


    private class Manual extends Command{
	protected Manual(ASH owner){
	    super(owner);
	    cName = C_MAN;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Displays a brief explanation on the usage of the given command.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 1){
		accessManual(args[0]);
	    } else {
		throw new Exception();
	    }
	}
    }


    private class CalcManual extends Command{
	protected CalcManual(ASH owner){
	    super(owner);
	    cName = C_CALCMAN;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = Operator.OPERATORS;
	    cOptions[0] = opt0;
	    cInstructions = "Displays a brief explanation on the usage of the given function or operator.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 1){
		accessCalculatorManual(args[0]);
	    } else {
		throw new Exception();
	    }
	}
    }




    private class MakeAlias extends Command{
	protected MakeAlias(ASH owner){
	    super(owner);
	    cName = C_ALIAS;
	    minValues = 0;
	    maxValues = 0;
	    cInstructions = "Creates an alias for a command. Once an alias is defined, entering it will lead to the original command being executed. If further arguments follow the alias, they are appended to the command. If one wishes to insert arguments inside the aliased command, this can be done by including the strings '#1', '#2' etc. (up to #9) in the alias definition. Upon calling the alias, this will lead to each '#1' being replaced by the first following argument etc. To include a series of commands in a single alias, wrap the full command series in quotes (\" or ', if you need nested quotes, alter between the two). Using a semicolon (;) without quotes will cut the evaluation of the command 'alias' and start immediately executing whatever follows. \nExample: > "+C_ALIAS+" cd \"directory switch #1; print ' *  changed the directory to   *'; directory\" (alias definition) \n         > cd ..  (alias call)";
	    cArgs = "(name)  (command)";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length > 1){
		String alias = args[0];
		String rest = "";
		for(int i=1; i<args.length; i++){
		    rest += args[i]+" ";
		}
		Alias shorthand = null;
		try{
		    shorthand = new Alias(alias,rest);
		    try{
			defineAlias(alias,shorthand);
		    } catch(Exception error){
			printMessage(alias+" is a reserved keyword",true);
		    }
		} catch(Exception error){
		    printMessage("invalid alias definition",true);
		}
	    } else {
		throw new Exception();
	    }
	}
    }


    private class ElementSwitch extends Command{
	protected ElementSwitch(ASH owner){
	    super(owner);
	    cName = C_ELE;
	    minValues = 1;
	    maxValues = 2;
	    cOptions = new String[1][];
	    String[] opt0 = {C_RENAME, C_SWITCH};
	    cOptions[0] = opt0;
	    cInstructions = "Manipulates element data.\n"+
		"Options:\n"+
		INSET+C_RENAME+" (element) (name) - Sets a name for the given element. Note, that you cannot rename indices 1-119, since those are fixed to represent the real elements. Indices 0 and 120 and up can be named freely.\n"+
		INSET+C_SWITCH+" [(old element)] (new element) - If only one argument is given, all the currently active atoms will be changed to the given element. If two arguments are given, the atoms of the specified element (in all frames) will be turned to atoms of the new element.";
	}
	protected void execute(String[] args)
	    throws Exception{
	    
	    if(args[0].equalsIgnoreCase(C_RENAME)){
		if(args.length == 3){
		    //int eleind = parseI(args[1]);
		    int eleind = parseI(args[1]);
		    if(eleind > 0 && eleind < PeriodicTable.UNKNOWN){
			printMessage("Cannot rename element "+eleind+", "+parseElement(eleind).getSymbol()+".",true);
			throw new Exception();
		    } else if(eleind < 0 || eleind >= ASH.elementTable.fill()){
			printMessage("The list of elements only goes up to "+ASH.elementTable.fill()+".",true);
			throw new Exception();
		    }
		    Element ele = parseElement(eleind);
		    String newname = args[2];
		    ele.setSymbol(newname);
		    ele.setName(newname);
		    //ASH.elementTable.substituteElement(eleind,ele);
		    updateElements();
		} else {
		    throw new Exception();
		}
	    } else if(args[0].equalsIgnoreCase(C_SWITCH)){
		if(args.length == 2){
		    Element ele = ASH.elementTable.getElement(args[1]);
		    Structure geo = frames.get(currentFrame);
		    Atom[] all = geo.getAllAtoms();
		    rememberStructure();
		    for(int i=0; i<all.length; i++){
			if(all[i].isPicked()){
			    all[i].setElement(ele);
			}
		    }
		    master.painter.updateGeo();
		} else if(args.length == 3){
		    Element oldele = parseElement(args[1]);
		    int oldind = oldele.getNumber();
		    //int newind = parseI(args[2])%ASH.elementTable.size();
		    //Element ele = ASH.parseElement(newind);
		    //rememberStructure();
		    Element ele = ASH.elementTable.getElement(args[2]);
		    for(int j=0; j<frames.size(); j++){
			Structure geo = frames.get(j);
			Atom[] all = geo.getAllAtoms();
			for(int i=0; i<all.length; i++){
			    if(all[i].getElement() == oldind){
				all[i].setElement(ele);
			    }
			}
		    }
		    master.painter.updateGeo();
		    
		} else {
		    throw new Exception();
		}
	    }
	}
    
    }

    private class MouseSwitch extends Command{
	protected MouseSwitch(ASH owner){
	    super(owner);
	    cName = C_MOUSE;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_INFO,C_PICK};
	    cOptions[0] = opt0;
	    cInstructions = "Toggles response to mouse clicks in the visualization window.\n"+
		"Options:\n"+
		INSET+C_INFO+" - Toggles mouse query mode. When the query mode is on, one can view the coordinates of atoms by clicking on them in the visualization window. If several atoms are on the 'line of sight', all their coordinates will be displayed in order of depth, starting from the one farthest away.\n"+
		INSET+C_PICK+" - Toggles mouse selection mode. When the selection mode is on, one can select and unselect atoms by clicking on them. A sinlge click either selects or unselects the atom that was clicked on, depending on whether it was previously selected or not. A double click does the same for all the particles on the 'line of sight'. A triple click selects all the particles on the line of sight. (It will not unselect any previously selected atoms, like a double click does.";
	}
	protected void execute(String[] args)
	    throws Exception{
	    if(args[0].equalsIgnoreCase(C_INFO)){
		if(GeoPainter.MOUSE_INFO){
		    GeoPainter.MOUSE_INFO = false;
		    printMessage("Mouse query mode is now off.");
		} else {
		    GeoPainter.MOUSE_INFO = true;
		    printMessage("Mouse query mode is now on.");
		}
	    } else if(args[0].equalsIgnoreCase(C_PICK)){
		if(GeoPainter.MOUSE_PICK){
		    GeoPainter.MOUSE_PICK = false;
		    printMessage("Mouse select mode is now off.");
		} else {
		    GeoPainter.MOUSE_PICK = true;
		    printMessage("Mouse select mode is now on.");
		}
	    }

	}
    }


    private class DirectorySwitch extends Command{
	protected DirectorySwitch(ASH owner){
	    super(owner);
	    cName = C_DIR;
	    minValues = 0;
	    maxValues = 1;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_JUMP,C_LIST};
	    cOptions[0] = opt0;
	    cInstructions = "Access to directory operations. By default, the command prints the current working directory. To emulate shell commands, one may define aliases such as 'alias cd directory switch', 'alias ls directory list' and 'alias pwd directory'.\n"+
		"Options:\n"+
		INSET+C_JUMP+" (directory) - Switches to the given directory.\n"+
		INSET+C_LIST+" - Lists the contents of the current directory.";
	}

	protected void execute(String[] args)
	    throws Exception{
	    
	    if(args.length == 0){
		printMessage(FileHandler.CWD.getCanonicalPath(),true);
	    } else if(args.length == 1){
		if(args[0].equalsIgnoreCase(C_LIST)){
		    String[] ls = FileHandler.CWD.list();
		    for(int i = 0; i < ls.length; i++){
			String fileName = ls[i];
			printMessage(fileName,true);
		    }
		}
	    } else if(args.length == 2){
		if(args[0].equalsIgnoreCase(C_JUMP)){
		    File newDir = new File(FileHandler.CWD,args[1]);
		    if(newDir.isDirectory()){
			FileHandler.CWD = newDir;
		    } else {
			newDir = new File(args[1]);
			if(newDir.isDirectory()){
			    FileHandler.CWD = newDir;
			} else {
			    printMessage("No such directory.",true);
			}
		    }
		}
	    }
	}
    }


    private class Scale extends Command{
	protected Scale(ASH owner){
	    super(owner);
	    cName = C_SCALE;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_CELL,C_X,C_Y,C_Z,C_PARTS};
	    cOptions[0] = opt0;
	    cArgs = "(factor)";
	    cInstructions = "Scaling operation. By default, the entire cell is scaled with respect to the origin by the given scaling factor. This includes scaling the supercell vectors, shifting particles, and scaling clusters. However, only active particles are affected.\n"+
		"Options;\n"+
		INSET+C_CELL+" (factor) - Scales the supercell and shifts the particles accordingly, but does not scale the size of clusters. If the given factor is a vector, its components are used for scaling in the directions of the corresponding supercell vectors.\n"+
		INSET+C_X+" (factor) - Scales the supercell and shifts particles only in the direction of the first supercell vector.\n"+
		INSET+C_Y+" (factor) - Scales the supercell and shifts particles only in the direction of the second supercell vector.\n"+
		INSET+C_Z+" (factor) - Scales the supercell and shifts particles only in the direction of the third supercell vector.\n"+
		INSET+C_PARTS+" (factor) - Only the sizes of clusters are scaled, no particles are shifted.";
	}
	protected void execute(String[] args)
	    throws Exception{

	    if(args.length == 1){
		double factor = parseR(args[0]);
		scale(factor,true,3);
	    } else if(args.length == 2){
		if(args[0].equalsIgnoreCase(C_CELL)){
		    try{
			Vector factors = new Vector(3);
			findVector(args,1,factors);
			scale(factors.element(0),false,0);
			scale(factors.element(1),false,1);
			scale(factors.element(2),false,2);
		    } catch(Exception error){
			double factor = parseR(args[1]);
			scale(factor,false,3);
		    }
		} else if(args[0].equalsIgnoreCase(C_X)){
		    double factor = parseR(args[1]);
		    scale(factor,false,0);
		} else if(args[0].equalsIgnoreCase(C_Y)){
		    double factor = parseR(args[1]);
		    scale(factor,false,1);
		} else if(args[0].equalsIgnoreCase(C_Z)){
		    double factor = parseR(args[1]);
		    scale(factor,false,2);
		} else if(args[0].equalsIgnoreCase(C_PARTS)){
		    double factor = parseR(args[1]);
		    scale(factor,true,-1);
		} else {
		    throw new Exception();
		}

	    } else {
		throw new Exception();
	    }
	}
    }


    private class Bend extends Command{
	protected Bend(ASH owner){
	    super(owner);
	    cName = C_BEND;
	    minValues = 4;
	    maxValues = 9;
	    cOptions = new String[1][];
	    String[] opt0 = {C_X,C_Y,C_Z,C_AXIS};
	    cOptions[0] = opt0;
	    cInstructions = "Bends the geometry around an axis. This is done by defining an axis vector, a reference point through which the axis is set, and a 'radius' vector defining the bending radius. (Only the component of the radius vector perpendicular to the bending axis is meningful. If the vector is not perpendicular to the axis, the perpendicular component is automatically extracted. It must not be a zero vector.) If you move by the radius vector from the reference point and take the line parallel to the bending axis, you get a line in space which is not affected by the bending operation. Let us call this the 'contact' line. Now consider the plane passing through the contact line, perpendicular to the plane defined the bending axis and the contact line (perpendicular to the radius vector). Let this be called the 'wrapping' plane. This plane is bent as if wrapping a cylinder defined by the axis and radius, so that the original in-plane distances are the same as the final distances measured along the cylinder surface. Naturally the operation also bends other planes. This is done so that straight lines in the direction of the radius vector are conserved. That is, planes parallel to the wrapping plane but closer to the axis are squeezed (distances in the bend direction are shrunk) and the planes farther from the axis are stretched. The operation only affetcs the currently active particles. Positions and orientations of clusters are affected, but their internal structure is not.\n"+
		"Options:\n"+
		INSET+C_X+" (py) (pz) (ry) (rz) - Bends the system around an axis set in the x direction, [1, 0, 0]. The axis is fixed at [0, py, pz] and the radius vector is defined as [0, ry, rz].\n"+
		INSET+C_Y+" (px) (pz) (rx) (rz) - Bends the system around an axis set in the y direction, [0, 1, 0]. The axis is fixed at [px, 0, pz] and the radius vector is defined as [rx, 0, rz].\n"+
		INSET+C_Z+" (px) (py) (rx) (ry) - Bends the system around an axis set in the z direction, [0, 0, 1]. The axis is fixed at [px, py, 0] and the radius vector is defined as [rx, ry, 0].\n"+
		INSET+C_AXIS+" (ax) (ay) (az) (px) (py) (pz) (rx) (ry) (rz) - Bends the system around an arbitrary axis [ax, ay, az] at [px, py, pz]. The radius vector is defined as [rx, ry, rz]. Only the component perpendicular to the axis is meaningful, and it must not be zero, i.e., the radius vector must not be parallel to the axis.";
	}
	protected void execute(String[] args)
	    throws Exception{
	    int delta = 1;
	    Vector axis = new Vector(3);
	    Vector point = new Vector(3);
	    Vector contact = new Vector(3);
	    if(args[0].equalsIgnoreCase(C_AXIS)){
		delta += findVector(args,delta,axis);
		delta += findVector(args,delta,point);
		delta += findVector(args,delta,contact);
	    } else if(args[0].equalsIgnoreCase(C_X)){
		axis = new Vector(1,0,0);
		Vector temppoint = new Vector(2);
		Vector tempcontact = new Vector(2);
		delta += findVector(args,delta,temppoint);
		delta += findVector(args,delta,tempcontact);
		point = new Vector(0,temppoint.element(0),temppoint.element(1));
		contact = new Vector(0,tempcontact.element(0),tempcontact.element(1));
	    } else if(args[0].equalsIgnoreCase(C_Y)){
		axis = new Vector(0,1,0);
		Vector temppoint = new Vector(2);
		Vector tempcontact = new Vector(2);
		delta += findVector(args,delta,temppoint);
		delta += findVector(args,delta,tempcontact);
		point = new Vector(temppoint.element(0),0,temppoint.element(1));
		contact = new Vector(tempcontact.element(0),0,tempcontact.element(1));
	    } else if(args[0].equalsIgnoreCase(C_Z)){
		axis = new Vector(0,0,1);
		Vector temppoint = new Vector(2);
		Vector tempcontact = new Vector(2);
		delta += findVector(args,delta,temppoint);
		delta += findVector(args,delta,tempcontact);
		point = new Vector(temppoint.element(0),temppoint.element(1),0);
		contact = new Vector(tempcontact.element(0),tempcontact.element(1),0);
	    } else {
		throw new Exception();
	    }
	    if(args.length != delta){
		throw new Exception();
	    }
	    bend(axis,point,contact);
	}
    }


    private class Wait extends Command{
	protected Wait(ASH owner){
	    super(owner);
	    cName = C_WAIT;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[0][0];
	    cArgs = "(milliseconds)";
	    cInstructions = "Stops the execution of the program for the given time. This can be used for animating scripts.";
	}
	protected void execute(String[] args)
	    throws Exception{
	    master.painter.updateGeo();
	    Thread.sleep(parseI(args[0]));
	}
    }



    /*
    private class Null extends Command{
	protected Null(ASH owner){
	    super(owner);
	    cName = C_EXIT;
	    minValues = 0;
	    maxValues = 0;
	    cOptions = new String[1][];
	    String[] opt0 = {C_NOOPTION,C_CELL};
	    cOptions[0] = opt0;
	    cInstructions = "template";
	}
	protected void execute(String[] args)
	    throws Exception{

	    throw new Exception();
	}
    }
    */


}

