package sdu.wocl.dataFactory.proxy;

import sdu.wocl.dataFactory.database.FilterDatabaseServer;
import sdu.wocl.dataFactory.server.FilterServer;
import sdu.wocl.dataSource.server.DataSourceManager;

/**
 * 过滤器代理
 * @author ljh_2015
 *
 */
public interface ProxyFilter {
    
    public void FilteringSource(DataSourceManager data,FilterServer filter);
    
    public void FilteringDatabase(FilterServer filter,FilterDatabaseServer database);
    
}
