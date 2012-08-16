import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import java.awt.geom.*;


public class GeoPainter extends Painter{

    private GeoWindow window;
    private ASH manager;

    private Structure geometry;
    private ViewPoint lookout;
    private View scene;
    private int updatedScene = 0;

    private boolean drawcell = false;
    private boolean drawaxis = false;
    private boolean followCenter = true;
    private boolean displayFrame = false;

    public static boolean MOUSE_INFO = false;
    public static boolean MOUSE_PICK = false;

    private boolean initDone = false;
    private int width;
    private int height;

    public GeoPainter(GeoWindow window, ASH manager){
	this.window = window;
	this.width = window.getWidth();
	this.height = window.getHeight();
	this.manager = manager;
	this.geometry = null;
	this.lookout = null;
	this.scene = null;
    }

    public void setGeometry(Structure geo){
	this.geometry = geo;
    }

    public void setViewPoint(ViewPoint look){
	this.lookout = look;
    }

    public ASH getManager(){
	return this.manager;
    }

    public Structure getGeometry(){
	return this.geometry;
    }

    public ViewPoint getViewPoint(){
	return this.lookout;
    }

    public View getView(){
	return this.scene;
    }

    public void resetViewPoint(){
	try{
	    this.lookout = ViewPoint.resetViewPoint(this.geometry,this.lookout);
	    updateGeo();
	} catch(Exception error){
	    if(!ASH.runningScript){
		System.out.println("could not reset the view");
	    }
	}
    }

    public void initGeo(Structure geo, ViewPoint look){
	this.geometry = geo;
	this.lookout = look;
	this.scene = new View(geo,look,width,height);
	stillActive = true;
	updatedScene = 5;
	initDone = true;
    }

    public void updateGeo(Structure geo, ViewPoint look){
	this.geometry = geo;
	this.lookout = look;
	this.scene = new View(geo,look,width,height);
	updatedScene = 5;
    }

    public void updateGeo(Structure geo){
	this.geometry = geo;
	this.scene = new View(geo,lookout,width,height);	
	updatedScene = 5;
    }

    public void updateGeo(ViewPoint look){
	this.lookout = look;
	this.scene = new View(geometry,look,width,height);
	updatedScene = 5;
    }

    public void updateGeo(){
	this.scene = new View(this.geometry,this.lookout,width,height);	
	updatedScene = 5;
    }

    public void resolveCommands(){
	double sideD = 0.0;
	double upD = 0.0;
	if(leftPressed){
	    sideD -= 0.1;
	}
	if(rightPressed){
	    sideD += 0.1;
	}
	if(upPressed){
	    upD += 0.1;
	}
	if(downPressed){
	    upD -= 0.1;
	}
	if(wheelClicks != 0){
	    upD -= 0.3*wheelClicks;
	    wheelClicks = 0;
	}
	if(mousePressed){
	    if((dragX-dragStartX) != 0 || (dragY-dragStartY) != 0){
		if(shiftPressed){

		    int natoms = geometry.countParticles();
		    Vector side = lookout.getSide();
		    Vector up = lookout.getUp();
		    Vector shift = side.times(0.1*(dragX-dragStartX)).plus(up.times(-0.1*(dragY-dragStartY)));
		    for(int i=0; i<natoms; i++){
			if(geometry.getParticle(i).isPicked()){
			    geometry.getParticle(i).shiftCoordinates(shift);
			}
		    }
		    geometry.forcePeriodicBounds();
		    updateGeo();
		    dragStartX = dragX;
		    dragStartY = dragY;

		} else if(rotPressed) {

		    int natoms = geometry.countParticles();
		    Vector side = lookout.getSide();
		    Vector up = lookout.getUp();
		    Quaternion rotator1 = new Quaternion(up,  0.01*(dragX-dragStartX));
		    Quaternion rotator2 = new Quaternion(side,0.01*(dragY-dragStartY));
		    Quaternion rotator = rotator1.times(rotator2);
		    for(int i=0; i<natoms; i++){
			if(geometry.getParticle(i).isPicked()){
			    geometry.getParticle(i).rotate(rotator);
			}
		    }
		    updateGeo();
		    dragStartX = dragX;
		    dragStartY = dragY;

		} else {
		    try{
			lookout.rotateViewPoint((double)(dragX-dragStartX)*-0.01,(double)(dragY-dragStartY)*0.01,geometry.getCenter());
			updateGeo();
			dragStartX = dragX;
			dragStartY = dragY;
		    } catch(Exception error){
		    }
		}
	    }
	}

	if(sideD != 0 || upD != 0){
	    try{
		lookout.shiftViewPoint(0.4*sideD,10*upD);
		//lookout.rotateViewPoint(sideD,upD,geometry.getCenter());
		updateGeo();
	    } catch(Exception error){}
	}
	if(nextPressed){
	    if(updatedScene < 3){
		this.manager.nextFrame();
		updateGeo();
		updatedScene = 7;
	    }
	}
	if(prevPressed){
	    if(updatedScene < 3){
		this.manager.previousFrame();
		updateGeo();
		updatedScene = 7;
	    }
	}

    }

