package sdu.wocl.dataFactory.entity.wordtree.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataFactory.entity.Result;

public class DatabaseTool {

    static DataBaseCommand dbc = new DataBaseCommand();

    /**
     * 包装要测试的数据库所有数据
     */
    static List<String> list = new ArrayList<String>();

    /**
     * 全部提取
     */
    public static void selectAll() {
	String sql = "select context,treeid from sentence";
	selectAll(sql);
    }

    /**
     * 选择提取
     * @param num 提取多少条
     * @param start 提取前置
     */
    public static void selectAll(int num,int start) {
	String sql = "select context,treeid from sentence";
	if(start>0) {
	    sql+=" where treeid>"+start;
	}
	sql+=" limit "+num;
	System.out.println(sql);
	selectAll(sql);
    }

    /**
     * 提取前100条
     * @param num
     */
    public static void selectAll(int num) {
	selectAll(num,0);
    }

    /**
     * 提取数据样本
     */
    public static void selectAll(String sql) {
	ResultSet rs = dbc.getResultBySql(sql);
	try {
	    while(rs.next()) {
		String context = rs.getString("context");
		int treeid = rs.getInt("treeid");
		String result = context+" "+treeid;
		list.add(result);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    try {
		rs.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
    
    public static void insertSingle(String text,int sentid) {
	String sql = "insert into sentence(context,treeid) values ('"+text+"',"+sentid+")";
	dbc.setCommand(sql);
	dbc.excuteBatch();
    }
    
    public static void reset() {
	list = new ArrayList<String>();
    }
    
    public static List<String> getSqlList() {
	return list;
    }
    
    public static int getTreeId(String table_name) {
	String sql = "SELECT * FROM `"+table_name+"` order by treeid desc limit 1";
	ResultSet rs = dbc.getResultBySql(sql);
	try {
	    if(rs.next()) {
	        int id = rs.getInt("treeid");
	        return id+1;
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return -1;
    }
    
    public static List<String> getStyles() {
	List<String> mys = new ArrayList<String>();
	String sql = "select * from _style_nowp";
	ResultSet rs = dbc.getResultBySql(sql);
	
	try {
	    while(rs.next()) {
	        int id= rs.getInt("id");
	        String style = rs.getString("style");
	        mys.add(style+" "+id);
	    }
	    if(mys.size()>0)
		return mys;
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }
    
    public static List<Result> getResult(String style){
	List<Result> results = new ArrayList<Result>();
	String sql="select hed,context,son,sonpos,sonnum from sentence,style_stb where sentence.treeid=style_stb.id and style_stb.son='"+style+"'";
	ResultSet rs = dbc.getResultBySql(sql);
	try {
	    while(rs.next()) {
	        String hed = rs.getString("hed");
	        String context = rs.getString("context");
	        String son = rs.getString("son");
	        String sonpos = rs.getString("sonpos");
	        String sonnum = rs.getString("sonnum");
	        results.add(new Result(hed,context,son,sonpos,sonnum));
	    }
	    return results;
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    try {
		rs.close();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return null;
    }
}