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
    		url : ctxPath + 'report/bidding'
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
			url : ctxPath+"report/bidding",//请求路径
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
			        pageNo: (params.offset / params.limit) + 1,   //当前页码
			        pageSize: params.limit,                         //页面大小
		
			        sequenceNumber : $("#sequenceNumber").val(),
			        contractNumber : $("#contractNumber").val(),
			        customerName   : $("#customerName").val(),
			        salesName      : $("#salesName").val(),
			        industry       : $("#industry").val(),
			        industryCode   : $("#industryCode").val(),
			        saleType       : $("#saleType").val(),
			        contractAmount1: $("#contractAmount1").val(),
			        contractAmount2: $("#contractAmount2").val(),
			        createTime     : $("#createTime").val(),
			        officeCode     : $("#officeCode").val(),
			        winning        : $("#winning").val(),
			        status         : $("#status").val()
			    };
			    return temp;
			},
			responseHandler: function (res) {
				console.log("result : ");
				console.log(res);
				if (res.status != 'ok') {
					layer.alert(res.msg);
					return {};
				}
				return {
					total: res.data.total,
					rows: res.data.rows
				}
            },
            exportDataType: "excel",
            //exportTypes: ['json', 'xml', 'csv', 'txt', 'sql', 'excel', 'pdf'],
			columns : [ {
				title : '需求流水号',
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
			}, {
				title : '店名',
				field : 'shopName',
				sortable : true
			}, {
				title : '客户经理名称',
				field : 'salesName',
				sortable : true
			}, {
				title : '是否改造店',
				field : 'isReformed',
				sortable : true,
				formatter: formatTrue
			},{
				title : '是否新客户',
				field : 'isNew',
				sortable : true,
				formatter: formatTrue
			},{
				title : '是否便利店',
				field : 'isConvenientStore',
				sortable : true,
				formatter: formatTrue
			},{
				title : '隶属关系',
				field : 'industryName',
				sortable : true
			},{
				title : '客户分类',
				field : 'customerIndustryCodeName',
				sortable : true
			},{
				title : '销售类型',
				field : 'saleTypeDesc',
				sortable : true
			},{
				title : '原币合同金额',
				field : 'contractRmbValue',
				sortable : true
			},{
				title : '订单创建日期',
				field : 'createTime',
				sortable : true
			},{
				title : '大区',
				field : 'officeName',
				sortable : true
			},{
				title : '中心',
				field : 'groupName',
				sortable : true
			},{
				title : '销售毛利率',
				field : 'margin',
				sortable : true,
				formatter: formatMargin
			},{
				title : '是否中标',
				field : 'quoteStatusDesc',
				sortable : true
			},{
				title : '订单状态',
				field : 'statusDesc',
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

function formatMargin(value, row, index) {
	return value * 100 + "%";
	//或者 return row.sex == 1 ? "男" : "女";
}
