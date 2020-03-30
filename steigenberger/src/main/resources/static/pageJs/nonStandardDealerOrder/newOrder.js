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
	var materialsTable = $('#materialsTable').bootstrapTable('getData');
	var countMaterialsTable = $('#materialsTable').bootstrapTable('getData').length;
	for(var i=0;i<countMaterialsTable;i++){
		var materialsRowData = materialsTable[i];
		var materialType = materialsRowData.materialType
		if(materialType=='T101'){		
			applyDiscountForRow(discount,materialsRowData,"#materialsTable");
		}
	}
}

//机组申请折扣应用
function applyMainDiscount(){
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
	calMergeDiscount();
}

function applyDiscountForRow(discount,materialsRowData,tableId){
	var discountValue = (discount/100).toFixed(2);
	var quantity = materialsRowData.quantity;
	var retailPrice = materialsRowData.retailPrice
	var optionalActualPrice = materialsRowData.optionalActualPrice;
	var actualPrice = toDecimal2(retailPrice*discountValue);
	var actualAmount = toDecimal2(quantity*actualPrice);
	var actualPriceSum = toDecimal2(parseFloat(actualPrice)+parseFloat(optionalActualPrice));
	var actualAmountSum = toDecimal2(actualPriceSum*quantity);
	$(tableId).bootstrapTable('updateByUniqueId', {
	    id:materialsRowData.rowNum,
	    row: {
	    	discount:discount,
	    	actualPrice:actualPrice,
	    	actualAmount:actualAmount,
	    	actualPriceSum:actualPriceSum,
	    	actualAmountSum:actualAmountSum	    	
	    }
	});
	calMergeDiscount();
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
		if(materialType=="T101"||materialType=="T102"){
			var discount = materialRowData.discount;
			var retailAmount = materialRowData.retailAmount;
			var discountRetailAmount = (accDiv(accMul(retailAmount,discount),100)).toFixed(1);
			discountRetailAmountsArray.push(discountRetailAmount);
			retailAmountsArray.push(retailAmount);
		}
	}
	var discountRetailAmounts =parseFloat(0).toFixed(1);
	$.each(discountRetailAmountsArray,function(index,value){
		discountRetailAmounts=accAdd(discountRetailAmounts,parseFloat(value));
	});
	var retailAmounts = parseFloat(0).toFixed(1);
	$.each(retailAmountsArray,function(index,value){
		retailAmounts=accAdd(retailAmounts,parseFloat(value));
	});
	var mergerDiscount = (accMul(accDiv(discountRetailAmounts,retailAmounts),100)).toFixed(1);
	$("#orderDiscount").val(mergerDiscount);
	$("#approveDiscount").val(mergerDiscount);
	
	
}
