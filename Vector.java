public class Vector{

    private double[] vec;
    private int dimension;

    public Vector(){
	this.dimension = 3;
	this.vec = new double[dimension];
	for(int i = 0; i < dimension; i++){
	    this.vec[i] = 0.0;
	}
    }

    public Vector(int dim){
	this.dimension = dim;
	this.vec = new double[dimension];
	for(int i = 0; i < dimension; i++){
	    this.vec[i] = 0.0;
	}
    }

    public Vector(double[] reals){	
	this.dimension = reals.length;
	this.vec = new double[dimension];
	for(int i = 0; i < dimension; i++){
	    this.vec[i] = reals[i];
	}
    }

    public Vector(double x, double y, double z){
	double[] v = {x,y,z};
	this.vec = v;
	this.dimension = 3;
    }
    
    public Vector copy(){
	double[] newv = new double[vec.length];
	for(int i=0; i<newv.length; i++){
	    newv[i] = vec[i];
	}
	return new Vector(newv);
    }
    
    public double element(int index){
	if(index >= 0 && index < dimension){
	    return this.vec[index];
	} else {
	    return 0.0;
	}
    }

    public void setElement(int index, double value){
	if(index >= 0 && index < dimension){
	    this.vec[index] = value;
	}
    }
    
    public double[] toArray(){
	return vec;
    }

    public double[][] toHorizontalVector(){
	double[][] result = new double[1][vec.length];
	for(int i=0; i<vec.length; i++){
	    result[0][i] = vec[i];
	}
	return result;
    }

    public double[][] toVerticalVector(){
	double[][] result = new double[vec.length][1];
	for(int i=0; i<vec.length; i++){
	    result[i][0] = vec[i];
	}
	return result;
    }

    public int dimension(){
	return this.dimension;
    }

    public Vector plus(double r){
	double[] result = new double[this.dimension];
	for(int i=0; i<dimension; i++){
	    result[i] = vec[i] + r;
	}
	return new Vector(result);
    }

    public Vector minus(double r){
	double[] result = new double[this.dimension];
	for(int i=0; i<dimension; i++){
	    result[i] = vec[i] - r;
	}
	return new Vector(result);
    }

    public Vector times(double r){
	double[] result = new double[this.dimension];
	for(int i=0; i<dimension; i++){
	    result[i] = vec[i] * r;
	}
	return new Vector(result);
    }

    public Vector dividedBy(double r){
	double[] result = new double[this.dimension];
	for(int i=0; i<dimension; i++){
	    result[i] = vec[i] / r;
	}
	return new Vector(result);
    }

    public Vector plus(Vector v2){
	int newdim = Math.min(this.dimension,v2.dimension);
	double[] result = new double[newdim];	
	for(int i=0; i<newdim; i++){
	    result[i] = vec[i] + v2.element(i);
	}
	return new Vector(result);
    }

    public Vector minus(Vector v2){
	int newdim = Math.min(this.dimension,v2.dimension);
	double[] result = new double[newdim];	
	for(int i=0; i<newdim; i++){
	    result[i] = vec[i] - v2.element(i);
	}
	return new Vector(result);
    }

    public Vector elementwiseProduct(Vector v2){
	int newdim = Math.min(this.dimension,v2.dimension);
	double[] result = new double[newdim];	
	for(int i=0; i<newdim; i++){
	    result[i] = vec[i] * v2.element(i);
	}
	return new Vector(result);
    }

    public Vector elementwiseDivision(Vector v2){
	int newdim = Math.min(this.dimension,v2.dimension);
	double[] result = new double[newdim];	
	for(int i=0; i<newdim; i++){
	    result[i] = vec[i] / v2.element(i);
	}
	return new Vector(result);
    }

    public double dot(Vector v2){
	int newdim = Math.min(this.dimension,v2.dimension);
	double result = 0.0;	
	for(int i=0; i<newdim; i++){
	    result += vec[i] * v2.element(i);
	}
	return result;
    }

    public Vector cross(Vector vv){
	double[] result = new double[3];
	if(this.dimension >= 3 && vv.dimension >= 3){
	    double[] v2 = vv.toArray();
	    result[0] = vec[1]*v2[2]-vec[2]*v2[1];
	    result[1] = vec[2]*v2[0]-vec[0]*v2[2];
	    result[2] = vec[0]*v2[1]-vec[1]*v2[0];
	} else {
	    result[0] = 0;
	    result[1] = 0;
	    result[2] = 0;
	}
	return new Vector(result);
    }

    public double norm(){
	double sqsum = 0.0;
	for(int i=0; i<dimension; i++){
	    sqsum += vec[i]*vec[i];
	}
	return Math.sqrt(sqsum);
    }

    public Vector unit(){
	return this.dividedBy(this.norm());
    }

    public Vector resetDimensionality(int newdim){
	double[] result = new double[newdim];
	for(int i=0; i<newdim; i++){
	    if(this.dimension > i){
		result[i] = vec[i];
	    } else {
		result[i] = 0.0;
	    }
	}
	return new Vector(result);
    }

    public Quaternion quaternionize(){
	return new Quaternion(0.0,vec[0],vec[1],vec[2]);
    }

    public Vector rotate(Quaternion q){
	Quaternion initial = this.quaternionize();
	Quaternion rotated = (q.times(initial)).times(q.conjugate());
	return rotated.vectorize();
    }

    public Vector rotate(Vector axis, double angle){
	Quaternion rotator = new Quaternion(axis,angle);
	return this.rotate(rotator);
    }

    public Vector projectionOnPlane(Vector proj){
	return this.minus(projectionOnVector(proj));
    }

    public Vector projectionOnVector(Vector proj){
	Vector unity = proj.unit();
	return unity.times(this.dot(unity));	
    }

    public Vector matMul(double[][] mat)
	throws Exception{
	if(dimension == mat[0].length){
	    double[] result = new double[mat.length];
	    for(int i=0; i<mat.length; i++){
		result[i] = 0.0;
		for(int j=0; j<mat[0].length; j++){
		    result[i] += mat[i][j]*vec[j];
		}
	    }
	    return new Vector(result);
	} else {
	    throw new Exception();
	}
    }

    public String toString(){
	String out = "";
	for(int i=0; i<dimension; i++){
	    out+=vec[i]+"  ";
	}
	return out;
    }

    public Vector inversionByPlane(Vector normal, Vector point){
	 return this.minus(this.minus(point).projectionOnVector(normal).times(2));
    }
}