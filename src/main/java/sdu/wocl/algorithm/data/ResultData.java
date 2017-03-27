package sdu.wocl.algorithm.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sdu.wocl.algorithm.StyleVector;

/**
 * 处理中间数据，得到需要展示出来的数据
 * weight的权重平衡根据句式常用度来调整
 * @author ljh_2015
 *
 */
public class ResultData {

    //标识
    private int code;
    
    private int docNum;
    //表示哪一类文档的模型
    private String name;
    //平均依赖关系的比重值
    private Map<String,Double> aveMap = new LinkedHashMap<String,Double>();
    //统计句式出现的次数
    private Map<String,Integer> styCount = new LinkedHashMap<String,Integer>();
    //常用句式
    private List<String> commons = new ArrayList<String>();
    //设置稀少度，默认是0.2
    private double scarcity=0.2;
    //设置常用度，默认是0.8
    private double commonDegrees=0.8;

    private StyleVector sv = new StyleVector();

    //记录权重
    //private int[] weight;

    public ResultData() {

    }

    public void printChart() {
	PrintChart.print(this,false);
	PrintChart.print(this, true);
    }

    public void setSv(StyleVector sv) {
	this.sv = sv;
    }

    /**
     * 构造方法
     * @param name
     */
    public ResultData(String name) {
	this.name = name;
    }

    public void setProportion(double sc,double co) {
	this.scarcity = sc;
	this.commonDegrees = co;
    }

    //合并融合
    public double mixual(ResultData result) {
	if(result!=null) {
	    //收录的数目增加
	    mixualAve(result.getAveMap());
	    mixualStyle(result.getStyleMap());
	    sv.merge(result.getSv());
	    docNum++;
	    //setSv(result.getSv());
	    //System.err.println(getCode()+" and "+result.getCode()+":"+getSv().euclidean(result.getSv()));
	    //weight = getSv().mix(result.getSv());
	}
	return getSv().euclidean(result.getSv());
    }

    public StyleVector getSv() {
	return sv;
    }

    private void mixualStyle(Map<String,Integer> map) {
	for (String key : map.keySet()) {
	    if(styCount.containsKey(key)) {
		styCount.put(key, styCount.get(key)+map.get(key));
	    } else {
		styCount.put(key, map.get(key));
	    }
	}
    }

    private void mixualAve(Map<String,Double> map) {
	for (String key : map.keySet()) {
	    if(aveMap.containsKey(key)) {
		aveMap.put(key, aveMap.get(key)+map.get(key));
	    } else
		aveMap.put(key, map.get(key));
	}
    }

    public Map<String, Double> getAveMap() {
	return aveMap;
    }

    public Map<String, Integer> getStyleMap() {
	return styCount;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setAveMap(Map<String, Double> aveMap) {
	this.aveMap = aveMap;
    }

    public void setSysMap(Map<String,Integer> sysmap) {
	this.styCount = sysmap;
    }

    //记录句式结构出现的次数
    public void count(String style) {
	if(styCount.containsKey(style)) {
	    styCount.put(style,styCount.get(style)+1);
	} else
	    styCount.put(style, 1);
    }

    //获取常用句式
    public List<String> getCommonStyle() {
	int size = styCount.values().size();
	System.err.println("总共"+size+"种句式");
	int low = (int)(size*0.1);
	int hight = (int)(size*0.2);
	boolean flag = true;
	int limit = 0; 
	while(flag) {
	    if(size!=0 && limit<100) {
		limit++;
		if(commons.size()<low) {
		    commonDegrees*=0.8;
		    getCommon();
		} else if(commons.size()>hight) {
		    commonDegrees+=0.01;
		    getCommon();
		} else
		    flag = false;
	    } else
		flag = false;

	}
	return commons;
    }

    private void getCommon() {
	//结果集中句式种类的数量
	commons.clear();
	int sum = 0;
	for (Integer in : styCount.values()) {
	    sum+=in;
	}
	for (String s:styCount.keySet()) {
	    int in = styCount.get(s);
	    if(in/(1.0*sum)>commonDegrees) {
		commons.add(s);
	    }
	}
    }

    //获取稀少句式
    public List<String> getScarcityStyle() {
	List<String> scarcitys = new ArrayList<String>();
	int sum = 0;
	for (Integer in : styCount.values()) {
	    sum+=in;
	}
	for (String s:styCount.keySet()) {
	    int in = styCount.get(s);
	    if(in/(1.0*sum)<scarcity) {
		scarcitys.add(s);
	    }
	}
	return scarcitys;
    }

    public String getCode() {
	return "No."+code+" result";
    }

    public void setCode(int code) {
	this.code = code;
    }

    public int getDocNum() {
        return docNum;
    }

}
