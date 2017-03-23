package sdu.wocl.dataSource.pool.document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sdu.wocl.dataSource.pool.document.Document.DocumentStorage;

public class DocumentStorageImpl implements DocumentStorage {

    @Override
    public boolean LocalStorage(int index,File dir, List<InputStream> in) throws IOException {
	if(in==null)
	    return false;
	if(dir==null)
	    return false;
	for (InputStream inputStream : in) {
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    File file = new File(dir,dir.getName()+"_("+index+").xmls");
	    FileWriter fw = new FileWriter(file,true);
	    Reader reader = new InputStreamReader(inputStream);
	    StringBuilder out = new StringBuilder();
	    char[] buffer = new char[1024];
	    int rsz = 0;
	    while((rsz=reader.read(buffer))>0) {
		out.append(buffer,0,rsz);
	    }	    
	    fw.write(out.toString());
	    inputStream.close();
	    reader.close();
	    fw.close();
	}
	return true;
    }

    private String dateString() {
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmssSSS");
	String dirName = sdf.format(date);
	return dirName;
    }

}
