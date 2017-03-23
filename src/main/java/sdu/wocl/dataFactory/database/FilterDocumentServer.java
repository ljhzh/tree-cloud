package sdu.wocl.dataFactory.database;

import java.io.InputStream;
import java.util.List;

/**
 * 文档库过滤器
 * 文档库 与数据哭共同构建成数据收集库
 * @author ljh_2015
 *
 */
public class FilterDocumentServer {
    
    private List<InputStream> inputStreams;
    
    public FilterDocumentServer(List<InputStream> inputStreams) {
	this.inputStreams = inputStreams;
    }
    
    public List<InputStream> getDocumentInputStreams() {
	return inputStreams;
    }
    
}
