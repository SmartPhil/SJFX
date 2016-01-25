<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上海新东方商机系统</title>
<base href="<%=basePath%>">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>

<script type="text/javascript">
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
});

$(document).ready(function(){
	$("#exportTJ").click(function(){
		window.location.href = "exportTjData.action";
	});
});
</script>
</head>
<body>
<% 
	HttpSession sessions = request.getSession();
	Object username = sessions.getAttribute("username");
%>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">
        		<img alt="" src="image/logo.png">
      		</a>
		</div>
		<div class="collapse navbar-collapse" style="margin-left: auto;margin-right: auto;width: 70%;">
			<ul class="nav nav-pills">
				<li role="presentation"><a id="usernameShow" href="#">欢迎您:<%=username %></a></li>
  				<li role="presentation"><a href="toKF.action">未处理商机</a></li>
  				<li role="presentation" class="active"><a href="#">数据量统计</a></li>
  				<li role="presentation"><a href="KF/adduser.jsp">添加用户</a></li>
  				<li role="presentation"><a href="KF/queryOpp.jsp">查询数据</a></li>
  				<li role="presentation"><a href="#">成单数据</a></li>
  				<li role="presentation"><a href="#">导入商机</a></li>
			</ul>
		</div>
	</div>
</nav>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">商机统计</div>
	
	<table id="tjByChannelTable" class="table" border="1">
		<thead>
			<tr>
				<th rowspan="2">结算模式</th>
				<th rowspan="2">咨询渠道</th>
				<th colspan="3">当周数据</th>
				<th colspan="3">累计数据</th>
			</tr>
			<tr>
				<th>本周</th>
				<th>上周</th>
				<th>增幅%</th>
				<th>本月</th>
				<th>上月</th>
				<th>本季</th>
			</tr>
		</thead>
		<tbody id="tjByChannelTBody">
			
		</tbody>
	</table>
	<div class="panel-footer">
		<button id="exportTJ" class="btn btn-primary">导出</button>
	</div>
</div>
</body>
</html>