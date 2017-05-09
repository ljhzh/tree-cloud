package sdu.wocl.algorithm.tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import sdu.wocl.dataFactory.entity.wordtree.tools.DataBaseCommand;

public class StyleMapps {
    
    private static DataBaseCommand dbc = new DataBaseCommand();
    
    public static Map<String,Integer> unRecordedStyle = new HashMap<String,Integer>();
    
    public static final String[] maps = new String[416];
    
    static {
	getMap();
    }
    
    public static void unRecordGet(String style) {
	if(unRecordedStyle.containsKey(style)) {
	    unRecordedStyle.put(style, unRecordedStyle.get(style)+1);
	} else
	    unRecordedStyle.put(style, 1);
    }
    
    public static void getMap() {
	String sql = "select style from _style_nowp order by count desc";
	ResultSet rs = dbc.getResultBySql(sql);
	try {
	    int i = 0 ;
	    while(rs.next()) {
	        String style = rs.getString("style");
	        maps[i++] = style;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
    }
    
    
    public static int getString(String style) {
	for (int i = 0; i < maps.length; i++) {
	    if(style.equalsIgnoreCase(maps[i])) {
		//System.out.println(maps[i]+" "+style);
		return i;
	    } 
	}
	return -1;
    }
    
    /**
     * 通过id数组，返回句式结构字符串
     * @param ids
     * @return
     */
    public static String getStringStyle(Integer[] ids) {
	String temp = "";
	if(ids.length>0) {
	    for (int i = 0; i < ids.length; i++) {
		if(ids[i]!=-1) {
		    temp+=maps[ids[i]];
		}
		if(i!=ids.length-1) {
		    temp+="_";
		}
	    }
	} else
	    temp=null;
	return temp;
    }
}
