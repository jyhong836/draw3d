/**
 * @(#)Draw3D.java
 *
 * 3D Draw Applet application
 *
 * @author jyhong  
 * @version 1.00 13/06/21
 */
 
import java.awt.*;
import java.applet.*;
import java.awt.image.BufferedImage;

public class Draw3D extends Applet {
	/**bufimg for save the buffered image to draw
	 * it will be used at method 
	 * @see paint(Graphics g)
	 */
	BufferedImage bufimg;
//	BufferedImage buffer;
	boolean flag = false;
	
	public void init() {
		this.setBackground(Color.black);
		bufimg = new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
//		buffer = new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB);
		rotateThread thread = new rotateThread(this,bufimg);
		thread.start();
	}
	
	/**reload of the method of paint(g)
	 * it will be used when applet is refreshed when the update or repaint method 
	 *  will be called
	 */
	public void paint(Graphics g) {
		g.drawString("Welcome to Java!!", 20, 40 );
		g.drawImage(bufimg,0,0,this);
	}
	/*public void repaint(){
		for(int i=0;i<400;i++){
			for(int j = 0;j<400;j++){
				buffer.setRGB(i,j,bufimg.getRGB(i,j));
			}
		}
		flag = !flag;
		if(flag){
	//		bufimg = (BufferedImage)queue.poll();
			this.update(this.getGraphics());
		}
	}*/
}

/**class rotateThread is a thread for run the draw and animation
 */
class rotateThread extends Thread{
	Component own;
	mCanvas canvas;
	/**bufimg save the pointer (or refer ) to the buffered image which
	 * will be use to draw in comonent(own)
	 */
	BufferedImage bufimg;
	private int fps = 24;                        //frame per second
	private int speed = 1000/fps;                //animation refresh speed :==1000millis/fps
	
	/** rotateThread constructer 
	 * initialize the Component mCanvas BufferedImage Graphics
	 */
	public rotateThread(Component owner,BufferedImage bufimg){	
		this.own = owner;
		this.bufimg = bufimg;
		canvas = new mCanvas(bufimg.getWidth(),bufimg.getHeight(),own,bufimg);
		canvas.setCurcolor(mMath.toRGB(255,255,255,0));
	}
	public void run(){
		/**
		 /////test part////
		mPoint testp1 = new mPoint(1,1,3,0.5f);
		mPoint testp2 = new mPoint(70,50,20,0.5f);
	//	mMath.LinearInterpolation(testp1,testp2);
	//	System.out.println(testp1.x+" \n"+testp2.x);
	
		canvas.drawLine(testp1,testp2);
		
		
	//	mPoint3 p[] = {new mPoint3(1,2,3),new mPoint3(2,1,3),new mPoint3(3,4,5)};
		mPoint p[] = {new mPoint(70,1,3,1),new mPoint(1,40,3,1),new mPoint(70,60,5,1)};
		canvas.fillTriangle(p);
		*/
		
		mCube cube = new mCube(new mPoint3(0,90,-80),100,100,100);
	//	mCube cube2 = new mCube(new mPoint3(-300,300,0),500,500,500);
		mCone cone = new mCone(new mPoint3(0,50,0),80,200);
		mGrid grid = new mGrid(100);
		mWorld w = new mWorld();
		w.add(cube);
	//	w.add(cube2);
		w.add(grid);
		w.add(cone);
		mCamera cam = new mCamera(canvas);
		
		cam.capture(w);
		own.repaint(speed);
		
		/*
		CoordinateTransformation.test();
		
		mTriangle tri = new mTriangle(p);
		/*
		for(int i=0;i<20;i++){
			cube.rotate((float)Math.PI/40,(float)Math.PI/40,(float)Math.PI/40);
			cube.move(new mVector3(0,5,0));
		try{
			this.sleep(90);
			}catch(InterruptedException e){
				System.err.println(e.getMessage());
			}
			canvas.clear();
			cam.capture(w);
			own.repaint();
		}//*/
		
		for(int i=0;i<10;i++){
			cube.rotate(0,(float)Math.PI/20,(float)Math.PI/20);//(float)Math.PI/20);
			cube.move(new mVector3(10,0,10));
		try{
			this.sleep(speed);
			}catch(InterruptedException e){
				System.err.println(e.getMessage());
			}
			canvas.clear();
			cam.capture(w);
			own.repaint(speed);//timeout = 60
		}//*/
		for(int i=0;i<20;i++){
			cube.rotate((float)Math.PI/40,0,(float)Math.PI/40);//(float)Math.PI/20);
			cube.move(new mVector3(-10,-5,0));
		try{
			this.sleep(speed);
			}catch(InterruptedException e){
				System.err.println(e.getMessage());
			}
			canvas.clear();
			cam.capture(w);
			own.repaint(speed);
		}//*/
	//	int time = 0;
		while(true){
			cam.rotate((float)Math.PI/30,0,0);
		//	time++;
		//	float move = 30*(float)Math.sin((float)time/5);
		//	cam.move(new mVector3(move,move,move));
			try{
				this.sleep(speed);
			}catch(InterruptedException e){
					System.err.println(e.getMessage());
			}
			canvas.clear();
			cam.capture(w);
			own.repaint(speed);
		}//*/
	}
	
	/**Method setFPS is used for fps setting
	 *@param fps
	 */
	public void setFPS(int fps){
		this.fps = fps;
		this.speed = 1000/fps;
	}
}//:~