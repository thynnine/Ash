public class Quaternion{

    private double[] qt = {0.0, 0.0, 0.0, 0.0};

    public Quaternion(){
	this.qt[0] = 0;
	this.qt[1] = 0;
	this.qt[2] = 0;
	this.qt[3] = 0;	
    }

    public Quaternion(Vector r, double a){
	Vector axis;
	if(r.dimension() == 3){
	    axis = r.unit();
	} else {
	    axis = r.resetDimensionality(3).unit();
	}
	this.qt[0] = Math.cos(a/2);
	double normer = Math.sin(a/2);
	this.qt[1] = axis.element(0)*normer;
	this.qt[2] = axis.element(1)*normer;
	this.qt[3] = axis.element(2)*normer;
    }

    public Quaternion(double w, double x, double y, double z){
	this.qt[0] = w;
	this.qt[1] = x;
	this.qt[2] = y;
	this.qt[3] = z;	
    }

    public Quaternion(double[] arr){
	for(int i = 0; i < 4; i++){
	    if(arr.length > i){
		this.qt[i] = arr[i];
	    } else {
		this.qt[i] = 0.0;
	    }
	}
    }

    public Quaternion copy(){
	return new Quaternion(this.qt[0],this.qt[1],this.qt[2],this.qt[3]);
    }

    public double element(int index){
	if(index >= 0 && index < 4){
	    return qt[index];
	} else {
	    return 0.0;
	}
    }
    
    public double[] toArray(){
	return this.qt;
    }

    public Quaternion plus(double r){
	double[] sum = new double[4];
	for(int i=0; i<4; i++){
	    sum[i] = this.qt[i] + r;
	}
	return new Quaternion(sum);
    }

    public Quaternion minus(double r){	
	double[] diff = new double[4];
	for(int i=0; i<4; i++){
	    diff[i] = this.qt[i] - r;
	}
	return new Quaternion(diff);
    }

    public Quaternion times(double r){	
	double[] prod = new double[4];
	for(int i=0; i<4; i++){
	    prod[i] = this.qt[i] * r;
	}
	return new Quaternion(prod);
    }

    public Quaternion dividedBy(double r){	
	double[] div = new double[4];
	for(int i=0; i<4; i++){
	    div[i] = this.qt[i] / r;
	}
	return new Quaternion(div);
    }

    public Quaternion plus(Quaternion q2){	
	double[] sum = new double[4];
	for(int i=0; i<4; i++){
	    sum[i] = this.qt[i] + q2.element(i);
	}
	return new Quaternion(sum);
    }

    public Quaternion minus(Quaternion q2){	
	double[] diff = new double[4];
	for(int i=0; i<4; i++){
	    diff[i] = this.qt[i] - q2.element(i);
	}
	return new Quaternion(diff);
    }

    public Quaternion times(Quaternion qq){
	double[] prod = new double[4];
	double[] q2 = qq.toArray();
	prod[0] = qt[0]*q2[0] - qt[1]*q2[1] - qt[2]*q2[2] - qt[3]*q2[3];
	prod[1] = qt[0]*q2[1] + qt[1]*q2[0] + qt[2]*q2[3] - qt[3]*q2[2];
	prod[2] = qt[0]*q2[2] - qt[1]*q2[3] + qt[2]*q2[0] + qt[3]*q2[1];
	prod[3] = qt[0]*q2[3] + qt[1]*q2[2] - qt[2]*q2[1] + qt[3]*q2[0];
	return new Quaternion(prod);
    }

    public Quaternion conjugate(){
	double[] conj = new double[4];
	conj[0] = this.qt[0];
	conj[1] = -this.qt[1];
	conj[2] = -this.qt[2];
	conj[3] = -this.qt[3];
	return new Quaternion(conj);
    }

    public double norm(){
	return Math.sqrt(qt[0]*qt[0] + qt[1]*qt[1] + qt[2]*qt[2] + qt[3]*qt[3]);
    }

    public Quaternion inverse(){
	return this.conjugate().dividedBy(this.norm());
    }

    public Quaternion unit(){
	return this.dividedBy(this.norm());
    }

    public boolean isUnit(){
	return (Math.abs(this.unit().norm()-1.0) < 0.00000001 );
    }

    public double rotationAngle(){
	return 2*Math.acos(this.element(0));
    }

    public Vector rotationAxis(){	
	double normer = Math.sin(this.rotationAngle());
	if(normer != 0.0){
	    double[] axis = new double[3];
	    axis[0] = this.qt[1]/normer;
	    axis[1] = this.qt[2]/normer;
	    axis[2] = this.qt[3]/normer;
	    return new Vector(axis);
	} else {
	    double[] axis = new double[3];
	    axis[0] = 0;
	    axis[1] = 0;
	    axis[2] = 1;
	    return new Vector(axis);
	}
    }

    public double[][] rotationMatrix(){
	double[][] mat = new double[3][3];

	mat[0][0] = 1-2*(qt[2]*qt[2]+qt[3]*qt[3]);
	mat[0][1] = 2*(qt[1]*qt[2]-qt[0]*qt[3]);
	mat[0][2] = 2*(qt[1]*qt[3]+qt[0]*qt[2]);
	mat[1][0] = 2*(qt[1]*qt[2]+qt[0]*qt[3]);
	mat[1][1] = 1-2*(qt[1]*qt[1]+qt[3]*qt[3]);
	mat[1][2] = 2*(qt[2]*qt[3]-qt[0]*qt[1]);
	mat[2][0] = 2*(qt[1]*qt[3]-qt[0]*qt[2]);
	mat[2][1] = 2*(qt[2]*qt[3]+qt[0]*qt[1]);
	mat[2][2] = 1-2*(qt[2]*qt[2]+qt[1]*qt[1]);

	return mat;
    }

    public Vector vectorize(){
	double[] elems = {qt[1],qt[2],qt[3]};
	return new Vector(elems);
    }

}