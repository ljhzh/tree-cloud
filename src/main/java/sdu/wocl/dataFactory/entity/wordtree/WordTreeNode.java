package sdu.wocl.dataFactory.entity.wordtree;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataFactory.entity.Word;
import sdu.wocl.dataFactory.entity.wordtree.tools.DataBaseCommand;

/**
 * 语句树
 * @author ljh_2015
 *
 */
public class WordTreeNode extends DoubleTreeNode<Word> {

    private static final DataBaseCommand dbc = new DataBaseCommand();
    private List<Word> node = new ArrayList<Word>();
    private String rel;
    private String pos;

    public WordTreeNode(){}
    /**
     * 语句树节点构造函数
     * 初始值 num = 1,deep = 1 孩子节点，父节点均为空
     * 判断该节点是否为根节点
     * @param w 由Word所构造的WordTreeNode 单节点
     */
    public WordTreeNode(Word w) {
	//节点编号
	this.id = w.getId();
	//将整个词语作为信息元素
	this.t = w;

	this.rel = w.getRelates();

	this.pos = w.getPos().name();
    }

    /**
     * 获取编号
     * @return
     */
    public String getStringId() {
	return pos+"["+rel+getNum()+"]";
    }

    public String getId() {
	return String.valueOf(this.id);
    }

    /**
     * 获取细则编号
     * @return
     */
    public String getString() {
	String result = rel+"("+pos+"{";
	if(getLeft().size()>0) {
	    for(int i=0;i<getLeft().size();i++) {
		WordTreeNode n = (WordTreeNode) getLeft().get(i);
		result+=n.getStringId();
		if(i!=getLeft().size()-1)
		    result+="_";
	    }
	} else
	    result+="0";
	result+="})__{";
	if(getRight().size()>0) {
	    for(int i=0;i<getRight().size();i++) {
		WordTreeNode n = (WordTreeNode) getRight().get(i);
		result+=n.getStringId();
		if(i!=getRight().size()-1)
		    result+="_";
	    }
	} else
	    result+="0";
	result+="}";
	return result;
    }

    public String read() {
	System.out.println(getString());
	return getString();
    }

    public List<String> getLeftStringWord() {
	List<String> temp = new ArrayList<String>();
	List<DoubleTreeNode<Word>> left = this.left;
	for(DoubleTreeNode<Word> dnode:left) {
	    temp.add(dnode.getElement().getText()+"_"+(dnode.getElement().getId()+1)+"_"+dnode.getElement().getPos().toString());
	}
	return temp;
    }

    public String getLeftWordNum() {
	String num="[";
	List<DoubleTreeNode<Word>> left = this.left;
	for(DoubleTreeNode<Word> dnode:left) {
	    num+=dnode.getNum()+",";
	}
	if(num.length()<2) {
	    return "[]";
	}
	return num.substring(0,num.length()-1)+"]";
    }

    public String getRightWordNum() {
	String num="[";
	List<DoubleTreeNode<Word>> right = this.right;
	for(DoubleTreeNode<Word> dnode:right) {
	    num+=dnode.getNum()+",";
	}
	if(num.length()<2) {
	    return "[]";
	}
	return num.substring(0,num.length()-1)+"]";
    }

    public List<String> getRightStringWord() {
	List<String> temp = new ArrayList<String>();
	List<DoubleTreeNode<Word>> right = this.right;
	for(DoubleTreeNode<Word> dnode:right) {
	    temp.add(dnode.getElement().getText()+"_"+dnode.getElement().getId()+"_"+dnode.getElement().getPos().toString());
	}
	return temp;
    }

    public String getLeftStyle() {
	String style = "";
	List<DoubleTreeNode<Word>> left = this.left;
	for(DoubleTreeNode<Word> dnode:left) {
	    style+="_"+dnode.getElement().getRelates();
	}

	if(style!=null && !style.equals(""))
	    return style.substring(1);
	return " ";
    }

    public String getLeftPos() {
	String pos="[";
	List<DoubleTreeNode<Word>> left = this.left;
	for(DoubleTreeNode<Word> dnode:left) {
	    pos+=dnode.getElement().getPos().toString()+",";
	}

	if(pos.length()<2) {
	    return "[]";
	}
	return pos.substring(0,pos.length()-1)+"]";
    }

    public String getRightPos() {
	String pos="[";
	List<DoubleTreeNode<Word>> right = this.right;
	for(DoubleTreeNode<Word> dnode:right) {
	    pos+=dnode.getElement().getPos().toString()+",";
	}

	if(pos.length()<2) {
	    return "[]";
	}
	return pos.substring(0,pos.length()-1)+"]";
    }

    public String getRightStyle() {
	String style = "";
	List<DoubleTreeNode<Word>> right = this.right;
	for(DoubleTreeNode<Word> dnode:right) {
	    style+="_"+dnode.getElement().getRelates();
	}
	if(style!=null && !style.equals(""))
	    return style.substring(1);
	return " ";
    }

    public WordTreeMessage getMessage() {
	return new WordTreeMessage(this);
    }
    @Override
    public List<WordTreeNode> getLeftNode() {
	List<DoubleTreeNode<Word>> db = this.getLeft();
	List<WordTreeNode> lefts = new ArrayList<WordTreeNode>();
	for (DoubleTreeNode<Word> doubleTreeNode : db) {
	    lefts.add((WordTreeNode)doubleTreeNode);
	}
	return lefts;
    }
    @Override
    public List<WordTreeNode> getRightNode() {
	List<DoubleTreeNode<Word>> db = this.getRight();
	List<WordTreeNode> rights = new ArrayList<WordTreeNode>();
	for (DoubleTreeNode<Word> doubleTreeNode : db) {
	    rights.add((WordTreeNode)doubleTreeNode);
	}
	return rights;
    }
}
