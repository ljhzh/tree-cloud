package sdu.wocl.web.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sdu.wocl.dataFactory.entity.Article;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.tool.BaseConfig;
import sdu.wocl.tool.DocumentCounter;
import sdu.wocl.tool.DocumentCounter.Counter;
import sdu.wocl.web.dao.DocumentDao;
import sdu.wocl.web.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{

    private final static Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    private DocumentDao documentDao;

    @Override
    public boolean saveDocument(String title, String path, String context) {
	logger.debug("正在存储:"+title);
	return documentDao.saveDocument(title, path, context);
    }

    @Override
    public String[] getTypes() {
	logger.debug("正在获取所有文档类别");
	return documentDao.getTypes();
    }

    @Override
    public List<Article> getDocumentsName(String type) {
	return type!=null && !type.trim().equals("")?documentDao.getDocumentsName(type):documentDao.getDocumentsName();
    }

    @Override
    public SentenceContainer getSentenceByDocument(String documentTitle) {
	List<String> titles = new ArrayList<String>();
	titles.add(documentTitle);
	return documentDao.getSentenceByDocument(titles);
    }

    @Override
    public List<Counter> getDocumentBase() {
	File[] files = documentDao.getDocumentBase();
	List<Counter> counters = new ArrayList<Counter>();
	for (File file : files) {
	    DocumentCounter dc = new DocumentCounter(file);
	    counters.add(dc.count());
	}
	return counters;
    }

    /**
     * 创建新仓库
     */
    @Override
    public boolean setBorningBase(String name) {
	File file = new File(BaseConfig.getString("ltp.document.path")+name);
	return file.mkdir();
    }

    @Override
    public boolean removeBase(String name) {
	File file = new File(BaseConfig.getString("ltp.document.path")+name);
	if(file.exists())
	    return file.delete();
	else
	    return false;
    }

    @Override
    public List<String> getDocumentBaseName() {
	File[] files = documentDao.getDocumentBase();
	List<String> counters = new ArrayList<String>();
	for (File file : files) {
	    counters.add(file.getName());
	}
	return counters;
    }


}
