package com.yy.springboot.utils;

import java.util.EnumSet;

import com.yy.springboot.base.BaseEnum;


/**
 * 数据处理工具类
 * @author liwei
 */
public class DataUtil {


	/**
	 * 获取枚举类型
	 * @param <T>
	 * @param enumCode
	 * @param enumClass
	 * @return
	 */
	public static <T extends Enum<T> & BaseEnum> T getEnumType(String enumCode, Class<T> enumClass) {
		T enumType = null;
		EnumSet<T> enumSet = EnumSet.allOf(enumClass);
		for (T enumItem : enumSet) {
			if(enumItem.getEnumCode().equals(enumCode)) {
				enumType = enumItem;
				break;
			}
		}
		return enumType;
	}
}
