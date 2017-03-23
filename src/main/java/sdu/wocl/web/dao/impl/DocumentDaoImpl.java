package sdu.wocl.web.dao.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import sdu.wocl.dataFactory.entity.Article;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.dataFactory.server.FilterServer;
import sdu.wocl.dataSource.server.DataSourceManager;
import sdu.wocl.web.dao.DocumentDao;

@Component
public class DocumentDaoImpl implements DocumentDao {

    //数据源处理器
    private DataSourceManager dataSourceServer = new DataSourceManager();
    //数据过滤工厂
    private FilterServer filterServer = new FilterServer();

    @Override
    public boolean saveDocument(String title, String path, String context) {
	return dataSourceServer.processing().documentStorage(title, path, context);
	
    }

    @Override
    public String[] getTypes() {
	return dataSourceServer.processing()
		.getTypes();
    }

    /**
     * 得到Sentence 的List
     */
    @Override
    public SentenceContainer getSentenceByDocument(List<String> documentTitles) {
	List<InputStream> inputStreams = null;
	try {
	    inputStreams = dataSourceServer.processing().findDocumentBytitle(documentTitles);
	    filterServer.receive(inputStreams);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return filterServer.getContainer();
    }

    @Override
    public List<Article> getDocumentsName() {
	String[] types = getTypes();
	List<Article> documentsGroup = new ArrayList<Article>();
	for (String type : types) {
	    documentsGroup.addAll(getDocumentsName(type));
	}
	return documentsGroup;
    }

    @Override
    public List<Article> getDocumentsName(String type) {
	
	List<Article> documentsGroup = new ArrayList<Article>();
	File dir = new File(dataSourceServer.processing().RootConfig(),type);
	String[] documents = dir.list(new FilenameFilter(){
	    @Override
	    public boolean accept(File dir, String name) {
		if(dir.isDirectory())
		    return true;
		return false;
	    }
	});
	for (String date : documents) {
	    File dateDir = new File(dir.getPath()+"/"+date);
	    for(String document:dateDir.list()) {
		Article arc = new Article();
		arc.setDate(date);
		arc.setDoc_title(document);
		arc.setType(type);
		documentsGroup.add(arc);
	    }
	}
	return documentsGroup;
    }

    @Override
    public File[] getDocumentBase() {
	return filterServer.getDocumentBase();
    }

}
