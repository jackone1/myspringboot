package com.yy.springboot.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yy.springboot.memory.MemoryConstants;

/**
 * 使用Jaxb2.0实现XML<->Java Object的Mapper.
 * 
 * 在创建时需要设定所有需要序列化的Root对象的Class.
 * 特别支持Root对象是Collection的情形.
 * 
 * @author calvin
 */
public class JaxbUtils {
	private static final Logger logger = LoggerFactory.getLogger(JaxbUtils.class);
	
	private static ConcurrentMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class, JAXBContext>();

	/**
	 * Java Object->Xml without encoding.
	 * @throws JAXBException 
	 */
	public static String toXml(Object root) {
		Class clazz = Reflections.getUserClass(root);
		return toXml(root, clazz, null);
	}

	/**
	 * Java Object->Xml with encoding.
	 * @throws JAXBException 
	 */
	public static String toXml(Object root, String encoding) {
		Class clazz = Reflections.getUserClass(root);
		return toXml(root, clazz, encoding);
	}

	/**
	 * Java Object->Xml with encoding.
	 * @throws JAXBException 
	 */
	public static String toXml(Object root, Class clazz, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(clazz, encoding).marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			logger.debug("toXml", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Java Collection->Xml without encoding, 特别支持Root Element是Collection的情形.
	 * @throws JAXBException 
	 */
	public static String toXml(Collection<?> root, String rootName, Class clazz) {
		return toXml(root, rootName, clazz, null);
	}

	/**
	 * Java Collection->Xml with encoding, 特别支持Root Element是Collection的情形.
	 * @throws JAXBException 
	 */
	public static String toXml(Collection<?> root, String rootName, Class clazz, String encoding) {
		try {
			CollectionWrapper wrapper = new CollectionWrapper();
			wrapper.collection = root;

			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
					CollectionWrapper.class, wrapper);

			StringWriter writer = new StringWriter();
			createMarshaller(clazz, encoding).marshal(wrapperElement, writer);

			return writer.toString();
		} catch (JAXBException e) {
			logger.debug("toXml", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Xml->Java Object.
	 * @throws JAXBException 
	 */
	public static <T> T fromXml(String xml, Class<T> clazz) {
		try {
			logger.debug("xml:" + xml);
			
			StringReader reader = new StringReader(xml);
			return (T) createUnmarshaller(clazz).unmarshal(reader);
		} catch (JAXBException e) {
			logger.debug("fromXml", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Xml加签名
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> String signXml(String xml, Class<T> clazz) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		logger.debug("xml:" + xml);
		T bean = (T)JaxbUtils.fromXml(xml, clazz);
		Method setSign = bean.getClass().getMethod("setSign", String.class);
		setSign.invoke(bean, MD5Util.sign(xml, MemoryConstants.PWD_KEY, MemoryConstants.UTF_8));
		xml = JaxbUtils.toXml(bean, MemoryConstants.MO_CHARSET);
		xml = ZipUtil.zipBase64String(xml);
		return xml;
	}
	
	/**
	 * Xml解密验签
	 * <p>验证不通过则返回null</p>
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 	
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static <T> String unSignXml(String xml, Class<T> clazz) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		logger.debug("xml:" + xml);
		String xmlData = ZipUtil.unzipBase642String(xml);
		T bean = JaxbUtils.fromXml(xmlData, clazz);
		Method getSign = bean.getClass().getMethod("getSign");
		String sign = getSign.invoke(bean)+"";
		Method setSign = bean.getClass().getMethod("setSign", String.class);
		setSign.invoke(bean, "");
		xmlData = JaxbUtils.toXml(bean, MemoryConstants.MO_CHARSET);
		// 签名验证
		if(!MD5Util.verify(xmlData, sign, MemoryConstants.PWD_KEY, MemoryConstants.UTF_8)) {
			logger.debug("xml解密验签失败");
			return null;
		}
		return xmlData;
	}

	/**
	 * 创建Marshaller并设定encoding(可为null).
	 * 线程不安全，需要每次创建或pooling。
	 * @throws JAXBException 
	 */
	public static Marshaller createMarshaller(Class clazz, String encoding) {
		try {
			JAXBContext jaxbContext = getJaxbContext(clazz);

			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			if (StringUtils.isNotBlank(encoding)) {
				marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			}

			return marshaller;
		} catch (JAXBException e) {
			logger.debug("createMarshaller", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建UnMarshaller.
	 * 线程不安全，需要每次创建或pooling。
	 * @throws JAXBException 
	 */
	public static Unmarshaller createUnmarshaller(Class clazz) {
		try {
			JAXBContext jaxbContext = getJaxbContext(clazz);
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			logger.debug("createUnmarshaller", e);
			throw new RuntimeException(e);
		}
	}

	protected static JAXBContext getJaxbContext(Class clazz) {
		Validate.notNull(clazz, "'clazz' must not be null");
		JAXBContext jaxbContext = jaxbContexts.get(clazz);
		if (jaxbContext == null) {
			try {
				jaxbContext = JAXBContext.newInstance(clazz, CollectionWrapper.class);
				jaxbContexts.putIfAbsent(clazz, jaxbContext);
			} catch (JAXBException ex) {
				throw new RuntimeException("Could not instantiate JAXBContext for class [" + clazz + "]: "
						+ ex.getMessage(), ex);
			}
		}
		return jaxbContext;
	}

	/**
	 * 封装Root Element 是 Collection的情况.
	 */
	public static class CollectionWrapper {

		@XmlAnyElement
		protected Collection<?> collection;
	}

}
