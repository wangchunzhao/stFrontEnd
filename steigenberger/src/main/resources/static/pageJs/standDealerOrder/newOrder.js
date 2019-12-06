$(function () {
	$('#specificationModal').on('hidden.bs.modal', function () {
		$("body").addClass("modal-open");
	});
	$('#subsidiaryModal').on('hidden.bs.modal', function () {
		$("body").removeClass("modal-open");
	});
	localStorage.clear()
	var paymentTable = new TableInit('paymentTable','','',paymentColumns);
	paymentTable.init();
	
	var addressTable = new TableInit('addressTable','','',addressColumns)
	addressTable.init();
	//初始化客户查询Table
	var contractUnitTable = new TableInit('contractUnitTable','',queryUnitParams,contractUnitTableColumn);
	contractUnitTable.init();
	
	//初始化物料查询table
	var materialTypeTable = new TableInit('materialTypeTable','',queryMaterialTypeParams,materialTypeColumn)
	materialTypeTable.init();
	
	//初始化毛利率table
	var grossProfitTable = new TableInit("grossProfitTable",'','',grossProfitColumns);
	grossProfitTable.init();
	
	if(installationTerms){
		var installationTerm = installationTerms[$("#customerClazzCode").val()];
		$.each(installationTerm, function (key, value) {
			$("#installCode").val(key);
			$("#installName").val(value);
			
		});
	}
	initSubsidiartFormValidator();
    //initOrderFormValidator();
	initMarialsTables();
	$('#first').tab('show');
	$('#shippDate').datepicker();
	var nowDateString = moment(new Date()).format('YYYY-MM-DD');
	$("#inputDate").val(nowDateString);
	$("#optTime").val(nowDateString);
	defaultCollapse();
	if(orderModelType=="new"){
		getUserDetail();
	}
	if(orderModelType!="new"){
		//修改订单查看订单，回显购销明细数据
		fillItems();
		//修改查看订单时,辉县地址数据
		fillOrderAddress();
	}
});

