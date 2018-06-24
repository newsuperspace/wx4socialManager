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
import cc.natapp4.ddaig.dao_interface.MemberDao;
import cc.natapp4.ddaig.domain.Activity;
import cc.natapp4.ddaig.domain.Member;
import cc.natapp4.ddaig.service_interface.ActivityService;
import cc.natapp4.ddaig.service_interface.MemberService;
import cc.natapp4.ddaig.utils.QRCodeUtils;

@Service("memberService")
public class MemberServiceImpl extends BaseServiceImpl<Member> implements MemberService {

	@Resource(name="memberDao")
	private MemberDao  dao;
	
	@Override
	protected BaseDao<Member> getBaseDao() {
		return dao;
	}

	
}
