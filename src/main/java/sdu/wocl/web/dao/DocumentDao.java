package sdu.wocl.web.dao;

import java.io.File;
import java.util.List;

import sdu.wocl.dataFactory.entity.Article;
import sdu.wocl.dataFactory.entity.SentenceContainer;

public interface DocumentDao {
    
    public boolean saveDocument(String title,String path,String context);
    
    public String[] getTypes();
    
    public List<Article> getDocumentsName();
    
    public List<Article> getDocumentsName(String type);
    
    public SentenceContainer getSentenceByDocument(List<String> documentTitle);
    
    public File[] getDocumentBase();
    
}
