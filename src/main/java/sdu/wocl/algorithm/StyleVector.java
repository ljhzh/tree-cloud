package sdu.wocl.algorithm;

/**
 * 句式维度
 * @author ljh_2015
 *
 */
public class StyleVector {
    
    private int[] vectors = new int[417];
    private int[] unCountV = new int[417];
    
    public void countVec(int i) {
	if(i==-1) {
	    vectors[0]++;
	    unCountV[0]++;
	}
	vectors[i+1]++;
	unCountV[i+1]=1;
    }
    
    public int[] getVector() {
	return vectors;
    }
    
    public int[] getUnCountVector() {
	return unCountV;
    }
    
    /**
     * 欧式距离
     * @return
     */
    public double euclidean(StyleVector s) {
	int[] dest = s.getVector();
	int sum = 0;
	for (int i = 0; i < vectors.length; i++) {
	    sum+=Math.pow((vectors[i]-dest[i]),2);
	}
	return Math.sqrt(sum);
    }
    
    public int[] mix(StyleVector s) {
	int[] dest = getUnCountVector();
	for (int i = 0; i < dest.length; i++) {
	    dest[i]+=unCountV[i];
	}
	return dest;
    }
    
    public double getSelfDistance() {
	double a = 0;
	for (int i = 0; i < vectors.length; i++) {
	    a+=Math.pow(vectors[i],2);
	}
	return Math.sqrt(a);
    }
    
    public void merge(StyleVector me) {
	for (int i = 0; i < vectors.length; i++) {
	    vectors[i]+=me.getVector()[i];
	    unCountV[i]+=me.getUnCountVector()[i];
	}
    }
}
