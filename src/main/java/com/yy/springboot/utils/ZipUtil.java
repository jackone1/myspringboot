package com.yy.springboot.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtil {

	private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class.getName());
	
	/**
	 * 使用zip进行压缩
	 * 
	 * @param str
	 * @author: 彭志翔
	 *            压缩前的文本
	 * @return 返回压缩后的文本
	 */
	public static String zipBase64String(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("zip"));
			zout.write(str.getBytes("utf-8"));
			zout.closeEntry();
			compressed = out.toByteArray();

			compressedStr = Base64.encodeBase64String(compressed);
		} catch (IOException e) {
			logger.error("使用zip进行压缩IOException", e);
			compressed = null;
		} catch (Exception e) {
			logger.error("使用zip进行压缩Exception", e);
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}

	/**
	 * 使用zip进行解压缩
	 * 
	 * @param compressed
	 *            Base64转码的压缩文本
	 * @return 解压后的字符串
	 */
	public static String unzipBase642String(String base64CompressedStr) {
		if (base64CompressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		ZipInputStream ginzip = null;
		String decompressed = null;
		try {
			byte[] compressed = Base64.decodeBase64(base64CompressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new ZipInputStream(in);
			ginzip.getNextEntry();

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString("utf-8");
		} catch (IOException e) {
			logger.error("使用zip进行解压缩IOException", e);
		}  catch (Exception e) {
			logger.error("使用zip进行解压缩Exception", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

}