//修改订单查看订单，回显购销明细数据
function fillItems(){
	if(items){
		for(var i=0;i<items.length;i++){
			var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
			var countMaterialsTableall1 = $('#materialsTableall1').bootstrapTable('getData').length;
			var countMaterialsTableall2 = $('#materialsTableall2').bootstrapTable('getData').length;
			var countMaterialsTableall3 = $('#materialsTableall3').bootstrapTable('getData').length;
			var countMaterialsTableall4 = $('#materialsTableall4').bootstrapTable('getData').length;
			var countMaterialsTableall5 = $('#materialsTableall5').bootstrapTable('getData').length;
			var countMaterialsTableall6 = $('#materialsTableall6').bootstrapTable('getData').length;
			var materialType = materialGroupMapGroupOrder[items[i].groupCode];
			if(materialType=='T101'){
				$("#materialsTableall1").bootstrapTable('insertRow', {
				    index: countMaterialsTableall1,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T102'){
				$("#materialsTableall2").bootstrapTable('insertRow', {
				    index: countMaterialsTableall2,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T103'){
				$("#materialsTableall3").bootstrapTable('insertRow', {
				    index: countMaterialsTableall3,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T104'){
				$("#materialsTableall4").bootstrapTable('insertRow', {
				    index: countMaterialsTableall4,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}else if(materialType=='T105'){
				$("#materialsTableall5").bootstrapTable('insertRow', {
				    index: countMaterialsTableall5,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: item[i]
				});
			}else if(materialType=='T106'){
				$("#materialsTableall6").bootstrapTable('insertRow', {
				    index: countMaterialsTableall6,
				    row: items[i]
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: items[i]
				});
			}
		}
	}
	
}

//修改查看订单时,辉县地址数据
function fillOrderAddress(){
	if(orderAddress){
		for(var i=0;i<orderAddress.length;i++){
			var row = orderAddress[i];
			row.index = i+1;
			$("#addressTable").bootstrapTable('insertRow', {
			    index: i,
			    row: orderAddress[i]
			});
		}
	}
}

//获取session中用户信息
function getUserDetail(){
	$.ajax({
	    url: "/steigenberger/order/user",
	    data: {},
	    type: "get",
	    success: function(data) {
	    	$("#salesName").val(data.userName);
	    	$("#salesTelnumber").val(data.tel);
	    	$("#salesCode").val(data.userIdentity);
	    }
	});
}
//设置面板折叠显示

function defaultCollapse(){
	$('#customerBasicInfo').collapse('show');
	$('#contractBasicInfomation').collapse('hide');
	$('#contractDetailInfo').collapse('hide');
	$('#researchTableContent').collapse('hide');
	$('#paymentMethod').collapse('hide');
	$('#purchaseDetail').collapse('hide');
	$('#infoCheck').collapse('show');
	$('#attachmentInfo').collapse('hide');
	
}


//Customer basic infomation js start 
function openSearchUnit(){
	$('#unitModal').modal('show');	
	var opt = {
				url: '/steigenberger/order/customers'
			   };
	$("#contractUnitTable").bootstrapTable('refresh', opt);
	$("#contractUnitTable").on('click-row.bs.table',function($element,row,field){
		$('#unitModal').modal('hide');
		$("#contractUnitName").val('');
		$("#contactorCode").val(row.code);
		$("#customer").val(row.name);
		$("#customerClazz").val(row.clazzName).change();
		$("#customerClazzName").val(row.clazzName);
	})
}

function queryUnitParams(params) {
    params.pageNo = this.pageNumber;
    params.customerName = $("#contractUnitName").val();
    params.clazzCode = $("#customerClazzCode").val();
    return params;
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

function salesTypeChange(obj,offices,taxRate,exchangeRate){
	$("#orignalContractAmount").val("");
	$("#exchangeRate").val("");
	$("#contractAmount").val("");
	var saleType = $(obj).val();
	getCurrency(saleType,exchangeRate);
	getIncoterm(saleType);
	$("#taxtRate").val(taxRate[saleType]);
	if(saleType=="20"){
		$("#freightDiv").show();
		$('#selectProvince').val('');
		$('#citySelect').val('');
		$('#selectDistrict').val('');
		$('#citySelect').attr("disabled",true);
		$('#selectDistrict').attr("disabled",true);	
		$('#selectProvince').attr("disabled",true);
		$('#currency').attr('disabled',false);
		$('#incoterm').attr("readonly",false);
		$('#incotermContect').attr("readonly",false);
		$('#installCode').val('');
		$('#installCode').attr("readonly",true);
		$('#transferType').attr("disabled",true);
		$('#contactor1Id').attr("readonly",true);
		$('#contactor2Id').attr("readonly",true);
		$('#contactor3Id').attr("readonly",true);
		$('#contactor1Tel').attr("readonly",true);
		$('#contactor2Tel').attr("readonly",true);
		$('#contactor3Tel').attr("readonly",true);
		$('#contactor1Id').val('');
		$('#contactor2Id').val('');
		$('#contactor3Id').val('');
		$('#contactor1Tel').val('');
		$('#contactor2Tel').val('');
		$('#contactor3Tel').val('');
	}else{
		$('#freightDiv').hide();
		$('#citySelect').attr("disabled",false);
		$('#selectDistrict').attr("disabled",false);
		$('#selectProvince').attr("disabled",false);
		
		$('#currency').attr('disabled',true);
		$('#incoterm').attr("readonly",true);
		$('#incotermContect').attr("readonly",true);
		
		$('#installCode').attr("readonly",false);
		$('#transferType').attr("readonly",false);
		$('#contactor1Id').attr("readonly",false);
		$('#contactor2Id').attr("readonly",false);
		$('#contactor3Id').attr("readonly",false);
		$('#contactor1Tel').attr("readonly",false);
		$('#contactor2Tel').attr("readonly",false);
		$('#contactor3Tel').attr("readonly",false);
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
		$("#selectGroup").html('');
		if($(obj).val() == '10'||$(obj).val() == '30'){
			$("#officeSelect").attr("readonly",false);
			$("#selectGroup").attr("readonly",false);
			$("#officeSelect").append("<option value=''>--选择大区--</option>");
			$("#selectGroup").append("<option value=''>--选择中心--</option>");
		}else{
			$("#officeSelect").attr("readonly",true);
			$("#selectGroup").attr("readonly",true);
		}	
		$("#officeSelect").val('');
		var officesMap = offices[saleType];
		$.each(officesMap, function (key,value) {
				$("#officeSelect").append("<option value='" + key + "' readonly>" + value + "</option>");			
		});
	} 		
}

function getIncoterm(salesType){
	$("#incoterm").html('');
	if(salesType=='20'){
		$.each(intercoms, function (key,value) {
			$("#incoterm").append("<option value=" + key +">" + value + "</option>");			
		});
	}
}
function getCurrency(saleType,exchangeRates){
	$("#currency").html("");
	if(saleType==''){
		$("#currency").html("");
	}else if(saleType=='20'){
		$("#currency").append("<option value=''>--选择币种--</option>");
		var currency = exchangeRates[saleType];
		$.each(currency, function (index,item) {
			$("#currency").append("<option value=" + item.code + ">" + item.name + "</option>");			
		});
	}else{
		var currency = exchangeRates[saleType];
		$.each(currency, function (index,item) {
			$("#currency").append("<option value=" + item.code + ">" + item.name + "</option>");
			$("#exchangeRate").val(item.rate);
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
	var salesType = $("#salesType").val();
	var currency = currencies[salesType];
	$.each(currency, function (index,item) {
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
    $("#contractAmount").val(toDecimal2(amount)).change();
}

function queryMaterialTypeParams(params) {
    params.pageNo = this.pageNumber;
    params.materialName = $("#materialsName").val();
    return params;
}


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
	var paymentType = $("#paymentType").find("option:selected").text();
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

//打开物料规格查询框
function addSubsidiary(){
		$('#subsidiaryModal').modal('show');
		$("#subsidiaryForm")[0].reset();
		$('#amount').val(1);
		$('#discount').val($("#approvedDicount").val());
		var opt = {
			url: "/steigenberger/order/materials"
		};
		$("#materialTypeTable").bootstrapTable('refresh', opt);
		$("#materialTypeTable").on('click-row.bs.table',function($element,row,field){
			$('#specificationModal').modal('hide');
			getMaterialInfo(row.code);
			$("#materialTypeName").val(row.description);
			$("#materialCode").val(row.code);
			if(row.isPurchased=='0'){
				$('#isPurchased').val('生产');
			}else{
				$('#isPurchased').val('采购');
			}
			$('#unitName').val(row.unitName);
		})
	$('#materialsModalType').val('new');
}

//点击查询出来的物料记录
function getMaterialInfo(code){	
	$.ajax({
	    url: "/steigenberger/order/material",
	    data: {code: code},
	    type: "POST",
	    dataType: "json",
	    success: function(data) {
	    	if(data){
	    		$("#confirmMaterial").attr("disabled",false);
	    		fillMaterailValue(data); 	   
	    	}else{
	    		alert("未查询到物料详细信息无法添加该物料！")
		    	$("#confirmMaterial").attr("disabled",true);
	    	}      
	    }
	});
}
//将查出来的物料信息填充到各个field中
function fillMaterailValue(data){
	if(data.materialName){
		$("#materialTypeName").val(data.materialName);
	}
	if(data.materialCode){
		$("#materialCode").val(data.materialCode);
	}
	if(data.b2cRemark){
		$("#b2cRemark").val(data.b2cRemark);
	}
	if(data.colorComments){
		$("#colorComments").val(data.colorComments);
	}
	if(data.specialRemark){
		$("#specialRemark").val(data.specialRemark);
	}
	if(data.itemCategory){
		$("#itemCategory").val(data.itemCategory);
	}
	if(data.purchased){
		$("#purchasePeriod").val(data.period);
	}else{	
		$("#producePeriod").val(data.period);
	}
	var groupName = data.groupName;
	$("#materialGroupName").val(data.groupName);
	$("#groupCode").val(data.groupCode);
	$("#isConfigurable").val(data.configurable);
	var materialsType = materialGroupMapGroupOrder[data.groupCode];
	var amount = $("#amount").val();
	$("#materialsType").val(materialsType);
	$("#unitName").val(data.unitName);
	$("#unitName").val(data.unitName);
	$("#materialClazzCode").val(data.clazzCode);
	$("#transcationPrice").val(toDecimal2(data.transcationPrice));
	$("#acturalPricaOfOptional").val(toDecimal2(data.acturalPricaOfOptional));
	$("#acturalPricaOfOptionalAmount").val(toDecimal2(amount*(data.acturalPricaOfOptional)));
	$("#transcationPriceOfOptional").val(toDecimal2(data.transcationPriceOfOptional));
	$("#B2CPriceEstimated").val(toDecimal2(data.b2CPriceEstimated));
	$("#B2CPriceEstimatedAmount").val(toDecimal2((data.b2CPriceEstimated)*amount));
	$("#B2CCostOfEstimated").val(toDecimal2(data.b2CCostOfEstimated));
	$("#transcationPriceTotal").val(toDecimal2(parseFloat($("#transcationPrice").val())+parseFloat($("#transcationPriceOfOptional").val())));
	$("#retailPrice").val(toDecimal2(data.retailPrice));
	$("#retailPriceAmount").val(toDecimal2(amount*(data.retailPrice)));
	var discountValue = $("#discount").val();
	var discount = discountValue.split("%")[0];
	var acturalPrice = (data.retailPrice*discount)
	$("#acturalPrice").val(toDecimal2(acturalPrice));
	$("#acturalPriceAmount").val(toDecimal2(amount*(acturalPrice)));
	$("#acturalPriceTotal").val(toDecimal2(parseFloat($("#acturalPrice").val())+parseFloat($("#acturalPricaOfOptional").val())));
	$("#acturalPriceAmountTotal").val(toDecimal2(($("#acturalPriceTotal").val())*amount));
	if(data.purchased){
		$("#producePeriod").val(data.period);
	}else{
		$("#purchasePeriod").val(data.period);
	}
	$("#deliveryDate").val(data.deliveryDate);
	$("#produceDate").val(data.produceDate);
	$("#onStoreDate").val(data.onStoreDate);
	$("#standardPrice").val(toDecimal2(data.standardPrice));
	
	
}

//数量变化
function amountChange(){
	var amount = $("#amount").val();
	if(amount==''){
		return;
	}
	$("#acturalPriceAmount").val(toDecimal2(amount*(parseFloat($("#acturalPrice").val()))));
	$("#acturalPricaOfOptionalAmount").val(toDecimal2(amount*(parseFloat($("#acturalPricaOfOptional").val()))));
	$("#B2CPriceEstimatedAmount").val(toDecimal2(amount*(parseFloat($("#B2CPriceEstimated").val()))));
	$("#retailPriceAmount").val(toDecimal2(parseFloat($("#retailPrice").val())*amount));
	$("#acturalPriceAmountTotal").val(toDecimal2(parseFloat($("#acturalPriceTotal").val())*amount));
}

function toDecimal2(x) {   
     var f = parseFloat(x);   
     if (isNaN(f)) {   
      return 0;   
     }   
     var f = Math.round(x*100)/100;   
     var s = f.toString();   
     var rs = s.indexOf('.');   
    if (rs < 0) {   
     rs = s.length;   
     s += '.';   
    }   
   while (s.length <= rs + 2) {   
     s += '0';   
   } 
   return s;
 }  
//编辑购销明细
function editMaterials(identification){
	$('#subsidiaryModal').modal('show');
	$('#materialsModalType').val('edit');
	var identificationSplit = identification.split('|');
	var materialsType = identificationSplit[1];
	var index = identificationSplit[0];
	var tableData;
	if(materialsType=='T101'){
		tableData = $('#materialsTableall1').bootstrapTable('getData')[index];
	}else if(materialsType=='T102'){
		tableData = $('#materialsTableall2').bootstrapTable('getData')[index];
	}else if(materialsType=='T103'){
		tableData = $('#materialsTableall3').bootstrapTable('getData')[index];
	}else if(materialsType=='T104'){
		tableData = $('#materialsTableall4').bootstrapTable('getData')[index];
	}else if(materialsType=='T105'){
		tableData = $('#materialsTableall5').bootstrapTable('getData')[index];
	}else if(materialsType=='T106'){
		tableData = $('#materialsTableall6').bootstrapTable('getData')[index];
	}
	
	fillMaterailValue(tableData);
}
//删除购销明细
function removeMaterials(identification){
	$('#materialsTable').bootstrapTable('remove', {
        field: "identification",
        values: identification
    });
	var count = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<count;i++){
		var rows = {
				index: i,
				field : "index",
				value : i+1
			}
		$('#materialsTable').bootstrapTable("updateCell",rows);
	}
	removeRelatedRow(identification);
	getAllCountFiled();
}
//删除其他tab相同的行
function removeRelatedRow(identification){
	var identificationSplit = identification.split('|');
	var type = identificationSplit[1];
	if(type=='T101'){
		$('#materialsTableall1').bootstrapTable('remove', {
	        field: "identification",
	        values: identification
	    });
		var count = $('#materialsTableall1').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "index",
					value : i+1
				}
			$('#materialsTableall1').bootstrapTable("updateCell",rows);
		}
	}else if(type=='T102'){
		$('#materialsTableall2').bootstrapTable('remove', {
	        field: "identification",
	        values: identification
	    });
		var count = $('#materialsTableall2').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "index",
					value : i+1
				}
			$('#materialsTableall2').bootstrapTable("updateCell",rows);
		}
	}else if(type=='T103'){
		$('#materialsTableall3').bootstrapTable('remove', {
	        field: "identification",
	        values: identification
	    });
		var count = $('#materialsTableall3').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "index",
					value : i+1
				}
			$('#materialsTableall3').bootstrapTable("updateCell",rows);
		}
	}else if(type=='T104'){
		$('#materialsTableall4').bootstrapTable('remove', {
	        field: "identification",
	        values: identification
	    });
		var count = $('#materialsTableall4').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "index",
					value : i+1
				}
			$('#materialsTableall4').bootstrapTable("updateCell",rows);
		}
	}else if(type=='T105'){
		$('#materialsTableall5').bootstrapTable('remove', {
	        field: "identification",
	        values: identification
	    });
		var count = $('#materialsTableall5').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "index",
					value : i+1
				}
			$('#materialsTableall5').bootstrapTable("updateCell",rows);
		}
	}else if(type=='T106'){
		$('#materialsTableall6').bootstrapTable('remove', {
	        field: "identification",
	        values: identification
	    });
		var count = $('#materialsTableall6').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "index",
					value : i+1
				}
			$('#materialsTableall6').bootstrapTable("updateCell",rows);
		}
	}
}

function initSubsidiartFormValidator(){
	 $('#subsidiaryForm').bootstrapValidator({
	　　　　　　　　message: 'This value is not valid',
		            fields: {
		            	amount: {
		            		validators: {
		            			notEmpty: {
		            	            message: '数量不能为空'
		            	        },
		            	        regexp: {
		            	            regexp: /^[1-9]\d{0,4}$/,
		            	            message: '只能输入小于十万的正整数'
		            	        }
		            	    }
		                },
		               itemRequirementPlan: {
	                    validators: {
	                        notEmpty: {
	                            message: '请选择需求计划'
	                        }
	                    }
		               },
		               itemCategory: {
		                    validators: {
		                        notEmpty: {
		                            message: '请选择行项目类别'
		                        }
		                    }
			           }
	            }
	        });
}

function initOrderFormValidator(){
    $('#orderForm').bootstrapValidator({
        message: 'This value is not valid',
        fields: {
        	salesTelnumber: {
        	    validators: {
        	    	regexp: {
        	    		regexp: /^1[3456789]\d{9}$/,
        	            message: '不是合法的手机号'
        	        }
        	    }
        	},
        	saleType:{
        	    validators: {
        	    	 notEmpty: {
                         message: '请选择销售类型'
                    }
        	    }
        	},
        	recordCode: {
                validators: {
                    notEmpty: {
                         message: '请填写项目编号'
                    }
                }
            },
        	customerName: {
                validators: {
                    notEmpty: {
                         message: '请填写店名'
                    }
                }
            },
            officeCode: {
                validators: {
                    notEmpty: {
                         message: '请选择大区'
                    }
                }
            },
            groupCode: {
                validators: {
                    notEmpty: {
                         message: '请选择中心'
                    }
                }
            },
            contactor1Id: {
            	validators: {
        	    	regexp: {
        	    		regexp: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
        	            message: '请填写正确的身份证号'
        	        }
        	    }
            },
            contactor2Id: {
            	validators: {
        	    	regexp: {
        	    		regexp: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
        	            message: '请填写正确的身份证号'
        	        }
        	    }
            },
            contactor3Id: {
            	validators: {
        	    	regexp: {
        	    		regexp: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
        	            message: '请填写正确的身份证号'
        	        }
        	    }
            },
            contactor1Tel: {
            	validators: {
        	    	regexp: {
        	    		regexp: /^1[3456789]\d{9}$/,
        	            message: '请填写正确的手机号'
        	        }
        	    }
            },
            contactor2Tel: {
            	validators: {
        	    	regexp: {
        	    		regexp: /^1[3456789]\d{9}$/,
        	            message: '请填写正确的手机号'
        	        }
        	    }
            },
            contactor3Tel: {
            	validators: {
        	    	regexp: {
        	    		regexp: /^1[3456789]\d{9}$/,
        	            message: '请填写正确的手机号'
        	        }
        	    }
            },
            contractorclassname: {
            	trigger:"change",
                validators: {
                    notEmpty: {
                         message: '请选择签约单位'
                    }
                }
            },
        	contractValue: {
                validators: {
                    notEmpty: {
                         message: '金额不能为空'
                    },
                    regexp: {
                        regexp: /^\d+(\.\d{0,2})?$/,
                        message: '请输入合法的金额，金额限制两位小数'
                    }
                }
            },
			contractRMBValue: {
				trigger:"change",
				validators: {
					identical: {
						field: 'itemsAmount',
						message: '合同明细金额和购销明细金额不一致，请验证后再提交！'
					}
				}
			},
			itemsAmount: {
			}
        }
    });
}

//确认购销明细modal
function confirmMaterials(){
	var bootstrapValidator = $("#subsidiaryForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
    	return
    }
	var modalType = $("#materialsModalType").val();
	var materialType = $("#materialsType").val();
	var rowIndex = $("#materialsIndex").val();
	var materialTypeName = $("#materialTypeName").val();
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	var countMaterialsTableall1 = $('#materialsTableall1').bootstrapTable('getData').length;
	var countMaterialsTableall2 = $('#materialsTableall2').bootstrapTable('getData').length;
	var countMaterialsTableall3 = $('#materialsTableall3').bootstrapTable('getData').length;
	var countMaterialsTableall4 = $('#materialsTableall4').bootstrapTable('getData').length;
	var countMaterialsTableall5 = $('#materialsTableall5').bootstrapTable('getData').length;
	var countMaterialsTableall6 = $('#materialsTableall6').bootstrapTable('getData').length;
	if(modalType=='new'){
		if(materialType=='T101'){
			var rowData = confirmRowData(countMaterialsTableall1,'');
			$("#materialsTableall1").bootstrapTable('insertRow', {
			    index: countMaterialsTableall1,
			    row: rowData
			});
			$("#second").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T102'){
			var rowData = confirmRowData(countMaterialsTableall2,'');
			$("#materialsTableall2").bootstrapTable('insertRow', {
			    index: countMaterialsTableall2,
			    row: rowData
			});
			$("#third").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T103'){
			var rowData = confirmRowData(countMaterialsTableall3,'');
			$("#materialsTableall3").bootstrapTable('insertRow', {
			    index: countMaterialsTableall3,
			    row: rowData
			});
			$("#four").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T104'){
			var rowData = confirmRowData(countMaterialsTableall4,'');
			$("#materialsTableall4").bootstrapTable('insertRow', {
			    index: countMaterialsTableall4,
			    row: rowData
			});
			$("#five").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T105'){
			var rowData = confirmRowData(countMaterialsTableall5,'');
			$("#materialsTableall5").bootstrapTable('insertRow', {
			    index: countMaterialsTableall5,
			    row: rowData
			});
			$("#six").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T106'){
			var rowData = confirmRowData(countMaterialsTableall6,'');
			$("#materialsTableall6").bootstrapTable('insertRow', {
			    index: countMaterialsTableall6,
			    row: rowData
			});
			$("#seven").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}
	}else{
		$("#materialsTable").bootstrapTable('updateRow', {
		    index: rowIndex,
		    row: {
		    	index:parseInt(rowIndex)+1,
		    	materialTypeName:materialTypeName
		    }
		});
	}
	
	$('#subsidiaryModal').modal('hide');
	//计算最早发货时间，最早出货时间，购销明细合计
	getAllCountFiled();
}

function getAllCountFiled(){
	var tableData = $('#materialsTable').bootstrapTable('getData');
	//工厂最早交货时间
	var deliveryTime=[];
	//要求发货时间
	var  requiredDeliveryTime=[];
	//购销明细金额
	var itemsAmount = [];
	$.each(tableData,function(index,item){
		if(item.deliveryDate){
			deliveryTime.push(moment(item.deliveryDate));
		}
		if(item.shippDate){
			requiredDeliveryTime.push(moment(item.shippDate));
		}
		if(item.acturalPriceAmountTotal){
			itemsAmount.push(item.acturalPriceAmountTotal);
		}
	})
	var totalAmount = toDecimal2(calculationAmount(itemsAmount));
	$("#itemsAmount").val(totalAmount);	
	var earlyRequiredDeliveryTime = compareDate(requiredDeliveryTime);
	if(earlyRequiredDeliveryTime){
		$("#requiredDeliveryTime").val(moment(earlyRequiredDeliveryTime).format('YYYY-MM-DD'));
	}else{
		$("#requiredDeliveryTime").val('');
	}	
	var earlyDeliveryTime = compareDate(deliveryTime);
	if(earlyDeliveryTime){
		$("#deliveryTime").val(moment(earlyDeliveryTime).format('YYYY-MM-DD'));
	}else{
		$("#deliveryTime").val('');
	}
	
}

//计算购销明细金额
function calculationAmount(amount){
	var totalAmount=0.00;
	if(amount.length!=0){	
		$.each(amount,function(index,item){
			totalAmount+=parseFloat(item);
		})	
	}
	return totalAmount;
}
//时间比较

function compareDate(date){
	if(date.length==0){
		return '';
	}
	var earlyDate = date[0];
	$.each(date,function(index,item){
		if(moment(item).isBefore(earlyDate)){
			earlyDate = item;
		}
	})
	return earlyDate;
}
//购销明细行数据

function confirmRowData(index,identification){
	var idenf = identification
	if(idenf==''){
		idenf=index+"|"+$("#materialsType").val()
	}
	var row = {
			rowNumber:(index+1)*10,
			materialName:$("#materialTypeName").val(),
			materialCode:$("#materialCode").val(),
			identification:idenf,
			clazzCode:$("#materialClazzCode").val(),
			configurable:$("#isConfigurable").val(),
			purchased:$("#purchasedCode").val(),
			groupName:$("#materialGroupName").val(),
			groupCode:$("#groupCode").val(),
			quantity:$("#amount").val(),
			unitName:$("#unitName").val(),
			standardPrice:$("#standardPrice").val(),
			acturalPrice:$("#acturalPrice").val(),
			acturalPriceAmount:$("#acturalPriceAmount").val(),
			transcationPrice:$("#transcationPrice").val(),
			acturalPricaOfOptional:$("#acturalPricaOfOptional").val(),
			acturalPricaOfOptionalAmount:$("#acturalPricaOfOptionalAmount").val(),
			transcationPriceOfOptional:$("#transcationPriceOfOptional").val(),
			B2CPriceEstimated:$("#B2CPriceEstimated").val(),
			B2CPriceEstimatedAmount:$("#B2CPriceEstimatedAmount").val(),
			B2CCostOfEstimated:$("#B2CCostOfEstimated").val(),
			acturalPriceTotal:$("#acturalPriceTotal").val(),
			acturalPriceAmountTotal:$("#acturalPriceAmountTotal").val(),
			transcationPriceTotal:$("#transcationPriceTotal").val(),
			retailPrice:$("#retailPrice").val(),
			retailPriceAmount:$("#retailPriceAmount").val(),
			discount:$("#discount").val(),
			itemCategory:$("#itemCategory").val(),
			itemRequirementPlan:$("#itemRequirementPlan").val(),
			producePeriod:$("#producePeriod").val(),
			deliveryDate:$("#deliveryDate").val(),
			produceDate:$("#produceDate").val(),
			shippDate:$("#shippDate").val(),
			materialAddress:$("#materialAddress").val(),
			onStoreDate:$("#onStoreDate").val(),
			purchasePeriod:$("#purchasePeriod").val(),
			b2cComments:$("#b2cRemark").val(),
			specialComments:$("#specialRemark").val(),
			colorComments:$("#colorComments").val()
	}
	return row;
}

//打开规格查询框
function searchSpecification(){
	$('#specificationModal').modal('show');
}

//查询规格
function searchMaterilType(){
	$('#materialTypeTable').bootstrapTable('refresh');
}


//添加物料地址
function addMaterialAddress(){
	$('#materialAddressTable').bootstrapTable('removeAll');
	$('#materialAddressModal').modal('show');
	var materialAddressTable = new TableInit('materialAddressTable','','',materialsAddressColumns);
	materialAddressTable.init();
	var addressTableData = $('#addressTable').bootstrapTable('getData')
	for(var i=0;i<addressTableData.length;i++){
		var addressTableRow = addressTableData[i];
		$("#materialAddressTable").bootstrapTable('insertRow', {
		    index: i,
		    row: addressTableRow
		});
	}
	$("#materialAddressTable").on('click-row.bs.table',function($element,row,field){
		$('#materialAddressModal').modal('hide');
		if(row.pca==''){
			$("#materialAddress").val(row.address);
		}else{
			$("#materialAddress").val(row.pca+"/"+row.address);
		}
		
	})
}

//打开调研表
function openConfig(identification){
	$("#materialconfigModal").modal('show');
	var value = identification.split(',')
	$("#materialConfigClazzCode").val(value[2]);
	$("#materialConfigCode").val(value[1]);
	$("#identification").val(value[0]);
	$("#viewPrice").val(value[3]);
	var url = "/steigenberger/order/material/configurations"
	var configTable = new TableInit('configTable','','',configTableColumns);
	configTable.init();
	var configData = localStorage[value[0]];
	if(configData){
		$("#configTable").bootstrapTable("removeAll");
		var jsonObject = JSON.parse(configData);
		for(var i=0;i<jsonObject.configTableData.length;i++){
			$("#configTable").bootstrapTable('insertRow',{
				index:i,
				row:jsonObject.configTableData[i]
			});
		}
		$("#configRemark").val(jsonObject.remark);
		
	}else{
		$("#configTable").bootstrapTable('refresh',{
			url:url,
			query:{'clazzCode':$("#materialConfigClazzCode").val(),
				   'materialCode':$("#materialConfigCode").val()
			}
		});
	}
}
//调研表初始化查询参数
function queryConfigParams(params) {
    params.clazzCode = $("#materialConfigClazzCode").val();
    params.materialCode = $("#materialConfigCode").val();
    return params;
}

//还原标准配置
function resetStandardConfiguration(){
	$("#configTable").bootstrapTable('refresh',{
		url:'/steigenberger/order/material/configurations',
		query:{'clazzCode':$("#materialConfigClazzCode").val(),
			   'materialCode':$("#materialConfigCode").val()
		}
	});
}

//关闭调研表
function closeMaterialConfig(){
	$("#materialconfigModal").modal('hide');
}

//保存调研表
function saveMaterialConfig(){
	var identification = $("#identification").val();
	var configData = new Object();
	var remark = $("#configRemark").val();
	var configTableData = $("#configTable").bootstrapTable('getData');
	configData.configTableData = configTableData;
	configData.remark = remark
	localStorage.setItem(identification, JSON.stringify(configData));
	$("#materialconfigModal").modal('hide');
}
//复制调研表
function copyMaterials(identification){
	var identificationSplit = identification.split('|');
	var configsData = localStorage[identification];
	var materialsType = identificationSplit[1];
	var index = identificationSplit[0];
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	if(materialsType=='T101'){
		var countMaterialsTableall1 = $('#materialsTableall1').bootstrapTable('getData').length;
		var materialsTableall1Data = $('#materialsTableall1').bootstrapTable('getData')[index];
		var rowData = getRowData(materialsTableall1Data,materialsType,countMaterialsTableall1);
		$("#materialsTableall1").bootstrapTable('append',  rowData);
		var rowDataAll = getRowData(materialsTableall1Data,materialsType,countMaterialsTable);
		$("#materialsTable").bootstrapTable('append', rowDataAll);
		if(configsData){
			localStorage.setItem(rowData.identification,configsData);
		}
	}else if(materialsType=='T102'){
		var countMaterialsTableall2 = $('#materialsTableall2').bootstrapTable('getData').length;
		var materialsTableall2Data = $('#materialsTableall2').bootstrapTable('getData')[index];
		var rowData = getRowData(materialsTableall2Data,materialsType,countMaterialsTableall2);
		$("#materialsTableall1").bootstrapTable('append',  rowData);
		$("#materialsTableall2").bootstrapTable('insertRow', {
		    index: countMaterialsTableall2,
		    row: materialsTableall2Data
		});
		var rowDataAll = getRowData(materialsTableall2Data,materialsType,countMaterialsTable);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:materialsTableall2Data
		});
		if(configsData){
			localStorage.setItem(materialsTableall2Data.identification,configsData);
		}
	}else if(materialsType=='T103'){
		var countMaterialsTableall3 = $('#materialsTableall3').bootstrapTable('getData').length;
		var materialsTableall3Data = $('#materialsTableall3').bootstrapTable('getData')[index];
		var rowData = getRowData(materialsTableall3Data,materialsType,countMaterialsTableall3);
		$("#materialsTableall3").bootstrapTable('insertRow', {
		    index: countMaterialsTableall3,
		    row: rowData
		});
		var rowDataAll = getRowData(materialsTableall3Data,materialsType,countMaterialsTable);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
		if(configsData){
			localStorage.setItem(rowData.identification,configsData);
		}
	}else if(materialsType=='T104'){
		var countMaterialsTableall4 = $('#materialsTableall4').bootstrapTable('getData').length;
		var materialsTableall4Data = $('#materialsTableall4').bootstrapTable('getData')[index];
		var rowData = getRowData(materialsTableall4Data,materialsType,countMaterialsTableall4);
		$("#materialsTableall4").bootstrapTable('insertRow', {
		    index: countMaterialsTableall4,
		    row: rowData
		});
		var rowDataAll = getRowData(materialsTableall4Data,materialsType,countMaterialsTable);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
		if(configsData){
			localStorage.setItem(rowData.identification,configsData);
		}
	}else if(materialsType=='T105'){
		var countMaterialsTableall5 = $('#materialsTableall5').bootstrapTable('getData').length;
		var materialsTableall5Data = $('#materialsTableall5').bootstrapTable('getData')[index];
		var rowData = getRowData(materialsTableall5Data,materialsType,countMaterialsTableall5);
		$("#materialsTableall5").bootstrapTable('insertRow', {
		    index: countMaterialsTableall5,
		    row: rowData
		});
		var rowDataAll = getRowData(materialsTableall5Data,materialsType,countMaterialsTable);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
		if(configsData){
			localStorage.setItem(rowData.identification,configsData);
		}
	}else if(materialsType=='T106'){
		var countMaterialsTableall6 = $('#materialsTableall6').bootstrapTable('getData').length;
		var materialsTableall6Data = $('#materialsTableall6').bootstrapTable('getData')[index];
		var rowData = getRowData(materialsTableall6Data,materialsType,countMaterialsTableall6);
		$("#materialsTableall6").bootstrapTable('insertRow', {
		    index: countMaterialsTableall6,
		    row: rowData
		});
		var rowDataAll = getRowData(materialsTableall6Data,materialsType,countMaterialsTable);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
		if(configsData){
			localStorage.setItem(rowData.identification,configsData);
		}
	}
}

