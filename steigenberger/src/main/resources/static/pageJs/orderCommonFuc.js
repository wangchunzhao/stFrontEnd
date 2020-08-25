//设置特批是否选中
function setSpecialChecked(){
	if($("#isUrgentDelivery").val()=='1'){
		$('#specialShipmentValue').prop('checked',true);
	}
	if($("#isSpecialOrder").val()=='1'){
		$('#isSpecialOrderValue').prop('checked',true);
	}
}

//查看订单页面不能修改
function disableAll(){ 
	  var form=document.forms[0];
	  for(var i=0;i<form.length;i++){
	    var element=form.elements[i];
	    element.disabled=true;
	  }
	  enableButton();
}
function sapOrderCheck(){
	if($("#hasSendSap").val()=='true'){
		$("#contractNumber").attr('readonly',true);
		$("#saleType").attr('disabled',true);
		$("#showCustomer").attr('disabled',true);
		$("#currency").attr('disabled',true);
		$("#contractAmount").attr('readonly',true);
	}
}
function getSapItemStatus(){
	var contractNumber = $("#contractNumber").val();
	if($("#hasSendSap").val()=='true'){
		$.ajax({
	        type: "get",
	        url: ctxPath+"order/"+contractNumber+"/sapstatus",
	        data: null,
	        dataType: "json",
	        success: function (data) {
	        	sapItemStatus = data.data.items;
	        }
		});
	}
}
//设置按钮可用
function enableButton(){
	  $(".close").attr('disabled',false);
	  $("#backButton").attr('disabled',false);
	  $("#grossProfitCloseBt").attr('disabled',false);
	  $("#grossProfitExportBt").attr('disabled',false);
	  $("#wtwGrossProfitExportBt").attr('disabled',false);
	  $("#showGrossProfitBt").attr('disabled',false);
	  $("#showWtwGrossProfitBt").attr('disabled',false);
	  $("#expandBt").attr('disabled',false);
	  $("#closeBt").attr('disabled',false);
	  $("#approve").attr('disabled',false);
	  $("#version").attr('disabled',false);
	  $(".viewDisable").attr('disabled',true);
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
	    	if(status==null||status==""||status=="undefined"){
	    		$("#salesName").val(data.name);
		    	$("#salesTel").val(data.tel);
		    	$("#salesCode").val(data.userIdentity);
	    	}
	    	$.each(data.roles,function(index,role){
	    		if(role.id==7){
	    			$("#wtwGrossProfit").attr("style","display:block;");
	    		}
	    	});
	    	
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
	$('#specialShipment').collapse('hide');
	$('#infoCheck').collapse('show');
	$('#attachmentInfo').collapse('hide');
	
}

//修改订单查看订单，回显购销明细数据
function fillItems(){
	if(items){
		for(var i=0;i<items.length;i++){
			var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
			var materialType = items[i].stMaterialGroupCode;	
			var rowData = fillItemToTableRow(items[i]);
			calPrice(rowData);
			rowData['materialType'] = materialType;
			var configAttachments = items[i].attachments
			var identification = rowData.rowNum;
			//添加调研表
			if(rowData.isConfigurable&&items[i].configs!=null){
				fillConfigsForMaterial(identification,items[i].configs,items[i].configComments,items[i].mosaicImage,rowData.materialCode,rowData.clazzCode,configAttachments);
			}	
			$("#materialsTable").bootstrapTable('insertRow', {
				index: countMaterialsTable,
				row: rowData
			});
		}
	}
	getAllCountFiled();
	//setTableToDifTab();
	if($("#stOrderType").val()=="2"){
		calMergeDiscount();
	}
}

function fillConfigsForMaterial(identification,configs,configRemark,mosaicImage,materialCode,clazzCode,configAttachments){
	var materialDefaultConfigs = getDefaultConfigs(materialCode,clazzCode);
	var editConfigs = [];
	$.each(materialDefaultConfigs,function(defaultIndex,defaultItem){
		$.each(configs,function(index,item){
			if(item.keyCode==defaultItem.code){
				var config = defaultItem;
				config["configValueCode"] = item.valueCode
				$.each(defaultItem.configs,function(defaultItemIndex,value){
					if(value.default){
						config["defaultConfig"] = value.code
					}
				})
				//没有默认的取第一个
				if(!config.defaultConfig){
					config["defaultConfig"] = defaultItem.configs[0].code
				}
				editConfigs.push(config)
				
			}
		})
	})
	var configData = new Object();
	configData.configTableData = editConfigs;
	configData.remark = configRemark;
	configData.mosaicImage = mosaicImage;
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
	var transactionPrice = toDecimal2(data.transactionPrice);
	var optionalActualPrice = toDecimal2(data.optionalActualPrice);
	var optionalTransationPrice = toDecimal2(data.optionalTransactionPrice);
	var b2cEstimatedPrice = toDecimal2(data.b2cEstimatedPrice);
	var b2cEstimatedAmount = toDecimal2((data.b2cEstimatedPrice)*quantity);
	var b2cEstimatedCost = toDecimal2(data.b2cEstimatedCost);
	var retailPrice = toDecimal2(data.retailPrice);
	var retailAmount = toDecimal2(retailPrice*parseFloat(quantity));
	var actualPrice = toDecimal2(data.actualPrice);
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
	var discount = data.discount;
	var row = {
			rowNum:data.rowNum,
			materialName:data.materialName,
			materialCode:data.materialCode,
			itemStatus:data.itemStatus,
			clazzCode:data.clazzCode,
			volumeCube:data.volumeCube,
			standardPrice:data.standardPrice,
			materialGroupCode:data.materialGroupCode,
			isConfigurable:data.isConfigurable,
			isPurchased:data.isPurchased,	
			materialGroupName:data.materialGroupName,
			quantity:data.quantity,
			unitName:data.unitName,
			unitCode:data.unitName,
			actualPrice:actualPrice,
			transactionPrice:transactionPrice,
			optionalActualPrice:optionalActualPrice,
			optionalTransactionPrice:data.optionalTransactionPrice,
			optionalStandardPrice:data.optionalStandardPrice,
			optionalRetailPrice:data.optionalRetailPrice,
			b2cEstimatedPrice:b2cEstimatedPrice,
			b2cEstimatedAmount:b2cEstimatedAmount,
			b2cEstimatedCost:b2cEstimatedCost,
			retailPrice:retailPrice,
			retailAmount:retailAmount,
			discount:discount,
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
	        deliveryAddressSeq: data.deliveryAddressSeq,
	        volumeCube:data.volumeCube
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
	$('#taxCode').val($("#taxCodeValue").val());
	$('#currency').val($("#currencyName").val());
	$('#currency').trigger("change");
	$('#officeSelect').val($('#officeCode').val());
	$('#officeSelect').trigger("change");
	$('#selectGroup').val($('#groupCode').val());
	$("#orignalContractAmount").val($("#contractValue").val());
	$("#contractAmount").val($("#contractRmbValue").val());
	$("#incoterm").val($("#incotermItem").val());
	$("#transferType").val($("#transferTypeCode").val());
	$('#transferType').trigger("change");
	
	
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
		$("#customerIndustry").val(row.industryCode);
		$("#customerIndustryName").val(row.industryName);
		$("#customerIndustryCode").val(row.industryCodeCode);
		$("#customerIndustryCodeName").val(row.industryCodeName);
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
		$('#citySelect').selectpicker('refresh');
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
		$('#selectDistrict').selectpicker('refresh');
	} 		
}

function salesTypeChange(obj,offices,taxRate,exchangeRate){
	$('#transferType').attr("disabled",false);
	var saleType = $(obj).val();
	getCurrency(saleType,exchangeRate);
	getIncoterm(saleType);
	$("#taxCode").html('');
	if(saleType==20){
		var rate = taxRate[20];
		$.each(rate,function(index,item){
			$("#taxCode").append("<option value='"+item.code+"'>"+item.tax+"</option>");
		});
		
	}else if(saleType==10){
		var rate = taxRate[10];
		$.each(rate,function(index,item){
			$("#taxCode").append("<option value='"+item.code+"'>"+item.tax+"</option>");
		});
	}else{
		var rate = taxRate[10];
		$.each(rate,function(index,item){
			$("#taxCode").append("<option value='"+item.code+"'>"+item.tax+"</option>");
		});
	}
	var stOrderType = $("#stOrderType").val();
	if(saleType=="20"){
		$("#exchangeRate").val("");
		$("#contractAmount").val("");
		$("#freightDiv").show();
		$("#freightDiv1").show();
		if(stOrderType!=5){
			$('#selectProvince').val('');
			$('#citySelect').val('');
			$('#selectDistrict').val('');
			$('#citySelect').attr("disabled",true);
			$('#selectDistrict').attr("disabled",true);	
			$('#selectProvince').attr("disabled",true);
			$("#transferType").val('01');
			$('#transferType').attr("disabled",true);
			
			var addressData = $("#addressTable").bootstrapTable("getData");
			var shippingAddress = addressData.address
			$("#addressTable").bootstrapTable('updateRow', {
			    index: 0,
			    row: {
			    	seq:1,
			    	pca: null,
			    	provinceCode:null,
			    	provinceName:null,
			    	cityCode:null,
			    	cityName:null,
					districtCode:null,
					districtName:null,
			    	address:shippingAddress
			    }
			});
			updateAddressInProd(0);
		}
        if(stOrderType==5){
        	$('#incotermContect').val('青岛');
        }
		$('#currency').attr('disabled',false);
		$('#incoterm').attr("disabled",false);
		$('#incotermContect').attr("readonly",false);
		$('#installCode').val('');
		$('#installCode').attr("readonly",true);
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
		$('#incotermContect').val('');		
		$('#freightDiv').hide();
		$('#freightDiv1').hide();
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
		
		getAmount('#orignalContractAmount');
		if(saleType=="20"){
			var officeCode = $("#officeSelect").val();
			var groups = groupsMap[officeCode];
			$.each(groups, function (key,value) {
					$("#selectGroup").append("<option value='" + key + "'>" + value + "</option>");			
			});
		}
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
			$("#currency").append("<option selected value=" + item.code + ">" + item.name + "</option>");
			$("#exchangeRate").val(item.rate);
		});
		getExchangeRate("#currency",exchangeRates);
		
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
	var items = $("#materialsTable").bootstrapTable('getData');
	for(var i=0;i<items.length;i++){
		var tableData = items[i];
		calPrice(tableData);
		updateTableRowPrice(i,tableData);
	}
	getAllCountFiled();
	
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

//打开购销明细初始化
function addSubsidiary(){
	$("#subsidiaryForm").data("bootstrapValidator").resetForm();
	var orderType = $("#stOrderType").val();
	var long = $("#isLongterm").val();
	var special = $("#isSpecial").val();
	var customerName = $("#customerName").val();
	if(customerName==""){
		layer.alert('请先选择客户', {icon: 5});
		return
	}
	if(orderType!='5'&&$('#addressTable').bootstrapTable('getData').length==0){
		layer.alert('请先添加订单地址', {icon: 5});
		return
	}
	if($("#saleType").val()==""){
		layer.alert('请先选择销售类型', {icon: 5});
		return
	}
	if($("#exchangeRate").val()==""){
		layer.alert('请先选择币种', {icon: 5});
		return
	}
	if(orderType=="2"&&long=='0'&&special=='0'&&($("#bodyDiscount").val()==""||$("#bodyDiscount").val()=="0.0")){
        layer.msg('折扣未录入！', function(){
        });
    }

    if($("#stOrderType").val()=="2"&&long=='0'&&special=='0'&&($("#mainDiscount").val()==""||$("#mainDiscount").val()=="0.0")){
        layer.msg('折扣未录入！', function(){
        });
    }
    $('#b2cEstimatedPrice').attr("disabled",false);
    $('#originalActualPrice').attr("disabled",true);
	if($("#saleType").val()=='20'){
		$('#originalActualPrice').attr("disabled",false);
		$('#b2cEstimatedPrice').attr("disabled",true);
	}
	if($("#stOrderType").val()=="2"&&(long=='1'||special=='1')){
		$("#discount").attr("disabled",false);
		$("#originalActualPrice").attr("disabled",false);
	}
	$('#subsidiaryModal').modal('show');
	$("#itemCategory").html('');
	$("#subsidiaryForm")[0].reset();
	$('#materialsModalType').val('new');
	initSubsidiary();
}



