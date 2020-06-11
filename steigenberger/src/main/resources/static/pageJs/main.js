var userIdentity;
$(function () {
	$.ajax({
        type: "GET",
        dataType: "json",
        url: ctxPath+"user/getOperations" ,
        async:false,
        success: function (result) {
        	console.log(result.data);
        	var user = result.data;
        	//profile赋值
        	//$("#userId").text("id:"+user.id);
        	//$("#userName").text("userName:"+user.userName);
        	//$("#userIdentity").text("userIdentity:"+user.userIdentity);
        	
        	$("#userId").text(user.id);
        	$("#userName").text(user.userName);
        	$("#userIdentity").text(user.userIdentity);
        	if(user.tel==null){
        		$("#tel").text("");
        	}else{
        		$("#tel").text(user.tel);
        	}
        	
        	var roleList = user.roles;
        	var roleNames = "";
        	for(var i=0;i<roleList.length;i++){
        		if(i==roleList.length-1){
        			roleNames+=roleList[i].name;
        		}else{
        			roleNames+=roleList[i].name+",";
        		}
        	}
        	//$("#userRoles").text("userRoles:"+roleNames);
        	$("#userRoles").text(roleNames);
        	
        	//判断权限
        	userIdentity = result.data.userIdentity;
        	var operationList = result.data.operations;
        	
        	var operationNames = ",";
        	if(operationList){
        		for(var i=0;i<operationList.length;i++){
            		operationNames+=operationList[i].id+",";
            	}
        	}
           if(operationNames.indexOf(",1001,")==-1){
        	   $('#todo').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1002,")==-1){ 
       			$('#newOrder').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1003,")==-1){
       			$('#orderManage').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1004,")==-1){
       			$('#contract').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1005,")==-1){
       			$('#newSpecial').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1006,")==-1){
       			$('#report1').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf("1007,")==-1){
       			$('#report2').attr('href',ctxPath+"main/noAuthorization");
       		}
       		
       		/*if(operationNames.indexOf("report1")==-1&&operationNames.indexOf("report2")==-1){
       			$('#report').attr('href',ctxPath+"main/noAuthorization");
       		}*/
       		
       		if(operationNames.indexOf(",1008,")==-1){
       			$('#user').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1013,")==-1){
       			$('#permission').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1010,")==-1){
       			$('#parameterSetting').attr('href',ctxPath+"main/noAuthorization");
       		}
       		if(operationNames.indexOf(",1009,")==-1){
       			$('#role').attr('href',ctxPath+"main/noAuthorization");
       		}
       		
       		if(operationNames.indexOf(",1019,")==-1){
       			$('#freight').attr('href',ctxPath+"main/noAuthorization");
       		}
       		
       		/*if(operationNames.indexOf("user")==-1&&
       				operationNames.indexOf("permission")==-1&&
       				operationNames.indexOf("parameterSetting")==-1&&
       				operationNames.indexOf("role")==-1
       				){
       			
       			//$('#sys').style="display:none";
       			$('#sys').attr('style',"display:none");
       		}*/
            
        },
        error : function() {
            //alert("没有查到数据！");
        }
    }); 
});
//退出按钮点击事件	
function signOut(){  
 //   alert("你点击了按钮哦"); 
    $.post(ctxPath+'/loginOut',null,function(ret){
		window.location.href=ctxPath;
	},null);
} 		 	

/**序列化表单，多个value用数组存放**/
$.fn.serializeObject = function() {  
    var o = {};  
    var arr = this.serializeArray();  
    $.each(arr,function(){  
        if (o[this.name]) {  //返回json中有该属性
            if (!o[this.name].push) { //将已存在的属性值改成数组
                o[this.name] = [ o[this.name] ];
            }  
            o[this.name].push(this.value || ''); //将值存放到数组中
        } else {  //返回json中没有有该属性
            o[this.name] = this.value || '';  //直接将属性和值放入返回json中
        }  
    });  
    return o;  
}

// 將json對象轉換為url
function toUrl(data) {
    try {
        var tempArr = [];
        for (var i in data) {
            var key = encodeURIComponent(i);
            var value = encodeURIComponent(data[i]);
            tempArr.push(key + '=' + value);
        }
        var urlParamsStr = tempArr.join('&');
        return urlParamsStr;
    } catch (err) {
        return '';
    }
}

function service(options) {
	var success = options.success;
	options.success = function(result,status,xhr) {
		if (result != null && result.status && result.status == 'logout') {
			alert(result.msg);
			window.location.href = ctxPath;
		}
		success.call(result, status, xhr);
	}
	$.ajax(options);
}

function checkLogout(result) {
	console.log(result);
	console.log(result != null && result.status && result.status == 'logout');
	if (result != null && result.status && result.status == 'logout') {
		window.location.href = ctxPath;
		alert(result.msg);
	}
}