//复制调研表时获取行数据
function getRowData(materialsTableData,type,newIndex){
	var identification = newIndex+'|'+type;
	var rowNumber = (newIndex+1)*10;
	var row = {
			rowNumber:rowNumber,
			materialName:materialsTableData.materialName,
			materialCode:materialsTableData.materialCode,
			identification:identification,
			clazzCode:materialsTableData.clazzCode,
			isConfigurable:materialsTableData.isConfigurable,
			isPurchased:materialsTableData.isPurchased,
			groupName:materialsTableData.groupName,
			groupCode:materialsTableData.groupCode,
			amount:materialsTableData.amount,
			unitName:materialsTableData.unitName,
			standardPrice:materialsTableData.standardPrice,
			acturalPrice:materialsTableData.acturalPrice,
			acturalPriceAmount:materialsTableData.acturalPriceAmount,
			transcationPrice:materialsTableData.transcationPrice,
			acturalPricaOfOptional:materialsTableData.acturalPricaOfOptional,
			acturalPricaOfOptionalAmount:materialsTableData.acturalPricaOfOptionalAmount,
			transcationPriceOfOptional:materialsTableData.transcationPriceOfOptional,
			B2CPriceEstimated:materialsTableData.B2CPriceEstimated,
			B2CPriceEstimatedAmount:materialsTableData.B2CPriceEstimatedAmount,
			B2CCostOfEstimated:materialsTableData.B2CCostOfEstimated,
			acturalPriceTotal:materialsTableData.acturalPriceTotal,
			acturalPriceAmountTotal:materialsTableData.acturalPriceAmountTotal,
			transcationPriceTotal:materialsTableData.transcationPriceTotal,
			retailPrice:materialsTableData.retailPrice,
			retailPriceAmount:materialsTableData.retailPriceAmount,
			discount:materialsTableData.discount,
			itemCategory:materialsTableData.itemCategory,
			itemRequirementPlan:materialsTableData.itemRequirementPlan,
			producePeriod:materialsTableData.producePeriod,
			deliveryDate:materialsTableData.deliveryDate,
			produceDate:materialsTableData.produceDate,
			shippDate:materialsTableData.shippDate,
			materialAddress:materialsTableData.materialAddress,
			onStoreDate:materialsTableData.onStoreDate,
			purchasePeriod:materialsTableData.purchasePeriod,
			b2cRemark:materialsTableData.b2cRemark,
			specialRemark:materialsTableData.specialRemark,
			colorComments:materialsTableData.colorComments
	}
	
	return row;
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function confirmAddress(){
	var province = $("#selectProvince").find("option:selected").text();
	var provinceValue = $("#selectProvince").val();
	var city = $("#citySelect").find("option:selected").text();
	var cityValue = $("#citySelect").val();
	var area = $("#selectDistrict").find("option:selected").text();
	var areaValue = $("#selectDistrict").val();
	var pca = ''
	if(provinceValue!=''){
		pca = province;
		if(cityValue!=''){
			pca+="/"+city
		}
		if(areaValue!=''){
			pca+="/"+area
		}
	}
	var shippingAddress = $("#shippingAddress").val();
	var addressModalType = $("#addressModalType").val();
	var count = $('#addressTable').bootstrapTable('getData').length;
	var rowIndex = $("#addressIndex").val()
	if(addressModalType=='new'){
		$("#addressTable").bootstrapTable('insertRow', {
		    index: count,
		    row: {
		    	index:count+1,
		    	pca: pca,
		    	provinceValue:provinceValue,
		    	cityValue:cityValue,
		    	areaValue:areaValue,
		    	address:shippingAddress
		    }
		});
	}else{
		$("#addressTable").bootstrapTable('updateRow', {
		    index: rowIndex,
		    row: {
		    	index:parseInt(rowIndex)+1,
		    	pca: pca,
		    	provinceValue:provinceValue,
		    	cityValue:cityValue,
		    	areaValue:areaValue,
		    	address:shippingAddress
		    }
		});
	}
	$("#addressModal").modal('hide');
}

function initMarialsTables(){
	var materialsTable = new TableInit('materialsTable','','',materialsColumn);
	materialsTable.init();
	var materialsTableall1 = new TableInit('materialsTableall1','','',materialsColumn);
	materialsTableall1.init();
	var materialsTableall2 = new TableInit('materialsTableall2','','',materialsColumn);
	materialsTableall2.init();
	var materialsTableall3 = new TableInit('materialsTableall3','','',materialsColumn);
	materialsTableall3.init();
	var materialsTableall4 = new TableInit('materialsTableall4','','',materialsColumn);
	materialsTableall4.init();
	var materialsTableall5 = new TableInit('materialsTableall5','','',materialsColumn);
	materialsTableall5.init();
	var materialsTableall6 = new TableInit('materialsTableall6','','',materialsColumn);
	materialsTableall6.init();
}

function addAddress(){
	$("#addressModal").modal('show');
	$("#addressModalType").val('new');
	$("#selectProvince").val('');
	$("#citySelect").val('');
	$("#selectDistrict").val('');
	$("#shippingAddress").val('');
}


function editAddress(index){
	var row = $('#addressTable').bootstrapTable('getData')[index];
	$("#addressIndex").val(index);
	$("#addressModalType").val("edit");
	$("#addressModal").modal('show');
	$("#selectProvince").val(row.provinceValue);
	$("#citySelect").val(row.cityValue);
	$("#selectDistrict").val(row.areaValue);
	$("#shippingAddress").val(row.address);
}

function removeAddress(index){
	var delIndex = [parseInt(index)+1];
	$('#addressTable').bootstrapTable('remove', {
        field: "index",
        values: delIndex
    });
	var count = $('#addressTable').bootstrapTable('getData').length;
	for(var i=0;i<count;i++){
		var rows = {
				index: i,
				field : "index",
				value : i+1
			}
		$('#addressTable').bootstrapTable("updateCell",rows);
	}
}
//获取需求计划

function changeRequirement(obj){	
	$("#itemRequirementPlan").html('');
	var requireMent1='<option value="Z004">物料需求计划</option>'+'<option value="Z001">B2C</option>'+'<option value="Z002">消化</option>'+'<option value="Z003">调发</option>';
	
	/*var requireMent2 = '<option value="Z004">物料需求计划</option>';*/
	var itemCategory = $(obj).val();
	if(itemCategory!=''){
		/*if(itemCategory=='ZHR1'||itemCategory=='ZHR2'){
			$("#itemRequirementPlan").append(requireMent2);
		}else{*/
			$("#itemRequirementPlan").append(requireMent1);
		/*}*/
	}else{
		$("#itemRequirementPlan").append('<option value="">请选择</option>');
	}
}

function expandAll(){
	$('#customerBasicInfo').collapse('show');
	$('#contractBasicInfomation').collapse('show');
	$('#contractDetailInfo').collapse('show');
	$('#researchTableContent').collapse('show');
	$('#paymentMethod').collapse('show');
	$('#purchaseDetail').collapse('show');
	$('#infoCheck').collapse('show');
	$('#attachmentInfo').collapse('show');
}

//保存提交订单
function saveOrder(type){
	if(type){
		 expandAll()
		 initOrderFormValidator();
		 var items = $("#materialsTable").bootstrapTable('getData');
		 if(items.length==0){
			 layer.alert('请添加购销明细', {icon: 5});
			 return
		 }
		var bootstrapValidator = $("#orderForm").data('bootstrapValidator');
		bootstrapValidator.validate();
		if(!bootstrapValidator.isValid()){ 
			layer.alert('订单信息录入有误，请检查后提交', {icon: 5});
			return
		}
		
	}
	$("#transferType").removeAttr("disabled");
	 var version = $("#version").val();
	 var payment = new Object();
	 payment['termCode'] = $("#paymentType").val();
	 payment['termName'] = $("#paymentType").find("option:selected").text();
	 payment['percentage'] = "1";
	 payment['payDate'] = $("#inputDate").val();
	 //获取下拉框name
	 getSelectName();
	 var orderData = $("#orderForm").serializeObject(); 
	 $('#transferType').attr("disabled",true);
	 var payments=new Array();
	 payments.push(payment);
	 orderData.payments = payments;
	 orderData['currentVersion'] = version;
	 orderData['orderType'] = 'ZH0D';
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[i].identification];
		 items[i].isVirtual = 0;
		 if(configData){
			 var jsonObject = JSON.parse(configData);
			 items[i]['configs']= jsonObject.configTableData;
			 if(!items[i].isConfigurable){
				if(items[i].itemCategory=='ZHD1'){
					items[i].itemCategory='ZHD2';
				}else if(items[i].itemCategory=='ZHD3'){
					items[i].itemCategory='ZHD4';
				}else{
					items[i].itemCategory='ZHR2';
				}
				
			 }
			 items[i]['configComments'] = jsonObject.remark
		 } else{
			 items[i]['configs'] = null;
		 }	 	 
	 }
	 orderData.orderAddress = $("#addressTable").bootstrapTable('getData');
	 if(type){
		 $.ajax({
			    url: "/steigenberger/order/dealer?action="+type,
			    contentType: "application/json;charset=UTF-8",
			    data: JSON.stringify(orderData),
			    type: "POST",
			    success: function(data) { 
			    	layer.alert('提交成功', {icon: 6});
			    	//跳转到订单管理页面
			    	window.location.href = '/steigenberger/menu/orderManageList';
			    },
			    error: function(){
			    	layer.alert('提交失败', {icon: 5});
			    }
		});  
	 }else{
		 $.ajax({
			    url: "/steigenberger/order/dealer?action="+'save',
			    contentType: "application/json;charset=UTF-8",
			    data: JSON.stringify(orderData),
			    type: "POST",
			    success: function(data) { 
			    	layer.alert('保存成功', {icon: 6});
			    },
			    error: function(){
			    	layer.alert('保存失败', {icon: 5});
			    }
		}); 
	 }
	 
}


