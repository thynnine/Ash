import java.util.ArrayList;
import java.awt.*;

public class Structure{

    //private ArrayList<Atom> atoms; 
    //private ArrayList<Cluster> clusters; 
    private ArrayList<Particle> particles;   
    private Vector center;
    private Vector[] cell;    
    private boolean[] pbc;
    private double[][] inverseCell;
    private String name;

    public Structure(Atom[] stuff, Cluster[] blobs){
	this.particles = new ArrayList<Particle>();
	for(int i=0; i<stuff.length; i++){
	    this.particles.add(stuff[i]);
	}
	for(int i=0; i<blobs.length; i++){
	    this.particles.add(blobs[i]);
	}
	center = new Vector(3);
	calculateCenter();
	cell = new Vector[3];
	cell[0] = new Vector(1.0,0.0,0.0);
	cell[1] = new Vector(0.0,1.0,0.0);
	cell[2] = new Vector(0.0,0.0,1.0);
	try{
	    this.inverseCell = inverseCell();
	} catch(Exception error){}
	pbc = new boolean[3];
	for(int i=0; i<3; i++){
	    pbc[i] = false;
	}
	this.name = "";
    }

    public Structure(Particle[] blobs){
	this.particles = new ArrayList<Particle>();
	for(int i=0; i<blobs.length; i++){
	    this.particles.add(blobs[i]);
	}
	calculateCenter();
	cell = new Vector[3];
	cell[0] = new Vector(1.0,0.0,0.0);
	cell[1] = new Vector(0.0,1.0,0.0);
	cell[2] = new Vector(0.0,0.0,1.0);
	try{
	    this.inverseCell = inverseCell();
	} catch(Exception error){}
	pbc = new boolean[3];
	for(int i=0; i<3; i++){
	    pbc[i] = false;
	}
	this.name = "";
    }
        
    public Structure copy(){
	Structure copy = new Structure(new Particle[0]);
	copy.particles = new ArrayList<Particle>();
	for(int i=0; i<this.particles.size(); i++){
	    copy.particles.add(this.particles.get(i).copy());
	}
	copy.calculateCenter();
	copy.cell = new Vector[3];
	copy.cell[0] = this.cell[0].copy();
	copy.cell[1] = this.cell[1].copy();
	copy.cell[2] = this.cell[2].copy();
	try{
	    copy.inverseCell = copy.inverseCell();
	} catch(Exception error){}
	copy.pbc = new boolean[3];
	for(int i=0; i<3; i++){
	    copy.pbc[i] = this.pbc[i];
	}
	copy.name = this.name;
	return copy;
    }

    public void setName(String newname){
	this.name = newname;
    }

    public String getName(){
	return this.name;
    }

    public boolean setCell(Vector[] axes, boolean[] bounds)
	throws Exception{

	Vector[] cellCopy = new Vector[3];
	for(int i=0; i<3; i++){
	    cellCopy[i] = cell[i].copy();
	}
	this.cell = axes;
	this.pbc = bounds;
	try{
	    this.inverseCell = inverseCell();
	    return true;
	} catch(Exception error){
	    this.cell = cellCopy;
	    return false;
	    /**
	    System.out.println("linearly dependent cell vectors - replacing by a cubic unit cell");
	    cell = new Vector[3];
	    cell[0] = new Vector(1.0,0.0,0.0);
	    cell[1] = new Vector(0.0,1.0,0.0);
	    cell[2] = new Vector(0.0,0.0,1.0);
	    pbc = new boolean[3];
	    for(int i=0; i<3; i++){
		pbc[i] = false;
	    }
	    try{
		this.inverseCell = inverseCell();
	    } catch(Exception error2){}
	    */
	}
    }

    public Vector[] getCell(){
	return this.cell;
    }
    
    public Vector[] getCellCopy(){
	Vector[] cellCopy = new Vector[3];
	for(int i=0; i<3; i++){
	    cellCopy[i] = cell[i].copy();
	}
	return cellCopy;
    }

    public boolean[] getBoundaryConditions(){
	return this.pbc;
    }
    
    public void calculateCenter(){
	if(!ASH.runningScript){
	    double[] cc = new double[3];
	    int atomcount = 0;
	    for(int i=0; i<3; i++){
		cc[i] = 0.0;
	    }
	    for(int j=0; j<particles.size(); j++){
		int components = particles.get(j).countAtoms();
		for(int i=0; i<3; i++){
		    cc[i] += particles.get(j).getCoordinates().element(i)*components;
		}
		atomcount += components;
	    }
	    if(atomcount > 0){
		for(int i=0; i<3; i++){
		    cc[i] /= atomcount;
		}
	    }
	    center = new Vector(cc);
	}
    }

