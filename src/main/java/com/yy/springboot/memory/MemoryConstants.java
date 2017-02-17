package com.yy.springboot.memory;

import org.apache.commons.io.FileUtils;

public class MemoryConstants {

	/**
	 * 数据文件保存位置
	 */
//	@Value("${MO_DATA_PATH:datas}")
	public static final String MO_DATA_PATH = "datas";
	
	/**
	 * demo数据文件名
	 */
//	@Value("${MO_DATA_PATH_DEMO:demo.data}")
	public static final String MO_DATA_PATH_DEMO = "demo.data";
	
	public static final String MO_DATA_PATH_springBootDRUGBASE = "springBootDrugBase.data";
	
	public static final String MO_DATA_PATH_springBootDRUG = "springBootDrug.data";
	
	public static final String MO_DATA_PATH_springBootORGANIZATION = "springBootOrganization.data";
	
	public static final String MO_DATA_PATH_springBootPRODUCER = "springBootProducer.data";
	
	public static final String MO_DATA_PATH_springBootSUPPLIER= "springBootSupplier.data";
	
	public static final String MO_CHARSET = "UTF-8";
	
	public static final String MO_SYNC_DATA_PROPERTIES = "data_synchronize.properties";

	public static final String MO_DATA_PATH_DB = "db.data";
	
	/**
	 * 模板文件保存位置
	 */
	public static final String TEMPLATE_PATH = "datas";
	public static final String TEMPLATE_PATH_SUPPLIER = "经营企业模板.xls";
	public static final String TEMPLATE_PATH_SUPPLIER0 = "复核数据.xls";
	public static final String TEMPLATE_PATH_SUPPLIER1 = "复核数据（生产企业数据）.xls";
	public static final String TEMPLATE_PATH_SUPPLIER2 = "复核数据（经营企业数据）.xls";
	public static final String TEMPLATE_PATH_PRODUCER = "生产企业模板.xls";

	/** 密钥key */
	public static final String PWD_KEY = "zaq1xsw2";
	/** UTF_8 */
	public static final String UTF_8 = "UTF-8";
	
	// 临时文件
	public static final String TEMP_PATH = FileUtils.getTempDirectoryPath(); //"C:\\temp\\";
	public static final String TEMP_NAME= "申请信息校验结果";

}
