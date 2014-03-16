/**class instance of mObject
 */
public class mCone extends mObject {
	//mCone attribute
	float radius,height;
	
	/**
	 * Method mCone
	 *
	 * @param pos ,is the center of the circle on the bottom
	 */
	public mCone(mPoint3 pos,float radius,float height) {
		this.pos = pos;
		this.radius = radius;
		this.height = height;
		this.shape = new mShape();
		int num = (int)(Math.PI*radius*2/20);
		if(num<10)
			num = 10;
		float theta = 2*(float)Math.PI/num;
		mPoint3[] points = new mPoint3[num+2];
		points[0] = pos;
		for(int i=0;i<num;i++){
			points[i+1] = new mPoint3(radius,(float)Math.PI/2,theta*i);
			CoordinateTransformation.RTFtoXYZ(points[i+1]);
			points[i+1].move(pos.x,pos.y,pos.z);
		}
		points[num+1] = new mPoint3(pos,0,0,height);
		
		short[][] lp = new short[2][num*2];
		for(short i=0;i<num;i++){
			lp[0][i] = (short)(num+1);
			lp[1][i] = (short)(i+1);
			lp[0][num+i] = (short)(i+1);
			lp[1][num+i] = (short)(i+2);
		}
		lp[1][num*2-1] = 1;
		short[][] tp = new short[3][num*2];
		for(short i=0;i<num;i++){
			tp[0][i] = (short)(num+1);
			tp[1][i] = (short)(i+1);
			tp[2][i] = (short)(i+2);
			tp[0][i+num] = 0;
			tp[1][i+num] = (short)(i+2);
			tp[2][i+num] = (short)(i+1);
		}
		tp[2][num-1] = 1;
		tp[1][2*num-1] = 1;
		shape.set(points,lp,tp);
		this.center = new mVector3(0,0,height/2);
		this.refer = new mReferenceTransformation(new mPoint3(pos,center),
			new mVector3(0,1,0),
			new mVector3(1,0,0));
	}	
}