function initSubsidiary(){
		var addressTableData = $('#addressTable').bootstrapTable('getData')
		var row = addressTableData[0];
		$('#materialProvinceCode').val(row.provinceCode);
		$('#materialProvinceName').val(row.provinceName);
		$('#materialCityCode').val(row.cityCode);
		$('#deliveryAddressSeq').val(row.seq);
		$('#materialCityName').val(row.cityName);
		$('#materialAreaCode').val(row.districtCode);
		$('#materialAreaName').val(row.districtName);
		$('#materialModalAddress').val(row.address)
		if(row.pca==''||row.pca==null||row.pca==undefined){
			$("#materialAddress").val(row.address).change();
		}else{
			$("#materialAddress").val(row.pca+"/"+row.address).change();
		}
	
	var  standardDiscount = $("#standardDiscount").val();
	
    $('#discount').val(standardDiscount?toDecimal2(standardDiscount):toDecimal2(100));
	
	$('#amount').val(1);
	
	var specialRemark="";
	if($("#isTerm1").val()=="1"){
		specialRemark+=" 甲供";	
	}
	if($("#isTerm2").val()=="1"){
		specialRemark+=" 远程监控"
	}
	
	if($("#isTerm3").val()=="1"){
		specialRemark+="  地下室"
	}
	$("#specialRemark").val(specialRemark);
	var requiredDeliveryTime = $("#requiredDeliveryTime").val();
	$('#shippDate').datepicker("setDate",requiredDeliveryTime)
}

function applyItemShippDate(){
	var requiredDeliveryTime = $("#requiredDeliveryTime").val();
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<countMaterialsTable;i++){
		$("#materialsTable").bootstrapTable('updateByUniqueId', {
		    id:materialsTable[i].rowNum,
		    row: {
		    	shippDate:requiredDeliveryTime,
		    }
		});
	}
	layer.alert("应用成功！");
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
		$('#amount').attr("disabled",false);
		$('#originalActualPrice').attr("disabled",true);
		$('#originalTransationPrice').attr("disabled",true);
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
//行项目类别对应关系
function getItemCategory(configure,data){
	var code = data.code||data.materialCode;
	var stOrderType =$("#stOrderType").val(); 
	if(stOrderType=="3"||stOrderType=="4"){
		if(configure=='true'&&(code!='BG1R8R00000-X'&&code!='BG1R8L00000-X'&&code!='BG1GD1000000-X')){
			// 大客户可配置
			$("#itemCategoryContent").append("<select class='form-control' name='itemCategory' id='itemCategory' onchange='itemCategoryChange(this)'><option value='ZHT1'>标准</option><option value='ZHT3'>免费</option><option value='ZHR1'>退货</option></select>");	
		}else{
			// 大客户不可配置
			if(code!='BG1R8R00000-X'&&code!='BG1R8L00000-X'&&code!='BG1GD1000000-X'){
				$("#itemCategoryContent").append("<select class='form-control' name='itemCategory' id='itemCategory' onchange='itemCategoryChange(this)'><option value='ZHT2'>标准</option><option value='ZHT4'>免费</option><option value='ZHR2'>退货</option><option value='ZHT6'>供应商直发</option></select>");
			}else{
				if(code=='BG1GD1000000-X'||code=='BG1R8R00000-X'){
					$("#itemCategoryContent").append("<input type='text' class='form-control' id='itemCategory'  name='itemCategory' value='ZH99'>")
				}else{
					$("#itemCategoryContent").append("<input type='text' class='form-control' id='itemCategory'  name='itemCategory' value='ZH98'>")
				}
				$("#itemCategory").attr("disabled",true);
			}
			
		}
	}else if(stOrderType=="1"||stOrderType=="2"){
		if(configure=='true'&&(code!='BG1R8R00000-X'&&code!='BG1R8L00000-X'&&code!='BG1GD1000000-X')){
			// 经销商可配置
			$("#itemCategoryContent").append("<select class='form-control' name='itemCategory' id='itemCategory' onchange='itemCategoryChange(this)'><option value='ZHD1'>标准</option><option value='ZHD3'>免费</option><option value='ZHR3'>退货</option></select>");	
		}else{
			// 经销商不可配置
			if(code!='BG1R8R00000-X'&&code!='BG1R8L00000-X'&&code!='BG1GD1000000-X'){
				$("#itemCategoryContent").append("<select class='form-control' name='itemCategory' id='itemCategory' onchange='itemCategoryChange(this)'><option value='ZHD2'>标准</option><option value='ZHD4'>免费</option><option value='ZHR4'>退货</option></select>");
			}else{
				if(code=='BG1GD1000000-X'){
					$("#itemCategoryContent").append("<input type='text' class='form-control' id='itemCategory'  name='itemCategory' value='ZH97'>")
				}else{
					$("#itemCategoryContent").append("<input type='text' class='form-control' id='itemCategory'  name='itemCategory' value='ZH98'>")
				}
				$("#itemCategory").attr("disabled",true);
			}
			
		}
	}else{
		if(configure=='true'){
			// 备货
			$("#itemCategoryContent").append("<select class='form-control' name='itemCategory' id='itemCategory' onchange='itemCategoryChange(this)'><option value='ZHM1'>标准</option></select>");	
		}else{
			$("#itemCategoryContent").append("<select class='form-control' name='itemCategory' id='itemCategory' onchange='itemCategoryChange(this)'><option value='ZHM2'>标准</option></select>");	
		}
	}
}

//将查出来的物料信息填充到各个field中
function fillMaterailValue(data){
	$("#itemCategoryContent").html('');
	var configure = data.configurable+'';
	getItemCategory(configure,data);
	$("#materialTypeName").val(data.description).change();
	$("#materialCode").val(data.code);
	//不可预估费特殊处理
	if(data.code=='BG1R8L00000-X'){
		$('#originalTransationPrice').attr("disabled",false);
		$('#amount').attr("disabled",true);
	}
	//冷库特殊处理BG1R8R00000-X
	if(data.code=='BG1R8R00000-X'){
		$('#originalActualPrice').attr("disabled",false);
		$('#amount').attr("disabled",true);
	}
	//其他项目收费特殊处理 BG1GD1000000-X
	if(data.code=='BG1GD1000000-X'){
		$('#originalActualPrice').attr("disabled",false);
		$('#amount').attr("disabled",true);
	}
	if(data.code!='BG1R8R00000-X'&&data.code!='BG1R8L00000-X'&&data.code!='BG1GD1000000-X'){
		$('#amount').attr("disabled",false);
		$('#originalActualPrice').attr("disabled",true);
		$('#originalTransationPrice').attr("disabled",true);
	}
	if($("#saleType").val()=='20'&&(data.code!='BG1R8R00000-X'&&data.code!='BG1R8L00000-X'&&data.code!='BG1GD1000000-X')){
		$('#originalActualPrice').attr("disabled",false);
		$('#b2cEstimatedPrice').attr("disabled",true);
	}

	if(data.purchased||data.purchased=='true'){
		$('#isPurchased').val('自制');
		$('#purchasedCode').val(data.purchased)
	}else{
		$('#isPurchased').val('外购');
	}
	$("#materialTypeName").val(data.description);	
	$("#materialGroupName").val(data.groupName);
	$("#groupCode").val(data.groupCode);
	$("#isConfigurable").val(data.configurable);
	var materialsType = data.stGroupCode;
	if($("#stOrderType").val()=="2"&&($("#isLongterm").val()=='1'||$("#isSpecial").val()=='1')){
		$("#discount").attr("disabled",false);
		$("#originalActualPrice").attr("disabled",false);
	}else{
		if($("#stOrderType").val()=="2"&&(materialsType=='T101'||materialsType=='T105')){
			var discount = $("#bodyDiscount").val();
			$("#discount").val(discount);
		}
		if($("#stOrderType").val()=="2"&&(materialsType=='T102'||materialsType=='T103')){
			var discount = $("#mainDiscount").val();
			$("#discount").val(discount);
		}
	}
	//虚拟物料折扣100%
	if($("#stOrderType").val()=="2"&&(data.code=='BG1GD1000000-X'||data.code=='BG1GD1000000-X'||data.code=='BG1R8R00000-X')){
		$("#discount").val(100);
	}
	if(($("#stOrderType").val()=="3"||$("#stOrderType").val()=="4")&&(data.code!='BG1R8R00000-X'&&data.code!='BG1R8L00000-X'&&data.code!='BG1GD1000000-X')){
		$("#discount").attr("disabled",true);
		$("#discount").val(100);
		$("#originalActualPrice").attr("disabled",false);
	}
	
	
	$("#materialType").val(materialsType);
	$("#unitName").val(data.unitName);
	$("#unitCode").val(data.unitCode);
	$("#materialClazzCode").val(data.clazzCode);
	//标准价
	$("#standardPrice").val(toDecimal2(data.standardPrice));
	//市场零售价
	$("#retailPrice").val(toDecimal2(data.retailPrice));
	$("#yearPurchasePrice").val(toDecimal2(data.annualPrice))
	var yearPurchasePrice = $("#yearPurchasePrice").val();
	if(($("#stOrderType").val()=='3'||$("#stOrderType").val()=='4')&&yearPurchasePrice&&yearPurchasePrice!='0.00'){
		$("#retailPrice").val(toDecimal2(yearPurchasePrice));
	}
	//转移价
	$("#transactionPrice").val(toDecimal2(data.transcationPrice));
	initMaterialPrice();
	if($("#stOrderType").val()=='5'){
		$("#actualPrice").val(toDecimal2(0));
		$("#originalActualPrice").val(toDecimal2(0));
		$("#originalActualPrice").trigger("oninput");
		$("#b2cEstimatedPrice").attr("disabled",true);
	}
	$('#volumeCube').val(data.materialSize)	
}
//行项目类别变化
function itemCategoryChange(obj){
	var category = $(obj).val();
	if(category=='ZHD4'||category=='ZHD3'||category=='ZHT3'||category=='ZHT4'){
		$("#originalActualPrice").val(0.00);
		$("#originalActualPrice").trigger("oninput");
		$("#b2cEstimatedPrice").val(0.00);
		$("#b2cEstimatedPrice").trigger("oninput");
	}else{
		//零售价
		var retailPrice = $("#retailPrice").val();
		//汇率
		var exchangeRate = $("#exchangeRate").val();
		
		//折扣率
		var discountValue = $("#discount").val();
		discountValue = (discountValue/100).toFixed(4);

		//产品实卖单价人民币
		var actualPrice = toDecimal2(parseFloat(retailPrice)*discountValue);
		$("#actualPrice").val(toDecimal2(actualPrice));
		
		//产品实卖单价凭证货币
		var originalActualPrice = toDecimal2(actualPrice/parseFloat(exchangeRate));
		$("#originalActualPrice").val(toDecimal2(originalActualPrice));
		$("#originalActualPrice").trigger("oninput");

	}
}
function initMaterialPrice(){
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	
	//折扣率
	var discountValue = $("#discount").val();
	discountValue = (discountValue/100).toFixed(4);
	
	//数量
	var amount = $("#amount").val();
	
	//零售单价
	var retailPrice = $("#retailPrice").val();
	
	//产品实卖单价人民币
	var actualPrice = toDecimal2(parseFloat(retailPrice)*discountValue);
	$("#actualPrice").val(toDecimal2(actualPrice));
	
	//产品实卖单价凭证货币
	var originalActualPrice = toDecimal2(actualPrice/parseFloat(exchangeRate));
	$("#originalActualPrice").val(toDecimal2(originalActualPrice));
	
	//产品实卖金额凭证货币
	var actualPriceAmount = toDecimal2(amount*(parseFloat(originalActualPrice)));
	$("#actualAmount").val(actualPriceAmount);
	
	//产品转移单价人民币
	var transcationPrice = $("#transactionPrice").val(); 
	//产品转移单价凭证货币
	var originalTransationPrice = toDecimal2(parseFloat(transcationPrice)/parseFloat(exchangeRate));
	$("#originalTransationPrice").val(originalTransationPrice);
	
	//产品转移金额
	var transactionPriceAmount = toDecimal2(amount*(parseFloat(originalTransationPrice)));
	$("#transactionPriceAmount").val(transactionPriceAmount);
	
    //可选项实卖单价	
	$("#originalOptionalActualPrice").val(toDecimal2(0.00));	
	$("#optionalActualPrice").val(toDecimal2(0.00));
	var originalOptionalActualPrice = $("#originalOptionalActualPrice").val();
	
	//可选项实卖金额
	$("#optionalActualAmount").val(toDecimal2(amount*parseFloat(originalOptionalActualPrice)));
	var optionalActualAmount = $("#acturalPriceOfOptionalAmount").val();
	
	//可选项转移单价人民币
	$("#optionalTransationPrice").val(toDecimal2(0.00));
	var optionalTransationPrice = $("#optionalTransationPrice").val();
	
	//可选项转移单价凭证货币
	$("#originalOptionalTransationPrice").val(toDecimal2(0.00));
	var originalOptionalTransationPrice = $("#originalOptionalTransationPrice").val();
	
	//可选项转移金额
	var optionalTransationPriceAmount = toDecimal2(amount*parseFloat(originalOptionalTransationPrice));
	$("#optionalTransationPriceAmount").val(optionalTransationPriceAmount);
	
	//b2c预估单价  初始化为空
	$("#b2cEstimatedPrice").val();
	var b2cEstimatedPrice = $("#b2cEstimatedPrice").val()?$("#b2cEstimatedPrice").val():toDecimal2(0.00);
	
	//B2C预估金额  初始化为空
	var b2cEstimatedAmount = toDecimal2(amount*parseFloat(b2cEstimatedPrice));
	$("#b2cEstimatedAmount").val(b2cEstimatedAmount);
	
	//B2C预估成本单价人民币  初始化为空
	$("#b2cEstimatedCost").val(toDecimal2(0.00));
	var b2cEstimatedCost = $("#b2cEstimatedCost").val();
	//B2c预估成本单价凭证货币
	var originalB2cEstimatedCost = toDecimal2(0.00);
	
	//B2C预估成本金额
	var b2cEstimatedCostAmount = toDecimal2(amount*parseFloat(b2cEstimatedCost));
	$("#b2cEstimatedCostAmount").val(b2cEstimatedCostAmount);
	
	//实卖单价合计 
	var actualPriceSum = toDecimal2(parseFloat(originalActualPrice)+parseFloat(originalOptionalTransationPrice)+parseFloat(b2cEstimatedPrice));
	$("#actualPriceSum").val(actualPriceSum);
	
	//实卖金额合计
	var actualAmountSum = toDecimal2(amount*parseFloat(actualPriceSum));
	$("#actualAmountSum").val(actualAmountSum);
	
	//转移单价合计
	var transactionPriceSum = toDecimal2(parseFloat(originalTransationPrice)+parseFloat(optionalTransationPrice)+parseFloat(originalB2cEstimatedCost));
	$("#transactionPriceSum").val(transactionPriceSum);
	
	//转移金额合计
	var transactionAmountSum = toDecimal2(amount*parseFloat(transactionPriceSum));
	$("#transactionAmountSum").val(transactionAmountSum);
	
	//市场零售金额
	$("#retailAmount").val(toDecimal2(amount*parseFloat(retailPrice)));
}

