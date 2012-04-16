public class ViewPoint{

    private Vector position;
    private Vector direction;
    private Vector up;
    private Vector side;
    private double zoom;
    private int projectionType;

    public static final int PERSPECTIVE = 0;
    public static final int ISOMETRIC = 1;
    public static final int LENS = 2;

    public ViewPoint(Vector p, Vector d, Vector u){
	position = p;
	direction = d;
	up = u;
	zoom = d.norm();
	updateAxes();
	projectionType = PERSPECTIVE;
    }

    public ViewPoint(Vector p, Vector d, Vector u, int pr){
	position = p;
	direction = d;
	up = u;
	zoom = d.norm();
	updateAxes();
	if(pr == ISOMETRIC ||
	   pr == PERSPECTIVE ||
	   pr == LENS){
	    projectionType = pr;
	} else {
	    projectionType = PERSPECTIVE;
	}
    }

    public void setProjectionType(int projo){
	this.projectionType = projo;
    }

    public int getProjectionType(){
	return this.projectionType;
    }

    public void shiftViewPoint(double sideD, double upD){
	Vector shifter = direction.unit().times(upD);
	position = position.plus(shifter);
	Quaternion rotation = new Quaternion(direction,-sideD);
	//position = position.rotate(rotation);
	direction = direction.rotate(rotation);
	up = up.rotate(rotation);
	side = side.rotate(rotation);	
    }

    public void rotateViewPoint(double sideD, double upD, Vector center){
	Vector axis = side.times(-upD).plus(up.times(sideD));
	double angle = Math.sqrt(sideD*sideD + upD*upD);
	Quaternion rotation = new Quaternion(axis,angle);
	position = position.minus(center).rotate(rotation).plus(center);
	direction = direction.rotate(rotation);
	up = up.rotate(rotation);
	side = side.rotate(rotation);
    }

    private void updateAxes(){
	if(direction.cross(up).norm() > 0.01){
	    this.side = direction.cross(up).unit();
	} else {
	    if(direction.cross(new Vector(0,0,1)).norm() > 0.01){
		this.side = direction.cross(new Vector(0,0,1)).unit();
	    } else {
		this.side = direction.cross(new Vector(0,1,0)).unit();
	    }
	}
	up = direction.cross(side).unit().times(-1);
	//System.out.println(side.element(0)+" "+side.element(1)+" "+side.element(2));
	//System.out.println(up.element(0)+" "+up.element(1)+" "+up.element(2));

    }

    public Vector getPosition(){
	return this.position;
    }

    public void setPosition(Vector newp){
	this.position = newp;
    }

    public Vector getDirection(){
	return this.direction;
    }

    public void setDirection(Vector newd){
	this.direction = newd;
	zoom = direction.norm();
	updateAxes();
    }

    public Vector getUp(){
	return this.up;
    }

    public void setUp(Vector newu){
	this.up = newu;
	updateAxes();
    }

    public double getZoom(){
	return this.zoom;
    }
    
    public Vector getSide(){
	return this.side;
    }

    public void aimAtCenter(Structure geo){
	Vector center = geo.getCenter();
	if(center == null){
	    geo.calculateCenter();
	    center = geo.getCenter();
	}
	this.direction = (center.minus(position)).unit().times(getZoom());
	updateAxes();
    }

    public static ViewPoint resetViewPoint(Structure geo)
	throws Exception{
	double stepback = 50;
	if(geo.countAllAtoms() > 100){
	    stepback = 12.0*Math.pow(geo.countAllAtoms(),1.0/3.0);
	}
	Vector positi = new Vector(0.0,0.0,stepback);
	Vector up = new Vector(0.0,1.0,0.0);
	Vector center = geo.getCenter();
	positi = positi.plus(center);
	Vector direct = (center.minus(positi)).unit().times(10);
	
	return new ViewPoint(positi, direct, up);	
    }

    public static ViewPoint resetViewPoint(Structure geo, ViewPoint old)
	throws Exception{
	double stepback = 50;
	if(geo.countAllAtoms() > 100){
	    stepback = 15.0*Math.pow(geo.countAllAtoms(),1.0/3.0);
	}
	Vector positi = new Vector(0.0,0.0,stepback);
	Vector up = new Vector(0.0,1.0,0.0);
	Vector center = geo.getCenter();
	positi = positi.plus(center);
	Vector direct = (center.minus(positi)).unit().times(10);
	int projo = old.getProjectionType();

	ViewPoint newview = new ViewPoint(positi, direct, up, projo);
	return newview;
    }

}