/**mGrid Íø¸ñ
 */
public class mGrid extends mObject {
	private float width = 20;          //every cube width
	
	/**
	 * Method mGrid
	 *
	 *
	 */
	public mGrid(float width) {
		this.width = width;
		this.pos = new mPoint3(0,0,0);
		mPoint3[] points = new mPoint3[17];
		points[0] = pos;
		for(int i=-2;i<=2;i++){
			points[2*i+5] = new mPoint3(2*width,i*width,0);
			points[2*i+6] = new mPoint3(-2*width,i*width,0);
		}
		for(int i=-1;i<=1;i++){
			points[2*i+13] = new mPoint3(i*width,2*width,0);
			points[2*i+14] = new mPoint3(i*width,-2*width,0);
		}
		this.shape = new mShape();
		short[][] lp = new short[2][10];
		short i;
		for(i=0;i<5;i++){
			lp[0][i] = (short)(i*2+1);
			lp[1][i] = (short)(2*i+2);
		}
		lp[0][5] = 2;
		lp[1][5] = 10;
		lp[0][9] = 1;
		lp[1][9] = 9;
		for(i=0;i<3;i++){
			lp[0][i+6] = (short)(2*i+11);
			lp[1][i+6] = (short)(2*i+12);
		}
		short[][] tp = new short[1][0];
		this.shape.set(points,lp,tp);
	}	
	
	/** reload the method to make it useless
	 */
	public void rotate(float txy,float tzx,float tyz){
		System.out.println("err: you can not rotate the grid!");
	}
	public void move(mVector3 v){
		System.out.println("err: you can not move the grid!");
	}
}
