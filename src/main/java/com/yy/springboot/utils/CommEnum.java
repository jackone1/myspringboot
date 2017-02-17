package com.yy.springboot.utils;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;

import com.yy.springboot.base.BaseEnum;

/**
 * 枚举类
 * 
 * <ol>
 * 定义的枚举类
 * 
 * @author liwei
 */
public class CommEnum {

	/**
	 * sheet列名（药品资质）
	 * 
	 */
	public enum SheetColumnEnum implements BaseEnum {
		DRUG_CODE("1", "药品ID", "drugCode"), 
		CODE("2", "统编代码", "code"), 
		PHARMACOLOGICAL_TYPE("3", "药理分类", "pharmacologicalType"), 
		DRUG_NAME("4", "通用名", "drugName"), 
		DRUG_DOSAGE("5", "剂型", "drugDosage"), 
		DOSAGE_SPECIFIC("6", "规格", "dosageSpecific"), 
		DRUG_TRADE_NAME("7", "商品名", "drugTradeName"), 
		PACKAGING_UNIT("8", "包装单位", "packagingUnit"), 
		PACAKGE_SPECIFIC("9", "包装规格", "pacakgeSpecific"), 
		PACAKGE_MATERIAL("10", "包装材质", "pacakgeMaterial"), 
		PRODUCER_NAME("11", "生产企业名称", "producerName"), 
		PROCUREMENT_METHODS("42", "采购范围", "procurementMethods"), 
		NET_PRICE("12", "阳光平台采购单价（元）", "netPrice"), 
		//*********（只用于生产企业模板）********** Start
		BUSINESS_NAME("0", "经营企业名称", "businessName"),  //经营企业名称（只用于生产企业模板）
		//*********（只用于生产企业模板）********** End
		DECLARING_TYPE("13", "申报企业类型（1.生产企业、2.经营企业）", "declaringType"), 
		SPECIAL_CASE("41", "备注（结算价降幅）", "specialCase"), 
		ACTUAL_OFF("14", "平均结算价降幅", "actualOff"), 
		SETTLEMENT_PRICE("15", "结算价（元）=采购单价×(1-平均结算价降幅)", "settlementPrice"), 
		//*********（只用于经营企业模板）********** Start
		AFFORD_COST("16", "预计供应链服务成本分担比例", "affordCost"), 
		//*********（只用于经营企业模板）********** End
		QUALIFICATION1("17", "2010年版GMP认证", "qualification1"), 
		QUALIFICATION2("18", "药品安全性研究报告", "qualification2"), 
		QUALIFICATION3("19", "药物经济性评估报告（WHO标准）", "qualification3"), 
		QUALIFICATION4("20", "国家科学技术奖", "qualification4"), 
		QUALIFICATION5("21", "FDA、cGMP、JGMP、PIC/SGMP制剂认证", "qualification5"), 
		QUALIFICATION6("22", "美、欧、日生物等效性评价认定", "qualification6"), 
		QUALIFICATION7("23", "香港医院管理局采购药品", "qualification7"), 
		QUALIFICATION8("24", "生产企业国家实验室（CNAS）认证", "qualification8"), 
		QUALIFICATION9("25", "生产企业通过ISO14000环境管理体系认证", "qualification9"), 
		QUALIFICATION10("26", "保护期内的国家一类新药、原研药", "qualification10"), 
		QUALIFICATION11("27", "仿制药质量一致性评价证明（化学药品资质项）", "qualification11"), 
		QUALIFICATION12("28", "化合物专利药品（化学药品资质项）", "qualification12"), 
		QUALIFICATION13("29", "首仿（创）药品（化学药品资质项）", "qualification13"), 
		QUALIFICATION14("30", "过保护期的化合物专利药品（化学药品资质项）", "qualification14"), 
		QUALIFICATION15("31", "化学药品原料获FDA/COS/CEP证书（化学药品资质项）", "qualification15"), 
		QUALIFICATION16("32", "国家保密处方中成药", "qualification16"), 
		QUALIFICATION17("33", "中药一级保护品种", "qualification17"), 
		QUALIFICATION18("34", "中药二级保护品种", "qualification18"), 
//		CHECKCOL("35", "模板校验用（隐藏空列）", "checkCol"), 
		QUALIFICATION19("36", "主要药材GAP认证", "qualification19"), 
		NINGBO_PIRCE("37", "宁波限价采购价（元）", "ningboPirce"), 
		SHENZHEN_PIRCE("38", "深圳集团采购价（元）", "shenzhenPirce"), 
		DRUG_QUALITY_RECORD("39", "药品质量监督不良记录", "drugQualityRecord"), 
		ILLEGALRECORD("40", "企业违纪违法记录", "Illegalrecord"),
		DAILY_COST("43","常用剂量的日均费用（元）","dailyCost"), 
		SINGLE_DOSE("44","单剂量","singleDose"),
		DAILY_FREQUENCY("45","每日次数","dailyFrequency"),
		SPECIAL_PURPOSE("46","特殊用途的包装（或剂型）","specialPurpose"),
		SPECIAL_POPULATIONS("47","适用于特别人群","specialPopulations"),
		FIRST_LISTED("48","首家上市品种","firstListed"),
		EXPORTS_TO_EU("49","药品原料（指原料生产企业）出口至欧盟美日","exportsToEU");
		private String code; // 列
		private String label; // 列名
		private String propName; // 对应的属性名称

