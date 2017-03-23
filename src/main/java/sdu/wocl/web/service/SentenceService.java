package sdu.wocl.web.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;

import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

public interface SentenceService {

    //展示所有句子信息
    public List<WordTreeMessage> SentenceShow();
    
    //展示指定句子信息
    public List<WordTreeMessage> SentenceShow(List<Sentence> sents);
    
    //将指定句子放入存储
    public boolean putSentence(Sentence sent);
    
    //将指定句组放入存储
    public boolean putSentencesAll(List<Sentence> sents);
    
    public Sentence remove(Sentence sent);
    
    public List<WordTreeMessage> SentenceStyle(String style) throws SQLException; 
    
    public List<String> SentenceStyleId(int styleid) throws SQLException; 
    
    public List<Sentence> remove(List<Sentence> sents);

    public Page<WordTreeMessage> getSentenceStyle(String style);

}
