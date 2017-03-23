package sdu.wocl.dataFactory.server;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataFactory.database.DataBoxServer;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.dataFactory.resolution.InputStreamResolutionFilter;
import sdu.wocl.tool.BaseConfig;

/**
 * 过滤器
 * 包含文档分析，以及数据库散句分析
 * List<InputStream> 是文档分析入口
 * @author ljh_2015
 *
 */
public class FilterServer {

    /**
     * 文档分析入口接收
     */
    private DataBoxServer databoxServer = new DataBoxServer();

    /**
     * 加工过滤厂
     * 将从数据源dataSourceServer传来的List<InputStream>
     * 进行加工得到Sentence类
     * 并将结果放入SentenceCantainer中进行封装
     * 结果的调用即对容器的调用
     * **/
    private InputStreamResolutionFilter inputStreamResolutionFilter = 
	    new InputStreamResolutionFilter();
    /**
     * 句容容器
     */
    private SentenceContainer sc = new SentenceContainer();

    private List<InputStream> input = null;
    private String key=null;

    public void reset() {
	input=null;
	key = null;
    }

    public List<InputStream> getInput() {
	return input;
    }

    public void setInput(List<InputStream> input) {
	this.input = input;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    /**
     * 加载数据
     */
    public void loading() {
	if(key!=null)
	    queryDatabase(key);
	else if(input!=null)
	    receive(input);
	else {
	    System.out.println("操作错误，没有数据可查询");
	    return;
	}
    }

    /**
     * 接收输入流
     * @param inputStream
     */
    public void receive(List<InputStream> inputStreams) {
	/*
	 * 需要容错：当inputStreams为Null时
	 * */
	//接受文档数据源
	analysis(databoxServer.setDocumentsServer(inputStreams).
		getDocumentInputStreams());
    }
    
    /**
     * 拿到文档库
     * @return
     */
    public File[] getDocumentBase() {
	String path = BaseConfig.getString("ltp.document.path");
	File root = new File(path);
	if(!root.exists())
	    root.mkdirs();
	return root.listFiles();
    }
    
    /**
     * 通过关键词查找数据库
     * 
     */
    public void queryDatabase(String key) {
	clear();
	sc.putAll(databoxServer.setDatabaseServer().QuerySentence(key));
    }

    public void receive(InputStream inputStream) {
	List<InputStream> inputStreams = new ArrayList<InputStream>();
	inputStreams.add(inputStream);
	receive(inputStreams);
    }

    //缓存清除
    private void clear() {
	inputStreamResolutionFilter.clear();
	sc.clear();
    }

    /**
     * 加工分析
     * @param inputStream
     */
    private void analysis(List<InputStream> inputStream) {
	clear();
	for (int i=0;i<inputStream.size();i++) {
	    inputStreamResolutionFilter.resolution(inputStream.get(i),(i+1));
	}
	sc.putAll(inputStreamResolutionFilter.getSentsTree());
    }

    /**
     * 返回句容容器
     * @return
     */
    public SentenceContainer getContainer() {
	return sc;
    }


}
