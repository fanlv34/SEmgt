package com.semgt.action.login;

import java.security.KeyPair;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseAction;
import com.semgt.base.Model;
import com.semgt.base.annotation.ReqField;
import com.semgt.base.annotation.ReqFields;
import com.semgt.exception.SeException;
import com.semgt.model.User;
import com.semgt.security.JCryptionUtil;
import com.semgt.service.IUserService;

@Component("login")
@ReqFields({
	@ReqField(name = "username", option = true, pattern = "^[a-zA-Z0-9]{4,20}$"),
	@ReqField(name = "password", option = true)
})
public class Login extends BaseAction {
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "jCryptionUtil")
	private JCryptionUtil jCryptionUtil;

	@Override
	public void doTrs(Model model) throws SeException {
		super.doTrs(model);
		User user = userService.qryUserByUsername((String)model.getData("username"));
		// 使用RSA私钥解密密码
		String decryptPassword = jCryptionUtil.decrypt((String)model.getData("password"), (KeyPair)model.getSessionAttr("keyPair"));
		model.removeSessionAttr("keyPair");
		
		if(null == user || !user.getPassword().equals(decryptPassword)) {
			throw new SeException("用户名或密码不正确！");
		}
		
		model.setSessionAttr("_User", user);
		// 更新登录时间
		userService.updLoginTime(user.getUserId());
	}
	
	public void setjCryptionUtil(JCryptionUtil jCryptionUtil) {
		this.jCryptionUtil = jCryptionUtil;
	}
}