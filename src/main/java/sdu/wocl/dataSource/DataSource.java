package sdu.wocl.dataSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sdu.wocl.dataSource.pool.DocumentInputPool;
import sdu.wocl.dataSource.pool.FileOutputPool;
import sdu.wocl.dataSource.pool.document.Document;
import sdu.wocl.dataSource.pool.document.DocumentIndexingImpl;
import sdu.wocl.dataSource.server.DataSourceManager;
import sdu.wocl.dataSource.server.DataSourceManager.Listener;
import sdu.wocl.dataSource.server.DataSourceManager.ListenerSelector;
import sdu.wocl.dataSource.server.LTPServer;
import sdu.wocl.dataSource.server.LTPServer.ConnectionAPI;
import sdu.wocl.exception.FileExistException;
import sdu.wocl.tool.TextBreakIterator;

/**
 * 数据源
 * 数据源分为两种源：
 * 文件源，与数据库源
 * 文件源需要相应解析，
 * 数据库源直接获取相关元数据
 * @author ljh_2015
 *
 */
@Component
public class DataSource {

    private final static Logger logger = LoggerFactory.getLogger(DataSource.class);
    /**
     * 服务器接口
     */
    private ConnectionAPI server;

    /**
     * 监听初始化
     */
    private List<ListenerSelector> listen = new ArrayList<ListenerSelector>();
    /**
     * 文档输入池
     */
    private DocumentInputPool in = new DocumentInputPool();

    /**
     * 文档输出池
     * @throws IOException 
     */
    private FileOutputPool out = new FileOutputPool();

    private Document document;


    /**
     * 刷新一个新链接
     */
    public void refresh() {
	this.server = new LTPServer().openAPI();
    }

    /**
     * 
     * @param title
     * @throws IOException
     */
    public void TransIn(final String title,final String type) {
	/**
	 * 通过传入池获取
	 * 清空内数据
	 */
	listen = new ArrayList<ListenerSelector>();
	/**
	 * in.remove 开始分析时，就移除传入池里的键值对
	 */
	String[] strContext = TextBreakIterator.TextBreak(in.remove(title)[1]);

	DataSourceManager dataSourceManager = new DataSourceManager();
	ListenerSelector listenerSelector = dataSourceManager.getListenerProcessing(strContext);
	/**
	 * 添加监听器
	 */
	listen.add(listenerSelector);
	listenerSelector.process(title, type,new Listener<ListenerSelector>(){
	    @Override
	    public void listen(ListenerSelector t) {
		System.out.println("当前运行："+t.getCurrentString()+"--已完成");
	    }    
	});
    }

    /**
     * 获取文件目录下所有与title相关的文档夹
     * @param title
     * @return
     * @throws IOException 
     */
    public List<InputStream> TransOut(String title) throws IOException {
	Document doc = new Document();
	out.openInputStream(title, doc.DocumentRead(title));
	return out.readInputStream(title);
    }

    /**
     * 顺序输入
     * @throws IOException
     */
    public void TransIn() throws IOException {
	for(String strs:in.getKeySets())
	    TransIn(strs,in.getContext(strs)[0]);
    }

    public void getDocument(String title,String type,String document) {
	String[] str = {type,document};
	in.addMap(title, str);
    }

    public String[] getTypes() {
	Document doc = new Document();
	return doc.getTypes(new DocumentIndexingImpl());
    }

    public int getPoolStatus() {
	return in.status();
    }

    public File getRootDirectory() throws FileExistException {
	return Document.getRootDirectory();
    }

    public List<ListenerSelector> getListens() {
	return listen;
    }
}
