package sdu.wocl.dataFactory.proxy;

import sdu.wocl.dataFactory.database.FilterDatabaseServer;
import sdu.wocl.dataFactory.server.FilterServer;
import sdu.wocl.dataSource.server.DataSourceManager;

public class ProxyFilterAdapter implements ProxyFilter{

    @Override
    public void FilteringSource(DataSourceManager data, FilterServer filter) {
	
    }

    @Override
    public void FilteringDatabase(FilterServer filter,
	    FilterDatabaseServer database) {
	
    }

}
