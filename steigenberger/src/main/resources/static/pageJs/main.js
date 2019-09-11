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
		            	var operationNames = "";
		            	for(var i=0;i<operationList.length;i++){
		            		operationNames+=operationList[i].name+",";
		            	}
		              	 //alert(operationNames);
		               if(operationNames.indexOf("todo")==-1){
		            	   $('#todo').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("newOrder")==-1){ 
			       			$('#newOrder').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("orderManage")==-1){
			       			$('#orderManage').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("contract")==-1){
			       			$('#contract').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("newSpecial")==-1){
			       			$('#newSpecial').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("report1")==-1){
			       			$('#report1').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("report2")==-1){
			       			$('#report2').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		
			       		if(operationNames.indexOf("report1")==-1&&operationNames.indexOf("report2")==-1){
			       			$('#report').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		
			       		if(operationNames.indexOf("user")==-1){
			       			$('#user').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("permission")==-1){
			       			$('#permission').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("parameterSetting")==-1){
			       			$('#parameterSetting').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		if(operationNames.indexOf("role")==-1){
			       			$('#role').attr('href',ctxPath+"main/noAuthorization");
			       		}
			       		
			       		if(operationNames.indexOf("user")==-1&&
			       				operationNames.indexOf("permission")==-1&&
			       				operationNames.indexOf("parameterSetting")==-1&&
			       				operationNames.indexOf("role")==-1
			       				){
			       			
			       			//$('#sys').style="display:none";
			       			$('#sys').attr('style',"display:none");
			       		}
		                
		            },
		            error : function() {
		                //alert("没有查到数据！");
		            }
		        }); 
			});
			
		