//数量变化
function amountChange(){
	var amount = $("#amount").val();
	if(amount==''){
		return;
	}
	//产品实卖金额
	var actualAmount = toDecimal2(amount*(parseFloat($("#originalActualPrice").val())));
	$("#actualAmount").val(actualAmount);
	
	//产品转移金额
	var transactionPriceAmount = toDecimal2(amount*(parseFloat($("#originalTransationPrice").val())));
	$("#transactionPriceAmount").val(transactionPriceAmount);
	
	//可选项实卖金额
	var optionalActualAmount = toDecimal2(amount*parseFloat($("#originalOptionalActualPrice").val()));
	$("#acturalPriceOfOptionalAmount").val(optionalActualAmount);
	

	//可选项转移金额
	var optionalTransationPriceAmount = toDecimal2(amount*parseFloat($("#originalOptionalTransationPrice").val()));
	$("#optionalTransationPriceAmount").val(optionalTransationPriceAmount);
	
	//B2C预估金额  初始化为空
	var b2cEstimatedAmount = toDecimal2(amount*parseFloat($("#b2cEstimatedPrice").val()));
	$("#b2cEstimatedAmount").val(b2cEstimatedAmount);
	
	//B2C预估成本金额
	var b2cEstimatedCostAmount = toDecimal2(amount*parseFloat($("#b2cEstimatedCost").val()));
	$("#b2cEstimatedCostAmount").val(b2cEstimatedCostAmount);
	
	//实卖金额合计
	var actualAmountSum = toDecimal2(amount*parseFloat($("#actualPriceSum").val()));
	$("#actualAmountSum").val(actualAmountSum);
	
	//转移金额合计
	var transactionAmountSum = toDecimal2(amount*parseFloat($("#transactionPriceSum").val()));
	$("#transactionAmountSum").val(transactionAmountSum);
	
	//市场零售金额
	$("#retailAmount").val(toDecimal2(parseFloat($("#retailPrice").val())*amount));
}

//实卖价编辑
function originalActualPriceChange(){
	//数量
	var amount = parseFloat($("#amount").val());
	
	//汇率
	var exchangeRate = $("#exchangeRate").val(); 
	
	//产品实卖单价凭证货币
	var originalActualPrice = $("#originalActualPrice").val();
	
	//产品实卖单价人民币
	var actualPrice = parseFloat(originalActualPrice)*parseFloat(exchangeRate);	
	$("#actualPrice").val(actualPrice);
	
	//可选项实卖单价凭证货币
	var  originalOptionalActualPrice = $("#originalOptionalActualPrice").val()?parseFloat($("#originalOptionalActualPrice").val()):parseFloat(0.00);
	
	//B2C预估单价人民币
	var b2cEstimatedPrice = $("#b2cEstimatedPrice").val();
	
	//B2C预估计凭证货币
	var originalB2cPrice = toDecimal2(parseFloat(b2cEstimatedPrice)/parseFloat(exchangeRate))
	
	//产品实卖金额
	var actualAmount = toDecimal2(parseFloat(originalActualPrice)*amount);
	$("#actualAmount").val(actualAmount);
	
	//实卖单价小计
	var actualPriceSum = toDecimal2(parseFloat(originalActualPrice)+parseFloat(originalOptionalActualPrice)+parseFloat(originalB2cPrice));
	$("#actualPriceSum").val(actualPriceSum)
	
	//实卖金额合计
	var actualAmountSum = toDecimal2(amount*parseFloat(actualPriceSum));
	$("#actualAmountSum").val(actualAmountSum);
	
	//市场零售价
	var retailPrice = $("#retailPrice").val();
	
	//计算折扣
	if($("#stOrderType").val()=="2"&&retailPrice!='0.00'){
		var discount =toDecimal2( (parseFloat(actualPrice)/parseFloat(retailPrice))*100);
		$("#discount").val(discount);
	}
}

//折扣率比变化
function discountChange(){
	//数量
	var amount = $("#amount").val()?parseFloat($("#amount").val()):parseFloat(1.00);
	var discount = $("#discount").val();
	var discountValue = (discount/100).toFixed(4);
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	
	//市场零售价
	var retailPrice = $("#retailPrice").val();
	
	//产品实卖价人民币
	var actualPrice = parseFloat(retailPrice)*parseFloat(discountValue);
	$("#actualPrice").val(actualPrice);
	
	//产品实卖价凭证货币
	var originalActualPrice =toDecimal2( parseFloat(actualPrice)/parseFloat(exchangeRate));
	$("#originalActualPrice").val(originalActualPrice);
	
	//可选项实卖价凭证货币
	var  originalOptionalActualPrice =  parseFloat($("#originalOptionalActualPrice").val());
	
	//B2C预估价
	var b2cPrice = $("#b2cEstimatedPrice").val();
	//B2C预估计凭证货币
	var originalB2cPrice = toDecimal2(parseFloat(b2cPrice)/parseFloat(exchangeRate));
	
	//产品实卖金额
	var actualAmount = toDecimal2(parseFloat(originalActualPrice)*amount);
	$("#actualAmount").val(actualAmount);
	
	//实卖单价小计
	var actualPriceSum = toDecimal2(parseFloat(originalActualPrice)+parseFloat(originalOptionalActualPrice)+parseFloat(originalB2cPrice));
	$("#actualPriceSum").val(actualPriceSum)
	
	//实卖金额合计
	var actualAmountSum = toDecimal2(amount*parseFloat(actualPriceSum));
	$("#actualAmountSum").val(actualAmountSum);
}

//B2C预估价变化
function getB2CAmount(obj){
	//汇率
	var exchangeRate = $("#exchangeRate").val()?parseFloat($("#exchangeRate").val()):parseFloat(1.00);
	//数量
	var amount = $("#amount").val()?parseFloat($("#amount").val()):parseFloat(1.00);
	
	
	//B2C预估单价人民币
	var b2cPrice = $(obj).val()?parseFloat($(obj).val()):parseFloat(0.00);
	
	//B2C预估计凭证货币
	var originalB2cPrice = toDecimal2(parseFloat(b2cPrice)/parseFloat(exchangeRate))
		
	//B2C预估金额人民币
	var b2cEstimatedAmount = parseFloat(b2cPrice)*parseFloat(amount);
	$("#b2cEstimatedAmount").val(b2cEstimatedAmount);
	
	
	//B2C预估金额凭证货币
	var originalB2cEstimatedAmount = toDecimal2(parseFloat(amount)*(parseFloat(originalB2cPrice)));
	
	//产品实卖单价
	var originalActualPrice = $("#originalActualPrice").val()?parseFloat($("#originalActualPrice").val()):parseFloat(0.00);
	
	//可选项实卖价
	var  originalOptionalActualPrice =  $("#originalOptionalActualPrice").val()?parseFloat($("#originalOptionalActualPrice").val()):parseFloat(0.00);
	
	//实卖单价小计
	var actualPriceSum = toDecimal2(parseFloat(originalB2cPrice)+parseFloat(originalActualPrice)+parseFloat(originalOptionalActualPrice));
	$("#actualPriceSum").val(actualPriceSum);
	
	//实卖金额合计
	var actualAmountSum = toDecimal2(parseFloat(actualPriceSum)*parseFloat(amount));
	$("#actualAmountSum").val(actualAmountSum);
	
}
//B2C预估成本变化
function getB2CCostAmount(obj){
	//数量
	var amount = $("#amount").val();
	//汇率
	var exchangeRate = $("#exchangeRate").val()?parseFloat($("#exchangeRate").val()):parseFloat(1.00);
	
	//B2C成本
	var b2cCost = $(obj).val()?$(obj).val():parseFloat(0.00);
	var b2cEstimatedCostAmount =toDecimal2( amount*parseFloat(b2cCost));
	$("#b2cEstimatedCostAmount").val(b2cEstimatedCostAmount);
	//B2C成本凭证货币
	var originalB2cCost = toDecimal2(parseFloat(b2cCost)/parseFloat(exchangeRate))
	
	//产品转移单价凭证货币
	var originalTransationPrice = $("#originalTransationPrice").val()?$("#originalTransationPrice").val():parseFloat(0.00);	
	
	//可选项转移单价凭证货币
	var originalOptionalTransationPrice = $("#originalOptionalTransationPrice").val()?$("#originalOptionalTransationPrice").val():parseFloat(0.00);
	
	//转移单价小计
	var transactionPriceSum = toDecimal2(parseFloat(originalTransationPrice)+parseFloat(originalOptionalTransationPrice)+parseFloat(originalB2cCost));
	$("#transactionPriceSum").val(transactionPriceSum);
	//转移金额合计
	var transactionAmountSum = toDecimal2(parseFloat(amount)*parseFloat(transactionPriceSum));
	$("#transactionAmountSum").val(transactionAmountSum);
}

