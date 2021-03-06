/**
 * by Phil Guan
 */
$(document).ready(function(){
	$("#sjInput").click(function(){
		$("#showSjInput").css("display","block");
		$("#sjInput").css("background-color","#FFB3FF");
		$("#showSjTj").css("display","none");
		$("#sjTj").css("background-color","#808080");
	});
	$("#sjTj").click(function(){
		$("#showSjInput").css("display","none");
		$("#sjInput").css("background-color","#808080");
		$("#showSjTj").css("display","block");
		$("#sjTj").css("background-color","#FFB3FF");
	});
	$("#submitSJ").click(function(){
		var $btn = $(this).button('loading');
		
		var contactTel1 = $("#contactTel1").val();
		if(contactTel1 == ""){
			alert("请输入联系电话1");
			$("#contactTel1").focus();
			$btn.button('reset');
			return;
		}
		
		var kfManagement = $("#kfManagement").val();
		if(kfManagement == ""){
			alert("请选择所属部门");
			$btn.button('reset');
			return;
		}
		
		$.ajax({
			url : "addOpp.action",
			type : "post",
			data : $("#sjForm").serialize(),
			success : function(data){
				var result = eval("(" + data + ")");
				var result1 = result.result;
				if(result1 == "success"){
					alert("提交成功！");
					$btn.button('reset');
					window.location.reload();
				}else if(result1 == "fail"){
					$btn.button('reset');
					alert("此学员已存在！");
				}
			},
			error : function(XMLResponse){
				$btn.button('reset');
				alert("提交失败！请联系系统管理员！");
			}
		});
	});
	
	$("#showManagementExplain").click(function(e){
		$("#managementExplain").modal({
			keyboard : true
		});
	});
});