function getSelectName() {
	var incotermCode = $("#incoterm").val();
	if(incotermCode){
		$("#incotermName").val(intercoms[incotermCode]);
	}
	var salesType = $("#salesType").val();
	var officeCode = $("#officeSelect").val();
	var groupCode = $("#selectGroup").val();
	if(salesType){
		var offices = officesMap[salesType];
		if(officeCode){
			$("#officeName").val(offices[officeCode]);
		}
		var groups = groupsMap[officeCode];
		if(groupCode){
			$("#groupName").val(groups[groupCode]);
		}
		
	}
	
	var shippingTypeCode = $("#transferType").val();
	if(shippingTypeCode){
		$("#transferTypeName").val(shippingTypesMap[shippingTypeCode]);
	}
	
	$("#confirmTypeName").val($("#confirmType").find("option:selected").text());
}


//调研表查看物料
function viewConfig(){
	var bomCode = $("#materialConfigCode").val();
	var formData = $("#materialConfigForm").serializeObject();
	var configTableData = $("#configTable").bootstrapTable('getData');
	var configCode = [];
	for(var i=0;i<configTableData.length;i++){
		configCode.push(configTableData[i].code);
	}
	formData.bomCode = bomCode;
	formData.configCode = configCode;
	$.ajax({
	    url: "/steigenberger/order/material/configuration",
	    contentType: "application/json;charset=UTF-8",
	    data: JSON.stringify(formData),
	    type: "POST",
	    dataType: "json",
	    success: function(data) { 
	    	$("#moreConfig").attr("style","display:block;");
	    	$("#viewCode").val($("#materialConfigCode").val());
	    	$("#viewPrice").val($("#viewPrice").val());
	    	$("#configPrice").val(data.priceGap);
	    },
	    error: function(){
	    	$("#viewError").attr("style","display:block;");
	    }
	});
}

