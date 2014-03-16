/**
 * class extends mMath ,will use some method defined in mMath
 *
 */
public class mReferenceTransformation extends mMath{
	
	/*
	 * reference Transformation
	 *
	 */
	private mPoint3 pos;                        //TODO: if it is used as center, you need to reset it when use it
	private mVector3 refer_v = null;            //reference vector ,point to forward(y)
	private mVector3 refer_t = null;            //reference vector ,point to right  (x)
	private mVector3 refer_n = null;            //reference vector ,point to up     (z)
	
	
	public mReferenceTransformation (mPoint3 pos,mVector3 v,mVector3 t){
		this.pos = pos;
		createReference (v,t);
	}
	
	public void setCenter(mPoint3 p){
		this.pos = p;
	}
	
	public mPoint3 getCenter(){
		return pos;
	}
	
	/**
	 * this method is to transform the p from old reference to new reference(has been setted)
	 *
	 */
	public mPoint3 referenceTransform (mPoint3 p){
	//	System.out.println("old  "+p.toString());
		mVector3 op = new mVector3(pos,p);
		mVector3 np = new mVector3();
	//	System.out.println("rrr  "+op.toString());
		np.y = vectorMutiple(op,refer_v);
		np.x = vectorMutiple(op,refer_t);
		np.z = vectorMutiple(op,refer_n);
	//	System.out.println("rrr  "+np.toString());
		return new mPoint3(np);
	}
	/**
	 * transform the p from current reference to old reference
	 *
	 */
	public mPoint3 antiReferenceTransform (mPoint3 p){
		mVector3 op = mMath.vectorPlus(mMath.vectorNumMultiple(refer_v,p.y),mMath.vectorNumMultiple(refer_t,p.x));
		op = mMath.vectorPlus(op,mMath.vectorNumMultiple(refer_n,p.z));
		return new mPoint3(pos,op);
	}
	
	/**This method is not recommended to use
	 * there is not examination for wrong reference vectors
	 *
	 */
	public void setReference (mVector3 v ,mVector3 t ,mVector3 n){
		refer_v = v;
		refer_t = t;
		refer_n = n;
	}
	
	//recommend set reference by createReference
	public void createReference (mVector3 v,mVector3 t){
		unitify(v);
		refer_v = v;
		createRightVector(v,t);
		refer_t = t;
		refer_n = multiplicationCross(t,v);
		//--------test---
		System.out.println("n "+refer_n.toString()+"t "+refer_t.toString()+"v "+refer_v.toString());
		//---------------
	}
	
	/**
	 * require v is unit vector3
	 *
	 */
	public void createRightVector(mVector3 v,mVector3 t) {
		float len = vectorMutiple(v,t);
		t.set(vectorMinus(t,new mVector3(v.x*len,v.y*len,v.z*len)));
		unitify(t);
		
		//---------test---
		//System.out.println("v"+v.toString()+"t "+t.toString());
		//---------end----
		//---------test---
		//System.out.println("t "+t.toString());
		//---------end----
	}
	
	/**
	 * rotate the refer_v ,t, n
	 * rotate order is txy,txz,tyz
	 * @param txy,tzx,tyz :the axis is the other axis, for instance,txy 's axis is z
	 * use the method rotateVector  
	 */
	public void rotate(float txy,float tzx,float tyz){
		this.rotateVector(refer_v,txy,tzx,tyz);
		this.rotateVector(refer_t,txy,tzx,tyz);
		refer_n = mMath.multiplicationCross(refer_t,refer_v);
	}
	private void rotateVector(mVector3 v,float txy,float tzx,float tyz){
		float[] p ={v.x,v.y,v.z};
		
		CoordinateTransformation.XYZtoRTF(p);
		p[2] += txy;
		CoordinateTransformation.RTFtoXYZ(p);
		v.set(p);
		
		p[0] = v.z;p[1] = v.x;p[2] = v.y;
		CoordinateTransformation.XYZtoRTF(p);
		p[2] += tzx;
		CoordinateTransformation.RTFtoXYZ(p);
		v.set(p[1],p[2],p[0]);
		
		p[0] = v.y;p[1] = v.z;p[2] = v.x;
		CoordinateTransformation.XYZtoRTF(p);
		p[2] += tyz;
		CoordinateTransformation.RTFtoXYZ(p);
		v.set(p[2],p[0],p[1]);
	}
}
