$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    InitSapSalesOffice();
});

var TableInit = function () {
	var oTableInit = new Object();
	
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'post',
//			url : "/steigenberger/myOrder/myOrderManageList",//请求路径
			url : "/steigenberger/order/queryTodo",//请求路径
			striped : true, //是否显示行间隔色
			toolbar: '#toolbar',
			cache: false,
			pagination: true,                   //是否显示分页（*）
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1, //初始化加载第一页
			pagination : true,//是否分页
			sidePagination : 'server',//server:服务器端分页|client：前端分页
			pageSize : 1,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : function (params) {
			    var temp = {
			    	pageSize: params.limit, // 每页显示数量
			    	pageNo: (params.offset / params.limit),  //当前页码
			    	
			    	sequenceNumber:$('#sequenceNumber').val(),
			    	contracterCode:$('#contracterCode').val(),
			    	salesName:$('#salesName').val(),
			    	orderType:$('#orderType').val(),
			    	officeCode:$('#officeCode').val()
			    };
			    return temp;
			},
			columns : [ {
				title : '流水号',
				field : 'sequenceNumber',
				sortable : true
			}, {
				title : '签约单位',
				field : 'contracterCode',
				sortable : true
			},{
				title : '订单类型',
				field : 'orderType',
				formatter : formatOrderType
			},{
				title : '折扣',
				field : 'approvedDiscount',
				sortable : true
			},{
				title : '区域',
				field : 'officeName',
				sortable : true
			},{
				title : '客户经理',
				field : 'salesName',
				sortable : true
			},{
				title : '创建日期',
				field : 'createTime',
				sortable : true
			},{
				title : '版本',
				field : 'currentVersion',
				visible: false 
			},{
				title : '订单状态',
				field : 'currentVersionStatus',
				formatter : formatStatus
			},{
				title : '操作',
				field : 'currentVersionStatus',
				formatter : operation,//对资源进行操作
			} ]
		})
			};
		return oTableInit;
};

function formatTrue(value, row, index) {
	return value == 48 ? "是" : "否";
}
function formatOrderType(value, row, index){
	if(value=="ZH0D"){
		return "经销商订单";
	}else if(value=="ZH0M"){
		return "备货订单";
	}else if(value=="ZH0T"){
		return "大客户订单";
	}
}
function formatStatus(value, row, index) {
	if(value=="0000"){
		return "订单新建保存";
	}else if(value=="0100"){
		return "客户经理提交待支持经理审核";
	}else if(value=="2"){
		return "B2C审核提交成功";
	}else if(value=="3"){
		return "工程人员提交成功";
	}else if(value=="4"){
		return "支持经理提交成功";
	}else if(value=="5"){
		return "订单审批通过";
	}else if(value=="6"){
		return "订单更改审批通过";
	}else if(value=="7"){
		return "订单更改保存";
	}else if(value=="8"){
		return "订单更改提交成功";
	}else if(value=="9"){
		return "已下推SAP";
	}else if(value=="10"){
		return "BPM驳回";
	}else if(value=="11"){
		return "Selling Tool驳回";
	}else if(value=="12"){
		return "待支持经理审批";
	}
}

//查询按钮事件
$('#search_btn').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : '/steigenberger/order/queryTodo'
	});
})

//重置按钮事件
$('#resetBtn').click(function() {
	$("#sequenceNumber").val("");
	$("#contracterCode").val("");
	$("#salesCode").val("");
	$("#officeCode").val("");
	$("#orderType").val("");
})
 
//删除、编辑操作
function operation(value, row, index) {
	var sequenceNumber = row.sequenceNumber;
	var orderType = row.orderType;
	var currentVersion = row.currentVersion;
	var viewHtm = "<a type='button' class='btn btn-primary' id='approveOrder' onclick='approveOrder(\""+sequenceNumber+"\",\""+orderType+"\",\""+currentVersion+"\")'>审批</button>";
	return viewHtm;
}

function approveOrder(seqNumb,ordType,version){
	var myForm = document.createElement("form");       
	    myForm.method = "post";  
	    myForm.action = "/steigenberger/order/approveOrder";        
		var seq = document.createElement("input");       
		seq.setAttribute("name", "sequenceNumber");  
		seq.setAttribute("value", seqNumb);  
		myForm.appendChild(seq);
		var type = document.createElement("input");       
		type.setAttribute("name", "orderType");  
		type.setAttribute("value", ordType);  
		myForm.appendChild(type);  
		var ver = document.createElement("input");       
		ver.setAttribute("name", "version");  
		ver.setAttribute("value", version);  
		myForm.appendChild(ver);    
		document.body.appendChild(myForm);     
		myForm.submit();   
		document.body.removeChild(myForm); 	
}

function InitSapSalesOffice(){
	$.post('/steigenberger/permission/sapSalesOfficelist',null,function(ret){
		if(ret.status==200){
			for(var i in ret.data){
				var item = ret.data[i];
				$('#officeCode').append(BuildAreaOption(item.name,item.code));
			}
		}else{
			alert('区域列表请求错误！')
		}
	},'json');
}

function BuildAreaOption(name,code){
	return "<option value='"+code+"'>"+name+"</option>";
}