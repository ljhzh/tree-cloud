package sdu.wocl.algorithm.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

/**
 * 常用工具
 * @author ljh_2015
 *
 */
public class UsualTool {

    /**
     * 将List<Sentence> 转换成 List<WordTreeMessage> 
     * 将获取的基本信息全部封装成List<WordTreeMessage>
     * @param sents 根据源数据得到的Sentence数据结构列表
     * @return
     */
    public static final List<WordTreeMessage> getMessagesFromSentences(List<Sentence> sents) {
	List<WordTreeMessage> list = new ArrayList<WordTreeMessage>();
	if(sents==null) {
	} else	
	    for (Sentence sentence : sents) {
		list.add(sentence.WORDTREE.getMessage());
	    }
	return list;
    }
    
    /**
     * 将信息中表达句式的字符串转换为数组
     * @param style
     * @return
     * @throws Exception
     */
    public static final String[] getStringGroupFromStyle(String style) throws Exception {
	if(style.equals(" ")) 
	    return null;
	String[] strs = style.split("_");
	return strs; 
    }
    
    /**
     * 将信息中表达句式的字符串转换为数组
     * @param style
     * @return
     * @throws Exception
     */
    public static final String getStyleFromValue(String values) throws Exception {
	if(values.equals(" ")) 
	    return "null";
	String[] value =values.split(" ");
	String strs="";
	for (int i=0;i<value.length;i++) {
	    strs+=UsualFinal.getString(Integer.parseInt(value[i]));
	    if(i!=value.length-1) {
		strs+="_";
	    }
	}
	return strs; 
    }
    
    public static final String[] getStringGroupFromStylePos(String pos) {
	if(pos.length()<3) {
	    return null;
	} else {
	    String temp = pos.substring(1,pos.length()-1);
	    String[] sp = temp.split(",");
	    String[] re = new String[sp.length];
	    for (int i = 0; i < re.length; i++) {
		re[i]=sp[i].replace("(", "").replace(")", "");
	    }
	    return re;
	}
    }
    
    public static final int[] getIntegerGroupFromStyleNum(String num) {
	if(num.length()<3) {
	    return null;
	} else {
	    String temp = num.substring(1,num.length()-1);
	    String[] sp = temp.split(",");
	    int[] re = new int[sp.length];
	    for (int i = 0; i < re.length; i++) {
		if(sp[i].length()>2)
		    re[i]=Integer.parseInt(sp[i].substring(1,sp[i].length()-1));
		else
		    re[i]=0;
	    }
	    return re;
	}
    }
    
    //对map排序遍历
    public static final <T> void querySortMaps(Map<T, Integer> relsTeam) {
	//对map进行重新排序
	List<Map.Entry<T, Integer>> list = new ArrayList<Map.Entry<T,Integer>>(relsTeam.entrySet());
	Collections.sort(list,new Comparator<Map.Entry<T,Integer>>() {
	    public int compare(Entry<T, Integer> o1,
		    Entry<T, Integer> o2) {
		return o1.getValue()<o2.getValue()?1:-1;
	    }

	});
	for (Map.Entry<T, Integer> mapping : list) {
	    System.out.println(mapping.getKey()+" :"+mapping.getValue());
	}
    }
    
  //对map排序遍历
    public static final <T> void querySortDoubleMaps(Map<T,Double> map) throws IllegalArgumentException, IllegalAccessException {
	//对map进行重新排序
	List<Map.Entry<T, Double>> list = new ArrayList<Map.Entry<T,Double>>(map.entrySet());
	Collections.sort(list,new Comparator<Map.Entry<T,Double>>() {
	    public int compare(Entry<T, Double> o1,
		    Entry<T, Double> o2) {
		return o1.getValue()<o2.getValue()?1:-1;
	    }

	});
	for (Map.Entry<T, Double> mapping : list) {
	    System.out.println(mapping.getKey()+"-"+UsualFinal.getInteger(mapping.getKey().toString())+" :"+mapping.getValue());
	}
    }
    
    public static final <T, V> void querySortListMaps(Map<T,List<V>> map) {
	//对map进行重新排序
	List<Map.Entry<T, List<V>>> list = new ArrayList<Map.Entry<T,List<V>>>(map.entrySet());
	Collections.sort(list,new Comparator<Map.Entry<T,List<V>>>() {
	    public int compare(Entry<T, List<V>> o1,
		    Entry<T, List<V>> o2) {
		return o1.getValue().size()<o2.getValue().size()?1:-1;
	    }
	});
	for (Map.Entry<T, List<V>> mapping : list) {
	    System.out.println(mapping.getKey()+" :"+mapping.getValue().size());
	}
    }

}
