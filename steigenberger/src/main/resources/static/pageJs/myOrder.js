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
//	url : "/steigenberger/myOrder/orderManageList1",//请求路径
	url : "/steigenberger/myOrder/getUserListPage",//请求路径
	striped : true, //是否显示行间隔色
	toolbar: '#toolbar',
	cache: false,
	pagination: true,                   //是否显示分页（*）
    sortable: true,                     //是否启用排序
    clickToSelect: true,               //是否启用点击选中行
    sortOrder: "asc",                   //排序方式
	pageNumber : 1, //初始化加载第一页
	pagination : true,//是否分页
	sidePagination : 'client',//server:服务器端分页|client：前端分页
	pageSize : 10,//单页记录数
	pageList : [ 10, 20, 30 ],//可选择单页记录数
	showRefresh : false,//刷新按钮
	queryParams : function (params) {
	    var temp = {
//	        limit : params.limit, // 每页显示数量
//	        offset : params.offset, // SQL语句起始索引
//	        page: (params.offset / params.limit) + 1,   //当前页码
	            
	       // Name:$('#search_name').val(),
	       // Tel:$('#search_tel').val()
	    };
	    return temp;
	},
	columns : [ {
		title : '登录名',
		field : 'code',
		sortable : true
	}, {
		title : '姓名',
		field : 'name',
		sortable : true
	},  {
		title : '操作',
		field : 'code',
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
function formatSex(value, row, index) {
	return value == 1 ? "男" : "女";
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
		url : 'user/getUserListPage'
	});
})