    public void mouseClicked(int x, int y, int number){
	if(MOUSE_INFO){
	    manager.listMouseInfo(x,y);
	}
	if(MOUSE_PICK){
	    manager.pickByMouse(x,y,number);
	}
    }

    public void setShowAxis(boolean yesno){
	this.drawaxis = yesno;
    }

    public void setShowCell(boolean yesno){
	this.drawcell = yesno;
    }

    public void setShowFrame(boolean yesno){
	this.displayFrame = yesno;
    }

    public void aimAtCenter(){
	if(followCenter && !ASH.runningScript){
	    this.lookout.aimAtCenter(this.geometry);
	}
    }

    public void recordSize(int x, int y){
	//System.out.println("new dimensions: "+x+", "+y);
	this.width = x;
	this.height = y;
	this.setBounds(0,0,x,y);
	this.updateGeo();
    }


    private void arrange(Graphics2D g){
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
	g.setStroke(new BasicStroke(View.STROKE_WIDTH));
	if(!initDone){
	    g.setColor(Color.black);
	    g.fillRect(0,0,width,height);
	} else {
	    g.setColor(View.bg);
	    g.fillRect(0,0,width,height);

	    Projection[] atomList = scene.getProjections();
	    for(int i=0; i<atomList.length; i++){
		if(atomList[i].isVisible()){
		    Shape blob = atomList[i].getAtomicShape(width,height);
		    g.setPaint(atomList[i].getLineColor());
		    g.draw(blob);	
		    g.setPaint(atomList[i].getColor());
		    g.fill(blob);
		}		    
	    }
	    
	    if(drawcell){
		g.setPaint(View.transRed);
		g.draw(scene.getCellAxisLine(0));
		g.setPaint(View.transGreen);
		g.draw(scene.getCellAxisLine(1));
		g.setPaint(View.transBlue);
		g.draw(scene.getCellAxisLine(2));		    
	    }
	    if(drawaxis){
		g.setPaint(View.transLightRed);
		g.draw(scene.getUnitAxisLine(0));
		g.setPaint(View.transLightGreen);
		g.draw(scene.getUnitAxisLine(1));
		g.setPaint(View.transLightBlue);
		g.draw(scene.getUnitAxisLine(2));		    
	    }
	    if(displayFrame){
		FontRenderContext frc = g.getFontRenderContext();
		Font f = new Font("Helvetica",Font.PLAIN, 10);
		String s = "frame "+(this.manager.getCurrentFrame()+1)+" / "+this.manager.getNumberOfFrames()+"  "+this.manager.getCurrentFrameName();
		TextLayout tl = new TextLayout(s, f, frc);
		Dimension theSize=getSize();
		g.setColor(View.black);
		tl.draw(g, 20, 20);
	    }
	    
	}
    }

    public BufferedImage getPainting(){
	BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D graphics = bi.createGraphics();
	arrange(graphics);
	return bi;
    }

    public void paint(long dt){
	if(updatedScene > 2){
	    updatedScene--;
	    if(updatedScene < 5){

		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		arrange(g);
	    
		// we've completed drawing so clear up the graphics
		// and flip the buffer over	    
		g.dispose();
		strategy.show();
	    }
	}
    }

}