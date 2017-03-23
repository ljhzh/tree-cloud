package sdu.wocl.dataSource.pool;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.tool.BaseConfig;

/**
 * 文档传输池：用来缓冲文件输入输出的缓冲池
 * @author ljh_2015
 *
 */
public class DocumentInputPool {

    private final static Logger logger = LoggerFactory.getLogger(DocumentInputPool.class);
    private final static String poolmax = BaseConfig.getString("ltp.FileInputPool.max");
    
    /**
     * 保证存入的顺序不变
     */
    private Map<String,String[]> pool = new LinkedHashMap<String,String[]>();
    
    /**
     * 将文档存入池中
     * @param title 标题
     * @param context 正文
     */
    public void addMap(String title,String[] context) {
	if(pool.size()<Integer.parseInt(poolmax)) {
	    logger.debug("正在传输文档-----"+title);
	    pool.put(title, context);
	} else
	    logger.debug("文档传输池正处于饱满状态，请稍后再试");
    }

    /**
     * 根据文章标题获取正文
     * @param title
     * @return
     */
    public String[] getContext(String title) {
	return pool.get(title);
    }
    
    public Collection<String[]> getValues() {
	return pool.values();
    }
    
    public Set<String> getKeySets() {
	return pool.keySet();
    }
    
    /**
     * 移除缓冲文档
     * @param title
     */
    public String[] remove(String title) {
	return pool.remove(title);
    }
    
    /**
     * 获取当前文档在线数
     * @return
     */
    public int status() {
	return pool.size();
    }
}
