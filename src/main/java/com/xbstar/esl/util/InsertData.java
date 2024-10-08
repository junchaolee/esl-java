package com.xbstar.esl.util;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

import com.xbstar.esl.controller.MediaServerConfig;

public class InsertData {
	public static void main(String[] args) {
		// 指定要修改的文件路径
		String sourcefilePath = Thread.currentThread().getContextClassLoader().getResource("sofia2.conf.data").getPath().substring(1);
		String destinationPath = Thread.currentThread().getContextClassLoader().getResource("sofia.conf.data").getPath().substring(1);
		// 读取文件内容
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourcefilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("修改前lines大小："+lines.size());
		// 修改第三行的内容
//		int lineNumberToModify = 131; // 第三行的索引为2
//		String startPortContent = "    <param name=\"rtp-start-port\" value=\"10020\"/>";
//		String endPortContent = "    <param name=\"rtp-end-port\" value=\"11230\"/>";
//		lines.set(lineNumberToModify, startPortContent);
//		lines.set(132, endPortContent);
//		
		String insertContent="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
				+"<document type=\"freeswitch/xml\">\n"
				+"  <section name=\"dialplan\" description=\"RE Dial Plan For FreeSwitch\">\n"
				+"    <context name=\"default\">\n"
				+"      <extension name=\"public_called_check_1\">\n"
				+"        <condition field=\"destination_number\" expression=\"^(110.*)$\">\n"
				+"            <action application=\"hangup\" data=\"NORMAL\" />\n"
				+"        </condition>\n"
				+"      </extension>\n"
 			+"</document>";
		
		// 将修改后的内容写回到文件中
		lines.add(34, insertContent);
		System.out.println("修改后lines大小："+lines.size());
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destinationPath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine(); // 写入换行符
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		String abc = InsertData.class.getClassLoader().getResource("sofia.conf.data").getPath().substring(1);
		System.out.println(abc);
	}
	

	

}
