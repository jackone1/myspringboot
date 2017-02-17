package com.yy.springboot.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yy.springboot.beans.SpringBootDrugBeanList;
import com.yy.springboot.memory.utils.MemorySpringBootDrugBeanListUtils;

/**
 * 内存中的对象列表
 * 1.MemoryObjects为单例，通过getInstance方法获取对象
 * 2.initObjects方法：用来服务启动时，将数据加载到内存中
 * 3.例：MemoryObjects中的demoBeanList对象操作，参见MemoryDemoBeanListUtils
 */
public class MemoryObjects {

	private static final Logger logger = LoggerFactory.getLogger(MemoryObjects.class);

	private static MemoryObjects memoryObjects;
	
	private SpringBootDrugBeanList springBootDrugBeanList;
	
	private MemoryObjects() {
//		initObjects(); //初始化由com.yy.springBootsc.memory.MemoryStartupRunner类在程序启动的时候完成
	}
	
	/**
	 * 创建单例内存对象
	 * @return
	 */
	public synchronized static MemoryObjects getInstance() {
		if (memoryObjects == null) {
			memoryObjects = new MemoryObjects();
		}
		
		return memoryObjects;
	}
	
	/**
	 * 初始对象(服务启动时执行)
	 * @throws Exception 
	 */
	public synchronized void initObjects() throws Exception {
		logger.info("初始化数据中...");
		
		MemorySpringBootDrugBeanListUtils.reloadSpringBootDrugBeanListByDataFile(this);
		
		logger.info("初始化数据完成");
	}
	
	public SpringBootDrugBeanList getSpringBootDrugBeanList() {
		return springBootDrugBeanList;
	}

	public void setSpringBootDrugBeanList(SpringBootDrugBeanList springBootDrugBeanList) {
		this.springBootDrugBeanList = springBootDrugBeanList;
	}

}
