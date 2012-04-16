import java.awt.Color;

public class Atom implements Particle{

    private String name;
    private int element;
    private int index;
    private double mass;
    private Vector coordinates;
    private Vector velocity;
    private Vector force;
    private Color color;
    private double radius;
    private boolean picked;
    private boolean hidden;

    private boolean[] free;

    public Atom(Vector coord){
	this.index = 0;
	this.coordinates = coord;
	this.name = "";
	this.element = 0;
	this.mass = 10.0;
	this.velocity = new Vector(3);
	this.force = new Vector(3);
	this.free = new boolean[3];
	for(int i=0; i<3; i++){
	    free[i] = true;
	}
	this.radius = 1.0;
	this.color = View.white;
	this.picked = ASH.defaultPick;
	this.hidden = false;
    }


    public Atom copy(){
	Atom newatom = new Atom(this.coordinates.copy());
	newatom.index = this.index;
	newatom.mass = this.mass;
	newatom.element = this.element;
	newatom.color = new Color(this.color.getRed(),
				  this.color.getGreen(),
				  this.color.getBlue(),
				  this.color.getAlpha());
	newatom.velocity = this.velocity.copy();
	newatom.force = this.force.copy();
	newatom.free = new boolean[3];
	for(int i=0; i<3; i++){
	    newatom.free[i] = this.free[i];
	}
	newatom.radius = this.radius;
	newatom.picked = this.picked;
	newatom.hidden = this.hidden;
	newatom.name = this.name;
	return newatom;
    }

    public void pick(){
	this.picked = true;
    }

    public void pick(boolean yesno){
	this.picked = yesno;
    }

    public void hide(boolean yesno){
	this.hidden = yesno;
    }

    public void unpick(){
	this.picked = false;
    }
    
    public boolean isPicked(){
	return this.picked;
    }
    
    public boolean isHidden(){
	return this.hidden;
    }

    public void setName(String newname){
	this.name = newname;
    }

    public String getName(){
	return this.name;
    }

    public void setElement(int ele){
	this.element = ele;
    }

    public void setElement(Element ele){	
	this.element = ele.getNumber();
	this.color = ele.getColor();
	this.mass = ele.getMass();
	this.radius = ele.getRadius();
	this.name = ele.getSymbol();
    }

    public int getElement(){
	return this.element;
    }

    public void setIndex(int ind){
	this.index = ind;
    }

    public int getIndex(){
	return this.index;
    }

    public void setMass(double m){
	this.mass = m;
    }

    public double getMass(){
	return this.mass;
    }

    public void setCoordinates(Vector cc){
	this.coordinates = cc;
    }

    public void shiftCoordinates(Vector dc){
	this.coordinates = this.coordinates.plus(dc);
    }

    public Vector getCoordinates(){
	return this.coordinates;
    }

    public void setVelocity(Vector cc){
	this.velocity = cc;
    }

    public Vector getVelocity(){
	return this.velocity;
    }

    public void setForce(Vector cc){
	this.force = cc;
    }

    public Vector getForce(){
	return this.force;
    }

    public void setFrozenCoordinates(boolean[] fr){
	for(int i=0; i<3; i++){
	    if(fr.length > i){
		free[i] = fr[i];
	    } else {
		free[i] = false;
	    }
	}
    }

    public boolean[] getFrozenCoordinates(){
	return this.free;
    }

    public void setColor(Color newc){
	this.color = newc;
    }

    public Color getColor(){
	return this.color;
    }

    public void setRadius(double newr){
	this.radius = newr;
    }

    public double getRadius(){
	return this.radius;
    }

    public void rotate(Quaternion q){} // required by the interface "Particle"
    public Quaternion getOrientation(){
	return new Quaternion(1.0,0,0,0);
    }

    public boolean isAtomic(){
	return true;
    }

    public int countAtoms(){
	return 1;
    }

    public int countParticles(){
	return 1;
    }

    public boolean isSimilarTo(Particle another){
	if(this.countAtoms() != another.countAtoms()){
	    //System.out.println("* csize "+another.countAtoms()+" "+this.countAtoms());
	    return false;
	}
	Atom other = (Atom)another;
	if(other.getElement() != this.element){
	    //System.out.println("* element "+other.getElement()+" "+this.element);
	    return false;
	}
	return true;

    }

    public Particle getParticle(int index){
	return this;
    }

    public Atom[] getAtoms(){
	Atom[] list = {this};
	return list;
    }

    public Particle[] getParticles(){
	Particle[] list = {this};
	return list;
    }

    public String toString(){
	String info = "atom            ";
	info += "   ("+FileHandler.formattedDouble(coordinates.element(0),12,6)+
	    ", "+FileHandler.formattedDouble(coordinates.element(1),12,6)+
	    ", "+FileHandler.formattedDouble(coordinates.element(2),12,6)+")";
	for(int i=0; i<3; i++){
	    if(free[i]){
		info += " t";
	    } else {
		info += " f";
	    }
	}
	info += "  "+FileHandler.formattedInt(this.element,3)+" "+
	    ASH.elementTable.getElement(this.element).getSymbol();
	return info;
    }

}