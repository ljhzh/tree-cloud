package sdu.wocl.dataSource.pool.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.exception.FileExistException;

public class Document {

    private final static Logger logger = LoggerFactory.getLogger(Document.class);
    private List<InputStream> in;

    public Document(List<InputStream> in) {
	this.in = in;
    }
    
    public Document(InputStream input) {
	this.in = new ArrayList<InputStream>();
	this.in.add(input);
    }
    
    public Document() {
	
    }
    
    /**
     * 文档定位
     * @author ljh_2015
     *
     */
    public interface DocumentLocation {
	File Location(boolean sameDocument,String title,String type) throws FileExistException;
	
	File LocationRoot() throws FileExistException;
	
	List<String[]> LocationDocuments(String type);
    }

    /**
     * 文档存储
     * @author ljh_2015
     *
     */
    public interface DocumentStorage {
	boolean LocalStorage(int index,File dir,List<InputStream> in) throws IOException;
    }

    /**
     * 通过文档标题查找文档目录
     * 文档索引
     * @author ljh_2015
     *
     */
    public interface DocumentIndexing {
	List<File> findDirectory(String title);
	String[] listTypes();
    }

    /**
     * 通过文档目录遍历原始数据
     * @author ljh_2015
     *
     */
    public interface DocumentReading {
	List<InputStream> reading(List<File> dir) throws FileNotFoundException;
    }

    /**
     * 定位存储，把InputStream用文件存储起来
     * 用于：将访问Server的服务数据存储到本地
     * @param location 定位
     * @param storage 存储
     * @param 语句顺序
     * @throws IOException 
     */
    public void DocumentInputProcessor(boolean sameDocument,int index,String title,String type,DocumentLocation location,
	    DocumentStorage storage) throws IOException {
	File dir;
	try {
	    dir = location.Location(sameDocument,title,type);
	    if(storage.LocalStorage(index,dir, in)) {
		logger.debug("成功存储到"+dir+"下");
	    }
	} catch (FileExistException e) {
	    logger.debug("文件存储失败，因为该文件已存在...");
	    dir = null;
	}
    }
    
    public void DocumentInputProcessor(boolean sameDocument,int index,String title,String type) throws IOException {
	DocumentInputProcessor(sameDocument,index,title,type,new DocumentLocationImpl(), new DocumentStorageImpl());
    }

    /**
     * 文档调用
     * 通过文档标题得到该文档目录下所有数据源的数据流
     * 由于是本地，因此速度能得以提升
     * @param title
     * @param indexing
     * @param reading
     * @return
     * @throws FileNotFoundException 
     */
    public List<InputStream> DocumentRead(String title,DocumentIndexing indexing,DocumentReading reading) throws FileNotFoundException {
	List<File> dir = indexing.findDirectory(title);
	return reading.reading(dir);
    }
    
    public List<InputStream> DocumentRead(String title) throws FileNotFoundException {
	return DocumentRead(title,new DocumentIndexingImpl(), new DocumentReadingImpl());
    }
    
    public String[] getTypes(DocumentIndexing indexing) {
	return indexing.listTypes();
    }
    
    public static File getRootDirectory() throws FileExistException {
	return new DocumentLocationImpl().LocationRoot();
    }
}
