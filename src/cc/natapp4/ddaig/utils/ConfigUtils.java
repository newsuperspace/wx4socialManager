package cc.natapp4.ddaig.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 收纳了专门用于读写配置file的工具类
 * @author Administrator
 *
 */
public class ConfigUtils {

	/**
	 * 用于加载Classpath根目录下的properties
	 * @param classpathFileName   相对于classpath的相对路径，如“wxConfig/weixin.properties”
	 * @return  Properties对象
	 */
	public static Properties  getProperties(String classpathFileName){
		
		Properties p  =  new Properties();
		/*
		 * 	 以ClassLoader类加载器的getResourceAsStream()加载的磁盘file的路径根目录默认是classpath
		 *  而如果你是以this.getClass.getResourceAsStream()加载的磁盘file的路径根目录默认是this类同级目录
		 *  这一点需要特别注意 ★★★
		 */
		InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream(classpathFileName);
		
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
	
}