    public Vector getCenter(){
	if(this.center == null){
	    calculateCenter();
	}
	return this.center;
    }
    
    public ArrayList<Particle> getParticles(){
	return this.particles;
    }
    
    public Particle getParticle(int index){
	return this.particles.get(index);
    }

    public Atom[] getAllAtoms(){
	Atom[] ats = new Atom[countAllAtoms()];

	int counter = 0;
	for(int i=0; i<countParticles(); i++){
	    Atom[] bits = this.particles.get(i).getAtoms();
	    for(int j=0; j<bits.length; j++){
		ats[counter] = bits[j];
		counter++;
	    }
	}

	return ats;
    }

    public int countAllAtoms(){
	int ats = 0;
	for(int i=0; i<countParticles(); i++){
	    ats += particles.get(i).countAtoms();
	}
	return ats;
    }

    public void addParticles(Particle[] newA){
	for(int i=0; i<newA.length; i++){
	    particles.add(newA[i]);
	}
	calculateCenter();
    }

    public void addParticle(Particle newA){
	particles.add(newA);
	calculateCenter();
    }

    public void addParticle(int index, Particle newA){
	particles.add(index,newA);
	calculateCenter();
    }

    public void addParticle(Particle newA, boolean update){
	particles.add(newA);
	if(update){
	    calculateCenter();
	}
    }

    public void addParticle(int index, Particle newA, boolean update){
	particles.add(index, newA);
	if(update){
	    calculateCenter();
	}
    }

    public void removeParticle(int index)
	throws Exception{
	particles.remove(index);
    }    

    public void forcePeriodicBounds(){
	try{
	    if(pbc[0] || pbc[1] || pbc[2]){
		for(int i=0; i<particles.size(); i++){
		    particles.get(i).setCoordinates(accountForPeriodicity(particles.get(i).getCoordinates()));
		}
	    }
	} catch(Exception error){
	    System.out.println("invalid supercell");
	}
    }

    public Vector getDirectCoordinates(Vector cart){
	try{
	    Vector direct = cart.matMul(inverseCell);
	    return direct;
	} catch(Exception error){
	    return cart;
	}
    }

    public Vector getCartesianCoordinates(Vector direct){
	Vector cart = cell[0].times(direct.element(0)).
	    plus(cell[1].times(direct.element(1))).
	    plus(cell[2].times(direct.element(2)));
	return cart;
    }

    public Vector accountForPeriodicity(Vector start){
	try{
	    Vector direct = start.matMul(inverseCell);
	    for(int j=0; j<3; j++){
		if(pbc[j]){
		    direct.setElement(j,direct.element(j)-Math.floor(direct.element(j)));
		}
	    }

	    Vector moved = cell[0].times(direct.element(0)).
		plus(cell[1].times(direct.element(1))).
		plus(cell[2].times(direct.element(2)));	    
	    return moved;
	} catch(Exception error){
	    return start;
	}
    }
    
    public double[][] getInverseCell(){
	return this.inverseCell;
    }

    public void updateInverseCell()
	throws Exception{
	try{
	    this.inverseCell = inverseCell();
	} catch(Exception error){}
    }

    public double[][] inverseCell()
	throws Exception{
	double[][] a = new double[3][3];
	for(int i=0; i<3; i++){
	    for(int j=0; j<3; j++){
		a[i][j] = cell[j].element(i);
	    }
	}
	double[][] inv = new double[3][3];
	double det = 
	    a[0][0]*(a[2][2]*a[1][1]-a[2][1]*a[1][2])-
	    a[1][0]*(a[2][2]*a[0][1]-a[2][1]*a[0][2])+
	    a[2][0]*(a[1][2]*a[0][1]+a[1][1]*a[0][2]);
	if(det == 0.0){
	    throw new Exception();
	}
	inv[0][0] = (a[2][2]*a[1][1]-a[2][1]*a[1][2])/det;
	inv[0][1] = -(a[2][2]*a[0][1]-a[2][1]*a[0][2])/det;
	inv[0][2] = (a[1][2]*a[0][1]-a[1][1]*a[0][2])/det;
	inv[1][0] = -(a[2][2]*a[1][0]-a[2][0]*a[1][2])/det;
	inv[1][1] = (a[2][2]*a[0][0]-a[2][0]*a[0][2])/det;
	inv[1][2] = -(a[1][2]*a[0][0]-a[1][0]*a[0][2])/det;
	inv[2][0] = (a[2][1]*a[1][0]-a[2][0]*a[1][1])/det;
	inv[2][1] = -(a[2][1]*a[0][0]-a[2][0]*a[0][1])/det;
	inv[2][2] = (a[1][1]*a[0][0]-a[1][0]*a[0][1])/det;
	return inv;
    }

