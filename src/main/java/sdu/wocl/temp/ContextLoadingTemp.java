package sdu.wocl.temp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import sdu.wocl.dataFactory.entity.Article;
import sdu.wocl.dataFactory.entity.wordtree.tools.DataBaseCommand;
import sdu.wocl.dataSource.server.DataSourceManager;

/**
 * 临时类
 * @author ljh_2015
 *
 */
public class ContextLoadingTemp {

    private Queue<Article> articles = new LinkedList<Article>();
    private ThreadLocal<Integer> local = new ThreadLocal<Integer>();
    //数据源处理器
    private DataSourceManager dataSourceServer = new DataSourceManager();
    private String sql = "select contextid,title,type,context from context where contextid>50000*? and contextid<50000*? order by contextid";
    private static AtomicInteger start = new AtomicInteger();
    private static int count = 0;
    private static boolean continues = true;
    
    public void processing() {
	while(continues) {
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e1) {
		e1.printStackTrace();
	    }
	    if(count<15) {
		count++; 
		new Thread(new Runnable() {	        
		    @Override
		    public void run() {
			//线程获取连接Connection
			DataBaseCommand dbc = new DataBaseCommand();
			PreparedStatement pstmt = dbc.getPreparedResultSet(sql);
			ResultSet rs = null;
			local.set(start.incrementAndGet());
			try {
			    pstmt.setInt(1, local.get());
			    pstmt.setInt(2, local.get()+1);
			    rs = pstmt.executeQuery();
			    if(!rs.first()) {
				System.out.println(count+" Thread no result");
				continues = false;
			    } else {
				int num=0;
				while(rs.next()) {
				    num++;
				    int id = rs.getInt("contextid");
				    String title = rs.getString("title");
				    String type = rs.getString("type");
				    String context = rs.getString("context");
				    Article a = new Article();
				    a.setDoc_title(String.valueOf(id)+"_"+title);
				    a.setType(type);
				    a.setContext(context);
				    articles.offer(a);
				}
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			} finally {
			    if(rs!=null) {
				try {
				    rs.close();
				    pstmt.close();
				    dbc.close();
				    System.out.println(count+" Thread is over");
				    count--;
				} catch (SQLException e) {
				    e.printStackTrace();
				}
			    }
			}

		    }
		}).start();
	    }
	}
	System.out.println(articles.size());
    }
    
    public void start() {
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		while(true) {
		    Article arc = articles.poll();
		    if(arc!=null) {
			System.out.println("=======正在收录========");
			dataSourceServer.processing().documentStorage(arc.getDoc_title(), arc.getType(), arc.getContext());
		    } else
			System.out.println("什么都没有");
		    try {
			Thread.sleep(1000);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	    
	}).start();
    }
    
}
