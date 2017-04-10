package sdu.wocl.dataFactory.entity.wordtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataFactory.entity.Word;
import sdu.wocl.dataFactory.entity.Wp;

/**
 * 语句树
 * 语句束中有很多的节点
 * @author ljh_2015
 *
 */
public class WordTree implements TreeImp<Word>,Serializable {

    /**
     * treeid
     */
    private int treeid;

    /**
     * 节点数组,其他元素
     */
    private List<WordTreeNode> nodes = new ArrayList<WordTreeNode>();
    private List<Word> word = new ArrayList<Word>();

    /**
     * 根节点
     */
    public WordTreeNode ROOT;

    public WordTree(int treeid) {
	this.treeid = treeid;
    }

    /**
     * 节点与树转换
     * @param root
     */
    public WordTree(WordTreeNode root) {
	ROOT = root;
    }

    public List<DoubleTreeNode<Word>> getCoos() {
	return ROOT.getRight();
    }

    /**
     * 将所有元素构造成节点
     */
    @Override
    public void create(List<Word> elements) {
	//临时节点
	boolean flag = true;
	WordTreeNode node = null;
	word= elements;
	for(Word w:elements) {
	    node = new WordTreeNode(w);
	    if(w.getRelates().equalsIgnoreCase("hed")) {
		ROOT = node;
	    } else
		nodes.add(node);
	}
	while(flag) {
	    int count = 0;
	    List<WordTreeNode> nods = new ArrayList<WordTreeNode>();
	    nods.addAll(nodes);
	    for(WordTreeNode n:nods) {
		if(put(ROOT,n)){
		    count++;
		}
	    }
	    if(count==0) {
		flag = false;
	    }
	}
    }

    @Override
    public boolean put(DoubleTreeNode<Word> parent,DoubleTreeNode<Word> Dtree) {
	// TODO Auto-generated method stub
	if(merge(parent,Dtree)) {
	    nodes.remove(Dtree);
	    return true;
	}
	return false;
    }

    @Override
    public Word getElement(int id) {
	return ROOT.find(id).t;
    }

    @Override
    public boolean read(Word t, double mark, int deep) {
	return false;
    }

    @Override
    public boolean merge(DoubleTreeNode<Word> parent, DoubleTreeNode<Word> son) {
	//拿到父节点的id;
	int id = son.t.getParentid();	

	/**
	 * 处理wp标点符号
	 */
	if(son.t instanceof Wp)  {
	    id = son.t.getSemparent();
	}

	DoubleTreeNode<Word> temp = parent.find(id);
	if(temp!=null) {
	    if(son.t.isFatherFriend()) {
		temp.putNodeRight(son);
	    } else {
		temp.putNodeLeft(son);
	    }
	    return true;
	} else
	    return false;
    }


    public WordTreeNode getRoot() {
	return ROOT;
    }

    public List<WordTreeNode> getOtherElement() {
	return nodes;
    }

    public List<Word> getWord() {
	return word;
    }

    /**
     * 获取源数据
     * @return
     */
    public String getTextString() {
	StringBuilder str = new StringBuilder();
	for(Word w:word) {
	    str.append(w.getText());
	}
	return str.toString();
    }

    public void read() {
	ROOT.read();
    }

    public void setRoot(WordTreeNode root) {
	this.ROOT = root;
    }

    public int getTreeId() {
	return treeid;
    }

    /**
     * 获取当前节点信息
     * @return
     */
    public WordTreeMessage getMessage() {
	return new WordTreeMessage(this);
    }

    public List<WordTreeMessage> getMessages() {
	return getMessages(ROOT);
    }

    public List<WordTreeMessage> getMessages(WordTreeNode node) {
	List<WordTreeMessage> messages = null;
	if(node.getRightNode().size()>0) {
	    messages = new ArrayList<WordTreeMessage>();
	    for(WordTreeNode nod:node.getRightNode()) {
		messages.add(nod.getMessage());
	    }
	}
	return messages;
    }

    @Override
    public void loadResult(DoubleTreeNode<Word> dtree) {
	// TODO Auto-generated method stub
	
    }


}