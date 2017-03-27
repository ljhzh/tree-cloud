package sdu.wocl.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseConfig {

    static Properties prop;

    static {
	prop = new Properties();
	InputStream in = BaseConfig.class.getResourceAsStream("/Property.properties");
	try {
	    prop.load(in);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static String getString(String key) {
	return prop.getProperty(key);
    }
    
    public static String getPath(String key) {
	return BaseConfig.class.getResource("/static/").getFile().toString()+prop.getProperty(key);
    }

}
