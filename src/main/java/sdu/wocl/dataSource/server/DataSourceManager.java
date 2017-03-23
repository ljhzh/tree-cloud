package sdu.wocl.dataSource.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import sdu.wocl.dataSource.DataSource;
import sdu.wocl.dataSource.pool.document.Document;
import sdu.wocl.dataSource.server.LTPServer.ConnectionAPI;


@Component
public class DataSourceManager {

    private static class DataSourceServer {
	static DataSource dataSource = new DataSource();
    }

    private static class DataSourceProcessor {
	static DataSourceProcessorImpl dataSourceProcessorImpl = new DataSourceProcessorImpl();
    }

    public static DataSource newInstant(){
	return DataSourceServer.dataSource;
    }

    public ListenerSelector getListenerProcessing(String[] contexts) {
	return new ListenerSelector(contexts);
    }

    public DataSourceProcessorImpl processing() {
	return DataSourceProcessor.dataSourceProcessorImpl;
    }

    public interface Listener<T> {
	void listen(T t);
    }

    //监听数据源，获取数据传输的状态
    public class ListenerSelector {

	//分句句列
	private List<String> strSource = null;
	//指针句
	private String currentString = null;
	//数据分析成功得句子
	private List<String> successStrings = null;
	//数据分析失败的句子
	private List<String> failedStrings = null;

	private int sum;

	public ListenerSelector(String[] contexts) {
	    //初始化
	    init(contexts);
	}

	//初始化
	public void init(String[] source) {
	    strSource = new ArrayList<String>();

	    for (String string : source) {
		strSource.add(string);
	    }
	    successStrings = new ArrayList<String>();
	    failedStrings =new ArrayList<String>();
	    sum = source.length;
	}

	public boolean process(String title,String type,Listener listener) {
	    for(int i=0;i<strSource.size();i++) {
		if(!strSource.get(i).equals("")) {
		    currentString=strSource.get(i);
		    listener.listen(this);
		    processSimple(currentString, title, type, i);
		    currentString=null;
		}
	    }
	    return endRunning();
	}

	private void processSimple(String sContext,String title,String type,int index) {
	    int count=1;
	    boolean flag = true;
	    if(!sContext.equalsIgnoreCase("") && sContext!=null)
		while(flag) {
		    try {
			Random r = new Random();
			Thread.sleep(r.nextInt(500)+1000);

			ConnectionAPI server = new LTPServer().openAPI();
			InputStream inputStream = server.AnalyzeGetResult(sContext);
			Document doc = new Document(inputStream);
			doc.DocumentInputProcessor(true,index,title, type);
			/**
			 * 将运行成功的句子存放
			 */
			successStrings.add(sContext);
			flag = false;
		    } catch (IOException e) {
			if(count==20) {
			    flag = false;
			    /**
			     * 将运行失败的句子存放
			     */
			    System.out.println("failed: "+sContext);
			    failedStrings.add(sContext);
			    break;
			}
			count++;
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	}

	/**
	 * 获取总数
	 * @return
	 */
	public int getSum() {
	    return sum;
	}

	//运行的当前句
	public int statusCount() {
	    return strSource.size();
	}

	public String getCurrentString() {
	    return currentString;
	}

	/**
	 * 判断监听器是否完成监听
	 * @return
	 */
	public boolean endRunning() {
	    if(currentString ==strSource.get(strSource.size()-1))
		return true;
	    return false;
	}
    }
}
