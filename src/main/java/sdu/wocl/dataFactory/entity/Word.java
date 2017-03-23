package sdu.wocl.dataFactory.entity;

public class Word {
    
  //词语在句子之中的id顺序
    private int id;
    //词语当前的词性
    private Pos pos;
    //词语的内容
    private String text;
    //是否为当前句子的核心位置
    private boolean isHed;
    //中心修饰词语的id
    private int parentid;
    //语义中心词
    private int semparent;
    //语义依存关系
    private String semrelate;
    //句子标识
    private int sentid;
    
    private String rel;

    private Word parent;

    public Word() {

    }

    /**
     * 
     * @param id 标识
     * @param text 词条
     * @param pos 词性
     * @param parentid 父亲标识
     * @param relate 关系
     * @param rel 关系
     * @param semid 语义依存父词
     * @param semrelate 语义依存关系
     */
    public Word(int sentid,int id,String text,Pos pos,int parentid,String rel,int semid,String semrelate) {
	this.sentid = sentid;
	this.id = id;
	this.text = text;
	this.pos = pos;
	this.parentid = parentid;
	this.rel = rel;
	this.semparent = semid;
	this.semrelate = semrelate;
	if(rel == "HED") 
	    this.isHed = true;
	else
	    this.isHed = false;
    }

    /**
     * 
     * @param id 标识
     * @param text 词条
     * @param pos 词性
     * @param parentid 父亲标识
     * @param relate 关系
     * @param rel 关系
     */
    public Word(int id,String text,Pos pos,int parentid,String rel,int semid,String semrelate) {
	this.id = id;
	this.text = text;
	this.pos = pos;
	this.parentid = parentid;
	this.rel = rel;
	this.semparent = semid;
	this.semrelate = semrelate;
	if(rel == "HED") 
	    this.isHed = true;
	else
	    this.isHed = false;
    }

    public int getId() {
	return id;
    }

    public double getPosValue() {
	if(getPos()!=null) 
	    return pos.getIndex();
	else
	    return 1.0;
    }

    public Pos getPos() {
	return pos;
    }

    public boolean isHed() {
	return isHed;
    }

    public int getParentid() {
	return parentid;
    }

    public String getText() {
	return text;
    }

    public void setParent(Word p) {
	this.parent = p;
    }

    public Word getParent() {
	return this.parent;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void setText(String text) {
	this.text = text;
    }

    public void setRel(String rel) {
	this.rel = rel;
    }

    public void setPos(Pos pos) {
	this.pos = pos;
    }

    public String getRelates() {
	return rel;
    }

    public boolean isFatherFriend() {
	if(rel.toLowerCase().equalsIgnoreCase("sim") || 
		rel.toLowerCase().equalsIgnoreCase("app") ||
		rel.toLowerCase().equalsIgnoreCase("coo") ||
		rel.toLowerCase().equalsIgnoreCase("vv") ){
	    return true;
	} 
	return false;
    }

    @Override
    public boolean equals(Object obj) {
	if(obj instanceof Word &&  this.id == ((Word)obj).getId()) {
	    return true;
	}
	return false;
    }

    public int getSemparent() {
	return semparent;
    }

    public void setSemparent(int semparent) {
	this.semparent = semparent;
    }

    public String getSemrelate() {
	return semrelate;
    }

    public void setSemrelate(String semrelate) {
	this.semrelate = semrelate;
    }
    
    public String toSqlInsertString() {
	String sql="insert into _tword(wordid,text,pos,parentid,rel,semid,semrel,sentid) values ("
		+id+",'"+text+"','"+pos.name()+"',"
		+parentid+",'"+rel+"',"+semparent+",'"+semrelate+"',"+sentid+")";
	System.out.println(sql);
	return sql;
    }
    
}
