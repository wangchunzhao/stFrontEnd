
$(function () {
    //1.初始化Table
	var oTable = new TableInit();
	$('#showGrossProfit').on('onclick',function(){
		oTable.Init();
	});

});

var TableInit = function () {
	var oTableInit = new Object();
	
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'get',
			url : "/steigenberger/newOrder/getGrossProfitList",//请求路径
			striped : true, //是否显示行间隔色
			toolbar: '#toolbar',
			cache: false,
			pagination: true,                   //是否显示分页（*）
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1, //初始化加载第一页
			pagination : false,//是否分页
			sidePagination : 'server',//server:服务器端分页|client：前端分页
			pageSize : 30,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : function (params) {
			    var temp = {
			    };
			    return temp;
			},
			columns : [ {
				title : '产品名称流水号',
				field : 'id',
				sortable : true
			}, {
				title : '金额',
				field : 'contractNo',
				sortable : true
			},{
				title : '不含税金额',
				field : 'contractUnit',
				sortable : true
			},{
				title : '成本',
				field : 'area',
				sortable : true
			},{
				title : '毛利',
				field : 'orderType',
				sortable : true
			},{
				title : '毛利率',
				field : 'b2c',
				sortable : true
			}]
		})
			};
		return oTableInit;
};




