function getTransactionPrice(obj){
	//产品转移单价
	var originalTransationPrice = $(obj).val()?parseFloat($(obj).val()):parseFloat(0.00);
	//汇率
	var exchangeRate = $("#exchangeRate").val()?parseFloat($("#exchangeRate").val()):parseFloat(1.00);
	var transactionPrice = toDecimal2(originalTransationPrice*exchangeRate);
	$("#transactionPrice").val(transactionPrice);
	//数量
	var amount = $("#amount").val();
	
	//产品转移金额
	var transactionPriceAmount = toDecimal2(amount*(originalTransationPrice));
	$("#transactionPriceAmount").val(transactionPriceAmount);
	
	//可选项转移单价凭证货币
	var originalOptionalTransationPrice = $("#originalOptionalTransationPrice").val()?$("#originalOptionalTransationPrice").val():parseFloat(0.00);
	
	//B2C成本
	var b2cCost = $("#b2cEstimatedCost").val()?$("#b2cEstimatedCost").val():parseFloat(0.00);
	//B2C成本凭证货币
	var originalB2cCost = toDecimal2(parseFloat(b2cCost)/parseFloat(exchangeRate))
	
	//转移单价小计
	var transactionPriceSum = toDecimal2(parseFloat(originalTransationPrice)+parseFloat(originalOptionalTransationPrice)+parseFloat(originalB2cCost));
	$("#transactionPriceSum").val(transactionPriceSum);
	//转移金额合计
	var transactionAmountSum = toDecimal2(parseFloat(amount)*parseFloat(transactionPriceSum));
	$("#transactionAmountSum").val(transactionAmountSum);
	
}

//编辑购销明细
function editMaterials(editContent){
	var index = editContent.split('|')[1];
	var rowNumber = editContent.split('|')[0]
	var tableData =$('#materialsTable').bootstrapTable('getData')[index];	
	if($("#hasSendSap").val()=='true'&&tableData.itemStatus=='10'){
		$.each(sapItemStatus,function(index,itemObject){
			if(itemObject.rowNum==rowNumber){
				var rejectCode = itemObject.rejectedCode?itemObject.rejectedCode:"";
				var plannedOrder = itemObject.plannedOrder?itemObject.plannedOrder:"";
				var productionOrder = itemObject.productionOrder?itemObject.productionOrder:"";
				layer.alert('拒绝原因:'+rejectCode+" ;计划订单:"+plannedOrder+" ;生产工单:"+productionOrder);
			}
		});
	}
	$('#subsidiaryModal').modal('show');
	$('#amount').attr("disabled",false);
	$("#b2cEstimatedPrice").attr("disabled",false);
	$('#originalActualPrice').attr("disabled",true);
	$('#originalTransationPrice').attr("disabled",true);
	if(status=='01'){
		$("#b2cEstimatedCost").attr("disabled",false);
	}	
	if($("#stOrderType").val()=="2"&&($("#isLongterm").val()=='1'||$("#isSpecial").val()=='1')){
		$("#discount").attr("disabled",false);
		$("#originalActualPrice").attr("disabled",false);
	}
	$('#materialsModalType').val('edit');
	$("#itemCategoryContent").html('');
	var configable = tableData.isConfigurable+'';
	fillEditMaterailValue(tableData,index);
	var materialCode = $("#materialCode").val();
	getItemCategory(configable,tableData);
	$("#itemCategory").val(tableData.itemCategory);
	//不可预估费特殊处理
	if(materialCode=='BG1R8L00000-X'){
		$('#originalTransationPrice').attr("disabled",false);
		$('#amount').attr("disabled",true);
	}
	//冷库特殊处理BG1R8R00000-X
	if(materialCode=='BG1R8R00000-X'){
		$('#originalActualPrice').attr("disabled",false);
		$('#amount').attr("disabled",true);
	}
	//其他项目收费特殊处理 BG1GD1000000-X
	if(materialCode=='BG1GD1000000-X'){
		$('#originalActualPrice').attr("disabled",false);
		$('#amount').attr("disabled",true);
	}
	
	if($("#saleType").val()=='20'&&(materialCode!='BG1R8R00000-X'&&materialCode!='BG1R8L00000-X'&&materialCode!='BG1GD1000000-X')){
		$('#originalActualPrice').attr("disabled",false);
		$('#b2cEstimatedPrice').attr("disabled",true);
	}
	
	if($("#stOrderType").val()=="3"|$("#stOrderType").val()=="4"){
		$("#discount").attr("disabled",true);
		$("#discount").val(100);
		$("#originalActualPrice").attr("disabled",false);
	}
	
	if($("#stOrderType").val()=='5'){
		$("#b2cEstimatedPrice").attr("disabled",true);
	}
	
	//查看订单是禁用所有控件
	if(orderOperationType=="2"){
		 var form=$("#subsidiaryForm")[0];
		  for(var i=0;i<form.length;i++){
		    var element=form.elements[i];
		    element.disabled=true;
		  }
		  $("#configClose").attr("disabled",false);
	}
	var itemAmount = $("#amount").val();
	if($("#hasSendSap").val()=='true'&&tableData.itemStatus=='10'){
		$.each(sapItemStatus,function(index,itemObject){
			if(itemObject.rowNum==rowNumber){	
				$("#sapItemDeliveryAmount").val(itemObject.plannedIssueQuantity);
				$("#itemCategory").attr("disabled",true);	 
			}
		});
	}
}

//编辑购销明细时页面值回显
function fillEditMaterailValue(data,index){	
	$("#index").val(index);
	$("#purchasedCode").val(data.isPurchased);
	if(data.isPurchased){
		$("#isPurchased").val("自制");
	}else{
		$("#isPurchased").val("外购");
	}
	$('#volumeCube').val(data.volumeCube)
	$("#itemStatus").val(data.itemStatus);
	$("#shippDate").val(data.shippDate)
	$("#rowNumber").val(data.rowNum);
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
	$("#materialType").val(data.materialType);
	$("#unitName").val(data.unitName);
	$("#unitCode").val(data.unitCode);
	$("#materialClazzCode").val(data.clazzCode);
	$("#originalActualPrice").val(data.originalActualPrice);
	$("#actualPrice").val(data.actualPrice);
	$("#actualAmount").val(data.actualAmount);
	
	$("#transactionPrice").val(data.transactionPrice);  
	$("#originalTransationPrice").val(data.originalTransationPrice);
	$("#transactionPriceAmount").val(data.transactionPriceAmount);
	
	$("#originalOptionalActualPrice").val(data.originalOptionalActualPrice);
	$("#optionalActualPrice").val(data.optionalActualPrice);
	$("#optionalActualAmount").val(data.optionalActualAmount);
	
	$("#optionalTransationPrice").val(data.optionalTransactionPrice);
	$("#originalOptionalTransationPrice").val(data.originalOptionalTransationPrice);
	$("#optionalTransationPriceAmount").val(data.optionalTransationPriceAmount);
	
	$("#b2cEstimatedPrice").val(data.b2cEstimatedPrice);
	$("#b2cEstimatedAmount").val(data.b2cEstimatedAmount);
	
	
	$("#b2cEstimatedCost").val(data.b2cEstimatedCost);
	$("#b2cEstimatedCostAmount").val(data.b2cEstimatedCostAmount);
	
	$("#actualPriceSum").val(data.actualPriceSum);
	$("#actualAmountSum").val(data.actualAmountSum);
	
	$("#transactionPriceSum").val(data.transactionPriceSum);
	$("#transactionAmountSum").val(data.transactionAmountSum);
	
	$("#retailPrice").val(data.retailPrice);
	$("#retailAmount").val(data.retailAmount);
	
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
	$('#materialProvinceCode').val(data.provinceCode);
	$('#materialProvinceName').val(data.provinceName);
	$('#materialCityCode').val(data.cityCode);
	$('#materialCityName').val(data.cityName);
	$('#materialAreaCode').val(data.districtCode);
	$('#materialAreaName').val(data.districtName);
	$('#materialModalAddress').val(data.address);	
	$("#deliveryAddressSeq").val(data.deliveryAddressSeq);
}

//删除购销明细
function removeMaterials(rowNum){
	$('#materialsTable').bootstrapTable('removeByUniqueId', rowNum);
	localStorage.removeItem(rowNum);
	getAllCountFiled();
}

function cacelMaterials(rowNumIndex){
	var index = rowNumIndex.split('|')[1];
	var rowNum = rowNumIndex.split('|')[0];
	var data = $('#materialsTable').bootstrapTable('getRowByUniqueId', rowNum);
	data.itemStatus ='Z2'
	$("#materialsTable").bootstrapTable('updateRow', {
	    index: index,
	    row: data
	});
	getAllCountFiled();
}

function recoveryMaterials(rowNumIndex){
	var index = rowNumIndex.split('|')[1];
	var rowNum = rowNumIndex.split('|')[0];
	var data = $('#materialsTable').bootstrapTable('getRowByUniqueId', rowNum);
	data.itemStatus ='10'
	$("#materialsTable").bootstrapTable('updateRow', {
	    index: index,
	    row: data
	});
	getAllCountFiled();
}

//点击确认购销明细
function confirmMaterials(){
	if($("#stOrderType").val()=='5'){
		$('#subsidiaryForm').data('bootstrapValidator').enableFieldValidators('b2cEstimatedPrice',false)
	}
	var bootstrapValidator = $("#subsidiaryForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
    	return
    }
    //修改数量
    var itemAmount = $("#amount").val();
    //实际交货数量
    var IssuedQuantity = $("#sapItemDeliveryAmount").val();
    if(IssuedQuantity&&parseInt(itemAmount)<parseInt(IssuedQuantity)){
    	layer.alert('变更后数量不能小于已交货数量', {icon: 5});
		return;
    }
	var identification = $("#identification").val();
	var materialTypeName = $("#materialTypeName").val();
	var modalType = $("#materialsModalType").val();
	var editIndex = $("#index").val(); 
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	if(modalType=='new'){
		var rowData;
		if(countMaterialsTable==0){
			rowData = confirmRowData(10);
		}else{
			var lastRowData = $('#materialsTable').bootstrapTable('getData')[parseInt(countMaterialsTable)-1];
			rowData = confirmRowData(parseInt(lastRowData.rowNum)+10);
		}	
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row: rowData
		});		
	}else{
		var rowNumber = $("#rowNumber").val();
		var rowData = confirmRowData(rowNumber);	
		$("#materialsTable").bootstrapTable('updateRow', {
		    index: editIndex,
		    row: rowData
		});
	}
	$('#subsidiaryModal').modal('hide');
	//计算最早发货时间，最早出货时间，购销明细合计
	getAllCountFiled();
	//非标准折扣计算合并折扣
	if($("#stOrderType").val()=="2"){
		calMergeDiscount();
	}
}

function showTab(materialType){
	if(materialType=='T101'){
		$("#second").tab('show');
	}else if(materialType=='T102'){
		$("#third").tab('show');
	}else if(materialType=='T103'){
		$("#four").tab('show');
	}else if(materialType=='T104'){
		$("#five").tab('show');
	}else if(materialType=='T105'){
		$("#six").tab('show');
	}else if(materialType=='T106'){
		$("#seven").tab('show');
	}
}

