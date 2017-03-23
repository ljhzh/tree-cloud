package sdu.wocl.dataFactory.entity.wordtree;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sdu.wocl.dataFactory.entity.Word;
import sdu.wocl.dataFactory.entity.wordtree.tools.DataBaseCommand;
import sdu.wocl.dataFactory.entity.wordtree.tools.ExchangeWordTree;
import sdu.wocl.dataFactory.entity.wordtree.tools.Simplification;

/**
 * 数据信息集
 * @author ljh_2015
 *
 */
public class WordTreeMessage extends Message {

    /**
     * 数据表达类
     * @author ljh_2015
     *
     */
    public class MessageGroup {
	
	//句式
	private String style;
	
	//词性
	private String pos;
	
	//数量
	private String num;
	
	//原词
	private String word;

	public MessageGroup(String style,String pos,String num) {
	    this.style = style;
	    this.pos = pos;
	    this.num = num;
	}
	
	public String getStyle() {
	    return style;
	}

	public void setStyle(String style) {
	    this.style = style;
	}

	public String getPos() {
	    return pos;
	}

	public void setPos(String pos) {
	    this.pos = pos;
	}

	public String getNum() {
	    return num;
	}

	public void setNum(String num) {
	    this.num = num;
	}

	public String getWord() {
	    return word;
	}

	public void setWord(String word) {
	    this.word = word;
	}
    }
    
    private MessageGroup left;
    
    private MessageGroup right;
    
    /**
     * 谓核
     */
    private String Hed;    
    /**
     * 简约句式
     */
    private String[] leftSimpleStyle;
    
    private String[] rightSimpleStyle;
    /**
     * 继承词列
     */
    private String leftWord;
    /**
     * 并列词列
     */
    private String rightWord;
    
    
    public WordTreeMessage(WordTree tree) {
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	id = tree.getTreeId();
	context = tree.getTextString();
	now = df.format(date);
	type = "wordTree Message";
	window = "analyse.html";
	Hed = tree.ROOT.getElement().getText()+" "+(tree.ROOT.getElement().getId()+1)+" "+tree.ROOT.getElement().getPos().toString();
	load(tree.ROOT);
    }
    
    public WordTreeMessage(WordTreeNode wn) {
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	now = df.format(date);
	type = "wordTreeNode Message";
	window = "analyse.html";
	load(wn);
    }
    
    public void load(WordTreeNode w) {
	left = new MessageGroup(w.getLeftStyle(), w.getLeftPos(), w.getLeftWordNum());
	right = new MessageGroup(w.getRightStyle(),w.getRightPos(),w.getRightWordNum());
	leftWord="";
	rightWord="";
	for(String temp:w.getLeftStringWord())
	    leftWord+=temp+" ";
	left.setWord(leftWord);
	for(String temp:w.getRightStringWord())
	    rightWord+=temp+" ";
	leftSimpleStyle = Simplification.simpliStyle(left);
	rightSimpleStyle = Simplification.simpliStyle(right);
    }
    
    public void load(List<DoubleTreeNode<Word>> ws) {
	for(int i=0;i<ws.size();i++) {
	    WordTreeNode w = (WordTreeNode) ws.get(i);
	    load(w);
	}
    }
    
    public String getContext() {
	return context;
    }

    public String getHed() {
        return Hed;
    }

    public String getLeftWord() {
        return leftWord;
    }

    public String getRightWord() {
        return rightWord;
    }
    
    public String getNow() {
	return now;
    }
    
    public String getType() {
	return type;
    }
    
    public String getWindow() {
	return window;
    }

    public String[] getLeftSimpleStyle() {
	return leftSimpleStyle;
    }

    public void setLeftSimpleStyle(String[] leftSimpleStyle) {
	this.leftSimpleStyle = leftSimpleStyle;
    }

    public String[] getRightSimpleStyle() {
	return rightSimpleStyle;
    }

    public void setRightSimpleStyle(String[] rightSimpleStyle) {
	this.rightSimpleStyle = rightSimpleStyle;
    }

//    public void save() {
//	DataBaseCommand dbc = new DataBaseCommand();
//	String fstb = "insert into style_fstb values('"+id+"','"+Hed+"',"+leftStyle+"','"+leftPos+"','"+leftNum+"','"+rightStyle+"','"+rightPos+"','"+rightNum+"')";
//	String stb="insert into style_stb values('"+id+"','"+Hed+"',"+leftSimpleStyle[0]+"','"+leftSimpleStyle[1]+"','"+leftSimpleStyle[2]+"','"+rightSimpleStyle[0]+"','"+rightSimpleStyle[1]+"','"+rightSimpleStyle[2]+"')";
//	dbc.setCommand(fstb);
//	dbc.setCommand(stb);
//	dbc.excuteBatch();
//    }
    
    public Object change(ExchangeWordTree inf) {
	return inf.changeMethod(this);
    }
}
