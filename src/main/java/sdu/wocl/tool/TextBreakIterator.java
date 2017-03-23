package sdu.wocl.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 文档分句器
 * @author ljh_2015
 *
 */
public class TextBreakIterator {
    
    public static String[] TextBreak(String text) {
	BreakIterator bi = BreakIterator.getSentenceInstance(Locale.CHINA);
	bi.setText(text);
	
	int bindex = bi.first();
	List<Integer> br = new ArrayList<Integer>();
	while(bindex != bi.DONE) {
	    br.add(bindex);
	    bindex = bi.next();
	}
	String[] vars = new String[br.size()];
	for(int i=0,j=0;i<br.size();i++) {
	    vars[i]=text.substring(j,br.get(i));
	    if(i>0 && vars[i].endsWith("\\t\\n")) {
		vars[i]=vars[i].substring(0,vars.length-2);
	    }
	    j=br.get(i);
	}
	return vars;
    }
    
    public static String[] TextBreak(File file) {
	StringBuilder temp = new StringBuilder();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    String str=null;
	    while((str=br.readLine())!=null) {
		temp.append(str);
	    }
	    return TextBreak(temp.toString());
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} 
	return null;
    }
}
