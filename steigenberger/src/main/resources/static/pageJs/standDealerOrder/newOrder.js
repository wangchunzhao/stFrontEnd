$(function () {	
	//modal 无法滚动问题
	$('#specificationModal').on('hidden.bs.modal', function () {
		$("body").addClass("modal-open");
	});
	$('#materialAddressModal').on('hidden.bs.modal', function () {
		$("body").addClass("modal-open");
	});
	$('#subsidiaryModal').on('hidden.bs.modal', function () {
		$("body").removeClass("modal-open");
	});
	
	//进页面前清空缓存
	localStorage.clear()
	
	//初始化付款方式页面
	var paymentTable = new TableInit('paymentTable','','',paymentColumns);
	paymentTable.init();
	
	var addressTable = new TableInit('addressTable','','',addressColumns)
	addressTable.init();
	//初始化客户查询Table
	var customerTable = new TableInit('customerTable','',queryUnitParams,customerTableColumn);
	customerTable.init();
	
	//初始化物料查询table
	var materialTypeTable = new TableInit('materialTypeTable','',queryMaterialTypeParams,materialTypeColumn)
	materialTypeTable.init();
	
	//订单文件列表初始化
	var fileListTable = new TableInit('fileList','','',fileListColumns)
	fileListTable.init();
	
	//订单文件上传成功处理方法
	$("#f_upload").on("fileuploaded", function(event, data, previewId, index) {
		var attachment = data.response.data;
		var a = $('#fileList').bootstrapTable('getData');
		var fileListLength = $('#fileList').bootstrapTable('getData').length;
		$("#fileList").bootstrapTable('insertRow', {
		    index: fileListLength,
		    row: {
		    	type:'fileList',
		    	index:fileListLength,
		    	fileName:attachment.fileName,
		    	fileUrl:attachment.fileUrl
		    }
		});
		//清空文件选择框
		$(".file-caption-name").val('');
	});
	
	//调研表文件列表初始化
	var itemFileListTable = new TableInit('itemFileList','','',fileListColumns)
	itemFileListTable.init();
	
	//订单文件上传成功处理方法
	$("#item_f_upload").on("fileuploaded", function(event, data, previewId, index) {
		var attachment = data.response.data;
		var a = $('#itemFileList').bootstrapTable('getData');
		var fileListLength = $('#itemFileList').bootstrapTable('getData').length;
		$("#itemFileList").bootstrapTable('insertRow', {
		    index: fileListLength,
		    row: {
		    	type:'itemFileList',
		    	index:fileListLength,
		    	fileName:attachment.fileName,
		    	fileUrl:attachment.fileUrl
		    }
		});
		//清空文件选择框
		$(".file-caption-name").val('');
	});
	
	//初始化毛利率table
	var grossProfitTable = new TableInit("grossProfitTable",'','',grossProfitColumns);
	grossProfitTable.init();	
	if(installationTerms){
		$.each(installationTerms, function (key, value) {
			if(orderType=="ZH0D"&&key=="02"){
				$("#installType").val(key);
				$("#installTypeName").val(value);
				
			}
			
		});
	}
	initSubsidiartFormValidator();
    initOrderFormValidator();
	initMarialsTables();
	$('#first').tab('show');
	$('#shippDate').datepicker();
	defaultCollapse();
	if(status==null||status==""||status=="undefined"){
		getUserDetail();
	}else{
		fillItems();
		//修改查看订单时,辉县地址数据
		fillOrderAddress();
		initDropDownList();
		fillAttachments();
	}
	if(orderOperationType=="2"){
		disableAll();
	}
});

//查看订单页面不能修改
function disableAll(){ 
	  var form=document.forms[0];
	  for(var i=0;i<form.length;i++){
	    var element=form.elements[i];
	    element.disabled=true;
	  }
	  enableButton();
}
//设置按钮可用
function enableButton(){
	  $(".close").attr('disabled',false);
	  $("#backButton").attr('disabled',false);
	  $("#grossProfitCloseBt").attr('disabled',false);
	  $("#grossProfitExportBt").attr('disabled',false);
	  $("#showGrossProfitBt").attr('disabled',false);
	  $("#expandBt").attr('disabled',false);
	  $("#closeBt").attr('disabled',false);
	  $("#approve").attr('disabled',false);
	  $("#version").attr('disabled',false);
	  $("#materialsEdit").attr('disabled',true);
	  $("#editAddressBt").attr('disabled',true);
	  $("#removeAddressBt").attr('disabled',true);
	  $("#copyMaterial").attr('disabled',true);  
	  if($("#expenseItem")){
		  $("#expenseItem").find("*").each(function() {
			 $(this).removeAttr("disabled");
		  });
	  }
}

