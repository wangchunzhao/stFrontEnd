$(document).ready(function() {
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
    	$("#sequenceNumber").val("");
    	$("#contractNumber").val("");
    	$("#customerName").val("");
    	$("#salesName").val("");
    	$("#industry").val("");
    	$("#industryCode").val("");
    	$("#saleType").val("");
    	$("#contractAmount1").val("");
    	$("#contractAmount2").val("");
    	$("#createTime").val("");
    	$("#officeCode").val("");
    	$("#winning").val("");
    	$("#status").val("");
    });

  //导出按钮事件
  $('#export_btn').click(function(e) {
		var params = getQueryParams({limit: 1000, offset: 0});
		var paramsUrl = toUrl(params);
		var url = ctxPath + "report/export?reportname=bidding&" + paramsUrl;
		
	    var a = document.createElement("a");// 创建a标签
	    a.target = "_blank"; // 每次点击一次都打开新的窗口；
	    // a.download = "a.pdf";// 设置下载文件的文件名
	    a.href = url;// content为后台返回的下载地址
	    a.click();// 设置点击事件 
    });
});

function getQueryParams(params) {
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
}

var TableInit = function () {
	var oTableInit = new Object();
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'get',
			url : ctxPath+"report/bidding",//请求路径
			striped : true, // 是否显示行间隔色
			toolbar: '#toolbar',
			cache: false,
			pagination: true,                   //是否显示分页（*）
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1,  // 初始化加载第一页
			pagination : true, // 是否分页
			sidePagination : 'server', // server:服务器端分页|client：前端分页
			pageSize : 20, // 单页记录数
			pageList : [ 10, 20, 30 ], // 可选择单页记录数
			showRefresh : false, // 刷新按钮
            showExport: false, // 工具栏上显示导出按钮
            exportDataType: 'all', // 导出范围，無效
            exportTypes:['XLSX'],  // 导出文件类型 ‘json’, ‘xml’, ‘png’, ‘csv’, ‘txt’, ‘sql’, ‘doc’, ‘excel’, ‘xlsx’, ‘pdf’
            exportOptions: {
                fileName: function () {
                   return 'BiddingReport'
                }
             }, 
			queryParams : getQueryParams,
			responseHandler: function (res) {
				console.log("result : ");
				console.log(res);
				if (res.status != 'ok') {
					layer.alert(res.msg);
					return {};
				}
				return {
					total: res.data.total,
					rows: res.data.rows == undefined ? [] : res.data.rows
				}
            },
			columns : [ {
				title : '需求流水号',
				field : 'sequenceNumber',
				sortable : false
			}, {
				title : '合同号',
				field : 'contractNumber',
				sortable : false
			}, {
				title : '签约单位',
				field : 'customerName',
				sortable : false
			}, {
				title : '店名',
				field : 'shopName',
				sortable : false
			}, {
				title : '客户经理名称',
				field : 'salesName',
				sortable : false
			}, {
				title : '是否改造店',
				field : 'isReformed',
				sortable : false,
				formatter: formatTrue
			},{
				title : '是否新客户',
				field : 'isNew',
				sortable : false,
				formatter: formatTrue
			},{
				title : '是否便利店',
				field : 'isConvenientStore',
				sortable : false,
				formatter: formatTrue
			},{
				title : '隶属关系',
				field : 'industryName',
				sortable : false
			},{
				title : '客户分类',
				field : 'customerIndustryCodeName',
				sortable : false
			},{
				title : '销售类型',
				field : 'saleTypeDesc',
				sortable : false
			},{
				title : '原币合同金额',
				field : 'contractRmbValue',
				sortable : false
			},{
				title : '订单创建日期',
				field : 'createTime',
				sortable : false
			},{
				title : '大区',
				field : 'officeName',
				sortable : false
			},{
				title : '中心',
				field : 'groupName',
				sortable : false
			},{
				title : '销售毛利率',
				field : 'margin',
				sortable : false,
				formatter: formatMargin
			},{
				title : '是否中标',
				field : 'quoteStatusDesc',
				sortable : false
			},{
				title : '订单状态',
				field : 'statusDesc',
				sortable : false
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
	// return value * 100 + "%";
	return value + "%";
}
