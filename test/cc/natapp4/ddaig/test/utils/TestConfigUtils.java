package cc.natapp4.ddaig.test.utils;

import java.util.Properties;

import org.junit.Test;

import cc.natapp4.ddaig.utils.ConfigUtils;

public class TestConfigUtils {

	@Test
	public  void testGetProerties(){
		Properties p = ConfigUtils.getProperties("wxConfig/weixin.properties");
		System.out.println("公众号的token是"+p.get("token"));
	}
}
