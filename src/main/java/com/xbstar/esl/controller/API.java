package com.xbstar.esl.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.service.impl.SipAccountServiceImpl;

@RestController
@RequestMapping("/api")
public class API {
	@Value("${SIP_DOMAIN}")
	private String sip_domain;

	@Autowired
	SipAccountServiceImpl sipAccountService;

	/*
	 * @GetMapping("/api/sipAccount/login") public String SipAccount() {
	 * if("id".equals(req.getParameter("key")) &&
	 * StringUtils.isNotEmpty(req.getParameter("user")) &&
	 * StringUtils.isNotEmpty(req.getParameter("FreeSWITCH-IPv4")) ){ String code =
	 * req.getParameter("code");
	 * if(org.apache.commons.lang.StringUtils.isEmpty(code)){ return "code必传"; }
	 * return feignClientCallCenter.sipAccountLogin(req.getParameter("user"),req.
	 * getParameter("code"),req.getParameter("FreeSWITCH-IPv4"));
	 * 
	 * } }
	 */
	@RequestMapping("/simpleParam")
	public String simpleParam(HttpServletRequest req) {
		String name = req.getParameter("name");
		String ageStr = req.getParameter("age");
		int age = Integer.parseInt(ageStr); // 注意：这里应该处理NumberFormatException
		System.out.println(name + ":" + age);
		return "OK";
	}

	@RequestMapping("/sipAccount/login")
	public String login(HttpServletRequest req) {
		// 根据参数数据判断数据库中是否有此数据
		// 【有】 生成xml数据
		// 【没有】 返回not found -> 注册失败
		String section = req.getParameter("section");
		switch (section) {
		case "directory":
			String user = req.getParameter("user");
			SipAccount sipAccountEntity = sipAccountService.findByUserId(user);
			if (sipAccountEntity == null) {
				return "not found";
			}
			

			// 1、配置用户默认的user_context=defualt
			String xml_reg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
					+ "<document type=\"freeswitch/xml\">\n" + "  <section name=\"directory\">\n"
					+ "    <domain name=\"" + sip_domain + "\">\n" + "      <user id=\"" + sipAccountEntity.getUserId()
					+ "\">\n" + "        <params>\n" + "          <param name=\"password\" value=\""
					+ sipAccountEntity.getPassword() + "\"/>\n"
					+ "          <param name=\"dial-string\" value=\"{sip_invite_domain=${dialed_domain},presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(${dialed_user}@${dialed_domain})}\"/>\n"
					+ "        </params>\n" + "        <variables>\n"
					+ "          <variable name=\"user_context\" value=\"" +"default"+ "\"/>\n"
					+ "          <variable name=\"toll_allow\" value=\"domestic,international,local\"/>\n"
					+ "          <variable name=\"accountcode\" value=\"" + sipAccountEntity.getUserId() + "\"/>\n"
					+ "          <variable name=\"effective_caller_id_name\" value=\""
					+ sipAccountEntity.getUserId() + "\"/>\n"
					+ "          <variable name=\"effective_caller_id_number\" value=\"" + sipAccountEntity.getUserId()
					+ "\"/>\n" + "          <variable name=\"callgroup\" value=\"techsupport\"/>\n"
					+ "        </variables>\n" + "      </user>\n" + "    </domain>\n" + "  </section>\n"
					+ "</document>";
			System.out.println("【注册用户信息】-"+"sip_profile:"+req.getParameter("sip_profile")+" | action:"+req.getParameter("action"));
			return xml_reg;
			
		case "dialplan":
			// 1、配置context 为：default|public
			String called = req.getParameter("Caller-Destination-Number");
			String domain_name = req.getParameter("variable_domain_name");
			String cxt = req.getParameter("Caller-Context");
			String xml_dia = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
					+"<document type=\"freeswitch/xml\">\n"
					+"  <section name=\"dialplan\" description=\"RE Dial Plan For FreeSwitch\">\n"
					+"    <context name=\"default\">\n"
					+"      <extension name=\"test9\">\n"
					+"        <condition field=\"destination_number\" expression=\"^([0-9]{5,6})$\">\n"
					+"          <action application=\"export\" data=\"dialed_extension=$1\"/>\n"
					+"          <action application=\"bridge\""+" data=\"user/"+called+"@"+sip_domain+"\"/>\n"
					+"        </condition>\n"
					+"      </extension>\n"
					+"    </context>\n"
					+"  </section>\n"
					+"</document>";
			System.out.println("【路由信息】-被叫:"+called+" | "+"主叫context: "+cxt+" | "+"domian:"+sip_domain);
			return xml_dia;

		default:
			return "not found"; 

		}

		/*
		 * if("directory".equals(req.getParameter("section"))) {
		 * 
		 * 
		 * 
		 * 
		 * }else { return "not found"; }
		 */

	}

}
