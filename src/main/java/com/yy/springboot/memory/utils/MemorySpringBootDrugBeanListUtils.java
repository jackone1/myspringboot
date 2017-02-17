package com.yy.springboot.memory.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yy.springboot.beans.SpringBootDrugBean;
import com.yy.springboot.beans.SpringBootDrugBeanList;
import com.yy.springboot.memory.MemoryConstants;
import com.yy.springboot.memory.MemoryObjects;
import com.yy.springboot.utils.BeanCopyUtils;
import com.yy.springboot.utils.Collections3;
import com.yy.springboot.utils.JaxbUtils;
import com.yy.springboot.utils.StringUtil;

/**
 * 操作MemoryObjects中SpringBootDrugBeanList对象的工具类 
 * 1.reloadSpringBootDrugBeanListByDataFile: 从文件中读取数据到内存中
 * 2.writeSpringBootDrugBeanList2File      ：将数据从内存写到文件
 * 3.getSpringBootDrugBeanListByComparator ：根据顺序比较器，并返回内存中对象的Copy
 * 4.setSpringBootDrugBeanList			   ：设置内存对象，并根据传入条件（isPersistence）判断是否将数据保存到文件
 */
public class MemorySpringBootDrugBeanListUtils {

	private static final Logger logger = LoggerFactory.getLogger(MemorySpringBootDrugBeanListUtils.class);
	
	/**
	 * 从文件中读取数据到内存中
	 * @throws Exception 
	 */
	public static synchronized void reloadSpringBootDrugBeanListByDataFile(MemoryObjects memoryObjects) throws Exception {
		logger.info("初始化SpringBootDrug数据中...");
		
		File file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_springBootDRUG);
		String xmlData = FileUtils.readFileToString(file, MemoryConstants.MO_CHARSET);
		xmlData = JaxbUtils.unSignXml(xmlData, SpringBootDrugBeanList.class);
		// 签名验证通过
		if(!StringUtil.isEmpty(xmlData)) {
			SpringBootDrugBeanList fromXml = JaxbUtils.fromXml(xmlData, SpringBootDrugBeanList.class);
			MemorySpringBootDrugBeanListUtils.setSpringBootDrugBeanList(memoryObjects, fromXml, false);
		}
		
		logger.info("初始化SpringBootDrug数据完成");
	}
	
	/**
	 * 将数据从内存写到文件
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static synchronized void writeSpringBootDrugBeanList2File(MemoryObjects memoryObjects) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		logger.info("保存SpringBootDrug数据...");
		
		String xmlData = JaxbUtils.toXml(memoryObjects.getSpringBootDrugBeanList(), MemoryConstants.MO_CHARSET);
		xmlData = JaxbUtils.signXml(xmlData, SpringBootDrugBeanList.class);
		
		File file = FileUtils.getFile(MemoryConstants.MO_DATA_PATH, MemoryConstants.MO_DATA_PATH_springBootDRUG);
		FileUtils.writeStringToFile(file, xmlData, MemoryConstants.MO_CHARSET, false);//完全覆盖
		
		logger.info("保存SpringBootDrug数据完成");
	}

	/**
	 * 根据顺序比较器，并返回内存中对象的Copy
	 * @param springBootDrugBeansComparator
	 * @return
	 */
	public static SpringBootDrugBeanList getSpringBootDrugBeanListByComparator(MemoryObjects memoryObjects, Comparator<SpringBootDrugBean> springBootDrugBeansComparator) {
		List<SpringBootDrugBean> rtnSpringBootDrugBeans = new ArrayList<>();
		SpringBootDrugBeanList rtnSpringBootDrugBeanList = new SpringBootDrugBeanList();
		rtnSpringBootDrugBeanList.setSpringBootDrugBeans(rtnSpringBootDrugBeans);
		BeanCopyUtils.copy(memoryObjects.getSpringBootDrugBeanList().getSpringBootDrugBeans(), rtnSpringBootDrugBeans);
		
		if (springBootDrugBeansComparator == null) {
		}
		
		//对象中的LIST进行排序
		Collections.sort(rtnSpringBootDrugBeans, springBootDrugBeansComparator);
		
		return rtnSpringBootDrugBeanList;
	}
	
	/**
	 * 根据顺序比较器，并返回内存中对象的Copy
	 * @param SpringBootDrugBeansComparator
	 * @return
	 */
	public static SpringBootDrugBeanList getSpringBootDrugBeanListByQueryAndComparator(MemoryObjects memoryObjects, Comparator<SpringBootDrugBean> SpringBootDrugBeansComparator, String propertyName, String wanterValue) {
		List<SpringBootDrugBean> rtnSpringBootDrugBeans = new ArrayList<>();
		SpringBootDrugBeanList rtnSpringBootDrugBeanList = new SpringBootDrugBeanList();
		rtnSpringBootDrugBeanList.setSpringBootDrugBeans(rtnSpringBootDrugBeans);

		List<SpringBootDrugBean> springBootDrugBeans = memoryObjects.getSpringBootDrugBeanList().getSpringBootDrugBeans();
		List<SpringBootDrugBean> extractToList = Collections3.extractToList(springBootDrugBeans, propertyName, wanterValue);
		BeanCopyUtils.copy(extractToList, rtnSpringBootDrugBeans);
		
		//对象中的LIST进行排序
		Collections.sort(rtnSpringBootDrugBeanList.getSpringBootDrugBeans(), SpringBootDrugBeansComparator);
		
		return rtnSpringBootDrugBeanList;
	}
	
	/**
	 * 设置内存对象，并根据传入条件（isPersistence）判断是否将数据保存到文件
	 * @param SpringBootDrugBeanList
	 * @param isPersistence 是否持久化到data文件（true:保存数据到文件，false:不保存到文件）
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static synchronized void setSpringBootDrugBeanList(MemoryObjects memoryObjects, SpringBootDrugBeanList newSpringBootDrugBeanList, boolean isPersistence) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		logger.info("设置内存中的SpringBootDrug数据....");
		//		SpringBootDrugBeanList oldSpringBootDrugBeanList = this.SpringBootDrugBeanList; //旧数据
		memoryObjects.setSpringBootDrugBeanList(newSpringBootDrugBeanList);//新数据
		logger.info("设置内存中的SpringBootDrug数据完成");
		
		if (isPersistence) { //保存数据到文件
			try {
				MemorySpringBootDrugBeanListUtils.writeSpringBootDrugBeanList2File(memoryObjects);
			} catch (IOException e) {
//				this.SpringBootDrugBeanList = oldSpringBootDrugBeanList; //失败回滚????
//				MemorySpringBootDrugBeanListUtils.writeSpringBootDrugBeanList2File(memoryObjects);
				logger.error("保存SpringBootDrug数据到文件失败", e);
				throw e;
			} finally {
//				oldSpringBootDrugBeanList = null;
			}
		}
	}
}
