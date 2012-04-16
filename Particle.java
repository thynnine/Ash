public interface Particle{

    public Vector getCoordinates();
    public void setCoordinates(Vector vec);
    public void shiftCoordinates(Vector vec);
    public double getMass();
    public void pick(boolean yesno);
    public void hide(boolean yesno);
    public boolean isPicked();
    public boolean isHidden();
    public void rotate(Quaternion q);
    public Quaternion getOrientation();
    public boolean isAtomic();
    public int countAtoms();
    public int countParticles();
    public boolean isSimilarTo(Particle other);
    public Particle copy();
    public Particle getParticle(int index);
    public Particle[] getParticles();
    public Atom[] getAtoms();
    public String toString();

}