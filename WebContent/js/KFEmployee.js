/**
 * by phil	
 */
var stuName,parentName,contactTel1,contactTel2,needCls,management,channel,channelType,createDate;

$(document).ready(function(){
	var table = $("#mainTable").DataTable({});

	$("#mainTable tbody").on("click","tr",function(e){
		var data = table.row( this ).data();
		var x = e.pageX;
        var y = e.pageY;
        $("#operation_div").css("display","block");
        $("#operation_div").css({'left':x + 'px','top':y + 'px'});
        stuName = data[0];
        parentName = data[1];
        contactTel1 = data[2];
        contactTel2 = data[3];
        needCls = data[4];
        management = data[5];
        channel = data[6];
        channelType = data[7];
        createDate = data[8];
	});
	
	$("#btn_move").click(function(){
		$("#moveDiv").css("display","block");
		$("#dataShowDiv").css("display","none");
		$("#operation_div").css("display","none");
		$("#move_stuNameTd").text(stuName);
		$("#move_parentNameTd").text(parentName);
		$("#move_contactTel1Td").text(contactTel1);
		$("#move_contactTel2Td").text(contactTel2);
		$("#move_needClsTd").text(needCls);
		$("#move_channelNameTd").text(channel);
		$("#move_channelTypeTd").text(channelType);
		$("#move_createDateTd").text(createDate);
		$("#move_isValidTd").text("有效");
		$("#move_noValidReason").text("无");
		$("#move_isAssign").text("未分配");
		$("#move_assignEmployee").text("无");
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
				alert("流转失败！");
			}
		});
	});
	
	$("#btn_handle").click(function(){
		$("#handleDiv").css("display","block");
		$("#dataShowDiv").css("display","none");
		$("#operation_div").css("display","none");
		
		$("#handle_stuNameTd").text(stuName);
		$("#handle_parentNameTd").text(parentName);
		$("#handle_contactTel1Td").text(contactTel1);
		$("#handle_contactTel2Td").text(contactTel2);
		$("#handle_needClsTd").text(needCls);
		$("#handle_managementTd").text(management);
		$("#handle_channelNameTd").text(channel);
		$("#handle_channelTypeTd").text(channelType);
		$("#handle_createDateTd").text(createDate);
		$("#handle_isValidTd").text("有效");
		$("#handle_noValidReason").text("无");
		$("#handle_isAssign").text("未分配");
		$("#handle_assignEmployee").text("无");
		
		$.ajax({
			url : "getFollowContentKFEmployee.action",
			type : "post",
			dataType : "json",
			data : {"contactTel1":contactTel1,"contactTel2":contactTel2},
			success : function(e){
				var data = eval("(" + e + ")");
				var table = document.getElementById("kfEmployeeFConTable");
				for(var i = 0; i < data.length; i++){
					var tr = document.createElement("tr");
					var tdTime = document.createElement("td");
					tdTime.appendChild(document.createTextNode(data[i].followTime));
					var tdContent = document.createElement("td");
					tdContent.appendChild(document.createTextNode(data[i].followContent));
					var tdEmployee = document.createElement("td");
					tdEmployee.appendChild(document.createTextNode(data[i].followEmployee));
					tr.appendChild(tdTime);
					tr.appendChild(tdContent);
					tr.appendChild(tdEmployee);
					table.appendChild(tr);
				}
			},
			error : function(e){
				//
			}
		})
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
				followEmployee : $("#curtUser").text().split(":")[1]
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
	
	$("#btn_back1").click(function(){
		$("#moveDiv").css("display","none");
		$("#dataShowDiv").css("display","block");
	});
	$("#btn_back2").click(function(){
		$("#handleDiv").css("display","none");
		$("#dataShowDiv").css("display","block");
		$("#kfEmployeeFConTable tr:not(:first)").html("");
	});
	$("#btn_back3").click(function(){
		$("#markInvalidDiv").css("display","none");
		$("#dataShowDiv").css("display","block");
	});
	$("#btn_back4").click(function(){
		$("#dealDiv").css("display","none");
		$("#dataShowDiv").css("display","block");
	});
	
	$("#btn_mark").click(function(){
		$("#markInvalidDiv").css("display","block");
		$("#dataShowDiv").css("display","none");
		$("#operation_div").css("display","none");
		
		$("#mark_stuNameTd").text(stuName);
		$("#mark_parentNameTd").text(parentName);
		$("#mark_contactTel1Td").text(contactTel1);
		$("#mark_contactTel2Td").text(contactTel2);
		$("#mark_needClsTd").text(needCls);
		$("#mark_managementTd").text(management);
		$("#mark_channelNameTd").text(channel);
		$("#mark_channelTypeTd").text(channelType);
		$("#mark_createDateTd").text(createDate);
		$("#mark_isValidTd").text("无效");
		$("#mark_isAssign").text("未分配");
		$("#mark_assignEmployee").text("无");
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
		    	}else{
		    		alert("标记失败！");
		    	}
		    	window.location.reload();
		    },
		    error : function(){
		    	alert("网络出错！请联系管理员！");
		    }
		});
	});
	
	$("#btn_cancle_operation").click(function(){
		$("#operation_div").css("display","none");
	});
	
	$("#btn_markDeal").click(function(){
		$("#dataShowDiv").css("display","none");
		$("#dealDiv").css("display","block");
		$("#operation_div").css("display","none");
		
		$("#deal_stuNameTd").text(stuName);
		$("#deal_parentNameTd").text(parentName);
		$("#deal_contactTel1Td").text(contactTel1);
		$("#deal_contactTel2Td").text(contactTel2);
		$("#deal_needClsTd").text(needCls);
		$("#deal_managementTd").text(management);
		$("#deal_channelNameTd").text(channel);
		$("#deal_channelTypeTd").text(channelType);
		$("#deal_createDateTd").text(createDate);
		$("#deal_isValidTd").text("无效");
		$("#deal_isAssign").text("未分配");
		$("#deal_assignEmployee").text("无");
	});
	
	$("#btn_submitDeal").click(function(){
		$.ajax({
			url : "markToDeal.action",
			type : "post",
			data : {
					contactTel1 : $("#deal_contactTel1Td").text(),
					contactTel2 : $("#deal_contactTel2Td").text()
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
		});
	});
});

