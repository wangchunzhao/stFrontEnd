$(function () {
	var customerUnitTable = '/steigenberger/order/customers'
	var contractUnitTable = new TableInit('contractUnitTable',customerUnitTable,queryUnitParams,contractUnitTableColumn);
	contractUnitTable.init();
	$("#contractUnitTable").on('check.bs.table',function(){
		$('#unitModal').modal('hide');
		$("#contractUnitName").val('');
		var selectCustomer=$("#contractUnitTable").bootstrapTable('getSelections');
		$("#customer").val(selectCustomer[0].name)
		$("#customerClazz").val(selectCustomer[0].clazzName)
	})
	
	var paymentTable = new TableInit('paymentTable','','',paymentColumns);
	paymentTable.init();
});
//Customer basic infomation js start 
function openSearchUnit(){
	$('#unitModal').modal('show');	
}
function searchUnit(){
	$('#contractUnitTable').bootstrapTable('refresh');
}

function getCity(obj,cityMaps){
	var provinceCode = $(obj).val();
	if ($(obj).val() == '') {
		$("#citySelect").html('');
		$("#citySelect").append("<option value=''>--选择市--</option>");
		$("#citySelect").val('');
		
		$("#selectDistrict").html('');
		$("#selectDistrict").append("<option value=''>--选择区--</option>");
		$("#selectDistrict").val('');
	}else{
		$("#citySelect").html('');
		$("#citySelect").append("<option value=''>--选择市--</option>");
		$("#citySelect").val('');
		var cityMap = cityMaps[provinceCode];
		$.each(cityMap, function (key, value) {
			$("#citySelect").append("<option value='" + key + "'>" + value + "</option>");
		});
	} 		
}

function getDistrict(obj,districts){
	var cityCode = $(obj).val();
	if ($(obj).val() == '') {
		$("#selectDistrict").html('');
		$("#selectDistrict").append("<option value=''>--选择区--</option>");
		$("#selectDistrict").val('');
	}else{
		$("#selectDistrict").html('');
		$("#selectDistrict").append("<option value=''>--选择区--</option>");
		$("#selectDistrict").val('');
		$.each(districts, function (index,item) {
			if(item.bCityCode==cityCode){
				$("#selectDistrict").append("<option value='" + item.code + "'>" + item.name + "</option>");
			}
			
		});
	} 		
}
function addAddress(){
	var count = $('#addressTable').bootstrapTable('getData').length;
	$("#ceshi").bootstrapTable('insertRow', {
	    index: count,
	    row: {
	    	pca: '',
	    	shippingAddress: '',
	    	deliveryWay: '',
	    	deleteButton:''
	    }
	});
}

function salesTypeChange(obj,offices){
	var saleType = $(obj).val();
	if(saleType=="20"){
		$("#freightDiv").show();
		$('#citySelect').attr("disabled",true);
		$('#selectDistrict').attr("disabled",true);	
		$('#selectProvince').attr("disabled",true);		
	}else{
		$('#freightDiv').hide();
		$('#citySelect').attr("disabled",false);
		$('#selectDistrict').attr("disabled",false);
		$('#selectProvince').attr("disabled",false);	
	}
	if ($(obj).val() == '') {
		$("#officeSelect").html('');
		$("#officeSelect").append("<option value=''>--选择大区--</option>");
		$("#officeSelect").val('');
		
		$("#selectGroup").html('');
		$("#selectGroup").append("<option value=''>--选择中心--</option>");
		$("#selectGroup").val('');
	}else{
		$("#officeSelect").html('');
		$("#officeSelect").append("<option value=''>--选择大区--</option>");
		$("#officeSelect").val('');
		var officesMap = offices[saleType];
		$.each(officesMap, function (key,value) {
				$("#officeSelect").append("<option value='" + key + "'>" + value + "</option>");
			
		});
	} 		
}