//获取session中用户信息
function getUserDetail(){
	$.ajax({
	    url: ctxPath+"order/user",
	    data: {},
	    type: "get",
	    success: function(data) {
	    	$("#salesName").val(data.name);
	    	$("#salesTel").val(data.tel);
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
			var materialType = items[i].stMaterialGroupCode;
			if(materialType=='T101'){			
				var identification = materialType+"|"+countMaterialsTableall1;
				var rowData = fillItemToTableRow(items[i]);
				var configAttachments = items[i].attachments
				rowData["allIndex"] = countMaterialsTable;
				rowData["identification"] = identification;
				//添加调研表
				if(rowData.isConfigurable&&items[i].configs!=null){
					fillConfigsForMaterial(identification,items[i].configs,rowData.comments,rowData.materialCode,rowData.clazzCode,configAttachments);
				}	
				$("#materialsTableall1").bootstrapTable('insertRow', {
				    index: countMaterialsTableall1,
				    row: rowData
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: rowData
				});
			}else if(materialType=='T102'){
				var identification = materialType+"|"+countMaterialsTableall2;
				var rowData = fillItemToTableRow(items[i]);
				rowData["allIndex"] = countMaterialsTable
				rowData["identification"] = identification
				rowData["materialConfig"] = rowData.configs;
				$("#materialsTableall2").bootstrapTable('insertRow', {
				    index: countMaterialsTableall2,
				    row: rowData
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: rowData
				});
			}else if(materialType=='T103'){
				var identification = materialType+"|"+countMaterialsTableall3;
				var rowData = fillItemToTableRow(items[i]);
				rowData["allIndex"] = countMaterialsTable
				rowData["identification"] = identification
				rowData["materialConfig"] = rowData.configs;
				$("#materialsTableall3").bootstrapTable('insertRow', {
				    index: countMaterialsTableall3,
				    row: rowData
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: rowData
				});
			}else if(materialType=='T104'){
				var identification = materialType+"|"+countMaterialsTableall4;
				var rowData = fillItemToTableRow(items[i]);
				rowData["allIndex"] = countMaterialsTable
				rowData["identification"] = identification
				rowData["materialConfig"] = rowData.configs;
				$("#materialsTableall4").bootstrapTable('insertRow', {
				    index: countMaterialsTableall4,
				    row: rowData
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: rowData
				});
			}else if(materialType=='T105'){
				var identification = materialType+"|"+countMaterialsTableall5;
				var rowData = fillItemToTableRow(items[i])
				rowData["allIndex"] = countMaterialsTable
				rowData["identification"] = identification
				rowData["materialConfig"] = rowData.configs
				$("#materialsTableall5").bootstrapTable('insertRow', {
				    index: countMaterialsTableall5,
				    row: rowData
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: rowData
				});
			}else if(materialType=='T106'){
				var identification = materialType+"|"+countMaterialsTableall6;
				var rowData = fillItemToTableRow(items[i]);
				rowData["allIndex"] = countMaterialsTable
				rowData["identification"] = identification
				rowData["materialConfig"] = rowData.configs
				$("#materialsTableall6").bootstrapTable('insertRow', {
				    index: countMaterialsTableall6,
				    row: rowData
				});
				$("#materialsTable").bootstrapTable('insertRow', {
				    index: countMaterialsTable,
				    row: rowData
				});
			}
		}
	}
	getAllCountFiled();
}

function fillConfigsForMaterial(identification,configs,configRemark,materialCode,clazzCode,configAttachments){
	var materialDefaultConfigs = getDefaultConfigs(materialCode,clazzCode);
	var editConfigs = [];
	$.each(materialDefaultConfigs,function(defaultIndex,defaultItem){
		$.each(configs,function(index,item){
			if(item.keyCode==defaultItem.code){
				var config = defaultItem;
				config["configValueCode"] = item.valueCode
				editConfigs.push(config)
				
			}
		})
	})
	/*materialDefaultConfigs.forEach((defaultItem,defaultIndex)=>{
		configs.forEach((item,index)=>{
			if(item.code==defaultItem.code){
				var config = defaultItem;
				config["configCodeValue"] = item.configCodeValue
				editConfigs.push(config)
			}
		})
	})*/
	var configData = new Object();
	configData.configTableData = editConfigs;
	configData.remark = configRemark
	configData.attachments = configAttachments;
	localStorage.setItem(identification, JSON.stringify(configData));
}

function getDefaultConfigs(materialCode,clazzCode){
	var materialDefaultConfigs;
	$.ajax({
	    url: ctxPath+"order/material/configurations?clazzCode="+clazzCode+"&materialCode="+materialCode,
	    type: "get",
	    async:false,
	    success: function(data) {
	    	materialDefaultConfigs = data.data;
	    }
	});
	return materialDefaultConfigs;
}

//修改查看订单时物料表格初始化
function fillItemToTableRow(data){
	var quantity = data.quantity;
	var transcationPrice = toDecimal2(data.transcationPrice);
	var optionalActualPrice = toDecimal2(data.optionalActualPrice);
	var optionalActualAmount = toDecimal2(quantity*(data.optionalActualPrice));
	var optionalTransationPrice = toDecimal2(data.optionalTransationPrice);
	var b2cEstimatedPrice = toDecimal2(data.b2cEstimatedPrice);
	var b2cEstimatedAmount = toDecimal2((data.b2cEstimatedPrice)*quantity);
	var b2cEstimatedCost = toDecimal2(data.b2cEstimatedCost);
	var transcationPriceTotal = toDecimal2(parseFloat(transcationPrice)+parseFloat(optionalTransationPrice));
	var retailPrice = toDecimal2(data.retailPrice);
	var retailAmount = toDecimal2(quantity*retailPrice);
	var discount = data.discount;
	var actualPrice = toDecimal2(retailPrice*discount);
	var actualAmount = toDecimal2(quantity*actualPrice);
	var actualPriceSum = toDecimal2(parseFloat(actualPrice)+parseFloat(optionalActualPrice));
	var actualAmountSum = toDecimal2(actualPriceSum*quantity);
	var transactionPriceSum = toDecimal2(transcationPriceTotal*quantity);
	var pcaAddress;
	if(data.provinceName){
		pcaAddress = data.provinceName;
		if(data.cityName){
			pcaAddress+="/"+data.cityName
		}
		if(data.districtName){
			pcaAddress+="/"+data.districtName
		}
		if(data.address){
			pcaAddress+=data.address
		}
	}
	var row = {
			rowNum:data.rowNum,
			materialName:data.materialName,
			materialCode:data.materialCode,
			clazzCode:data.clazzCode,
			isConfigurable:data.isConfigurable,
			isPurchased:data.isPurchased,
			materialGroupCode:data.materialGroupCode,
			materialGroupName:data.materialGroupName,
			quantity:data.quantity,
			unitName:data.unitName,
			actualPrice:actualPrice,
			actualAmount:actualAmount,
			transcationPrice:transcationPrice,
			optionalActualPrice:optionalActualPrice,
			optionalActualAmount:optionalActualAmount,
			optionalTransationPrice:optionalTransationPrice,
			b2cEstimatedPrice:b2cEstimatedPrice,
			b2cEstimatedAmount:b2cEstimatedAmount,
			b2cEstimatedCost:b2cEstimatedCost,
			actualPriceSum:actualPriceSum,
			actualAmountSum:actualAmountSum,
			transactionPriceSum:transactionPriceSum,
			retailPrice:retailPrice,
			retailAmount:retailAmount,
			discount:data.discount,
			itemCategory:data.itemCategory,
			itemRequirementPlan:data.itemRequirementPlan,
			producePeriod:data.period,
			deliveryDate:data.deliveryDate,
			produceDate:data.produceDate,
			shippDate:data.shippDate,
			materialAddress:pcaAddress,
			onStoreDate:data.onStoreDate,
			purchasePeriod:data.period,
			b2cComments:data.b2cComments,
			specialComments:data.specialComments,
			colorComments:data.colorComments,
			provinceCode:data.provinceCode,
			provinceName:data.provinceName,
			cityCode:data.provinceName,
	        cityName:data.cityName,
	        districtCode:data.districtCode,
	        districtName:data.districtName,
	        address:data.address,
	        deliveryAddressSeq: data.deliveryAddressSeq
	}
	return row;
}


//修改查看订单时,回显地址数据
function fillOrderAddress(){
	if(orderAddress){
		for(var i=0;i<orderAddress.length;i++){
			var row = orderAddress[i];
			var pca;
			if(row.provinceName){
				pca = row.provinceName;
				if(row.cityName){
					pca+="/"+row.cityName
				}
				if(row.districtName){
					pca+="/"+row.districtName
				}
			}
			row.pca=pca;
			row.index = i+1;
			$("#addressTable").bootstrapTable('insertRow', {
			    index: i,
			    row: orderAddress[i]
			});
		}
	}
}

//修改查看订单时,回显上传附件列表
function fillAttachments(){
	if(attachments){
		for(var i=0;i<attachments.length;i++){
			var row = attachments[i];
			$("#fileList").bootstrapTable('insertRow', {
			    index: i,
			    row: attachments[i]
			});
		}
	}
}

function initDropDownList(){
	$('#saleType').trigger("change");
	$('#officeSelect').val($('#officeCode').val());
	$('#officeSelect').trigger("change");
	$('#selectGroup').val($('#groupCode').val());
	$("#orignalContractAmount").val($("#contractValue").val());
	$("#contractAmount").val($("#contractRmbValue").val());
	
}

//Customer basic infomation js start 
function openSearchCustomer(){
	$('#customerSearchModal').modal('show');	
	var opt = {
				url: ctxPath+'order/customers'
			   };
	$("#customerTable").bootstrapTable('refresh', opt);
	$("#customerTable").on('click-row.bs.table',function($element,row,field){
		$('#customerSearchModal').modal('hide');
		$("#customerSearchName").val('');
		$("#customerCode").val(row.code);
		$("#customerName").val(row.name).change();
		$("#customerClazzName").val(row.clazzName);
		$("#customerIndustryCode").val(row.industryCode);
	})
}

function queryUnitParams(params) {
    params.pageNo = this.pageNumber;
    params.customerName = $("#customerSearchName").val();
    params.clazzCode = $("#customerClazzCode").val();
    return params;
}
function searchCustomer(){
	$('#customerTable').bootstrapTable('refresh');
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
	if(taxRate){
		$("#taxRate").val(taxRate[saleType]);
	}
	if(saleType=="20"){
		$("#freightDiv").show();
		$('#selectProvince').val('');
		$('#citySelect').val('');
		$('#selectDistrict').val('');
		$('#citySelect').attr("disabled",true);
		$('#selectDistrict').attr("disabled",true);	
		$('#selectProvince').attr("disabled",true);
		$('#currency').attr('disabled',false);
		$('#incoterm').attr("disabled",false);
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
		$('#incoterm').attr("disabled",true);
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
		$("#currency").append("<option value=''>--选择币种--</option>");
		$.each(currency, function (index,item) {
			$("#currency").append("<option selected value=" + item.code + ">" + item.name + "</option>");
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
	var salesType = $("#saleType").val();
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

//打开添加物料规格modal
function addSubsidiary(){
	var customerName = $("#customerName").val();
	if(customerName==""){
		layer.alert('请先选择客户', {icon: 5});
		return
	}
	$('#subsidiaryModal').modal('show');
	$("#subsidiaryForm")[0].reset();
	$('#amount').val(1);
	$('#discount').val($("#standardDiscount").val());
	$('#materialsModalType').val('new');
	if($("#isTerm1").val()=="1"||$("#isTerm2").val()=="1"||$("#isTerm3").val()=="1"){
		$("#itemRequirementPlan").val("001");
	}
	var b2cRemark="";
	if($("#isTerm1").val()=="1"){
		b2cRemark+="是否甲供；";	
	}
	if($("#isTerm2").val()=="1"){
		b2cRemark+="远程监控；"
	}
	
	if($("#isTerm3").val()=="1"){
		b2cRemark+="地下室；"
	}
	$("#b2cRemark").val(b2cRemark);
}

//打开规格查询框
function searchSpecification(){
	$('#specificationModal').modal('show');
	var opt = {
			url: ctxPath+"order/materials"
	};
	$("#materialTypeTable").bootstrapTable('refresh', opt);
	$("#materialTypeTable").on('click-row.bs.table',function($element,row,field){
		$('#specificationModal').modal('hide');
		fillMaterailValue(row);
	})
}

function queryMaterialTypeParams(params) {
    params.pageNo = this.pageNumber;
    params.materialName = $("#materialsName").val();
    //经销商先固定为unkn
   if($("#orderType").val()=="ZH0D"){
    	params.industoryCode = "unkn";	
    }else{
    	 params.industoryCode = $("#customerIndustryCode").val();
   }  
    return params;
}


//查询规格
function searchMaterilType(){
	$('#materialTypeTable').bootstrapTable('refresh');
}

//将查出来的物料信息填充到各个field中
function fillMaterailValue(data){
	$("#materialTypeName").val(data.description);
	$("#materialCode").val(data.code);
	if(data.purchased){
		$('#isPurchased').val('外购');
	}else{
		$('#isPurchased').val('自制');
	}
	$("#materialTypeName").val(data.description);	
	$("#materialGroupName").val(data.groupName);
	$("#groupCode").val(data.groupCode);
	$("#isConfigurable").val(data.configurable);
	var materialsType = data.stGroupCode;
	$("#materialsType").val(materialsType);
	$("#unitName").val(data.unitName);
	$("#unitCode").val(data.unitCode);
	$("#materialClazzCode").val(data.clazzCode);
	
	//价格模块
	var amount = $("#amount").val();
	//市场零售价
	$("#retailPrice").val(toDecimal2(data.retailPrice));
	//转移价
	$("#transcationPrice").val(toDecimal2(data.transcationPrice));
	
	$("#acturalPricaOfOptional").val(toDecimal2(0.00));
	$("#acturalPricaOfOptionalAmount").val(toDecimal2(0.00));
	$("#transcationPriceOfOptional").val(toDecimal2(0.00));
	$("#B2CPriceEstimated").val(toDecimal2(0.00));
	$("#B2CPriceEstimatedAmount").val(toDecimal2(0.00));
	$("#B2CCostOfEstimated").val(toDecimal2(0.00));
	$("#transcationPriceTotal").val(toDecimal2(parseFloat($("#transcationPrice").val())+parseFloat($("#transcationPriceOfOptional").val())));
	$("#retailPriceAmount").val(toDecimal2(amount*(data.retailPrice)));
	var discountValue = $("#discount").val();
	var discount = discountValue.split("%")[0];
	var actualPrice = (data.retailPrice*discount)
	$("#acturalPrice").val(toDecimal2(actualPrice));
	$("#acturalPriceAmount").val(toDecimal2(amount*(actualPrice)));
	$("#acturalPriceTotal").val(toDecimal2(parseFloat($("#acturalPrice").val())+parseFloat($("#acturalPricaOfOptional").val())));
	$("#acturalPriceAmountTotal").val(toDecimal2(($("#acturalPriceTotal").val())*amount));
	/*$("#deliveryDate").val(data.deliveryDate);
	$("#produceDate").val(data.produceDate);
	$("#onStoreDate").val(data.onStoreDate);*/
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
//B2C预估价变化
function getB2CAmount(obj){
	var b2cPrice = $(obj).val();
	var amount = $("#amount").val();
	$("#B2CPriceEstimatedAmount").val(toDecimal2(amount*(parseFloat(b2cPrice))));
}

//编辑购销明细
function editMaterials(identification){
	$('#subsidiaryModal').modal('show');
	$('#materialsModalType').val('edit');
	var identificationSplit = identification.split('|');
	var materialsType = identificationSplit[0];
	var index = identificationSplit[1];
	var tableData = getTableDataByIndex(materialsType,index);	
	fillEditMaterailValue(tableData);
}

//根据index获取表格行数据
function getTableDataByIndex(materialsType,index){
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
	return tableData;
}

//编辑购销明细时页面值回显
function fillEditMaterailValue(data){	
	if(data.allIndex){
		$("#allIndex").val(data.allIndex);
	}
	if(data.allIndex==0){
		$("#allIndex").val(data.allIndex);
	}
	
	if(data.identification){
		$("#identification").val(data.identification);
	}
	$("#purchasedCode").val(data.isPurchased);
	if(data.isPurchased){
		$("#isPurchased").val("外购");
	}else{
		$("#isPurchased").val("自制");
	}
	$("#shippDate").val(data.shippDate)
	$("#rowNumber").val(data.rowNum);
	$("#identification").val(data.identification);
	$("#materialTypeName").val(data.materialName);
	$("#materialCode").val(data.materialCode);
	$("#amount").val(data.quantity);
	$("#b2cRemark").val(data.b2cComments);
	$("#colorComments").val(data.colorComments);
	$("#specialRemark").val(data.specialComments);
	$("#itemCategory").val(data.itemCategory).change();
	$("#itemRequirementPlan").val(data.itemRequirementPlan);
	$("#materialGroupName").val(data.materialGroupName);
	$("#groupCode").val(data.materialGroupCode);
	$("#isConfigurable").val(data.isConfigurable);
	$("#materialsType").val(materialsType);
	$("#unitName").val(data.unitName);
	$("#unitCode").val(data.unitCode);
	$("#materialClazzCode").val(data.clazzCode);
	$("#transcationPrice").val(data.transcationPrice);
	$("#acturalPricaOfOptional").val(data.optionalActualPrice);
	$("#acturalPricaOfOptionalAmount").val(data.optionalActualAmount);
	$("#transcationPriceOfOptional").val(data.optionalTransationPrice);
	$("#B2CPriceEstimated").val(data.b2cEstimatedPrice);
	$("#B2CPriceEstimatedAmount").val(data.b2cEstimatedAmount);
	$("#B2CCostOfEstimated").val(data.b2cEstimatedCost);
	$("#transcationPriceTotal").val(data.transactionPriceSum);
	$("#retailPrice").val(data.retailPrice);
	$("#retailPriceAmount").val(data.retailAmount);
	$("#acturalPrice").val(data.actualPrice);
	$("#acturalPriceAmount").val(data.actualAmount);
	$("#acturalPriceTotal").val(data.actualPriceSum);
	$("#acturalPriceAmountTotal").val(data.actualAmountSum);
	if(data.isPurchased){
		$("#producePeriod").val(data.period);
	}else{
		$("#purchasePeriod").val(data.period);
	}
	$("#deliveryDate").val(data.deliveryDate);
	$("#produceDate").val(data.produceDate);
	$("#onStoreDate").val(data.onStoreDate);
	$("#standardPrice").val(toDecimal2(data.standardPrice));
	$("#discount").val(data.discount);
	$("#materialAddress").val(data.materialAddress);
	$("#deliveryAddressSeq").val(data.deliveryAddressSeq);
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


//点击确认购销明细
function confirmMaterials(){
	var bootstrapValidator = $("#subsidiaryForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
    	return
    }
    var allIndex = $("#allIndex").val();
	var identification = $("#identification").val();
	var materialTypeName = $("#materialTypeName").val();
	var modalType = $("#materialsModalType").val();
	var materialType;
	if(modalType=="new"){
		materialType = $("#materialsType").val();
	}else{
		materialType = identification.split("|")[0];
	}	
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	var countMaterialsTableall1 = $('#materialsTableall1').bootstrapTable('getData').length;
	var countMaterialsTableall2 = $('#materialsTableall2').bootstrapTable('getData').length;
	var countMaterialsTableall3 = $('#materialsTableall3').bootstrapTable('getData').length;
	var countMaterialsTableall4 = $('#materialsTableall4').bootstrapTable('getData').length;
	var countMaterialsTableall5 = $('#materialsTableall5').bootstrapTable('getData').length;
	var countMaterialsTableall6 = $('#materialsTableall6').bootstrapTable('getData').length;
	if(modalType=='new'){
		if(materialType=='T101'){
			var rowData = confirmRowData(countMaterialsTable);
			var identification = materialType+"|"+countMaterialsTableall1;//所有表格中行的唯一标识		
			rowData["allIndex"] = countMaterialsTable;	//所有以外的行需要记录所有中的index
			rowData["identification"] = identification;
			$("#materialsTableall1").bootstrapTable('insertRow', {
			    index: countMaterialsTableall1,
			    row: rowData
			});
			$("#second").tab('show');
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowData
			});
		}else if(materialType=='T102'){
			var rowData = confirmRowData(countMaterialsTable);
			var identification = materialType+"|"+countMaterialsTableall2;
			rowData["allIndex"] = countMaterialsTable;
			rowData["identification"] = identification;
			$("#materialsTableall2").bootstrapTable('insertRow', {
			    index: countMaterialsTableall2,
			    row: rowData
			});
			$("#third").tab('show');
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowData
			});
		}else if(materialType=='T103'){
			var rowData = confirmRowData(countMaterialsTable);
			var identification = materialType+"|"+countMaterialsTableall3;
			rowData["allIndex"] = countMaterialsTable;
			rowData["identification"] = identification;
			$("#materialsTableall3").bootstrapTable('insertRow', {
			    index: countMaterialsTableall3,
			    row: rowData
			});
			$("#four").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable);	
			rowDataAll["allIndex"] = identification;
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T104'){
			var rowData = confirmRowData(countMaterialsTable);
			var identification = materialType+"|"+countMaterialsTableall4;
			rowData["allIndex"] = countMaterialsTable;
			rowData["identification"] = identification;
			$("#materialsTableall4").bootstrapTable('insertRow', {
			    index: countMaterialsTableall4,
			    row: rowData
			});
			$("#five").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable);	
			rowDataAll["allIndex"] = identification;
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T105'){
			var rowData = confirmRowData(countMaterialsTable);
			var identification = materialType+"|"+countMaterialsTableall5;
			rowData["allIndex"] = countMaterialsTable;
			rowData["identification"] = identification;
			$("#materialsTableall5").bootstrapTable('insertRow', {
			    index: countMaterialsTableall5,
			    row: rowData
			});
			$("#six").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable);	
			rowDataAll["allIndex"] = identification;
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}else if(materialType=='T106'){
			var rowData = confirmRowData(countMaterialsTable);
			var identification = materialType+"|"+countMaterialsTableall6;	
			rowData["allIndex"] = countMaterialsTable;
			rowData["identification"] = identification;
			$("#materialsTableall6").bootstrapTable('insertRow', {
			    index: countMaterialsTableall6,
			    row: rowData
			});
			$("#seven").tab('show');
			var rowDataAll = confirmRowData(countMaterialsTable);
			rowDataAll["allIndex"] = identification;
			$("#materialsTable").bootstrapTable('insertRow', {
			    index: countMaterialsTable,
			    row: rowDataAll
			});
		}
	}else{
		var rowNumber = $("#rowNumber").val();
		if(materialType=='T101'){
			var rowData = confirmRowData(countMaterialsTable,rowNumber);	
			rowData["allIndex"] = allIndex;	//所有以外的行需要记录所有中的index
			rowData["identification"] = identification;
			var subTableIndex = identification.split("|")[1];
			$("#materialsTableall1").bootstrapTable('updateRow', {
			    index: subTableIndex,
			    row: rowData
			});
			$("#second").tab('show');		
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: allIndex,
			    row: rowData
			});
		}else if(materialType=='T102'){
			var rowData = confirmRowData(countMaterialsTable,rowNumber);	
			rowData["allIndex"] = allIndex;	//所有以外的行需要记录所有中的index
			rowData["identification"] = identification;
			var subTableIndex = identification.split("|")[1];
			$("#materialsTableall2").bootstrapTable('updateRow', {
			    index: subTableIndex,
			    row: rowData
			});
			$("#second").tab('show');		
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: allIndex,
			    row: rowData
			});
		}else if(materialType=='T103'){
			var rowData = confirmRowData(countMaterialsTable,rowNumber);	
			rowData["allIndex"] = allIndex;	//所有以外的行需要记录所有中的index
			rowData["identification"] = identification;
			var subTableIndex = identification.split("|")[1];
			$("#materialsTableall3").bootstrapTable('updateRow', {
			    index: subTableIndex,
			    row: rowData
			});
			$("#third").tab('show');		
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: allIndex,
			    row: rowData
			});
		}else if(materialType=='T104'){
			var rowData = confirmRowData(countMaterialsTable,rowNumber);	
			rowData["allIndex"] = allIndex;	//所有以外的行需要记录所有中的index
			rowData["identification"] = identification;
			var subTableIndex = identification.split("|")[1];
			$("#materialsTableall4").bootstrapTable('updateRow', {
			    index: subTableIndex,
			    row: rowData
			});
			$("#third").tab('show');		
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: allIndex,
			    row: rowData
			});
		}else if(materialType=='T105'){
			var rowData = confirmRowData(countMaterialsTable);	
			rowData["allIndex"] = allIndex;	//所有以外的行需要记录所有中的index
			rowData["identification"] = identification;
			var subTableIndex = identification.split("|")[1];
			$("#materialsTableall5").bootstrapTable('updateRow', {
			    index: subTableIndex,
			    row: rowData
			});
			$("#third").tab('show');		
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: allIndex,
			    row: rowData
			});
		}else if(materialType=='T106'){
			var rowData = confirmRowData(countMaterialsTable,rowNumber);	
			rowData["allIndex"] = allIndex;	//所有以外的行需要记录所有中的index
			rowData["identification"] = identification;
			var subTableIndex = identification.split("|")[1];
			$("#materialsTableall6").bootstrapTable('updateRow', {
			    index: subTableIndex,
			    row: rowData
			});
			$("#third").tab('show');		
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: allIndex,
			    row: rowData
			});
		}	
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
		if(item.actualAmountSum){
			itemsAmount.push(item.actualAmountSum);
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

function confirmRowData(index,rowNumber){
	var row = {
			rowNum:rowNumber?rowNumber:(index+1)*10,
			materialName:$("#materialTypeName").val(),
			materialCode:$("#materialCode").val(),
			clazzCode:$("#materialClazzCode").val(),
			isConfigurable:$("#isConfigurable").val(),
			isPurchased:$("#purchasedCode").val(),
			materialGroupName:$("#materialGroupName").val(),
			materialGroupCode:$("#groupCode").val(),
			quantity:$("#amount").val(),
			unitName:$("#unitName").val(),
			unitCode:$("#unitCode").val(),
			standardPrice:$("#standardPrice").val(),
			actualPrice:$("#acturalPrice").val(),
			actualAmount:$("#acturalPriceAmount").val(),
			transcationPrice:$("#transcationPrice").val(),
			optionalActualPrice:$("#acturalPricaOfOptional").val(),
			optionalActualAmount:$("#acturalPricaOfOptionalAmount").val(),
			optionalTransationPrice:$("#transcationPriceOfOptional").val(),
			b2cEstimatedPrice:$("#B2CPriceEstimated").val(),
			b2cEstimatedAmount:$("#B2CPriceEstimatedAmount").val(),
			b2cEstimatedCost:$("#B2CCostOfEstimated").val(),
			actualPriceSum:$("#acturalPriceTotal").val(),
			actualAmountSum:$("#acturalPriceAmountTotal").val(),
			transactionPriceSum:$("#transcationPriceTotal").val(),
			retailPrice:$("#retailPrice").val(),
			retailAmount:$("#retailPriceAmount").val(),
			discount:$("#discount").val(),
			itemCategory:$("#itemCategory").val(),
			itemRequirementPlan:$("#itemRequirementPlan").val(),
			producePeriod:$("#producePeriod").val(),
			deliveryDate:$("#deliveryDate").val(),
			produceDate:$("#produceDate").val(),
			shippDate:$("#shippDate").val(),
			deliveryAddressSeq:$("#deliveryAddressSeq").val(),
			materialAddress:$("#materialAddress").val(),
			onStoreDate:$("#onStoreDate").val(),
			purchasePeriod:$("#purchasePeriod").val(),
			b2cComments:$("#b2cRemark").val(),
			specialComments:$("#specialRemark").val(),
			colorComments:$("#colorComments").val(),
			provinceCode:$("#materialProvinceCode").val(),
			provinceName:$("#materialProvinceName").val(),
			cityCode:$("#materialCityCode").val(),
			cityName:$("#materialCityName").val(),
			districtCode:$("#materialAreaCode").val(),
			districtName:$("#materialAreaName").val(),
			address: $('#materialModalAddress').val()
			
			
	}
	return row;
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
		$('#materialProvinceCode').val(row.provinceCode);
		$('#materialProvinceName').val(row.provinceName);
		$('#materialCityCode').val(row.cityCode);
		$('#deliveryAddressSeq').val(row.seq);
		$('#materialCityName').val(row.cityName);
		$('#materialAreaCode').val(row.districtCode);
		$('#materialAreaName').val(row.districtName);
		$('#materialModalAddress').val(row.address)
		if(row.pca==''){
			$("#materialAddress").val(row.address).change();
		}else{
			$("#materialAddress").val(row.pca+"/"+row.address).change();
		}
		
	})
}

