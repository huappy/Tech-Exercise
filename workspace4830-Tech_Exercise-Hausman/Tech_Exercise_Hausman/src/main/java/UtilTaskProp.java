import java.util.Properties;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;

public class UtilTaskProp{
	static Properties prop = new Properties();
	
	public static void loadProperty(ServletContext servletContext) throws Exception{
		String filePath = "/WEB-INF/config.properties";
		InputStream inputstream = servletContext.getResourceAsStream(filePath);
		
		System.out.println("[DBG] Loaded: " + new File(filePath).getAbsolutePath());
		prop.load(inputstream);
	}
	
	public static String getProp(String key) {
		return prop.getProperty(key).trim();
	}
}