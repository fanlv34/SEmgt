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
import com.semgt.util.SeUtil;

@Component("register")
@ReqFields({
	@ReqField(name = "username", option = false, pattern = "^[a-zA-Z0-9]{4,20}$"),
	@ReqField(name = "password", option = false),
	@ReqField(name = "email", option = true, pattern = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"),
	@ReqField(name = "mobile", option = true, pattern = "^1[3|4|5|8]\\d{9}$")
})
public class Register extends BaseAction {
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "jCryptionUtil")
	private JCryptionUtil jCryptionUtil;
	
	@Override
	public void doTrsPre(Model model) throws SeException {
		//检查用户名是否存在
		if(!SeUtil.isNullOrEmpty(userService.qryUserByUsername((String)model.getData("username")))) {
			throw new SeException("用户名已存在");
		}
	}

	@Override
	public void doTrs(Model model) throws SeException {
		// 使用RSA私钥解密密码
		String decryptPassword = jCryptionUtil.decrypt((String)model.getData("password"), (KeyPair)model.getSessionAttr("keyPair"));
		model.removeSessionAttr("keyPair");
		
		User newUser = new User();
		newUser.setUsername((String)model.getData("username"));
		newUser.setPassword(decryptPassword);
		newUser.setEmail((String)model.getData("email"));
		newUser.setMobile((String)model.getData("mobile"));
		userService.addUser(newUser);
	}

	public void setjCryptionUtil(JCryptionUtil jCryptionUtil) {
		this.jCryptionUtil = jCryptionUtil;
	}
	
}