function getAllCountFiled(){
	var tableData = $('#materialsTable').bootstrapTable('getData');
	//工厂最早交货时间
	var deliveryTime=[];
	//要求发货时间
	/*var  requiredDeliveryTime=[];*/
	//购销明细金额
	var itemsAmount = [];
	$.each(tableData,function(index,item){
		//需排除取消状态的行项目
		if(item.itemStatus!='Z2'&&item.itemCategory!='ZHR1'&&item.itemCategory!='ZHR2'&&item.itemCategory!='ZHR3'&&item.itemCategory!='ZHR4'){
			if(item.deliveryDate){
				deliveryTime.push(moment(item.deliveryDate));
			}
			/*if(item.shippDate){
				requiredDeliveryTime.push(moment(item.shippDate));
			}*/
			if(item.actualAmountSum){
				itemsAmount.push(item.actualAmountSum);
			}
		}
		if(item.itemCategory=='ZHR1'||item.itemCategory=='ZHR2'||item.itemCategory=='ZHR3'||item.itemCategory=='ZHR4'){
			$('#materialsTable>tbody tr:eq('+index+')').addClass('configtrRed');
			itemsAmount.push(-item.actualAmountSum);
		}
	})
	var totalAmount = toDecimal2(calculationAmount(itemsAmount));
	$("#itemsAmount").val(totalAmount);	
	$("#orignalContractAmount").val(totalAmount);
	if($("#exchangeRate").val()){
		var contractAmount =toDecimal2( totalAmount*parseFloat($("#exchangeRate").val()))
		$("#contractAmount").val(contractAmount);
	}
	/*var earlyRequiredDeliveryTime = compareDate(requiredDeliveryTime);
	if(earlyRequiredDeliveryTime){
		$("#requiredDeliveryTime").val(moment(earlyRequiredDeliveryTime).format('YYYY-MM-DD'));
	}else{
		$("#requiredDeliveryTime").val('');
	}	*/
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
		if(moment(item).isAfter(earlyDate)){
			earlyDate = item;
		}
	})
	return earlyDate;
}

//计算合并折扣
function calMergeDiscount(){
	//折后零售金额集合
	var discountRetailAmountsArray = new Array();
	//实际零售金额集合
	var retailAmountsArray = new Array();
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	if(countMaterialsTable==0){
		return;
	}
	for(var i=0;i<countMaterialsTable;i++){
		var materialRowData = materialsTable[i];
		var materialType = materialRowData.materialType;
		var materialCode = materialRowData.materialCode;
		if((materialType=="T101"||materialType=="T102"||materialType=="T103"||materialType=="T105")&&(materialCode!='BG1R8L00000-X'&&materialCode!='BG1R8R00000-X'&&materialCode!='BG1GD1000000-X')){
			var discount = materialRowData.discount;
			var retailAmount = materialRowData.retailAmount;
			var discountRetailAmount = (accDiv(accMul(retailAmount,discount),100)).toFixed(2);
			discountRetailAmountsArray.push(discountRetailAmount);
			retailAmountsArray.push(retailAmount);
		}
	}
	var discountRetailAmounts =parseFloat(0).toFixed(2);
	if(discountRetailAmountsArray.length==0){return;}
	$.each(discountRetailAmountsArray,function(index,value){
		discountRetailAmounts=accAdd(discountRetailAmounts,parseFloat(value));
	});
	var retailAmounts = parseFloat(0).toFixed(2);
	$.each(retailAmountsArray,function(index,value){
		retailAmounts=accAdd(retailAmounts,parseFloat(value));
	});
	var mergerDiscount = (accMul(accDiv(discountRetailAmounts,retailAmounts),100)).toFixed(2);
	$("#orderDiscount").val(mergerDiscount);
	$("#approveDiscount").val(mergerDiscount);
	
	
}

//购销明细行数据
function confirmRowData(rowNumber){
	var row = {
			rowNum:rowNumber,
			materialName:$("#materialTypeName").val(),
			materialCode:$("#materialCode").val(),
			clazzCode:$("#materialClazzCode").val(),
			isConfigurable:$("#isConfigurable").val(),
			isPurchased:$("#purchasedCode").val(),
			materialGroupName:$("#materialGroupName").val(),
			materialGroupCode:$("#groupCode").val(),
			materialType: $("#materialType").val(),
			quantity:$("#amount").val(),
			unitName:$("#unitName").val(),
			unitCode:$("#unitCode").val(),
			standardPrice:$("#standardPrice").val(),
			actualPrice:$("#actualPrice").val(),
			originalActualPrice:$("#originalActualPrice").val(),
			actualAmount:$("#actualAmount").val(),
			itemStatus:$("#itemStatus").val()?$("#itemStatus").val():'00',
			
			transactionPrice:$("#transactionPrice").val(),
			originalTransationPrice:$("#originalTransationPrice").val(),
			transactionPriceAmount:$("#transactionPriceAmount").val(),
			
			originalOptionalActualPrice:$("#originalOptionalActualPrice").val(),
			optionalActualPrice:$("#optionalActualPrice").val(),
			
			optionalActualAmount:$("#optionalActualAmount").val(),
			
			optionalTransactionPrice:$("#optionalTransationPrice").val(),
			originalOptionalTransationPrice:$("#originalOptionalTransationPrice").val(),
			optionalTransationPriceAmount:$("#optionalTransationPriceAmount").val(),
			
			b2cEstimatedPrice:$("#b2cEstimatedPrice").val(),
			b2cEstimatedAmount:$("#b2cEstimatedAmount").val(),
			
			b2cEstimatedCost:$("#b2cEstimatedCost").val(),
			b2cEstimatedCostAmount:$("#b2cEstimatedCostAmount").val(),
			
			actualPriceSum:$("#actualPriceSum").val(),
			actualAmountSum:$("#actualAmountSum").val(),
			
			transactionPriceSum:$("#transactionPriceSum").val(),
			transactionAmountSum:$("#transactionAmountSum").val(),
			
			retailPrice:$("#retailPrice").val(),
			retailAmount:$("#retailAmount").val(),
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
			address: $('#materialModalAddress').val(),
			volumeCube: $("#volumeCube").val()
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
	      	excluded: [':hidden', ':not(:visible)'],
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
                 materialTypeName: {
                	 trigger:"change",
                	 validators: {
	                	 notEmpty: {
	            	         message: '请选择物料'
	            	     } 
                	 }
                 },
                 b2cEstimatedPrice: {
            		validators: {
            			callback: {
                            message: 'B2C预估单价不能为空！',
                            callback:function(value, validator,$field){
                            	if($("#saleType").val()=='20'){
                            		return true
                            	}else{
                            		if($("#itemRequirementPlan").val()=='001'&&value==''){
                            			return false;
                            		}else{
                            			return true;
                            		}
                            	}
                            }
            			}
            	    }
	             }
             
	          }
	        });
}

function restMaterialValidator(){
	$('#subsidiaryModal').on('hidden.bs.modal', function() {
	    $("#subsidiaryForm").data('bootstrapValidator').destroy();
	    $('#subsidiaryForm').data('bootstrapValidator',null);
	    initSubsidiartFormValidator();//要重新绑定验证

	});
}

//需求计划改变重置B2C预估价校验
function onItemRequirementPlanChange(val){
	if($("#stOrderType").val()!='5'){
		$("#subsidiaryForm").bootstrapValidator('updateStatus', "b2cEstimatedPrice", 'NOT_VALIDATED')
	    .bootstrapValidator('validateField', "b2cEstimatedPrice");
	}
	
}


//订单提交校验
function initOrderFormValidator(){
   $('#orderForm').bootstrapValidator({
       message: 'This value is not valid',
       fields: {
    	shopName:{
    		validators:{
    			notEmpty: {
                    message: '请输入店名'
               }
    		}
    	},
    	recordCode:{
    		validators:{
    			notEmpty: {
                    message: '请填写项目报备编号'
               }
    		}
    	},
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
       contactor1Id:{
    	   validators: {
               notEmpty: {
                    message: '请填写授权人1及身份证号'
               }
           }  
       },
       contactor2Id:{
    	   validators: {
               notEmpty: {
                    message: '请填写授权人2及身份证号'
               }
           }  
       },
       contactor3Id:{
    	   validators: {
               notEmpty: {
                    message: '请填写授权人3及身份证号'
               }
           }  
       },
       contactor1Tel:{
    	   validators: {
               notEmpty: {
                    message: '请填写授权人1电话'
               }
           }  
       },
       contactor2Tel:{
    	   validators: {
               notEmpty: {
                    message: '请填写授权人2电话'
               }
           }  
       },
       contactor3Tel:{
    	   validators: {
               notEmpty: {
                    message: '请填写授权人3电话'
               }
           }  
       },
       paymentType:{
    	   validators: {
               notEmpty: {
                    message: '请填写结算方式'
               }
           }  
       },
       bodyDiscount: {
      		validators: {
      			callback: {
                      message: '折扣录入有误',
                      callback:function(value, validator,$field){
                      	if(value==''||(parseFloat(value)<1&&parseFloat(value)!=0)||parseFloat(value)>100){
                      		return false;
                      	}else{
                      		return true;
                      	}
                      }
      			}
      	    }
       },
       incotermContect: {
   		validators: {
   			callback: {
                   message: '请输入国际贸易条件',
                   callback:function(value, validator,$field){
                   	if(!$("#incoterm").val()==''&&value==''){
                   		return false;
                   	}else{
                   		return true;
                   	}
                   }
   			}
   	    }
        },
        mainDiscount: {
       		validators: {
       			callback: {
                       message: '折扣录入有误',
                       callback:function(value, validator,$field){
                       	if(value==''||(parseFloat(value)<1&&parseFloat(value)!=0)||parseFloat(value)>100){
                       		return false;
                       	}else{
                       		return true;
                       	}
                       }
       			}
       	    }
            }
       }
   });
}


//打开调研表
function openConfig(configContent){
	$("#materialconfigModal").modal('show');
	$("#viewError").attr("style","display:none;");
	$("#moreConfig").attr("style","display:none;");
	var rowNum = configContent.split('|')[0];
	var index = configContent.split('|')[1];
	var itemStatus = configContent.split('|')[2];
	var tableData = $('#materialsTable').bootstrapTable('getRowByUniqueId', rowNum);
	$("#configMaterialCode").val(tableData.materialCode);
	$("#configMaterialTypeName").val(tableData.materialName);
	$("#materialConfigClazzCode").val(tableData.clazzCode);
	$("#materialConfigCode").val(tableData.materialCode);
	$("#configContractNumber").val($("#contractNumber").val())
	$("#configContractName").val($("#customerName").val())
	$("#configIndex").val(index);
	$("#viewCode").val(tableData.materialCode);
	$("#viewTransationPrice").val(tableData.transactionPrice);
	$("#viewActualPrice").val(tableData.actualPrice);
	$("#rowNum").val(rowNum);
	var url =ctxPath+"order/material/configurations";
	var configTable = new TableInit('configTable','','',configTableColumns);
	configTable.init();
	var configData = localStorage[rowNum];
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
		$("#mosaicImage").val(jsonObject.mosaicImage);
		if(jsonObject.attachments){
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
		$('.selectpicker').selectpicker({});
	}else{
		insertDefaultConfigs();
		$("#itemFileList").bootstrapTable("removeAll");
		$("#configRemark").val('');
		$("#mosaicImage").val('');
		/*$("#configTable").bootstrapTable('refresh',{
			url:url,
			query:{'clazzCode':$("#materialConfigClazzCode").val(),
				   'materialCode':$("#materialConfigCode").val()
			}
		});*/
	}
	var configData = $("#configTable").bootstrapTable("getData");
	$.each(configData,function(index,value){
			if(value.configValueCode!=value.defaultConfig){
				$('#configTable>tbody tr:eq('+index+')').addClass('configtr')
			}
	})
	if(itemStatus!='00'){
		$("#materialConfigButton").attr("style","display:none;");
	}else{
		$("#materialConfigButton").attr("style","display:block;");
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
				config["defaultConfig"] = value.code
			}
		})
		//没有默认的取第一个
		if(!config.configValueCode){
			config['configValueCode'] = item.configs[0].code;
			config["defaultConfig"] = item.configs[0].code
		}
		insertConfigs.push(config);
	})
	for(var i=0;i<insertConfigs.length;i++){
		$("#configTable").bootstrapTable('insertRow',{
			index:i,
			row:insertConfigs[i]
		});
	}
	$('.selectpicker').selectpicker({});
}
//调研表初始化查询参数
function queryConfigParams(params) {
    params.clazzCode = $("#materialConfigClazzCode").val();
    params.materialCode = $("#materialConfigCode").val();
    return params;
}

//还原标准配置
function resetStandardConfiguration(){
	insertDefaultConfigs()
}

//关闭调研表
function closeMaterialConfig(){
	$("#materialconfigModal").modal('hide');
}

