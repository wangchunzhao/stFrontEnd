$(function () {
	localStorage.clear()
	var paymentTable = new TableInit('paymentTable','','',paymentColumns);
	paymentTable.init();
	
	var addressTable = new TableInit('addressTable','','',addressColumns)
	addressTable.init();
	
	initMarialsTables();
	$('#first').tab('show');
	$('#end').datepicker();
	var nowDateString = moment(new Date()).format('YYYY-MM-DD');
	$("#inputDate").val(nowDateString);
	defaultCollapse();
});

//设置面板折叠显示

function defaultCollapse(){
	$('#customerBasicInfo').collapse('show');
	$('#contractBasicInfomation').collapse('hide');
	$('#contractDetailInfo').collapse('hide');
	$('#researchTableContent').collapse('hide');
	$('#paymentMethod').collapse('hide');
	$('#purchaseDetail').collapse('hide');
	$('#infoCheck').collapse('hide');
	$('#attachmentInfo').collapse('hide');
	
}


//Customer basic infomation js start 
function openSearchUnit(){
	$('#unitModal').modal('show');	
	var customerUnitTable = '/steigenberger/order/customers'
	var contractUnitTable = new TableInit('contractUnitTable',customerUnitTable,queryUnitParams,contractUnitTableColumn);
	contractUnitTable.init();
	$("#contractUnitTable").on('click-row.bs.table',function($element,row,field){
		$('#unitModal').modal('hide');
		$("#contractUnitName").val('');
		$("#customer").val(row.name)
		$("#customerClazz").val(row.clazzName)
	})
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

function salesTypeChange(obj,offices,taxRate){
	var saleType = $(obj).val();
	$("#taxtRate").val(taxRate[saleType]);
	if(saleType=="20"){
		debugger
		$("#freightDiv").show();
		$('#selectProvince').val('');
		$('#citySelect').val('');
		$('#selectDistrict').val('');
		$('#citySelect').attr("disabled",true);
		$('#selectDistrict').attr("disabled",true);	
		$('#selectProvince').attr("disabled",true);
		$('#orignalContractAmount').attr("readonly",false);
		$('#incoterm').attr("readonly",false);
		$('#incotermContect').attr("readonly",false);
		$('#warrenty').val('');
		$('#warrenty').attr("readonly",true);
		$('#installCode').val('');
		$('#installCode').attr("readonly",true);
		$('#transferType').attr("readonly",true);
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
		$('#warrenty').attr("readonly",false);
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
		if($(obj).val() == '10'){
			$("#officeSelect").append("<option value=''>--选择大区--</option>");
		}	
		$("#officeSelect").val('');
		var officesMap = offices[saleType];
		$.each(officesMap, function (key,value) {
				$("#officeSelect").append("<option value='" + key + "'>" + value + "</option>");			
		});
		if($(obj).val() != '10'){
			$("#officeSelect").attr("readonly",true);
			$("#selectGroup").attr("readonly",true);
		}
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
		$('#amount').val(1);
		var materialTypeTableUrl = "/steigenberger/order/materials"
		var materialTypeTable = new TableInit('materialTypeTable',materialTypeTableUrl,queryMaterialTypeParams,materialTypeColumn)
		materialTypeTable.init();
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
	       fillMaterailValue(data); 
	    }
	});
}
//将查出来的物料信息填充到各个field中
function fillMaterailValue(data){
	$("#groupName").val(data.groupName);
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
	$("#discount").val('0.4');
	var discountValue = $("#discount").val();
	var discount = discountValue.split("%")[0];
	var acturalPrice = (data.retailPrice*discount)/100
	$("#acturalPrice").val(toDecimal2(acturalPrice));
	$("#acturalPriceAmount").val(toDecimal2(amount*(acturalPrice)));
	$("#acturalPriceTotal").val(toDecimal2(parseFloat($("#acturalPrice").val())+parseFloat($("#acturalPricaOfOptional").val())));
	$("#acturalPriceAmountTotal").val(toDecimal2(($("#acturalPriceTotal").val())*amount));
	if($('#isPurchased').val()=='生产'){
		$("#producePeriod").val(data.period);
	}else{
		$("#purchasePeriod").val(data.period);
	}
	$("#deliveryDate").val(data.deliveryDate);
	$("#produceDate").val(data.produceDate);
	$("#onStoreDate").val(data.onStoreDate);
	
	
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
      return false;   
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
function editMaterials(index){
	$('#subsidiaryModal').modal('show');
	$('#materialsModalType').val('edit');
	$('#materialsIndex').val(index);
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
	
}
//删除其他tab相同的行
function removeRelatedRow(identification){
	debugger
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

//确认购销明细modal
function confirmMaterials(){
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
}


//购销明细行数据

function confirmRowData(index,identification){
	var idenf = identification
	if(idenf==''){
		idenf=index+"|"+$("#materialsType").val()
	}
	var row = {
			index:index+1,
			materialTypeName:$("#materialTypeName").val(),
			code:$("#materialCode").val(),
			identification:idenf,
			clazzCode:$("#materialClazzCode").val(),
			isPurchased:$("#isPurchased").val(),
			groupName:$("#groupName").val(),
			amount:$("#amount").val(),
			unitName:$("#unitName").val(),
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
			producePeriod:$("#producePeriod").val(),
			deliveryDate:$("#deliveryDate").val(),
			produceDate:$("#produceDate").val(),
			shippDate:$("#shippDate").val(),
			materialAddress:$("#materialAddress").val(),
			onStoreDate:$("#onStoreDate").val(),
			purchasePeriod:$("#purchasePeriod").val(),
			b2cRemark:$("#b2cRemark").val(),
			specialRemark:$("#specialRemark").val()
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

function addAddress(){
	$("#addressModal").modal('show');
	$("#addressModalType").val('new');
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
	var materialsType = identificationSplit[1];
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	if(materialsType=='T101'){
		var countMaterialsTableall1 = $('#materialsTableall1').bootstrapTable('getData').length;
		var rowData = confirmRowData(countMaterialsTableall1,'');
		$("#materialsTableall1").bootstrapTable('insertRow', {
		    index: countMaterialsTableall1,
		    row: rowData
		});
		var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
	}else if(type=='T102'){
		var countMaterialsTableall2 = $('#materialsTableall2').bootstrapTable('getData').length;
		var rowData = confirmRowData(countMaterialsTableall2,'');
		$("#materialsTableall2").bootstrapTable('insertRow', {
		    index: countMaterialsTableall2,
		    row: rowData
		});
		var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
	}else if(type=='T103'){
		var countMaterialsTableall3 = $('#materialsTableall3').bootstrapTable('getData').length;
		var rowData = confirmRowData(countMaterialsTableall3,'');
		$("#materialsTableall3").bootstrapTable('insertRow', {
		    index: countMaterialsTableall1,
		    row: rowData
		});
		var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
	}else if(type=='T104'){
		var countMaterialsTableall4 = $('#materialsTableall4').bootstrapTable('getData').length;
		var rowData = confirmRowData(countMaterialsTableall4,'');
		$("#materialsTableall4").bootstrapTable('insertRow', {
		    index: countMaterialsTableall1,
		    row: rowData
		});
		var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
	}else if(type=='T105'){
		var countMaterialsTableall5 = $('#materialsTableall5').bootstrapTable('getData').length;
		var rowData = confirmRowData(countMaterialsTableall5,'');
		$("#materialsTableall1").bootstrapTable('insertRow', {
		    index: countMaterialsTableall5,
		    row: rowData
		});
		var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
	}else if(type=='T106'){
		var countMaterialsTableall6 = $('#materialsTableall6').bootstrapTable('getData').length;
		var rowData = confirmRowData(countMaterialsTableall6,'');
		$("#materialsTableall1").bootstrapTable('insertRow', {
		    index: countMaterialsTableall6,
		    row: rowData
		});
		var rowDataAll = confirmRowData(countMaterialsTable,rowData.identification);
		$("#materialsTable").bootstrapTable('insertRow', {
		    index: countMaterialsTable,
		    row:rowDataAll
		});
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
		    	shippingAddress:shippingAddress
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


function editAddress(index){
	var row = $('#addressTable').bootstrapTable('getData')[index];
	$("#addressIndex").val(index);
	$("#addressModalType").val("edit");
	$("#addressModal").modal('show');
	$("#selectProvince").val(row.provinceValue);
	$("#citySelect").val(row.cityValue);
	$("#selectDistrict").val(row.areaValue);
	$("#shippingAddress").val(row.shippingAddress);
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


//保存订单
function saveOrder(){
	 var orderData = $("#orderForm").serializeObject();
	 var items = $("#materialsTable").bootstrapTable('getData');
	 orderData.items = items;
	 for(var i=0;i<items.length;i++){
		 var configData = localStorage[items[0].identification];
		 var jsonObject = JSON.parse(configData);
		 items[0]['configs']= jsonObject.configTableData
		 items[0]['configComments'] = jsonObject.remark
	 }

	 orderData.orderAddress = $("#addressTable").bootstrapTable('getData');
	 $.ajax({
		    url: "/steigenberger/order/dealer?action="+'save',
		    contentType: "application/json;charset=UTF-8",
		    data: JSON.stringify(orderData),
		    type: "POST",
		    dataType: "json",
		    success: function(data) { 
		    }	 
	});
}


//调研表查看物料
function viewConfig(){
	var bomCode = $("#materialConfigCode").val();
	var formData = $("#materialConfigForm").serializeObject();
	formData.bomCode = bomCode;
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
	field : 'shippingAddress',
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
    	if(value){
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
},{
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
    	$.each(value,function(index,item){
    		if(item.code==row.configValueCode){
    			start+='<option value=\'' + item.code + '\' selected = "selected">' + item.name + '</option>'
    		}else{
    			start+='<option value=\'' + item.code + '\'>' + item.name + '</option>'
    		}	
    	})
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