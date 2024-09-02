package com.xbstar.esl.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbstar.esl.domain.Conference;
import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.domain.SipGateway;
import com.xbstar.esl.service.ConferenceService;
import com.xbstar.esl.service.impl.ConferenceServiceImpl;
import com.xbstar.esl.service.impl.SipAccountServiceImpl;
import com.xbstar.esl.service.impl.SipGatewayServiceImpl;
import com.xbstar.esl.util.AlvesJSONResult;


/**
 * @Description:
 * @Class:API.java
 * @Author:janus
 * @Date:2024年8月31日下午11:53:23
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/api")
public class API {
	@Value("${SIP_DOMAIN}")
	private String sip_domain;

	@Autowired
	SipAccountServiceImpl sipAccountService;
	
	@Autowired
	SipGatewayServiceImpl sipGatewayService;
	
	@Resource
	ConferenceService confService;
	



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
			//System.out.println("【注册用户信息】-"+"sip_profile:"+req.getParameter("sip_profile")+" | 【action】:"+req.getParameter("action"));
			return xml_reg;
			
		case "dialplan":
			
			String called = req.getParameter("Caller-Destination-Number");
			//String domain_name = req.getParameter("variable_domain_name");
			String cxt = req.getParameter("Caller-Context");
			
			// 方法1、配置context 为：default|public
			// 桥接等待5秒 +"          <action application=\"sleep\"  data=\"5000\" />\n"
			String xml_default= "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
					+"<document type=\"freeswitch/xml\">\n"
					+"  <section name=\"dialplan\" description=\"RE Dial Plan For FreeSwitch\">\n"
					+"    <context name=\"default\">\n"
					+"      <extension name=\"public_called_check_1\">\n"
					+"        <condition field=\"destination_number\" expression=\"^(110.*)$\">\n"
					+"            <action application=\"hangup\" data=\"NORMAL\" />\n"
					+"        </condition>\n"
					+"      </extension>\n"
					+"      <extension name=\"public_called_check_1\">\n"
					+"        <condition field=\"destination_number\" expression=\"^(120.*)$\">\n"
					+"            <action application=\"hangup\" data=\"NORMAL\" />\n"
					+"        </condition>\n"
					+"      </extension>\n"
					+"      <extension name=\"Local_Dial\">\n"
					+"        <condition field=\"destination_number\" expression=\"^([0-9]{5,6})$\">\n"
					+"          <action application=\"export\" data=\"dialed_extension=$1\"/>\n"
					+"          <action application=\"bridge\""+" data=\"{absolute_codec_string=PCMA\\,PCMU}user/"+called+"@"+sip_domain+"\"/>\n"
					+"        </condition>\n"
					+"      </extension>\n"
					+"      <extension name=\"2FXO\">\n"
					+"        <condition field=\"destination_number\" expression=\"^([0-9]{11,12})$\">\n"
					+"          <action application=\"export\" data=\"dialed_extension=$1\"/>\n"
					+"          <action application=\"bridge\""+" data=\"{absolute_codec_string=^^:PCMA:PCMU}sofia/gateway/gwfxo8/$1\"/>\n"
					+"        </condition>\n"
					+"      </extension>\n"
					+"    </context>\n"
					+"  </section>\n"
					+"</document>";
			
			// 方法2、对所有呼入做park处理，交由esl处理
			// park 之前加上180振铃 <action application="ring_ready"/>
			String xml_park = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
					+"<document type=\"freeswitch/xml\">\n"
					+"  <section name=\"dialplan\" description=\"RE Dial Plan For FreeSwitch\">\n"
					+"    <context name=\"default\">\n"
					+"      <extension name=\"test2\">\n"
					+"        <condition field=\"destination_number\" expression=\"^(.+)$\">\n"
					+"          <action application=\"export\" data=\"dialed_extension=$1\"/>\n"
					+"          <action application=\"ring_ready\"/>\n"
					+"          <action application=\"park\"/>\n"
					+"        </condition>\n"
					+"      </extension>\n"
					+"    </context>\n"
					+"  </section>\n"
					+"</document>";
			
			System.out.println("【路由信息】-被叫:"+called+" | "+"主叫context: "+cxt+" | "+"domian:"+sip_domain);
			return xml_default;
			
		case "configuration":
			
			if("sofia.conf".equals(req.getParameter("key_value"))&&"external".equals(req.getParameter("profile"))) {
				String gws="";
				List<SipGateway> gwList = sipGatewayService.findAll();
				for(SipGateway sg:gwList) {
					String gwname = sg.getGwName();
					String gwip = sg.getGwIP();
					gws+="<gateway name=\""+gwname+"\">\n"
					       +"   <param name=\"proxy\" value=\""+gwip+"\" />\n"
					       +" 	<param name=\"realm\" value=\""+gwip+"\" />\n"
					       +" 	<param name=\"register\" value=\"false\" />\n"
					       +" 	<param name=\"rtp-autofix-timing\" value=\"false\" />\n"
					       +" 	<param name=\"caller-id-in-from\" value=\"true\" />\n"
					       +" 	<param name=\"register-transport\" value=\"udp\" />\n"
					       +"</gateway>\n";
				}
				//System.out.println("网关信息:"+gw);
				String xml_gw= "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
			            +"<document type=\"freeswitch/xml\">\n"
			            +"  <section name=\"configuration\" description=\"Various Configuration\">\n" 
			            +"    <configuration name=\"sofia.conf\" description=\"sofia Endpoint\">\n"
			            +"      <profiles>\n"
			            +"         <profile name=\"external\">\n"
			            +"            <gateways>\n" 
			            +               gws
			            +"           </gateways>\n" 
			            +"         </profile>\n" 
			            +"     </profiles>\n" 
			            +"   </configuration>\n" 
			            +"  </section>\n" 
			            +"</document>\n";
				System.out.println(xml_gw);
			    return xml_gw;
				
			}else {
				return "nothing";
			}
			
			
			
		
		default:
			return "not found"; 

		}


	}

	
	/**
	 * 创建会议
	 * 创建一个会议对象，对象里包含有会议名称、成员、并将会议信息存储到表结构
	 * 先期只做音频，后期加上视频做判断
	 */
	@RequestMapping("/conference/create")
	public AlvesJSONResult confCreate(@Validated @RequestBody Conference conf) {
		//生成会议名(ID)
		String confName = RandomStringUtils.randomNumeric(8);
		//主持人
		String userId = conf.getUserId();
		String cmdstr = confName+"@video-mcu-stereo bgdial {absolute_codec_string=^^:pcma:pcmu}user/"+userId+" "+confName+" conference";
		ESL.client.sendAsyncApiCommand("conference", cmdstr);
//		String cmdStr2="6577786 bgdial {absolute_codec_string=^^:pcma:pcmu}user/321321";
//		ESL.client.sendAsyncApiCommand("conference", cmdStr2);
		
		
		return AlvesJSONResult.ok("会议【"+confName+"】创建成功,主持人："+userId);

	}
	
	
	
	/**
	 * @param:会议名+成员分机
	 * 添加会议成员+
	 * 如果添加N个成员，是执行N次接口调用
	 * 还是一次调用接口，获取所有成员信息
	 * 循环执行？
	 * 
	 * 添加会议成员时，根据事件存储结果存储信息到数据库
	 * @return
	 */
	@RequestMapping("/conference/addMember")
	public AlvesJSONResult confAdd(@Validated @RequestBody Conference con) {
		String userId = con.getUserId();
		
		String confName = con.getConfName();
		System.out.println("成员分机："+userId+"|会议名："+confName);
		String cmdStr=confName+" bgdial {absolute_codec_string=^^:pcma:pcmu}user/"+userId;
		ESL.client.sendAsyncApiCommand("conference", cmdStr);
		return AlvesJSONResult.ok("会议成员【"+userId+"】添加成功");
	}
	
	
	/**
	 * @param:会议名+成员分机
	 * 踢出会议成员
	 * 通过成员获取所在会议的memberID
	 * @return
	 */
	@RequestMapping("/conference/delMember")
	public AlvesJSONResult confDel(@Validated @RequestBody Conference con) {
		String confName = con.getConfName();
		String userId = con.getUserId();
		String mId=confService.queryMemberId(confName,userId);
		ESL.client.sendAsyncApiCommand("conference", confName+" kick "+mId);
		return AlvesJSONResult.ok("会议成员【"+userId+"】已踢出");
	}
	
	/**
	 * 销毁会议
	 * conference 6577786 hup all
	 */
	@RequestMapping("/conference/destroy")
	public AlvesJSONResult confDestroy(@Validated @RequestBody Conference con){
		//获取会议名,直接传参
		//通过会议成员获取
		String confName = con.getConfName();
		
		
		String res = ESL.client.sendAsyncApiCommand("conference", confName+" hup all");
		
		return AlvesJSONResult.ok("会议【"+confName+"】销毁成功");
		
	}
	
}