    public int countParticles(){
	return this.particles.size();
    }

    public int countPickedParticles(){
	int count = 0;
	for(int i=0; i<particles.size(); i++){
	    if(particles.get(i).isPicked()){
		count++;
	    }
	}
	return count;
    }

    public void reIndex(Structure master){
	if(master.isSimilarTo(this)){
	    int[] indexSwap = new int[particles.size()];
	    boolean[] taken = new boolean[particles.size()];
	    double[] closedist = new double[particles.size()];
	    ArrayList<Particle> newlist = new ArrayList<Particle>();

	    for(int i=0; i<particles.size(); i++){
		indexSwap[i] = i;
		taken[i] = !particles.get(i).isPicked();
		if(taken[i]){
		    closedist[i] = 0.0;
		} else {
		    closedist[i] = 999999999.0;
		}
	    }
	    int shells = 5;
	    for(int k=0; k<shells; k++){
		for(int i=0; i<particles.size(); i++){
		    if(closedist[i] > k*0.5){
			double closest = 99999999999999.0;
			for(int j=0; j<particles.size(); j++){
			    if(!taken[j] && master.getParticle(i).isSimilarTo(particles.get(j))){
				double dist = this.distance(master.getParticle(i).getCoordinates(),particles.get(j).getCoordinates());
				if(dist < closest){
				    if(k == shells-1 || dist <= (k+1)*0.5){
					indexSwap[i] = j;
					closest = dist;
				    }
				}
			    }
			}
			if(closest < 99999999.0){
			    taken[indexSwap[i]] = true;
			    closedist[i] = closest;
			}
		    }
		}
	    }

	    for(int i=0; i<particles.size(); i++){
		newlist.add(particles.get(indexSwap[i]));
	    }
	    particles = newlist;

	} else {
	    System.out.println("cannot reindex different structures");
	}
    }

    public boolean isSimilarTo(Structure another){
	if(countParticles() != another.countParticles()){
	    //System.out.println("number");
	    return false;
	}
	for(int i=0; i<countParticles(); i++){
	    if(!particles.get(i).isSimilarTo(another.getParticle(i))){
		//System.out.println("particle diff "+i);
		return false;
	    }
	}
	return true;
    }

    public static Structure interpolate(Structure a, Structure b, double ratio)
	throws Exception{
	if(a.isSimilarTo(b)){
	    Particle[] newParticles = new Particle[a.countParticles()];
	    for(int i=0; i<a.countParticles(); i++){
		Particle pa = a.getParticle(i);
		Particle pb = b.getParticle(i);
	
		Vector shifter = (pb.getCoordinates().minus(pa.getCoordinates())).times(ratio);
		newParticles[i] = pa.copy();
		newParticles[i].shiftCoordinates(shifter);

		if(!pa.isAtomic()){
		    // Interpolate clusters using rotate
		    Quaternion start = pa.getOrientation();
		    Quaternion finish = pb.getOrientation();
		    // d*a = b -> d = b*a^-1
		    Quaternion delta = finish.times(start.inverse());
		    Vector axis = delta.rotationAxis();
		    double angle = delta.rotationAngle();
		    Quaternion newRot = new Quaternion(axis,angle*ratio);
		    //Quaternion newOrientation = newrot.times(start);
		    newParticles[i].rotate(newRot);
		}
	    }

	    Structure interpolated = new Structure(newParticles);
	    interpolated.setCell(a.getCell(),a.getBoundaryConditions());
	    interpolated.updateInverseCell();
	    interpolated.forcePeriodicBounds();
	    return interpolated;
	} else {
	    throw new Exception();
	}
	
    }

    public double distance(Vector a, Vector b){
	double dist = 0.0;
	if(pbc[0] || pbc[1] || pbc[2]){
	    double trial = 0.0;
	    Vector separation = a.minus(b);
	    dist = separation.norm();
	    for(int x=-1; x<=1; x++){
		if(x == 0 || pbc[0]){
		    for(int y=-1; y<=1; y++){
			if(y == 0 || pbc[1]){
			    for(int z=-1; z<=1; z++){
				if(z == 0 || pbc[2]){
				    trial = separation
					.plus(cell[0].times(x))
					.plus(cell[1].times(y))
					.plus(cell[2].times(z)).norm();
				    if(trial < dist){
					dist = trial;
				    }
				}
			    }
			}
		    }
		}
	    }
	} else {
	    dist = a.minus(b).norm();
	}
	return dist;
    }

