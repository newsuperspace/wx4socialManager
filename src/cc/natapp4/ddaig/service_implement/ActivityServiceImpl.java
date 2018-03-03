package cc.natapp4.ddaig.service_implement;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;

import cc.natapp4.ddaig.dao_interface.ActivityDao;
import cc.natapp4.ddaig.dao_interface.BaseDao;
import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Service("activityService")
public class ActivityServiceImpl extends BaseServiceImpl<Activity> implements ActivityService {

	@Resource(name="activityDao")
	private ActivityDao  dao;
	
	@Override
	protected BaseDao<Activity> getBaseDao() {
		return dao;
	}

	/**
	 * 个性方法
	 * 由于Activity需要在创建的时候使用它的aid来生成专属QRCODE，因此aid就必须手动指定
	 * 且需要在新建活动的时候生成QRCODE图片，因此需要在当前service层做一些特别处理。
	 */
	@Override
	public void save(Activity t) {

		// 手动生成新建活动的专属aid
		String aid =  UUID.randomUUID().toString();
		t.setAid(aid);
		// TODO 需要等wex5的前端页面的日期时间选择器组件回传的数据类型（yyyy-MM-dd hh:mm这样的字符串还是Date类型或long类型毫秒值）后再做时间的处理
		String beginData =  new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		String endData =  new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()+1000*60*60*24*1));
		t.setBeginData(beginData);
		t.setEndData(endData);
		// 生成QRCODE
		String qrcodeUrl = QRCodeUtils.createActivityQR(aid);
		t.setQrcodeUrl(qrcodeUrl);
		// 最后调用父类的save()方法实现最终向数据库写入数据的工作
		super.save(t);
	}

	
	
}
