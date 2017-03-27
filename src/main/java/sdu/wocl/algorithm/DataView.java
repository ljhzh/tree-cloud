package sdu.wocl.algorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sdu.wocl.algorithm.data.ComputingData;
import sdu.wocl.algorithm.data.PreparedData;
import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.SentenceContainer;
import sdu.wocl.dataFactory.server.FilterServer;
import sdu.wocl.dataSource.pool.document.Document.DocumentLocation;
import sdu.wocl.dataSource.pool.document.DocumentLocationImpl;
import sdu.wocl.dataSource.server.DataSourceManager;


/**
 * 数据可视化接口
 * @author ljh_2015
 *
 */
public class DataView {

    //数据源处理器
    private DataSourceManager dataSourceServer = new DataSourceManager();
    //数据过滤工厂
    private FilterServer filterServer = new FilterServer();
    private List<String> titles = new ArrayList<String>();
    private ComputingData computing = new ComputingData();
    private HandleAdapter<PreparedData,ComputingData> h = new HandleAdapter<PreparedData,ComputingData>();

    public DataView() {
	h.setComputing(computing);
    }

    public HandleAdapter<PreparedData, ComputingData> getAdapter() {
	return h;
    }

    //显示接口
    public void show() {
	h.showing();
    }

    //取名接口
    public void setName(String name) {
	computing.setName(name);
    }

    public List<String> getTitles() {
	return titles;
    }

    public void init(String title) {
	titles.add(title);
	List<InputStream> inputStreams = null;
	try {
	    inputStreams = dataSourceServer.processing().findDocumentBytitle(titles);
	    filterServer.receive(inputStreams);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	if(titles.remove(title)) {
	    SentenceContainer sc = filterServer.getContainer();
	    List<Sentence> context = sc.getSentences();
	    h.setTitle(title);
	    h.setDocumentBox(context);
	    h.setDatas(new PreparedData());
	    h.processing();
	    sc.clear();
	}
    }
    
    public static DataView DocumentManager(String title) {
	return DocumentManager(title,true);
    }

    public static DataView DocumentManager(String path,boolean isTitle) {
	DataView dataView = new DataView();
	if(isTitle) {
	    return dataView.setTitle(path);
	}
	DocumentLocation dl = new DocumentLocationImpl();
	List<String[]> documents = dl.LocationDocuments(path);
	dataView.setName(path+"类");
	for (String[] strings : documents) {
	    for (String string : strings) {
		dataView.init(string);
	    }
	}
	return dataView;
    }

    public DataView DocumentCollectorTitle(String title) {
	init(title);
	return this;
    }

    public DataView setTitle(String title) {
	this.init(title);
	this.setName(title);
	return this;
    }



    public static void main(String[] args) {
	//DataView.DocumentCollectorPath("作文").getAdapter().showing();
	//DataView.DocumentCollectorPath("写人作文").getAdapter().showing();
	//DataView.DocumentCollectorPath("叙事作文").getAdapter().showing();
	//DataView.DocumentCollectorPath("日记").getAdapter().showing();
	DataView.DocumentManager("挣钱").show();
    }
}
