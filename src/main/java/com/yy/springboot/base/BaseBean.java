package com.yy.springboot.base;

import java.io.Serializable;

public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 790044478495610796L;

	/**
	 * 序号（该值不可重复），可用来进行默认的排序
	 */
	protected Integer index = 0;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