//确认购销明细校验
function initSubsidiartFormValidator(){
	 $('#subsidiaryForm').bootstrapValidator({
	　　　　　　　　message: 'This value is not valid',
	        		excluded:[':hidden', ':not(:visible)'],
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
		            	   trigger:"change",
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
			           },
			           materialAddress: {
			        	   trigger:"change",
		                    validators: {
		                        notEmpty: {
		                            message: '请添加地址'
		                        }
		                    }
			           }
	            }
	        });
}
//订单提交校验
function initOrderFormValidator(){
   $('#orderForm').bootstrapValidator({
       message: 'This value is not valid',
       fields: {
    	   salesTel: {
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
            	   trigger:"change",
                   notEmpty: {
                        message: '请选择签约单位'
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


//打开调研表
function openConfig(identification){
	$("#materialconfigModal").modal('show');
	var identificationSplit = identification.split('|');
	var materialsType = identificationSplit[0];
	var index = identificationSplit[1];
	var tableData = getTableDataByIndex(materialsType,index);
	$("#materialConfigClazzCode").val(tableData.clazzCode);
	$("#materialConfigCode").val(tableData.materialCode);
	$("#identification").val(identification);
	$("#viewCode").val(tableData.materialCode);
	$("#viewTransationPrice").val(tableData.transcationPrice);
	$("#viewActualPrice").val(tableData.actualPrice);
	var url =ctxPath+"order/material/configurations";
	var configTable = new TableInit('configTable','','',configTableColumns);
	configTable.init();
	var configData = localStorage[identification];
	if(configData){
		var jsonObject = JSON.parse(configData);
		if(jsonObject.configTableData.length>0){
			$("#configTable").bootstrapTable("removeAll");
			var jsonObject = JSON.parse(configData);
			for(var i=0;i<jsonObject.configTableData.length;i++){
				$("#configTable").bootstrapTable('insertRow',{
					index:i,
					row:jsonObject.configTableData[i]
				});
			}
		}else{
			insertDefaultConfigs()
		}
		$("#configRemark").val(jsonObject.remark);
		if(jsonObject.attachments.length>0){
			$("#itemFileList").bootstrapTable("removeAll");
			var jsonObject = JSON.parse(configData);
			for(var i=0;i<jsonObject.attachments.length;i++){
				$("#itemFileList").bootstrapTable('insertRow',{
					index:i,
					row:jsonObject.attachments[i]
				});
			}
		}else{
			$("#itemFileList").bootstrapTable("removeAll");
		}
		
	}else{
		insertDefaultConfigs()
		$("#itemFileList").bootstrapTable("removeAll");
		/*$("#configTable").bootstrapTable('refresh',{
			url:url,
			query:{'clazzCode':$("#materialConfigClazzCode").val(),
				   'materialCode':$("#materialConfigCode").val()
			}
		});*/
	}
}

function insertDefaultConfigs(){
	$("#configTable").bootstrapTable("removeAll");
	var defaultConfigs = getDefaultConfigs($("#materialConfigCode").val(),$("#materialConfigClazzCode").val());
	var insertConfigs = new Array();
	$.each(defaultConfigs,function(index,item){
		var config = item;
		$.each(item.configs,function(index,value){
			if(value.default){
				config['configValueCode'] = value.code
			}
		})
		//没有默认的取第一个
		if(!config.configValueCode){
			config['configValueCode'] = item.configs[0].code;
		}
		insertConfigs.push(config);
	})
	for(var i=0;i<insertConfigs.length;i++){
		$("#configTable").bootstrapTable('insertRow',{
			index:i,
			row:insertConfigs[i]
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
	$("#configTable").bootstrapTable("removeAll");
	var defaultConfigs = getDefaultConfigs($("#materialConfigCode").val(),$("#materialConfigClazzCode").val());
	var insertConfigs = new Array();
	$.each(defaultConfigs,function(index,item){
		var config = item;
		$.each(item.configs,function(index,value){
			if(value.default){
				config['configValueCode'] = value.code
			}
		})
		if(!config.configValueCode){
			config['configValueCode'] = item.configs[0].code;
		}
		insertConfigs.push(config);
	})
	for(var i=0;i<insertConfigs.length;i++){
		$("#configTable").bootstrapTable('insertRow',{
			index:i,
			row:insertConfigs[i]
		});
	}
}

//关闭调研表
function closeMaterialConfig(){
	$("#materialconfigModal").modal('hide');
}

//保存调研表
function saveMaterialConfig(){
	var identification = $("#identification").val();
	var materialType = identification.split('|')[0];
	var index = identification.split('|')[1];
	var tableData = getTableDataByIndex(materialType,index);
	//获取调研表配置价格
	viewConfigPrice("cal");
	var optionalTransationPrice = $("#viewOptionalTransationPrice").val();
	var optionalActualPrice = $("#viewOptionalActualPrice").val();
	tableData.optionalTransationPrice = toDecimal2(optionalTransationPrice);
	tableData.optionalActualPrice = toDecimal2(optionalActualPrice);
	tableData = calPrice(tableData);
	//更新表格价格数据
	updateTableRowPrice(materialType,index,tableData);
	var configData = new Object();
	var remark = $("#configRemark").val();
	var configTableData = $("#configTable").bootstrapTable('getData');
	var attachs = $("#itemFileList").bootstrapTable('getData');
	configData.configTableData = configTableData;
	configData.remark = remark
	configData.attachments = attachs
	localStorage.setItem(identification, JSON.stringify(configData));
	$("#materialconfigModal").modal('hide');
}

//更新行项目价格信息
function updateTableRowPrice(materialsType,index,tableData){
	if(materialsType=='T101'){
		$('#materialsTableall1').bootstrapTable('updateRow',{index: index, row: tableData});
	}else if(materialsType=='T102'){
		$('#materialsTableall2').bootstrapTable('updateRow',{index: index, row: tableData});
	}else if(materialsType=='T103'){
		$('#materialsTableall3').bootstrapTable('updateRow',{index: index, row: tableData});
	}else if(materialsType=='T104'){
		$('#materialsTableall4').bootstrapTable('updateRow',{index: index, row: tableData});
	}else if(materialsType=='T105'){
		$('#materialsTableall5').bootstrapTable('updateRow',{index: index, row: tableData});
	}else if(materialsType=='T106'){
		$('#materialsTableall6').bootstrapTable('updateRow',{index: index, row: tableData});
	}
	$("#materialsTable").bootstrapTable('updateRow',{index: tableData.allIndex, row: tableData})
}

//计算可选项价格和总价格
function calPrice(tableData){
	//数量
	var quantity = tableData.quantity;
	//产品实卖价
	var actualPrice = tableData.actualPrice;
	//可选项实卖价格
	var optionalActualPrice = tableData.optionalActualPrice;
	//可选项实卖金额
	var optionalActualAmount = toDecimal2(quantity*optionalActualPrice);
	//转移价
	var transcationPrice = tableData.transcationPrice;
	//可选项转移价
	var optionalTransationPrice = tableData.optionalTransationPrice;
	//实卖价合计
	var actualPriceSum = toDecimal2(parseFloat(optionalActualPrice)+parseFloat(actualPrice));
	//实卖金额合计
	var actualAmountSum = toDecimal2(quantity*actualPriceSum);
	//转移价合计
	var transactionPriceSum = toDecimal2(parseFloat(transcationPrice)+parseFloat(optionalTransationPrice));
	
	tableData.optionalActualAmount = optionalActualAmount;
	
	tableData.actualPriceSum = actualPriceSum;
	
	tableData.actualAmountSum = actualAmountSum;
	
	return tableData;
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
	var identification = type+'|'+newIndex;
	var rowNumber = (newIndex+1)*10;
	var row = {
			rowNum:rowNumber,
			materialName:materialsTableData.materialName,
			materialCode:materialsTableData.materialCode,
			identification:identification,
			clazzCode:materialsTableData.clazzCode,
			isConfigurable:materialsTableData.isConfigurable,
			isPurchased:materialsTableData.isPurchased,
			materialGroupName:materialsTableData.materialGroupName,
			materialGroupCode:materialsTableData.materialGroupCode,
			quantity:materialsTableData.quantity,
			unitName:materialsTableData.unitName,
			unitCode:materialsTableData.unitCode,
			standardPrice:materialsTableData.standardPrice,
			actualPrice:materialsTableData.actualPrice,
			actualAmount:materialsTableData.actualAmount,
			transcationPrice:materialsTableData.transcationPrice,
			acturalPricaOfOptional:materialsTableData.acturalPricaOfOptional,
			acturalPricaOfOptionalAmount:materialsTableData.acturalPricaOfOptionalAmount,
			transcationPriceOfOptional:materialsTableData.transcationPriceOfOptional,
			B2CPriceEstimated:materialsTableData.B2CPriceEstimated,
			B2CPriceEstimatedAmount:materialsTableData.B2CPriceEstimatedAmount,
			B2CCostOfEstimated:materialsTableData.B2CCostOfEstimated,
			actualPriceSum:materialsTableData.actualPriceSum,
			actualAmountSum:materialsTableData.actualAmountSum,
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
		    	seq:count+1,
		    	pca: pca,
		    	provinceCode:provinceValue,
		    	provinceName:province,
		    	cityCode:cityValue,
		    	cityName:city,
				districtCode:areaValue,
				districtName:area,
		    	address:shippingAddress
		    }
		});
	}else{
		$("#addressTable").bootstrapTable('updateRow', {
		    index: rowIndex,
		    row: {
		    	seq:parseInt(rowIndex)+1,
		    	pca: pca,
		    	provinceCode:provinceValue,
		    	provinceName:province,
		    	cityCode:cityValue,
		    	cityName:city,
				districtCode:areaValue,
				districtName:area,
		    	address:shippingAddress
		    }
		});
	}
	$("#addressModal").modal('hide');
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
	$("#selectProvince").val(row.provinceCode).change();
	$("#citySelect").val(row.cityCode).change();
	$("#selectDistrict").val(row.districtCode);
	$("#shippingAddress").val(row.address);
}

function removeAddress(index){
	var delIndex = [parseInt(index)+1];
	$('#addressTable').bootstrapTable('remove', {
		field: "seq",
        values: delIndex
    });
	
	if(status==null||status==""||status=="undefined"){
		var count = $('#addressTable').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "seq",
					value : i+1
				}
			$('#addressTable').bootstrapTable("updateCell",rows);
		}
	}	
}

function setItemRequirementPlan(obj){
	var b2cValue = $(obj).val();
	if(b2cValue!=''){
		$("#itemRequirementPlan").val("001");
	}else{
		$("#itemRequirementPlan").val("004");
	}
}

//删除已上传文件
function removeFile(type_index){
	var type = type_index.split(',')[0];
	var index = type_index.split(',')[1];
	$('#'+type).bootstrapTable('remove', {
        field: "index",
        values: index
    });
}
//下载已上传文件
function downloadFile(fileInfo){
	var fileName = fileInfo.split(',')[0];
	var fileUrl = fileInfo.split(',')[1];
	var myForm = document.createElement("form");
    myForm.method = "get";
    myForm.action = ctxPath+"order/download";
    var seq = document.createElement("input");
    seq.setAttribute("name", "fileName");
    seq.setAttribute("value", fileName);
    var seq1 = document.createElement("input");
    seq1.setAttribute("name", "fileUrl");
    seq1.setAttribute("value", fileUrl);
    myForm.appendChild(seq);
    myForm.appendChild(seq1);
    document.body.appendChild(myForm);
    myForm.submit();
    document.body.removeChild(myForm);  
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
		 var items = $("#materialsTable").bootstrapTable('getData');
		 if(items.length==0){
			 layer.alert('请添加购销明细', {icon: 5});
			 return
		 }
		var bootstrapValidator = $("#orderForm").data('bootstrapValidator');
		bootstrapValidator.validate();
		if(!bootstrapValidator.isValid()){ 
			layer.alert('订单信息录入有误，请检查后提交', {icon: 5});
			expandAll()
			return
		}
		
	}
	$('#transferType').attr("disabled",false);
	$("#currency").attr("disabled",false);
	 /*var version = $("#version").val();
	 var payment = new Object();
	 payment['termCode'] = $("#paymentType").val();
	 payment['termName'] = $("#paymentType").find("option:selected").text();
	 payment['percentage'] = "1";
	 payment['payDate'] = $("#inputDate").val();*/
	 //获取下拉框name
	 getSelectName();
	 //初始化订单状态，防止提交报错
	 if(status==null||status==""||status=="undefined"){
		 $("#status").val("00");
	 }
	 var orderData = $("#orderForm").serializeObject(); 
	 $('#transferType').attr("disabled",true);
	 /*var payments=new Array();
	 payments.push(payment);
	 orderData.payments = payments;
	 orderData['currentVersion'] = version;
	 orderData['orderType'] = 'ZH0D';*/
	 var payments=new Array();
	 orderData.payments= payments;
	 var attachments = $("#fileList").bootstrapTable('getData');
	 orderData.attachments = attachments
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[i].identification];
		 items[i].isVirtual = 0;
		 if(configData){
			 var jsonObject = JSON.parse(configData);
			 var storedConfigs = jsonObject.configTableData;
			 var configs = new Array();
			 for(var j=0;j<storedConfigs.length;j++){
				 var config = new Object();
				 config['keyCode'] = storedConfigs[j].code;
				 config['valueCode'] = storedConfigs[j].configValueCode;
				 config['configurable'] = items[i].isConfigurable;
				 configs.push(config);
			 }
			 items[i]['configs'] = configs; 
			 if(!items[i].isConfigurable){
				if(items[i].itemCategory=='ZHD1'){
					items[i].itemCategory='ZHD2';
				}else if(items[i].itemCategory=='ZHD3'){
					items[i].itemCategory='ZHD4';
				}else{
					items[i].itemCategory='ZHR2';
				}			
			 }
			 items[i]['attachments'] = jsonObject.attachments;
			 items[i]['configComments'] = jsonObject.remark
		 } else{
			 items[i]['configs'] = null;
		 }	 	 
	 }
	 orderData.deliveryAddress = $("#addressTable").bootstrapTable('getData');
	 if(type){
		 $.ajax({
			    url: ctxPath+"order/submit",
			    contentType: "application/json;charset=UTF-8",
			    data: JSON.stringify(orderData),
			    type: "POST",
			    success: function(result) { 
			    	if(result == null || result.status != 'ok'){
			    		layer.alert("提交订单失败:" + (result != null ? result.msg : ""));
			    	}else{
			    		layer.alert('提交订单成功', {icon: 6});
			    		window.location.href = ctxPath+'menu/orderManageList';
			    	} 	
			    },
			    error: function(){
			    	layer.alert('提交失败', {icon: 5});
			    }
		});  
	 }else{
		 $.ajax({
			    url: ctxPath+"order/save",
			    contentType: "application/json;charset=UTF-8",
			    data: JSON.stringify(orderData),
			    type: "POST",
			    success: function(data) { 
			    	if(data == null || data.status != 'ok'){
			    		layer.alert("保存订单失败！" + (data != null ? data.msg : ""));
			    	}else{
			    		layer.alert('保存成功', {icon: 6});
			    		window.location.href = ctxPath+'menu/orderManageList';
			    	} 	
			    },
			    error: function(){
			    	layer.alert('保存失败', {icon: 5});
			    }
		}); 
	 }
	 
}


function goBpm(){	 
	 var items = $("#materialsTable").bootstrapTable('getData');
	 if(items.length==0){
		 layer.alert('请添加购销明细', {icon: 5});
		 return
	 }
	var bootstrapValidator = $("#orderForm").data('bootstrapValidator');
	bootstrapValidator.validate();
	if(!bootstrapValidator.isValid()){ 
		layer.alert('订单信息录入有误，请检查后提交', {icon: 5});
		expandAll()
		return
	}
	$('#transferType').attr("disabled",false);
	$("#currency").attr("disabled",false);
	 /*var version = $("#version").val();
	 var payment = new Object();
	 payment['termCode'] = $("#paymentType").val();
	 payment['termName'] = $("#paymentType").find("option:selected").text();
	 payment['percentage'] = "1";
	 payment['payDate'] = $("#inputDate").val();*/
	 //获取下拉框name
	 getSelectName();
	 var orderData = $("#orderForm").serializeObject(); 
	 $('#transferType').attr("disabled",true);
	 /*var payments=new Array();
	 payments.push(payment);
	 orderData.payments = payments;
	 orderData['currentVersion'] = version;
	 orderData['orderType'] = 'ZH0D';*/
	 var payments=new Array();
	 orderData.payments= payments;
	 var attachments = $("#fileList").bootstrapTable('getData');
	 orderData.attachments = attachments
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[i].identification];
		 items[i].isVirtual = 0;
		 if(configData){
			 var jsonObject = JSON.parse(configData);
			 var storedConfigs = jsonObject.configTableData;
			 var config = new Object();
			 var configs = new Array();
			 for(var j=0;j<storedConfigs.length;j++){
				 config['keyCode'] = storedConfigs[j].code;
				 config['valueCode'] = storedConfigs[j].configValueCode;
				 config['configurable'] = items[i].isConfigurable;
				 configs.push(config);
			 }
			 items[i]['configs'] = configs; 
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
	 orderData.deliveryAddress = $("#addressTable").bootstrapTable('getData');
	 $.ajax({
		    url: ctxPath+"order/submitbpm",
		    contentType: "application/json;charset=UTF-8",
		    data: JSON.stringify(orderData),
		    type: "POST",
		    success: function(result) { 
		    	if(result == null || result.status != 'ok'){
		    		layer.alert("提交BPM失败：" + (result != null ? result.msg : ""));
		    	}else{
		    		layer.alert('提交BPM成功', {icon: 6});
		    		window.location.href = ctxPath+'menu/orderManageList';
		    	} 	
		    },
		    error: function(){
		    	layer.alert('提交失败', {icon: 5});
		    }
	});  
}


function getSelectName() {
	var incotermCode = $("#incoterm").val();
	if(incotermCode){
		$("#incotermName").val(intercoms[incotermCode]);
	}
	var salesType = $("#saleType").val();
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
	
	$("#receiveTypeName").val($("#receiveType").find("option:selected").text());
	$("#industryCode").val($("#industryCode").find("option:selected").text());
}


//调研表查看物料
function viewConfigPrice( type){
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
	    url: ctxPath+"order/material/configuration",
	    contentType: "application/json;charset=UTF-8",
	    data: JSON.stringify(formData),
	    type: "POST",
	    dataType: "json",
	    success: function(res) { 
	    	if(type=="view"){
	    		if(res.status!="ok"){
		    		$("#viewError").attr("style","display:block;");
		    	}else{
		    		$("#moreConfig").attr("style","display:block;");
			    	$("#viewOptionalTransationPrice").val(res.data.transferPrice);
			    	$("#viewOptionalActualPrice").val(res.data.price);
		    	}	    
	    	}else{
	    		$("#viewOptionalTransationPrice").val(res.data.transferPrice);
		    	$("#viewOptionalActualPrice").val(res.data.price);
	    	}
	    		
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
	if(!$("#saleType").val()){
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
	    url: ctxPath+"order/grossprofit",
	    contentType: "application/json;charset=UTF-8",
	    data: JSON.stringify(orderData),
	    type: "POST",
	    success: function(data) { 
	    	$("#grossProfitTable").bootstrapTable('load', data.data);
	    },
	    error: function(){
	    	layer.alert('毛利率查看失败', {icon: 5});
	    }
	});  
}


//查看合同
function viewContract(){
	var orderInfoId = $("#orderInfoId").val();
	var url = ctxPath+"order/"+parseInt(orderInfoId)+"/contract";
    $.ajax({
        type: "post",
        url: url,
        data: null,
        dataType: "json",
        success: function (data) {
        	if(data.data == null){
        		$('#noContractModal').modal('show');
	    	}else if(data.status != 'ok'){
	    		layer.alert('查看合同失败', {icon: 6});
	    		$('#mytab').bootstrapTable('refresh');
	    	}else{
	    		var contract = data.data;
	    		showEditModal(contract);
	    	} 	
        }
    });
}

//显示合同编辑对话框
function showEditModal(contract) {	
	var html = template('contract-edit-tpl', contract);
	$("#contractDialogBody").html(html);
	
	if (!contract.isedit) {
		$(":input", "#contractForm").each(function(index,element) {
			$(element).attr('disabled', true);
		});
	}
	$("#contractDialog").modal('show');
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

var fileListColumns=[
	{
		 field: 'type',
		 title: '',
		 visible:false
	},
	{
		 field: 'index',
		 title: '',
		 visible:false
	},
	{
		 field: 'id',
		 title: '',
		 visible:false
	},
	{
		 field: 'orderInfoId',
		 title: '',
		 visible:false
	},
	{
		 field: 'fileName',
		 title: '已上传文件名称',
		 width:'75%',
		 formatter: function(value, row, index) {
		    	var actions = [];
				actions.push('<a href="javascript:void(0)" onclick="downloadFile(\'' + value+','+row.fileUrl + '\')">'+value+'</a>');
				return actions.join('');
		}
	},{
		 field: 'fileUrl',
		 title: '文件路径',
		 visible:false
	},{
	    title: '操作',
	    align: 'center',
	    width:'25%',
	    formatter: function(value, row, index) {
	    	var actions = [];
			actions.push('<a class="btn" onclick="removeFile(\'' + row.type+','+index + '\')"><i class="fa fa-remove"></i>删除</a>');
			return actions.join('');
		}
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

var customerTableColumn = [ {
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
    field: 'seq',
    width:'5%'
},{
	title : '省市区',
	field : 'pca',
	width:'35%'
}, 
{
	field:'provinceCode',
	visible:false
},
{
	field:'provinceName',
	visible:false
},
{
	field:'cityCode',
	visible:false
},
{
	field:'cityName',
	visible:false
},{
	field:'districtCode',
	visible:false
},
{
	field:'districtName',
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
		actions.push('<a class="btn" id="editAddressBt" onclick="editAddress(\'' + index + '\')"><i class="fa fa-edit"></i>编辑</a> ');
		actions.push('<a class="btn" id="removeAddressBt" onclick="removeAddress(\'' + index + '\')"><i class="fa fa-remove"></i>删除</a>');
		return actions.join('');
    }
}]

var materialsAddressColumns = [{
	title:'行号',
    field: 'seq',
    width:'5%'
},{
	title : '省市区',
	field : 'pca',
	width:'35%'
},
{
	field:'provinceCode',
	visible:false
},
{
	field:'provinceName',
	visible:false
},
{
	field:'cityCode',
	visible:false
},
{
	field:'cityName',
	visible:false
},
{
	field:'districtCode',
	visible:false
},{
	field:'districtName',
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
	title:'',
	visible:false,
	field:'configValueCode'
},
{
	title:'配置值',
	field:'configs',
	width:'50%',
	formatter: function(value, row, index) {
		var id="configsId"+index;
    	var start = '<select class="form-control" id=\'' + id + '\' name="configValueCode" onchange="setConfigValueCode(this,\'' + index + '\')">';
    	var end = '</select>';
    	var configIdValue;
    	if(row.configValueCode){
    		$.each(value,function(index,item){
        		if(item.code==row.configValueCode){
        			start+='<option value=\'' + item.code + '\' selected = "selected">' + item.name + '</option>';	
        		}else{
        			start+='<option value=\'' + item.code + '\'>' + item.name + '</option>'
        		}	
        	})
    	}/*else{
    		$.each(value,function(index,item){
        		if(item.default){
        			start+='<option value=\'' + item.code + '\' selected = "selected">' + item.name + '</option>';
        			configIdValue = item.code;
        		}else{
        			start+='<option value=\'' + item.code + '\'>' + item.name + '</option>'
        		}	
        	})
        	$("#"+id).val(configIdValue).change();
    		$("#configTable").bootstrapTable('updateCell', {
        	    index: index,
        	    field:'configValueCode',
        	    value:configIdValue
        	});
    	}
    	*/
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
			columns : tableColumns,
			responseHandler : function(res){
				if (res.status != 'ok') {
					layer.alert(res.msg);
					return {};
				}
				if(res.data.rows){
					return {
						total: res.data.total,
						rows: res.data.rows
					}	
				}else{
					return res.data;
				}
				
			},
		})
	};
	return oTableInit;
};