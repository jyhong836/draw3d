/**
 * mCube is the instance class of mObject
 * @see mObject.java
 * the param must be initialized :pos,height,width,length
 *   shape,center,refer
 */
public class mCube extends mObject {
	
	//mCube attributes
	float height,width,length;
	
	/**
	 * Method mCube
	 *
	 * @param pos ,the cube's position in world reference
	 * @param height(z) width(x) length(y) ,the atrribute 
	 *    of cube
	 * center is set AT the geometre center of cube
	 * the auto direction is x(width),y(length),z(height)
	 *   which will be saved in (mReferenceTransformation) refer
	 */
	public mCube(mPoint3 pos,float height,float width,float length) {
		this.pos = pos;
		this.height = height;
		this.width = width;
		this.length = length;
		this.shape = new mShape();
		mPoint3[] points = {
			pos,
			new mPoint3(pos,width,0,0),
			new mPoint3(pos,0,length,0),
			new mPoint3(pos,0,0,height),
			new mPoint3(pos,width,length,0),
			new mPoint3(pos,width,0,height),
			new mPoint3(pos,0,length,height),
			new mPoint3(pos,width,length,height)
		};
		short[][] linepoints = {
			{0,0,0,1,1,2,2,3,3,4,5,6},
			{1,2,3,4,5,4,6,5,6,7,7,7}
		};
		short[][] tripoints = {
			{0,0,0,4,4,4,5,5,5,6,6,6},
			{2,1,3,7,2,1,1,7,3,7,3,2},
			{1,3,2,1,7,2,7,3,1,2,7,3}
		};
		this.shape.set(points,linepoints,tripoints);
		this.center = new mVector3(width/2,length/2,height/2);
		this.refer = new mReferenceTransformation(new mPoint3(pos,center),
			new mVector3(0,1,0),
			new mVector3(1,0,0));
	}	
}
