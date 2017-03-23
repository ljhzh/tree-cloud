package sdu.wocl.algorithm.view;

public class XYPoint {

    private String type;
    private String title;
    private Data data;

    class Data {
	private double value;
	private double similarity;

	public Data(double value,double similarity) {
	    this.value = value;
	    this.similarity = similarity;
	}
	
	public double getSimilarity() {
	    return similarity;
	}
	public double getValue() {
	    return value;
	}
    }
    
    
    public Data getData() {
        return data;
    }
    public void setData(double x,double y) {
        this.data = new Data(x,y);
    }
    public String getType() {
	return type;
    }
    public void setType(String type) {
	this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
