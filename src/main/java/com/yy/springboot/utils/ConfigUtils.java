package com.yy.springboot.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.yy.springboot.memory.MemoryConstants;

public class ConfigUtils {
	private static final String CONFIGS_DIR_PATH = "datas/";
	
	private static Properties properties;
	static {
		properties = new Properties();
		readConfigsProperties(CONFIGS_DIR_PATH, properties);
	}

	public static String getValueByKey(String key) {
		return properties.getProperty(key);
	}

	public static void setValueByKey(String key, String value){
		properties.setProperty(key, value);
		try {
			properties.store(FileUtils.openOutputStream(FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_SYNC_DATA_PROPERTIES)), "Update '" + key + "' value");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *  读取与jar包同级的configs目录下的配置文件
	 */
	private static void readConfigsProperties(String configsDirPath, Properties properties) {
		File configsDir = new File(configsDirPath);
		try {
			Collection<File> files = FileUtils.listFiles(configsDir, new String[]{"properties"}, true);
			for (File file : files) {
				readPropertiesFile(file, properties);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  读取资源文件
	 */
	private static void readPropertiesFile(File file, Properties properties) throws IOException {
		InputStream inputStream = FileUtils.openInputStream(file);
		properties.load(inputStream);
		inputStream.close(); // 关闭流
	}	
}
