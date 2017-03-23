package sdu.wocl.dataFactory.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.persistence.Entity;

import org.apache.xalan.xsltc.compiler.sym;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.dataFactory.database.DataBoxServer;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

/**
 * 用来专门装sentence的容器
 * @author ljh_2015
 *
 */
@Entity
public class SentenceContainer {

    private final static Logger logger = LoggerFactory.getLogger(SentenceContainer.class);
    //用list存储sentence
    List<Sentence> sentences = new ArrayList<Sentence>();
    
    //合并
    public void merge(SentenceContainer sc) {
	sentences.addAll(sc.getSentences());
    }

    //分离
    public List<Sentence> split(SentenceContainer sc) {
	List<Sentence> splitSentence = new ArrayList<Sentence>();
	for (Sentence sentence : sc.getSentences()) {
	    if(this.remove(sentence)) {
		splitSentence.add(sentence);
	    }
	}
	if(splitSentence.size()==0)
	    return null;
	else
	    return splitSentence;
    }

    public List<Sentence> getSentences() {
	return sentences;
    }

    //存放sentence
    public boolean put(Sentence sent) {
	//logger.debug("put a sentence into a "+this.getClass().getSimpleName());
	return sent!=null && sentences.add(sent);
    }

    public boolean putAll(List<Sentence> sents) {
	return sentences.addAll(sents);
    }

    //移除sentence
    public boolean remove(Sentence sent) {
	//logger.debug("remove a element");
	return sent!=null && sentences.remove(sent);
    }

    //清楚数组
    public void clear() {
	//logger.debug("clear the arrays");
	sentences.removeAll(sentences);
    }

    public List<WordTreeMessage> getMessages() {
	List<WordTreeMessage> messages = new ArrayList<WordTreeMessage>();
	for(Sentence sent:sentences) {
	    WordTreeMessage wtm = new WordTreeMessage(sent.WORDTREE);
	    messages.add(wtm);
	}
	if(messages.size()==0)
	    return null;
	return messages;
    }

}
