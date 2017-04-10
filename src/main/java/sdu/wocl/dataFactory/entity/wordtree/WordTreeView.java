package sdu.wocl.dataFactory.entity.wordtree;

import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataFactory.entity.Word;

public class WordTreeView {

    private WordTreeNode root;
    private List<ViewNode> nodes = new ArrayList<ViewNode>();
    private List<ViewLine> lines = new ArrayList<ViewLine>();
    
    class ViewLine {
	//子树编号
	private int gid;
	//节点
	private int nid;
    }
    
    class ViewNode {
	//节点位置
	private int id;
	
	private int parentid;
	
	private String pos;
	
	private String relate;
	
	private double r;
	
	private boolean isNull;
	
	private boolean isMix;
	
	//子数组结点
	public ViewNode(int parentid) {
	    this.setNull(false);
	    this.setParentid(parentid);
	    this.setMix(true);
	}
	
	public ViewNode(WordTreeNode node) {
	    this.id = node.id;
	    this.parentid = node.parentid;
	    this.pos = node.getPos();
	    this.relate = node.getPos();
	    this.r = node.getNum();
	    this.isNull = false;
	    this.setMix(false);
	}

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}

	public int getParentid() {
	    return parentid;
	}

	public void setParentid(int parentid) {
	    this.parentid = parentid;
	}

	public String getPos() {
	    return pos;
	}

	public void setPos(String pos) {
	    this.pos = pos;
	}

	public String getRelate() {
	    return relate;
	}

	public void setRelate(String relate) {
	    this.relate = relate;
	}

	public double getR() {
	    return r;
	}

	public void setR(double r) {
	    this.r = r;
	}

	public boolean isNull() {
	    return isNull;
	}

	public void setNull(boolean isNull) {
	    this.isNull = isNull;
	}

	public boolean isMix() {
	    return isMix;
	}

	public void setMix(boolean isMix) {
	    this.isMix = isMix;
	}
    }
    
    public WordTreeView(WordTreeNode root) {
	this.root = root;
    }
    
    public void createViewTree() {
	push(root);
    }
    
    public void push(WordTreeNode node) {
	if(node!=null) {
	    nodes.add(new ViewNode(node));
	    //左子树组
	    nodes.add(new ViewNode(node.id));
	    //右子树组
	    nodes.add(new ViewNode(node.id));
	   
	}
	for(int i=0;i<getLeftGroup(node).size();i++) {
	    push((WordTreeNode) getLeftGroup(node).get(i));
	}
	
	for(int i=0;i<getRightGroup(node).size();i++) {
	    push((WordTreeNode) getRightGroup(node).get(i));
	}
    }
    
    private List<DoubleTreeNode<Word>> getLeftGroup(WordTreeNode node) {
	return node.getLeft();
    }
    
    private List<DoubleTreeNode<Word>> getRightGroup(WordTreeNode node) {
	return node.getRight();
    }
    
}
