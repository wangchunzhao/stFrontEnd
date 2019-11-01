$(document).ready(function() {
        $('#reservation').daterangepicker(null, function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
        });
});

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
			url : "/steigenberger/order/query",//请求路径
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
//			        offset : params.offset, // SQL语句起始索引
			    	pageNo: (params.offset / params.limit),  //当前页码
			    	
			    	sequenceNumber:$('#sequenceNumber').val(),
			    	contractNumber:$('#contractNumber').val(),
			    	contracterCode:$('#contracterCode').val(),
			    	officeCode:$('#officeCode').val(),
			    	orderType:$('#orderType').val(),
//			    	b2c:$('#b2c').val(),
//			    	specialDiscount:$('#specialDiscount').val(),
			    	status:$('#status').val()
//			        id:$('#id').val()
			    };
			    return temp;
			},
			columns : [ {
				title : '流水号',
				field : 'sequenceNumber',
				sortable : true
			}, {
				title : '合同号',
				field : 'contractNumber',
				sortable : true
			},{
				title : '签约单位',
				field : 'contracterCode',
				sortable : true
			},{
				title : '订单类型',
				field : 'orderType',
				sortable : true
			},{
				title : '折扣',
				field : 'approvedDiscount',
				sortable : true
			},{
				title : '是否特批折扣',
				field : 'approvedDiscount',
				formatter : formatTrue
			},{
				title : '区域',
				field : 'officeName',
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
				title : '按钮权限',
				field : 'buttonControl',
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
	//或者 return row.sex == 1 ? "男" : "女";
}
function formatStatus(value, row, index) {
	if(value=="0"){
		return "订单新建保存";
	}else if(value=="1"){
		return "客户经理提交成功";
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
	}
}

 
//删除、编辑操作
function operation(value, row, index) {
	var sequenceNumber = row.sequenceNumber;
	var orderType = row.orderType;
	var currentVersion = row.currentVersion;
	var currentVersionStatus = row.currentVersionStatus;
	var buttonControl = row.buttonControl;

	if(currentVersionStatus=="5" && buttonControl ==null){
		var htm = "<button type='button' id=tosap' onclick='tosap(\""+sequenceNumber+"\",\""+orderType+"\",\""+currentVersion+"\")'>下推订单</button>"
	}else{
		var htm = "<button>删除</button>"
	}
	return htm;
}

function tosap(seqNumb,ordType,version) {
	$.post('/steigenberger/order/sap',{sequenceNumber:seqNumb,orderType:ordType,currentVersion:version},function(data, textStatus, jqXHR){
	
		if(textStatus=="success"){
			alert('下推订单成功！')
			$('#mytab').bootstrapTable('refresh', {
				url : '/steigenberger/order/query'
			});
		}else{
			alert('下推订单失败！')
		}
	},'text');
}

//查询按钮事件
$('#search_btn').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : '/steigenberger/order/query'
	});
})

//重置按钮事件
$('#resetBtn').click(function() {
/*	$("#id").val("");
	$("#contractNo").val("");
	$("#reservation").val("");
	$("#contractUnit").val("");
	$("#area").val(-1);
	$("#orderType").val(-1);
	$("#b2c").val(-1);
	$("#specialDiscount").val(-1);
	$("#status").val(-1);*/
})


function InitSapSalesOffice(){
		$.post('/steigenberger/permission/sapSalesOfficelist',null,function(ret){
			if(ret.status==200){
				for(var i in ret.data){
					var item = ret.data[i];
					$('#officeCode').append(BuildAreaOption(item.name,item.code));
				}
			}else{
				alert('角色列表请求错误！')
			}
		},'json');
	}
	
	function BuildAreaOption(name,code){
		return "<option value='"+code+"'>"+name+"</option>";
	}
















