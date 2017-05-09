package sdu.wocl.algorithm.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import sdu.wocl.algorithm.tool.UsualTool;

/**
 * 根据特征数据，进行计算得到中间数据
 * 对model队列中的Model进行处理，
 * 将处理的数据用以计算构建一个特征model
 * @author ljh_2015
 *
 */
public class ComputingData {

    /*    //常用句式集
    private static List<Struct> structSet = new ArrayList<Struct>();

    //对句式集的计数
    private static List<Integer> counts = new ArrayList<Integer>();*/

    //公共域的ResultData
    private ResultData resultModel = null;

    private List<ResultData> singleResults = new ArrayList<ResultData>();

    private List<ResultData> singleResultsCopy = new ArrayList<ResultData>();

    //PreparedData队列
    public Queue<PreparedData> datas = new LinkedList<PreparedData>();

    public boolean mixualable = false;

    public void setName(String name) {
	resultModel = new ResultData(name);
    }
    
    /**
     * 接收一个model
     * @param model
     */
    public void receive(PreparedData model) {
	datas.offer(model);	
    }
    
    /**
     * 计算两篇文章的相似度
     * @param model_s
     * @param model_d
     */
    public ResultData compareModels(PreparedData model_s,PreparedData model_d) {
	ResultData r_s = new ResultData();
	r_s.setName(model_s.getName()+":"+model_d.getName()+" ");
	r_s.setKey("compare");
	r_s.setSysMap(model_s.getStableStructSize());
	r_s.setSv(model_s.setSVector());
	r_s.setAveMap(model_s.getMap());
	
	ResultData r_d = new ResultData();
	r_d.setSysMap(model_d.getStableStructSize());
	r_d.setSv(model_d.setSVector());
	r_d.setAveMap(model_d.getMap());
	
	r_s.mixual(r_d);
	return r_s;
    }

    //model接收处理器
    public void receiveAndProcedure(ResultData r) {
	PreparedData model = datas.poll();
	Map<String,Double> map = model.getMap();
	Map<String,Integer> sty = model.getStableStructSize();
	//设置标号
	r.setAveMap(map);
	r.setName(model.getName());
	r.setCode(singleResults.size());
	r.setSysMap(sty);
	r.setSv(model.setSVector());
	singleResults.add(r);
    }

    //得到二维数组
    public double[][] eachDistanceMixual() {
	int range = singleResults.size();
	double[][] ranges = new double[range][range];
	for (int i=0;i<range;i++) {
	    for(int j=i+1;j<range;j++) {
		//System.out.println("\n相似度"+i+":"+j+" "+CosineSimilarity(singleResults.get(i), singleResults.get(j)));
		ranges[i][j]=singleResults.get(i).mixual(singleResults.get(j));
		System.out.print(ranges[i][j]+"\t");
	    }
	    System.out.println("\t");
	}
	return ranges;
    }
    
    public double[][] eachSimilarityMixual() {
	int range = singleResults.size();
	double[][] ranges = new double[range][range];
	for (int i=0;i<range;i++) {
	    for(int j=i;j<range;j++) {
		ranges[i][j]=CosineSimilarity(singleResults.get(i), singleResults.get(j));
		System.out.print(ranges[i][j]+"\t");
	    }
	    System.out.println("\t");
	}
	return ranges;
    }

    public double CosineSimilarity(ResultData sr,ResultData de) {
	double srl = sr.getSv().getSelfDistance();
	double del = de.getSv().getSelfDistance();
	int[] srx = sr.getSv().getVector();
	int[] dex = de.getSv().getVector();
	double sum = 0;
	for (int i = 0; i < dex.length; i++) {
	    sum+=dex[i]*srx[i];
	}
	return sum/(del*srl);
    }

    public void mixual() {
	if(resultModel==null) {
	    resultModel = new ResultData();
	}
	for (ResultData re : singleResults) {
	    resultModel.mixual(re);
	    singleResultsCopy.add(re);
	}
	List<String> str = resultModel.getCommonStyle();
	Collections.sort(str,new Comparator<String>() {
	    @Override
	    public int compare(String o1, String o2) {
		return resultModel.getStyleMap().get(o1)<resultModel.getStyleMap().get(o2)?1:-1;
	    }

	});
	System.out.println("======常用句式======");
	for (String string : str) {
	    try {
		System.err.println(UsualTool.getStyleFromValue(string.substring(8))+":"+resultModel.getStyleMap().get(string));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
    
    
    public ResultData getResultModel() {
        return resultModel;
    }
    public List<ResultData> getSingleResults() {
        return singleResults;
    }
    
    
}
