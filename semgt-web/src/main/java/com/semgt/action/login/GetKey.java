package com.semgt.action.login;

import java.security.KeyPair;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.semgt.base.BaseAction;
import com.semgt.base.Model;
import com.semgt.base.annotation.RespField;
import com.semgt.exception.SeException;
import com.semgt.security.JCryptionUtil;

@Component("getKey")
@RespField("e,n,maxdigits")
public class GetKey extends BaseAction {
	private final int KEY_SIZE = 1024;

	@Resource(name = "jCryptionUtil")
	private JCryptionUtil jCryptionUtil;

	@Override
	public void doTrs(Model model) throws SeException {
		KeyPair kp = jCryptionUtil.generateKeypair(1024);
		model.setSessionAttr("keyPair", kp);
		model.setData("e", JCryptionUtil.getPublicKeyExponent(kp));
		model.setData("n", JCryptionUtil.getPublicKeyModulus(kp));
		model.setData("maxdigits", JCryptionUtil.getMaxDigits(KEY_SIZE));
	}

	public void setjCryptionUtil(JCryptionUtil jCryptionUtil) {
		this.jCryptionUtil = jCryptionUtil;
	}
}