//保存调研表
function saveMaterialConfig(){
	var rowNum = $('#rowNum').val();
	var configIndex = $("#configIndex").val();
	var tableData = $('#materialsTable').bootstrapTable('getRowByUniqueId', rowNum);
	//获取调研表配置价格
	viewConfigPrice("cal");
	var discount = parseFloat(tableData.discount)/100;
	var optionalTransationPrice = $("#viewOptionalTransactionPrice").val();
	var optionalRetailPrice = $("#viewOptionalRetailPrice").val();
	var optionalStandardPrice = $("#viewOptionalStandardPrice").val();
	//可选项转移价
	tableData.optionalTransactionPrice = toDecimal2(optionalTransationPrice); 
	//可选项零售价
	tableData.optionalRetailPrice = toDecimal2(optionalRetailPrice);
	//可选项标准价
	tableData.optionalStandardPrice = toDecimal2(optionalStandardPrice);
	//可选项实卖价人民币
	tableData.optionalActualPrice = toDecimal2(discount*(tableData.optionalRetailPrice));
	tableData = calPrice(tableData);
	//更新表格价格数据
	updateTableRowPrice(configIndex,tableData);
	var configData = new Object();
	var remark = $("#configRemark").val();
	var mosaicImageRemark = $("#mosaicImage").val();
	var configTableData = $("#configTable").bootstrapTable('getData');
	var attachs = $("#itemFileList").bootstrapTable('getData');
	configData.configTableData = configTableData;
	configData.remark = remark
	configData.attachments = attachs
	configData.mosaicImage = mosaicImageRemark;
	localStorage.setItem(rowNum, JSON.stringify(configData));
	$("#materialconfigModal").modal('hide');
}

//更新行项目价格信息
function updateTableRowPrice(index,tableData){
	$("#materialsTable").bootstrapTable('updateRow',{index: index, row: tableData}) 
}

//计算可选项价格和总价格
function calPrice(tableData){
	
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	//数量
	var quantity = tableData.quantity;
	
	//产品实卖价人民币
	var actualPriceCny = tableData.actualPrice;
	//产品实卖价凭证货币
	var originalActualPrice = toDecimal2(parseFloat(tableData.actualPrice)/parseFloat(exchangeRate));
	var actualAmount = toDecimal2(parseFloat(originalActualPrice)*quantity);
	
	//可选项实卖价人民币
	var optionalActualPriceCny = tableData.optionalActualPrice;
	//可选项实卖价凭证货币
	var originalOptionalActualPrice = toDecimal2(parseFloat(optionalActualPriceCny)/parseFloat(exchangeRate));
	//产品转移单价价人民币
	var transcationPrice = tableData.transactionPrice;
	//产品转移单价价凭证货币
	var originalTransationPrice = toDecimal2(parseFloat(transcationPrice)/parseFloat(exchangeRate));
	var transactionPriceAmount = toDecimal2(parseFloat(originalTransationPrice)*quantity);
	//可选项转移单价人民币
	var optionalTransationPrice = tableData.optionalTransactionPrice;
	//可选项转移单价凭证货币
	var originalOptionalTransationPrice = toDecimal2(parseFloat(optionalTransationPrice)/parseFloat(exchangeRate));  
	var optionalTransationPriceAmount = toDecimal2(parseFloat(originalOptionalTransationPrice)*quantity);
	//B2C预估价
	var b2cEstimatedPrice = tableData.b2cEstimatedPrice;
	//B2C预估价凭证货币
	var originalB2cEstimatedPrice = toDecimal2(parseFloat(tableData.b2cEstimatedPrice)/parseFloat(exchangeRate));
	//B2C预估金额
	var b2cEstimatedAmountCny = parseFloat(b2cEstimatedPrice)*quantity;
	var originalB2cEstimatedAmount = toDecimal2(parseFloat(b2cEstimatedAmountCny)/parseFloat(exchangeRate));
	//可选项实卖金额
	var optionalActualAmount = toDecimal2(quantity*originalOptionalActualPrice);
	
	//实卖价单价小计
	var actualPriceSum = toDecimal2(parseFloat(originalOptionalActualPrice)+parseFloat(originalActualPrice)+parseFloat(originalB2cEstimatedPrice));
	//实卖金额合计
	var actualAmountSum = toDecimal2(quantity*parseFloat(actualPriceSum));
	
	//B2C预估成本人民币
	var b2cEstimatedCost = tableData.b2cEstimatedCost;
	//B2C预估成本凭证货币
	var originalB2cEstimatedCost = toDecimal2(parseFloat(b2cEstimatedCost)/parseFloat(exchangeRate));
	var b2cEstimatedCostAmount = toDecimal2(quantity*parseFloat(b2cEstimatedCost))
	//转移价单价小计
	var transactionPriceSum = toDecimal2(parseFloat(originalTransationPrice)+parseFloat(originalOptionalTransationPrice)+parseFloat(originalB2cEstimatedCost));	
	var transactionAmountSum = toDecimal2(quantity*parseFloat(transactionPriceSum))
	
	tableData.optionalActualAmount = optionalActualAmount;
	tableData.originalTransationPrice = originalTransationPrice;
	tableData.originalOptionalTransationPrice = originalOptionalTransationPrice;
	tableData.actualPriceSum = actualPriceSum;
	tableData.transactionPriceAmount = transactionPriceAmount;
	tableData.actualAmountSum = actualAmountSum;
	tableData.optionalTransationPriceAmount = optionalTransationPriceAmount;
	tableData.optionalActualPrice = originalOptionalActualPrice;
	tableData.transactionPriceSum = transactionPriceSum;
	tableData.originalOptionalActualPrice = originalOptionalActualPrice;
	tableData.b2cEstimatedCostAmount = b2cEstimatedCostAmount;
	tableData.b2cEstimatedAmountCny = b2cEstimatedAmountCny;
	
	tableData.originalActualPrice = originalActualPrice;
	
	tableData.actualAmount = actualAmount;
	
	tableData.transactionAmountSum = transactionAmountSum;
	
	return tableData;
}

//复制调研表
function copyMaterials(rowNum){
	var configsData = localStorage[rowNum];
	var data = $('#materialsTable').bootstrapTable('getRowByUniqueId', rowNum);
	var rowData =JSON.parse(JSON.stringify(data));
	rowData.itemStatus = '00';
	var currentTableData= $('#materialsTable').bootstrapTable('getData')
	var lastRowData = $('#materialsTable').bootstrapTable('getData')[currentTableData.length-1]
	var newRowNum = parseInt(lastRowData.rowNum)+10; 
	rowData["rowNum"] = newRowNum;
	$("#materialsTable").bootstrapTable('append',rowData);
	if(configsData){
		localStorage.setItem(newRowNum,configsData);
	}
	//setTableToDifTab();	
}

//插入行项目
function insertMaterials(index){
	var currentRowDatas= $('#materialsTable').bootstrapTable('getData')
	var currentRowData= $('#materialsTable').bootstrapTable('getData')[index];
	var currentRowNum = currentRowData.rowNum;
	var nextIndex = parseInt(index)+1;
	var nextRowData = $('#materialsTable').bootstrapTable('getData')[nextIndex];
	
	var discount = $("#standardDiscount").val()?toDecimal2($("#standardDiscount").val()):toDecimal2(100);
	var addressTableData = $('#addressTable').bootstrapTable('getData')
	var row = addressTableData[0];
	$('#materialProvinceCode').val(row.provinceCode);
	$('#materialProvinceName').val(row.provinceName);
	$('#materialCityCode').val(row.cityCode);
	$('#deliveryAddressSeq').val(row.seq);
	$('#materialCityName').val(row.cityName);
	$('#materialAreaCode').val(row.districtCode);
	$('#materialAreaName').val(row.districtName);
	$('#materialModalAddress').val(row.address)
	if(row.pca==''||row.pca==null||row.pca==undefined){
		$("#materialAddress").val(row.address).change();
	}else{
		$("#materialAddress").val(row.pca+"/"+row.address).change();
	}
	if(nextRowData==undefined){
		var rowData ={};
		var newRowNum = parseInt(currentRowNum)+10; 
		rowData["rowNum"] = newRowNum;
		rowData["quantity"] = 1;
		rowData["discount"] = discount;
		rowData["materialAddress"] = $("#materialAddress").val();
		rowData["provinceCode"] =$('#materialProvinceCode').val(); 
		rowData["provinceName"] =$('#materialProvinceName').val(); 
		rowData["cityCode"] =$('#materialCityCode').val(); 
		rowData["cityName"] =$('#materialCityName').val(); 
		rowData["districtCode"] =$('#materialAreaCode').val(); 
		rowData["districtName"] =$('#materialAreaName').val(); 
		var specialRemark="";
		if($("#isTerm1").val()=="1"){
			specialRemark+=" 甲供";	
		}
		if($("#isTerm2").val()=="1"){
			specialRemark+=" 远程监控"
		}
		
		if($("#isTerm3").val()=="1"){
			specialRemark+="  地下室"
		}
		rowData["specialComments"] = specialRemark;
		var requiredDeliveryTime = $("#requiredDeliveryTime").val();
		rowData["shippDate"] = requiredDeliveryTime;
		rowData["itemRequirementPlan"] = "004";
		$("#materialsTable").bootstrapTable('append',rowData);
		/*$('#subsidiaryModal').modal('show');
		$("#subsidiaryForm")[0].reset();
		$("#itemCategory").html('');
		$('#materialsModalType').val('edit');
		$('#index').val(nextIndex);
		$('#rowNumber').val(newRowNum);
		initSubsidiary();*/
		//setTableToDifTab();
		editMaterials(newRowNum+"|"+nextIndex);
	}else{
		var newRowNum = nextRowData.rowNum;
		if((parseInt(newRowNum)-parseInt(currentRowNum))==1){
			layer.alert('无法继续插入，请新增项目！', {icon: 5});
			return
		}
		var gap = parseInt(newRowNum)-parseInt(currentRowNum);
		var insertRowNum = 0;
		if(gap==2||gap==4||gap==6||gap==8||gap==10){
			insertRowNum = parseInt(currentRowNum)+parseInt(gap)/2;
		}else{
			insertRowNum = parseInt(currentRowNum)+Math.floor(parseInt(gap)/2)+1;
		}
		var rowData ={};
		rowData["rowNum"] = insertRowNum;
		rowData["quantity"] = 1;
		rowData["discount"] = discount;
		rowData["materialAddress"] = $("#materialAddress").val();
		rowData["provinceCode"] =$('#materialProvinceCode').val(); 
		rowData["provinceName"] =$('#materialProvinceName').val(); 
		rowData["cityCode"] =$('#materialCityCode').val(); 
		rowData["cityName"] =$('#materialCityName').val(); 
		rowData["districtCode"] =$('#materialAreaCode').val(); 
		rowData["districtName"] =$('#materialAreaName').val(); 
		rowData["deliveryAddressSeq"] =$('#deliveryAddressSeq').val();
		var specialRemark="";
		if($("#isTerm1").val()=="1"){
			specialRemark+=" 甲供";	
		}
		if($("#isTerm2").val()=="1"){
			specialRemark+=" 远程监控"
		}
		
		if($("#isTerm3").val()=="1"){
			specialRemark+="  地下室"
		}
		rowData["specialComments"] = specialRemark;
		var requiredDeliveryTime = $("#requiredDeliveryTime").val();
		rowData["shippDate"] = requiredDeliveryTime;
		rowData["itemRequirementPlan"] = "004";
		$("#materialsTable").bootstrapTable('insertRow',{index:nextIndex,row:rowData});
		$('#subsidiaryModal').modal('show');
		/*$("#subsidiaryForm")[0].reset();
		$("#itemCategory").html('');
		$('#materialsModalType').val('edit');
		$('#index').val(nextIndex);
		$('#rowNumber').val(insertRowNum);*/
		editMaterials(insertRowNum+"|"+nextIndex);
		//setTableToDifTab();
	}
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
	updateAddressInProd(0);
	$("#addressModal").modal('hide');
}

