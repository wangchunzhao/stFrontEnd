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
	//初始化付款方式页面
	var paymentTable = new TableInit('paymentTable','','',paymentColumns);
	paymentTable.init();

	var addressTable = new TableInit('addressTable','','',stockUpAddressColumns)
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
		insertStockUpAddress();
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

function insertStockUpAddress(){
	$("#addressTable").bootstrapTable('insertRow', {
	    index: 0,
	    row: {
	    	seq:1,
	    	pca:"山东青岛黄岛区",
	    	provinceCode:'20',
	    	provinceName:'山东',
	    	cityCode:'20.11',
	    	cityName:'青岛',
	    	districtCode:'20.11.13',
	    	districtName:'黄岛区',
	    	address:'山东青岛黄岛区'
	    }
	});
}
var stockUpAddressColumns = [{
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
}]