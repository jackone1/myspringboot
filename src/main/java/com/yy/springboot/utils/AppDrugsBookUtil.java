package com.yy.springboot.utils;

import java.util.ArrayList;
import java.util.List;

import com.yy.springboot.utils.CommEnum.SheetColumnEnum;

/**
 * 药品资质-导入excel对应列（注意顺序）
 *
 */
public class AppDrugsBookUtil {

	public static final String appDurgInfo = "药品信息";
	public static final String entQual = "经营企业资质信息";
	public static final String entName = "经营企业名称列表";
	public static final int appDurgInfoStart = 1;
	public static final int entQualStart = 1;

	/**
	 * 药品信息列
	 * @return
	 */
	public static final List<String> getAppDurgInfoProp(boolean isProducerUser) {
		List<String> list = new ArrayList<String>();
		list.add(SheetColumnEnum.DRUG_CODE.getPropName());// 药品ID
		list.add(SheetColumnEnum.CODE.getPropName());// 统编代码
		list.add(SheetColumnEnum.PHARMACOLOGICAL_TYPE.getPropName());// 药理分类
		list.add(SheetColumnEnum.DRUG_NAME.getPropName());// 通用名
		list.add(SheetColumnEnum.DRUG_DOSAGE.getPropName());// 剂型
		list.add(SheetColumnEnum.DOSAGE_SPECIFIC.getPropName());// 规格
		list.add(SheetColumnEnum.DRUG_TRADE_NAME.getPropName());// 商品名
		list.add(SheetColumnEnum.PACKAGING_UNIT.getPropName());// 包装单位
		list.add(SheetColumnEnum.PACAKGE_SPECIFIC.getPropName());// 包装规格
		list.add(SheetColumnEnum.PACAKGE_MATERIAL.getPropName());// 包装材质
		list.add(SheetColumnEnum.PRODUCER_NAME.getPropName());// 生产企业名称
		list.add(SheetColumnEnum.PROCUREMENT_METHODS.getPropName());// 采购范围
		list.add(SheetColumnEnum.NET_PRICE.getPropName());// 阳光平台采购单价（元）
		//*********（只用于生产企业模板）********** Start
		if (isProducerUser) {
			list.add(SheetColumnEnum.BUSINESS_NAME.getPropName());// 经营企业名称
		}
		//*********（只用于生产企业模板）********** End
		list.add(SheetColumnEnum.DECLARING_TYPE.getPropName());// 申报企业类型（1.生产企业、2.经营企业）
		list.add(SheetColumnEnum.SPECIAL_CASE.getPropName());//  备注（结算价降幅）
		list.add(SheetColumnEnum.ACTUAL_OFF.getPropName());// 平均结算价降幅
		list.add(SheetColumnEnum.SETTLEMENT_PRICE.getPropName());// 结算价（元）=采购单价×(1-平均结算价降幅)
		//*********（只用于经营企业模板）********** Start
		if (!isProducerUser) {

			list.add(SheetColumnEnum.AFFORD_COST.getPropName());// 预计供应链服务成本分担比例
		}
		list.add(SheetColumnEnum.QUALIFICATION1.getPropName());// 2010年版GMP认证
		list.add(SheetColumnEnum.QUALIFICATION2.getPropName());// 药品安全性研究报告
		list.add(SheetColumnEnum.QUALIFICATION3.getPropName());// 药物经济性评估报告（WHO标准）
		list.add(SheetColumnEnum.QUALIFICATION4.getPropName());// 国家科学技术奖
		list.add(SheetColumnEnum.QUALIFICATION5.getPropName());// FDA、cGMP、JGMP、PIC/SGMP制剂认证
		list.add(SheetColumnEnum.QUALIFICATION6.getPropName());// 美、欧、日生物等效性评价认定
		list.add(SheetColumnEnum.QUALIFICATION7.getPropName());// 香港医院管理局采购药品
		list.add(SheetColumnEnum.QUALIFICATION8.getPropName());// 生产企业国家实验室（CNAS）认证
		list.add(SheetColumnEnum.QUALIFICATION9.getPropName());// 生产企业通过ISO14000环境管理体系认证
		list.add(SheetColumnEnum.QUALIFICATION10.getPropName());// 保护期内的国家一类新药、原研药
		list.add(SheetColumnEnum.QUALIFICATION11.getPropName());// 仿制药质量一致性评价证明（化学药品资质项）
		list.add(SheetColumnEnum.QUALIFICATION12.getPropName());// 化合物专利药品（化学药品资质项）
		list.add(SheetColumnEnum.QUALIFICATION13.getPropName());// 首仿（创）药品（化学药品资质项）
		list.add(SheetColumnEnum.QUALIFICATION14.getPropName());// 过保护期的化合物专利药品（化学药品资质项）
		list.add(SheetColumnEnum.QUALIFICATION15.getPropName());// 化学药品原料获FDA/COS/CEP证书（化学药品资质项
		list.add(SheetColumnEnum.QUALIFICATION16.getPropName());// 国家保密处方中成药
		list.add(SheetColumnEnum.QUALIFICATION17.getPropName());// 中药一级保护品种
		list.add(SheetColumnEnum.QUALIFICATION18.getPropName());// 中药二级保护品种
		//list.add(SheetColumnEnum.CHECKCOL.getPropName());// 模板校验用（隐藏空列）
		list.add(SheetColumnEnum.QUALIFICATION19.getPropName());// 主要药材GAP认证
		list.add(SheetColumnEnum.NINGBO_PIRCE.getPropName());// 宁波限价采购价（元）
		list.add(SheetColumnEnum.SHENZHEN_PIRCE.getPropName());// 深圳集团采购价（元）
		list.add(SheetColumnEnum.DRUG_QUALITY_RECORD.getPropName());// 药品质量监督不良记录
		list.add(SheetColumnEnum.ILLEGALRECORD.getPropName());// 企业违纪违法记录
		//*********（只用于经营企业模板）********** Start
		if (!isProducerUser) {
			list.add(SheetColumnEnum.DAILY_COST.getPropName());//  日均费用
			list.add(SheetColumnEnum.SINGLE_DOSE.getPropName());// 单剂量
			list.add(SheetColumnEnum.DAILY_FREQUENCY.getPropName());// 每日次数
			list.add(SheetColumnEnum.SPECIAL_PURPOSE.getPropName());//  特殊用途的包装（或剂型）
			list.add(SheetColumnEnum.SPECIAL_POPULATIONS.getPropName());//  适用于特别人群
			list.add(SheetColumnEnum.FIRST_LISTED.getPropName());//  首家上市品种
			list.add(SheetColumnEnum.EXPORTS_TO_EU.getPropName());//  药品原料（指原料生产企业）出口至欧盟美日
		}
		//*********（只用于经营企业模板）********** End
		return list;
	}
	
	/**
	 * 经营企业资质
	 * @return
	 */
	public static final List<String> getEnterQualProp() {
		List<String> list = new ArrayList<String>();
		list.add("businessName");// 经营企业名称
		list.add("information1");// 物流硬件配置说明（常温库）
		list.add("information2");// 物流硬件配置说明（低温库）
		list.add("information3");// 物流硬件配置说明（常温封闭运输车辆）
		list.add("information4");// 物流硬件配置说明（冷链运输车辆）
		list.add("information5");// 物流网络覆盖情况
		list.add("information6");// 配送服务优势证明
		list.add("information7");// 配与其它经营企业建立战略合作关系
		return list;
	}
	
	/**
	 * 经营企业
	 * @return
	 */
	public static final List<String> getEnterNmProp() {
		List<String> list = new ArrayList<String>();
		list.add("businessEnterprise");// 经营企业名称
		return list;
	}
}
