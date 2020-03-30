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
				fillConfigsForMaterial(identification,items[i].configs,rowData.comments,rowData.materialCode,rowData.clazzCode,configAttachments);
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

function fillConfigsForMaterial(identification,configs,configRemark,materialCode,clazzCode,configAttachments){
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
	var transcationPrice = toDecimal2(data.transactionPrice);
	var optionalActualPrice = toDecimal2(data.optionalActualPrice);
	var optionalTransationPrice = toDecimal2(data.optionalTransationPrice);
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
			transactionPrice:transcationPrice,
			optionalActualPrice:optionalActualPrice,
			optionalTransationPrice:data.optionalTransationPrice,
			optionalStandardPrice:data.optionalStandardPrice,
			optionalRetailPrice:data.optionalRetailPrice,
			b2cEstimatedPrice:b2cEstimatedPrice,
			b2cEstimatedAmount:b2cEstimatedAmount,
			b2cEstimatedCost:b2cEstimatedCost,
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
	$('#currency').val($("#currencyName").val());
	$('#currency').trigger("change");
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
	var saleType = $(obj).val();
	getCurrency(saleType,exchangeRate);
	getIncoterm(saleType);
	$("#taxRate").html('');
	if(taxRate&&saleType==20){
		var rate = taxRate[10];
		$("#taxRate").append("<option value='0'>0</option>");
		$("#taxRate").append("<option value='"+rate+"'>"+rate+"</option>");
	}else{
		var rate = taxRate[10];
		$("#taxRate").append("<option value='"+rate+"'>"+rate+"</option>");
	}
	if(saleType=="20"){
		$("#exchangeRate").val("");
		$("#contractAmount").val("");
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
	var customerName = $("#customerName").val();
	if(customerName==""){
		layer.alert('请先选择客户', {icon: 5});
		return
	}
	if($('#addressTable').bootstrapTable('getData').length==0){
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
	if($("#stOrderType").val()=="2"&&$("#bodyDiscount").val()==""){
        layer.msg('折扣未录入！', function(){
        });
    }

    if($("#stOrderType").val()=="2"&&$("#mainDiscount").val()==""){
        layer.msg('折扣未录入！', function(){
        });
    }
    
	if($("#saleType").val()=='20'){
		$('#acturalPrice').attr("disabled",false);
	}
	if($("#stOrderType").val()=="2"&&$("#isLongterm").val()=='1'){
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
	if(row.pca==''){
		$("#materialAddress").val(row.address).change();
	}else{
		$("#materialAddress").val(row.pca+"/"+row.address).change();
	}
	$('#amount').val(1);
	$('#discount').val($("#standardDiscount").val());
	if($("#isTerm1").val()=="1"||$("#isTerm2").val()=="1"||$("#isTerm3").val()=="1"){
		$("#itemRequirementPlan").val("001");
	}
	var b2cRemark="";
	if($("#isTerm1").val()=="1"){
		b2cRemark+="甲供；";	
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
	$("#itemCategory").html('');
	var configure = data.configurable+'';
	if(configure=='true'){
		$("#itemCategory").append("<option value='ZHD1'>标准</option><option value='ZHD3'>免费</option><option value='ZHR3'>退货</option>");	
	}else{
		$("#itemCategory").append("<option value='ZHD2'>标准</option><option value='ZHD4'>免费</option><option value='ZHR4'>退货</option>");
	}
	$("#materialTypeName").val(data.description);
	$("#materialCode").val(data.code);
	if(data.code=='BG1GD1000000-X'||data.code=='BG1R8R00000-X'||data.code=='BG1R8L00000-X'){
		$('#acturalPrice').attr("disabled",false);
	}else{
		$('#acturalPrice').attr("disabled",true);
		if($("#saleType").val()=='20'){
			$('#acturalPrice').attr("disabled",false);
		}
	}
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
	$("#materialType").val(materialsType);
	$("#unitName").val(data.unitName);
	$("#unitCode").val(data.unitCode);
	$("#materialClazzCode").val(data.clazzCode);
	//标准价
	$("#standardPrice").val(toDecimal2(data.standardPrice));
	//市场零售价
	$("#retailPrice").val(toDecimal2(data.retailPrice));
	//转移价
	$("#transcationPrice").val(toDecimal2(data.transcationPrice));
	initMaterialPrice();
	$('#volumeCube').val(data.materialSize)	
}

function initMaterialPrice(){
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	//数量
	var amount = $("#amount").val();
	//零售价
	var retailPrice = $("#retailPrice").val();
	//转移价
	var transcationPrice = $("#transcationPrice").val();
	//折扣率
	var discountValue = $("#discount").val();
	if($("#stOrderType").val()=="2"){
		discountValue = (discount/100).toFixed(2);
	}
	//实卖价人民币
	var actualPriceCny = toDecimal2(parseFloat(retailPrice)*discountValue);
	$("#actualPriceCny").val(toDecimal2(actualPriceCny));
	//实卖价凭证货币
	var actualPrice = toDecimal2(actualPriceCny/parseFloat(exchangeRate));
	$("#originalActualPrice").val(toDecimal2(actualPrice));
	//产品实卖金额凭证货币
	var acturalPriceAmount = toDecimal2(amount*(actualPrice));
	$("#acturalPriceAmount").val(acturalPriceAmount);
	
    //可选项实卖价	
	$("#originalActuralPriceOfOptional").val(toDecimal2(0.00));
	$("#acturalPriceOfOptionalCny").val(toDecimal2(0.00));
	var acturalPriceOfOptional = $("#acturalPriceOfOptional").val();
	
	//可选项实卖金额
	$("#acturalPriceOfOptionalAmount").val(toDecimal2(0.00));
	var acturalPriceOfOptionalAmount = $("#acturalPriceOfOptionalAmount").val();
	//可选项转移价
	$("#transcationPriceOfOptional").val(toDecimal2(0.00));
	var transcationPriceOfOptional = $("#transcationPriceOfOptional").val();
	//b2c预估价  初始化为空
	//B2C预估金额  初始化为空
	//B2CCostOfEstimated 初始化为空
	//实卖价合计 
	$("#acturalPriceTotal").val(toDecimal2(parseFloat($("#originalActualPrice").val())));
	//实卖金额合计
	$("#acturalPriceAmountTotal").val(toDecimal2(acturalPriceAmount));
	//转移价合计
	$("#transcationPriceTotal").val(toDecimal2(parseFloat(transcationPrice)*amount));
	//市场零售金额
	$("#retailPriceAmount").val(toDecimal2(amount*parseFloat(retailPrice)));
}

//数量变化
function amountChange(){
	var amount = $("#amount").val();
	if(amount==''){
		return;
	}
	$("#acturalPriceAmount").val(toDecimal2(amount*(parseFloat($("#originalActualPrice").val()))));
	$("#acturalPriceOfOptionalAmount").val(toDecimal2(amount*(parseFloat($("#originalActuralPricaOfOptional").val()))));
	$("#B2CPriceEstimatedAmount").val(toDecimal2(amount*(parseFloat($("#B2CPriceEstimated").val()))));
	$("#retailPriceAmount").val(toDecimal2(parseFloat($("#retailPrice").val())*amount));
	$("#acturalPriceAmountTotal").val(toDecimal2(parseFloat($("#acturalPriceTotal").val())*amount));
}
//实卖价编辑
function originalActualPriceChange(){
	//数量
	var amount = parseFloat($("#amount").val());
	//产品实卖价凭证货币
	var originalActualPrice = $("#originalActualPrice").val();
	//汇率
	var exchangeRate = $("#exchangeRate").val(); 
	var actualPriceCny = parseFloat(originalActualPrice)*parseFloat(exchangeRate);
	$("#actualPriceCny").val(actualPriceCny);
	//可选项实卖价
	var  acturalPriceOfOptional =  parseFloat($("#originalActuralPriceOfOptional").val());
	//B2C预估价
	var b2cPriceCny = $("#B2CPriceEstimated").val();
	//B2C预估计凭证货币
	var b2cPrice = toDecimal2(parseFloat(b2cPriceCny)/parseFloat(exchangeRate))
	var acturalPriceAmount = parseFloat(originalActualPrice)*amount;
	var acturalPriceTotal = toDecimal2(originalActualPrice+acturalPriceOfOptional+parseFloat(b2cPrice));
	$("#acturalPriceTotal").val(acturalPriceTotal)
	$("#acturalPriceAmountTotal").val(toDecimal2(acturalPriceTotal*amount));
	//市场零售价
	var retailPrice = $("#retailPrice").val();
	//计算折扣
	if($("#stOrderType").val()=="2"){
		var discount =toDecimal2( (parseFloat(actualPriceCny)/parseFloat(retailPrice))*100);
	}
}

//折扣率比变化
function discountChange(){
	var discount = $("#discount").val();
	var discountValue = toDecimal2((parseFloat(discount)/100));
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	//市场零售价
	var retailPrice = $("#retailPrice").val();
	//产品实卖价人民币
	var actualPriceCny = parseFloat(retailPrice)*parseFloat(discountValue);
	$("#actualPriceCny").val(actualPriceCny);
	//产品实卖价凭证货币
	var originalActualPrice =toDecimal2( parseFloat(actualPriceCny)/parseFloat(exchangeRate));
	$("#originalActualPrice").val(originalActualPrice);
	//可选项实卖价
	var  acturalPriceOfOptional =  parseFloat($("#originalActuralPriceOfOptional").val());
	//B2C预估价
	var b2cPriceCny = $("#B2CPriceEstimated").val();
	//B2C预估计凭证货币
	var b2cPrice = toDecimal2(parseFloat(b2cPriceCny)/parseFloat(exchangeRate))
	var acturalPriceAmount = parseFloat(originalActualPrice)*amount;
	var acturalPriceTotal = toDecimal2(originalActualPrice+acturalPriceOfOptional+parseFloat(b2cPrice));
	$("#acturalPriceTotal").val(acturalPriceTotal)
	$("#acturalPriceAmountTotal").val(toDecimal2(acturalPriceTotal*amount));
}

//B2C预估价变化
function getB2CAmount(obj){
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	//B2C预估价
	var b2cPriceCny = $(obj).val();
	//B2C预估计凭证货币
	var b2cPrice = toDecimal2(parseFloat(b2cPriceCny)/parseFloat(exchangeRate))
	var amount = $("#amount").val();
	//B2C预估金额
	$("#B2CPriceEstimatedAmount").val(toDecimal2(amount*(parseFloat(b2cPriceCny))));
	//计算实卖价合计
	//产品实卖价
	var acturalPrice = parseFloat($("#originalActualPrice").val());
	//可选项实卖价
	var  acturalPriceOfOptional =  parseFloat($("#originalActuralPriceOfOptional").val());
	var acturalPriceTotal = acturalPrice+acturalPriceOfOptional+parseFloat(b2cPrice);
	$("#acturalPriceTotal").val(acturalPriceTotal)
}
//B2C预估成本变化
function getB2CCostAmount(obj){
	//B2C成本
	var b2cCost = $(obj).val();
	//转移价
	var transcationPrice = $("#transcationPrice").val();
	//数量
	var amount = $("#amount").val();
	//可选项转移价
	var transcationPriceOfOptional = $("#transcationPriceOfOptional").val(toDecimal2(0.00));
	
	var transactionPriceSum = toDecimal2(parseFloat(transcationPrice)*parseFloat(amount)+parseFloat(transcationPriceOfOptional)+parseFloat(b2cCost));
}

//编辑购销明细
function editMaterials(editContent){
	$('#subsidiaryModal').modal('show');
	if(status=='01'){
		$("#B2CCostOfEstimated").attr("disabled",false);
	}
	$('#materialsModalType').val('edit');
	var index = editContent.split('|')[1];
	var rowNumber = editContent.split('|')[0]
	var tableData =$('#materialsTable').bootstrapTable('getData')[index];	
	$("#itemCategory").html('');
	var configable = tableData.isConfigurable+'';
	if(configable=='true'){
		$("#itemCategory").append("<option value='ZHD1'>标准</option><option value='ZHD3'>免费</option><option value='ZHR3'>退货</option>");	
	}else{
		$("#itemCategory").append("<option value='ZHD2'>标准</option><option value='ZHD4'>免费</option><option value='ZHR4'>退货</option>");
	}
	fillEditMaterailValue(tableData,index);
}

//编辑购销明细时页面值回显
function fillEditMaterailValue(data,index){	
	$("#index").val(index);
	$("#purchasedCode").val(data.isPurchased);
	if(data.isPurchased){
		$("#isPurchased").val("外购");
	}else{
		$("#isPurchased").val("自制");
	}
	$('#volumeCube').val(data.volumeCube)
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
	$("#transcationPrice").val(data.transactionPrice);
	$("#acturalPricaOfOptionalAmount").val(data.optionalActualAmount);
	$("#transcationPriceOfOptional").val(data.optionalTransationPrice);
	$("#B2CPriceEstimated").val(data.b2cEstimatedPrice);
	$("#B2CPriceEstimatedAmount").val(data.b2cEstimatedAmount);
	$("#B2CCostOfEstimated").val(data.b2cEstimatedCost);
	$("#transcationPriceTotal").val(data.transactionPriceSum);
	$("#retailPrice").val(data.retailPrice);
	$("#retailPriceAmount").val(data.retailAmount);
	$("#originalActuralPrice").val(data.originalActualPrice);
	$("#acturalPrice").val(data.acturalPrice);
	$("#originalActuralPriceOfOptional").val(data.originalActuralPriceOfOptional);
	$("#acturalPriceOfOptionalCny").val(data.acturalPriceOfOptional);
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
	$('#materialsTable').bootstrapTable('remove', {
        field: "rowNum",
        values: rowNum
    });
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
}

//点击确认购销明细
function confirmMaterials(){
	var bootstrapValidator = $("#subsidiaryForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
    	return
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
	$("#orignalContractAmount").val(totalAmount);
	if($("#exchangeRate").val()){
		var contractAmount =toDecimal2( totalAmount*parseFloat($("#exchangeRate").val()))
		$("#contractAmount").val(contractAmount);
	}
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
		if(moment(item).isAfter(earlyDate)){
			earlyDate = item;
		}
	})
	return earlyDate;
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
			actualPrice:$("#actualPriceCny").val(),
			originalActualPrice:$("#originalActualPrice").val(),
			actualAmount:$("#acturalPriceAmount").val(),
			transactionPrice:$("#transcationPrice").val(),
			optionalActualPrice:$("#acturalPricaOfOptionalCny").val(),
			originalOptionalActualPrice:$("#originalOptionalActualPrice").val(),
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
   	/*contractValue: {
           validators: {
               notEmpty: {
                    message: '金额不能为空'
               },
               regexp: {
                   regexp: /^\d+(\.\d{0,2})?$/,
                   message: '请输入合法的金额，金额限制两位小数'
               },
               identical: {
					field: 'itemsAmount',
					message: '合同明细金额和购销明细金额不一致，请验证后再提交！'
				}
           }
       },
		itemsAmount: {
		}*/
       }
   });
}


