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
	
	//物料弹出框关闭时重置校验规则
	restMaterialValidator();
	//进页面前清空缓存
	localStorage.clear()
	
	produceSeqNum();
	//监控是否特批发货
	$('#specialShipmentValue').change(function () {
		if($('#specialShipmentValue').prop('checked')){
			 $("#isUrgentDelivery").val(1);
		 }else{
			 $("#isUrgentDelivery").val(0);
		 }
	})
	
	//监控是否特批下单
	$('#isSpecialOrderValue').change(function () {
		if($('#isSpecialOrderValue').prop('checked')){
			 $("#isSpecialOrder").val(1);
		 }else{
			 $("#isSpecialOrder").val(0);
		 }
	})
	setSpecialChecked();
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
	
	//文件列表初始化
	var fileListTable = new TableInit('fileList','','',fileListColumns)
	fileListTable.init();
	//文件上传成功处理方法
	$("#f_upload").on("fileuploaded", function(event, data, previewId, index) {
		var attachment = data.response.data;
		var a = $('#fileList').bootstrapTable('getData');
		var fileListLength = $('#fileList').bootstrapTable('getData').length;
		$("#fileList").bootstrapTable('insertRow', {
		    index: fileListLength,
		    row: {
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
		$('#shippDate').datepicker({ startDate: new Date() });
		getUserDetail();
	}else{
		$('#shippDate').datepicker({ startDate: new Date($("#createTime").val()) });
		initDropDownList();
		fillOrderAddress();
		fillItems();	
		fillAttachments();
	}
	if(orderOperationType=="2"){
		disableAll();
	}
});

//是否长期折扣变化
function isLongtermChange(obj){
	var isLongTerm = $(obj).val();
	if(isLongTerm=='1'){
		$("#mainDiscount").val('');
		$("#bodyDiscount").val('');
		 $("#mainDiscount").attr('disabled',true);
		 $("#bodyDiscount").attr('disabled',true);
		 $("#mainDiscountButton").attr('disabled',true);
		 $("#bodyDiscountButton").attr('disabled',true);
	}else{
		 $("#mainDiscount").attr('disabled',false);
		 $("#bodyDiscount").attr('disabled',false);
		 $("#mainDiscountButton").attr('disabled',false);
		 $("#bodyDiscountButton").attr('disabled',false);
	}
}

//柜体申请折扣应用
function applyBodyDiscount(){
	var discount = $("#bodyDiscount").val();
	if(!parseFloat(discount)||parseFloat(discount)<1||parseFloat(discount)>100){
		layer.alert("无法应用，折扣需大等于1小于等于100！");
		return;
	}
	
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<countMaterialsTable;i++){
		var materialsRowData = materialsTable[i];
		var materialType = materialsRowData.materialType
		if(materialType=='T101'){		
			applyDiscountForRow(discount,materialsRowData,"#materialsTable");
		}
	}
	layer.alert("应用成功！");
}

//机组申请折扣应用
function applyMainDiscount(){
	var discount = $("#mainDiscount").val();
	if(!parseFloat(discount)||parseFloat(discount)<1||parseFloat(discount)>100){
		layer.alert("无法应用，折扣需大等于1小于等于100！");
		return;
	}
	
	var discount = $("#mainDiscount").val();
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<countMaterialsTable;i++){
		var materialsRowData = materialsTable[i];
		var materialType = materialsRowData.materialType;
		if(materialType=='T102'){		
			applyDiscountForRow(i,discount,materialsRowData,"#materialsTable");
		}
	}
	layer.alert("应用成功！");
}

function applyDiscountForRow(discount,materialsRowData,tableId){
	var materialType=materialsRowData.materialType;
	if(materialType=='BG1GD1000000-X'||materialType=='BG1GD1000000-X'||materialType=='BG1R8R00000-X'){
		return;
	}
	//汇率
	var exchangeRate = $("#exchangeRate").val();
	var discountValue = (discount/100).toFixed(2);
	var quantity = parseFloat(materialsRowData.quantity);
	var optionalActualPrice  = parseFloat(materialsRowData.optionalActualPrice);
	var retailPrice = parseFloat(materialsRowData.retailPrice);
	//实卖价人民币
	var actualPrice = toDecimal2(parseFloat(retailPrice)*parseFloat(discountValue));
	//实卖价凭证货币
	var originalActualPrice = toDecimal2(parseFloat(actualPrice)/parseFloat(exchangeRate));
	//产品实卖金额
	var actualAmount = toDecimal2(parseFloat(quantity)*parseFloat(originalActualPrice));
	
	//B2C预估成本单价人民币  
	var b2cEstimatedCost = parseFloat(materialsRowData.b2cEstimatedCost);
	
	//B2c预估成本单价凭证货币
	var originalB2cEstimatedCost =toDecimal2(b2cEstimatedCost/parseFloat(exchangeRate));
	
	//可选项实卖价凭证货币
	var optionalActualPrice = parseFloat(materialsRowData.optionalActualPrice);
	
	var actualPriceSum = toDecimal2(parseFloat(originalActualPrice)+parseFloat(optionalActualPrice)+parseFloat(originalB2cEstimatedCost));
	var actualAmountSum = toDecimal2(parseFloat(actualPriceSum)*parseFloat(quantity));
	$(tableId).bootstrapTable('updateByUniqueId', {
	    id:materialsRowData.rowNum,
	    row: {
	    	discount:discount,
	    	actualPrice:actualPrice,
	    	originalActualPrice:originalActualPrice,
	    	actualAmount:actualAmount,
	    	actualPriceSum:actualPriceSum,
	    	actualAmountSum:actualAmountSum	    	
	    }
	});
	calMergeDiscount();
}