function updateAddressInProd(updateIndex){
	var seq = parseInt(updateIndex)+1;
	var tableData = $('#materialsTable').bootstrapTable('getData');
	var addressContent = $("#addressTable").bootstrapTable('getData')[updateIndex];
	$.each(tableData,function(index,item){
		if(item.deliveryAddressSeq==seq||item.deliveryAddressSeq==null){
			item.provinceCode = addressContent.provinceCode;
			item.provinceName = addressContent.provinceName;
			item.cityCode = addressContent.cityCode;
			item.cityName = addressContent.cityName;
			item.districtCode = addressContent.districtCode;
			item.districtName = addressContent.districtName;
			var pca='';
			if(item.provinceCode){
				pca = item.provinceName;
				if(item.cityCode){
					pca+="/"+item.cityName
				}
				if(item.districtCode){
					pca+="/"+item.districtName
				}
			}
			item.materialAddress = pca+addressContent.address;
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: index,
			    row: item
			});
		}
	})
	//setTableToDifTab();
}

function addAddress(){
	if($('#addressTable').bootstrapTable('getData').length>0){
		layer.alert('只能添加一条地址！', {icon: 5});
		return;
	}
	$("#addressModal").modal('show');
	$("#addressModalType").val('new');
	$("#selectProvince").selectpicker('val',$("#selectProvince").find('option:first').val());
	$("#selectProvince").find("option").attr("selected", false); 
	$("#citySelect").selectpicker('val',$("#citySelect").find('option:first').val());
	$("#citySelect").find("option").attr("selected", false); 
	$("#selectDistrict").selectpicker('val',$("#selectDistrict").find('option:first').val());
	$("#selectDistrict").find("option").attr("selected", false); 
	$("#shippingAddress").val('');
}

function editAddress(index){
	var row = $('#addressTable').bootstrapTable('getData')[index];
	$("#addressIndex").val(index);
	$("#addressModalType").val("edit");
	$("#addressModal").modal('show');
	$("#selectProvince").val(row.provinceCode).change();
	$("#citySelect").val(row.cityCode).change();
	$("#selectDistrict").val(row.districtCode).change();
	$("#shippingAddress").val(row.address);
}
function removeAddress(index){
	var delIndex = parseInt(index)+1;
	layer.confirm("确认是否删除，删除后行项目关联地址也会清空，请重新修改行项目地址信息！", {btn: ['确定', '取消'], title: "提示"}, function () {
		$('#addressTable').bootstrapTable('remove', {
			field: "seq",
	        values: [delIndex]
	    });
		deleteAddressInProd(delIndex);
		layer.closeAll('dialog');
    });
	/*if(status==null||status==""||status=="undefined"){
		var count = $('#addressTable').bootstrapTable('getData').length;
		for(var i=0;i<count;i++){
			var rows = {
					index: i,
					field : "seq",
					value : i+1
				}
			$('#addressTable').bootstrapTable("updateCell",rows);
		}
	}	*/
}

