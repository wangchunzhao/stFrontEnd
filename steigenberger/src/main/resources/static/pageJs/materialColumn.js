var materialsColumn = [{
		title: '<input type="button"  value="+" class="btn btn-primary viewDisable" onclick="addSubsidiary()" id="materialAdd"/>',
	    align: 'center',
	    formatter: function(value, row, index) {
	    	var actions = [];
	    	if(row.itemStatus!='Z2'){
	    		actions.push('<a class="btn"  id="materialsEdit" onclick="editMaterials(\'' +row.rowNum+'|'+index+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
	    		actions.push('<a class="btn viewDisable"  onclick="copyMaterials(\'' + row.rowNum + '\')"><i class="fa fa-copy"></i>复制</a>');
	    	}
	    	if(row.itemStatus=='Z2'){
	    		actions.push('<a class="btn viewDisable"  onclick="recoveryMaterials(\'' +row.rowNum+'|'+index+ '\')"><i class="fa fa-edit"></i>恢复</a>');
	    	}
	    	actions.push('<a class="btn viewDisable"  onclick="insertMaterials(\'' +index+ '\')"><i class="fa fa-edit"></i>插入</a>');
			if(row.itemStatus=='10'){
				actions.push('<a class="btn viewDisable"  onclick="cacelMaterials(\'' + row.rowNum + '|'+index+ '\')"><i class="fa fa-edit"></i>取消</a>');
			}
			if(row.itemStatus!='Z2'&&row.itemStatus!='10')	{
				actions.push('<a class="btn viewDisable"  onclick="removeMaterials(\'' + row.rowNum + '\')"><i class="fa fa-edit"></i>删除</a>');
			}	
			return actions.join('');		
	    }
	},{
		title:'行号',
	    field: 'rowNum'
	},{
		title:'调研表',
		align: 'center',
	    formatter: function(value, row, index) {
	    	var actions = [];
	    	var configurable = row.isConfigurable+'';
	    	if(configurable=='true'){
	    		actions.push('<a class="btn" onclick="openConfig(\'' + row.rowNum+'|'+index+'|'+row.itemStatus+'\')"><i class="fa fa-edit"></i></a> ');
	    	}			
			return actions.join('');
	    }
	},{
		title:'规格型号',
	    field: 'materialName'
	},{
		title:'专用号',
	    field: 'materialCode'
	},{
		title:'状态',
	    field: 'itemStatus',
	    formatter: function(value, row, index) {
	    	if(value=='10'){
	    		return '已下推SAP'
	    	}else if(value=='Z2'){
	    		return '取消'
	    	}else if(value=='Z7'){
	    		return '订单关闭'
	    	}else{
	    		return '草稿'
	    	}
	    }
	},{
		title:'标识',
	    field: 'identification',
	    visible:false
	},{

		title:'all',
	    field: 'all',
	    visible:false
	    //0所有tab 1其他tab
	    //visible:false
    },{
		title:'',
	    field: 'clazzCode',
	    visible:false
	},
	{
		title:'',
	    field: 'materialType',
	    visible:false
	},
	{
		title:'',
	    field: 'volumeCube',
	    visible:false
	},
	{
		title:'',
	    field: 'standardPrice',
	    visible:false
	},{
		title:'',
	    field: 'materialGroupCode',
	    visible:false
	},{
		title:'',
	    field: 'isConfigurable',
	    visible:false
	},{
		title:'需求计划',
	    field: 'itemRequirementPlan',
	    formatter: function(value, row, index) {
	    	if(value=="004"){
	    		return "物料需求计划";
	    	}else if(value=="001"){
	    		return "B2C";
	    	}else if(value=="002"){
	    		return "消化";
	    	}else if(value=="003"){
	    		return "调发";
	    	}else{
	    		return '替换';
	    	}
	    }
	},{
		title:'类型',
	    field: 'materialGroupName'
	},{
		title:'数量',
	    field: 'quantity'
	},{
		title:'计量单位',
	    field: 'unitName'
	},{
		title:'',
	    field: 'unitCode',
	    visible:false
	},{
		title:'产品实卖价cny',
		visible:false,
	    field: 'actualPrice'
	},{
		title:'产品实卖单价（凭证货币）',
	    field: 'originalActualPrice'
	},{
		title:'产品实卖金额（凭证货币）',
	    field: 'actualAmount'
	},{
		title:'产品转移单价（凭证货币）',
		visible:false,
	    field: 'transactionPrice'
	},{
		title:'产品转移单价（凭证货币）',
	    field: 'originalTransationPrice'
	},{
		title:'产品转移金额（凭证货币）',
	    field: 'transactionPriceAmount'
	},{
		title:'可选项实卖价CNY',
		visible:false,
	    field: 'optionalActualPrice'
	},{
		title:'可选项实卖单价（凭证货币）',
	    field: 'originalOptionalActualPrice'
	},{
		title:'可选项实卖金额（凭证货币）',
	    field: 'optionalActualAmount'
	},{
		title:'可选项转移单价（人民币）',
	    field: 'optionalTransactionPrice',
	    visible:false
	},{
		title:'可选项转移单价（凭证货币）',
	    field: 'originalOptionalTransationPrice',
	},{
		title:'可选项转移金额（凭证货币）',
	    field: 'optionalTransationPriceAmount',
	},
	{
		title:'可选项标准价差',
	    field: 'optionalStandardPrice',
	    visible:false
	},
	{
		title:'可选项零售价差',
	    field: 'optionalRetailPrice',
	    visible:false
	},{
		title:'B2C预估单价（CNY）',
	    field: 'b2cEstimatedPrice'

	},{
		title:'B2C预估金额（CNY）',
	    field: 'b2cEstimatedAmount'

	},{
		title:'B2C预估成本单价',
	    field: 'b2cEstimatedCost'
	},{
		title:'B2C预估成本金额',
	    field: 'b2cEstimatedCostAmount'
	},{
		title:'实卖单价小计（凭证货币）',
	    field: 'actualPriceSum'

	},{
		title:'实卖金额合计（凭证货币）',
	    field: 'actualAmountSum'

	},{
		title:'转移单价小计',
	    field: 'transactionPriceSum'

	},{
		title:'转移金额合计',
	    field: 'transactionAmountSum'

	},{
		title:'市场零售价（CNY）',
	    field: 'retailPrice'

	},{
		title:'市场零售金额（CNY）',
	    field: 'retailAmount'

	},{
		title:'折扣率',
	    field: 'discount'

	},{
		title:'行项目类别',
	    field: 'itemCategory',
	    formatter: function(value, row, index) {
	    	var config = row.isConfigurable+'';
	    	if(config=='true'){
	    		if(value=="ZHD1"||value=="ZHT1"||value=="ZHM1"){
		    		return "标准";
		    	}else if(value=="ZHD3"||value=="ZHT3"){
		    		return "免费";
		    	}else if(value=="ZHR3"||value=="ZHR1"){
		    		return "退货";
		    	}else{ 
		    		return value;
		    	}
	    	}else{
	    		if(value=="ZHD2"||value=="ZHT2"||value=="ZHM2"){
		    		return "标准";
		    	}else if(value=="ZHD4"||value=="ZHT4"){
		    		return "免费";
		    	}else if(value=="ZHR4"||value=="ZHR2"){
		    		return "退货";
		    	}else if(value=="ZHT6"){
		    		return "供应商直发";
		    	}else if (value=="ZHDF"){
		    		return "经销商直发";
		    	}else{ 
		    		return value;
		    	}
	    	}
	    	
	    }

	},{
		title:'物料属性',
	    field: 'isPurchased',
	    formatter: function(value, row, index) {
	    	if(value==undefined){
	    		return "";
	    	}
	    	if(value||value=='true'){
	    		return "自制";
	    	}else{
	    		return "外购";
	    	}
	    }
	},{
		title:'生产周期',
	    field: 'producePeriod'

	},{
		title:'工厂最早交货时间',
	    field: 'deliveryDate'

	},{
		title:'生产开始时间',
	    field: 'produceDate'
	},{
		title:'要求发货时间',
	    field: 'shippDate'

	},{
		title:'',
	    field: 'deliveryAddressSeq',
	    visible:false		
	},{
		title:'地址',
	    field: 'materialAddress'

	},{
		title:'入库时间',
	    field: 'onStoreDate'

	},{
		title:'采购周期',
	    field: 'purchasePeriod'

	},{
		title:'B2C备注',
	    field: 'b2cComments'

	},{
		title:'特殊备注',
	    field: 'specialComments'

	},{
		title:'颜色备注',
	    field: 'colorComments'

	},
	{
		field:'provinceCode',
		visible:false
	},
	{
		field:'provinceName',
		visible:false
	},
	{
		field:'cityCode',
		visible:false
	},
	{
		field:'cityName',
		visible:false
	},
	{
		field:'districtCode',
		visible:false
	},{
		field:'districtName',
		visible:false
	},{
		field:'address',
		visible:false
	}
]