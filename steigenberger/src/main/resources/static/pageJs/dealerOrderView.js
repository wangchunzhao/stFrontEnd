$(function () {
	localStorage.clear()
	var addressTable = new TableInit('addressTable','','',addressColumns)
	addressTable.init();
	
	//初始化毛利率table
	var grossProfitTable = new TableInit("grossProfitTable",'','',grossProfitColumns);
	grossProfitTable.init();
	
	var installationTerm = installationTerms[$("#customerClazzCode").val()];
	$.each(installationTerm, function (key, value) {
		$("#installCode").val(key);
		$("#installName").val(value);
		
	});	
	initMarialsTables();
	$('#first').tab('show');
	$('#shippDate').datepicker();
	getUserDetail();
	disableAll();
	fillItems();
});

//初始化购销明细
function fillItems(){
	if(items){
		for(var i=0;i<items.length;i++){
			var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
			var countMaterialsTableall1 = $('#materialsTableall1').bootstrapTable('getData').length;
			var countMaterialsTableall2 = $('#materialsTableall2').bootstrapTable('getData').length;
			var countMaterialsTableall3 = $('#materialsTableall3').bootstrapTable('getData').length;
			var countMaterialsTableall4 = $('#materialsTableall4').bootstrapTable('getData').length;
			var countMaterialsTableall5 = $('#materialsTableall5').bootstrapTable('getData').length;
			var countMaterialsTableall6 = $('#materialsTableall6').bootstrapTable('getData').length;
			var materialType = materialGroupMapGroupOrder[items[i].groupCode];
			if(materialType=='T101'){
				$("#materialsTableall1").bootstrapTable('insertRow', {
				    index: countMaterialsTableall1,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T102'){
				$("#materialsTableall2").bootstrapTable('insertRow', {
				    index: countMaterialsTableall2,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T103'){
				$("#materialsTableall3").bootstrapTable('insertRow', {
				    index: countMaterialsTableall3,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T104'){
				$("#materialsTableall4").bootstrapTable('insertRow', {
				    index: countMaterialsTableall4,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T105'){
				$("#materialsTableall5").bootstrapTable('insertRow', {
				    index: countMaterialsTableall5,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: item[i]
				});
			}else if(materialType=='T106'){
				$("#materialsTableall6").bootstrapTable('insertRow', {
				    index: countMaterialsTableall6,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}
		}
	}
	
}
function disableAll(){ 
  var form=document.forms[0]; 
  for(var i=0;i<form.length;i++){ 
    var element=form.elements[i]; 
    element.disabled=true; 
  } 
  $("#back").attr('disabled',false);
  $("#grossClose").attr('disabled',false);
  $("#showGrossProfit").attr('disabled',false);
  $("#grossCloseb").attr('disabled',false);
  $("#grossExport").attr('disabled',false);
  $("#collapseShow").attr('disabled',false);
  $("#collapseClose").attr('disabled',false);
  $("#reject").attr('disabled',false);
  $("#approve").attr('disabled',false);
  if($("#expenseItem")){
	  $("#expenseItem").find("*").each(function() {
		 $(this).removeAttr("disabled");
	  });
  }
}
//获取session中用户信息
function getUserDetail(){
	$.ajax({
	    url: "/steigenberger/order/user",
	    data: {},
	    type: "get",
	    success: function(data) {
	    	$("#salesName").val(data.userName);
	    	$("#salesTelnumber").val(data.tel);
	    	$("#salesCode").val(data.userIdentity);
	    }
	});
}

//打开调研表
function openConfig(identification){
	$("#materialconfigModal").modal('show');
	var value = identification.split(',')
	$("#materialConfigClazzCode").val(value[2]);
	$("#materialConfigCode").val(value[1]);
	$("#identification").val(value[0]);
	$("#viewPrice").val(value[3]);
	var url = "/steigenberger/order/material/configurations"
	var configTable = new TableInit('configTable','','',configTableColumns);
	configTable.init();
	var configData = localStorage[value[0]];
	if(configData){
		$("#configTable").bootstrapTable("removeAll");
		var jsonObject = JSON.parse(configData);
		for(var i=0;i<jsonObject.configTableData.length;i++){
			$("#configTable").bootstrapTable('insertRow',{
				index:i,
				row:jsonObject.configTableData[i]
			});
		}
		$("#configRemark").val(jsonObject.remark);
		
	}else{
		$("#configTable").bootstrapTable('refresh',{
			url:url,
			query:{'clazzCode':$("#materialConfigClazzCode").val(),
				   'materialCode':$("#materialConfigCode").val()
			}
		});
	}
}
//调研表初始化查询参数
function queryConfigParams(params) {
    params.clazzCode = $("#materialConfigClazzCode").val();
    params.materialCode = $("#materialConfigCode").val();
    return params;
}

//关闭调研表
function closeMaterialConfig(){
	$("#materialconfigModal").modal('hide');
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function initMarialsTables(items){
	var materialsTable = new TableInit('materialsTable','','',materialsColumn);
	materialsTable.init();
	var materialsTableall1 = new TableInit('materialsTableall1','','',materialsColumn);
	materialsTableall1.init();
	var materialsTableall2 = new TableInit('materialsTableall2','','',materialsColumn);
	materialsTableall2.init();
	var materialsTableall3 = new TableInit('materialsTableall3','','',materialsColumn);
	materialsTableall3.init();
	var materialsTableall4 = new TableInit('materialsTableall4','','',materialsColumn);
	materialsTableall4.init();
	var materialsTableall5 = new TableInit('materialsTableall5','','',materialsColumn);
	materialsTableall5.init();
	var materialsTableall6 = new TableInit('materialsTableall6','','',materialsColumn);
	materialsTableall6.init();
}

//调研表查看物料
function viewConfig(){
	var bomCode = $("#materialConfigCode").val();
	var formData = $("#materialConfigForm").serializeObject();
	formData.bomCode = bomCode;
	$.ajax({
	    url: "/steigenberger/order/material/configuration",
	    contentType: "application/json;charset=UTF-8",
	    data: JSON.stringify(formData),
	    type: "POST",
	    dataType: "json",
	    success: function(data) { 
	    	$("#moreConfig").attr("style","display:block;");
	    	$("#viewCode").val($("#materialConfigCode").val());
	    	$("#viewPrice").val($("#viewPrice").val());
	    	$("#configPrice").val(data.priceGap);
	    },
	    error: function(){
	    	$("#viewError").attr("style","display:block;");
	    }
	});
}

//查看毛利率信息
function viewGrossProfit(){
	$("#grossProfit").modal("show");
	var version = $("#version").val();
	var sequenceNumber = $("#sequenceNumber").val();
	$("#grossSeqNum").val(sequenceNumber);
	$("#grossVersion").val(version);
	$("#grossContractRMBValue").val($("#contractAmount").val());
	$("#grossPerson").val($("#salesName").val());
	$("#grossDate").val($("#inputDate").val());
	$("#grossClazz").val($("#customerClazz").val());
	var opt = {
			url: '/steigenberger/order/'+sequenceNumber+'/'+version+'/grossprofit'
	};
	$("#grossProfitTable").bootstrapTable('refresh', opt);	
}


var grossProfitColumns=[
	{
		 field: 'name',
		 title: '产品名称'
	},{
		 field: 'amount',
		 title: '金额'
	},{
		 field: 'excludingTaxAmount',
		 title: '不含税金额'
	},{
		 field: 'cost',
		 title: '成本'
	},{
		 field: 'grossProfit',
		 title: '毛利'
	},
	{
		 field: 'grossProfitMargin',
		 title: '毛利率'
	}
]

var addressColumns = [{
	title:'行号',
    field: 'index',
    width:'5%'
},{
	title : '省市区',
	field : 'pca',
	width:'35%'
}, 
{
	field:'provinceValue',
	visible:false
},
{
	field:'cityValue',
	visible:false
},
{
	field:'areaValue',
	visible:false
},{
	title : '到货地址',
	field : 'address',
	width:'45%'
},{
    title: '<input type="button"  value="+" class="btn btn-primary" onclick="addAddress()"/>',
    align: 'center',
    width:'15%',
    formatter: function(value, row, index) {
    	var actions = [];
		actions.push('<a class="btn" onclick="editAddress(\'' + index + '\')"><i class="fa fa-edit"></i>编辑</a> ');
		actions.push('<a class="btn" onclick="removeAddress(\'' + index + '\')"><i class="fa fa-remove"></i>删除</a>');
		return actions.join('');
    }
}]

var configTableColumns = [
{
	title :'选项',
	field :'optional',
	width:'15%',
	formatter: function(value, row, index) {
    	if(value){
    		return '必选项'
    	}else{
    		return '可选项'
    	}
    }
}, 
{
	title:'配置',
	field:'name',
	width:'35%'
},
{
	title:'',
	visible:false,
	field:'code'
},
{
	title:'test',
	visible:false,
	field:'configValueCode'
},
{
	title:'配置值',
	field:'configs',
	width:'50%',
	formatter: function(value, row, index) {		
    	var start = '<select class="form-control" name="configValueCode" onchange="setConfigValueCode(this,\'' + index + '\')">';
    	var end = '</select>';
    	$.each(value,function(index,item){
    		if(item.code==row.configValueCode){
    			start+='<option value=\'' + item.code + '\' selected = "selected">' + item.name + '</option>'
    		}else{
    			start+='<option value=\'' + item.code + '\'>' + item.name + '</option>'
    		}	
    	})
		return start+end;
    }
}
]

var TableInit = function (id,url,params,tableColumns) {
	var oTableInit = new Object();
	var tableId = '#'+id;
	oTableInit.init = function () {
		$(tableId).bootstrapTable({
			method : 'get',
			url : url,//请求路径
			striped : true, //是否显示行间隔色
			toolbar: '#toolbar',
			uniqueId:'index',
			cache: false,            
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    smartDisplay:true,
		    singleSelect: true,
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1, //初始化加载第一页
			sidePagination : 'server',//server:服务器端分页|client：前端分页
			pageSize : 10,//单页记录数,
			showRefresh : false,//刷新按钮
			queryParams : params,
			columns : tableColumns
		})
	};
	return oTableInit;
};