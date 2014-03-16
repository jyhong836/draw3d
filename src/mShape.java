/** class mShape
 * @param (mPoints[])points ,(protected short [][]) linepoints ,(protected short [][]) tripoints
 * 总是以自身为参考
 */
public class mShape {
	
	protected mPoint3[] points;                           //p[0] save the position
	protected short [][] linepoints = new short [2][];    //lines      (p[0][i],p[1][i])
	/**triangles  (p[0][i],p[1][i],p[2][i]) 
	 *  the n vector will be calculate as <p[0],p[1]> X <p[0],p[2]>,
	 *  so you should set the order of tripoints[0,1,2][] correctly
	 */
	protected short [][] tripoints = new short [3][];
	
	/**
	 * Method mShape
	 *
	 * init an instance of mShape
	 * @param should be set by call Mathod init ,which should be called when 
	 *   child class init in constructer 
	 * 
	 */
	public mShape() {
	}	
	
	protected void set(mPoint3[] p ,short [][] lp ,short [][] tp){
		this.points = p;
		this.linepoints = lp;
		this.tripoints = tp;
	}
	
	protected void init(mPoint3[] p ,short [][] lp ,short [][] tp){
		if(lp.length!=2){
			System.err.println("@ method mShape:(need2)length err"+lp.length);
			return ;
		}
		if(lp[0].length!=lp[1].length){
			System.err.println("@ method mShape:array length not match"+lp[0].length+"!="+lp[1].length);
			return ;
		}
		if(tp.length!=3){
			System.err.println("@ method mShape:(need2)length err"+tp.length);
			return ;
		}
		if(tp[0].length!=tp[1].length||tp[2].length!=tp[1].length||tp[0].length!=tp[2].length){
			System.err.println("@ method mShape:array length not match"+tp[0].length+"!="+tp[1].length+" "+tp[2].length);
			return ;
		}
		int l = p.length;
		points = new mPoint3[l];
		while((--l)>=0){
			points[l] = new mPoint3(p[l]);
		}
		l = lp[0].length;
		linepoints[0] = new short[l];
		linepoints[1] = new short[l];
		while((--l)<=0){
			linepoints[0][l] = lp[0][l];
			linepoints[1][l] = lp[1][l];
		}
		l = tp[0].length;
		tripoints[0] = new short[l];
		tripoints[1] = new short[l];
		tripoints[2] = new short[l];
		while((--l)>=0){
			tripoints[0][l] = tp[0][l];
			tripoints[1][l] = tp[1][l];
			tripoints[2][l] = tp[2][l];
		}
	}
	/**Method rotate
	 * rotate the points of mShape
	 * use the method in refer 
	 * @see mReferenceTransformation.java
	 * @param centerpoint ,used to set the center
	 * 
	 */  
	public void rotate(float txy,float tzx,float tyz,mReferenceTransformation refer,mPoint3 centerpoint){
		mPoint3 oldpos = refer.getCenter();                   //对于mCamera来说pos 同时扮演了两个角色，所以要把它保护起来
		refer.setCenter(centerpoint);                         //set the refer's center(pos)
		
		int l = points.length;
		while((--l)>=0){
			points[l].set(refer.referenceTransform(points[l]));
		}
		refer.rotate(txy,tzx,tyz);
		l = points.length;
		while((--l)>=0){
			points[l].set(refer.antiReferenceTransform(points[l]));
		}
		refer.setCenter(oldpos);                               //返回原始值
	}
	/**Method move
	 * move the points of mShape
	 */
	public void move(mVector3 v){
		int l = points.length;
		while((--l)>=0){
			points[l].move(v);
		}
	}
}
