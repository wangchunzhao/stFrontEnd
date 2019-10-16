var materialsColumn = [{
		title: '<input type="button"  value="+" class="btn btn-primary" onclick="addSubsidiary()" id="materialAdd"/>',
	    align: 'center',
	    width:200,
	    formatter: function(value, row, index) {
	    	var actions = [];
			actions.push('<a class="btn" onclick="editMaterials(\'' + index + '\')"><i class="fa fa-edit"></i>编辑</a> ');
			actions.push('<a class="btn" onclick="removeMaterials(\'' + index + '\')"><i class="fa fa-remove"></i>删除</a>');
			return actions.join('');
	    }
	},{
		title:'行号',
	    field: 'index',
	    width:50
	},{
		title:'调研表',
		align: 'center',
		width:60,
	    formatter: function(value, row, index) {
	    	var actions = [];
			actions.push('<a class="btn" onclick="editPayment(\'' + index + '\')"><i class="fa fa-edit"></i></a> ');
			return actions.join('');
	    }
	},{
		title:'规格型号',
	    field: 'materialTypeName',
	    width:200
	},{
		title:'专用号',
	    field: 'index',
	    width:150
	},{
		title:'物料属性',
	    field: 'index',
	    width:80
	},{
		title:'类型',
	    field: 'index',
	    width:80
	},{
		title:'数量',
	    field: 'index',
	    width:60
	},{
		title:'计量单位',
	    field: 'index',
	    width:80
	},{
		title:'产品实卖价',
	    field: 'index',
	    width:150
	},{
		title:'产品实卖金额',
	    field: 'index',
	    width:150
	},{
		title:'产品转移价',
	    field: 'index',
	    width:150
	},{
		title:'可选项实卖价',
	    field: 'index',
	    width:150
	},{
		title:'可选项实卖金额',
	    field: 'index',
	    width:150
	},{
		title:'可选项转移价',
	    field: 'index',
	    width:150

	},{
		title:'B2C预估价',
	    field: 'index',
	    width:150

	},{
		title:'B2C预估金额',
	    field: 'index',
	    width:150

	},{
		title:'B2C预估成本',
	    field: 'index',
	    width:150
	},{
		title:'实卖价合计',
	    field: 'index',
	    width:150

	},{
		title:'实卖金额合计',
	    field: 'index',
	    width:150

	},{
		title:'转移价合计',
	    field: 'index',
	    width:150

	},{
		title:'市场零售价',
	    field: 'index',
	    width:150

	},{
		title:'市场零售金额',
	    field: 'index',
	    width:150

	},{
		title:'折扣率',
	    field: 'index',
	    width:150

	},{
		title:'行项目类别',
	    field: 'index',
	    width:150

	},{
		title:'需求计划',
	    field: 'index',
	    width:150
	},{
		title:'生产周期',
	    field: 'index',
	    width:150

	},{
		title:'工厂最早交货时间',
	    field: 'index',
	    width:150

	},{
		title:'生产开始时间',
	    field: 'index',
	    width:150

	},{
		title:'要求发货时间',
	    field: 'index',
	    width:150

	},{
		title:'地址',
	    field: 'index',
	    width:250

	},{
		title:'入库时间',
	    field: 'index',
	    width:150

	},{
		title:'采购周期',
	    field: 'index',
	    width:150

	},{
		title:'B2C备注',
	    field: 'index',
	    width:150

	},{
		title:'特殊备注',
	    field: 'index',
	    width:150

	}
]