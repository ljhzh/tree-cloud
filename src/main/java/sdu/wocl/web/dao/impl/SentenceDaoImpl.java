package sdu.wocl.web.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import sdu.wocl.dataFactory.database.DataBoxServer;
import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;
import sdu.wocl.web.dao.SentenceDao;

@Component
public class SentenceDaoImpl implements SentenceDao {

    private SentenceContainer sentenceContainer;
    
//    private static Queue<List<Sentence>> sentsQueue = new LinkedList<List<Sentence>>();
//    private static boolean starter = true;
//    private static boolean flag = true;
    
    public SentenceDaoImpl() {
	this.sentenceContainer = new SentenceContainer();
    }
    
    public SentenceDaoImpl(SentenceContainer sentenceContainer) {
	this.sentenceContainer = sentenceContainer;
    }

    @Override
    public List<WordTreeMessage> SentenceMessageList(SentenceContainer sc) {
	return sc.getMessages();
    }

    @Override
    public List<WordTreeMessage> SentenceMessageList() {
	return SentenceMessageList(this.sentenceContainer);
    }

    @Override
    public boolean setSentenceContainer(SentenceContainer sentenceContainer) {
	if(sentenceContainer==null)
	    return false;
	this.sentenceContainer.merge(sentenceContainer);
	return true;
    }

    @Override
    public List<Sentence> removeListSentence(List<Sentence> sents) {
	SentenceContainer sentenceContainer = new SentenceContainer();
	sentenceContainer.putAll(sents);
	return sentenceContainer.split(sentenceContainer);
    }

    @Override
    public void SentenceList(String style) throws SQLException {
	//return DataBoxServer.setDatabaseServer().sents;
    }

    @Override
    public List<String> SentenceList(int styleid) throws SQLException {
	return DataBoxServer.setDatabaseServer().querySentenceByStyleId(styleid);
    }
    
}
