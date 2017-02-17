package com.yy.springboot.base;

/**
 * 表字段类
 * 
 * @author liwei
 *
 */
public class ColumnData {

	private String columnName; // 列名
	private String dataType; // 数据类型
	private String javaType; // java数据类型
	private String comments; // 描述
	private String columnLowName; // 列名小写
	private String dataScale; // 小数位数
	private String dataPrecision; // 数据的精度
	private String dataMaxLength; // 最大长度

	public ColumnData() {
	}

	public ColumnData(String columnName, String columnLowName, String dataType, String javaType, String comments,
			String dataScale, String dataPrecision, String dataMaxLength) {
		this.columnName = columnName;
		this.columnLowName = columnLowName;
		this.dataType = dataType;
		this.javaType = javaType;
		this.comments = comments;
		this.dataScale = dataScale;
		this.dataPrecision = dataPrecision;
		this.dataMaxLength = dataMaxLength;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getColumnLowName() {
		return columnLowName;
	}

	public void setColumnLowName(String columnLowName) {
		this.columnLowName = columnLowName;
	}

	public String getDataScale() {
		return dataScale;
	}

	public void setDataScale(String dataScale) {
		this.dataScale = dataScale;
	}

	public String getDataPrecision() {
		return dataPrecision;
	}

	public void setDataPrecision(String dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public String getDataMaxLength() {
		return dataMaxLength;
	}

	public void setDataMaxLength(String dataMaxLength) {
		this.dataMaxLength = dataMaxLength;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

}