//设置配置值
function setConfigValueCode(obj,index){
	var configValueCode = $(obj).val();
	$("#configTable").bootstrapTable('updateCell', {
	    index: index,
	    field:'configValueCode',
	    value:configValueCode
	});
}
//查看毛利率信息
function viewGrossProfit(){
	if($("#orderModelType").val()=='edit'){
		editViewGrossProfit();
		return;
	}
	if(!$("#salesType").val()){
		layer.alert('请选择销售类型', {icon: 5});
		return
	}
	 var items = $("#materialsTable").bootstrapTable('getData');
	 if(items.length==0){
		 layer.alert('请添加行项目', {icon: 5});
		 return
	 }
	var version = $("#version").val();
	 var orderData = $("#orderForm").serializeObject();
	 orderData['currentVersion'] = version;
	 orderData['orderType'] = 'ZH0D';
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[i].identification];
		 items[i].isVirtual = 0;
		 if(configData){
			 var jsonObject = JSON.parse(configData);
			 items[i]['configs']= jsonObject.configTableData;
			 items[i]['configComments'] = jsonObject.remark
		 } else{
			 items[i]['configs'] = null;
		 }	 	 
	 }
	 orderData.orderAddress = $("#addressTable").bootstrapTable('getData');
	$("#grossProfit").modal("show");
	$("#grossSeqNum").val($("#sequenceNumber").val());
	$("#grossVersion").val(version);
	$("#grossContractRMBValue").val($("#contractAmount").val());
	$("#grossPerson").val($("#salesName").val());
	$("#grossDate").val($("#inputDate").val());
	$("#grossClazz").val($("#customerClazz").val());
	$.ajax({
	    url: "/steigenberger/order/grossprofit",
	    contentType: "application/json;charset=UTF-8",
	    data: JSON.stringify(orderData),
	    type: "POST",
	    success: function(data) { 
	    	$("#grossProfitTable").bootstrapTable('load', data);
	    },
	    error: function(){
	    	layer.alert('毛利率查看失败', {icon: 5});
	    }
	});  
}