    public int[] findElements(){
	Atom[] ats = getAllAtoms();
	int[] found = new int[PeriodicTable.SPACE];
	int different = 0;
	for(int i=0; i<found.length; i++){
	    found[i] = 0;
	}
	for(int i=0; i<ats.length; i++){
	    found[ats[i].getElement()]++;
	    if(found[ats[i].getElement()] == 1){
		different++;
	    }
	}
	int index = 0;
	int[] list = new int[different];
	for(int i=0; i<found.length; i++){
	    if(found[i] > 0){
		list[index] = i;
		index++;
	    }
	}
	return list;
    }

    public int[] mapAtomsToParticles(){
	Atom[] all = getAllAtoms();
	int[] mapper = new int[all.length];
	int index = 0;
	for(int i=0; i<countParticles(); i++){
	    int csize = particles.get(i).countAtoms();
	    for(int j=0; j<csize; j++){
		mapper[index] = i;
		index++;
	    }
	}
	return mapper;
    }

    public int[] countElements(){
	Atom[] ats = getAllAtoms();
	int[] found = new int[PeriodicTable.SPACE];
	int different = 0;
	for(int i=0; i<found.length; i++){
	    found[i] = 0;
	}
	for(int i=0; i<ats.length; i++){
	    found[ats[i].getElement()]++;
	    if(found[ats[i].getElement()] == 1){
		different++;
	    }
	}
	int[] list = new int[different];
	int index = 0;
	for(int i=0; i<found.length; i++){
	    if(found[i] > 0){
		list[index] = found[i];
		index++;
	    }
	}
	
	return list;
    }

    public int copyPart(Structure clip, boolean overwrite,boolean alsoCell){
	if(overwrite){
	    this.particles.clear();
	}
	if(alsoCell){
	    Vector[] newcell = new Vector[3];
	    boolean[] newpbc = new boolean[3];
	    for(int i=0; i<3; i++){
		newcell[i] = clip.getCell()[i].copy();
		newpbc[i] = clip.getBoundaryConditions()[i];
	    }
	    try{
		this.setCell(newcell,newpbc);
		this.updateInverseCell();
	    } catch(Exception error){}
	}
	int copied = 0;
	for(int i=0; i<clip.countParticles(); i++){
	    if(clip.getParticle(i).isPicked()){
		this.addParticle(clip.getParticle(i).copy(),false);
		copied++;
	    }
	}
	return copied;

    }

    public void updateElements(){
	Atom[] all = this.getAllAtoms();
	for(int i=0; i< all.length; i++){
	    int elem = all[i].getElement();
	    all[i].setElement(ASH.elementTable.getElement(elem%ASH.elementTable.size()));
	}	
    }

    public void recolorElements(int elem){
	Atom[] all = this.getAllAtoms();
	for(int i=0; i<all.length; i++){
	    if(all[i].getElement() == elem){
		all[i].setColor(ASH.elementTable.getElement(elem%ASH.elementTable.size()).getColor());
	    }
	}
    }

    public void recolorParticle(int index, int r, int g, int b, int a){
	Particle part = this.particles.get(index);
	Atom[] ats = part.getAtoms();
	for(int i=0; i<ats.length; i++){
	    ats[i].setColor(new Color(r,g,b,a));
	}
    }

    public void invertByPlane(Vector normal, Vector point){
	for(int i=0; i<particles.size(); i++){
	    Particle particle = particles.get(i);
	    if(particle.isPicked()){
		Vector coords = particles.get(i).getCoordinates();
		particle.setCoordinates(coords.inversionByPlane(normal,point));
		if(!particle.isAtomic()){
		    Cluster cluster = (Cluster)particle;
		    cluster.invertByPlane(normal,point);
		}
	    }
	}
    }

    public void removeDuplicates(double tolerance){
	for(int i=0; i<particles.size()-1; i++){
	    Particle first = particles.get(i);
	    for(int j=i+1; j<particles.size(); j++){
		Particle second = particles.get(j);
		if(first.isSimilarTo(second)){
		    if(this.distance(first.getCoordinates(),second.getCoordinates()) < tolerance){
			particles.remove(j);
			j--;
		    }
		}
	    }
	}
    }

}