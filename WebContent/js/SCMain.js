/**
 * by Phil
 */

$(document).ready(function(){
	/*var table = $("#dealTable").DataTable({});
	
	$("#addChannelUser").click(function(){
		$("#tjByChannelDiv").css("display","none");
		$("#addChannelUserDiv").css("display","block");
		$("#dealInformationDiv").css("display","none");
		$("#customRebateDiv").css("display","none");
		
		$("#tjByChannel").css("background-color","#808080");
		$("#addChannelUser").css("background-color","blue");
		$("#dealInformation").css("background-color","#808080");
		$("#customRebate").css("background-color","#808080");
	});
	
	$("#tjByChannel").click(function(){
		$("#tjByChannelDiv").css("display","block");
		$("#addChannelUserDiv").css("display","none");
		$("#dealInformationDiv").css("display","none");
		$("#customRebateDiv").css("display","none");
		
		$("#tjByChannel").css("background-color","blue");
		$("#addChannelUser").css("background-color","#808080");
		$("#dealInformation").css("background-color","#808080");
		$("#customRebate").css("background-color","#808080");
	});
	
	$("#submitAdd").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		var channelName = $("#channelName").val();
		
		if(username == "" || username == null){
			alert("请输入用户名！");
			return;
		}
		if(password == "" || password == null){
			alert("请输入密码！");
			return;
		}
		if(confirmPassword == "" || confirmPassword == null){
			alert("请输入确认密码！");
			return;
		}
		if(channelName == "" || channelName == null){
			alert("请输入渠道商名！");
			return;
		}
		if(password != confirmPassword){
			alert("密码两次输入不一致！");
			return;
		}
		
		$.ajax({
			url : "addChannelUser.action",
			type : "post",
			data : $("#addChannelUserForm").serialize(),
			success : function(e){
				var result = eval("(" + e + ")");
				var addResult = result.result;
				if(addResult == "success"){
					alert("添加成功！");
				}else{
					alert("添加失败！");
				}
			},
			error : function(e){
				alert("添加失败！");
			}
		});
	});*/
	
	/*$("#dealInformation").click(function(){
		$("#tjByChannelDiv").css("display","none");
		$("#addChannelUserDiv").css("display","none");
		$("#dealInformationDiv").css("display","block");
		$("#customRebateDiv").css("display","none");
		
		$("#dealInformation").css("background-color","blue");
		$("#tjByChannel").css("background-color","#808080");
		$("#addChannelUser").css("background-color","#808080");
		$("#customRebate").css("background-color","#808080");
		
		//默认显示当月成单数据
		$.ajax({
			url : 'getCurtMonDealInfo.action',
			type : 'post',
			dataType : 'json',
			success : function(e){
				var data = eval("(" + e + ")");
				table.clear().draw(false);
				for (var i = 0; i < data.length; i++) {
					var rebatePer = data[i].rebate*1*100 + "%";
					var obj = [
					           data[i].stuName,data[i].clsName,data[i].pay,data[i].channelName,
					           data[i].management,rebatePer,data[i].commission
					           ];
					table.row.add(obj).draw(false);
				}
			},
			error : function(e){
				alert("查询当月成单数据失败：");
			}
		});
	});*/
	
	/*$("#searchButton").click(function(){
		$.ajax({
			url : 'searchDeal.action',
			type : 'post',
			data : {	
						'beginDate' : $("#beginDate").val(),
						'endDate' : $("#endDate").val(), 
						'channelName' : $("#channel option:selected").text(), 
						'stuContactTel' : $("#stuContactTel").val()
					},
			dataType : 'json',
			success : function(e){
				table.clear().draw(false);
				var data = eval("(" + e + ")");
				for (var i = 0; i < data.length; i++) {
					var rebatePer = data[i].rebate*1*100 + "%";
					var obj = [
					           data[i].stuName,data[i].clsName,data[i].pay,data[i].channelName,
					           data[i].management,rebatePer,data[i].commission
					           ];
					table.row.add(obj).draw(false);
				}
			},
			error : function(e){
				alert("按条件查询成单数据失败！");
			}
		})
	});*/
	
	/*$("#exportExcel").click(function(){
		var channel = $("#channel option:selected").text();
		channel = encodeURI(encodeURI(channel));
		var param = "begin=" + $("#beginDate").val() + "&end=" + $("#endDate").val() + "&channel=" + channel + "&stuContactTel=" + $("#stuContactTel").val();
		location.href = "exportDealInfo.action?" + param;
	});*/
	
	$("#customRebate").click(function(){
		$("#customRebateDiv").css("display","block");
		$("#tjByChannelDiv").css("display","none");
		$("#addChannelUserDiv").css("display","none");
		$("#dealInformationDiv").css("display","none");
		
		$("#customRebate").css("background-color","blue");
		$("#tjByChannel").css("background-color","#808080");
		$("#addChannelUser").css("background-color","#808080");
		$("#dealInformation").css("background-color","#808080");
		
		//默认显示当前一个月的商机，以供市场人员修改返点比例
		
		$.ajax({
			url : 'getCurtMonDealInfo.action',
			type : 'post',
			dataType : 'json',
			success : function(e){
				var data = eval("(" + e + ")");
				$("#customRebateTable tbody").html("");
				for (var i = 0; i < data.length; i++) {
					var rebatePer = data[i].rebate*1*100 + "%";
					var tr = $("#customRebateTable tbody").append("<tr></tr>");
					tr.append("<td>" + data[i].stuName + "</td>");
					tr.append("<td>" + data[i].clsName + "</td>");
					tr.append("<td>" + data[i].pay + "</td>");
					tr.append("<td>" + data[i].channelName + "</td>");
					tr.append("<td>" + data[i].management + "</td>");
					tr.append("<td><input class=\"customModify\" id=\"" + data[i].id + "\" type=\"text\" value=\"" + rebatePer + "\"/></td>");
					tr.append("<td><input class=\"customModify\" type=\"text\" value=\"" + data[i].commission + "\"/></td>");
				}
			}
		});
	});
	
	$("#customSearchButton").click(function(){
		$.ajax({
			url : 'searchDeal.action',
			type : 'post',
			data : {	
						'beginDate' : $("#customBeginDate").val(),
						'endDate' : $("#customEndDate").val(), 
						'channelName' : $("#customChannel option:selected").text(), 
						'stuContactTel' : $("#customStuContactTel").val()
					},
			dataType : 'json',
			success : function(e){
				$("#customRebateTable tbody").html("");
				var data = eval("(" + e + ")");
				for (var i = 0; i < data.length; i++) {
					var rebatePer = data[i].rebate*1*100 + "%";
					var tr = $("#customRebateTable tbody").append("<tr></tr>");
					tr.append("<td>" + data[i].stuName + "</td>");
					tr.append("<td>" + data[i].clsName + "</td>");
					tr.append("<td>" + data[i].pay + "</td>");
					tr.append("<td>" + data[i].channelName + "</td>");
					tr.append("<td>" + data[i].management + "</td>");
					tr.append("<td><input id=\"" + data[i].id + "\" type=\"text\" value=\"" + rebatePer + "\"/></td>");
					tr.append("<td><input id=\"\" type=\"text\" value=\"" + data[i].commission + "\"/></td>");
				}
			},
			error : function(e){
				alert("按条件查询成单数据失败！");
			}
		})
	});
});




