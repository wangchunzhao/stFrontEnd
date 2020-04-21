$(function(){
	
	InitRoles();
	InitSapSalesOffice();
	
//	$('#roleName').on('change',function(){
//		if("1,2".indexOf($(this).val())>-1){
//			$('#area').parents('.form-group').show();
//		}else{
//			$('#area').parents('.form-group').hide();
//		}
//	});
	function BuildOption(name,val){
		return "<option value='"+val+"'>"+name+"</option>";
	}
	
	function BuildAreaOption(name,code){
		return "<option value='"+code+"'>"+name+"</option>";
	}
});

function InitRoles(){
	var result = null;
	$.ajax({ url: "/st/permission/roleList",
		async: false,//改为同步方式
		type: "POST",
		data: { },
		success: function(ret){
			if(ret.status==200){
				result = ret.data
			}else{
				alert('角色列表请求错误！')
			}
		}
	});
	return result;
}

function InitSapSalesOffice(){
	var result = null;
	$.ajax({ url: "/st/permission/sapSalesOfficelist",
		async: false,//改为同步方式
		type: "POST",
		data: { },
		success: function(ret){
			if(ret.status==200){
				result = ret.data
			}else{
				alert('区域列表请求错误！')
			}
		}
	});
	return result;
}

//提交表单
function add(){
//	var username = document.getElementById('username').value;
//	var useremil = document.getElementById('useremil').value;
//	var userid = document.getElementById('userid').value;
//	var roleName = document.getElementById('roleName').value;
//	var area = document.getElementById('area').value;
//	var status = document.getElementById('status').value;
	var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
	var obj = document.getElementById("useremil"); //要验证的对象
	var roleName = document.getElementById('roleName').value;
	var userid = document.getElementById('userid').value;
	var tel = document.getElementById('telnum').value;
//	alert(tel);
	if(!reg.test(obj.value)){
//		alert("邮箱格式不正确!");
//		return false;
	}else if(roleName=="" ||tel==undefined|| userid==undefined){
		alert("表单信息填写不完整!");
		return false;
	}else{
		$.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "/st/permission/adduser" ,//url
            data: $('#form1').serialize(),
            success: function (result) {
                console.log(result);//打印服务端返回的数据(调试用)
                if (result.status == 200) {
                    alert("保存成功");
					window.location.href = ctxPath+"/menu/userIndex";
                }else{
                	var a = result.msg;
                	alert(a);
                }
                ;
            },
            error : function() {
                alert("保存失败！");
            }
        });
	}
	
}