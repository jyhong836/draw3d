/**
 * @(#)mCamera.java
 *
 * class 3D Draw project
 *
 * @author jyhong  
 * the param must be init ;refer ,can ,center , shape .
 */
 
import java.util.Vector;

public class mCamera extends mObject {
	/**mCanvas save the canvas to draw
	 * eye save the relative position between eye and screen
	 */
	private mCanvas can;
	private float eye = -512;
	
	/**
	 * Method mCamera
	 * 
	 * @param can ,init can
	 * @param refer ,init refer
	 */
	public mCamera(mCanvas can ,mReferenceTransformation refer) {
		this.can = can;
		this.refer = refer;
		this.center = new mVector3(pos,new mPoint3(0,0,0));
		this.shape = new mShape();
		mPoint3[] p = {pos};
		shape.set(p,null,null);
		can.setDeep(20-eye);
	}	
	/**
	 * Method mCamera
	 * 
	 * @param can ,init can
	 * refer will be init by default
	 */
	public mCamera(mCanvas can){
		this.can = can;
		this.pos = new mPoint3(100,-100,100);
		mVector3 v = new mVector3(pos,new mPoint3(0,0,0));
		refer = new mReferenceTransformation(this.pos,v,
			mMath.multiplicationCross(v,new mVector3(0,0,1)));
		center = new mVector3(pos,new mPoint3(0,0,0));
		this.shape = new mShape();
		mPoint3[] p = {pos};
		shape.set(p,null,null);
		can.setDeep(20-eye);
	}  
	
	/**
	 * Method capture
	 * @param world ,the world will be draw
	 * @param drawType ,the draw type ,the value should be one of below three
	 *   the method drawObject or method drawObjectLine be called according to
	 *   the drawType
	 */
	public static final short DRAW_TYPE_FILL = 0;
	public static final short DRAW_TYPE_LINE = 1;
	public static final short DRAW_TYPE_FILL_LINE = 2;
	public void capture(mWorld world,short drawType){
		Vector obj = world.objects;
		int size = obj.size();
		if(!obj.isEmpty()){
			while((--size)>=0){
				switch(drawType){
					case DRAW_TYPE_FILL:drawObject((mObject)obj.elementAt(size));break;
					case DRAW_TYPE_LINE:this.drawObjectLine((mObject)obj.elementAt(size));break;
					case DRAW_TYPE_FILL_LINE:
					default:drawObject((mObject)obj.elementAt(size));this.drawObjectLine((mObject)obj.elementAt(size));
				}
				
			}
		}
	}
	/**
	 * Method capture
	 * @param world ,the world will be draw
	 *,the draw type ,will be default set as DRAW_TYPE_FILL
	 */
	public void capture(mWorld world){
		Vector obj = world.objects;
		int size = obj.size();
		// ####test
//		System.out.println("capture::draw object:"+obj);
		if(!obj.isEmpty()){
			while((--size)>=0){
				this.drawObject((mObject)obj.elementAt(size));
			}
		}
	}
	
	private void drawObject(mObject obj){
		mPoint3[] p = obj.shape.points;
		short[][] tp = obj.shape.tripoints;
		short[][] lp = obj.shape.linepoints;
		int len = tp[0].length;
		//*
		for(int i=0;i<len;i++){
			
			mPoint3[] tri3 = {refer.referenceTransform(p[tp[0][i]]),
				refer.referenceTransform(p[tp[1][i]]),
				refer.referenceTransform(p[tp[2][i]])};
			/** 如果三角形的法向量方向相反则不作任何处理
			 */
			if(mMath.getNVectorY(tri3)>0){
			//	System.out.println("0000"+tri3[0]);
				continue;
			}
			/*
			else {
				System.out.println("1111"+tri3[0]);
				System.out.println("1112"+tri3[1]);
				System.out.println("1113"+tri3[2]);
				System.out.println(mMath.getNVectorY(tri3));
			}//*/
			
			//----------ADD:lihgt calculate-----------
			float light[] = new float[3];
			if((light[0] = mMath.getHightLightZ(tri3))<0)
				light[0] = 0;
		//	System.out.println("p "+tri3[0]);
			mPoint3[] plight1 = {tri3[1],tri3[2],tri3[0]};
			if((light[1] = mMath.getHightLightZ(plight1))<0)
				light[1] = 0;
			mPoint3[] plight2 = {tri3[2],tri3[0],tri3[1]};
			if((light[2] = mMath.getHightLightZ(plight2))<0)
				light[2] = 0;
		//	System.out.println("0 "+light[0]+" 1 "+light[1]+" 2 "+light[2]);
			//*///------------end :lihgt  ----------------
			
			mPoint[] tri = {
				projection(tri3[0]),
				projection(tri3[1]),
				projection(tri3[2])
				};
			for(int j=0;j<3;j++){
				tri[j].light = light[j];
			}
			can.fillTriangle(tri);
			//-------------
		//	System.out.println("fill triangle "+i+" of "+obj.toString());
			//---------
		}
		if(len==0){
			drawObjectLine(obj);
		}
		/*/
		len = lp[0].length;
		//*
		int rgb = can.getCurcolor();
		can.setCurcolor(0xff00ff00);
		for(int i=0;i<3;i++){
		//	System.out.println(projection(refer.referenceTransform(p[lp[0][i]])));
			can.drawLine(projection(refer.referenceTransform(p[lp[0][i]])),
				projection(refer.referenceTransform(p[lp[1][i]])) );
		}
		can.setCurcolor(rgb);
		//*/
	}
	
	private void drawObjectLine(mObject obj){
		mPoint3[] p = obj.shape.points;
		short[][] tp = obj.shape.tripoints;
		short[][] lp = obj.shape.linepoints;
		int len = tp[0].length;
		len = lp[0].length;
		int rgb = can.getCurcolor();
		can.setCurcolor(0xff00ff00);                  //setCurcolor :all line will be set color green
		for(int i=0;i<len;i++){
		//	System.out.println(projection(refer.referenceTransform(p[lp[0][i]])));
			can.drawLine(projection(refer.referenceTransform(p[lp[0][i]])),
				projection(refer.referenceTransform(p[lp[1][i]])) );
		}
		can.setCurcolor(rgb);
	}
	
	public void setEye(float e){
		this.eye = e;
	}
	
	/*投影映射projection
	 *
	 * p3.x -> p.x
	 * p3.z -> -p.y
	 * p3.y -> p.z
	 */
	
	private mPoint projection(mPoint3 p3){
		float d = p3.y - eye;
		float l = 0-eye;
		float x = p3.x*l/d;
		float y = -p3.z*l/d;
		x += can.width/2;
		y += can.height/2;
		mPoint p = new mPoint((int)x,(int)y,p3.y,1);
		//--------
	//	System.out.println(p3.toString());
	//	System.out.println(p.toString());
		//---------
		return p;
	}
}
