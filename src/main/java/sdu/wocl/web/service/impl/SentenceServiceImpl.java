package sdu.wocl.web.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;
import sdu.wocl.web.dao.SentenceDao;
import sdu.wocl.web.service.SentenceService;

@Service
public class SentenceServiceImpl implements SentenceService{

    
    @Autowired
    private SentenceDao sentenceDao;
    
    @Override
    public List<WordTreeMessage> SentenceShow() {
	return sentenceDao.SentenceMessageList();
    }

    @Override
    public boolean putSentence(Sentence sent) {
	SentenceContainer sc = new SentenceContainer();
	sc.put(sent);
	return sentenceDao.setSentenceContainer(sc);
    }

    @Override
    public boolean putSentencesAll(List<Sentence> sents) {
	SentenceContainer sc = new SentenceContainer();
	sc.putAll(sents);
	return sentenceDao.setSentenceContainer(sc);
    }

    @Override
    public List<WordTreeMessage> SentenceShow(List<Sentence> sents) {
	SentenceContainer sc = new SentenceContainer();
	sc.putAll(sents);
	return sentenceDao.SentenceMessageList(sc);
    }

    @Override
    public Sentence remove(Sentence sent) {
	return null;
    }

    @Override
    public List<Sentence> remove(List<Sentence> sents) {
	return null;
    }

    @Override
    public List<WordTreeMessage> SentenceStyle(String style) throws SQLException {
	SentenceContainer sc = new SentenceContainer();
	return sentenceDao.SentenceMessageList(sc);
    }

    @Override
    public List<String> SentenceStyleId(int styleid)
	    throws SQLException {
	return sentenceDao.SentenceList(styleid);
    }

    @Override
    public Page<WordTreeMessage> getSentenceStyle(String style) {
	return null;
    }

    
}
