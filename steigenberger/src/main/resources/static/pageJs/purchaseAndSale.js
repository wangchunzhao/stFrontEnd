$(document).ready(function() {
    $('#createTime').daterangepicker(null, function(start, end, label) {
      console.log(start.toISOString(), end.toISOString(), label);
    });
        
});

var queryType="byOrder";

$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
    var mTable = new materialTableInit();
    mTable.Init();
    
    
    $("#byOrder").on("click",function(){
     	$('#mtable').attr('style',"display:none;");
     	$('#otable').attr('style',"display:'';");
     	queryType="byOrder";
     });

     $("#byMaterial").on("click",function(){
    	 $('#otable').attr('style',"display:none;");
      	$('#mtable').attr('style',"display:'';");
      	queryType="byMaterial";
     });
    
});



var TableInit = function () {
	var oTableInit = new Object();
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'get',
			url : "/steigenberger/report/index",//请求路径
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
			pageSize : 10,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : function (params) {
			    var temp = {
			        limit : params.limit, // 每页显示数量
			        offset : params.offset, // SQL语句起始索引
			        page: (params.offset / params.limit) + 1,   //当前页码
		
			        queryType:queryType,
			        sequenceNumber:$('#sequenceNumber').val(),
			        contractorCode:$('#contractorCode').val(),
			        contractUnit:$('#contractUnit').val(),
			        createTime:$('#createTime').val(),
			        contractorClassCode:$('#contractorClassCode').val(),
			        isSpecialDiscount:$('#isSpecialDiscount').val(),
			        orderTypeCode:$('#orderTypeCode').val(),
			        officeCode:$('#officeCode').val()
			
			    };
			    return temp;
			},
			columns : [ {
				title : '序号',
				field : 'id',
				sortable : true
			}, {
				title : '单据编号',
				field : 'contractNo',
				sortable : true
			}, {
				title : '签约人',
				field : 'contractNo',
				sortable : true
			}, {
				title : '区域',
				field : 'area',
				sortable : true
			}, {
				title : '中心',
				field : 'contractNo',
				sortable : true
			}, {
				title : '签约日期',
				field : 'contractNo',
				sortable : true
			}, {
				title : '合同号',
				field : 'contractNo',
				sortable : true
			},{
				title : '客户编号',
				field : 'contractUnit',
				sortable : true
			},{
				title : '签约单位',
				field : 'contractUnit',
				sortable : true
			},{
				title : '客户性质',
				field : 'contractUnit',
				sortable : true
			},{
				title : '店名',
				field : 'contractUnit',
				sortable : true
			},{
				title : '终端客户性质',
				field : 'orderType',
				sortable : true
			},{
				title : '是否特批折扣',
				field : 'orderType',
				sortable : true
			},{
				title : '是否改造店',
				field : 'orderType',
				sortable : true
			},{
				title : '合同金额',
				field : 'orderType',
				sortable : true
			},{
				title : '毛利率',
				field : 'orderType',
				sortable : true
			},{
				title : '是否长期折扣',
				field : 'orderType',
				sortable : true
			},{
				title : '折扣',
				field : 'orderType',
				sortable : true
			},{
				title : '产品规格型号',
				field : 'orderType',
				sortable : true
			},{
				title : '物料专用号',
				field : 'orderType',
				sortable : true
			},{
				title : '物料属性',
				field : 'orderType',
				sortable : true
			},{
				title : '合同数量',
				field : 'orderType',
				sortable : true
			},{
				title : '销售单价',
				field : 'orderType',
				sortable : true
			}, {
				title : '销售金额',
				field : 'contractNo',
				sortable : true
			}, {
				title : '单位',
				field : 'contractNo',
				sortable : true
			}, {
				title : '合同号',
				field : 'contractNo',
				sortable : true
			}, {
				title : '到货地址',
				field : 'contractNo',
				sortable : true
			}, {
				title : '要求发货时间',
				field : 'contractNo',
				sortable : true
			}, {
				title : '安装公司',
				field : 'contractNo',
				sortable : true
			}, {
				title : '收货方式',
				field : 'contractNo',
				sortable : true
			}, {
				title : '授权人及身份证号',
				field : 'contractNo',
				sortable : true
			}, {
				title : '授权人电话',
				field : 'contractNo',
				sortable : true
			}, {
				title : '收货人身份证号',
				field : 'contractNo',
				sortable : true
			}, {
				title : '结算方式',
				field : 'contractNo',
				sortable : true
			}, {
				title : '运费',
				field : 'contractNo',
				sortable : true
			}, {
				title : '保修期限（年）',
				field : 'contractNo',
				sortable : true
			}, {
				title : '财务1审核人',
				field : 'contractNo',
				sortable : true
			}, {
				title : '财务2审核人',
				field : 'contractNo',
				sortable : true
			}, {
				title : '币别',
				field : 'contractNo',
				sortable : true
			}, {
				title : '原币合同金额',
				field : 'contractNo',
				sortable : true
			}, {
				title : '汇率',
				field : 'contractNo',
				sortable : true
			}, {
				title : '是否新客户',
				field : 'contractNo',
				sortable : true
			}]
		})
			};
		return oTableInit;
};


