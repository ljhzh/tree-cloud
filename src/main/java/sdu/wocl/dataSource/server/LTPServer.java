package sdu.wocl.dataSource.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.tool.BaseConfig;

/**
 * LTP服务器类
 * @author ljh_2015
 *
 */
public class LTPServer {

    private final static Logger logger = LoggerFactory.getLogger(LTPServer.class);
    private ConnectionPool connectionPool = new ConnectionPool();

    /**
     * 获取连接
     * @return 返回从连接池中获取的连接
     */
    public ConnectionAPI openAPI() {
	return connectionPool.getConnection();
    }

    public ConnectionAPI openAPI(String format,String pattern) {
	return connectionPool.getConnection(format,pattern);
    }
    
    /**
     * 释放连接
     * @param api
     */
    public void closeConnection(ConnectionAPI api) {
	api.openBusy();
    }
    
    /**
     * 平台连接池
     * @author ljh_2015
     *
     */
    private class ConnectionPool {
	String max = BaseConfig.getString("ltp.server.connection.pool.max");
	List<ConnectionAPI> pool = new ArrayList<ConnectionAPI>();

	public ConnectionPool() {
	    init();
	}
	
	void init() {
	    for(int i=0;i<Integer.parseInt(this.max);i++) {
		pool.add(new ConnectionAPI());
	    }
	}

	ConnectionAPI getConnection() {
	    for (ConnectionAPI connectionAPI : pool) {
		if(!connectionAPI.isbusy()) {
		    return connectionAPI;
		}
	    }
	    return null;
	}

	ConnectionAPI getConnection(String format,String pattern) {
	    for (ConnectionAPI connectionAPI : pool) {
		if(!connectionAPI.isbusy()) {
		    connectionAPI.setFormat(format);
		    connectionAPI.setPattern(pattern);
		    return connectionAPI;
		}
	    }
	    return null;
	}
	
	    
    }

    /**
     * 内置接口，链接LTP平台
     * @author ljh_2015
     *
     */
    public class ConnectionAPI {
	
	private boolean isBusy = false;
	private final String api_key="p8D9P0V1rsbV3aBKCudxIVwgpyjb8GjGFXKwHxsV";
	private String pattern;
	private String format;

	/**
	 * 可根据自身情况手动设置
	 * @param pat
	 * @param frmat
	 */
	public ConnectionAPI(final String pat,final String frmat) {
	    this.pattern = pat;
	    this.format = frmat;
	}

	public void setPattern(String pattern) {
	    this.pattern = pattern;
	}

	public void setFormat(String format){
	    this.format=format;
	}

	/**
	 * 默认参数:pattern = "all",format="xml"
	 */
	public ConnectionAPI(){
	    this.pattern = "all";
	    this.format = "xml";
	}

	public synchronized InputStream AnalyzeGetResult(String text) throws IOException {       
	    URLConnection conn=null;
	    text = URLEncoder.encode(text, "utf-8");
	    /**
	     * 编码问题：最后字符为换行符的字符串
	     * 解决策略：截掉%0D%0A之后的字符
	     */
	    int index = text.lastIndexOf("%0D%0A");
	    if(-1!=index) {
		text=text.substring(0,index);
	    }
	    conn = getUrl(api_key,text,format,pattern);
	    conn.connect();
	    logger.debug(Thread.currentThread().getName()+" get stream from LTPServer");
	    isBusy = true;
	    return conn.getInputStream();
	}

	private URLConnection getUrl(String api_key, String text, String format, String pattern) throws IOException {
	    URL url = new URL("http://api.ltp-cloud.com/analysis/?"
		    + "api_key=" + api_key + "&"
		    + "text="    + text    + "&"
		    + "format="  + format  + "&"
		    + "pattern=" + pattern);
	    URLConnection connection = url.openConnection();
	    return connection;
	}

	public void openBusy() {
	    logger.debug(Thread.currentThread().getName()+"release a connectionAPI");
	    isBusy = false;
	}
	
	/**
	 * 判断连接是否处于忙碌状态
	 * @return
	 */
	public boolean isbusy() {
	    return isBusy;
	}
    }

}
