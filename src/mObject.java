/**class mObject
 *
 * @param must be initialized in child class:
 *    pos,center,shape,refer;
 * child class please refer to the class mCube as sample  
 */
public class mObject {
	/**   below param need to init in child class   */
	public mPoint3 pos;                             //save the POINTER of position
	public mVector3 center = null;                  //save the location of center relative to pos
	public mShape shape;
	public mReferenceTransformation refer;
	
	/**
	 * Method mObject
	 *
	 *
	 */
	public mObject() {
		pos = new mPoint3(0,0,0);
	}	
	
	public boolean hasShape(){
		return !(shape==null);
	}
	
	public mShape getShape(){
		return shape;
	}
	
	/**
	 *@see mShape.move(...)
	 */
	public void move(mVector3 v){
		shape.move(v);
	}
	/**
	 *@see mShape.rotate(...)
	 */
	public void rotate(float txy,float tzx,float tyz){
		mPoint3 oc = new mPoint3(pos,center);          //get center point by var-center 
	//	System.out.println(oc+" "+pos+" "+center);
		shape.rotate(txy,tzx,tyz,refer,oc);
		center = new mVector3(pos,oc);                 //reset the var-center(which is a relative vector to pos)
	}
}
