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
    		url : ctxPath + 'report/orderitems'
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
    	$("#status").val("");
    	$("#contractManager").val("");
    	$("#stOrderType").val("");
    	$("#materialName").val("");
    	$("#requirementPlan").val("");
    	$("#itemCategory").val("");
    	$("#onStoreDate").val("");
    	$("#shippDate").val("");
    });
    
 // 自定义按钮导出数据
    $('#export_btn').click(function() {
		var params = getQueryParams({limit: 1000, offset: 0});
		var paramsUrl = toUrl(params);
		var url = ctxPath + "report/export?reportname=orderitems&" + paramsUrl;
		
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
        status         : $("#status").val(),
        contractManager : $("#contractManager").val(),
        stOrderType        : $("#stOrderType").val(),
        materialName        : $("#materialName").val(),
        requirementPlan        : $("#requirementPlan").val(),
        itemCategory        : $("#itemCategory").val(),
        onStoreDate        : $("#onStoreDate").val(),
        shippDate        : $("#shippDate").val()
    };
    return temp;
}

var TableInit = function () {
	var oTableInit = new Object();
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'get',
			url : ctxPath+"report/orderitems", // 请求路径
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
                   return 'OrderItems'
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
				title : '序号',
				width : '5%',
				visible : false,
				formatter: function(value, row, index) {
			        var pageSize = $('#mytab').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
			        var pageNumber = $('#mytab').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
			        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
			    }
			}, {
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
			}, {
				title : '行号'
				,field : 'rowNum'
				,sortable : false
			}, {
				title : '规格型号'
				,field : 'materialName'
				,sortable : false
			}, {
				title : '专用号',
				field : 'materialCode',
				sortable : false
			}, {
				title : '状态',
				field : 'itemStatus',
				sortable : false
			}, {
				title : '需求计划',
				field : 'itemRequirementPlan',
				sortable : false
			}, {
				title : '类型',
				field : 'materialGroupCode',
				sortable : false
			}, {
				title : '数量',
				field : 'quantity',
				sortable : false
			}, {
				title : '计量单位',
				field : 'unitCode',
				sortable : false
			}, {
				title : '产品实卖单价（凭证货币）',
				field : 'actualPrice',
				sortable : false
			}, {
				title : '产品实卖金额（凭证货币）',
				field : 'actualAmount',
				sortable : false
			}, {
				title : '产品转移单价（凭证货币）',
				field : 'transactionPrice',
				sortable : false
			}, {
				title : '产品转移金额（凭证货币）',
				field : 'transactionPriceAmount',
				sortable : false
			}, {
				title : '可选项实卖单价（凭证货币）',
				field : 'optionalActualPrice',
				sortable : false
			}, {
				title : '可选项实卖金额（凭证货币）',
				field : 'originalOptionalActualPrice',
				sortable : false
			}, {
				title : '可选项转移单价（凭证货币）',
				field : 'optionalTransactionPrice',
				sortable : false
			}, {
				title : '可选项转移金额（凭证货币）',
				field : 'optionalTransationPriceAmount',
				sortable : false
			}, {
				title : 'B2C预估单价（CNY）',
				field : 'b2cEstimatedPrice',
				sortable : false
			}, {
				title : 'B2C预估金额（CNY）',
				field : 'b2cEstimatedAmount',
				sortable : false
			}, {
				title : 'B2C预估成本单价',
				field : 'b2cEstimatedCost',
				sortable : false
			}, {
				title : 'B2C预估成本金额',
				field : 'b2cEstimatedCostAmount',
				sortable : false
			}, {
				title : '实卖单价小计（凭证货币）',
				field : 'actualPriceSum',
				sortable : false
			}, {
				title : '实卖金额合计（凭证货币）',
				field : 'actualAmountSum',
				sortable : false
			}, {
				title : '转移单价小计',
				field : 'transactionPriceSum',
				sortable : false
			}, {
				title : '转移金额合计',
				field : 'transactionAmountSum',
				sortable : false
			}, {
				title : '市场零售价（CNY）',
				field : 'retailPrice',
				sortable : false
			}, {
				title : '市场零售金额（CNY）',
				field : 'retailAmount',
				sortable : false
			}, {
				title : '折扣率',
				field : 'discount',
				sortable : false
			}, {
				title : '行项目类别',
				field : 'itemCategory',
				sortable : false
			}, {
				title : '物料属性',
				field : 'isPurchased',
				sortable : false
			}, {
				title : '生产周期',
				field : 'period',
				sortable : false
			}, {
				title : '工厂最早交货时间',
				field : 'deliveryDate',
				sortable : false
			}, {
				title : '生产开始时间',
				field : 'produceDate',
				sortable : false
			}, {
				title : '要求发货时间',
				field : 'shippDate',
				sortable : false
			}, {
				title : '入库时间',
				field : 'onStoreDate',
				sortable : false
			}, {
				title : 'B2C备注',
				field : 'b2cComments',
				sortable : false
			}, {
				title : '特殊备注',
				field : 'specialComments',
				sortable : false
			}, {
				title : '颜色备注',
				field : 'colorComments',
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
