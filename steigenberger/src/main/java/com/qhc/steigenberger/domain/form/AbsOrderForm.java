/**
 * 
 */
package com.qhc.steigenberger.domain.form;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wang@dxc.com
 *
 */
public abstract class AbsOrderForm {
	/**
	 * 客户基本信息 Basic information
	 */
	private String contracterCode;//签约单位 Contract unit
	private String customerName;//店名 customer name
	private boolean isConvenientStore;//是否便利店 convenience store
	private String salesCode;//客户经理 Customer manager
	private String salesTelnumber;//客户经理电话 Customer manager Tel
	private boolean isNew;//是否新客户 new customer
	/**
	 * 合同详细信息 Contract details
	 */
	private String sequenceNumber;//流水号 code
	private String contractNumber;//合同号 contract no
	private String saleType;//销售类型 Sales type
	private String taxRate;//税率 Rate
	private String incoterm;//国际贸易条件 international trade
	private String incotermContect;//国际贸易条件 international trade
	private double contractValue;//原币合同金额 Contract amount
	private double contractRMBValue;//合同金额 Contract amount
	private String currency;//币种 currency
	private double currencyRate;//汇率 exchange rate
	private Date createDate;//录入日期 Date of entry
	//public abstract double getItemsAmount();//购销明细金额合计 Aggregate amount
	/*
	 * 合同详细信息 Contract details
	 */
	private String officeCode;//大区 area
	private String groupCode;//中心 center
	private int warrenty;//保修年限
	private boolean isBidding;//安装方式 installation
	private List<Map<Integer,String>> address; //地区 Region,到货地址 Address
	private Map<String,String> contactors;//授权人及身份证号
	private String confirmTypeCode;//收货方式 Receiving way
	private String transferTypeCode;//运输类型 Type of transportation
	//
	private double bodyDiscount;//柜体折扣 standard discount
	private double mainDiscount;//机组折扣 standard discount
	private boolean isLongterm;//是否为长期折扣
	/**
	 * 结算方式 Method of payment
	 */
	private List<AbsSettlement> settles;
	/**
	 * 调研表相关内容 Research table related content
	 */
	private boolean isTerm1;//柜体控制阀件是否甲供
	private boolean isTerm2;//分体柜是否远程监控
	private boolean isTerm3;//立柜柜体是否在地下室
	/**
	 * 购销明细 Purchase and sale subsidiar
	 */
	private List<ProductItem> items;//购销明细
	private String comments;//备注
	/**
	 * 附件信息 Attachment information
	 */
	private List<String> attachedFileName;
	
}
