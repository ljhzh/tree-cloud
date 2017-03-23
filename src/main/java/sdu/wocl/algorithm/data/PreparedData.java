package sdu.wocl.algorithm.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sdu.wocl.algorithm.StyleVector;
import sdu.wocl.algorithm.elements.Model;
import sdu.wocl.algorithm.elements.Struct;

/**
 * 数据预节点，用以提取源数据中需要的特征数据
 * 预处理类，里面包含源数据，然后将其进行处理之后，送到计算节点
 * @author ljh_2015
 *
 */
public class PreparedData {
    //String:句式结构主干中的某种结构
    //Double:这种结构在整个文档分析出现的频率
    private Map<String,Double> map = new LinkedHashMap<String,Double>();
    //每个预处理Data都包含一个Model model=>一篇Document的信息
    private Model model;
    //string:数字,List<Struct>:这种句式有多少种
    private Map<String,List<Struct>> sys = null;
    
    private StyleVector sv = new StyleVector();
    
    public StyleVector setSVector() {
	List<String> lines = model.getStyleline();
	for (int i = 0; i < lines.size(); i++) {
	    int temp = StyleMapps.getString(lines.get(i));
	    if(temp!=-1)
		sv.countVec(temp);
	    else
		StyleMapps.unRecordGet(lines.get(i));
	}
	return sv;
    }
    
    public PreparedData setPreparedData(Model model) {
	this.model = model;
	this.sys = model.queryStruct();
	return this;
    }

    public void getPercentage() throws IllegalArgumentException, IllegalAccessException {
	System.out.println("-----------正在进行依赖关系比例计算---------------");
	Map<String,Integer> mapping = model.getRels();
	int sum = 0;
	for(String s:mapping.keySet()) {
	    sum+=mapping.get(s);
	}
	for(String s:mapping.keySet()) {
	    double per = mapping.get(s)/(sum*1.0);
	    map.put(s, per);
	}
    }

    public void showPercentage() {
	for(String s:map.keySet()) {
	    System.out.println(s+" "+map.get(s));
	}
    }
    
    public Map<String,Integer> getStyleSize() {
	Map<String,Integer> map = new LinkedHashMap<String, Integer>();
	for (String s : sys.keySet()) {
	    map.put(s, sys.get(s).size());
	}
	return map;
    }
    
    public String getName() {
	return model.getName();
    }
    
    public Map<String,Double> getMap() {
	return map;
    }

    public void push(ComputingData c) {
	c.receive(this);
    }
    
}
