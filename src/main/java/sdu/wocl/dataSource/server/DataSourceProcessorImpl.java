package sdu.wocl.dataSource.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataSource.DataSource;
import sdu.wocl.dataSource.server.DataSourceManager.ListenerSelector;
import sdu.wocl.exception.FileExistException;

/**
 * 数据源处理接口
 * @author ljh_2015
 *
 */
public class DataSourceProcessorImpl {
    
    DataSource dataSource = DataSourceManager.newInstant();
    
    /**
     * 根据Title 获取相关文档
     * @param title
     * @throws IOException 
     * @return
     */
    public List<InputStream> findDocumentBytitle(List<String> titles) throws IOException {
	List<InputStream> result = new ArrayList<InputStream>();
	for (String title : titles) {
	    result.addAll(
		    dataSource.
		    TransOut(title));
	}
	return result;
    }
    
    public boolean documentStorage(String title,String type,String document) {
	dataSource.getDocument(title, type, document);
	try {
	    dataSource.TransIn();
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }
    
    public String[] getTypes() {
	return dataSource.getTypes();
    }
    
    public File RootConfig() {
	try {
	    return dataSource.getRootDirectory();
	} catch (FileExistException e) {
	    System.err.println("请检查配置路径");
	    return null;
	}
    }
    
}