//修改订单时查看毛利率
function editViewGrossProfit(){
	$("#grossProfit").modal("show");
	var version = $("#version").val();
	var sequenceNumber = $("#sequenceNumber").val();
	$("#grossSeqNum").val(sequenceNumber);
	$("#grossVersion").val(version);
	$("#grossContractRMBValue").val($("#contractAmount").val());
	$("#grossPerson").val($("#salesName").val());
	$("#grossDate").val($("#inputDate").val());
	$("#grossClazz").val($("#customerClazz").val());
	var opt = {
			url: '/steigenberger/order/'+sequenceNumber+'/'+version+'/grossprofit'
	};
	$("#grossProfitTable").bootstrapTable('refresh', opt);	
}

var grossProfitColumns=[
	{
		 field: 'name',
		 title: '产品名称'
	},{
		 field: 'amount',
		 title: '金额'
	},{
		 field: 'excludingTaxAmount',
		 title: '不含税金额'
	},{
		 field: 'cost',
		 title: '成本'
	},{
		 field: 'grossProfit',
		 title: '毛利'
	},
	{
		 field: 'grossProfitMargin',
		 title: '毛利率'
	}
]

//规格型号查询column
var materialTypeColumn = [ {
	title : '专用号',
	field : 'code'
},{
	title : '规格型号',
	field : 'description'
}]

