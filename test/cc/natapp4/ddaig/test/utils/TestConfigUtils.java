package cc.natapp4.ddaig.test.utils;

import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;

import cc.natapp4.ddaig.utils.ConfigUtils;

public class TestConfigUtils {

	@Test
	public  void testGetProperties(){
		Properties p = ConfigUtils.getProperties("wxConfig/weixin.properties");
		System.out.println("公众号的token是"+p.get("token"));
	}
	
	@Test
	public  void  testGetProperties2(){
		Properties p = ConfigUtils.getProperties("wxConfig/projectTypes.properties");
		Enumeration<String> propertyNames = (Enumeration<String>) p.propertyNames();
		while (propertyNames.hasMoreElements()){
			String key = propertyNames.nextElement();
			System.out.println(p.getProperty(key));
		}
		
	}
}
