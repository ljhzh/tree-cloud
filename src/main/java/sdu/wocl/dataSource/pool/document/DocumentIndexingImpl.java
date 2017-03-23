package sdu.wocl.dataSource.pool.document;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.dataSource.pool.document.Document.DocumentIndexing;
import sdu.wocl.tool.BaseConfig;

public class DocumentIndexingImpl implements DocumentIndexing{

    private final static Logger logger = LoggerFactory.getLogger(DocumentIndexingImpl.class);
    Map<String,List<File>> pathMap = new HashMap<String,List<File>>();

    @Override
    public List<File> findDirectory(String title) {
	if(pathMap.containsKey(title))
	    return pathMap.get(title);
	String root = BaseConfig.getString("ltp.document.path");
	List<File> files = new ArrayList<File>();
	File rootDir = new File(root);
	for(File typeFile:rootDir.listFiles()) {
	    for (File dateFile:typeFile.listFiles()) {
		for (File titleFile:dateFile.listFiles()) {
		    //logger.debug("正在检索"+dateFile.getName()+"目录下文档："+titleFile.getName());
		    if(titleFile.getName().contains(title))
			files.add(titleFile);
		}
	    }
	}
	if(files.size()==0) {
	    logger.debug("并未找到与"+title+"相关的文档");
	    return null;
	} else {
	    pathMap.put(title, files);
	    logger.debug("已找到与"+title+"相关的文档: "+files.size()+"篇");
	    return files;
	}
    }

    @Override
    public String[] listTypes() {
	String root = BaseConfig.getString("ltp.document.path");
	File rootDir = new File(root);
	return rootDir.list();
    }

}
