$(document).ready(function() {
    $('#reservation').daterangepicker(null, function(start, end, label) {
      console.log(start.toISOString(), end.toISOString(), label);
    });
        
});
//var ctxPath  = [[@{/}]];
$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
    //查询按钮事件
    $('#search_btn').click(function() {
    	$('#mytab').bootstrapTable('refresh', {
    		url : ctxPath + 'report/biddingDetailList'
    	});
    });

    //重置按钮事件
    $('#reset_btn').click(function() {
    	$("#customer").val("");
    	$("#reservation").val("");
    	$("#area").val("-1");
    });
    
    

  //导出按钮事件
  $('#export_btn').click(function(e) {
  	e.preventDefault();
  	
  	var customer = $("#customer").val();    
  	var area = $("#area").val();    
  	var reservation = $("#reservation").val();    
  	
  	window.location.href=ctxPath+"report/exportExcel?customer="+customer+"&area="+area+"&createTime1="+reservation;
  	
  });
  
  
//下载按钮事件
  $('#down_btn').click(function(e) {
  	e.preventDefault();
  	var customer = $("#customer").val();    
  	window.location.href=ctxPath+"report/down";
  	
  });
    
});

var TableInit = function () {
	var oTableInit = new Object();
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'get',
			url : ctxPath+"report/biddingDetailList",//请求路径
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
		
			       // customer:$('#customer').val(),
			        area:$('#area').val(),
			        createTime1:$('#reservation').val()
			    };
			    return temp;
			},
			columns : [ {
				title : '序号',
				field : 'id',
				sortable : true
			}, {
				title : '流水号 Code',
				field : 'contractNo',
				sortable : true
			}, {
				title : '分类 Type',
				field : 'contractNo',
				sortable : true
			}, {
				title : '客户Customer',
				field : 'area',
				sortable : true
			}, {
				title : '店名Name',
				field : 'contractNo',
				sortable : true
			}, {
				title : '开标时间Bid Open Time',
				field : 'contractNo',
				sortable : true
			}, {
				title : '供应商Supplier',
				field : 'contractNo',
				sortable : true
			},{
				title : '金额Amount',
				field : 'contractUnit',
				sortable : true
			},{
				title : '毛利Gross Margin',
				field : 'contractUnit',
				sortable : true
			},{
				title : '新店/改造店/其他New Store/Renovation Store/Others',
				field : 'contractUnit',
				sortable : true
			},{
				title : '大区Area',
				field : 'contractUnit',
				sortable : true
			},{
				title : '是否中标Whether Or Not The Winning',
				field : 'orderType',
				sortable : true
			},{
				title : '备注Remark',
				field : 'orderType',
				sortable : true
			}]
		})
			};
		return oTableInit;
};

function formatTrue(value, row, index) {
	return value == 1 ? "是" : "否";
	//或者 return row.sex == 1 ? "男" : "女";
}
