package sdu.wocl.dataFactory.entity.wordtree.tools;

import sdu.wocl.dataFactory.entity.wordtree.WordTreeMessage.MessageGroup;

/**
 * 简化算法
 * @author ljh_2015
 *
 */
public class Simplification {

    /**
     * 将句式结构简化处理
     * @param style
     * @param num
     * @param pos
     * @return
     */
    public static String[] simpliStyle(MessageGroup group) {
	String style = group.getStyle();
	String num = group.getNum();
	String pos = group.getPos();
	if(style.trim().equalsIgnoreCase("")) {
	    String[] nullString = new String[3];
	    nullString[0]="";
	    nullString[1]="[()]";
	    nullString[2]="[()]";
	    return nullString;
	}
	String[] RELs = style.split("_");
	String[] NUMs = num.substring(1, num.length()-1).split(",");
	String[] POSs = pos.substring(1,pos.length()-1).split(",");

	String Result="",ResultNum="[(",ResultPos="[(";
	String temp="";
	int count = 0;
	int wordcount=0;
	for(int i=0;i<RELs.length;i++) {
	    if(RELs[i].equalsIgnoreCase("wp"))
		continue;
	    if(temp.equalsIgnoreCase("")) {
		temp = RELs[i];
		count=0;
		wordcount++;
	    }
	    if(!temp.equalsIgnoreCase(RELs[i])) {
		Result+=temp;
		ResultNum+=")";
		ResultPos+=")";
		temp = RELs[i];
		wordcount++;
		count=0;
		if(i!=RELs.length) {
		    Result+="_";
		    ResultNum+=",(";
		    ResultPos+=",(";
		}
	    }
	    if(count!=0) {
		ResultNum+=",";
		ResultPos+=",";
	    }
	    ResultNum+=NUMs[i];
	    ResultPos+=POSs[i];
	    count++;
	}

	if(wordcount!=0) {
	    Result+=temp;
	    ResultNum+=")]";
	    ResultPos+=")]";
	} else {
	    Result="";
	    ResultNum="[]";
	    ResultPos="[]";
	}
	String[] str= new String[3];
	str[0]=Result;
	str[1]=ResultNum;
	str[2]=ResultPos;
	return str;
    }
}
