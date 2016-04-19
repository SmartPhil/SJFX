/**
 * by phil	
 */
var stuName,parentName,contactTel1,contactTel2,needCls,management,channel,channelType,createDate;

$(document).ready(function(){
	var table = $("#mainTable").DataTable({
		"scrollX": true
	});

	$("#mainTable tbody").on("click","button[name='btn_handle']",function(e){
		var td = $(this).parent();
		var tr = $(td).parent();
		var tds = $(tr).children("td");
		$("#handle_stuNameTd").text($(tds[0]).text());
		$("#handle_parentNameTd").text($(tds[1]).text());
		$("#handle_contactTel1Td").text($(tds[2]).text());
		$("#handle_contactTel2Td").text($(tds[3]).text());
		$("#handle_needClsTd").text($(tds[4]).text());
		$("#handle_managementTd").text($(tds[5]).text());
		$("#handle_channelNameTd").text($(tds[6]).text());
		$("#handle_channelTypeTd").text($(tds[7]).text());
		$("#handle_createDateTd").text($(tds[8]).text());
		$("#handle_isValidTd").text("有效");
		$("#handle_noValidReason").text("无");
		$("#handle_isAssign").text("未分配");
		$("#handle_assignEmployee").text("无");
		
		$.ajax({
			url : "getFollowContent.action",
			type : "post",
			dataType : "json",
			data : {
				contactTel1 : $("#handle_contactTel1Td").text(),
				contactTel2 : $("#handle_contactTel2Td").text()
			},
			success : function(data){
				$("#handleContentTable tbody").html("");
				var result = eval("(" + data + ")");
				for(var i = 0; i < result.length; i++){
					$("#handleContentTable tbody").append("<tr>"
							+ "<td>" + result[i].followTime + "</td>"
							+ "<td>" + result[i].followContent + "</td>"
							+ "<td>" + result[i].followEmployee + "</td>"
							+ "<td>" + result[i].answer + "</td>"
							+ "</tr>")
				}
			},
			error : function(data){
				
			}
		});
		
		$("#handleOppModal").modal({
			keyboard : true
		});
	});
	
	$("#btn_submitHandle").click(function(){
		$.ajax({
			url : "addFCon.action",
			type : "post",
			dataType : "json",
			data : {
				contactTel1 : $("#handle_contactTel1Td").text(),
				contactTel2 : $("#handle_contactTel2Td").text(),
				followTime : $("#followTime").text(),
				followContent : $("#followContent").val(),
				answer : $("#answer").val(),
				followEmployee : $("#usernameShow").text().split(":")[1]
			},
			success : function(data){
				var info = eval("(" + data + ")");
				var result = info.result;
				if(result == "success"){
					alert("跟进成功！");
					window.location.reload();
				}else{
					alert("跟进失败！");
				}
			}
		});
	});
	
	$("#mainTable tbody").on("click","button[name='btn_move']",function(e){
		var td = $(this).parent();
		var tr = $(td).parent();
		var tds = $(tr).children("td");
		$("#move_stuNameTd").text($(tds[0]).text());
		$("#move_parentNameTd").text($(tds[1]).text());
		$("#move_contactTel1Td").text($(tds[2]).text());
		$("#move_contactTel2Td").text($(tds[3]).text());
		$("#move_needClsTd").text($(tds[4]).text());
		$("#move_channelNameTd").text($(tds[5]).text());
		$("#move_channelTypeTd").text($(tds[6]).text());
		$("#move_createDateTd").text($(tds[7]).text());
		$("#move_isValidTd").text("有效");
		$("#move_noValidReason").text("无");
		$("#move_isAssign").text("未分配");
		$("#move_assignEmployee").text("无");
		
		$("#moveOppModal").modal({
			keyboard : true
		});
	});
	
	$("#btn_submitMove").click(function(){
		$.ajax({
			url : "moveManagement.action",
			type : "post",
			dataType : "json",
			data : {
				management : $("#move_management").val(),
				contactTel1 : $("#move_contactTel1Td").text(),
				contactTel2 : $("#move_contactTel2Td").text()
			},
			success : function(data){
				var info = eval("(" + data + ")");
				var result = info.result;
				if(result == "success"){
					alert("流转成功！");
					window.location.reload();
				}else{
					alert("流转失败！");
				}
			},
			error : function(data){
				alert("网络错误！流转失败！");
			}
		});
	});
	
	$("#mainTable tbody").on("click","button[name='btn_martToDeal']",function(e){
		var td = $(this).parent();
		var tr = $(td).parent();
		var tds = $(tr).children("td");
		
		$.ajax({
			url : "markToDeal.action",
			type : "post",
			data : {
					contactTel1 : $(tds[2]).text(),
					contactTel2 : $(tds[3]).text()
					},
			dataType : "json",
			success : function(e){
				var data = eval("(" + e + ")");
				alert(data.markResult);
				if(data.markResult == "success"){
					alert("成功！");
				}else{
					alert("失败：" + data.failReason);
				}
				window.location.reload();
			},
			error : function(e){
				alert("发生错误！");
				window.location.reload();
			}	
		})
	});
	
	$("#mainTable tbody").on("click","button[name='btn_mark']",function(e){
		var td = $(this).parent();
		var tr = $(td).parent();
		var tds = $(tr).children("td");
		$("#mark_stuNameTd").text($(tds[0]).text());
		$("#mark_parentNameTd").text($(tds[1]).text());
		$("#mark_contactTel1Td").text($(tds[2]).text());
		$("#mark_contactTel2Td").text($(tds[3]).text());
		$("#mark_needClsTd").text($(tds[4]).text());
		$("#mark_managementTd").text($(tds[5]).text());
		$("#mark_channelNameTd").text($(tds[6]).text());
		$("#mark_channelTypeTd").text($(tds[7]).text());
		$("#mark_createDateTd").text($(tds[8]).text());
		$("#mark_isValidTd").text("无效");
		$("#mark_isAssign").text("未分配");
		$("#mark_assignEmployee").text("无");
		
		$("#markOppModal").modal({
			keyboard : true
		});
	});
	
	$("#btn_submitMark").click(function(){
		$.ajax({
			url : "markToInvalid.action",
			type : "post",
			dataType : "json",
			data : {
						contactTel1 : $("#mark_contactTel1Td").text(),
						contactTel2 : $("#mark_contactTel2Td").text(),
						invalidReason : $("#inValidReason").val()
					},
		    success : function(result){
		    	var data = eval("(" + result + ")");
		    	if(data.result == "success"){
		    		alert("标记成功！");
		    		window.location.reload();
		    	}else{
		    		alert("标记失败！");
		    	}
		    },
		    error : function(){
		    	alert("网络出错！请联系管理员！");
		    }
		})
	});
});

