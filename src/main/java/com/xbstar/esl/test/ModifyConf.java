package com.xbstar.esl.test;

import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xbstar.esl.service.ConferenceService;
import com.xbstar.esl.service.ParasService;
import com.xbstar.esl.service.impl.ParasServiceImpl;
import com.xbstar.esl.util.XMLUtilLjc;

/**
 * @Description:
 * @Author:janus
 * @Date:2024年9月8日下午3:51:38
 * @Version:1.0.0
 */
public class ModifyConf {
	@Autowired
	ParasServiceImpl parasService;
	@Autowired
	ConferenceService confService;
	
	@Test
	public void modifySwitchConf() {
		
		String rtpStartPort = parasService.findByName("system.rtp.port.start");
		String rtpEndPort = parasService.findByName("system.rtp.port.end");
		
//		System.out.println("【rtp开始端口】："+rtpStartPort);
//		System.out.println("【rtp结束端口】："+rtpEndPort);
//		String startPortContent="    <param name=\"rtp-start-port\" value=\""+rtpStartPort+"\"/>";
//		String endPortContent="    <param name=\"rtp-end-port\" value=\""+rtpEndPort+"\"/>";
		
//		/opt/etc/freeswitch/autoload_configs/switch.conf.xml
//		String switch_path = Thread.currentThread().getContextClassLoader().getResource("switch.conf.data").getPath();
		
//		ModifyConfig.modifyOneline(switch_path, 149,startPortContent); 
//		ModifyConfig.modifyOneline(switch_path, 150, endPortContent);
		String switchPath ="src/main/resources/switch.conf.data";
		Document doc = XMLUtilLjc.getDocument(switchPath);
		Element rootElement = doc.getRootElement();
		List<Element> childElements = rootElement.elements();
		for(Element child : childElements) {
			if("Settings".equals(child.getName())) {
				List<Element> zzElements = child.elements();
				for(Element zz : zzElements) {
//					System.out.println("【子2元素及属性name为】:"+zz.getName()+"|"+zz.attributeValue("name")+"|"+zz.attributeValue("value"));
					if("rtp-start-port".equals(zz.attributeValue("name"))) {
						zz.attribute("value").setValue(rtpStartPort);
						
					}
					if("rtp-end-port".equals(zz.attributeValue("name"))) {
						zz.attribute("value").setValue(rtpEndPort);
						
					}
					try {
						XMLUtilLjc.writeXml(doc, switchPath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	
	@Test
	public void getMid() {
		
		String mId=confService.queryMemberId("70965315","72172");
		System.out.println(mId);
		
	}
	
}
