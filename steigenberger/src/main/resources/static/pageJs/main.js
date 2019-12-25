var userIdentity;
$(function () {
	DynamicMenu();
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

function DynamicMenu() {
	var myParentClass = $(".treeview");
	var myChildClass = $(".treeview-menu").children();
	//隐藏所有父菜单
	for (var i = 0; i < 7; i++) {
		myParentClass[i].style.display = "none";
	}
	//隐藏所有子菜单
	for (var i = 0; i < 6; i++) {
		myChildClass[i].style.display = "none";
	}

	var dynamicMenuConf = getDynamicMenuConf();

	if (dynamicMenuConf['1001'].code == '1001' && dynamicMenuConf['1001'].parentId == undefined) {
		myParentClass[0].style.display = "block";
		if(dynamicMenuConf['1001'].childs.length > 0) {
			dynamicMenuConf['1001'].childs.forEach(function (value) {
				dynamicChildSwitch(value, myChildClass)
			});
		}
	}
	if (dynamicMenuConf['1002'].code == '1002' && dynamicMenuConf['1002'].parentId == undefined) {
		myParentClass[1].style.display = "block";
		if(dynamicMenuConf['1002'].childs.length > 0) {
			dynamicMenuConf['1002'].childs.forEach(function (value) {
				dynamicChildSwitch(value, myChildClass)
			});
		}
	}
	if (dynamicMenuConf['1003'].code == '1003' && dynamicMenuConf['1003'].parentId == undefined) {
		myParentClass[2].style.display = "block";
		if(dynamicMenuConf['1003'].childs.length > 0) {
			dynamicMenuConf['1003'].childs.forEach(function (value) {
				dynamicChildSwitch(value, myChildClass)
			});
		}
	}
	if (dynamicMenuConf['1004'].code == '1004' && dynamicMenuConf['1004'].parentId == undefined) {
		myParentClass[3].style.display = "block";
		if(dynamicMenuConf['1004'].childs.length > 0) {
			dynamicMenuConf['1004'].childs.forEach(function (value) {
				dynamicChildSwitch(value, myChildClass)
			});
		}
	}
	if (dynamicMenuConf['1005'].code == '1005' && dynamicMenuConf['1005'].parentId == undefined) {
		myParentClass[4].style.display = "block";
		if(dynamicMenuConf['1005'].childs.length > 0) {
			dynamicMenuConf['1005'].childs.forEach(function (value) {
				dynamicChildSwitch(value, myChildClass)
			});
		}
	}
	if (dynamicMenuConf['800'].code == '800' && dynamicMenuConf['800'].parentId == undefined) {
		myParentClass[5].style.display = "block";
		if(dynamicMenuConf['800'].childs.length > 0) {
			dynamicMenuConf['800'].childs.forEach(function (value) {
				dynamicChildSwitch(value, myChildClass)
			});
		}
	}
	if (dynamicMenuConf['900'].code == '900' && dynamicMenuConf['900'].parentId == undefined) {
		myParentClass[6].style.display = "block";
		if(dynamicMenuConf['900'].childs.length > 0) {
			dynamicMenuConf['900'].childs.forEach(function (value) {
				dynamicChildSwitch(value, myChildClass)
			});
		}
	}
}

function getDynamicMenuConf() {
	var dynamicMenuConf = {
		'1001' : {
			code: '1001',
			name: '代办任务',
			childs: []
		},
		'1002' : {
			code: '1002',
			name: '新建订单',
			childs: []
		},
		'1003' : {
			code: '1003',
			name: '订单管理',
			childs: []
		},
		'1004' : {
			code: '1004',
			name: '合同管理',
			childs: []
		},
		'1005' : {
			code: '1005',
			name: '特批发货',
			childs: []
		},
		'800' : {
			code: '800',
			name: '报表管理',
			childs: ['1006','1007']
		},
		'900' : {
			code: '900',
			name: '系统管理',
			childs: ['1008','1010','1009','1019']
		},
	}
	return dynamicMenuConf;
}

function dynamicChildSwitch(value, myChildClass) {
	switch (value) {
		case '1006' :
			myChildClass[0].style.display = "block";
			break;
		case '1007' :
			myChildClass[1].style.display = "block";
			break;
		case '1008' :
			myChildClass[2].style.display = "block";
			break;
		case '1010' :
			myChildClass[3].style.display = "block";
			break;
		case '1009' :
			myChildClass[4].style.display = "block";
			break;
		case '1019' :
			myChildClass[5].style.display = "block";
			break;
	}
}
		