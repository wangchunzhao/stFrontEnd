$(document).ready(function() {
        $('#reservation').daterangepicker(
        		{
        			autoUpdateInput:true,
            		startDate: moment().subtract('days', 6),  //这里配置的起止时间将会决定在ranges中默认选中哪个时间段
            		endDate: moment()
                }, function(start, end, label) {
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
			url : ctxPath+"order/queryTodo",//请求路径
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
			pageSize : 20,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : function (params) {
			    var temp = {
			    	pageSize: params.limit, // 每页显示数量
			    	pageNo: (params.offset / params.limit)+1,  //当前页码
			    	
			    	sequenceNumber:$('#sequenceNumber').val(),
			    	contractNumber:$('#contractNumber').val(),//合同号
			    	contracterName:$('#contracterName').val(),//签约单位
			    	salesName:$('#salesName').val(),
			    	stOrderType:$('#orderType').val(),
			    	createTime:$('#reservation').val(),
			    	officeCode:$('#officeCode').val()
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
			}, {
				title : '签约单位',
				field : 'customerName',
				sortable : true
			},{
				title : '订单类型',
				field : 'stOrderType',
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
				field : 'status',
				formatter : formatStatus
			},{
				title : '操作',
				field : 'status',
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
	if(value=="1"){
		return "经销商标准折扣订单";
	}else if(value=="2"){
		return "经销商非标准折扣订单";
	}else if(value=="3"){
		return "直签客户投标报价";
	}else if(value=="4"){
		return "直签客户下定单";
	}else if(value=="5"){
		return "备货";
	}
}

function formatStatus(value, row, index) {
	if(value=="00"){
		return "草稿";
	}else if(value=="01"){
		return "待B2C审批";
	}else if(value=="02"){
		return "待工程人员审批";
	}else if(value=="03"){
		return "待支持经理审批";
	}else if(value=="04"){
		return "提交到BPM";
	}else if(value=="05"){
		return "BPM审批通过";
	}else if(value=="06"){
		return "订单更改审批通过";
	}else if(value=="09"){
		return "已下推SAP";
	}else if(value=="10"){
		return "Selling Tool驳回";
	}else if(value=="11"){
		return "BPM驳回";
	}
}

//查询按钮事件
$('#search_btn').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : ctxPath+'order/queryTodo'
	});
})

//重置按钮事件
$('#resetBtn').click(function() {
	$("#sequenceNumber").val("");
	$("#contractNumber").val("");
	$("#contracterCode").val("");
	$("#salesCode").val("");
	$("#officeCode").val("");
	$("#orderType").val("");
	$("#reservation").val("");
})
 
//删除、编辑操作
function operation(value, row, index) {
	var sequenceNumber = row.sequenceNumber;
	var orderType = row.orderType;
	var currentVersion = row.currentVersion;
	var orderInfoId = row.id;
	var viewHtm = "<a type='button' class='btn btn-primary' id='approveOrder' onclick='approveOrder(\""+orderInfoId+"\")'>审批</button>";
	if(row.status=='00'){
		viewHtm+="<a type='button' class='btn btn-primary' id='deleteOrder' onclick='deleteOrder(\""+orderInfoId+"\")'>删除</button>";
	}
	return viewHtm;
}

function approveOrder(orderInfoId){
	var myForm = document.createElement("form");       
	    myForm.method = "get";  
	    myForm.action = ctxPath+"order/toOrderPage";        
		var seq = document.createElement("input");       
		seq.setAttribute("name", "orderInfoId");  
		seq.setAttribute("value", orderInfoId);  
		myForm.appendChild(seq);
		var seq1 = document.createElement("input");       
		seq1.setAttribute("name", "orderOperationType");  
		seq1.setAttribute("value", "3");  
		myForm.appendChild(seq1);
		document.body.appendChild(myForm);     
		myForm.submit();   
		document.body.removeChild(myForm); 	
}

function deleteOrder(orderInfoId) {
	
	layer.confirm("确定要删除订单吗?", {btn: ['确定', '取消'], title: "提示"}, function () {
        var url = ctxPath+"order/"+orderInfoId+"/delete";
        $.ajax({
            type: "post",
            url: url,
            data: null,
            dataType: "json",
            success: function (data) {
            	if(data == null || data.status != 'ok'){
		    		layer.alert("订单删除失败：" + (data != null ? data.msg : ""));
		    	}else{
		    		layer.alert('订单删除成功', {icon: 6});
		    		$('#mytab').bootstrapTable('refresh');
		    	} 	
            }
        });
    });
}


function InitSapSalesOffice(){
	$.post(ctxPath+'permission/sapSalesOfficelist',null,function(ret){
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