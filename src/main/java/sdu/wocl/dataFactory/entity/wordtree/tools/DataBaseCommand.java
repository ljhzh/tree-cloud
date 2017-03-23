package sdu.wocl.dataFactory.entity.wordtree.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataBaseCommand {
    
    private final HibernateFactoryUtil hfu = new HibernateFactoryUtil();
    private Statement sts = null;
    private PreparedStatement pstmt = null;
    
    public DataBaseCommand() {
	Connection conn = hfu.getConnection();
	try {
	    sts = conn.createStatement();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    System.err.println("connecting error");
	    e.printStackTrace();
	}
    }
    
    public PreparedStatement getPreparedResultSet(String sql) {
	Connection conn = hfu.getConnection();
	try {
	    pstmt = conn.prepareStatement(sql);
	    return pstmt;
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    System.err.println("connecting error");
	    e.printStackTrace();
	}
	return null;
    }
    /**
     * 构造sql执行语句
     * @param sql
     */
    public void setCommands(List<String> sql) {
	try {
	    for(String s:sql) {
		System.out.println(s);
		sts.addBatch(s);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    
	}
    }
    
    public void setCommand(String sql) {
	try {
	    sts.addBatch(sql);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    System.err.println("sql sentence error,please check your input");
	    e.printStackTrace();
	}
    }
    
    /**
     * 清除sql语句
     */
    public void clear() {
	try {
	    sts.clearBatch();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    /**
     * 批处理执行语句
     */
    public void excuteBatch(){
	try {
	    sts.executeBatch();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public ResultSet getResultBySql(String sql) {
	ResultSet rs = null;
	try {
	    rs = sts.executeQuery(sql);
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
	return rs;
    }
    
    public void close() {
	try {
	    sts.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
