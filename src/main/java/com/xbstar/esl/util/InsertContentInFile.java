package com.xbstar.esl.util;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
 
public class InsertContentInFile {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\tong\\Desktop\\sofia.xml"; // 文件路径
        int insertPosition = 10; // 插入位置（从0开始计数）
        String contentToInsert = "这是我要插入的内容！！！！！。"; // 要插入的内容
 
        insertContent(filePath, insertPosition, contentToInsert);
    }
 
    public static void insertContent(String filePath, int position, String content) {
        Path path = Paths.get(filePath);
        Charset charset = StandardCharsets.UTF_8;
 
        try (BufferedReader reader = Files.newBufferedReader(path, charset);
             BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
            
            String line;
            StringBuilder sb = new StringBuilder();
            int lineNumber = 0;
 
            // 读取文件直到指定位置
            while ((line = reader.readLine()) != null) {
                if (lineNumber == position) {
                    sb.append(content).append(System.lineSeparator()); // 插入内容
                }
                sb.append(line).append(System.lineSeparator()); // 添加原始行
                lineNumber++;
            }
 
            // 如果还有内容要插入，继续插入
            while (position++ < lineNumber) {
                sb.insert(sb.length() - System.lineSeparator().length(), content + System.lineSeparator());
            }
 
            // 写入新内容
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}