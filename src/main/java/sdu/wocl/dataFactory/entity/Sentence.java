package sdu.wocl.dataFactory.entity;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.dataFactory.entity.wordtree.DoubleTreeNode;
import sdu.wocl.dataFactory.entity.wordtree.WordTree;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeNode;

/**
 * 句子
 * @author ljh_2015
 *
 */
public class Sentence {

    private final static Logger logger = LoggerFactory.getLogger(Sentence.class);
    //用以指向当前节点
    private WordTreeNode key;
    private int id;//句子标识
    private List<Word> words = null;

    public WordTree WORDTREE;

    /**
     * 语句树构造
     * @param ws
     */
    public Sentence(List<Word> ws,int sentid) {
	this.id = sentid;
	this.words = ws;
	WORDTREE = new WordTree(this.id);
	WORDTREE.create(words);
	//logger.debug("create one tree from a sentence");
    }

    public Sentence(List<Word> ws) {
	this.words = ws;
	WORDTREE = new WordTree(this.id);
	WORDTREE.create(words);
	logger.debug("start a tree of one sentence");
    }
    
    /**
     * 获取句子长度
     * @return
     */
    public int getLength() {
	return words.size();
    }

    public Word getWord(int i) {
	for(Word word:words) {
	    if(i==words.get(i).getId())
		return word;
	}
	return null;
    }

    public void setKey(WordTreeNode node) {
	key = node;
    }

    /**
     * 
     */
    public String readNode() {
	if(key!=null)
	    return key.read();
	else
	    System.err.println("please set your keyNode before read");
	return null;
    }

    public void readNodeLeft() {
	for(DoubleTreeNode<Word> node:key.getLeft()) {
	    setKey((WordTreeNode) node);
	    readNode();
	}
    }

    public String loadStyle() {
	setKey(this.WORDTREE.ROOT);
	return readNode();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getTextString() {
	StringBuilder str = new StringBuilder();
	for(Word w:words) {
	    str.append(w.getText());
	}
	return str.toString();
    }
}
