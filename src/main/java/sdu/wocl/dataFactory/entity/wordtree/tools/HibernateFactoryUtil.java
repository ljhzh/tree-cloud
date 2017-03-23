package sdu.wocl.dataFactory.entity.wordtree.tools;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class HibernateFactoryUtil {

    private Connection connection;
    private String url;
    private String username;
    private String password;
    private String driver;

    public Connection getConnection() {
	try {
	    Properties property = new Properties();
	    InputStream in = getClass().getResourceAsStream("/Property.properties");
	    property.load(in);
	    this.username = property.getProperty("username").trim();
	    this.password = property.getProperty("password").trim();
	    this.url = property.getProperty("url").trim();
	    this.driver = property.getProperty("Driver").trim();
	    Class.forName(driver);
	    connection = DriverManager.getConnection(url, username, password);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if(connection!=null)
	    return connection;
	return null;
    }

    public void closeConnection(){
	try {
	    connection.close();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
