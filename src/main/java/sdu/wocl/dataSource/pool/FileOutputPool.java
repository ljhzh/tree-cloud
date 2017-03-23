package sdu.wocl.dataSource.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用以将从Server返回得到的InputStream 写入文件中，并能管理文件系统
 * @author ljh_2015
 *
 */
public class FileOutputPool {
    
    private final static Logger logger = LoggerFactory.getLogger(FileOutputPool.class);
    Map<String,List<InputStream>> ins = new LinkedHashMap<String,List<InputStream>>();
  
    /**
     * 绑定一个iid,绑定inn
     * @param iid
     * @param in
     */
    public void openInputStream(String title,List<InputStream> in) {
	if(ins.size()<50) {
	    ins.put(title, in);
	} else
	    logger.debug("输出池已满，请稍后");
    } 
    
    public List<InputStream> readInputStream(String title) throws IOException {
	return ins.get(title);
    }
    
    /**
     * 关闭输入流
     * @param iid
     * @throws IOException
     */
    public void closeInputStream(String title) throws IOException {
	List<InputStream> in = ins.get(title);
	if(in!=null) {
	    for (InputStream inputStream : in) {
		inputStream.close();
	    }
	    ins.remove(title);
	}
    }
}
