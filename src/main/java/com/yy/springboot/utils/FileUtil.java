package com.yy.springboot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具
 * @author liwei
 */
public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class.getName());

	/**
	 * 输出文件（兼容xls、xlsx）
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @param wb
	 * @param extension
	 *            后缀
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void outputFile(String fileName, HttpServletRequest request, HttpServletResponse response,
			Workbook wb, String extension) throws UnsupportedEncodingException, IOException {
		String agent = request.getHeader("User-Agent");
		// 火狐浏览器导出文件不会url解码
		if (StringUtil.isEmpty(agent) == false && agent.toLowerCase().indexOf("firefox") > -1) {
			response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "." + extension + "\"");
		} else {
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + extension, "UTF-8"));
		}
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}

	/**
	 * 导出压缩文件zip
	 * @param fileName 导出文件名称
	 * @param srcFile 源文件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportZipFile(String fileName, File srcFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String agent = request.getHeader("User-Agent");
		// 火狐浏览器导出文件不会url解码
		if (StringUtil.isEmpty(agent) == false && agent.toLowerCase().indexOf("firefox") > -1) {
			response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".zip\"");
		} else {
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".zip", "UTF-8"));
		}
		response.setContentType("application/zip;charset=UTF-8");
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		OutputStreamWriter writer = new OutputStreamWriter(zos);
		ZipEntry zipEntry = new ZipEntry(srcFile.getName());
		zos.putNextEntry(zipEntry);
		writer.write(FileUtils.readFileToString(srcFile));
		writer.flush();
		writer.close();
	}
	
	/**
	 * 导出压缩文件data
	 * @param fileName 导出文件名称
	 * @param file 源文件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void exportDataFile(String fileName, File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String agent = request.getHeader("User-Agent");
		// 火狐浏览器导出文件不会url解码
		if (StringUtil.isEmpty(agent) == false && agent.toLowerCase().indexOf("firefox") > -1) {
			response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".data\"");
		} else {
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".data", "UTF-8"));
		}
		response.setContentType("application/data;charset=UTF-8");
		byte[] bytes = new byte[1024];
		InputStream is = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		while (is.read(bytes) != -1) {
			out.write(bytes);
		}
		out.flush();
		out.close();
		is.close();
	}
	

}
