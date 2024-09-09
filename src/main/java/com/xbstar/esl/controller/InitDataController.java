/**
 * 
 */
package com.xbstar.esl.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xbstar.esl.service.impl.ParasServiceImpl;
import com.xbstar.esl.util.XMLUtilLjc;

/**
 *  @Description:
 *  @Date:2024年9月9日15:42:59
 *  @Author:tong
 *  @Version:v1.0.0
 */

@Controller
public class InitDataController {
	private static final Logger log = LoggerFactory.getLogger(InitDataController.class);

	
	@Autowired
	ParasServiceImpl parasService;
	
	
	@PostConstruct
	public void init() throws IOException {
		//1、修改switch.conf.xml的rtp端口范围 
		String rtpStartPort = parasService.findByName("system.rtp.port.start");
		String rtpEndPort = parasService.findByName("system.rtp.port.end");
		
		log.info("【rtp开始端口】："+rtpStartPort);
		log.info("【rtp结束端口】："+rtpEndPort);
		
		String switch_path ="/opt/etc/freeswitch/autoload_configs/switch.conf.xml";
//		String des_path="/tmp/switch.data";
		
		Document doc = XMLUtilLjc.getDocument(switch_path); 
		Element rootElement =doc.getRootElement(); 
		log.info("根元素的名称:"+rootElement.getName());
		List<Element> childElemnets = rootElement.elements();
		for (Element element : childElemnets) { 
			if ("settings".equals(element.getName())) {
				  List<Element> zzElements = element.elements(); 
				  for (Element zz : zzElements) {
						log.info("【子2元素及属性name|value值为】:" + zz.getName() + "|" + zz.attributeValue("name")
								+ "|" + zz.attributeValue("value"));

						if ("rtp-start-port".equals(zz.attributeValue("name"))) {
							zz.attribute("value").setValue(rtpStartPort);

						}

						if ("rtp-end-port".equals(zz.attributeValue("name"))) {
							zz.attribute("value").setValue(rtpEndPort);

						}
						try {
							XMLUtilLjc.writeXml(doc, switch_path);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				  
				  
			  }
		} 
		
	
	
		 
	}

}
