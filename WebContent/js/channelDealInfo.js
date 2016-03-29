/**
 * by phil 
 */

$(document).ready(function(){
	//初始化表格
	var table = $("#oppInfoTab").DataTable({});
	
	//初始化页面：
	$.ajax({
		url : 'getOppDealInfoByChannel.action',
		type : 'post',
		dataType : 'json',
		data : {'username' : $("#usernameShow").html().split(":")[1],'isCurtMonFlag' : 'true'},
		success : function(e){
			var basicData = eval("(" + e + ")");
			var result = basicData.result;
			if(result == "success"){
				table.clear().draw(false);
				var oppInfo = eval("(" + basicData.oppInfo + ")");
				for (var i = 0; i < oppInfo.length; i++) {
					var obj = [
					           oppInfo[i].createTime, oppInfo[i].stuName, oppInfo[i].parentName, oppInfo[i].contactTel1, oppInfo[i].contactTel2,
					           oppInfo[i].needCls, oppInfo[i].management, oppInfo[i].isValid, oppInfo[i].noValidReason, oppInfo[i].state
					           ]
					table.row.add(obj).draw(false);
				}
			}else {
				//go to error page
			}
		},
		error : function(e){
			alert('失败！');
		}
	});
	
	$("#searchOppByChannel").click(function(){
		var $btn = $(this).button('loading');
		
		$.ajax({
			url : 'getOppDealInfoByChannel.action',
			type : 'post',
			dataType : 'json',
			data : {
					'username' : $("#usernameShow").html().split(":")[1],
					'isCurtMonFlag' : 'false',
					'begin' : $("#startDate").val(),
					'end' : $("#endDate").val(),
					'stuContactTel' : $("#stuContactTel").val(),
					},
			success : function(e){
				var basicData = eval("(" + e + ")");
				var result = basicData.result;
				if(result == "success"){
					table.clear().draw(false);
					var oppInfo = eval("(" + basicData.oppInfo + ")");
					for (var i = 0; i < oppInfo.length; i++) {
						var obj = [
						           oppInfo[i].createTime, oppInfo[i].stuName, oppInfo[i].parentName, oppInfo[i].contactTel1, oppInfo[i].contactTel2,
						           oppInfo[i].needCls, oppInfo[i].management, oppInfo[i].isValid, oppInfo[i].noValidReason, oppInfo[i].state
						           ]
						table.row.add(obj).draw(false);
					}
				}else {
					//go to error page
				}
				$btn.button('reset');
			},
			error : function(e){
				alert('失败！');
				$btn.button('reset');
			}
		})
	});
	
	$("#exportOppDealInfo").click(function(){
		var begin = $("#startDate").val();
		var end = $("#endDate").val();
		var stuContactTel = $("#stuContactTel").val();
		var username = $("#usernameShow").html().split(":")[1];
		alert(username);
		var param = "begin=" + begin + "&end=" + end + "&stuContactTel=" + stuContactTel + "&username=" + username;
		window.location.href = "exportOppDealInfo.action?" + param;
	});
});
