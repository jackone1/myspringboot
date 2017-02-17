package com.yy.springboot.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.yy.springboot.base.BaseBean;

/**
 * 导入-药品资质的所有记录
 *
 */
@XmlRootElement
public class SpringBootDrugBeanList extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8055377156305890370L;
	
	private List<SpringBootDrugBean> springBootDrugBeans;

	private String sign;
	
	/**
	 * 
	 * @param comparator 可指定比较器，对LIST进行排列
	 * @return
	 */
	@XmlElementWrapper(name="springBootDrugBeans")
	@XmlElement(name="springBootDrugBean")
	public List<SpringBootDrugBean> getSpringBootDrugBeans() {
		if (springBootDrugBeans == null) {
			springBootDrugBeans = new ArrayList<>();
		}
		
		return springBootDrugBeans;
	}

	/**
	 * 设值
	 * @param demoBeans
	 */
	public void setSpringBootDrugBeans(List<SpringBootDrugBean> springBootDrugBeans) {
		this.springBootDrugBeans = springBootDrugBeans;
	}

	@XmlElement(name="sign")
	public String getSign() {
		if(sign==null) 
			sign = "";
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
