package sdu.wocl.web.dao;

import java.sql.SQLException;
import java.util.List;

import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

public interface SentenceDao {
    
    //设置句子容器
    public boolean setSentenceContainer(SentenceContainer sentenceContainer);
    //获取语句的信息
    public List<WordTreeMessage> SentenceMessageList(SentenceContainer sc);
    
    public List<WordTreeMessage> SentenceMessageList();
    
    public void SentenceList(String style) throws SQLException;
    
    public List<String> SentenceList(int styleid) throws SQLException;
    
    public List<Sentence> removeListSentence(List<Sentence> sents);
}
