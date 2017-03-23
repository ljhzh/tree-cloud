package sdu.wocl.dataFactory.entity;

public class Article {

    private String doc_title;
    private String context;
    private String type;
    private String date;
    private String path;
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDoc_title() {
	return doc_title;
    }
    public void setDoc_title(String doc_title) {
	this.doc_title = doc_title;
    }
    public String getContext() {
	return context;
    }
    public void setContext(String context) {
	this.context = context;
    }
    public String getPath() {
	return path;
    }
    public void setPath(String path) {
	this.path = path;
    }

}
