package cc.natapp4.ddaig.test.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;

import cc.natapp4.ddaig.domain.Permission;
import cc.natapp4.ddaig.domain.PermissionLevel;
import cc.natapp4.ddaig.domain.PermissionType;

public class TestGson2Json {

	class Student {
		String name;
		int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

	/*
	 * 创建Gson的功能入口对象
	 */
	// 方式一
	Gson g = new Gson();
	// 方式二
	Gson g2 = new GsonBuilder().setLenient()// json宽松
			.enableComplexMapKeySerialization()// 支持Map的key为复杂对象的形式
			.serializeNulls() // 智能null
			.setPrettyPrinting()// 调教格式
			.disableHtmlEscaping() // 默认是GSON把HTML 转义的
			.create();

	// =============================在JavaBean与Json之间进行转换============================

	@Test
	public void testBean2Json() {
		Student s = new Student();
		s.setAge(10);
		s.setName("合金装备");

		String json = g.toJson(s, Student.class);
		System.out.println(json); // print {"name":"合金装备","age":10}
	}

	@Test
	public void testJson2Bean() {
		String json = "{\"name\":\"合金装备\",\"age\":10}";
		Student s = g.fromJson(json, Student.class);
		System.out.println(s.getAge() + s.getName()); // print 10合金装备
	}

	// ============================在Map容器与Json之间进行转换============================
	@Test
	public void testMap2Json() {
		Map<String, String> m = new HashMap<String, String>();
		m.put("合金装备", "12");
		m.put("细胞分裂", "14");
		m.put("生化危机", "16");
		m.put("变形金刚", "18");
		m.put("死亡空间", "20");
		String json = g.toJson(m);
		System.out.println(json); // print
									// {"死亡空间":"20","变形金刚":"18","细胞分裂":"14","合金装备":"12","生化危机":"16"}
	}

	@Test
	public void testJson2Map() {
		String json = "{\"死亡空间\":\"20\",\"变形金刚\":\"18\",\"细胞分裂\":\"14\",\"合金装备\":\"12\",\"生化危机\":\"16\"}";
		Type t = new TypeToken<HashMap<String, String>>() {
		}.getType();
		Map<String, String> fromJson = g.fromJson(json, t);

		Set<Entry<String, String>> entrySet = fromJson.entrySet();
		for (Entry<String, String> e : entrySet) {
			System.out.println(e.getKey() + ":" + e.getValue());
		}
		/*
		 * 死亡空间:20 变形金刚:18 细胞分裂:14 合金装备:12 生化危机:16
		 * 
		 */
	}

	// ============================在List容器与Json之间进行转换============================
	@Test
	public void testList2JsonAndJson2List() {
		List<Student> l = new ArrayList<Student>();
		for (int i = 0; i < 10; i++) {
			Student s = new Student();
			s.setAge(i);
			s.setName("name" + i);
			l.add(s);
		}
		String json = g.toJson(l);
		System.out.println(json);
		/*
		 * print
		 * 
		 * [{"name":"name0","age":0},{"name":"name1","age":1},{"name":"name2",
		 * "age":2},{"name":"name3","age":3},{"name":"name4","age":4},{"name":
		 * "name5","age":5},{"name":"name6","age":6},{"name":"name7","age":7},{
		 * "name":"name8","age":8},{"name":"name9","age":9}]
		 * 
		 */
		// 接下来再将JSon重新放入到List容器中
		// 通过JsonPrimitive可以实现对JSON字符处的转义字符的自动处理（当然也可以原文输出）
		JsonPrimitive jsonPrimitive = new JsonPrimitive(json);
		System.out.println("toString:" + jsonPrimitive.toString());
		System.out.println("getAsString:" + jsonPrimitive.getAsString());

		Type type = new TypeToken<ArrayList<Student>>() {
		}.getType();
		ArrayList<Student> fromJson = g.fromJson(json, type);
		for (Student s : fromJson) {
			System.out.println(s.getAge() + s.getName());
		}
	}

	@Test
	public void testSetToJson() {
		Set<Student> set = new HashSet<Student>();
		for (int i = 0; i < 10; i++) {
			Student s = new Student();
			s.setAge(i);
			s.setName("name" + i);
			set.add(s);
		}

		String json = g.toJson(set);
		System.out.println(json);
	}

	// ===========================读取*.json文件并解析其中的JSON字符串=============================
	@Test
	public void parseJsonFile() throws IOException {
		InputStream is = null;

		/**
		 * class.getClassLoader().getResourceAsStream() 是用于加载项目中资源的最佳方式
		 * 给他传递的参数是基于classpath类路径根目录的相对路径字符串，
		 * 当前项目由于是基于Maven的项目，因此工程目录中的/src、/test、/config以及所有jar包的根目录
		 * 都相当于是一个共同的classpath集合，因此如果我们想要加载
		 * /config/permissionConfig/PermissionLevel_PermissionType_Permission.
		 * json 这个路径下的文件，只需要以permissionConfig/
		 * PermissionLevel_PermissionType_Permission.json为相对路径名作为参数即可
		 */
		is = TestGson2Json.class.getClassLoader()
				.getResourceAsStream("permissionConfig/PermissionLevel_PermissionType_Permission.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String temp = "";
		StringBuffer sb = new StringBuffer();

		while ((temp = reader.readLine()) != null) {
			sb.append(temp);
		}

//		System.out.println(jsonPrimitive.getAsString());
//		System.out.println(jsonPrimitive.toString());
//		System.out.println(sb.toString().replace("\t", "").replace(" ", ""));
		JsonPrimitive jsonPrimitive = new JsonPrimitive(sb.toString().replace("\t", "").replace(" ", ""));
//		System.out.println(sb.toString().replace("\t", "").replace(" ", ""));
		String json = jsonPrimitive.toString();
//		System.out.println(json);
		json = jsonPrimitive.getAsString();
//		System.out.println(json);
		
//		json = "[{\"description\":\"街道层级\",\"permissionLevelName\":\"minus_first\",\"level\":-1,\"enabled\":true,\"permissionTypes\":[{\"description\":\"人员类型\",\"permissionTypeName\":\"user\",\"enabled\":true,\"permissions\":[{\"description\":\"查询\",\"permissionName\":\"retrieve\",\"enabled\":true},{\"description\":\"新建\",\"permissionName\":\"create\",\"enabled\":true},{\"description\":\"修改\",\"permissionName\":\"update\",\"enabled\":true},{\"description\":\"删除\",\"permissionName\":\"delete\",\"enabled\":true}]},{\"description\":\"项目类型\",\"permissionTypeName\":\"project\",\"enabled\":true,\"permissions\":[{\"description\":\"查询\",\"permissionName\":\"retrieve\",\"enabled\":true},{\"description\":\"新建\",\"permissionName\":\"create\",\"enabled\":true},{\"description\":\"修改\",\"permissionName\":\"update\",\"enabled\":true},{\"description\":\"删除\",\"permissionName\":\"delete\",\"enabled\":true}]}]}]";
		
		Type t = new TypeToken<List<PermissionLevel>>() {
		}.getType();

		ArrayList<PermissionLevel> list = g.fromJson(json, t);
		for (PermissionLevel level : list) {
			System.out.println("PermissionLevel:" + level.getPermissionLevelName() + "-" + level.getDescription());
			for (PermissionType type : level.getPermissionTypes()) {
				System.out.println("PermissionType:" + type.getPermissionTypeName() + "-" + type.getDescription());
				for (Permission p : type.getPermissions()) {
					System.out.println("Permission:" + p.getPermissionName() + "-" + p.getDescription());
				}
			}
		}
	}

	
	
}
