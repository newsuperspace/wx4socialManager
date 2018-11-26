package cc.natapp4.ddaig.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.nutz.qrcode.QRCode;

public class QRCodeUtils {

	/**
	 * 创建Activity活动的时候会配套生成一个二维码
	 * 
	 * @param aid
	 *            该活动的aid
	 * @return 形如： qrcode/1/12/xxx.gif 的二维码图片相对路径，只需要拼接 域名/web应用名/return结果
	 *         就可以直接使用到前端<src>标签上以供展示了
	 */
	public static String createActivityQR(String aid) {

		String codePath = "";
		codePath = "qrcode";
		int hashCode = aid.hashCode();
		int first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >> 4;
		codePath = codePath + File.separator + first + File.separator + second;

		ServletContext context = ServletActionContext.getServletContext();
		/**
		 * 如request..getServletContext().getRealPath(File.separator)
		 * 得到项目在服务器磁盘上的绝对真实路径如：
		 * 
		 * D:\\ProgramFiles\\apache-tomcat-6.0.33\\webapps\wt4\
		 * 
		 * request.getServletContext().getRealPath(“/updload/video”)
		 * 得到项目在服务器磁盘上的绝对真实路径下再拼接上参数路径，最终返回一个路径
		 * 
		 * D:\\ProgramFiles\\apache-tomcat-6.0.33\\webapps\\wt4\\updload\\video
		 */
		String realPath = context.getRealPath(File.separator + codePath); // C:\Android\apache-tomcat-9.0.0.M8\webapps\library\qrcode\12\2
		// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
		File file = new File(realPath);
		if (!file.exists()) {
			// 如果该文件路径不存在，则先逐层创建路径中的所有文件夹
			file.mkdirs();
		}
		// 然后我们创建二维码图片的文件对象，文件名仍然以书籍的bid为名字，然后拓展名为gif
		codePath = codePath + File.separator + aid + ".gif";
		System.out.println("最终的codePath：" + codePath);
		realPath = realPath + File.separator + aid + ".gif";
		System.out.println("最终的realPath:" + realPath);
		
		// 开始生成二维码图片文件
		boolean result = createQRcode(realPath,aid);
		if(result){
			System.out.println("新建层级对象的二维码生成完毕");
		}else{
			System.out.println("新建层级对象的二维码生成失败");
		}
		// 返回相对路径（可以保存在数据库中）
		return codePath;
	}

	/**
	 * 直接根据给定的完整路径创建二维码，并返回创建结果
	 * 调用本方法前一定要首先保证realPath（本地服务器的磁盘路径）一定存在，
	 * 因为方法会直接使用realPath创建文件。
	 * 
	 * @param realPath   形如：E:\新建文件夹\qrcode\0\13\9a7cc0d6-36bc-4905-b63a-0f5de9962654.gif 的完整路径
	 * @param content   形如：tag=minus_first&lid=xjoduf7293jf2wjf9jd9suf9uw  的二维码内容
	 * @return  true  创建成功   false 创建失败
	 */
	public  static  boolean   createQRcode(String  realPath, String content){
		boolean  result  =  false;
		// 创建二维码图片到BufferedImage图形二进制缓存中
		BufferedImage image = QRCode.toQRCode(content);
		// 创建用于生成二维码图片文件的File对象
		File file = new File(realPath);
		FileOutputStream fos;
		try {
			// 创建指定路径的输出流
			fos = new FileOutputStream(file);
			// 通过专门用于创建图片文件的ImageIO写出二维码图片
			ImageIO.write(image, "GIF", fos);
			// 关闭输出流
			fos.close();
			result  = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 创建各个层级对象时的qrcode之用
	 * 
	 * @param code  形如： tag=minus_first&lid=xjoduf7293jf2wjf9jd9suf9uw
	 * 									tag用来标记该层级的类型（minus_first、zero、first、second、third、fourth）
	 * 									lid 就是通过UUID.Random()生成的随机id字符串
	 * @return
	 */
	public static String createLevelQR(String code) {
		return createActivityQR(code);
	}

	/**
	 * 创建用户qrcode
	 * 
	 * @param uid
	 *            用户的uid
	 * @return 返回已生成的qrcode的相对路径
	 */
	public static String createUserQR(String uid) {
		return createActivityQR(uid);
	}

	/**
	 * 创建兑换商品的qrcode
	 * 
	 * @param wid
	 *            商品的wid
	 * @return 返回已经生成的qrcode的相对路径
	 */
	public static String createWareQR(String wid) {
		return createActivityQR(wid);
	}

	/**
	 * 创建临时用二维码
	 * 本方法用于桌面端扫描二维码登录系统后台时生成存放在/temp目录下的临时二维码密钥
	 * @param uuid 密钥内容
	 * @return 存放密钥二维码图片的临时相对路径，用于前端拼接成<img></img>标签的src属性显示图片之用
	 */
	public static String createQR2Temp(String uuid) {

		String codePath = "";
		BufferedImage image = QRCode.toQRCode(uuid);
		codePath = "temp";
		int hashCode = uuid.hashCode();
		int first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >> 4;
		codePath = codePath + "/" + first + "/" + second; // "temp/2/10"

		String realPath = ServletActionContext.getServletContext().getRealPath("/" + codePath); // C:\Android\apache-tomcat-9.0.0.M8\webapps\library\temp\12\2
		// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 然后我们创建二维码图片的文件对象，文件名借用所传阅的书籍的bid为名字，然后拓展名为gif
		codePath = codePath + "/" + uuid + ".gif";
		System.out.println("最终的codePath：" + codePath);
		realPath = realPath + "/" + uuid + ".gif";
		System.out.println("最终的realPath:" + realPath);
		file = new File(realPath);
		FileOutputStream fos; // 流处理 读入&写出
		try {
			fos = new FileOutputStream(file);
			ImageIO.write(image, "GIF", fos); // 调用ImageIO的静态方法，将包含有二维码的image，按照gif格式，通过fos文件输出流写出到指定磁盘目录下
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return codePath; // 最后将保存该二维码图片的相对路径。形如：
							// “temp/2/10/3842-284-382-181-31.gif”的字符串返回。
							// 这样前端就能通过拼凑 "http://192.168.23.1/library" + "/" +
							// codePath 得到访问该图片的绝对路径了，配置<img>标签就嫩显示QRCODE了
	}

}
