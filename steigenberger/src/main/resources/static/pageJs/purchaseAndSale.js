$(document).ready(function() {
    $('#reservation').daterangepicker(null, function(start, end, label) {
      console.log(start.toISOString(), end.toISOString(), label);
    });
        
});

$(function () {
    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
    
    var mTable = new materialTableInit();
    mTable.Init();
    
    
    $("#byOrder").on("click",function(){
     	$('#mtable').attr('style',"display:none;");
     	$('#otable').attr('style',"display:'';");
     });

     $("#byMaterial").on("click",function(){
    	 $('#otable').attr('style',"display:none;");
      	$('#mtable').attr('style',"display:'';");
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
			pageSize : 1,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : function (params) {
			    var temp = {
			        limit : params.limit, // 每页显示数量
			        offset : params.offset, // SQL语句起始索引
			        page: (params.offset / params.limit) + 1,   //当前页码
		
			        contractNo:$('#contractNo').val(),
			        contractUnit:$('#contractUnit').val(),
			        area:$('#area').val(),
			        orderType:$('#orderType').val(),
			        specialDiscount:$('#specialDiscount').val(),
			        createTime1:$('#reservation').val(),
			        status:$('#status').val()
			
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
				title : '序号22222222222',
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
		return mTableInit;
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
	$("#id").val("");
	$("#contractNo").val("");
	$("#reservation").val("");
	$("#contractUnit").val("");
	$("#area").val(-1);
})



















