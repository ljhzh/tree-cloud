package sdu.wocl.dataFactory.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdu.wocl.dataFactory.server.FilterServer;
import sdu.wocl.dataSource.server.DataSourceManager;

public class BeforeFilter extends ProxyFilterAdapter {

    private final static Logger logger = LoggerFactory.getLogger(BeforeFilter.class);
    private List<String> titles = new ArrayList<String>();
    
    private DataSourceManager dataServer;
    
    private FilterServer filterServer;
    
    public BeforeFilter(List<String> titles) {
	dataServer = new DataSourceManager();
	filterServer = new FilterServer();
	setTitles(titles);
    }
    
    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public void setDataServer(DataSourceManager dataServer) {
        this.dataServer = dataServer;
    }

    public void setFilterServer(FilterServer filterServer) {
        this.filterServer = filterServer;
    }

    @Override
    public void FilteringSource(DataSourceManager data, FilterServer filter) {
	try {
	    List<InputStream> list = data.processing().findDocumentBytitle(titles);
	    if(list==null) {
		return;
	    }
	    filter.receive(list);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