function deleteAddressInProd(delIndex){
	var tableData = $('#materialsTable').bootstrapTable('getData');
	$.each(tableData,function(index,item){
		if(item.deliveryAddressSeq==delIndex){
			item.deliveryAddressSeq=null;
			item.materialAddress = null;
			item.provinceCode = null;
			item.provinceName = null;
			$("#materialsTable").bootstrapTable('updateRow', {
			    index: index,
			    row: item
			});
		}
	})
	//setTableToDifTab();
}
function setItemRequirementPlan(obj){
	var b2cValue = $(obj).val();
	var itemPlan = $("#itemRequirementPlan").val();
	if(itemPlan=='002'||itemPlan=='003'){
		return;
	}
	if(b2cValue!=''){
		$("#itemRequirementPlan").val("001").change();
	}else{
		$("#itemRequirementPlan").val("004").change();
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

function transferTypeChange(){
	var transferType = $("#transferType").val();
	if(($("#stOrderType").val()=="3"||$("#stOrderType").val()=="4")&&transferType=='02'){
		$("#additionalFreight").attr('disabled',true);
		$("#additionalFreight").val('');
	}else if(($("#stOrderType").val()=="3"||$("#stOrderType").val()=="4")&&transferType=='01'){
		$("#additionalFreight").attr('disabled',false);
	}
}

//保存提交订单
function saveOrder(type){
	if(type){		 
		 var items = $("#materialsTable").bootstrapTable('getData');
		 if(items.length==0){
			 layer.alert('请添加购销明细', {icon: 5});
			 return
		 }
		 if($("#saleType").val()=='20'){
			 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor1Id',false,'notEmpty');
			 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor2Id',false,'notEmpty');
			 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor3Id',false,'notEmpty');
			 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor1Tel',false,'notEmpty');
			 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor2Tel',false,'notEmpty');
			 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor3Tel',false,'notEmpty');
		 }
		 if($("#stOrderType").val()=="3"||$("#stOrderType").val()=="4"||$("#stOrderType").val()=="5"){
			 $('#orderForm').data('bootstrapValidator').enableFieldValidators('recordCode',false,'notEmpty');
		 }
		 expandAll();
		var bootstrapValidator = $("#orderForm").data('bootstrapValidator');
		bootstrapValidator.validate();
		
		if(!bootstrapValidator.isValid()){ 
			layer.alert('订单信息录入有误，请检查后提交', {icon: 5});
			return
		}
		
	}
	 $('#loadingModal').modal('show');
	$('#transferType').attr("disabled",false);
	$("#currency").attr("disabled",false);
	if($("#hasSendSap").val()=='true'){
		$("#saleType").attr('disabled',false);
	}
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
	 var payments=new Array();
	 orderData.payments= payments;
	 var attachments = $("#fileList").bootstrapTable('getData');
	 orderData.attachments = attachments
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[i].rowNum];
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
				 config['color'] = storedConfigs[j].color;
				 configs.push(config);
			 }
			 items[i]['configs'] = configs; 
			 items[i]['attachments'] = jsonObject.attachments;
			 items[i]['configComments'] = jsonObject.remark;
			 items[i]['mosaicImage'] = jsonObject.mosaicImage;
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
			    	$('#loadingModal').modal('hide');
			    	if(result == null || result.status != 'ok'){
			    		layer.alert("提交订单失败:" + (result != null ? result.msg : ""));
			    	}else{
			    		layer.confirm("提交订单成功！", {btn: ['确定'], title: "提示"}, function () {
			    			window.location.href = ctxPath+'menu/orderManageList';
			    	 });
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
			    	$('#loadingModal').modal('hide');
			    	if(data == null || data.status != 'ok'){
			    		layer.alert("保存订单失败！" + (data != null ? data.msg : ""));
			    		 
			    	}else{
			    		 layer.confirm("保存订单成功！", {btn: ['确定'], title: "提示"}, function () {
				    			window.location.href = ctxPath+'menu/orderManageList';
				    	 });
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
	 if($("#saleType").val()=='20'){
		 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor1Id',false,'notEmpty');
		 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor2Id',false,'notEmpty');
		 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor3Id',false,'notEmpty');
		 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor1Tel',false,'notEmpty');
		 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor2Tel',false,'notEmpty');
		 $('#orderForm').data('bootstrapValidator').enableFieldValidators('contactor3Tel',false,'notEmpty');
	 }
	 if($("#stOrderType").val()=="3"||$("#stOrderType").val()=="4"||$("#stOrderType").val()=="5"){
		 $('#orderForm').data('bootstrapValidator').enableFieldValidators('recordCode',false,'notEmpty');
	 }
	 expandAll();
	var bootstrapValidator = $("#orderForm").data('bootstrapValidator');
	bootstrapValidator.validate();
	if(!bootstrapValidator.isValid()){ 
		layer.alert('订单信息录入有误，请检查后提交', {icon: 5});
		
		return
	}
	$('#loadingModal').modal('show');
	$('#transferType').attr("disabled",false);
	$("#currency").attr("disabled",false);
	if($("#hasSendSap").val()=='true'){
		$("#saleType").attr('disabled',false);
	}
	 //获取下拉框name
	 getSelectName();
	 var orderData = $("#orderForm").serializeObject(); 
	 $('#transferType').attr("disabled",true);
	 var payments=new Array();
	 orderData.payments= payments;
	 var attachments = $("#fileList").bootstrapTable('getData');
	 orderData.attachments = attachments
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[i].rowNum];
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
				 config['color'] = storedConfigs[j].color;
				 configs.push(config);
			 }
			 items[i]['configs'] = configs; 
			 items[i]['attachments'] = jsonObject.attachments;
			 items[i]['configComments'] = jsonObject.remark;
			 items[i]['mosaicImage'] = jsonObject.mosaicImage;
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
		    	$('#loadingModal').modal('hide');
		    	if(result == null || result.status != 'ok'){
		    		layer.alert("提交BPM失败：" + (result != null ? result.msg : ""));
		    	}else{
		    		layer.confirm("提交BPM成功！", {btn: ['确定'], title: "提示"}, function () {
		    			window.location.href = ctxPath+'menu/orderManageList';
		    		});
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
	$("#taxRate").val($("#taxCode").find("option:selected").text());
}


//调研表查看物料
function viewConfigPrice( type){
	var formData = new Object();
	var bomCode = $("#materialConfigCode").val();	
	var configTableData = $("#configTable").bootstrapTable('getData');
	var configCode = []; 
	var configValueCodes = [];
	for(var i=0;i<configTableData.length;i++){
		if(configTableData[i].color){
			//颜色选项不传
		}else{
			configCode.push(configTableData[i].code);
			configValueCodes.push(configTableData[i].configValueCode);
		}
		
	}
	formData.bomCode = bomCode;
	formData.configCode = configCode;
	formData.configValueCode = configValueCodes;
	$.ajax({
	    url: ctxPath+"order/material/configuration",
	    contentType: "application/json;charset=UTF-8",
	    data: JSON.stringify(formData),
	    type: "POST",
	    async: false,
	    dataType: "json",
	    success: function(res) { 
	    	if(type=="view"){
	    		if(res.status!="ok"){
		    		$("#viewError").attr("style","display:block;");
		    	}else{
		    		$("#moreConfig").attr("style","display:block;");
			    	$("#viewOptionalTransactionPrice").val(res.data.transferPrice);
			    	$("#viewOptionalStandardPrice").val(res.data.standardPrice);
			    	$("#viewOptionalRetailPrice").val(res.data.retailPrice);
		    	}	    
	    	}else{
	    		$("#viewOptionalTransactionPrice").val(res.data.transferPrice);
		    	$("#viewOptionalStandardPrice").val(res.data.standardPrice);
		    	$("#viewOptionalRetailPrice").val(res.data.retailPrice);
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
	 $('.selectpicker').selectpicker({
     });
	 var configData = $("#configTable").bootstrapTable("getData");
	 $.each(configData,function(index,value){
			if(value.configValueCode!=value.defaultConfig){
				$('#configTable>tbody tr:eq('+index+')').addClass('configtr')
			}
	})
	var rowData = configData[index];
	if(rowData.color&&configValueCode=='2'){
		layer.alert('请填写行项目的颜色备注');
	}
}
//查看毛利率信息
function viewGrossProfit(type){
	$('#grossProfitTable').bootstrapTable('destroy'); 
	 if(type=='wtw'){		
		var wtwGrossProfitTable = new TableInit("grossProfitTable",'','',wtwGrossProfitColumns);
		wtwGrossProfitTable.init();
	 }else{
		 var grossProfitTable = new TableInit("grossProfitTable",'','',grossProfitColumns);
		 grossProfitTable.init();
	 }
	if(type=='wtw'){
		$("#grossProfitExportBt").hide();
		$("#wtwGrossProfitExportBt").show();
	}else{
		$("#grossProfitExportBt").show();
		$("#wtwGrossProfitExportBt").hide();
	}
	if(type=='wtw'&&orderOperationType!=1){		
		$("#wtwGrossProfitExportBt").show();
	}else{
		$("#wtwGrossProfitExportBt").hide();
	}
	if(type=='normal'&&orderOperationType!=1){
		$("#grossProfitExportBt").show();
	}else{
		$("#grossProfitExportBt").hide();
	}
	//查看订单显示毛利率
	if(orderOperationType==2){
		viewOrderGrossProfit();
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
	 $('#transferType').attr("disabled",false);
	 $("#currency").attr("disabled",false);
	 if($("#hasSendSap").val()=='true'){
			$("#saleType").attr('disabled',false);
	 }
	 getSelectName(); 
	 var orderData = $("#orderForm").serializeObject();
	 if($("#saleType").val()=='20'){
		 $('#transferType').attr("disabled",true);
	 }
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
	$("#grossVersion").val($("#version").val());
	$("#grossContractRMBValue").val($("#contractAmount").val());
	$("#grossPerson").val($("#salesName").val());
	$("#grossDate").val($("#inputDate").val());
	$("#grossClazz").val($("#customerClazzName").val());
	$.ajax({
	    url: ctxPath+"order/grossprofit",
	    contentType: "application/json;charset=UTF-8",
	    data: JSON.stringify(orderData),
	    type: "POST",
	    success: function(data) { 
	    	var tableData = data.data
	    	$.each(tableData,function(index,item){
	    		item.grossProfitMargin = toDecimal2(item.grossProfitMargin*100)+"%";
	    		item.amount = toDecimal2(item.amount);
	    		item.excludingTaxAmount = toDecimal2(item.excludingTaxAmount);
	    		item.wtwCost = toDecimal2(item.wtwCost);
	    		item.cost = toDecimal2(item.cost);
	    		item.wtwGrossProfit = toDecimal2(item.wtwGrossProfit);
	    		item.grossProfit = toDecimal2(item.grossProfit);
	    		item.wtwGrossProfitMargin = toDecimal2(item.wtwGrossProfitMargin*100)+"%";
	    		
	    	})
	    	$("#grossProfitTable").bootstrapTable('load', tableData);
	    },
	    error: function(){
	    	layer.alert('毛利率查看失败', {icon: 5});
	    }
	});  
}

//查看订单时显示毛利率
function viewOrderGrossProfit(){
	$("#grossProfit").modal("show");
	$("#grossSeqNum").val($("#sequenceNumber").val());
	$("#grossContractNo").val($("#contractNumber").val());
	$("#grossContractTitle").val($("#customerName").val());
	$("#grossVersion").val($("#version").val());
	$("#grossContractRMBValue").val($("#contractAmount").val());
	$("#grossPerson").val($("#salesName").val());
	$("#grossDate").val($("#inputDate").val());
	$("#grossClazz").val($("#customerClazzName").val());
	var tableStringData = grossProfitMargin;
	var tableData =JSON.parse(tableStringData)
	$.each(tableData,function(index,item){
		item.grossProfitMargin = toDecimal2(item.grossProfitMargin*100)+"%";
		item.amount = toDecimal2(item.amount);
		item.excludingTaxAmount = toDecimal2(item.excludingTaxAmount);
		item.wtwCost = toDecimal2(item.wtwCost);
		item.cost = toDecimal2(item.cost);
		item.wtwGrossProfit = toDecimal2(item.wtwGrossProfit);
		item.grossProfit = toDecimal2(item.grossProfit);
		item.wtwGrossProfitMargin = toDecimal2(item.wtwGrossProfitMargin*100)+"%";
	})
	$("#grossProfitTable").bootstrapTable('load', tableData);	
}

function exportGross(type){
	var grossData = $("#grossProfitTable").bootstrapTable("getData");
	$.each(grossData,function(index,item){
		item.grossProfitMargin = item.grossProfitMargin.replace("%","")
		item.grossProfitMargin = toDecimal2(parseFloat(item.grossProfitMargin)/100);
		item.wtwGrossProfitMargin = item.wtwGrossProfitMargin.replace("%","");
		item.wtwGrossProfitMargin = toDecimal2(parseFloat(item.wtwGrossProfitMargin)/100);
	})
	var sequenceNumber = $("#sequenceNumber").val();
	var versionNum = $("#version").val();
	var createTime = formatDate(new Date()) ;
	var salesCode = $("#salesCode").val();
	
	/*$.ajax({
		url: ctxPath+"order/grossprofit/export/"+sequenceNumber+','+versionNum+','+createTime+','+salesCode,
		contentType: "application/json;charset=UTF-8",
		data: JSON.stringify(grossData),
		type: "POST",
		success: function(data) {
			
		},
		error: function(){
			layer.alert('导出失败', {icon: 5});
		}
	});*/
		var myForm = document.createElement("form");
	    myForm.method = "post";
	    myForm.action = ctxPath+"order/grossprofit/export";
	    document.body.appendChild(myForm);
	    var seq = document.createElement("input");
	    seq.setAttribute("name", "sequenceNumber");
	    seq.setAttribute("value", sequenceNumber);
	    var seq1 = document.createElement("input");
	    seq1.setAttribute("name", "versionNum");
	    seq1.setAttribute("value", versionNum);
	    var seq2 = document.createElement("input");
	    seq2.setAttribute("name", "createTime");
	    seq2.setAttribute("value", createTime);
	    var seq3 = document.createElement("input");
	    seq3.setAttribute("name", "salesCode");
	    seq3.setAttribute("value", salesCode);
	    var seq4 = document.createElement("input");
	    seq4.setAttribute("name", "grossProfitMargin");
	    seq4.setAttribute("value", JSON.stringify(grossData));
	    if(type=='wtw'){
	    	var seq5 = document.createElement("input");
		    seq5.setAttribute("name", "wtw");
		    seq5.setAttribute("value", true);
		    myForm.appendChild(seq5);
	    }
	    
	    myForm.appendChild(seq);
	    myForm.appendChild(seq1);
	    myForm.appendChild(seq3);
	    myForm.appendChild(seq4);
	    myForm.submit();
	    document.body.removeChild(myForm);  
	
}

//日期格式化
function formatDate(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    return y + '.' + m + '.' + d;
}

//时间格式化处理
function dateFtt(fmt, date) { //author: meizz   
    var o = {
        "M+": date.getMonth() + 1, //月份   
        "d+": date.getDate(), //日   
        "h+": date.getHours(), //小时   
        "m+": date.getMinutes(), //分   
        "s+": date.getSeconds(), //秒   
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度   
        "S": date.getMilliseconds() //毫秒   
    };
    if(/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//查看合同
function viewContract(){
	var orderInfoId = $("#id").val();
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
//驳回
function reject(){
	var orderInfoId = $("#id").val();
	var url = ctxPath+"order/"+parseInt(orderInfoId)+"/reject";
    $.ajax({
        type: "post",
        url: url,
        data: null,
        dataType: "json",
        success: function (result) {
        	if(result == null || result.status != 'ok'){
	    		layer.alert("驳回订单失败" + (result != null ? result.msg : ""));
	    	}else{
	    		layer.confirm("驳回订单成功！", {btn: ['确定'], title: "提示"}, function () {
	    			window.location.href = ctxPath+'menu/orderManageList';
	    	    });  		
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
//合同号自动转大写
function contractToUpperCase(obj){
	obj.value = obj.value.toUpperCase()
	var maxChars =10;//最多字符数
    if (obj.value.length > maxChars){
        obj.value = obj.value.substring(0,maxChars);
    }
}

function updateRowSpecialComments(materialsRowData){
	$("#materialsTable").bootstrapTable('updateByUniqueId', {
	    id:materialsRowData.rowNum,
	    row: {
	    	specialComments:materialsRowData.specialComments	
	    }
	});
}

//是否甲供切换
function onTerm1Change(val){
	var termValue = $(val).val();
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<countMaterialsTable;i++){
		var materialsRowData = materialsTable[i];
		var specialComments = materialsRowData.specialComments;
		if(termValue=='1'&&specialComments.indexOf("甲供") == -1){
			materialsRowData.specialComments = specialComments+" 甲供"
			updateRowSpecialComments(materialsRowData)
		}else if(termValue=='0'&&specialComments.indexOf("甲供") !==0){
			materialsRowData.specialComments = specialComments.replace(/甲供/, "")
			updateRowSpecialComments(materialsRowData)
		}
	}
}
//是否远程监控
function onTerm2Change(val){
	var termValue = $(val).val();
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<countMaterialsTable;i++){
		var materialsRowData = materialsTable[i];
		var specialComments = materialsRowData.specialComments;
		if(termValue=='1'&&specialComments.indexOf("远程监控") ==  -1){
			materialsRowData.specialComments = specialComments+" 远程监控"
			updateRowSpecialComments(materialsRowData)
		}else if(termValue=='0'&&specialComments.indexOf("远程监控")!==0){
			materialsRowData.specialComments = specialComments.replace(/远程监控/, "")
			updateRowSpecialComments(materialsRowData)
		}
	}
}
//否在地下室
function onTerm3Change(val){
	var termValue = $(val).val();
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<countMaterialsTable;i++){
		var materialsRowData = materialsTable[i];
		var specialComments = materialsRowData.specialComments;
		if(termValue=='1'&&specialComments.indexOf("地下室") == -1){
			materialsRowData.specialComments = specialComments+" 地下室"
			updateRowSpecialComments(materialsRowData)
		}else if(termValue=='0'&&specialComments.indexOf("地下室") !==0){
			materialsRowData.specialComments = specialComments.replace(/地下室/, "")
			updateRowSpecialComments(materialsRowData)
		}
	}
}

//生成序列号
function produceSeqNum(){
	if($("#orderInfoId").val()==""){
		var seqNum = "QHC"+getNowTimeStr();
		$("#sequenceNumber").val(seqNum);
	}
}

function getNowTimeStr() {
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1;
	var date = nowDate.getDate();
	var hour = nowDate.getHours() < 10 ? "0" + nowDate.getHours() : nowDate.getHours();
	var minute = nowDate.getMinutes() < 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
	var second = nowDate.getSeconds() < 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
	var milliSeconds = nowDate.getMilliseconds();
	var currentTime = year+''+month + date + hour +  minute +  second +milliSeconds;
	return currentTime;
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

var wtwGrossProfitColumns=[
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
	},
	{
		 field: 'wtwCost',
		 title: 'wtw成本'
	},
	{
		 field: 'wtwGrossProfit',
		 title: 'wtw毛利'
	},
	{
		 field: 'wtwGrossProfitMargin',
		 title: 'wtw毛利率'
	}
]

var fileListColumns=[
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
			actions.push('<a class="btn" onclick="removeFile(\'' + row.type+','+row.fileUrl + '\')"><i class="fa fa-remove"></i>删除</a>');
			return actions.join('');
		}
	}
]

var fileListColumnsByXujiamao=[
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
			actions.push('<a class="btn" onclick="removeFile(\'' + row.type+','+row.fileUrl + '\')"><i class="fa fa-remove"></i>删除</a>');
			return actions.join('');
		}
	}
]

var fileListColumnsWithoutOperationByXujiamao=[
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
	width:'8%',
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
	width:'20%'
},
{
	title:'',
	visible:false,
	field:'code'
},
{
	title:'defaultConfig',
	visible:false,
	field:'defaultConfig'
},
{
	title:'configValueCode',
	visible:false,
	field:'configValueCode'
},
{
	title:'',
	visible:false,
	field:'color'
},
{
	title:'配置值',
	field:'configs',
	width:'72%',
	formatter: function(value, row, index) {
		if(row.color){
			var id="configsId"+index;
	    	var start = '<select class="form-control selectpicker" data-live-search="true" id=\'' + id + '\' name="configValueCode" onchange="setConfigValueCode(this,\'' + index + '\')">';
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
	    	}
			return start+end;
		}else{
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
	    	}
			return start+end;
		}
		
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
function rowStyle(row, index){
	if(row.name=="柜内颜色"){
		 var style = {};            
		    style={css:{'color':'#ed5565'}};               
		    return style;
	}
	
}
var TableInit = function (id,url,params,tableColumns) {
	var oTableInit = new Object();
	var tableId = '#'+id;
	oTableInit.init = function () {
		$(tableId).bootstrapTable({
			method : 'get',
			url : url,//请求路径
			striped : true, //是否显示行间隔色
			toolbar: '#toolbar',
			uniqueId:'rowNum',
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
