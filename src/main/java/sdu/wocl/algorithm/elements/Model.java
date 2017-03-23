package sdu.wocl.algorithm.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sdu.wocl.algorithm.tool.UsualFinal;
import sdu.wocl.algorithm.tool.UsualTool;

/**
 * 一篇文章对应一个model
 * 统计文档主干句式信息
 * 检测常用定式结构
 * 检测常用句式
 * @author ljh_2015
 *
 */
public class Model {

    //最大矩阵秩
    int rank_max;

    //统计主干句式关系的个数
    int sumRel=0;
    //依存数组，保存这个模式下常用的依存关系
    Map<String,Integer> relsTeam = new TreeMap<String,Integer>();    

    //依存列
    int[] stygline;
    
    //文档名
    String Name;
    
    //词性列 三维
    List<String[]> poss = new ArrayList<String[]>();
    //数目列 二维
    List<Integer[]> nums = new ArrayList<Integer[]>();
    //依存关系列矩阵 一维
    List<Integer[]> glines = new ArrayList<Integer[]>();
    //依存关系列字符串
    List<String> slines = new ArrayList<String>();
    List<String> styleLines = new ArrayList<String>();
    
    //没有记录下来的依存关系
    static List<String> unRecord = new ArrayList<String>();

    private void getOneInstanceTh(String[] pos) {
	if(pos==null){
	    return;
	}
	String[] p = new String[pos.length];
	for (int i = 0; i < p.length; i++) {
	    p[i]=pos[i];
	}
	poss.add(p);
    }

    private void getOneInstanceSe(int[] num) {
	if(num==null) {
	    return;
	}
	Integer[] p = new Integer[num.length];
	for (int i = 0; i < p.length; i++) {
	    p[i]=num[i];
	}
	nums.add(p);
    }

    private void getOneInstance(String[] str) throws Exception {
	getCollection(str[0]);
	getOneInstanceFi(UsualTool.getStringGroupFromStyle(str[0]));
	getOneInstanceSe(UsualTool.getIntegerGroupFromStyleNum(str[1]));
	getOneInstanceTh(UsualTool.getStringGroupFromStylePos(str[2]));
    }
    
    private void getCollection(String sty) {
	styleLines.add(sty);
    }

    /**
     * 如果每个句式被遍历过去，则在数据中进行统计，总数+1，map对应的key值上+1,
     * 如果不存在key，则创建key，并
     * @param str
     */
    private void getOneInstanceFi(String[] str) {
	//判定最大秩数
	if(str==null) {
	    if(relsTeam.containsKey("null")) {
		relsTeam.put("null", relsTeam.get("null")+1);
	    } else
		relsTeam.put("null", 1);
	} else {
	    rank_max=rank_max>str.length?rank_max:str.length;

	    Integer[] gline = new Integer[str.length];
	    for (int i=0;i<str.length;i++) {
		if(i<=str.length-1) {
		    String s = str[i];
		    //总数+1
		    sumRel++;
		    try {
			slines.add(s);
			gline[i] = UsualFinal.getInteger(s);
			//记录未记录的依存关系
			if(gline[i]==-1 && !unRecord.contains(s)) {
			    unRecord.add(s);
			    System.out.println(s.toUpperCase()+" is not collected");
			}
		    } catch (IllegalArgumentException e) {
			e.printStackTrace();
		    } catch (IllegalAccessException e) {
			e.printStackTrace();
		    }
		    if(relsTeam.containsKey(s)) {
			relsTeam.put(s, relsTeam.get(s)+1);
		    } else
			relsTeam.put(s, 1);
		} else
		    gline[i]=0;
	    }
	    glines.add(gline);
	}
    }

    /**
     * 对Message的style进行提取特征
     * @param style
     */
    public void instanceStyle(String[] style) {
	try {
	    getOneInstance(style);
	} catch (Exception e) {
	    //如果style为" "
	    //System.out.println("-----该句的主干句式没有修饰词-----");
	    e.printStackTrace();
	}
    }

    /**
     * 对那些没有进行记录的依赖关系进行存储
     * 暂存在数组中
     */
    public void queryUnRecordRel() {
	for (String unr : unRecord) {
	    System.out.print(unr+" ");
	}
	System.out.print("\n");
    }

    public void queryRels() {
	//对map进行重新排序
	UsualTool.querySortMaps(relsTeam);
    }

    public void modelShow() {
	for (int j=0;j<glines.size();j++) {
	    for (int i = 0; i < rank_max; i++) {
		if(i>glines.get(j).length-1) {
		    System.out.print(-1+" ");
		} else
		    System.out.print(glines.get(j)[i]+" ");
	    }

	    System.out.print("  |  ");

	    for (int i = 0; i < rank_max; i++) {
		if(i>nums.get(j).length-1) {
		    System.out.print(-1+" ");
		} else
		    System.out.print(nums.get(j)[i]+" ");
	    }

	    System.out.print("  |  ");

	    for (int i = 0; i < rank_max; i++) {
		if(i>poss.get(j).length-1) {
		    System.out.print("空 ");
		} else {
		    if(poss.get(j)[i].equals("")) {
			System.out.print("nul ");
		    } else
			System.out.print(poss.get(j)[i]+" ");
		}
	    }

	    System.out.print("\n");
	}
    }

    public Map<String,List<Struct>> queryStruct() {
	StructSys sys = new StructSys();
	return sys.examing(glines, rank_max);
    }
    
    /**
     * 获取句式列
     * @return
     */
    public List<String> getSline() {
	return slines;
    }
    
    public List<String> getStyleline() {
	return styleLines;
    }

    /**
     * 定式结构
     * @author ljh_2015
     *
     */
    class StructSys {

	Map<String,List<Struct>> structMapping = new HashMap<String,List<Struct>>();
	/**
	 * 检测矩阵
	 */
	public Map<String,List<Struct>> examing(List<Integer[]> ran,int rank) {
	    /**
	     * 由句式结构长度从低到高检测
	     */
	    for(int i=0;i<=rank;i++) {
		for(int j=0;j<ran.size();j++) {
		    Integer[] ints = ran.get(j);
		    int length = 0;
		    /**
		     * 单词成句，或者熟句主干结构为空，默认为0
		     */
		    if(ints!=null) {
			length = ints.length;
		    }
		    if(i==length) {
			Struct str = new Struct(ints);
			String key = str.toString();

			if(structMapping.get(key)!=null) {
			    List<Struct> list = structMapping.get(key);
			    list.add(str);
			    structMapping.put(key,list);
			} else {
			    List<Struct> list = new ArrayList<Struct>();
			    list.add(str);
			    structMapping.put(str.toString(), list);
			}
		    }
		}
	    }
	    return structMapping;
	}
    }
    
    public Map<String,Integer> getRels() {
	return relsTeam;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
