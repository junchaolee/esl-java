package com.xbstar.esl.backcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.service.SipAccountService;
import com.xbstar.esl.util.AlvesJSONResult;
import com.xbstar.esl.util.DateUtil;

/**
 * @Description: 分机相关
 * @Class:SipAccountController.java
 * @Author:janus
 * @Date:2024年8月31日下午11:55:48
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/sipaccount")
public class SipAccountController {
	@Autowired
	private SipAccountService sipService;

	@RequestMapping("/add")
	public AlvesJSONResult add(@Validated @RequestBody SipAccount sip) {
		sip.setCreateTime(DateUtil.getNowStr());

		int save = sipService.insertAccount(sip);
		if (save != 0) {
			return AlvesJSONResult.ok("添加成功");
		} else {
			return AlvesJSONResult.errorMsg("添加失败");
		}
	}
	
	@RequestMapping("/del")
	public AlvesJSONResult del(@Validated @RequestBody SipAccount sip) {
		int del = sipService.delAccount(sip);
		if (del != 0) {
			return AlvesJSONResult.ok("删除成功");
		} else {
			return AlvesJSONResult.errorMsg("删除失败");
		}
		
	
	}
	
	
	@RequestMapping("/findAllAccount")
	public AlvesJSONResult findAllAccount(HttpServletRequest req) {
		List<SipAccount> all = sipService.findAll();
		
		return AlvesJSONResult.ok(all);
		
	}

}