		/**
		 * 
		 * @param code
		 * @param label
		 * @param propName 对应的属性名称
		 */
		private SheetColumnEnum(String code, String label, String propName) {
			this.code = code;
			this.label = label;
			this.propName = propName;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}

		public String getPropName() {
			return propName;
		}
		
			
	}
	
	/**
	 * <pre>
	 * 经营企业资质
	 * </pre>
	 * 
	 */
	public enum Sheet2ColumnEnum implements BaseEnum {
		// TEST(DEMO)
		BUSINESS_NAME("0", "经营企业名称", "BUSINESSNAME"),
		INFORMATION1("1", "物流硬件配置说明（常温库）", "information1"),
		INFORMATION2("2", "物流硬件配置说明（低温库）", "information2"),
		INFORMATION3("3", "物流硬件配置说明（常温封闭运输车辆）", "information3"),
		INFORMATION4("4", "物流硬件配置说明（冷链运输车辆）", "information4"),
		INFORMATION5("5", "物流网络覆盖情况", "information5"),
		INFORMATION6("6", "配送服务优势证明", "information6"),
		INFORMATION7("7", "与其它经营企业建立战略合作关系", "information7");
			
		private String code; // 列
		private String label; // 列名
		private String propName; // 对应的属性名称
			/**
			 * 
			 * @param code
			 * @param label
			 * @param propName 对应的属性名称
			 */
			private Sheet2ColumnEnum(String code, String label, String propName) {
				this.code = code;
				this.label = label;
				this.propName = propName;
			}
	
			public String getEnumCode() {
				return code;
			}
	
			public String getEnumLabel() {
				return label;
			}
			
			public String getPropName() {
				return propName;
			}
		}

	/**
	 * <pre>
	 * 生产企业经营企业名称列表
	 * </pre>
	 * 
	 */
	public enum Sheet3ColumnEnum implements BaseEnum {
		// TEST(DEMO)
		BUSINESS_ENTERPRISE("0", "经营企业名称", "BUSINESSNAME"),
		BUSINESS_ENTERPRISEID("1", "企业编码", "information1");
			
		private String code; // 列
		private String label; // 列名
		private String propName; // 对应的属性名称
			/**
			 * 
			 * @param code
			 * @param label
			 * @param propName 对应的属性名称
			 */
		private Sheet3ColumnEnum(String code, String label, String propName) {
			this.code = code;
			this.label = label;
			this.propName = propName;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}
		
		public String getPropName() {
			return propName;
		}
		}
		/**
		 * <pre>
		 * 支持的sheet名称列表
		 * </pre>
		 * 
		 */
		public enum SheetNameEnum implements BaseEnum {
			
			SHEET_DRUG("1", "药品信息"),
			SHEET_SUPPLIER("2", "经营企业资质信息"),
			SHEET_PRODUCER("3", "厂家资质信息");
			private String code; // 枚举代码
			private String label; // 枚举标签
	
			private SheetNameEnum(String code, String label) {
				this.code = code;
				this.label = label;
			}
	
			public String getEnumCode() {
				return code;
			}
	
			public String getEnumLabel() {
				return label;
			}
	
