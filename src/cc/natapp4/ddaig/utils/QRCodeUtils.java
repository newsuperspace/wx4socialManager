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
	 * @param aid  该活动的aid
	 * @return   形如：  qrcode/1/12/xxx.gif  的二维码图片相对路径，只需要拼接 域名/web应用名/return结果  就可以直接使用到前端<src>标签上以供展示了  
	 */
	public static String  createActivityQR(String aid){
		
		String codePath = "";
		BufferedImage image = QRCode.toQRCode(aid);
		codePath = "qrcode";
		int hashCode = aid.hashCode();
		int first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >> 4;
		codePath = codePath + "/" + first + "/" + second;

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
		String realPath = context.getRealPath("/" + codePath);   // C:\Android\apache-tomcat-9.0.0.M8\webapps\library\qrcode\12\2
		// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
		File file  =  new File(realPath);
		if(!file.exists()){
			file.mkdirs();
		}
		// 然后我们创建二维码图片的文件对象，文件名仍然以书籍的bid为名字，然后拓展名为gif
		codePath = codePath + "/"  + aid +".gif";
		System.out.println("最终的codePath："+codePath);    
		realPath  =  realPath +"/"+ aid+ ".gif";
		System.out.println("最终的realPath:"+realPath);
		file  = new  File(realPath);
		FileOutputStream  fos;
		try {
			fos  = new  FileOutputStream(file);
			ImageIO.write(image, "GIF", fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("二维码生成完毕");
		return codePath;
	}
	
	/**
	 * 创建用户qrcode
	 * @param uid  用户的uid
	 * @return  返回已生成的qrcode的相对路径
	 */
	public static String  createUserQR(String uid){
		return  createActivityQR(uid);
	}
	
	/**
	 * 创建兑换商品的qrcode
	 * @param wid 商品的wid
	 * @return 返回已经生成的qrcode的相对路径
	 */
	public static String createWareQR(String wid){
		return createActivityQR(wid);
	}
	
	public static String createQR2Temp(String uuid){
		
		String codePath = "";
		BufferedImage image = QRCode.toQRCode(uuid);
		codePath = "temp";
		int hashCode = uuid.hashCode();
		int first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >> 4;
		codePath = codePath + "/" + first + "/" + second;   // "temp/2/10"
		
		String realPath = ServletActionContext.getServletContext().getRealPath("/" + codePath);   // C:\Android\apache-tomcat-9.0.0.M8\webapps\library\temp\12\2
		// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
		File file  =  new File(realPath);
		if(!file.exists()){
			file.mkdirs();
		}
		// 然后我们创建二维码图片的文件对象，文件名借用所传阅的书籍的bid为名字，然后拓展名为gif
		codePath = codePath + "/"  + uuid +".gif";
		System.out.println("最终的codePath："+codePath);    
		realPath  =  realPath +"/"+ uuid+ ".gif";
		System.out.println("最终的realPath:"+realPath);
		file  = new  File(realPath);
		FileOutputStream  fos;   // 流处理 读入&写出
		try {
			fos  = new  FileOutputStream(file);
			ImageIO.write(image, "GIF", fos);   // 调用ImageIO的静态方法，将包含有二维码的image，按照gif格式，通过fos文件输出流写出到指定磁盘目录下
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return codePath;   // 最后将保存该二维码图片的相对路径。形如： “temp/2/10/3842-284-382-181-31.gif”的字符串返回。
									// 这样前端就能通过拼凑  "http://192.168.23.1/library" + "/" + codePath 得到访问该图片的绝对路径了，配置<img>标签就嫩显示QRCODE了
	}
	
}
