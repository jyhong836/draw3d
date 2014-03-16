/**mTriangle instance of mShape
 */
public class mTriangle extends mShape {
	
	/**
	 * Method mTriangle
	 *
	 *
	 */
	public mTriangle(mPoint3[] p){
		short [][] lp = {{0,0,1},{1,2,2}};
		short [][] tp = {{0},{1},{2}};
		this.init(p,lp,tp);
	}
	
	public void show(){
	}
	
	public void rotate(float arg){
	}
	
}
