package sdu.wocl.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocumentCounter {

    private File file;

    public DocumentCounter(File file) {
	this.file = file;
    }

    private boolean isDocumentType() {
	String name = file.getName();
	return name.endsWith("文") || name.endsWith("歌") || name.endsWith("信") || name.endsWith("话") || name.endsWith("记");
    }

    public Counter count() {
	if(isDocumentType()) {
	    //得到不同时期录入文档的名称
	   return new Counter(file.getName(),file.listFiles());
	}
	return null;
    }

   public class Counter {
        private String typeName;
	private List<String[]> fileNames = new ArrayList<String[]>();
	private int num;
	
	public Counter(String fileName,File[] listFiles) {
	    this.typeName = fileName;
	    for (File file : listFiles) {
		fileNames.add(file.list());
	    }
	    int count = 0;
	    for (String[] names : fileNames) {
		count+=names.length;
	    }
	    this.num = count;
	}

	public List<String[]> getFileNames() {
	    return fileNames;
	}
	
	public int getNum() {
	    return num;
	}
	
	public String getTypeName() {
	    return typeName;
	}
    }
}
