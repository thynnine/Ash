import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Projection implements Comparable{

    private int index;
    private double x;
    private double y;
    private double r;
    private Color color;
    private Color lineColor;
    private double depth;

    public Projection(int index){
	this.index = index;
	x = -100;
	y = -100;
	r = 0;
	color = View.white;
	lineColor = View.black;
	depth = -100;
    }

    public int getIndex(){
	return this.index;
    }

    public void setX(double x){
	this.x = x;
    }

    public void setY(double y){
	this.y = y;
    }

    public double getX(){
	return this.x;
    }

    public double getY(){
	return this.y;
    }

    public void setRadius(double r){
	this.r = r;
    }

    public double getRadius(){
	return this.r;
    }

    public void setColor(Color newc){
	this.color = newc;
    }

    public Color getColor(){
	return this.color;
    }

    public void setLineColor(Color newc){
	this.lineColor = newc;
    }

    public Color getLineColor(){
	return this.lineColor;
    }

    public void setDepth(double d){
	this.depth = d;
    }

    public double getDepth(){
	return this.depth;
    }

    public int compareTo(Object proj){
	Projection p = (Projection) proj;
	if(this.depth < p.depth){
	    return 1;
	} else if(this.depth > p.depth){
	    return -1;
	} else {
	    if(this.index < p.index){
		return 1;
	    } else if(this.index > p.index){
		return -1;
	    }
	    return 0;
	}
    }

    public boolean isVisible(){
	if(depth < 0){
	    return false;
	}
	return true;
    }

    public Shape getAtomicShape(int width, int height){
	double r2 = r*100*View.displayRadiusMultiplier;
	double x2 = x*100+width/2;
	// the y axis points down on the screen, so invert the y coordinate
	// to get a right handed coordinate system
	double y2 = -y*100+height/2;
	return new Ellipse2D.Double(x2-r2,y2-r2,2*r2,2*r2);
    }

    public boolean coversScreenPoint(int xx, int yy, int width, int height){
	double r1 = r*100*View.displayRadiusMultiplier;
	double x1 = x*100+width/2;
	double y1 = -y*100+height/2;
	double distance = Math.sqrt((x1-xx)*(x1-xx) + (y1-yy)*(y1-yy));
	if(distance < r1){
	    return true;
	} else {
	    return false;
	}
    }

    public boolean coversPoint(int xx, int yy){
	double distance = Math.sqrt((x-xx)*(x-xx) + (y-yy)*(y-yy));
	if(distance < r){
	    return true;
	} else {
	    return false;
	}
    }

}