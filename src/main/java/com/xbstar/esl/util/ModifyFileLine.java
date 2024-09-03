package com.xbstar.esl.util;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

import com.xbstar.esl.controller.API;

public class ModifyFileLine {
	public static void main(String[] args) {
		// 指定要修改的文件路径
		String filePath = "C:\\Users\\tong\\Desktop\\switch.conf.xml";
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
		// 修改第三行的内容
		int lineNumberToModify = 128; // 第三行的索引为2
		String startPortContent = "    <param name=\"rtp-start-port\" value=\"44020\"/>";
		String endPortContent = "    <param name=\"rtp-end-port\" value=\"44030\"/>";
		lines.set(lineNumberToModify, startPortContent);
		lines.set(129, endPortContent);
		// 将修改后的内容写回到文件中
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine(); // 写入换行符
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		URL abc = ModifyFileLine.class.getClassLoader().getResource("sofia.conf.data");
		System.out.println(abc);
	}
	

	

}