//打开调研表
function openConfig(configContent){
	$("#materialconfigModal").modal('show');
	var rowNum = configContent.split('|')[0];
	var index = configContent.split('|')[1];
	var tableData = $('#materialsTable').bootstrapTable('getRowByUniqueId', rowNum);
	$("#materialConfigClazzCode").val(tableData.clazzCode);
	$("#materialConfigCode").val(tableData.materialCode);
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
	var discount = tableData.discount;
	var optionalTransationPrice = $("#viewOptionalTransationPrice").val();
	var optionalRetailPrice = $("#viewOptionalRetailPrice").val();
	var optionalStandardPrice = $("#viewOptionalStandardPrice").val();
	//可选项转移价
	tableData.optionalTransationPrice = toDecimal2(optionalTransationPrice); 
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
	debugger
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	//数量
	var quantity = tableData.quantity;
	//产品实卖价人民币
	var actualPriceCny = tableData.actualPrice;
	//产品实卖价凭证货币
	var originalActualPrice = toDecimal2(parseFloat(tableData.actualPrice)/parseFloat(exchangeRate));
	var actualAmount = toDecimal2(originalActualPrice*quantity);
	//可选项实卖价人民币
	var optionalActualPriceCny = tableData.optionalActualPrice;
	//可选项实卖价凭证货币
	var originalOptionalActualPrice = toDecimal2(parseFloat(optionalActualPriceCny)/parseFloat(exchangeRate));
	//转移价
	var transcationPrice = tableData.transactionPrice;
	//可选项转移价
	var optionalTransationPrice = tableData.optionalTransationPrice;
	//B2C预估价
	var b2cEstimatedPrice = tableData.b2cEstimatedPrice;
	//B2C预估价凭证货币
	var originalB2cEstimatedPrice = toDecimal2(parseFloat(tableData.b2cEstimatedPrice)/parseFloat(exchangeRate))
	//可选项实卖金额
	var optionalActualAmount = toDecimal2(quantity*originalOptionalActualPrice);
	//实卖价合计
	var actualPriceSum = toDecimal2(parseFloat(originalOptionalActualPrice)+parseFloat(originalActualPrice)+parseFloat(originalB2cEstimatedPrice));
	//实卖金额合计
	var actualAmountSum = toDecimal2(quantity*actualPriceSum);
	//B2C预估成本
	var b2cEstimatedCost = tableData.b2cEstimatedCost;
	//转移价合计
	var transactionPriceSum = toDecimal2((parseFloat(transcationPrice)*quantity)+parseFloat(optionalTransationPrice)+parseFloat(b2cEstimatedCost));
	
	tableData.optionalActualAmount = optionalActualAmount;
	
	tableData.actualPriceSum = actualPriceSum;
	
	tableData.actualAmountSum = actualAmountSum;
	
	tableData.optionalActualPrice = originalOptionalActualPrice;
	
	tableData.originalOptionalActualPrice = originalOptionalActualPrice;
	
	tableData.originalActualPrice = originalActualPrice;
	tableData.actualAmount = actualAmount;
	
	return tableData;
}

