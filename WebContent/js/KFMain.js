/**
 * by phil guan 
 */

var stuName,parentName,contactTel1,contactTel2,needCls,management,channel,channelType,createDate;


$(document).ready(function(){
	var table = $("#mainTable").DataTable({
		"scrollX": true
	});
	var t = $("#queryDataTable").DataTable();
	var dealTable = $("#dealDataTable").DataTable();
	
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
	
	$("#mainTable tbody").on("click","button[name='btn_assign']",function(e){
		var td = $(this).parent();
		var tr = $(td).parent();
		var tds = $(tr).children("td");
		$("#stuNameTd").text($(tds[0]).text());
		$("#parentNameTd").text($(tds[1]).text());
		$("#contactTel1Td").text($(tds[2]).text());
		$("#contactTel2Td").text($(tds[3]).text());
		$("#needClsTd").text($(tds[4]).text());
		$("#managementTd").text($(tds[5]).text());
		$("#channelNameTd").text($(tds[6]).text());
		$("#channelTypeTd").text($(tds[7]).text());
		$("#createDateTd").text($(tds[8]).text());
		$("#isValidTd").text("有效");
		$("#noValidReason").text("无");
		$("#isAssign").text("未分配");
		
		$("#assignOppModal").modal({
			keyboard : true
		});
	});
	
	$("#btn_submitAssign").click(function(){
		$.ajax({
			url : "assignEmployee.action",
			type : "post",
			data : {
						contactTel1 : $("#contactTel1Td").text(),
						contactTel2 : $("#contactTel2Td").text(),
						employee : $("#assignEmployeeSelect").val()
					},
			dataType : "json",
			success : function(data){
				var returnData = eval("(" + data + ")");
				var result = returnData.result;
				if(result == "success"){
					alert("分配成功！");
					window.location.reload();
				}else{
					alert("分配失败！");
				}
			},
			error : function(){
				
			},
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
	
	
	//show the statistics view
	/*$("#tjByChannel").click(function(){
		$("#tjByChannel").css("background-color","blue");
		$("#addKFUser").css("background-color","#808080");
		$("#queryData").css("background-color","#808080");
		$("#sjInput").css("background-color","#808080");
		$("#queryDealData").css("background-color","#808080");
		$("#importOpp").css("background-color","#808080");
		
		$("#tjByChannelDiv").css("display","block");
		$("#mainBox").css("display","none");
		$("#addKFUserDiv").css("display","none");
		$("#queryDataDiv").css("display","none");
		$("#queryDealDataDiv").css("display","none");
		$("#importOppDiv").css("display","none");
		
		$("#tjByChannelTBody").html("");
		$.ajax({
			url : "getOppCount.action",
			type : "post",
			dataType : "json",
			success : function(e){
				var data = eval("(" + e + ")");
				var dataCollaList = new Array();
				var netCollaList = new Array();
				var marketCollaList = new Array();
				for(var i = 0; i < data.length; i++){
					if(data[i].type == "数据合作"){
						dataCollaList.push(data[i]);
					}else if(data[i].type == "网络合作"){
						netCollaList.push(data[i]);
					}else if(data[i].type == "市场推荐"){
						marketCollaList.push(data[i]);
					}
				}
				
				var table = document.getElementById("tjByChannelTBody");
				
				for(var i = 0; i < dataCollaList.length; i++){
					var tr = document.createElement("tr");
					if(i == 0){
						var td = document.createElement("td");
						td.appendChild(document.createTextNode("数据合作"));
						td.setAttribute("rowspan", dataCollaList.length);
						tr.appendChild(td);
					}
					var td1 = document.createElement("td");
					td1.appendChild(document.createTextNode(dataCollaList[i].name))
					var td2 = document.createElement("td");
					td2.appendChild(document.createTextNode(dataCollaList[i].curtWeekCount));
					var td3 = document.createElement("td");
					td3.appendChild(document.createTextNode(dataCollaList[i].lastWeekCount));
					var td4 = document.createElement("td");
					td4.appendChild(document.createTextNode(dataCollaList[i].weekRisePercent));
					if(Number(dataCollaList[i].weekRisePercent.split("%")[0]) < 0){
						td4.style.color = "red";
					}
					var td5 = document.createElement("td");
					td5.appendChild(document.createTextNode(dataCollaList[i].curtMonthCount));
					var td6 = document.createElement("td");
					td6.appendChild(document.createTextNode(dataCollaList[i].lastMonthCount));
					var td7 = document.createElement("td");
					td7.appendChild(document.createTextNode(dataCollaList[i].curtQuarter));
					
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					tr.appendChild(td5);
					tr.appendChild(td6);
					tr.appendChild(td7);
					
					table.appendChild(tr);
				}
				
				for(var i = 0; i < netCollaList.length; i++){
					var tr = document.createElement("tr");
					if(i == 0){
						var td = document.createElement("td");
						td.appendChild(document.createTextNode("网络合作"));
						td.setAttribute("rowspan", netCollaList.length);
						tr.appendChild(td);
					}
					var td1 = document.createElement("td");
					td1.appendChild(document.createTextNode(netCollaList[i].name))
					var td2 = document.createElement("td");
					td2.appendChild(document.createTextNode(netCollaList[i].curtWeekCount));
					var td3 = document.createElement("td");
					td3.appendChild(document.createTextNode(netCollaList[i].lastWeekCount));
					var td4 = document.createElement("td");
					td4.appendChild(document.createTextNode(netCollaList[i].weekRisePercent));
					if(Number(netCollaList[i].weekRisePercent.split("%")[0]) < 0){
						td4.style.color = "red";
					}
					var td5 = document.createElement("td");
					td5.appendChild(document.createTextNode(netCollaList[i].curtMonthCount));
					var td6 = document.createElement("td");
					td6.appendChild(document.createTextNode(netCollaList[i].lastMonthCount));
					var td7 = document.createElement("td");
					td7.appendChild(document.createTextNode(netCollaList[i].curtQuarter));
					
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					tr.appendChild(td5);
					tr.appendChild(td6);
					tr.appendChild(td7);
					
					table.appendChild(tr);
				}
				
				for (var i = 0; i < marketCollaList.length; i++) {
					var tr = document.createElement("tr");
					if(i == 0){
						var td = document.createElement("td");
						td.appendChild(document.createTextNode("市场推荐"));
						td.setAttribute("rowspan", marketCollaList.length);
						tr.appendChild(td);
					}
					var td1 = document.createElement("td");
					td1.appendChild(document.createTextNode(marketCollaList[i].name))
					var td2 = document.createElement("td");
					td2.appendChild(document.createTextNode(marketCollaList[i].curtWeekCount));
					var td3 = document.createElement("td");
					td3.appendChild(document.createTextNode(marketCollaList[i].lastWeekCount));
					var td4 = document.createElement("td");
					td4.appendChild(document.createTextNode(marketCollaList[i].weekRisePercent));
					if(Number(marketCollaList[i].weekRisePercent.split("%")[0]) < 0){
						td4.style.color = "red";
					}
					var td5 = document.createElement("td");
					td5.appendChild(document.createTextNode(marketCollaList[i].curtMonthCount));
					var td6 = document.createElement("td");
					td6.appendChild(document.createTextNode(marketCollaList[i].lastMonthCount));
					var td7 = document.createElement("td");
					td7.appendChild(document.createTextNode(marketCollaList[i].curtQuarter));
					
					tr.appendChild(td1);
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
					tr.appendChild(td5);
					tr.appendChild(td6);
					tr.appendChild(td7);
					
					table.appendChild(tr);
				}
				
				var totalTr = document.createElement("tr");
				var tTd1 = document.createElement("td");
				tTd1.appendChild(document.createTextNode("合计"));
				tTd1.setAttribute("colspan","2");
				var curtWeekTotal = 0;
				var lastWeekTotal = 0;
				var weekRisePerTotal = "";
				var curtMonthTotal = 0;
				var lastMonthTotal = 0;
				var curtQuarterTotal = 0;
				for (var i = 0; i < data.length; i++) {
					curtWeekTotal += Number(data[i].curtWeekCount);
					lastWeekTotal += Number(data[i].lastWeekCount);
					curtMonthTotal += Number(data[i].curtMonthCount);
					lastMonthTotal += Number(data[i].lastMonthCount);
					curtQuarterTotal += Number(data[i].curtQuarter);
				}
				weekRisePerTotal = (((curtWeekTotal - lastWeekTotal) / lastWeekTotal).toFixed(2) * 100).toString() + "%";
				var tTd2 = document.createElement("td");
				tTd2.appendChild(document.createTextNode(curtWeekTotal));
				var tTd3 = document.createElement("td");
				tTd3.appendChild(document.createTextNode(lastWeekTotal));
				var tTd4 = document.createElement("td");
				tTd4.appendChild(document.createTextNode(weekRisePerTotal));
				if((curtWeekTotal - lastWeekTotal) < 0){
					tTd4.style.color = "red";
				}
				var tTd5 = document.createElement("td");
				tTd5.appendChild(document.createTextNode(curtMonthTotal));
				var tTd6 = document.createElement("td");
				tTd6.appendChild(document.createTextNode(lastMonthTotal));
				var tTd7 = document.createElement("td");
				tTd7.appendChild(document.createTextNode(curtQuarterTotal));
				
				totalTr.appendChild(tTd1);
				totalTr.appendChild(tTd2);
				totalTr.appendChild(tTd3);
				totalTr.appendChild(tTd4);
				totalTr.appendChild(tTd5);
				totalTr.appendChild(tTd6);
				totalTr.appendChild(tTd7);
				
				table.appendChild(totalTr);
			},
			error : function(e){
				alert("网络出错！请检查网络！");
			}
		})
	});*/
	
	//show the unhandle's opportunities
	/*$("#sjInput").click(function(){
		$("#mainBox").css("display","block");
		$("#addKFUserDiv").css("display","none");
		$("#queryDataDiv").css("display","none");
		$("#tjByChannelDiv").css("display","none");
		$("#queryDealDataDiv").css("display","none");
		$("#importOppDiv").css("display","none");
		
		$("#addKFUser").css("background-color","#808080");
		$("#queryData").css("background-color","#808080");
		$("#tjByChannel").css("background-color","#808080");
		$("#queryDealData").css("background-color","#808080");
		$("#importOpp").css("background-color","#808080");
		$("#sjInput").css("background-color","blue");
	});*/
	
	/*//show the query opportunity view
	$("#queryData").click(function(){
		$("#mainBox").css("display","none");
		$("#addKFUserDiv").css("display","none");
		$("#tjByChannelDiv").css("display","none");
		$("#queryDealDataDiv").css("display","none");
		$("#queryDataDiv").css("display","block");
		$("#importOppDiv").css("display","none");
		
		$("#addKFUser").css("background-color","#808080");
		$("#sjInput").css("background-color","#808080");
		$("#tjByChannel").css("background-color","#808080");
		$("#queryDealData").css("background-color","#808080");
		$("#importOpp").css("background-color","#808080");
		$("#queryData").css("background-color","blue");
		
		//获取当月商机数据
		t.clear().draw(false);
		$.ajax({
			url : "getCurrentMonthOpp.action",
			type : "post",
			dataType : "json",
			success : function(data){
				var result = eval("(" + data + ")");
				for(var i = 0; i < result.length; i++){
					var obj = [
					           result[i].stuName,result[i].parentName,result[i].contactTel1,result[i].contactTel2,
					           result[i].needCls,result[i].management,result[i].channelName,result[i].channelType,
					           result[i].createDate,result[i].isValid,result[i].noValidReason,result[i].isAssign,
					           result[i].assignEmployee
					          ];
					
					t.row.add(obj).draw(false);
				}
				
				$("#queryDataTable").css("text-align","center");
			},
			error : function(){
				alert("Net Error!");
			}
		});
	});*/
	
	//提交添加客服用户的请求
	/*$("#submitAddKFUser").click(function(){
		var username = $("#kfUsername").val();
		var password = $("#kfPassword").val();
		var confirmPassword = $("#kfConfirmPassword").val();
		var name = $("#kfName").val();
		var management = $("#kfManagement").val();
		if(username == null || username == ""){
			alert("用户名不能为空！");
			return;
		}
		if(password == null || password == ""){
			alert("密码不能为空！");
			return;
		}
		if(confirmPassword == null || confirmPassword == ""){
			alert("确认密码不能为空！");
			return;
		}
		
		$.ajax({
			url : "addKFEmployeeUser.action",
			type : "post",
			data : {"username":username,"password":password,"name":name,"management":management},
			dataType : "json",
			success : function(e){
				var data = eval("(" + e + ")");
				var result = data.result;
				if(result == "success"){
					alert("添加成功！");
					window.location.reload();
				}else{
					alert("添加失败！");
				}
			},
			error : function(e){
				alert("添加失败！");
			}
		})
	});*/
	
	/*$("#kfUsername").blur(function(){
		$("#isExistP").text("");
		var username = $("#kfUsername").val();
		if(username == null || username == ""){
			$("#isExistP").append("用户名不能为空！");
			$("#isExistP").css("background-color","red");
			return;
		}
		
		$("#isExistP").css("background-color","white");
		$.ajax({
			url : "userNameIsExist.action",
			type : "post",
			data : {"username":username},
			dataType : "json",
			success : function(e){
				var data = eval("(" + e + ")");
				var result = data.result;
				if(result == "isExist"){
					$("#isExistP").append("用户名已存在！");
					$("#isExistP").css("background-color","red");
				}else if(result == "notExist"){
					$("#isExistP").append("用户名可用！");
				}
			},
			error : function(e){
				
			}
		});
	});*/
	
	/*$("#kfConfirmPassword").blur(function(){
		var psw = $("#kfPassword").val();
		var confirmPsw = $("#kfConfirmPassword").val();
		$("#confirmPswP").text("");
		$("#confirmPswP").css("background-color","white");
		if(psw != confirmPsw){
			$("#confirmPswP").append("两次密码输入不一致！");
			$("#confirmPswP").css("background-color","red");
		}
	});*/
	
	/*$("#searchButton").click(function(){
		var beginDate = $("#beginDate").val();
		var endDate = $("#endDate").val();
		
		$.ajax({
			url : "searchOpp.action",
			type : "post",
			data : {"beginDate":beginDate,"endDate":endDate},
			success : function(data){
				t.clear().draw(false);
				var result = eval("(" + data + ")");
				for(var i = 0; i < result.length; i++){
					var obj = [
					           	result[i].name,result[i].parentName,result[i].contactTel1,
					           	result[i].contactTel2,result[i].needCls,result[i].management,
					           	result[i].channelName,result[i].channelType,result[i].createDate,
					           	result[i].isValid,result[i].noValidReason,result[i].isAssign,
					           	result[i].assignEmployee
					           ];
					t.row.add(obj).draw(false);
				}
			},
			error : function(data){
				alert("查询出错！ 请检查网络！");
			}
		})
	});*/
	
	/*$("#exportExcel").click(function(){
		var beginDate = $("#beginDate").val();
		var endDate = $("#endDate").val();
		if(beginDate == null || beginDate == ""){
			alert("请在导出excel前先指定商机起始日期！");
			return;
		}
		if(endDate == null || endDate == ""){
			alert("请在导出excel前先指定商机截止日期！");
			return;
		}
		var param = "beginDate=" + beginDate + "&endDate=" + endDate;
		location.href = "exportExcel.action?" + param;
	});*/
	
	/*$("#btn_mark").click(function(){
		$("#markToInValidDiv").css("display","block");
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
	});*/
	
	
	
	
	
	/*$("#btn_martToDeal").click(function(){
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
	});*/
	
	/*$("#dealExportExcel").click(function(){
		var userName = $("#curtUser").text().split(":")[1];
		var begin = $("#dealBeginDate").val();
		var end = $("#dealEndDate").val();
		var contactTel = $("#dealContactTel").val();
		window.location.href = "exportDealInfoByUser.action?userName=" + userName + "&begin=" + begin + "&end=" + end + "&contactTel=" + contactTel;
	});*/
	
	/*$("#importOpp").click(function(){
		$("#mainBox").css("display","none");
		$("#addKFUserDiv").css("display","none");
		$("#tjByChannelDiv").css("display","none");
		$("#queryDataDiv").css("display","none");
		$("#queryDealDataDiv").css("display","none");
		$("#importOppDiv").css("display","block");
		
		$("#addKFUser").css("background-color","#808080");
		$("#sjInput").css("background-color","#808080");
		$("#tjByChannel").css("background-color","#808080");
		$("#queryData").css("background-color","#808080");
		$("#queryDealData").css("background-color","#808080");
		$("#importOpp").css("background-color","blue");
	});*/
	
	/*$("#file_upload").uploadify({
		'swf' : 'uploadify/uploadify.swf',  
        'uploader' : 'importExcelOpp.action',  
        'cancelImg'      : 'img/uploadify-cancel.png',  
        'folder'         : 'UploadFiles',  
        'queueID'        : 'some_file_queue',  
        'auto'           : false,  
        'multi'          : true,  
        'simUploadLimit' : 2,
        'fileObjName' : 'file_upload',
        'buttonText' : '选择数据文件',
        'onUploadStart' : function(file) {
            $("#file_upload").uploadify('settings','formData',{'fileName':file.name});
        }
    });
	
	$("#importExcel").click(function(){
		$("#file_upload").uploadify("upload");
	});*/
});

