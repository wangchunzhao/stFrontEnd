var ctxPath  = [[@{/}]];
			$(function () {
				$.ajax({
		            type: "GET",
		            dataType: "json",
		            url: ctxPath+"user/getOperations" ,
		            async:false,
		            success: function (result) {
		                console.log(result);
		               var operationNames = result.data;
			               if(operationNames.indexOf("todo")==-1){
			            	   $('#todo').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("newOrder")==-1){ 
				       			$('#newOrder').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("orderManage")==-1){
				       			$('#orderManage').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("contract")==-1){
				       			$('#contract').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("newSpecial")==-1){
				       			$('#newSpecial').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("report1")==-1){
				       			$('#report1').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("report2")==-1){
				       			$('#report2').attr('href','javascript:void(0);');
				       		}
				       		
				       		if(operationNames.indexOf("report1")==-1&&operationNames.indexOf("report2")==-1){
				       			$('#report').attr('href','javascript:void(0);');
				       		}
				       		
				       		if(operationNames.indexOf("user")==-1){
				       			$('#user').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("permission")==-1){
				       			$('#permission').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("parameterSetting")==-1){
				       			$('#parameterSetting').attr('href','javascript:void(0);');
				       		}
				       		if(operationNames.indexOf("role")==-1){
				       			$('#role').attr('href','javascript:void(0);');
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
		