//复制调研表
function copyMaterials(rowNum){
	var configsData = localStorage[rowNum];
	var data = $('#materialsTable').bootstrapTable('getRowByUniqueId', rowNum);
	var rowData =JSON.parse(JSON.stringify(data));
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
	if(nextRowData==undefined){
		var rowData ={};
		var newRowNum = parseInt(currentRowNum)+10; 
		rowData["rowNum"] = newRowNum;
		rowData["quantity"] = 1;
		rowData["discount"] = 
		$("#materialsTable").bootstrapTable('append',rowData);
		$('#subsidiaryModal').modal('show');
		$("#subsidiaryForm")[0].reset();
		$("#itemCategory").html('');
		$('#materialsModalType').val('edit');
		$('#index').val(nextIndex);
		$('#rowNumber').val(newRowNum);
		initSubsidiary();
		//setTableToDifTab();
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
			insertRowNum = currentRowNum+Math.floor(parseInt(gap)/2)+1;
		}
		var rowData ={};
		rowData["rowNum"] = insertRowNum;
		$("#materialsTable").bootstrapTable('insertRow',{index:nextIndex,row:rowData});
		$('#subsidiaryModal').modal('show');
		$("#subsidiaryForm")[0].reset();
		$("#itemCategory").html('');
		$('#materialsModalType').val('edit');
		$('#index').val(nextIndex);
		$('#rowNumber').val(insertRowNum);
		initSubsidiary();
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
		updateAddressInProd(rowIndex);
	}
	$("#addressModal").modal('hide');
}

