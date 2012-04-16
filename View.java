import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;

public class View{

    public static final Color darkBlue = new Color(0,0,128,255);
    public static final Color darkRed = new Color(128,0,0,255);
    public static final Color darkGreen = new Color(0,128,0,255);
    public static final Color blue = new Color(0,0,255,255);
    public static final Color red = new Color(255,0,0,255);
    public static final Color green = new Color(0,255,0,255);
    public static final Color gold = new Color(255,193,37,255);
    public static final Color yellow = new Color(255,255,0,255);
    public static final Color steel = new Color(35,107,142,255);
    public static final Color transBlue = new Color(0,0,128,128);
    public static final Color transRed = new Color(128,0,0,128);
    public static final Color transGreen = new Color(0,128,0,128);
    public static final Color transLightBlue = new Color(0,0,255,128);
    public static final Color transLightRed = new Color(255,0,0,128);
    public static final Color transLightGreen = new Color(0,255,0,128);
    public static final Color transYellow = new Color(255,255,0,128);
    public static final Color black = new Color(0,0,0,255);
    public static final Color darkGray = new Color(63,63,63,255);
    public static final Color gray = new Color(127,127,127,255);
    public static final Color lightGray = new Color(191,191,191,255);
    public static final Color white = new Color(255,255,255,255);
    public static final Color transWhite = new Color(255,255,255,128);
    public static final Color transBlack = new Color(0,0,0,128);
    public static final Color transGold = new Color(245,195,50,150);
    public static Color bg = new Color(128,128,128,255);
    public static Color picked = new Color(0,0,0,255);
    public static Color unpicked = new Color(50,50,50,100);
    public static Color hiddenPicked = new Color(0,0,0,42);
    public static Color hiddenUnpicked = new Color(50,50,50,6);
    public static Color transparent = new Color(0,0,0,0);
    public static int STROKE_WIDTH = 4;

    public static double displayRadiusMultiplier = 0.5;

    private Projection[] projections;
    private Vector originProjection;
    private Vector[] cellProjections;
    private Vector[] axisProjections;
    private int visibleAtoms;
    private boolean isReady;
    private int width;
    private int height;

    public View(Structure geo, ViewPoint look, int width, int height){
	isReady = false;
	this.width = width;
	this.height = height;
	try{
	    calculateProjections(geo,look);
	} catch(Exception error){
	    axisProjections = null;
	    cellProjections = null;
	    projections = null;
	    visibleAtoms = 0;
	}
    }

    public boolean isReady(){
	return this.isReady;
    }

    public void setReady(boolean yesno){
	this.isReady = yesno;
    }

    public Shape getAtomicShape(int index){
	return projections[index].getAtomicShape(width,height);
    }

    public boolean isVisible(int index){
	return projections[index].isVisible();
    }

    public Line2D getCellAxisLine(int index){
	if(originProjection.element(0) > 0 && cellProjections[index].element(0) > 0){
	    double originx = originProjection.element(1)*100+width/2;
	    double originy = -originProjection.element(2)*100+height/2;
	    double vecx = cellProjections[index].element(1)*100+width/2;
	    double vecy = -cellProjections[index].element(2)*100+height/2;
	    return new Line2D.Double(originx,originy,vecx,vecy);
	} else {
	    return new Line2D.Double(-100,-100,-100,-100);
	}
    }

    public Line2D getUnitAxisLine(int index){
	if(originProjection.element(0) > 0 && cellProjections[index].element(0) > 0){
	    double originx = originProjection.element(1)*100+width/2;
	    double originy = -originProjection.element(2)*100+height/2;
	    double vecx = axisProjections[index].element(1)*100+width/2;
	    double vecy = -axisProjections[index].element(2)*100+height/2;
	    return new Line2D.Double(originx,originy,vecx,vecy);
	} else {
	    return new Line2D.Double(-100,-100,-100,-100);
	}
    }

    public void updateProjections(Structure geo, ViewPoint look)
	throws Exception{
	Atom[] atoms = geo.getAllAtoms();
	visibleAtoms = 0;
	if(atoms.length != projections.length){
	    throw new Exception();
	}
	for(int i=0; i<atoms.length; i++){
	    Vector pVec = projection(atoms[i],geo,look);
	    projections[i].setX(pVec.element(1));
	    projections[i].setY(pVec.element(2));
	    projections[i].setDepth(pVec.element(3));
	    projections[i].setRadius(pVec.element(0));
	    if(atoms[i].isHidden()){
		projections[i].setColor(transparent);
		if(atoms[i].isPicked()){
		    projections[i].setLineColor(hiddenPicked);
		} else {
		    projections[i].setLineColor(hiddenUnpicked);
		}
	    } else {
		projections[i].setColor(atoms[i].getColor());
		if(atoms[i].isPicked()){
		    projections[i].setLineColor(picked);
		} else {
		    projections[i].setLineColor(unpicked);
		}
	    }
	    if(projections[i].isVisible()){
		visibleAtoms ++;
	    }
	}
	Arrays.sort(projections);
	originProjection = projection(new Vector(0,0,0),geo,look);
	for(int i=0; i<3; i++){
	    cellProjections[i] = projection(geo.getCell()[i],geo,look);
	}
	axisProjections[0] = projection(new Vector(1,0,0),geo,look);
	axisProjections[1] = projection(new Vector(0,1,0),geo,look);
	axisProjections[2] = projection(new Vector(0,0,1),geo,look);
    }

