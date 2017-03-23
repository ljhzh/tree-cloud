package sdu.wocl.dataFactory.entity.wordtree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import sdu.wocl.dataFactory.entity.wordtree.tools.DataBaseCommand;

/**
 * 句式表
 * @author ljh_2015
 *
 */
public class StyleTable {

    /**
     * 用以存储句式库
     */
    static Map<String,Integer> map = new HashMap<String,Integer>();

    static {
	DataBaseCommand dbc = new DataBaseCommand();
	String sql = "select * from _style_nowp";
	ResultSet rs = dbc.getResultBySql(sql);

	try {
	    while(rs.next()) {
		int id = rs.getInt("id");
		String style = rs.getString("style");
		map.put(style, id);
	    }
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public static Map<String,Integer> getStyleTable() {
	return map;
    }
}