function getGroups(obj,groups){
	var officeCode = $(obj).val();
	if ($(obj).val() == '') {
		$("#selectGroup").html('');
		$("#selectGroup").append("<option value=''>--选择中心--</option>");
		$("#selectGroup").val('');
	}else{
		$("#selectGroup").html('');
		$("#selectGroup").append("<option value=''>--选择中心--</option>");
		$("#selectGroup").val('');
		var groupsMap = groups[officeCode];
		$.each(groupsMap, function (key,value) {
				$("#selectGroup").append("<option value='" + key + "'>" + value + "</option>");			
		});
	} 		
}

function getExchangeRate(obj,currencies){
	var currencyCode = $(obj).val();
	if(currencyCode==''){
		$('#exchangeRate').val('');
		$("#contractAmount").val('');
		return
	}
	$.each(currencies, function (index,item) {
		if(item.code==currencyCode){
			$('#exchangeRate').val(item.rate);
			getAmount('#orignalContractAmount');
		}
		
	});
}

function getAmount(obj){
    var exchangeRate = $("#exchangeRate").val()
	var originalAmount = $(obj).val();
    var amount = exchangeRate*originalAmount
    $("#contractAmount").val(amount);
}

function queryUnitParams(params) {
    params.pageNo = this.pageNumber;
    params.customerName = $("#contractUnitName").val();
    params.clazzCode = $("#customerClazzCode").val();
    return params;
}	
var contractUnitTableColumn = [ {
	title:'选择',
    checkbox: true

},{
	title : '签约单位',
	field : 'name'
}, {
	title : '地址',
	field : 'address'
},{
	title : '性质分类',
	field : 'clazzName'
}]

function add(){
	$('#paymentModal').modal('show');
	$("#modalType").val("new");
	$("#paymentType").val('');
	$("#paymentTime").val('');
	$("#proportion").val('');
	$("#remark").val('');
	$("#budgetReturnAmount").val('');
	$("#originalBudgetReturnAmount").val('');
}

function confirmPayment(){
	var modalType = $("#modalType").val();
	var paymentType = $("#paymentType").find("option:selected").text();;
	var paymentTypeValue = $("#paymentType").val();
	var paymentTime = $("#paymentTime").val();
	var proportion = $("#proportion").val();
	var remark = $("#remark").val();
	var budgetReturnAmount = $("#budgetReturnAmount").val();
	var originalBudgetReturnAmount = $("#originalBudgetReturnAmount").val();
	var count = $('#paymentTable').bootstrapTable('getData').length;
	var rowIndex = $("#index").val()
	if(modalType=='new'){
		$("#paymentTable").bootstrapTable('insertRow', {
		    index: count,
		    row: {
		    	index:count+1,
		    	paymentType: paymentType,
		    	paymentTypeValue:paymentTypeValue,
		    	paymentTime: paymentTime,
		    	budgetReturnAmount: budgetReturnAmount,
		    	originalBudgetReturnAmount:originalBudgetReturnAmount,
		    	proportion:proportion+'%',
		    	remark:remark
		    }
		});
	}else{
		$("#paymentTable").bootstrapTable('updateRow', {
		    index: rowIndex,
		    row: {
		    	index:parseInt(rowIndex)+1,
		    	paymentType: paymentType,
		    	paymentTypeValue:paymentTypeValue,
		    	paymentTime: paymentTime,
		    	budgetReturnAmount: budgetReturnAmount,
		    	originalBudgetReturnAmount:originalBudgetReturnAmount,
		    	proportion:proportion+'%',
		    	remark:remark
		    }
		});
	}
	
	$('#paymentModal').modal('hide');
	getPaymentAreaContent();
}

function proportionChange(){
	var proportion = $("#proportion").val();
	var orignalContractAmount = $("#orignalContractAmount").val();
	var contractAmount = $("#contractAmount").val()
	var budgetReturnAmount = contractAmount*proportion;
	var originalBudgetReturnAmount = orignalContractAmount*proportion;
	$("#budgetReturnAmount").val(budgetReturnAmount/100);
	$("#originalBudgetReturnAmount").val(originalBudgetReturnAmount/100);
}

