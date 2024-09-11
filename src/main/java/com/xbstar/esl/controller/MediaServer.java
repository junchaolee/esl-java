package com.xbstar.esl.controller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xbstar.esl.domain.ParasInfo;
import com.xbstar.esl.domain.SipAccount;
import com.xbstar.esl.domain.SipGateway;
import com.xbstar.esl.service.impl.ParasServiceImpl;
import com.xbstar.esl.service.impl.SipAccountServiceImpl;
import com.xbstar.esl.service.impl.SipGatewayServiceImpl;
import com.xbstar.esl.util.ModifyConfig;
import com.xbstar.esl.util.ReadXml;
import com.xbstar.esl.util.XMLUtilLjc;


/**
 * @Description:
 * @Class:API.java
 * @Author:janus
 * @Date:2024年8月31日下午11:53:23
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/api")
public class MediaServer {
	private static final Logger log = LoggerFactory.getLogger(MediaServer.class);
	
	@Value("${SIP_DOMAIN}")
	private String sip_domain;

	@Autowired
	SipAccountServiceImpl sipAccountService;
	
	@Autowired
	SipGatewayServiceImpl sipGatewayService;
	
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	String not_found="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
	     +"<document type=\"freeswitch/xml\">\n"
	     +"   <section name=\"result\">\n"
	     +"      <result status=\"not found\" />\n"
	     +"    </section>\n"
	     +"</document>";


	@RequestMapping("/mediaserver")
	public String mediaserverConfig(HttpServletRequest req) {
		String section = req.getParameter("section");
		
		//1、用户注册
		if("directory".equals(section)) {
			String user = req.getParameter("user");
			SipAccount sipAccountEntity = sipAccountService.findByUserId(user);
			if (sipAccountEntity == null) {
				return not_found;
			}
			
			// 1、配置用户默认的user_context=defualt
			String xml_reg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + "<document type=\"freeswitch/xml\">\n" + "  <section name=\"directory\">\n"
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
		}
		
		//2、拨号计划		
		if("dialplan".equals(section)) {
			
			String called = req.getParameter("Caller-Destination-Number");
			//String domain_name = req.getParameter("variable_domain_name");
			String cxt = req.getParameter("Caller-Context");
			
			// 方法一、配置context 为：default|public
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
					+"          <action application=\"sleep\" data=\"500\"/>\n"
					+"          <action application=\"set\" data=\"recordfile=record_${strftime(%Y-%m-%d-%H-%M-%S)}_${destination_number}_${caller_id_number}.wav\" />\n"
					+"          <action application=\"set\" data=\"recordfile_path=${Call_record_path}/record/${recordfile}\" />\n"
					+"          <action application=\"log\"  data=\"Call_record_path:${Call_record_path}\"    />\n"
					+"          <action application=\"set\" data=\"execute_on_answer=record_session ${recordfile_path}\" />\n"
					+"          <action application=\"bridge\""+" data=\"{absolute_codec_string=PCMA\\,OPUS\\,PCMU}{originatioin_caller_id_number=7777}user/"+called+"@"+sip_domain+"\"/>\n"
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
			
			log.info("【路由信息】-被叫:"+called+" | "+"主叫context: "+cxt+" | "+"domian:"+sip_domain);
			return xml_default;
		}

		
		
		//3、配置
		if("configuration".equals(section)) {
				
			
			
			
			switch(req.getParameter("key_value")) {
			case "sofia.conf":
				//if("config_sofia".equals(req.getParameter("Event-Calling-Function"))) {
				//}
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
				
				
				

				
				//2、修改sofia.conf.xml,添加网关配置到external
//				String sourcePath = Thread.currentThread().getContextClassLoader().getResource("sofia2.conf.data").getPath();
//				String destinationPath = Thread.currentThread().getContextClassLoader().getResource("sofia.conf.data").getPath();
				
				InputStream is = null;
				try {
					is = resourceLoader.getResource("classpath:sofia2.conf.data").getInputStream();
				} catch (IOException e) {
					e.printStackTrace();
				}
//				String destinationPath="/tmp/sofia.conf.data";
				String destinationPath="C:\\Users\\tong\\Desktop\\sofia.data";
				
//				ClassPathResource classPathResource = new ClassPathResource("sofia2.conf.data");
			
				
//				resource2.get
				
//				System.out.println("两个路径："+sourcePath+"|"+destinationPath);
				String insertContent="<gateways>\n"
						+"      "+gws
						+"</gateways>";
//				ModifyConfig.insertContent(sourcePath.substring(1), destinationPath.substring(1), 34, insertContent);
				ModifyConfig.insertContent(is, destinationPath, 34, insertContent);
				String xml_all = ReadXml.readXMLFile(destinationPath);
				//log.info(xml_all);
				return xml_all;
			
			default:
				return not_found;
			}
			
		}else {
			
			return not_found;
		}
		
			
	}

	
}
