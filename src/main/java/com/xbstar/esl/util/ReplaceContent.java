package com.xbstar.esl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile向指定文件，指定位置插入内容
 * 1.先使用File的createTempFile创建一个临时文件
 * 2.将插入以后的文件内容（末尾节点开始后的内容）保存到临时文件中
 * 3.程序重新定位到插入点，将要插入的内容保存到节点后
 * 4.这样就相当于完成了文件的拼接
 * 
 * @author yangkai
 */
public class ReplaceContent {
	public static void insert(String fileName, String startContext, String endContext, String insertContent)
			throws IOException {

		RandomAccessFile raf = null;
		// 创建临时文件保存插入点后数据
		File tmp = File.createTempFile("tmp", null);
		FileOutputStream tmpout = null;
		FileInputStream tmpin = null;

		// 定位节点使用，readline方法不能识别\r\n,因此需要自己添加进去，最开始因为这个检索节点始终不准耽误了很长时间
		StringBuffer buffer = new StringBuffer();
		BufferedReader bf = new BufferedReader(new FileReader(fileName));
		String s = null;
		while ((s = bf.readLine()) != null) {// 使用readLine方法，一次读一行
			buffer.append(s + "\r\n");
		}

		String xml = buffer.toString();

		long posStatr = xml.indexOf(startContext) + startContext.length() + 2;
		long posEnd = xml.indexOf(endContext);

		tmp.deleteOnExit();// jvm退出的时候删除临时文件
		try {
			raf = new RandomAccessFile(fileName, "rw");
			tmpout = new FileOutputStream(tmp);
			tmpin = new FileInputStream(tmp);
			raf.seek(posEnd);
			// 将插入点后的内容读入临时文件中保存
			byte[] bbuf = new byte[64];
			int hasRead = 0;
			while ((hasRead = raf.read(bbuf)) > 0) {
				tmpout.write(bbuf, 0, hasRead);
			}
			// 把文件记录指针定位到pos
			raf.seek(posStatr);
			raf.write(insertContent.getBytes());
			while ((hasRead = tmpin.read(bbuf)) > 0) {
				raf.write(bbuf, 0, hasRead);
			}

		} finally {
			raf.close();
		}

	}

	public static void main(String[] args) throws IOException {
		insert("filePath","posStart","posEnd", "replaceContext\r\n");
	}

}

