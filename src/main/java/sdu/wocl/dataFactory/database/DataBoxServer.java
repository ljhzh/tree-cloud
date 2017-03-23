package sdu.wocl.dataFactory.database;

import java.io.InputStream;
import java.util.List;

public class DataBoxServer {

    public static FilterDocumentServer setDocumentsServer(List<InputStream> inputStream) {
	return new FilterDocumentServer(inputStream);
    }

    public static FilterDatabaseServer setDatabaseServer() {
	return new FilterDatabaseServer();
    }

    /**
     * 数据库与文档库之间的相互转换
     * 文档库-->数据库
     * @param filterDocumentServer
     * @param filterDatabaseServer
     */
    public static void checkoutDoToDb(FilterDocumentServer filterDocumentServer,FilterDatabaseServer filterDatabaseServer) {

    }

    public static void checkoutDbToDo(FilterDatabaseServer filterDatabaseServer,FilterDocumentServer filterDocumentServer) {

    }

}