    private void calculateProjections(Structure geo, ViewPoint look){
	Atom[] atoms = geo.getAllAtoms();
	projections = new Projection[atoms.length];
	visibleAtoms = 0;
	for(int i=0; i<atoms.length; i++){
	    Vector pVec = projection(atoms[i],geo,look);
	    projections[i] = new Projection(i);
	    projections[i].setX(pVec.element(1));
	    projections[i].setY(pVec.element(2));
	    projections[i].setDepth(pVec.element(3));
	    projections[i].setRadius(pVec.element(0));
	    projections[i].setColor(atoms[i].getColor());
	    if(atoms[i].isHidden()){
		projections[i].setColor(transparent);
		if(atoms[i].isPicked()){
		    projections[i].setLineColor(hiddenPicked);
		} else {
		    projections[i].setLineColor(hiddenUnpicked);
		}
	    } else {
		projections[i].setColor(atoms[i].getColor());
		if(atoms[i].isPicked()){
		    projections[i].setLineColor(picked);
		} else {
		    projections[i].setLineColor(unpicked);
		}
	    }
	    if(projections[i].isVisible()){
		visibleAtoms ++;
	    }
	}
	Arrays.sort(projections);
	cellProjections = new Vector[3];
	axisProjections = new Vector[3];
	originProjection = projection(new Vector(0,0,0),geo,look);
	for(int i=0; i<3; i++){
	    cellProjections[i] = projection(geo.getCell()[i],geo,look);
	}
	axisProjections[0] = projection(new Vector(1,0,0),geo,look);
	axisProjections[1] = projection(new Vector(0,1,0),geo,look);
	axisProjections[2] = projection(new Vector(0,0,1),geo,look);
    }

    public static Vector projection(Atom atom, Structure geo, ViewPoint look){
	Vector proj = projection(atom.getCoordinates(),geo,look);
	proj.setElement(0,proj.element(0)*atom.getRadius());
	return proj;
    }

    public static Vector projection(Vector coordinates, Structure geo, ViewPoint look){
	// perspective projection
	Vector shifted = coordinates.minus(look.getPosition());
	double[] projCoord = new double[4];
	projCoord[3] = shifted.norm();
	if(look.getProjectionType() == ViewPoint.PERSPECTIVE){
	    double totalDist = shifted.projectionOnVector(look.getDirection()).norm();
	    if(shifted.dot(look.getDirection()) > 0){
		projCoord[0] = look.getZoom()/totalDist;
	    } else {
		projCoord[0] = -1;
	    }
	    shifted = shifted.times(projCoord[0]).minus(look.getDirection());
	    projCoord[1] = shifted.dot(look.getSide());
	    projCoord[2] = shifted.dot(look.getUp());
	    return new Vector(projCoord);
	} else if(look.getProjectionType() == ViewPoint.ISOMETRIC){	    
	    double totalDist = (geo.getCenter().minus(look.getPosition())).norm();
	    projCoord[3] = shifted.dot(look.getDirection());
	    if(shifted.dot(look.getDirection()) > 0){
		projCoord[0] = look.getZoom()/totalDist;
	    } else {
		projCoord[0] = -1;
	    }
	    shifted = shifted.times(projCoord[0]).projectionOnPlane(look.getDirection());
	    projCoord[1] = shifted.dot(look.getSide());
	    projCoord[2] = shifted.dot(look.getUp());
	    return new Vector(projCoord);
	} else if(look.getProjectionType() == ViewPoint.LENS){
	    double totalDist = shifted.norm();
	    if(shifted.dot(look.getDirection()) > 0){
		projCoord[0] = look.getZoom()/totalDist;
	    } else {
		projCoord[0] = -1;
	    }
	    shifted = shifted.unit();
	    Vector unitDir = look.getDirection().unit();
	    Vector planeProj = shifted.projectionOnPlane(unitDir).unit();
	    double theta = Math.acos(shifted.dot(unitDir));
	    double phiS = 0.0;
	    double phiU = 0.0;
	    if(theta != 0){
		phiS = (planeProj.dot(look.getSide()));
		phiU = (planeProj.dot(look.getUp()));
	    }
	    projCoord[1] = phiS*theta*look.getZoom();
	    projCoord[2] = phiU*theta*look.getZoom();
	    return new Vector(projCoord);

	}
	return null;
    }

    public Projection[] getProjections(){
	return this.projections;
    }

    public Vector[] getCellProjections(){
	return this.cellProjections;
    }

    public int getNumberOfVisibleAtoms(){
	return visibleAtoms;
    }

    public int[] coversScreenPoint(int x, int y){
	boolean[] covered = new boolean[this.projections.length];
	int hits = 0;
	for(int i=0; i<covered.length; i++){
	    covered[i] = projections[i].coversScreenPoint(x,y,width,height);
	    if(covered[i]){
		hits++;
	    }
	}
	int[] list = new int[hits];
	int index = 0;
	for(int i=0; i<covered.length; i++){
	    if(covered[i]){
		list[index] = projections[i].getIndex();
		index++;
	    }
	}
	//Arrays.sort(list);
	return list;
    }

}