var materialsColumn = [{
		title: '<input type="button"  value="+" class="btn btn-primary" onclick="addSubsidiary()" id="materialAdd"/>',
	    align: 'center',
	    width:200,
	    formatter: function(value, row, index) {
	    	var actions = [];
    		actions.push('<a class="btn" style="font-size:11px;width:20px;margin-right:15px;" id="materialsEdit" onclick="editMaterials(\'' +row.rowNum+'|'+index+ '\')"><i class="fa fa-edit"></i>编辑</a> ');
			actions.push('<a class="btn" style="font-size:11px;width:20px;margin-right:15px;" onclick="copyMaterials(\'' + row.rowNum + '\')"><i class="fa fa-copy"></i>复制</a>');
			actions.push('<a class="btn" style="font-size:11px;width:20px;margin-right:15px;" onclick="insertMaterials(\'' +index+ '\')"><i class="fa fa-edit"></i>插入</a>');
			actions.push('<a class="btn" style="font-size:11px;width:20px;margin-right:15px;" onclick="removeMaterials(\'' + row.rowNum + '\')"><i class="fa fa-edit"></i>删除</a>');
			return actions.join('');		
	    }
	},{
		title:'行号',
	    field: 'rowNum',
	    width:50
	},{
		title:'调研表',
		align: 'center',
		width:120,
	    formatter: function(value, row, index) {
	    	var actions = [];
	    	var configurable = row.isConfigurable+'';
	    	if(configurable=='true'){
	    		actions.push('<a class="btn" onclick="openConfig(\'' + row.rowNum+'|'+index+'\')"><i class="fa fa-edit"></i></a> ');
	    	}			
			return actions.join('');
	    }
	},{
		title:'规格型号',
	    field: 'materialName',
	    width:200
	},{
		title:'专用号',
	    field: 'materialCode',
	    width:150
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
		title:'物料属性',
	    field: 'isPurchased',
	    width:80,
	    formatter: function(value, row, index) {
	    	if(value==undefined){
	    		return "";
	    	}
	    	if(value){
	    		return "外购";
	    	}else{
	    		return "自制";
	    	}
	    }
	},{
		title:'类型',
	    field: 'materialGroupName',
	    width:80
	},{
		title:'数量',
	    field: 'quantity',
	    width:60
	},{
		title:'计量单位',
	    field: 'unitName',
	    width:80
	},{
		title:'',
	    field: 'unitCode',
	    visible:false
	},{
		title:'产品实卖价',
	    field: 'actualPrice',
	    width:150
	},{
		title:'产品实卖金额',
	    field: 'actualAmount',
	    width:150
	},{
		title:'产品转移价',
	    field: 'transactionPrice',
	    width:150
	},{
		title:'可选项实卖价',
	    field: 'optionalActualPrice',
	    width:150
	},{
		title:'可选项实卖金额',
	    field: 'optionalActualAmount',
	    width:150
	},{
		title:'可选项转移价',
	    field: 'optionalTransationPrice',
	    width:150

	},{
		title:'B2C预估价',
	    field: 'b2cEstimatedPrice',
	    width:150

	},{
		title:'B2C预估金额',
	    field: 'b2cEstimatedAmount',
	    width:150

	},{
		title:'B2C预估成本',
	    field: 'b2cEstimatedCost',
	    width:150
	},{
		title:'实卖价合计',
	    field: 'actualPriceSum',
	    width:150

	},{
		title:'实卖金额合计',
	    field: 'actualAmountSum',
	    width:150

	},{
		title:'转移价合计',
	    field: 'transactionPriceSum',
	    width:150

	},{
		title:'市场零售价',
	    field: 'retailPrice',
	    width:150

	},{
		title:'市场零售金额',
	    field: 'retailAmount',
	    width:150

	},{
		title:'折扣率',
	    field: 'discount',
	    width:150

	},{
		title:'行项目类别',
	    field: 'itemCategory',
	    width:150,
	    formatter: function(value, row, index) {
	    	var config = row.isConfigurable+'';
	    	if(config=='true'){
	    		if(value=="ZHD1"){
		    		return "标准";
		    	}else if(value=="ZHD3"){
		    		return "免费";
		    	}else if(value=="ZHR3"){
		    		return "退货";
		    	}else{ 
		    		return '';
		    	}
	    	}else{
	    		if(value=="ZHD2"){
		    		return "标准";
		    	}else if(value=="ZHD4"){
		    		return "免费";
		    	}else if(value=="ZHR4"){
		    		return "退货";
		    	}else{ 
		    		return '';
		    	}
	    	}
	    	
	    }

	},{
		title:'需求计划',
	    field: 'itemRequirementPlan',
	    width:150,
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
	    		return '';
	    	}
	    }
	},{
		title:'生产周期',
	    field: 'producePeriod',
	    width:150

	},{
		title:'工厂最早交货时间',
	    field: 'deliveryDate',
	    width:150

	},{
		title:'生产开始时间',
	    field: 'produceDate',
	    width:150

	},{
		title:'要求发货时间',
	    field: 'shippDate',
	    width:150

	},{
		title:'',
	    field: 'deliveryAddressSeq',
	    visible:false		
	},{
		title:'地址',
	    field: 'materialAddress',
	    width:250

	},{
		title:'入库时间',
	    field: 'onStoreDate',
	    width:150

	},{
		title:'采购周期',
	    field: 'purchasePeriod',
	    width:150

	},{
		title:'B2C备注',
	    field: 'b2cComments',
	    width:150

	},{
		title:'特殊备注',
	    field: 'specialComments',
	    width:150

	},{
		title:'颜色备注',
	    field: 'colorComments',
	    width:150

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