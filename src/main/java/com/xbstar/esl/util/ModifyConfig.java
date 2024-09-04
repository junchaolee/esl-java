package com.xbstar.esl.util;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

import com.xbstar.esl.controller.MediaServerConfig;

public class ModifyConfig {
	public static void modifyOneline(String filePath, int lineNumber, String content) {
		// 读取文件内容并放入arrayList
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 修改第N行的内容（覆盖）
		lines.set(lineNumber, content);

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine(); // 写入换行符
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void insertContent(String filePath, int position, String content) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 在第N行添加内容
		lines.add(position, content);
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine(); // 写入换行符
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
