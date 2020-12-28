$(function () {	
	$(document).keyup(function(event){
	    if(event.keyCode ==13){
	    	window.event.returnValue = false;
	    }
	});
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
	 $('#selectProvince').selectpicker({
     });
	//物料弹出框关闭时重置校验规则
	restMaterialValidator();
	
	//进页面前清空缓存
	localStorage.clear()
	
	produceSeqNum();
	/*//监控是否特批发货
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
	setSpecialChecked();*/
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
	//初始化行项目校验
	initSubsidiartFormValidator();
	//初始化订单校验
    initOrderFormValidator();
    //初始化行项目表格
	initMarialsTables();
	$('#first').tab('show');
	
	defaultCollapse();
	getUserDetail();
	if(status==null||status==""||status=="undefined"){
		$('#shippDate').datepicker({ 
			startDate: new Date(),
			format: "yyyy-mm-dd",
			language: 'zh-CN',
		});
		$('#requiredDeliveryTime').datepicker({
			format: "yyyy-mm-dd",
			language: 'zh-CN',
			startDate: new Date() 
		});	
		var dateTime=new Date();
		dateTime=dateTime.setDate(dateTime.getDate()+21);
		dateTime=new Date(dateTime);
		$('#requiredDeliveryTime').datepicker("setDate",dateFtt("yyyy-MM-dd",dateTime))
	}else{
		$('#shippDate').datepicker({ 
			startDate: new Date($("#createTime").val()) ,
			format: "yyyy-mm-dd",
			language: 'zh-CN',
		
		});
		$('#requiredDeliveryTime').datepicker({
			format: "yyyy-mm-dd",
			language: 'zh-CN',
			startDate: new Date() 
		});	
		fillOrderAddress();
		initDropDownList();
		fillItems();
		fillAttachments();
	}
	if(orderOperationType=="2"){
		disableAll();
	}
	sapOrderCheck();
	getSapItemStatus();
});