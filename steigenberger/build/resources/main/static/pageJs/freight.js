$(function () {

    //1.初始化Table
    var oTable = new TableInit();
    oTable.Init();

});

var TableInit = function () {
	var oTableInit = new Object();
	
	oTableInit.Init = function () {
		$('#mytab').bootstrapTable({
			method : 'get',
//			url : "/steigenberger/myOrder/myOrderManageList",//请求路径
			url : "/steigenberger/freight/List",//请求路径
			striped : true, //是否显示行间隔色
			toolbar: '#toolbar',
			cache: false,
			pagination: true,                   //是否显示分页（*）
		    sortable: true,                     //是否启用排序
		    clickToSelect: true,               //是否启用点击选中行
		    sortOrder: "asc",                   //排序方式
			pageNumber : 1, //初始化加载第一页
			pagination : true,//是否分页
			sidePagination : 'server',//server:服务器端分页|client：前端分页
			pageSize : 10,//单页记录数
			pageList : [ 10, 20, 30 ],//可选择单页记录数
			showRefresh : false,//刷新按钮
			queryParams : function (params) {
			    var temp = {
			        limit : params.limit, // 每页显示数量
			        offset : params.offset, // SQL语句起始索引
			        page: (params.offset / params.limit) + 1,   //当前页码
		
			        name:$('#countyName').val()
			       
			       // Tel:$('#search_tel').val()
			    };
			    return temp;
			},
			columns : [ {
				title : '一级地',
				field : 'pname',
				sortable : true
			},{
				title : '二级地',
				field : 'cname',
				sortable : true
			},{
				title : '三级地',
				field : 'name',
				sortable : true
			},{
				title : '供应商<20m³单价',
				field : 'price',
				sortable : true
			},{
				title : '供应商<20m³送货费',
				field : 'price1',
				sortable : true
			},{
				title : '供应商>20<50m³单价',
				field : 'price2',
				sortable : true
			},{
				title : '供应商>20<50m³送货费',
				field : 'price3',
				sortable : true
			},{
				title : '供应商>=50m³单价',
				field : 'price4',
				sortable : true
			},{
				title : '供应商>50m³送货费',
				field : 'price5',
				sortable : true
			},{
				title : '客户<=20m³单价',
				field : 'price6',
				sortable : true
			},{
				title : '客户<=20m³送货费',
				field : 'price7',
				sortable : true
			},{
				title : '客户>20<50m³单价',
				field : 'price8',
				sortable : true
			},{
				title : '客户>20<50m³送货费',
				field : 'price9',
				sortable : true
			},{
				title : '客户>=50m³单价',
				field : 'name10',
				sortable : true
			},{
				title : '客户>=50m³送货费',
				field : 'price11',
				sortable : true
			}]
		})
			};
		return oTableInit;
};
  
//查询按钮事件
$('#search_btn').click(function() {
	$('#mytab').bootstrapTable('refresh', {
		url : '/steigenberger/freight/List'
	});
})

//重置按钮事件
$('#resetBtn').click(function() {
	
})

//上传
initUpload("excelFile",  "/steigenberger/freight/upload");
    function initUpload(ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl: uploadUrl, //上传的地址
            uploadAsync: true, //默认异步上传
            showCaption: true,//是否显示标题
            showUpload: true, //是否显示上传按钮
            browseClass: "btn btn-primary", //按钮样式
            allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀
            maxFileCount: 10,//最大上传文件数限制
            previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
            showPreview: true, //是否显示预览
            previewFileIconSettings: {
                'docx': '<i ass="fa fa-file-word-o text-primary"></i>',
                'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
                'xls': '<i class="fa fa-file-excel-o text-success"></i>',
                'pptx': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
                'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
                'pdf': '<i class="fa fa-file-archive-o text-muted"></i>',
                'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            },
            uploadExtraData: function () {
                var extraValue = "test";
                return {"excelType": extraValue};
            }
        });
    }
    $("#excelFile").on("fileuploaded", function (event, data, previewId, index) {
        console.log(data);
        if(data.response.status == 200)
        {
            alert(data.files[index].name + "上传成功!");
            //上传成功后刷新
            $('#mytab').bootstrapTable('refresh', {
        		url : '/steigenberger/freight/List'
        	});
        //关闭
            $(".close").click();
        }
        else{
            alert(data.files[index].name + "上传失败!" + data.response.message);
        //重置
        $("#excelFile").fileinput("clear");
        $("#excelFile").fileinput("reset");
        $('#excelFile').fileinput('refresh');
        $('#excelFile').fileinput('enable');
        }
    });
    
    
