document.write("<script src='../pageJs/jumpAjax.js'></script>");
$(document).ready(function() {
        $('#reservation').daterangepicker(
        		{
        		autoUpdateInput:true,
        		startDate: moment().subtract('days', 7),  //这里配置的起止时间将会决定在ranges中默认选中哪个时间段
        		endDate: moment()
        		}, 
        		function(start, end, label) {
        			 $('#reservation').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
        			console.log(start.toISOString(), end.toISOString(), label);
        });
        $('#reservation1').daterangepicker(
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
    InitTime();
    oTable.Init();
    InitSapSalesOffice();
    InitTime();
});

var TableInit = function () {
	var oTableInit = new Object();
	
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'post',
//			url : "/steigenberger/myOrder/myOrderManageList",//请求路径
			url : ctxPath+"order/query",//请求路径
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
//			        offset : params.offset, // SQL语句起始索引
			    	pageNo: (params.offset / params.limit)+1,  //当前页码
			    	
			    	sequenceNumber:$('#sequenceNumber').val(),//需求流水号
			    	contractNumber:$('#contractNumber').val(),//合同号
			    	contracterName:$('#contracterName').val(),//签约单位
			    	officeCode:$('#officeCode').val(),
			    	orderType:$('#orderType').val(),
			    	b2c:$('#b2c').val(),
			    	createTime:$('#reservation').val(),
			    	submitTime:$('#reservation1').val(),
			    	specialDiscount:$('#specialDiscount').val(),
			    	status:$('#status').val()
//			        id:$('#id').val()
			    };
			    return temp;
			},
			columns : [ {
				title:'',
			    field: 'id',
			    visible:false
			},{
				title : '流水号',
				field : 'sequenceNumber',
				sortable : true
			}, {
				title : '合同号',
				field : 'contractNumber',
				sortable : true
			},{
				title : '签约单位',
				field : 'customerName',
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
	//或者 return row.sex == 1 ? "男" : "女";
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

 
//删除、编辑操作
function operation(value, row, index) {
	var sequenceNumber = row.sequenceNumber;
	var orderType = row.orderType;
	var currentVersion = row.currentVersion;
	var currentVersionStatus = row.status;
	var orderInfoId = row.id;
	var buttonControl = row.buttonControl;
	var stOrderType = row.stOrderType;
	var quoteStatus = row.quoteStatus;
	var actions = [];
	actions.push("<a type='button' class='btn btn-primary' id='viewOrder' onclick='viewOrder(\""+orderInfoId+"\")'>查看</a>");
	actions.push("<a type='button' class='btn btn-primary' id='copyOrder' onclick='copyOrder(\""+orderInfoId+"\")'>复制</a>");
	/*actions.push("<a type='button' class='btn btn-primary' id='upgradeOrder' onclick='upgradeOrder(\""+orderInfoId+"\")'>订单变更</a>");*/
	if(stOrderType!="3"&&currentVersionStatus=="05"&&buttonControl=="true"){
		actions.push("<a type='button' class='btn btn-primary' id=tosap' onclick='tosap(\""+orderInfoId+"\")'>下推订单</a>");
		actions.push("<a type='button' class='btn btn-primary' onclick='upgradeOrder(\""+orderInfoId+"\")'>订单变更</a>");
	}
	if(currentVersionStatus=="09"&&buttonControl=="true"){
		actions.push("<a type='button' class='btn btn-primary' id='upgradeOrder' onclick='upgradeOrder(\""+orderInfoId+"\")'>订单变更</a>");
	}
	if(stOrderType=="3"&&status=="05"&&quoteStatus=="00"&&buttonControl=="true"){
		actions.push("<a type='button' class='btn btn-primary' id='tenderOffer' onclick='tenderOffer(\""+orderInfoId+"\")'>中标</a>");
	}
	if(stOrderType=="3"&&status=="05"&&quoteStatus=="01"&&buttonControl=="true"){
		actions.push("<a type='button' class='btn btn-primary' id='createOrder' onclick='createOrder(\""+orderInfoId+"\")'>下单</a>");
	}

	return actions.join('');
}

function viewOrder(orderInfoId){
	jumpAjax("get", ctxPath+"order/toOrderPage", {
		"orderInfoId" : orderInfoId,
		"orderOperationType" : "2"
	})
}

function editStockUpOrder(seqNumb,ordType,version){
	jumpAjax("post",ctxPath+"order/editStockUpOrder", {
		"sequenceNumber" : seqNumb,
		"orderType" : ordType,
		"version" : version
	})
}

function editOrder(seqNumb,ordType,version){
	jumpAjax("post", ctxPath+"order/editOrder", {
		"sequenceNumber" : seqNumb,
		"orderType" : ordType,
		"version" : version
	})
}

function tosap(orderInfoId) {
	
	layer.confirm("确定要下推订单吗?", {btn: ['确定', '取消'], title: "提示"}, function () {
        var url = ctxPath+"order/"+orderInfoId+"/sap";
        $.ajax({
            type: "post",
            url: url,
            data: null,
            dataType: "json",
            success: function (data) {
            	if(data == null || data.status != 'ok'){
		    		layer.alert("订单下推失败：" + (data != null ? data.msg : ""));
		    	}else{
		    		layer.alert('订单下推成功', {icon: 6});
		    		$('#mytab').bootstrapTable('refresh');
		    	} 	
            }
        });
    });
	/*$.post(ctxPath+'order/sap',{sequenceNumber:seqNumb,orderType:ordType,currentVersion:version},function(data, textStatus, jqXHR){	
		if(textStatus=="success"){
			alert('下推订单成功！')
			$('#mytab').bootstrapTable('refresh', {
				url : ctxPath+'order/query'
			});
		}else{
			alert('下推订单失败！')
		}
	},'text');*/
}

function upgradeOrder(orderInfoId){
	layer.confirm("确定要变更订单吗?", {btn: ['确定', '取消'], title: "提示"}, function () {
        var url = ctxPath+"order/"+parseInt(orderInfoId)+"/upgrade";
        $.ajax({
            type: "post",
            url: url,
            data: null,
            dataType: "json",
            success: function (data) {
            	if(data == null || data.status != 'ok'){
		    		layer.alert("订单变更失败：：" + (data != null ? data.msg : ""));
		    	}else{
		    		layer.alert('订单变更成功', {icon: 6});
		    		$('#mytab').bootstrapTable('refresh');
		    	} 	
            }
        });
    });
}

function copyOrder(orderInfoId){
	layer.confirm("确定要复制订单吗?", {btn: ['确定', '取消'], title: "提示"}, function () {
        var url = ctxPath+"order/"+parseInt(orderInfoId)+"/copy";
        $.ajax({
            type: "post",
            url: url,
            data: null,
            dataType: "json",
            success: function (data) {
            	if(data == null || data.status != 'ok'){
		    		layer.alert("订单复制失败：：" + (data != null ? data.msg : ""));
		    	}else{
		    		layer.alert('订单复制成功', {icon: 6});
		    		$('#mytab').bootstrapTable('refresh');
		    	} 	
            }
        });
    });
}

//确认中标
function tenderOffer(orderInfoId){
    layer.confirm("确认中标吗?", {btn: ['确定', '取消'], title: "提示"}, function () {
	    var url = ctxPath+"order/"+orderInfoId+"quote/00";
	    $.ajax({
	        type: "post",
	        url: url,
	        data: null,
	        dataType: "json",
	        async: false,
	        success: function (data) {
	        	if(data == null || data.status != 'ok'){
		    		layer.alert("确认中标失败:" + (data != null ? data.msg : ""));
		    	}else{
		    		layer.alert('确认中标成功！', {icon: 6});
		    		$('#mytab').bootstrapTable('refresh');
		    	} 	
	        }
	    });
    });
}
//确认下单
function createOrder(orderInfoId){
	layer.confirm("确认中标吗?", {btn: ['确定', '取消'], title: "提示"}, function () {
	    var url = ctxPath+"order/"+orderInfoId+"quote/01";
	    $.ajax({
	        type: "post",
	        url: url,
	        data: null,
	        dataType: "json",
	        async: false,
	        success: function (data) {
	        	if(data == null || data.status != 'ok'){
		    		layer.alert("确认下单失败:" + (data != null ? data.msg : ""));
		    	}else{
		    		layer.alert('确认下单成功！', {icon: 6});
		    		$('#mytab').bootstrapTable('refresh');
		    	} 	
	        }
	    });
    });
}
//查询按钮事件
$('#search_btn').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : ctxPath+'order/query'
	});
})

//重置按钮事件
$('#resetBtn').click(function() {
	$("#sequenceNumber").val("");
	$("#contractNumber").val("");
	$("#contracterCode").val("");
	$("#officeCode").val("");
	$("#orderType").val("");
	$("#reservation1").val("");
	$("#status").val("");

})


function InitSapSalesOffice(){
		$.post(ctxPath+'permission/sapSalesOfficelist',null,function(ret){
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

function InitTime(){
	var endDate = new Date();
	var startDate = new Date();
	startDate.setTime(endDate.getTime() - 1000*3600*24*7);
	$("#reservation").val(dateFtt("yyyy-MM-dd", startDate)+" - "+dateFtt("yyyy-MM-dd", endDate));
	
}
//时间格式化处理
function dateFtt(fmt, date) { //author: meizz   
    var o = {
        "M+": date.getMonth() + 1, //月份   
        "d+": date.getDate(), //日   
        "h+": date.getHours(), //小时   
        "m+": date.getMinutes(), //分   
        "s+": date.getSeconds(), //秒   
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度   
        "S": date.getMilliseconds() //毫秒   
    };
    if(/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}













