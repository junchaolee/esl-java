package com.xbstar.esl.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


/**
 * @Description:
 * @Class:API.java
 * @Author:janus
 * @Date:2024年8月31日下午11:53:23
 * @Version:1.0.0
 */
@RestController
@RequestMapping("/api")
public class MediaServerConfig {
	
	@Value("${SIP_DOMAIN}")
	private String sip_domain;

	@Autowired
	SipAccountServiceImpl sipAccountService;
	
	@Autowired
	SipGatewayServiceImpl sipGatewayService;
	
	@Autowired
	ParasServiceImpl parasService;
	
	String not_found="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
	     +"<document type=\"freeswitch/xml\">\n"
	     +"   <section name=\"result\">\n"
	     +"      <result status=\"not found\" />\n"
	     +"    </section>\n"
	     +"</document>";


	@RequestMapping("/mediaserver")
	public String mediaserverConfig(HttpServletRequest req) {
		// 根据参数数据判断数据库中是否有此数据
		// 【有】 生成xml数据
		// 【没有】 返回not found -> 注册失败
		String section = req.getParameter("section");
		switch (section) {
		case "directory":
			String user = req.getParameter("user");
			SipAccount sipAccountEntity = sipAccountService.findByUserId(user);
			if (sipAccountEntity == null) {
				return not_found;
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
					+"          <action application=\"bridge\""+" data=\"{absolute_codec_string=PCMA\\,OPUS\\,PCMU}user/"+called+"@"+sip_domain+"\"/>\n"
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
			//&&"external".equals(req.getParameter("profile"))
			
			if("sofia.conf".equals(req.getParameter("key_value"))) {
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
				//1、修改switch.conf.xml的rtp端口范围 
//				String rtpStartPort = parasService.findByName("system.rtp.port.start").getParameterValue();
//				String rtpEndPort = parasService.findByName("system.rtp.port.end").getParameterValue();
								  
//				System.out.println("【rtp开始端口】："+rtpStartPort);
//				System.out.println("【rtp结束端口】："+rtpEndPort);
//				String startPortContent="    <param name=\"rtp-start-port\" value=\""+rtpStartPort+"\"/>";
//				String endPortContent="    <param name=\"rtp-end-port\" value=\""+rtpEndPort+"\"/>";
				
//				/opt/etc/freeswitch/autoload_configs/switch.conf.xml
//				String switch_path = Thread.currentThread().getContextClassLoader().getResource("switch.conf.data").getPath();
				
//				ModifyConfig.modifyOneline(switch_path, 149,startPortContent); 
//				ModifyConfig.modifyOneline(switch_path, 150, endPortContent);
				 
				//2、修改sofia.conf.xml,添加网关配置到external
				String sourcePath = Thread.currentThread().getContextClassLoader().getResource("sofia2.conf.xml").getPath();
				String destinationPath = Thread.currentThread().getContextClassLoader().getResource("sofia.conf.xml").getPath();
				System.out.println("两个路径："+sourcePath+"|"+destinationPath);
				String insertContent="<gateways>\n"
						+"      "+gws
						+"    </gateways>";
				ModifyConfig.insertContent(sourcePath.substring(1), destinationPath.substring(1), 34, insertContent);
				String xml_all = ReadXml.readXMLFile(destinationPath);
				System.out.println(xml_all);
			    return xml_all;
				
			}else {
				return not_found;
			}
			
		
		default:
			return not_found; 

		}


	}

	
}
