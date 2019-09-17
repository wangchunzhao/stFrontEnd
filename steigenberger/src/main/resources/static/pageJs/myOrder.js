$(document).ready(function() {
        $('#reservation').daterangepicker(null, function(start, end, label) {
          console.log(start.toISOString(), end.toISOString(), label);
        });
});

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
//			url : "/steigenberger/myOrder/myOrderManageList",//请求路径
			url : "/steigenberger/kOrderInfo/kOrderInfoList",//请求路径
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
			        limit : params.limit, // 每页显示数量
			        offset : params.offset, // SQL语句起始索引
			        page: (params.offset / params.limit) + 1,   //当前页码
		
			        id:$('#id').val(),
			        contractNo:$('#contractNo').val(),
			        contractUnit:$('#contractUnit').val(),
			        area:$('#area').val(),
			        orderType:$('#orderType').val(),
			        b2c:$('#b2c').val(),
			        specialDiscount:$('#specialDiscount').val(),
			        createTime1:$('#reservation').val(),
			        status:$('#status').val()
			       // Tel:$('#search_tel').val()
			    };
			    return temp;
			},
			columns : [ {
				title : '流水号',
				field : 'id',
				sortable : true
			}, {
				title : '合同号',
				field : 'contractNo',
				sortable : true
			},{
				title : '签约单位',
				field : 'contractUnit',
				sortable : true
			},{
				title : '区域',
				field : 'area',
				sortable : true
			},{
				title : '订单类型',
				field : 'orderType',
				sortable : true
			},{
				title : '是否有B2C',
				field : 'b2c',
				formatter : formatTrue,
				sortable : true
			},{
				title : '是否特批折扣',
				field : 'specialDiscount',
				formatter : formatTrue,
				sortable : true
			},{
				title : '创建日期',
				field : 'createTime',
				sortable : true
			},{
				title : '状态',
				field : 'status',
				sortable : true
			},{
				title : 'SAP状态',
				field : 'sapStatus',
				sortable : true
			},  {
				title : '操作',
				field : 'id',
				formatter : operation,//对资源进行操作
			} ]
		})
			};
		return oTableInit;
};
 
//value代表该列的值，row代表当前对象
//{
//		title : '性别',
//		field : 'sex',
//		formatter : formatSex,//对返回的数据进行处理再显示
//	},
function formatTrue(value, row, index) {
	return value == 1 ? "是" : "否";
	//或者 return row.sex == 1 ? "男" : "女";
}
 
//删除、编辑操作
function operation(value, row, index) {
	var htm = "<button>删除</button><button>修改</button>"
	return htm;
}
 
//查询按钮事件
$('#search_btn').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : '/steigenberger/kOrderInfo/kOrderInfoList'
	});
})

//重置按钮事件
$('#resetBtn').click(function() {
	$("#id").val("");
	$("#contractNo").val("");
	$("#reservation").val("");
	$("#contractUnit").val("");
	$("#area").val(-1);
})



















