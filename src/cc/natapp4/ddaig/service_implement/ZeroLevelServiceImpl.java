package cc.natapp4.ddaig.service_implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.dao_interface.ZeroLevelDao;
import cc.natapp4.ddaig.domain.cengji.ZeroLevel;
import cc.natapp4.ddaig.json.returnMessage.ReturnMessage4Common;
import cc.natapp4.ddaig.service_interface.ZeroLevelService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

@Service("zeroLevelService")
public class ZeroLevelServiceImpl extends BaseServiceImpl<ZeroLevel> implements ZeroLevelService {

	@Resource(name="zeroLevelDao")
	private ZeroLevelDao  zeroLevelDao;
	
	@Override
	protected BaseDao<ZeroLevel> getBaseDao() {
		return zeroLevelDao;
	}

	// =========================个性化方法=====================
	/**
	 * 从微信端服务器获取带参数二维码（30天有效期），这样当用户扫描带参数二维码加入公众号的时候就会自动划归到该层级对象的直接管理层之下
	 * @param lid	层级对象的ID
	 * @param param	形如:"level$0_id$f55669aa-b039-4919-ae23-7c15472e29b1"的参数字符串
	 * @param r		用来保存通过AJAX向前端返回JSON信息的RetrunMessage4Common类型对象
	 * @return		形如："qrcode\8\10\5e0224c6-482b-4f2a-bc09-5d21b5bd7761.jpg"本地带参数二维码图片相对路径
	 */
	private String getQrcodeFromWeixin(String lid,String param,ReturnMessage4Common r) {
		
		// 与微信服务器进行交互
		WxMpQrCodeTicket qrTicket = null;
		File inFile = null;
		try {
			// 将参数文本提交给微信服务器，微信会生成带参数的临时二维码（30天）
			qrTicket = mpService.getQrcodeService().qrCodeCreateTmpTicket(param, 2592000);
			// 通过票据换取指定带参数二维码的File
			inFile = mpService.getQrcodeService().qrCodePicture(qrTicket);
			if (null == inFile) {
				throw new RuntimeException("从微信端获取的带参数二维码的File是null");
			}
		} catch (WxErrorException e1) {
			e1.printStackTrace();
			String message = "从微信端获取带参数二维码时出现异常,层级对象创建失败";
			System.out.println(message);
			r.setMessage(message);
			r.setResult(false);
			return "";
		}
		// 将带参数二维码图片的File保存到本地，备用
		String codePath = "";
		codePath = "qrcode";
		int hashCode = lid.hashCode();
		int first = hashCode & 0xf;
		int second = (hashCode & 0xf0) >> 4;
		// 由于不同文件系统的层级分隔符不一样（/正斜线或\反斜线），可以通过File.separator来自动适配分隔符，保证当前应用在任何操作系统中都能使用
		codePath = codePath + File.separator + first + File.separator + second;
		ServletContext context = ServletActionContext.getServletContext();
		// realPath就是服务器本地的真实路径，形如：C:\Android\apache-tomcat-9.0.0.M8\webapps\library\qrcode\12\2
		String realPath = context.getRealPath(File.separator + codePath);
		// 接下来我们通过File来逐层创建该文件目录结构，保证生成二维码图片的时候，该路径确实存在
		File outFile = new File(realPath); // 路径，而非文件
		if (!outFile.exists()) {
			// 如果该路径不存在，则逐层创建路径
			outFile.mkdirs();
		}
		// 然后我们创建二维码图片的文件对象，文件名仍然以层级对象的id值为名字，然后拓展名为jpg
		codePath = codePath + File.separator + lid + ".jpg";
		System.out.println("最终的codePath：" + codePath);
		realPath = realPath + File.separator + lid + ".jpg";
		System.out.println("最终的realPath:" + realPath);
		outFile = new File(realPath); // 文件，而非路径
		// 创建输入流和输出流，准备开始流对接
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(outFile);
			fis = new FileInputStream(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			String message = "在将从微信服务器获取的存有二维码jpq图片的File保存到本次磁盘时，创建输入或输出流出现异常,层级对象创建失败";
			System.out.println(message);
			r.setMessage(message);
			r.setResult(false);
			return "";
		}
		// 开始流对接，temp为字节缓冲（1024B=1KB）————字节数组就是缓冲区，8位（bit）=1字节（B），因此字节是仅次于位的最小信息单位，用它存放二进制数据最合适
		byte[] temp = new byte[1024];
		// 记录每次从输入流转入到输出流中的字节数
		int len = 0;
		// 开始流对接
		try {
			while ((len = fis.read(temp)) != -1) {
				// 边读边写，一旦len=-1表明输入流中的数据全都读入了，没有额外数据可供读入了
				fos.write(temp, 0, len);
			}
			fis.close();
			fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return codePath;
	}
}
