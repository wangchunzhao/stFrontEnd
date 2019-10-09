
$(function () {
    //1.初始化Table
	var oTable = new TableInit();
	oTable.Init();
	 $("#currency").change(function(value){
		 if($(this).val()=='12'){
			 $("#exchangeRate").val('6') 
			 var exchangeRate = $("#exchangeRate").val()
			 var contractAmount = $("#contractAmount").val();
			 var orignalContractAmount = exchangeRate*contractAmount
			 $("#orignalContractAmount").val(orignalContractAmount);
		 }else{
			 $("#exchangeRate").val('7')
			 var exchangeRate = $("#exchangeRate").val()
			 var contractAmount = $("#contractAmount").val();
			 var orignalContractAmount = exchangeRate*contractAmount
			 $("#orignalContractAmount").val(orignalContractAmount);
		 }
         
     })

});
function selectOnchang(obj){
	alert("");
}
var TableInit = function () {
	var oTableInit = new Object();	
	oTableInit.Init = function () {
		$('#customerTab').bootstrapTable({
			method : 'get',
			url : "",//请求路径
			striped : true, //是否显示行间隔色
			cache: false,
			pagination: true,                   //是否显示分页（*）
			paginationDetailHAlign:'right',
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1, //初始化加载第一页
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
				title : '签约单位',
				field : 'id',
				sortable : true
			}, {
				title : '地址',
				field : 'contractNo',
				sortable : true
			},{
				title : '性质分类',
				field : 'contractUnit',
				sortable : true
			}]
		})
			};
		return oTableInit;
};




















