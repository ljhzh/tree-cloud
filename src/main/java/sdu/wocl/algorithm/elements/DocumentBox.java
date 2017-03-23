package sdu.wocl.algorithm.elements;

import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

/**
 * 文档的数据预处理
 * @author ljh_2015
 *
 */
public abstract class DocumentBox {


    //文档的语句列表
    private List<Sentence> contexts;

    private List<WordTreeMessage> sentMessages = new ArrayList<WordTreeMessage>();

    public void setDocumentBox(List<Sentence> context) {
	this.contexts = context;
	if(context==null) {
	    sentMessages = new ArrayList<WordTreeMessage>();
	} else
	    for (Sentence sentence : context) {
		sentMessages.add(sentence.
			WORDTREE.
			getMessage());
	    }
    }

    public List<Sentence> getContexts() {
	return contexts;
    }

    public List<WordTreeMessage> getSentMessages() {
	return sentMessages;
    }

    public abstract void processing();
}