function updateAddressInProd(updateIndex){
	var seq = parseInt(updateIndex)+1;
	var tableData = $('#materialsTable').bootstrapTable('getData');
	var addressContent = $("#addressTable").bootstrapTable('getData')[updateIndex];
	$.each(tableData,function(index,item){
		if(item.deliveryAddressSeq==seq){
			item.provinceCode = addressContent.provinceCode;
			item.provinceName = addressContent.provinceName;
			item.cityCode = addressContent.cityCode;
			item.cityName = addressContent.cityName;
			item.districtCode = addressContent.districtCode;
			item.districtName = addressContent.districtName;
			var pca;
			if(item.provinceName){
				pca = item.provinceName;
				if(item.cityName){
					pca+="/"+item.cityName
				}
				if(item.districtName){
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
	debugger
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
	 var payments=new Array();
	 orderData.payments= payments;
	 var attachments = $("#fileList").bootstrapTable('getData');
	 orderData.attachments = attachments
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[i].rowNum];
		 items[i].isVirtual = 0;
		 items[i].discount = toDecimal2(parseFloat(items[i].discount)/100)
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
			    		/*window.location.href = ctxPath+'menu/orderManageList';*/
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
		 items[i].discount = toDecimal2(parseFloat(items[i].discount)/100)
		 if(configData){
			 var jsonObject = JSON.parse(configData);
			 var storedConfigs = jsonObject.configTableData;
			 var config = new Object();
			 var configs = new Array();
			 for(var j=0;j<storedConfigs.length;j++){
				 config['keyCode'] = storedConfigs[j].code;
				 config['valueCode'] = storedConfigs[j].configValueCode;
				 config['configurable'] = items[i].isConfigurable;
				 config['color'] = storedConfigs[j].color;
				 configs.push(config);
			 }
			 items[i]['configs'] = configs; 
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
			    	$("#viewOptionalTransactionPrice").val(res.data.transferPrice);
			    	$("#viewOptionalStandardPrice").val(res.data.standardPrice);
			    	$("#viewOptionalRetailPrice").val(res.data.retailPrice);
		    	}	    
	    	}else{
	    		$("#viewOptionalTransationPrice").val(res.data.transferPrice);
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
function viewGrossProfit(){
	if(orderOperationType!=1){
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
	 var orderData = $("#orderForm").serializeObject();
	 $('#transferType').attr("disabled",true);
	 $("#currency").attr("disabled",true);
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
	    		item.grossProfitMargin = (item.grossProfitMargin*100)+"%";
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
	$("#grossVersion").val($("#version").val());
	$("#grossContractRMBValue").val($("#contractAmount").val());
	$("#grossPerson").val($("#salesName").val());
	$("#grossDate").val($("#inputDate").val());
	$("#grossClazz").val($("#customerClazzName").val());
	var tableStringData = grossProfitMargin;
	var tableData =JSON.parse(tableStringData)
	$.each(tableData,function(index,item){
		item.grossProfitMargin = (item.grossProfitMargin*100)+"%";
	})
	$("#grossProfitTable").bootstrapTable('load', tableData);	
}

function exportGross(){
	var grossData = $("#grossProfitTable").bootstrapTable("getData");
	$.each(grossData,function(index,item){
		item.grossProfitMargin = item.grossProfitMargin.replace("%","")
		item.grossProfitMargin = toDecimal2(parseFloat(item.grossProfitMargin)/100);
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
	debugger
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
