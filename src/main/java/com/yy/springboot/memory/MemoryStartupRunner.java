package com.yy.springboot.memory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行
 */
@Component()
@Order(value=1)
public class MemoryStartupRunner implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(MemoryStartupRunner.class);
	
    @Override
    public void run(String... args) throws Exception {
    	//校验文件是否存在
    	File file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_springBootDRUG);
    	validateFileExists(file);
    	
    	file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_springBootDRUGBASE);
    	validateFileExists(file);
    	
    	file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_springBootORGANIZATION);
    	validateFileExists(file);
    	
    	file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_springBootPRODUCER);
    	validateFileExists(file);
    	
    	file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_springBootSUPPLIER);
    	validateFileExists(file);
    	
    	file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_DB);
    	validateFileExists(file);
    	
    	file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_SYNC_DATA_PROPERTIES);
    	validateFileExists(file);
    }
    
    /**
     * 校验文件是否存在，不存在抛出异常（程序启动失败）
     * @param file
     */
    private void validateFileExists(File file) {
    	if (!file.exists()) {
    		logger.error("系统文件({})缺失", file.getName());
    		throw new RuntimeException("系统文件(" + file.getName() + ")缺失");
		}
    }

}