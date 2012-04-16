import java.awt.Canvas;
import java.awt.image.BufferStrategy;

public abstract class Painter extends Canvas{

    protected BufferStrategy strategy;
    protected boolean leftPressed;
    protected boolean rightPressed;
    protected boolean upPressed;
    protected boolean downPressed;
    protected boolean nextPressed;
    protected boolean prevPressed;
    protected boolean mousePressed;
    protected int wheelClicks = 0;


    protected boolean stillActive = true;

    protected int dragStartX;
    protected int dragStartY;
    protected int dragX;
    protected int dragY;

    public void initializeBuffer(){
	this.createBufferStrategy(2);
	this.strategy = getBufferStrategy();
    }

    public void startInteraction(){
	// Start a thread for resolving incoming commands
	CommandHandler updater = new CommandHandler(this);
	Thread console = new Thread(updater);
	console.start();
    }

    public abstract void paint(long dt);
    public void resolveCommands(){};
    public void recordSize(int x, int y){};

    public void pressLeft(){
	leftPressed = true;
    }
    public void pressRight(){
	rightPressed = true;
    }
    public void pressUp(){
	upPressed = true;
    }
    public void pressDown(){
	downPressed = true;
    }
    public void pressNext(){
	nextPressed = true;
    };
    public void pressPrevious(){
	prevPressed = true;
    };

    public void releaseLeft(){
	leftPressed = false;
    }
    public void releaseRight(){
	rightPressed = false;
    }
    public void releaseUp(){
	upPressed = false;
    }
    public void releaseDown(){
	downPressed = false;
    }
    public void releaseNext(){
	nextPressed = false;
    };
    public void releasePrevious(){
	prevPressed = false;
    };

    public void mousePressed(int x, int y){
	mousePressed = true;
	dragStartX = x;
	dragStartY = y;
	dragX = x;
	dragY = y;
    };

    public void mouseClicked(int x, int y, int number){
    };

    public void mouseReleased(int x, int y){
	mousePressed = false;
    };

    public void mouseDragged(int x, int y){
	dragX = x;
	dragY = y;
    };

    public void mouseWheelMoved(int scroll){
	wheelClicks = scroll;
    };

    protected boolean commandInLine(){
	if(leftPressed ||
	   rightPressed ||
	   upPressed ||
	   downPressed ||
	   mousePressed ||
	   nextPressed ||
	   prevPressed ||
	   wheelClicks != 0){
	    return true;
	} else {
	    return false;
	}
    }



    protected static class CommandHandler implements Runnable{
		
	protected Painter painter;

	public CommandHandler(Painter thepainter){
	    this.painter = thepainter;	    
	}
	
	/**
	 * Thread for asking the painter to react.
	 */
	public void run(){	    
	    while(this.painter.stillActive){
		try{ Thread.sleep(30); } catch(Exception error){}
		if(this.painter.commandInLine()){
		    this.painter.resolveCommands();
		}
	    }	    
	}

    }




}