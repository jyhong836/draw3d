/**
 * 
 */

/**
 * mSurface is the surface instance of mObject
 * @author jyhong
 *
 */
public class mSurface extends mObject {
	/**
	 * 
	 * @param points:surface points,should be organized by x&y arised,
	 */
	public mSurface(mPoint3 surfp[][], int xsize, int ysize) {
		this.pos = surfp[0][0];
		this.shape = new mShape();
		mPoint3[] points = new mPoint3[xsize*ysize];
		for (int i=0;i<ysize;i++){
			for (int j=0;j<xsize;j++){
				points[i*xsize+j] = surfp[j][i];
			}
		}
		short[][] linepoints = new short[2][0];
		short[][] tripoints = new short[3][xsize*ysize*2];
		for (int i = 0; i < ysize-1; i++) {
			for (int j = 0; j < xsize-1; j++) {
				tripoints[0][2*(i*xsize+j)  ]=(short)(i*xsize+j);
				tripoints[0][2*(i*xsize+j)+1]=(short)(i*xsize+j);
				tripoints[1][2*(i*xsize+j)  ]=(short)((i+1)*xsize+(j+1));
				tripoints[1][2*(i*xsize+j)+1]=(short)(i*xsize+j+1);
				tripoints[2][2*(i*xsize+j)  ]=(short)((i+1)*xsize+j);
				tripoints[2][2*(i*xsize+j)+1]=(short)((i+1)*xsize+j+1);
			}
		}
		this.shape.set(points,linepoints,tripoints);
		this.center = new mVector3(0,0,0);
		this.refer = new mReferenceTransformation(new mPoint3(pos,center),
			new mVector3(0,1,0),
			new mVector3(1,0,0));
	}

}
