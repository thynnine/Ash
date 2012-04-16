import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;


public class GeoWindow extends JFrame{

    private ScreenRefresher updater;
    private Painter painter;

    private boolean painterWorking;
    private boolean windowOpen;

    public static final int WINDOW_HEIGHT = 400;
    public static final int WINDOW_WIDTH = 400;
    public static final int FRAME_DT = 30;

    /**
     * Construct our graphics window
     */
    public GeoWindow(ASH manager) {
	// create a frame to contain our graphics
	super("Atomic Structure Handler");

	// get hold the content of the frame and set up the initial resolution
	JPanel panel = (JPanel) this.getContentPane();
	panel.setPreferredSize(new Dimension(GeoWindow.WINDOW_WIDTH,GeoWindow.WINDOW_HEIGHT));
	panel.setLayout(null);

	this.painter = new GeoPainter(this,manager);
	
	// setup our canvas size and put it into the content of the frame	
	this.painter.setBounds(0,0,GeoWindow.WINDOW_WIDTH,GeoWindow.WINDOW_HEIGHT);
	panel.add(this.painter);
	
	// Tell AWT not to bother repainting our canvas since we're
	// going to do that our self in accelerated mode	
	setIgnoreRepaint(true);
	
	// finally make the window visible 
	this.pack();
	//this.setResizable(false);
	this.setVisible(true);
	
	// add a listener to respond to the user closing the window. If they
	// do we'd like to exit the game	
	this.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		    System.exit(0);
		}
		public void windowGainedFocus(WindowEvent e) {
		    painter.requestFocus();
		}
	    });

	this.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
		    painter.recordSize(e.getComponent().getWidth(),
				       e.getComponent().getHeight());
		}
	    });
	
	// add a key input system (defined below) to our canvas
	// so we can respond to key pressed
	this.painter.addKeyListener(new KeyInputHandler(this));
	this.painter.addMouseListener(new MouseInputHandler(this));
	this.painter.addMouseMotionListener(new MouseInputHandler(this));
	this.painter.addMouseWheelListener(new MouseInputHandler(this));
	
	this.painter.initializeBuffer();
	//this.painter.requestFocus();
	this.painterWorking = true;
	this.windowOpen = true;
	
    }

    public void startGraphics(){
	// Start a thread for updating the on-screen graphics
	updater = new ScreenRefresher(this);
	Thread screen = new Thread(updater);
	screen.start();
    }

    public void resumeGraphics(){
	this.painterWorking = true;
    }

    public void pauseGraphics(){
	this.painterWorking = false;
    }

    public Painter getPainter(){
	return this.painter;
    }

    public void closeWindow(){
	this.windowOpen = false;
    }

    public void changePainter(Painter newPainter){

	this.painterWorking = false;
	this.painter = newPainter;
	JPanel panel = (JPanel) this.getContentPane();
	this.painter.setBounds(0,0,GeoWindow.WINDOW_WIDTH,GeoWindow.WINDOW_HEIGHT);
	panel.removeAll();
	panel.add(newPainter);	
	this.pack();
	this.painter.initializeBuffer();
	this.painter.addKeyListener(new KeyInputHandler(this));
	this.painter.addMouseListener(new MouseInputHandler(this));
	this.painter.requestFocus();
	this.painterWorking = true;

    }

    public boolean isOpen(){
	return this.windowOpen;
    }

    public boolean painterWorking(){
	return this.painterWorking;
    }

    /**
     * A class to handle keyboard input from the user.
     */
    private class MouseInputHandler extends MouseAdapter {
	/** 
	 * The window is always the same, but the
	 * painter (graphics engine) may change. Therefore, always ask
	 * for the current painter.
	 */
	private GeoWindow window;

	public MouseInputHandler(GeoWindow window){
	    super();
	    this.window = window;
	}

	public void mouseClicked(MouseEvent e){
	    this.window.getPainter().mouseClicked(e.getX(),e.getY(),e.getClickCount());
	}

	public void mousePressed(MouseEvent e){
	    this.window.getPainter().mousePressed(e.getX(),e.getY());
	}

	public void mouseReleased(MouseEvent e){
	    this.window.getPainter().mouseReleased(e.getX(),e.getY());
	}

	public void mouseMoved(MouseEvent e){
	}

	public void mouseDragged(MouseEvent e){
	    this.window.getPainter().mouseDragged(e.getX(),e.getY());
	}

	public void mouseWheelMoved(MouseWheelEvent e){
	    this.window.getPainter().mouseWheelMoved(e.getWheelRotation());
	}

    }



    /**
     * A class to handle keyboard input from the user.
     */
    private class KeyInputHandler extends KeyAdapter {
	/** 
	 * The window is always the same, but the
	 * painter (graphics engine) may change. Therefore, always ask
	 * for the current painter.
	 */
	private GeoWindow window;

	public KeyInputHandler(GeoWindow window){
	    super();
	    this.window = window;
	}

	/**
	 * Notification from AWT that a key has been pressed. Note that
	 * a key being pressed is equal to being pushed down but *NOT*
	 * released. Thats where keyTyped() comes in.
	 *
	 * @param e The details of the key that was pressed 
	 */
	public void keyPressed(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		this.window.getPainter().pressLeft();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		this.window.getPainter().pressRight();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_UP) {
		this.window.getPainter().pressUp();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		this.window.getPainter().pressDown();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_N) {
		this.window.getPainter().pressNext();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_P) {
		this.window.getPainter().pressPrevious();
	    }

	    
	} 
	
	/**
	 * Notification from AWT that a key has been released.
	 *
	 * @param e The details of the key that was released 
	 */
	public void keyReleased(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		this.window.getPainter().releaseLeft();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		this.window.getPainter().releaseRight();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_UP) {
		this.window.getPainter().releaseUp();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		this.window.getPainter().releaseDown();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_N) {
		this.window.getPainter().releaseNext();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_P) {
		this.window.getPainter().releasePrevious();
	    }

	}
	
	/**
	 * Notification from AWT that a key has been typed. Note that
	 * typing a key means to both press and then release it.
	 *
	 * @param e The details of the key that was typed. 
	 */
	public void keyTyped(KeyEvent e) {
	    
	    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	    }
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	    }
	    if (e.getKeyCode() == KeyEvent.VK_UP) {
	    }
	    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	    }
	    if (e.getKeyCode() == KeyEvent.VK_F) {
	    }
	    if (e.getKeyCode() == KeyEvent.VK_E) {
	    }
	    
	}
	
    }
    
    private static class ScreenRefresher implements Runnable{
	
	private GeoWindow window;
	
	public ScreenRefresher(GeoWindow thewindow){
	    this.window = thewindow;
	}
	
	/**
	 * Thread for asking the painter to refresh the graphics.
	 */
	public void run(){
	    long lastLoopTime = System.currentTimeMillis();
	    long delta = 0;
	    
	    while(this.window.isOpen()){
		delta = System.currentTimeMillis() - lastLoopTime;
		lastLoopTime = System.currentTimeMillis();
		
		if(this.window.painterWorking()){
		    try{
			this.window.getPainter().paint(delta);
		    } catch(Exception error){
			//System.out.println("painting error");
			//error.printStackTrace();
			//System.exit(0);
		    }
		}
		delta = System.currentTimeMillis() - lastLoopTime;
		
		try { Thread.sleep(FRAME_DT-delta); } catch (Exception e) {}
	    }
	    
	}

    }



}