var contractUnitTableColumn = [ {
	title : '签约单位',
	field : 'name'
}, {
	title : '地址',
	field : 'address'
},{
	title : '性质分类',
	field : 'clazzName'
}]

var addressColumns = [{
	title:'行号',
    field: 'index',
    width:'5%'
},{
	title : '省市区',
	field : 'pca',
	width:'35%'
}, 
{
	field:'provinceValue',
	visible:false
},
{
	field:'cityValue',
	visible:false
},
{
	field:'areaValue',
	visible:false
},{
	title : '到货地址',
	field : 'address',
	width:'45%'
},{
    title: '<input type="button"  value="+" class="btn btn-primary" onclick="addAddress()"/>',
    align: 'center',
    width:'15%',
    formatter: function(value, row, index) {
    	var actions = [];
		actions.push('<a class="btn" onclick="editAddress(\'' + index + '\')"><i class="fa fa-edit"></i>编辑</a> ');
		actions.push('<a class="btn" onclick="removeAddress(\'' + index + '\')"><i class="fa fa-remove"></i>删除</a>');
		return actions.join('');
    }
}]

var materialsAddressColumns = [{
	title:'行号',
    field: 'index',
    width:'5%'
},{
	title : '省市区',
	field : 'pca',
	width:'35%'
}, 
{
	field:'provinceValue',
	visible:false
},
{
	field:'cityValue',
	visible:false
},
{
	field:'areaValue',
	visible:false
},{
	title : '到货地址',
	field : 'address',
	width:'45%'
}]

