package com.xbstar.esl.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;
import org.dom4j.Document;
import org.dom4j.DocumentException;
public class ModifyFileLine2 {
	@Test
	public void linebyline() {
		
		// 指定要修改的文件路径
		String filePath = "C:\\Users\\janus\\Desktop\\switch.conf.xml";
		// 读取文件内容
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		String startPortContent = "    <param name=\"rtp-start-port\" value=\"189000\"/>";
		String endPortContent = "    <param name=\"rtp-end-port\" value=\"10100\"/>";
		lines.set(149, startPortContent);
		lines.set(150, endPortContent);
		
//		String str1 = lines.get(20);
//		int size = lines.size();
//		System.out.println(str1);
		// 将修改后的内容写回到文件中
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine(); // 写入换行符
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void linebyline2() {
		try {
			String xmlpath = "C:\\Users\\janus\\Desktop\\switch.conf.xml";
			SAXReader saxReader = new SAXReader();
//			Document doc1 = saxReader.read("src/cn/ljc/web_xml/dialplan.xml");
			Document doc = saxReader.read(new File(xmlpath));
			Element rootElement = doc.getRootElement();
			System.out.println("根元素名称：" + rootElement.getName());
			List<Element> childElemnets = rootElement.elements();
			for (Element element : childElemnets) {
				System.out.println("子1元素:" + element.getName());
				if ("settings".equals(element.getName())) {
					List<Element> zzElements = element.elements();
					for (Element zz : zzElements) {
						System.out.println("【子2元素及属性name|value值为】:" + zz.getName() + "|" + zz.attributeValue("name")
								+ "|" + zz.attributeValue("value"));

						if ("rtp-start-port".equals(zz.attributeValue("name"))) {
							zz.attribute("value").setValue("10019");

						}

						if ("rtp-end-port".equals(zz.attributeValue("name"))) {
							zz.attribute("value").setValue("10059");

						}
						try {
							XMLUtilLjc.writeXml(doc, xmlpath);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void linebyline3() {
		try {
			String xmlpath = Thread.currentThread().getContextClassLoader().getResource("switch.conf.data").getPath().substring(1);
			SAXReader saxReader = new SAXReader();
//			Document doc1 = saxReader.read("src/cn/ljc/web_xml/dialplan.xml");
			Document doc = saxReader.read(new File(xmlpath));
			Element rootElement = doc.getRootElement();
			System.out.println("根元素名称：" + rootElement.getName());
			List<Element> childElemnets = rootElement.elements();
			for (Element element : childElemnets) {
				System.out.println("子1元素:" + element.getName());
				if ("settings".equals(element.getName())) {
					List<Element> zzElements = element.elements();
					for (Element zz : zzElements) {
						System.out.println("【子2元素及属性name|value值为】:" + zz.getName() + "|" + zz.attributeValue("name")
								+ "|" + zz.attributeValue("value"));

						if ("rtp-start-port".equals(zz.attributeValue("name"))) {
							zz.attribute("value").setValue("20079");

						}

						if ("rtp-end-port".equals(zz.attributeValue("name"))) {
							zz.attribute("value").setValue("30059");

						}
						try {
							XMLUtilLjc.writeXml(doc, xmlpath);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
