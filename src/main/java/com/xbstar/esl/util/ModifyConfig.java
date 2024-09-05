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

	/**
	 * 从源配置文件中读取基础配置，将修改的结果写入到一个新的配置文件中，再将这个新的
	 * 配置返回给fs
	 * @param sourcePath 基础配置文件路径
	 * @param destinationPath 要生成的目标配置文件路径
	 * @param position 要插入内容的位置
	 * @param content 插入的内容
	 */
	public static void insertContent(String sourcePath,String destinationPath, int position, String content) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourcePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 在第N行添加内容
		lines.add(position, content);
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destinationPath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine(); // 写入换行符
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
