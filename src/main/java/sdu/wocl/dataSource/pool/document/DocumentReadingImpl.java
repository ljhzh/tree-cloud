package sdu.wocl.dataSource.pool.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sdu.wocl.dataSource.pool.document.Document.DocumentReading;

public class DocumentReadingImpl implements DocumentReading {

    @Override
    public List<InputStream> reading(List<File> dir) throws FileNotFoundException {
	if(dir==null)
	    return null;
	List<InputStream> inlist = new ArrayList<InputStream>();
	for(File file:dir) {
	    for(File xmls:file.listFiles()) {
		FileInputStream fis = new FileInputStream(xmls);
		inlist.add(fis);
	    }
	}
	if(inlist.size()>0)
	    return inlist;
	return null;
    }

}
