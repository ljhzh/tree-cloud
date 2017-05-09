package sdu.wocl.algorithm.tool;

import java.awt.Font;
import java.io.File;
import java.util.UUID;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import sdu.wocl.algorithm.data.ResultData;
import sdu.wocl.tool.BaseConfig;

/**
 * 利用jfreechart 对每类文章进行jfreechart
 * @author ljh_2015
 *
 */
public class PrintChart {

    public static void print(ResultData r,boolean isChart) {
	String title = r.getName();
	UUID id = UUID.randomUUID();
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	int[] num;
	if(isChart) {
	    title+="_count_";
	    num = r.getSv().getVector();
	} else {
	    title+="_num_";
	    num = r.getSv().getUnCountVector();
	}
	for(int i=1;i<num.length;i++) {
	    dataset.addValue(num[i], "num", "s"+i);
	}

	JFreeChart chart=ChartFactory.createBarChart3D("", "句式结构", "数量", dataset, PlotOrientation.VERTICAL, true, true, true);
	Font font = new Font("宋体",Font.BOLD,16);
	TextTitle titles = new TextTitle("句式结构统计图，共收录"+r.getDocNum()+"篇"+title+"文章",font);
	chart.setTitle(titles);
	CategoryPlot plot = chart.getCategoryPlot();
	NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
	CategoryAxis domainAxis = plot.getDomainAxis();
	domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
	domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
	numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
	numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
	chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
	try {
	    File file = new File(BaseConfig.getPath("ltp.document.png.save")+title+"_"+id.toString()+".png");
	    ChartUtilities.saveChartAsPNG(file,chart,1600,500);//把报表保存为文件
	    System.out.println("保存成功");
	}catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