			public Integer getIntegerEnumCode() {
				return Integer.parseInt(getEnumCode());
			}
		}
		/**
		 * <pre>
		 * 单号前缀规则
		 * 
		 * 平台生成单号的规则：
		 * 前缀+序列号
		 * </pre>
		 * 
		 */
		public enum CodePrefixEnum implements BaseEnum {
		// TEST(DEMO)
		PREFIX_CODE_TEST("CODE", "TEST", "DEMO", 100001);

		private String code; // 枚举代码
		private String prefix; // 枚举前缀
		private String label; // 枚举标签
		private Integer initValue;//生成序列的启始值(默认10000开始)

		private CodePrefixEnum(String code, String prefix, String label, Integer initValue) {
			this.code = code;
			this.prefix = prefix;
			this.label = label;
			this.initValue = initValue;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumPrefix() {
			return prefix;
		}

		public String getEnumLabel() {
			return label;
		}

		public Integer getInitValue() {
			if (initValue == null) {
				initValue = 10000;
			}
			
			return initValue;
		}
	}
	
	/**
	 * <pre>
	 * 排序类型
	 * </pre>
	 * 
	 */
	public enum SortTypeEnum implements BaseEnum {
		
		SORT_TYPE_ASC("ASC", "升序排序"),
		SORT_TYPE_DESC("DESC", "倒序排序");
		
		private String code; // 枚举代码
		private String label; // 枚举标签
		
