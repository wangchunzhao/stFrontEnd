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