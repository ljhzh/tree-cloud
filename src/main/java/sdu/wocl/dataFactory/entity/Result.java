package sdu.wocl.dataFactory.entity;

public class Result {
    
    private String hed;
    private String resultContext;
    private String resultStyle;
    private String resultPos;
    private String resultNum;
    
    public Result(String hed,String resultContext,String resultStyle,String resultPos,String resultNum) {
	this.hed = hed;
	this.resultContext = resultContext;
	this.resultStyle = resultStyle;
	this.setResultPos(resultPos);
	this.resultNum = resultNum;
    }
   
    public String getResultContext() {
        return resultContext;
    }
    public void setResultContext(String resultContext) {
        this.resultContext = resultContext;
    }
    public String getResultStyle() {
        return resultStyle;
    }
    public void setResultStyle(String resultStyle) {
        this.resultStyle = resultStyle;
    }
    public String getResultNum() {
        return resultNum;
    }
    public void setResultNum(String resultNum) {
        this.resultNum = resultNum;
    }
    public String getHed() {
	return hed;
    }
    public void setHed(String hed) {
	this.hed = hed;
    }

    public String getResultPos() {
	return resultPos;
    }

    public void setResultPos(String resultPos) {
	this.resultPos = resultPos;
    }
    
    
    
}