		private SortTypeEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}
		
		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}
	}
	
	/**
	 * <pre>
	 * 登陆用户机构类型枚举
	 * </pre>
	 */
	public enum OrgTypeEnum implements BaseEnum {

		ORG_TYPE_BUSINESS("0", "经营企业"),
		ORG_TYPE_PRODUCER("1", "生产企业");
		private String code; // 枚举代码
		private String label; // 枚举标签

		private OrgTypeEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}

		public Integer getIntegerEnumCode() {
			return Integer.parseInt(getEnumCode());
		}
	}
	
	/**
	 * <pre>
	 * 导入excel数据列校验类型
	 * </pre>
	 */
	public enum ColumnCheckTypeEnum implements BaseEnum {
		
		CHECK_TYPE_NOTNULL("1", "非空数据校验"),
		CHECK_TYPE_PRICE("2", "价格类型校验,取2位小数"),
		CHECK_TYPE_PRICE_1("3", "价格类型校验,取4位小数"),
		CHECK_TYPE_LONG("4", "整数类型校验");
		
		private String code; // 枚举代码
		private String label; // 枚举标签
		
		private ColumnCheckTypeEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}
		
		public String getEnumCode() {
			return code;
		}
		
		public String getEnumLabel() {
			return label;
		}
	}
	
	/**
	 * <pre>
	 * 导入excel数据列校验类型
	 * </pre>
	 */
	public enum CellDataFormatEnum implements BaseEnum {
		
		DATA_FORMAT_DOUBLE("" + HSSFDataFormat.getBuiltinFormat("0.00"), "2位小数浮点型数值"),
		DATA_FORMAT_PRICE("" + HSSFDataFormat.getBuiltinFormat("0.0000"), "4位小数浮点型数值"),
		DATA_FORMAT_INT("" + HSSFDataFormat.getBuiltinFormat("0"), "整数型数值"),
		DATA_FORMAT_DATE("" + HSSFDataFormat.getBuiltinFormat("yyyy/MM/dd"), "date日期类型"),
		DATA_FORMAT_TIMESTAMP("" + HSSFDataFormat.getBuiltinFormat("yyyy/MM/dd HH:mm:ss"), "timestamp日期类型");
		
		private String code; // 枚举代码
		private String label; // 枚举标签
		
		private CellDataFormatEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}
		
		public String getEnumCode() {
			return code;
		}
		
		public String getEnumLabel() {
			return label;
		}

		public Short getShortEnumCode() {
			return Short.parseShort(code);
		}
	}

	/**
	 * <pre>
	 * 导入校验结果类型 枚举(1校验通过，2校验不通过)
	 * </pre>
	 * @author xuyuyang
	 */
	public enum CheckResultTypeEnum implements BaseEnum {
		CHECK_TITLE("0", "标题"),
		CHECK_PASS("1", "校验通过"),
		CHECK_NO_PASS("2", "校验不通过");
		
		private String code; // 枚举代码
		private String label; // 枚举标签

		private CheckResultTypeEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}

		public Integer getIntegerEnumCode() {
			return Integer.parseInt(getEnumCode());
		}
	}
	
	/**
	 * <pre>
	 * 接口上传接收结果
	 * </pre>
	 * @author LiYang
	 */
	public enum ReceiveResultEnum implements BaseEnum {
		SUCCESS("0", "成功"),
		FAIL("1", "失败");

		private String code; // 枚举代码
		private String label; // 枚举标签

		private ReceiveResultEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}
	}
	
	/**
	 * <pre>
	 * 接口上传接收结果
	 * </pre>
	 * @author LiYang
	 */
	public enum BookDataEnum implements BaseEnum {
		SHEET_STARROW("STARROW", "sheet数据开始行"),
	    PROP_INDEX("PROP_INDEX", "属性_列下标"),
	    DISPLAY_MODE("DISPLAY_MODE", "数据"),
	    BOOK_DATA("DATA", "数据");

		private String code; // 枚举代码
		private String label; // 枚举标签

		private BookDataEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}
	}
	
	
	/**
	 * <pre>
	 * 登录调用接口，返回状态码
	 * </pre>
	 * 
	 */
	public enum EntryStatusCodeEnum implements BaseEnum { 
		
			SUCCESS("0", "0"),
			FAIL("9", "99999"),
			ERROR_1("1", "1001"),
			ERROR_2("2", "1002"),
			ERROR_3("3", "1003"),
		    ERROR_4("4", "1004");
		
			private String code; // 列
			private String label; // 列名
	
			private EntryStatusCodeEnum(String code, String label) {
				this.code = code;
				this.label = label;
			}
	
			public String getEnumCode() {
				return code;
			}
	
			public String getEnumLabel() {
				return label;
			}
		}
	
	/**
	 * <pre>
	 * sheet数据显示方式
	 * </pre>
	 * @author LiYang
	 */
	public enum DataDisplayMode implements BaseEnum {
		HORIZONTAL("horizontal", "水平"),
		VERTICAL("vertical", "垂直");
		private String code; // 枚举代码
		private String label; // 枚举标签

		private DataDisplayMode(String code, String label) {
			this.code = code;
			this.label = label;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}
	}
	
	/**
	 * <pre>
	 * 申报企业类型
	 * </pre>
	 */
	public enum AppTypeEnum implements BaseEnum {

		ORG_TYPE_PRODUCER("1", "生产企业"),
		ORG_TYPE_BUSINESS("2", "经营企业");
		private String code; // 枚举代码
		private String label; // 枚举标签

		private AppTypeEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}

		public Integer getIntegerEnumCode() {
			return Integer.parseInt(getEnumCode());
		}
	}
	
	/**
	 * <pre>
	 * 校验结果
	 * </pre>
	 */
	public enum CheckTitleEnum implements BaseEnum {

		TITLE_TYPE("0", "校验结果"),
		TITLE_REMARK("1", "结果说明");
		private String code; // 枚举代码
		private String label; // 枚举标签

		private CheckTitleEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}

		public String getEnumCode() {
			return code;
		}

		public String getEnumLabel() {
			return label;
		}

		public Integer getIntegerEnumCode() {
			return Integer.parseInt(getEnumCode());
		}
	}
	
	/**
	 * <pre>
	 * 申报excel sheet名称枚举
	 * </pre>
	 */
	public enum ExcelSheetEnum implements BaseEnum {
		SHEET_1("SHEET_1", "药品信息"),
		SHEET_2("SHEET_2", "经营企业资质信息"),
		SHEET_3("SHEET_3", "经营企业名称列表");
		
		private String code; // 枚举代码
		private String label; // 枚举标签
		
		private ExcelSheetEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}
		
		public String getEnumCode() {
			return code;
		}
		
		public String getEnumLabel() {
			return label;
		}
	}
	
	/**
	 * <pre>
	 * 机构类型枚举
	 * </pre>
	 */
	public enum CompanyTypeEnum implements BaseEnum {
		TYPE_SUPPLIER("0", "经营企业"),
		TYPE_PRODUCER("1", "生产企业");
		
		private String code; // 枚举代码
		private String label; // 枚举标签
		
		private CompanyTypeEnum(String code, String label) {
			this.code = code;
			this.label = label;
		}
		
		public String getEnumCode() {
			return code;
		}
		
		public String getEnumLabel() {
			return label;
		}
	}
}
