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
    		url : ctxPath + 'report/ordersummary'
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
			url : ctxPath+"report/ordersummary",//请求路径
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
			        status         : $("#status").val(),
			        contractManager : $("#contractManager").val(),
			        stOrderType        : $("#stOrderType").val()
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
				title : '合同金额（凭证货币）',
				field : 'contractValue',
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
				title : '订单状态',
				field : 'statusDesc',
				sortable : false
			},{
				title : '性质分类',
				field : 'industryCodeName',
				sortable : false
			},{
				title : '合同管理员',
				field : 'contractManager',
				sortable : false
			},{
				title : '订单类型',
				field : 'stOrderTypeDesc',
				sortable : false
			},{
				title : '是否年采客户',
				field : 'isYearpurchase',
				sortable : false,
				formatter: formatTrue
			},{
				title : '项目报备编号',
				field : 'recordCode',
				sortable : false
			},{
				title : '税率',
				field : 'taxRate',
				sortable : false
			},{
				title : '币种',
				field : 'currency',
				sortable : false
			},{
				title : '汇率',
				field : 'exchange',
				sortable : false
			},{
				title : '国际贸易条件1',
				field : 'incoterm',
				sortable : false
			},{
				title : '国际贸易条件2',
				field : 'incotermContect',
				sortable : false
			},{
				title : '保修年限',
				field : 'warranty',
				sortable : false
			},{
				title : '安装方式',
				field : 'installType',
				sortable : false
			},{
				title : '省市区',
				field : 'city',
				sortable : false
			},{
				title : '到货地址',
				field : 'address',
				sortable : false
			},{
				title : '运输方式',
				field : 'transferType',
				sortable : false
			},{
				title : '追加运费',
				field : 'additionalFreight',
				sortable : false
			},{
				title : '外销运费',
				field : 'freight',
				sortable : false
			},{
				title : '授权人1及身份证号',
				field : 'contactor1Id',
				sortable : false
			},{
				title : '授权人1电话',
				field : 'contactor1Tel',
				sortable : false
			},{
				title : '授权人2及身份证号',
				field : 'contactor2Id',
				sortable : false
			},{
				title : '授权人2电话',
				field : 'contactor2Tel',
				sortable : false
			},{
				title : '授权人3及身份证号',
				field : 'contactor3Id',
				sortable : false
			},{
				title : '授权人3电话',
				field : 'contactor3Tel',
				sortable : false
			},{
				title : '结算方式',
				field : 'paymentType',
				sortable : false
			},{
				title : '要求发货时间',
				field : 'earliestDeliveryDate',
				sortable : false
			},{
				title : '柜体控制阀件是否甲供',
				field : 'isTerm1',
				sortable : false,
				formatter: formatTrue
			},{
				title : '分体归是否远程监控',
				field : 'isTerm2',
				sortable : false,
				formatter: formatTrue
			},{
				title : '立柜柜体是否在地下室',
				field : 'isTerm3',
				sortable : false,
				formatter: formatTrue
			},{
				title : '是否特批发货',
				field : 'isUrgentDelivery',
				sortable : false,
				formatter: formatTrue
			},{
				title : '是否特批下单',
				field : 'isSpecialOrder',
				sortable : false,
				formatter: formatTrue
			},{
				title : '销售毛利率',
				field : 'margin',
				sortable : false,
				formatter: formatMargin
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