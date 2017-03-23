package sdu.wocl.dataSource.pool.document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.dataSource.pool.document.Document.DocumentLocation;
import sdu.wocl.exception.FileExistException;
import sdu.wocl.tool.BaseConfig;

public class DocumentLocationImpl implements DocumentLocation {

    private final static Logger logger = LoggerFactory.getLogger(DocumentLocationImpl.class);
    private String Filepath = BaseConfig.getString("ltp.document.path");

    @Override
    public File Location(boolean sameDocument,String title,String type) throws FileExistException {
	if(!Filepath.endsWith("/"))
	    type="/"+type;
	File file = new File(Filepath+type+"/"+dateDirectory()+"/"+title);
	//logger.debug(file.getPath());
	if(!sameDocument && file.listFiles()!=null) {
	    throw new FileExistException();
	} else
	    file.mkdirs();
	return file;
    }
    
    

    private String dateDirectory() {
	return dateString().substring(0,10);
    }

    private String dateString() {
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
	String dirName = sdf.format(date);
	return dirName;
    }



    @Override
    public File LocationRoot() throws FileExistException {
	return new File(Filepath);
    }
    
    /**
     * 获取一类文档名称
     */
    public List<String[]> LocationDocuments(String type) {
	File file = new File(Filepath+type);
	File[] dates = file.listFiles();
	List<String[]> names = new ArrayList<String[]>();
	for (int i = 0; i < dates.length; i++) {
	    names.add(dates[i].list());
	}
	return names;
    }
}
