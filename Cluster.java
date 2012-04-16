public class Cluster implements Particle{

    //private Atom[] atoms;
    private Particle[] parts;
    private Vector[] neutralPositions;
    private Quaternion rotation;
    private Vector center;
    private boolean picked;
    private boolean hidden;

    public Cluster(Particle[] stuff){
	parts = stuff;
	calculateCenter();
	pick(true);
	//hide(false);
	neutralPositions = new Vector[parts.length];
	for(int i=0; i<parts.length; i++){
	    neutralPositions[i] = parts[i].getCoordinates().minus(center);
	}
	rotation = new Quaternion(1.0,0.0,0.0,0.0);
    }

    public Cluster copy(){
	Particle[] stuff = new Particle[this.countParticles()];
	for(int i=0; i<stuff.length; i++){
	    stuff[i] = this.parts[i].copy();
	}
	Cluster newC = new Cluster(stuff);
	newC.neutralPositions = new Vector[this.neutralPositions.length];
	for(int i=0; i<this.neutralPositions.length; i++){
	    newC.neutralPositions[i] = this.neutralPositions[i].copy();
	}
	newC.rotation = this.rotation.copy();
	newC.picked = this.picked;
	newC.hidden = this.hidden;
	return newC;
    }

    public boolean isSimilarTo(Particle another){
	if(this.countParticles() != another.countParticles()){
	    return false;
	}
	for(int i=0; i<parts.length; i++){
	    if(!parts[i].isSimilarTo(another.getParticle(i))){
		return false;
	    }
	}
	return true;
    }

    public Vector getCoordinates(){
	return this.center;
    }

    public void scale(double factor){
	for(int i=0; i<parts.length; i++){
	    if(parts[i].isAtomic()){
		neutralPositions[i] = neutralPositions[i].times(factor);
	    } else {
		((Cluster)parts[i]).scale(factor);
	    }
	}
	updateAtomPositions();
    }

    private void updateAtomPositions(){
	for(int i=0; i<parts.length; i++){
	    if(parts[i].isAtomic()){
		Vector newc = neutralPositions[i].rotate(rotation).plus(center);
		parts[i].setCoordinates(newc);
	    } else {
		Vector newc = neutralPositions[i].rotate(rotation).plus(center);
		parts[i].setCoordinates(newc);
		((Cluster)parts[i]).updateAtomPositions();
	    }
	}
    }

    public void setCoordinates(Vector newc){
	this.center = newc;
	updateAtomPositions();
    }

    public void shiftCoordinates(Vector dc){
	this.center = this.center.plus(dc);
	updateAtomPositions();
    }

    public void rotate(Quaternion rot){
	this.rotation = rot.times(this.rotation);
	for(int i=0; i<parts.length; i++){
	    parts[i].rotate(rot);
	}
	updateAtomPositions();
    }

    private void calculateCenter(){
	double[] cc = new double[3];
	for(int i=0; i<3; i++){
	    cc[i] = 0.0;
	}
	Atom[] atoms = getAtoms();
	for(int j=0; j<atoms.length; j++){
	    for(int i=0; i<3; i++){
		cc[i] += atoms[j].getCoordinates().element(i);
	    }
	}
	for(int i=0; i<3; i++){
	    cc[i] /= atoms.length;
	}
	center = new Vector(cc);
    }

    public double getMass(){
	double m = 0.0;
	Atom[] atoms = getAtoms();
	for(int i=0; i<atoms.length; i++){
	    m += atoms[i].getMass();
	}
	return m;
    }

    public Atom[] getAtoms(){
	int nat = countAtoms();
	Atom[] atoms = new Atom[nat];
	int j=0;
	for(int i=0; i<parts.length; i++){
	    if(parts[i].isAtomic()){
		atoms[j] = (Atom)parts[i];
		j++;
	    } else {
		Atom[] list = parts[i].getAtoms();
		for(int k=0; k<list.length; k++){
		    atoms[j] = list[k];
		    j++;
		}
	    }
	}
	return atoms;
    }

    public Particle[] getParticles(){
	return this.parts;
    }

    public Particle getParticle(int index){
	return this.parts[index];
    }

    public int countAtoms(){
	int nat = 0;
	for(int i=0; i<parts.length; i++){	    
	    nat += parts[i].countAtoms();
	}
	return nat;
    }

    public int countParticles(){
	return parts.length;
    }

    public Quaternion getOrientation(){
	return this.rotation;
    }

    public void pick(boolean yesno){
	this.picked = yesno;
	for(int i=0; i<parts.length; i++){
	    parts[i].pick(yesno);
	}
    }

    public void hide(boolean yesno){
	this.hidden = yesno;
	for(int i=0; i<parts.length; i++){
	    parts[i].hide(yesno);
	}
    }

    public boolean isPicked(){
	return this.picked;
    }

    public boolean isHidden(){
	return this.hidden;
    }

    public boolean isAtomic(){
	return false;
    }

    public String toString(){
	String info = "cluster ";
	info += "of "+FileHandler.formattedInt(countAtoms(),4)+" ";
	info += "   ("+FileHandler.formattedDouble(center.element(0),12,6)+
	    ", "+FileHandler.formattedDouble(center.element(1),12,6)+
	    ", "+FileHandler.formattedDouble(center.element(2),12,6)+")";
	return info;
    }

    public void invertByPlane(Vector normal, Vector point){
	Vector axis = this.rotation.rotationAxis();
	double angle = this.rotation.rotationAngle();
	// Internal reflection
	Vector internalNormal = normal.rotate(new Quaternion(axis,-angle));
	for(int i=0; i<parts.length; i++){
	    neutralPositions[i] = neutralPositions[i].inversionByPlane(internalNormal,new Vector(0,0,0));
	    if(!parts[i].isAtomic()){
		((Cluster)parts[i]).invertByPlane(normal,point);
	    }
	}
	updateAtomPositions();
   }


}