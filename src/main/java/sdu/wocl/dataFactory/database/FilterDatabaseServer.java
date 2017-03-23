package sdu.wocl.dataFactory.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sdu.wocl.dataFactory.entity.Pos;
import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.Word;
import sdu.wocl.dataFactory.entity.Wp;
import sdu.wocl.dataFactory.entity.wordtree.tools.DataBaseCommand;

/**
 * 数据库过滤器
 * 将数据库中零散的语料收集起来，与文档库共同构建成预料库
 * @author ljh_2015
 *
 */
public class FilterDatabaseServer {

    /**
     * 数据集 
     */
    private ResultSet resultSets;

    /**
     * 数据库连接接口
     */
    private final DataBaseCommand dbc = new DataBaseCommand();

    /**
     * 存储键值对
     * @author ljh_2015
     *
     */
    public static class CacheManager {

	/**
	 * 键值对
	 */
	private static HashMap<String[],List<Sentence>> cacheMap = new HashMap<String[],List<Sentence>>();

	public synchronized static boolean put(String[] key,List<Sentence> sents) {
	    if(key.length<1 && sents==null && sents.size()<1) {
		return false; 
	    }
	    cacheMap.put(key, sents);
	    return true;
	}

	public static List<Sentence> getValueSentence(String[] key) {
	    return cacheMap.get(key);
	}

    } 

    public List<Sentence> QuerySentence(String keyWords) {
	String[] keys = keyWords.split(" ");
	List<Sentence> sents = null;
	//从缓冲区调用结果
	sents = CacheManager.getValueSentence(keys);
	List<Integer> ids = querySentenceIds(keys);
	if(sents!=null && ids.size() == sents.size())
	    return sents;
	return querySentenceById(ids);
    }


    /**
     * 获取句式查询
     * @param id
     * @return
     */
    public ResultSet queryStyleSet(int id) {
	String sql = "select style,count from _style_nowp where id ="+id;
	return dbc.getResultBySql(sql);
    }

    /**
     * 获取相关句子的查询
     * @param sql
     * @return
     */
    public ResultSet querySentenceSets(List<Integer> ids) {
	String sql = "select context,style_sample_nowp_id from sentence where id in (";
	for (int i=0;i<ids.size();i++) {
	    sql+="\""+ids.get(i)+"\"";
	    if(i<ids.size()-1) {
		sql+=",";
	    }
	}
	sql+=")";
	return dbc.getResultBySql(sql);
    }

    /**
     * 获取相关词语的查询
     * 查询到相关句子ID
     * @param sql
     * @return
     */
    private List<Integer> querySentenceIds(String[] words) {
	List<Integer> ids = new ArrayList<Integer>();
	String sql ="select sentid from _tword where text in (";
	for (int i=0;i<words.length;i++) {
	    sql+="\""+words[i]+"\"";
	    if(i<words.length-1) {
		sql+=",";
	    }
	}
	sql+=")";
	ResultSet rs=dbc.getResultBySql(sql);
	try {
	    while(rs.next()) {
		int sentid = rs.getInt("sentid");
		ids.add(sentid);
	    }
	} catch (SQLException e) {
	    System.out.println("querySentenceIds+++++++您的sql语句有错误");
	    return null;
	}
	return ids;
    }

    /**
     * 根据得到的语句编号，找到对应的语句词列，构造Sentence
     * @param ids
     * @return
     */
    private List<Sentence> querySentenceById(List<Integer> ids) {
	System.out.println("query "+ids.size());

	List<Sentence> sentences = new ArrayList<Sentence>();
	for (Integer integer : ids) {
	    List<Word> words = new ArrayList<Word>();
	    String sql = "select * from _tword where sentid="+integer;
	    ResultSet rs = dbc.getResultBySql(sql);
	    try {
		/**
		 * 遍历每个id得到的词语集，构成Sentence
		 */
		while(rs.next()) {
		    int wid = rs.getInt("wordid");
		    String text = rs.getString("text");
		    String pos = rs.getString("pos");
		    int parentid = rs.getInt("parentid");
		    String rel = rs.getString("rel");
		    int semid = rs.getInt("semid");
		    String semrel = rs.getString("semrel");
		    int sentid = rs.getInt("sentid");
		    Word wd = null;
		    if(Pos.wp.name().equalsIgnoreCase(pos)) {
			wd = new Wp(sentid,wid,text,Pos.getPos(pos),parentid,rel,semid,semrel);
		    } else
			wd = new Word(sentid,wid,text,Pos.getPos(pos),parentid,rel,semid,semrel);
		    words.add(wd);
		}
	    } catch (SQLException e) {
		/**
		 * 如果出错，整条句子作废
		 */
		words = null;
	    }
	    Sentence sent = new Sentence(words);
	    sentences.add(sent);
	}
	return sentences;
    }

    public List<String> querySentenceByStyleId(int styleid) throws SQLException {
	if(styleid==-1)
	    return null;
	String sql = "select * from sentence where style_sample_nowp_id ="+styleid;
	ResultSet rs = dbc.getResultBySql(sql);
	List<String> ints = new ArrayList<String>();
	while(rs.next()) {
	    int sentid = rs.getInt("id");
	    String context = rs.getString("context");
	    ints.add(sentid+"_:"+context);
	}
	return ints;
    }

    /**
     * 将一个Sentence的信息存入数据库中
     * @param sent
     */
    public void saveSentenceToDataBase(Sentence sent) {

    }
}