var materialTableInit = function () {
	var mTableInit = new Object();
	
	mTableInit.Init = function () {
		$('#mytab2').bootstrapTable({
			method : 'get',
			url : "/steigenberger/report/index",//请求路径
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
			pageSize : 10,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : function (params) {
			    var temp = {
			        limit : params.limit, // 每页显示数量
			        offset : params.offset, // SQL语句起始索引
			        page: (params.offset / params.limit) + 1,   //当前页码
			        queryType:queryType,
			        sequenceNumber:$('#sequenceNumber').val(),
			        contractorCode:$('#contractorCode').val(),
			        contractUnit:$('#contractUnit').val(),
			        createTime:$('#createTime').val(),
			        contractorClassCode:$('#contractorClassCode').val(),
			        isSpecialDiscount:$('#isSpecialDiscount').val(),
			        orderTypeCode:$('#orderTypeCode').val(),
			        officeCode:$('#officeCode').val()
			    };
			    return temp;
			},
			columns : [ {
				title : '序号',
				field : 'id',
				sortable : true,
				formatter:function (value, row, index) {
					return index + 1;
				}
			}, {
				title : '流水号',
				field : 'sequenceNumber',
				sortable : true
			}, {
				title : '签约人',
				field : 'contractorName',
				sortable : true
			}, {
				title : '区域',
				field : 'officeCode',
				sortable : true
			}, {
				title : '中心',
				field : 'groupName',
				sortable : true
			}, {
				title : '签约日期',
				field : 'createTime',
				sortable : true
			}, {
				title : '合同号',
				field : 'contractSeq',
				sortable : true
			},{
				title : '客户编号',
				field : 'coustomerNo',
				sortable : true
			},{
				title : '签约单位',
				field : 'contractUnit',
				sortable : true
			},{
				title : '客户性质',
				field : 'contractorClassName',
				sortable : true
			},{
				title : '店名',
				field : 'customerName',
				sortable : true
			},{
				title : '终端客户性质',
				field : 'terminalIndustryCodeName',
				sortable : true
			},{
				title : '是否特批折扣',
				field : 'isSpecialDiscount',
				sortable : true
			},{
				title : '是否改造店',
				field : 'isRreformed',
				sortable : true
			},{
				title : '合同金额',
				field : 'amount',
				sortable : true
			},{
				title : '毛利率',
				field : 'grossProfit',
				sortable : true
			},{
				title : '是否长期折扣',
				field : 'isLongTermDiscount',
				sortable : true
			},{
				title : '折扣',
				field : 'discount',
				sortable : true
			},{
				title : '产品规格型号',
				field : 'materialCode',
				sortable : true
			},{
				title : '物料专用号',
				field : 'materialSpecificNumber',
				sortable : true
			},{
				title : '物料属性',
				field : 'materialAttribute',
				sortable : true
			},{
				title : '合同数量',
				field : 'quantity',
				sortable : true
			},{
				title : '销售单价',
				field : 'price',
				sortable : true
			}, {
				title : '销售金额',
				field : 'amount',
				sortable : true
			}, {
				title : '单位',
				field : 'measureUnitName',
				sortable : true
			}, {
				title : '到货地址',
				field : 'receiverAddress',
				sortable : true
			}, {
				title : '要求发货时间',
				field : 'earliestDeliveryDate',
				sortable : true
			}, {
				title : '安装公司',
				field : 'installTermName',
				sortable : true
			}, {
				title : '收货方式',
				field : 'receiveType',
				sortable : true
			}, {
				title : '授权人及身份证号1',
				field : 'contactor1Id',
				sortable : true
			}, {
				title : '授权人电话1',
				field : 'contactor1Tel',
				sortable : true
			}, {
				title : '授权人及身份证号2',
				field : 'contactor2Id',
				sortable : true
			}, {
				title : '授权人电话2',
				field : 'contactor2Tel',
				sortable : true
			}, {
				title : '授权人及身份证号3',
				field : 'contactor3Id',
				sortable : true
			}, {
				title : '授权人电话3',
				field : 'contactor3Tel',
				sortable : true
			},{
				title : '收货人身份证号',
				field : 'receiverID',
				sortable : true
			}, {
				title : '结算方式',
				field : 'settlementMethod',
				sortable : true
			}, {
				title : '运费',
				field : 'freight',
				sortable : true
			}, {
				title : '保修期限（年）',
				field : 'warranty',
				sortable : true
			},{
				title : '币别',
				field : 'currencyName',
				sortable : true
			}, {
				title : '原币合同金额',
				field : 'contractAmount',
				sortable : true
			}, {
				title : '汇率',
				field : 'exchange',
				sortable : true
			}, {
				title : '是否新客户',
				field : 'isNew',
				sortable : true
			}]
		})
			};
		return mTableInit;
};




 
//查询按钮事件
$('#byOrder').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : '/steigenberger/report/index'
	});
})

$('#byMaterial').click(function() {
	$('#mytab2').bootstrapTable('refresh', {
		url : '/steigenberger/report/index'
	});
})

//重置按钮事件
$('#resetBtn').click(function() {
	$("#sequenceNumber").val("");
	$("#contractorCode").val("");
	$("#contractUnit").val("");
	$("#createTime").val("");
	$("#contractorClassCode").val("");
	$("#isSpecialDiscount").val("-1");
	$("#orderTypeCode").val("");
	$("#officeCode").val("");
	
})



