function removePayment(index){
	var delIndex = [parseInt(index)+1];
	$('#paymentTable').bootstrapTable('remove', {
        field: "index",
        values: delIndex
    });
	var count = $('#paymentTable').bootstrapTable('getData').length;
	for(var i=0;i<count;i++){
		var rows = {
				index: i,
				field : "index",
				value : i+1
			}
		$('#paymentTable').bootstrapTable("updateCell",rows);
	}
	getPaymentAreaContent();
	
}

function editPayment(index){
	var row = $('#paymentTable').bootstrapTable('getData')[index];	
	$('#paymentModal').modal('show');
	$("#index").val(index);
	$("#modalType").val("edit");
	$("#paymentType").val(row.paymentTypeValue);
	$("#paymentTime").val(row.paymentTime);
	$("#proportion").val(row.proportion);
	$("#remark").val(row.remark);
	$("#budgetReturnAmount").val(row.budgetReturnAmount);
	$("#originalBudgetReturnAmount").val(row.originalBudgetReturnAmount);
}

function getPaymentAreaContent(){
	var paymentTableData = $('#paymentTable').bootstrapTable('getData')
	if(paymentTableData.length==0){
		$("#paymentArea").text('');
	}else{
		var paymentAreaContent = '';
		$.each(paymentTableData, function(i, item) {
			paymentAreaContent = paymentAreaContent+"  行号"+item.index+";回款类型:"+item.paymentType+";"+"回款起始时间:"
			+item.paymentTime+";"+"预算回款金额:"+item.budgetReturnAmount+";"+"预算回款金额(原币)"+item.originalBudgetReturnAmount
			+";"+"所占比例:"+item.proportion+";"+"备注:"+item.remark+";";
		});
		$("#paymentArea").text(paymentAreaContent);
	}
	
}
function addSubsidiary(){
	$('#subsidiaryModal').modal('show');
}
function searchSpecification(){
	$('#specificationModal').modal('show');
}

var paymentColumns = [
{
	 field: 'index',
	 title: '行号',
	 width:'5%'
},
{
    field: 'paymentType',
    title: '回款类型',
    width:'15%'
},
{
	field:'paymentTypeValue',
	visible:false
},
{
    field: 'paymentTime',
    title: '回款起始时间',
    width:'10%'
},
{
    field: 'budgetReturnAmount',
    title: '预算回款金额',
    width:'10%'
},
{
    field: 'originalBudgetReturnAmount',
    title: '预算回款金额(原币)',
    width:'15%'
    	
},
{
    field: 'proportion',
    title: '所占比列',
    width:'10%'
},
{
    field: 'remark',
    title: '备注',
    width:'20%'
},
{
    title: '<input type="button"  value="+" class="btn btn-primary" onclick="add()"/>',
    align: 'center',
    width:'15%',
    formatter: function(value, row, index) {
    	var actions = [];
		actions.push('<a class="btn" onclick="editPayment(\'' + index + '\')"><i class="fa fa-edit"></i>编辑</a> ');
		actions.push('<a class="btn" onclick="removePayment(\'' + index + '\')"><i class="fa fa-remove"></i>删除</a>');
		return actions.join('');
    }
}];

//Customer basic infomation js end

var TableInit = function (id,url,params,tableColumns) {
	var oTableInit = new Object();
	var tableId = '#'+id;
	oTableInit.init = function () {
		$(tableId).bootstrapTable({
			method : 'get',
			url : url,//请求路径
			striped : true, //是否显示行间隔色
			toolbar: '#toolbar',
			cache: false,            
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    smartDisplay:false,
		    singleSelect: true,
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1, //初始化加载第一页
			sidePagination : 'server',//server:服务器端分页|client：前端分页
			pageSize : 50,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : params,
			columns : tableColumns
		})
	};
	return oTableInit;
};
















