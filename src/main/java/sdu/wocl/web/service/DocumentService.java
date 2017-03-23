package sdu.wocl.web.service;

import java.util.List;

import sdu.wocl.dataFactory.entity.Article;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.tool.DocumentCounter.Counter;

public interface DocumentService {
    
    public boolean saveDocument(String title,String path,String context);
    
    public String[] getTypes();
    
    public List<Article> getDocumentsName(String type);
    
    public SentenceContainer getSentenceByDocument(String documentTitle);
    
    public List<Counter> getDocumentBase();
    
    public List<String> getDocumentBaseName();
    
    public boolean setBorningBase(String name);
    
    public boolean removeBase(String name);
}
