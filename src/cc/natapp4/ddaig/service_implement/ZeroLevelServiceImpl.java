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
}
