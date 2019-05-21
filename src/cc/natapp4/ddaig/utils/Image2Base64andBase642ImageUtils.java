package cc.natapp4.ddaig.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * 将图片转换为Base64 将base64编码字符串解码成img图片
 *
 */
public class Image2Base64andBase642ImageUtils {

	/**
	 * 【实例】
	 * @param args
	 */
	public static void main(String[] args) {
		String imgFile = "d:\\1.png";// 待处理的图片
		String imgbese = getImgStr(imgFile);
		System.out.println(imgbese.length());
		System.out.println(imgbese);
		String imgFilePath = "d:\\new1.jpg";// 新生成的图片
		generateImage(imgbese, imgFilePath);
	}

	/**
	 * 【UTIL】
	 * 将图片转换成Base64编码
	 * 
	 * @param imgFile 待处理图片,形如：“d:\\1.jpg”的服务器本地磁盘路径
	 * @return
	 */
	public static String getImgStr(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(data));
	}

	/**
	 * 【UTIL】
	 * 对字符串的字节数组进行Base64解码，重新生成图片
	 * 
	 * @param imgStr      图片的BASE64编码字符串
	 * @param imgFilePath 保存图片全路径地址，形如：“d:\\new1.jpg”
	 * @return
	 */
	public static boolean generateImage(String imgStr, String imgFilePath) {
		//
		if (imgStr == null) // 图像数据为空
			return false;

		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片

			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
