$(document).ready(function() {
        $('#createTime1').daterangepicker(null, function(start, end, label) {
      //    console.log(start.toISOString(), end.toISOString(), label);
        });
        
});

var applyListMap ={};
var applyId;
var orderId;
var kOrderVersionId;

$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
});

var TableInit = function () {
	var oTableInit = new Object();
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'get',
			url : ctxPath+"specialdelivery",//请求路径
			responseHandler: function (res) {
				return res.data
			},
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
			        limit : params.limit, // 每页显示数量
			        offset : params.offset, // SQL语句起始索引
			        page: (params.offset / params.limit) + 1,   //当前页码
		
			        sequenceNumber:$('#sequenceNumber').val(),
			        createTime1:$('#createTime1').val(),
			        orderTypeCode:$('#orderTypeCode').val()
			    };
			    return temp;
			},
			columns : [{
				title : '序列号 SequenceNumber',
				field : 'sequenceNumber',
				sortable : true
			},{
				title : '签约单位 Contract Unit',
				field : 'customerName',
				sortable : true
			},{
				title : '折扣 Discount',
				field : 'discount',
				// formatter : formatTrue,
				sortable : true
			},{
				title : '创建日期 Creation Time',
				field : 'createTime',
				sortable : true,
				formatter:function (value, row, index) {
					return changeDateFormatter(value);
				}
			},
				// {
				// title: '申请号 applyId',
	         //    field: 'customerClazz',
	         //    align: 'center',
	         //    width : 160,
	         //    formatter: function (value, row, index) {// selNumber'+ row.kOrderId + '
	         //    	var option;
	         //    	//下拉框
	         //    	$.ajax({
	         //    		url: '/steigenberger/special/queryApplyListByOrderId',
	         //            type: "get",
	         //            data : {kOrderVersionId:row.kOrderVersionId},
	         //            dataType: "json",
	         //            async : false,
	         //            success:function(data){
	         //            	var result = data.data;
	         //            	var headOption = "<option value ='0' selected>请选择</option>";
	         //            	if(result == '' ){
	         //            		headOption = headOption + "<option value ='-1'>填写申请</option>";
	         //            	}else{
	         //            		var key = row.kOrderId;
	         //            		applyListMap[key] = result;
	         //            		console.log(applyListMap);
	         //            		$.each(data.data,function(i,obj){
	         //            			headOption = headOption + "<option value='"+obj.applyId+"'>"+obj.applyId+"</option>";
	         //            		});
	         //            		headOption = headOption + "<option value='-2'>填写申请</option>";
	         //            	}
	         //            	var thisorderId = row.kOrderId;
	         //                option = '<select class="form-control" id="'+thisorderId+'" name="'+row.kOrderVersionId+'" style="height:33px;"  onchange="changeStatus(this.id,this.value,this.name)">'+
	         //                headOption + '</select>';
	         //
	         //              //  console.log(option);
	         //            }
	         //        })
	         //        return option;
	         //    }
	         // }
	        //  ,{
			// 	title : '申请单状态 Apply Status',
			// 	field : 'applyStatus',
			// 	formatter : statusfun,
			// },
				{
				title : '操作 operation',
				field : 'opt',
				formatter : operation,//对资源进行操作
			} ]
		})
			};
		return oTableInit;
};

function changeDateFormatter(cellval){
	var date = new Date();
	return  date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
}

function changeStatus(rowid,value,name){
	kOrderVersionId = name;
	orderId = rowid;
	applyId = value;
	var apply_opt_id="opt_"+rowid;
	var show_opt_id="show_"+rowid;
	var status_id="status_"+rowid;
	
	if(value=="-1"){
		$("#"+apply_opt_id).attr('style',"display:''");
		$("#"+show_opt_id).attr('style',"display:none");
		$("#"+status_id).html('');
	}else if(value=="-2"){
		$("#"+apply_opt_id).attr('style',"display:''");
		$("#"+show_opt_id).attr('style',"display:none");
		$("#"+status_id).html('');
	}else if(value=="0"){
		$("#"+apply_opt_id).attr('style',"display:none");
		$("#"+show_opt_id).attr('style',"display:none");
		$("#"+status_id).html('');
	}else{
		$("#"+apply_opt_id).attr('style',"display:none");
		$("#"+show_opt_id).attr('style',"display:''");
		console.log(applyListMap);
		var applyList = applyListMap[rowid];
		for(var i=0;i<applyList.length;i++){
			var appid=applyList[i].applyId;
			var applyStatus = applyList[i].applyStatus;
			//alert("value="+value+" -------"+"appid="+appid);
			if(value==appid){
				$("#"+status_id).html(applyStatus);
			}
		}
	}
	
}
 
function formatTrue(value, row, index) {
	return value == 1 ? "是" : "否";
}
 

//操作
function operation(value, row, index) {
	var htm = ''
	if (row.id === undefined) {
		htm += '<button onclick="handleApplyAndUpdateClick(' + index + ')">特批申请</button>'
	} else if (row.applyStatus !== 1 || row.status === 1) {
		htm += '<button onclick="handleApplyAndUpdateClick(' + index + ')">修改</button>'
		htm += '<button onclick="handleSubmitClick(' + index + ')">提交</button>'
	}
	return htm;
	// var apply_opt_id="opt_"+row.kOrderId;
	// var show_opt_id="show_"+row.kOrderId;
	// var htm = '<button id="'+apply_opt_id+'" onclick="toAdd()" style="display:none;">特批申请</button> <button id="'+show_opt_id+'" style="display:none;" onclick="toShow()">查看申请</button>';
}
function statusfun(value, row, index){
	var status_id="status_"+row.kOrderId;
	var htm = '<span id="'+status_id+'"></span>';
	return htm;
}

function toAdd(){
//	console.log(orderId);
	window.location.href=ctxPath+"special/toAdd?kOrderVersionId="+kOrderVersionId+"&ordersId="+orderId;
}

function toShow(){
//	console.log(applyId);
	window.location.href=ctxPath+"special/toShow?applyId="+applyId;
}

function handleApplyAndUpdateClick(index) {
	let row = $('#mytab').bootstrapTable('getData')[index];
	let id = row.id
	if (id) {
		//修改
		window.location.href = ctxPath + "menu/showApply?row=" + encodeURIComponent(JSON.stringify(row));
	} else {
		//申请
		window.location.href = ctxPath + "menu/specialApply?sequenceNumber="
			+ row['sequenceNumber'] + "&customerCode="
			+ row['customerCode'] + "&orderInfoId="
			+ row['orderInfoId'];
	}
}

function handleSubmitClick(id) {
	alert(id)
}
 
//查询按钮事件
$('#search_btn').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : ctxPath+'specialdelivery'
	});
})

//重置按钮事件
$('#reset_btn').click(function() {
	$("#sequenceNumber").val("");
	$("#createTime1").val("");
	$("#orderTypeCode").val("-1");
})

