var configTableColumns = [
{
	title :'选项',
	field :'optional',
	width:'15%',
	formatter: function(value, row, index) {
    	if(!value){
    		return '必选项'
    	}else{
    		return '可选项'
    	}
    }
}, 
{
	title:'配置',
	field:'name',
	width:'35%'
},
{
	title:'',
	visible:false,
	field:'code'
},
{
	title:'test',
	visible:false,
	field:'configValueCode'
},
{
	title:'配置值',
	field:'configs',
	width:'50%',
	formatter: function(value, row, index) {		
    	var start = '<select class="form-control" name="configValueCode" onchange="setConfigValueCode(this,\'' + index + '\')">';
    	var end = '</select>';
    	if(row.configValueCode){
    		$.each(value,function(index,item){
        		if(item.code==row.configValueCode){
        			start+='<option value=\'' + item.code + '\' selected = "selected">' + item.name + '</option>'
        		}else{
        			start+='<option value=\'' + item.code + '\'>' + item.name + '</option>'
        		}	
        	})
    	}else{
    		$.each(value,function(index,item){
        		if(item.default){
        			start+='<option value=\'' + item.code + '\' selected = "selected">' + item.name + '</option>'
        		}else{
        			start+='<option value=\'' + item.code + '\'>' + item.name + '</option>'
        		}	
        	})
    	}
    	
		return start+end;
    }
}
]


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
			uniqueId:'index',
			cache: false,            
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    smartDisplay:true,
		    singleSelect: true,
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1, //初始化加载第一页
			sidePagination : 'server',//server:服务器端分页|client：前端分页
			pageSize : 10,//单页记录数,
			showRefresh : false,//刷新按钮
			queryParams : params,
			columns : tableColumns
		})
	};
	return oTableInit;
};