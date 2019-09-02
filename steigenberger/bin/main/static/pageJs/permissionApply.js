//提交表单
function add(){
	var username = document.getElementById('username').value;
	var useremil = document.getElementById('useremil').value;
	var userid = document.getElementById('userid').value;
	var roleName = document.getElementById('roleName').value;
	var area = document.getElementById('area').value;
	var status = document.getElementById('status').value;
	$.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "/permission/adduser" ,//url
            data: $('#form1').serialize(),
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)
                if (result.resultCode == 200) {
                    alert("SUCCESS");
                }
                ;
            },
            error : function() {
                alert("异常！");
            }
        });
}