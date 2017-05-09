package sdu.wocl.algorithm;

import java.util.ArrayList;
import java.util.List;

import sdu.wocl.algorithm.data.ComputingData;
import sdu.wocl.algorithm.data.PreparedData;
import sdu.wocl.algorithm.data.ResultData;
import sdu.wocl.algorithm.elements.DocumentInstance;
import sdu.wocl.algorithm.elements.Model;
import sdu.wocl.algorithm.tool.UsualTool;
import sdu.wocl.algorithm.view.XYPoint;
import sdu.wocl.dataFactory.entity.Sentence;
import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage;

public class HandleAdapter<P extends PreparedData,C extends ComputingData> extends DocumentInstance{

    private P p;
    private C c ;
    
    private int count = 1;
    private String type;
    private List<String> titles = new ArrayList<String>();
    
    //欧式距离矩阵
    private double[][] distances = null;
    private double[][] similarity = null;

    public HandleAdapter<P, C> showing() {
	show();
	return this;
    }
    
    public void setComputing(C c) {
	this.c = c;
    }

    public void setDatas(P p) {
	this.p = p;
    }
    
    public void setTitle(String title) {
	titles.add(title);
    }

    public double[][] getDistances() {
        return distances;
    }
    
    public double[][] getSimilarity() {
        return similarity;
    }

    public HandleAdapter<P, C> setType(String type) {
        this.type = type;
        return this;
    }

    public ResultData getResultData() {
	return c.getResultModel();
    }
    
    public List<ResultData> getResultDataEach() {
	return c.getSingleResults();
    }

    /**
     * 预处理算法
     */
    @Override
    protected void preparedProcessing(Model model) {
	p.setPreparedData(model);
	System.out.println("--------已经得到预处理数据--------");
    }

    /**
     * 计算算法
     */
    @Override
    protected void computingProcessing() {
	if(p!=null) {
	    try {
		p.getPercentage();
		p.push(c);
		c.receiveAndProcedure(new ResultData());
		System.out.println("-------得到计算结果 -----------");
	    } catch (Exception e) {
		e.printStackTrace();
		System.err.println("-------计算发生故障------------");
	    }
	}
    }

    /**
     * 收集算法
     */
    @Override
    protected void resultProcessing() {
	if(c.datas.isEmpty()) {
	    c.mixual();
	    System.out.println("==============");
	    distances = c.eachDistanceMixual();
	    similarity = c.eachSimilarityMixual();
	    c.getResultModel().printChart();
	}
    }
    
    public List<XYPoint> getPointList() throws InstantiationException, IllegalAccessException {
	List<XYPoint> points = new ArrayList<XYPoint>(); 
	for (int i = 0; i < distances.length; i++) {
	    for (int j = 0; j < distances.length; j++) {
		XYPoint x = new XYPoint();
		x.setType(this.type);
		x.setTitle(titles.get(i)+" : "+titles.get(j));
		x.setData(distances[i][j],similarity[i][j]);
		points.add(x);
	    }
	}
	return points;
    }
    

    /**
     * 反馈算法
     */
    @Override
    protected void feedback() {
	//System.out.println("----------反馈机制正在反馈---------");
    }

}
