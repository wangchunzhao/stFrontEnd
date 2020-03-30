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
		var installationTerm = installationTerms[$("#customerClazzCode").val()];
		$.each(installationTerm, function (key, value) {
			$("#installType").val(key);
			$("#installTypeName").val(value);

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
		//修改查看订单时,辉县地址数据
		fillOrderAddress();
		initDropDownList();
		fillItems();
		
		fillAttachments();
	}
	if(orderOperationType=="2"){
		disableAll();
	}
});