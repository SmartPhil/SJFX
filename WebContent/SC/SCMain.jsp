<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.xdf.dto.Channel"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商机分销系统</title>
<!-- css文件 -->
<link href="./css/SCMain.css" rel="stylesheet" type="text/css"/>
<link href="./css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="./css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<!-- js文件 -->
<script type="text/javascript" src="./js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="./js/SCMain.js"></script>
<script type="text/javascript" src="./js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="./My97DatePicker/WdatePicker.js"></script>
</head>
<body>

<div id="header">
	<span id="title">商机分销系统</span>
</div>
<br/>
<div id="navigator">
	<div id="userDiv">
		<% 
			HttpSession sessions = request.getSession();
			Object username = sessions.getAttribute("username");
		%>
		<p>当前用户：<%=username %></p>
	</div>
	<div id="barDiv">
		<ul>
			<li><a id="tjByChannel">数据量统计</a></li>
			<li><a id="addChannelUser">添加渠道商</a></li>
			<li><a id="dealInformation">详细成单数据</a></li>
			<li><a id="customRebate">自定义返点</a></li>
		</ul>
	</div>
</div>
<br/>
<% 
	List<Channel> channelList = (List<Channel>) request.getAttribute("channelList");
	List<Channel> dataCollaList = new ArrayList<Channel>();
	List<Channel> netCollaList = new ArrayList<Channel>();
	List<Channel> marketCollaList = new ArrayList<Channel>();
	for(int i = 0; i < channelList.size(); i++){
		if("数据合作".equals(channelList.get(i).getType())){
			dataCollaList.add(channelList.get(i));
		}else if("网络合作".equals(channelList.get(i).getType())){
			netCollaList.add(channelList.get(i));
		}else if("市场推荐".equals(channelList.get(i).getType())){
			marketCollaList.add(channelList.get(i));
		}
	}
%>
<div id="tjByChannelDiv">
	<table class="tjByChannelTable" border="1">
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
			<% 
				int curtWeekTotal = 0;
				int lastWeekTotal = 0;
				int curtMonthTotal = 0;
				int lastMonthTotal = 0;
				double weekRiseTotal = 0;
				int curtCourtator = 0;
				double riseD = 0;
				int riseInt = 0;
				String weekRisePer = "";
			%>
			<%
				if(dataCollaList.size() != 0){
					curtWeekTotal = dataCollaList.get(0).getCurtWeekCount();
					lastWeekTotal = dataCollaList.get(0).getLastWeekCount();
					curtMonthTotal = dataCollaList.get(0).getCurtMonthCount();
					lastMonthTotal = dataCollaList.get(0).getLastMonthCount();
					weekRiseTotal = dataCollaList.get(0).getWeekRisePercent();
					curtCourtator = dataCollaList.get(0).getCurtQuarter();
			%>
			<tr>
				<td rowspan="<%=dataCollaList.size() %>">数据合作</td>
				<td><%=dataCollaList.get(0).getName() %></td>
				<td><%=dataCollaList.get(0).getCurtWeekCount() %></td>
				<td><%=dataCollaList.get(0).getLastWeekCount() %></td>
				<% 
				   riseD = dataCollaList.get(0).getWeekRisePercent();
				   riseInt =  (int)(riseD * 100);
				   weekRisePer = String.valueOf(riseInt) + "%";
				%>
				<% if(riseD >= 0){ %>
				<td><%=weekRisePer %></td>
				<%}else if(riseD < 0){ %>
				<td style="color: red"><%=weekRisePer %></td>
				<%} %>
				<td><%=dataCollaList.get(0).getCurtMonthCount() %></td>
				<td><%=dataCollaList.get(0).getLastMonthCount() %></td>
				<td><%=dataCollaList.get(0).getCurtQuarter() %></td>
			</tr>
			<%}else{
					curtWeekTotal = 0;
					lastWeekTotal = 0;
					curtMonthTotal = 0;
					lastMonthTotal = 0;
					weekRiseTotal = 0;
					curtCourtator = 0;
			}
			%>

			<% for(int i = 1; i < dataCollaList.size(); i++){
				curtWeekTotal += dataCollaList.get(i).getCurtWeekCount();
				lastWeekTotal += dataCollaList.get(i).getLastWeekCount();
				curtMonthTotal += dataCollaList.get(i).getCurtMonthCount();
				lastMonthTotal += dataCollaList.get(i).getLastMonthCount();
				weekRiseTotal += dataCollaList.get(i).getWeekRisePercent();
				curtCourtator += dataCollaList.get(i).getCurtQuarter();
			%>
			<tr>
				<td><%=dataCollaList.get(i).getName() %></td>
				<td><%=dataCollaList.get(i).getCurtWeekCount() %></td>
				<td><%=dataCollaList.get(i).getLastWeekCount() %></td>
				<% 
				   riseD = dataCollaList.get(i).getWeekRisePercent();
				   riseInt =  (int)(riseD * 100);
				   weekRisePer = String.valueOf(riseInt) + "%";
				%>
				<% if(riseD >= 0){ %>
				<td><%=weekRisePer %></td>
				<%}else if(riseD < 0){ %>
				<td style="color: red"><%=weekRisePer %></td>
				<%} %>
				<td><%=dataCollaList.get(i).getCurtMonthCount() %></td>
				<td><%=dataCollaList.get(i).getLastMonthCount() %></td>
				<td><%=dataCollaList.get(i).getCurtQuarter() %></td>
			</tr>
			<%} %>
			
			<%
				if(netCollaList.size() != 0){
					curtWeekTotal += netCollaList.get(0).getCurtWeekCount();
					lastWeekTotal += netCollaList.get(0).getLastWeekCount();
					curtMonthTotal += netCollaList.get(0).getCurtMonthCount();
					lastMonthTotal += netCollaList.get(0).getLastMonthCount();
					weekRiseTotal += netCollaList.get(0).getWeekRisePercent();
					curtCourtator += netCollaList.get(0).getCurtQuarter();
			%>
			<tr>
				<td rowspan="<%=netCollaList.size() %>">网络合作</td>
				<td><%=netCollaList.get(0).getName() %></td>
				<td><%=netCollaList.get(0).getCurtWeekCount() %></td>
				<td><%=netCollaList.get(0).getLastWeekCount() %></td>
				<% 
				   riseD = netCollaList.get(0).getWeekRisePercent();
				   riseInt =  (int)(riseD * 100);
				   weekRisePer = String.valueOf(riseInt) + "%";
				%>
				<% if(riseD >= 0){ %>
				<td><%=weekRisePer %></td>
				<%}else if(riseD < 0){ %>
				<td style="color: red"><%=weekRisePer %></td>
				<%} %>
				<td><%=netCollaList.get(0).getCurtMonthCount() %></td>
				<td><%=netCollaList.get(0).getLastMonthCount() %></td>
				<td><%=netCollaList.get(0).getCurtQuarter() %></td>
			</tr>
			<%}else{
					curtWeekTotal += 0;
					lastWeekTotal += 0;
					curtMonthTotal += 0;
					lastMonthTotal += 0;
					weekRiseTotal += 0;
					curtCourtator += 0;
				}
			%>
			<% 
				for(int i = 1; i < netCollaList.size(); i++){
					curtWeekTotal += netCollaList.get(i).getCurtWeekCount();
					lastWeekTotal += netCollaList.get(i).getLastWeekCount();
					curtMonthTotal += netCollaList.get(i).getCurtMonthCount();
					lastMonthTotal += netCollaList.get(i).getLastMonthCount();
					weekRiseTotal += netCollaList.get(i).getWeekRisePercent();
					curtCourtator += netCollaList.get(i).getCurtQuarter();
			%>
			<tr>
				<td><%=netCollaList.get(i).getName() %></td>
				<td><%=netCollaList.get(i).getCurtWeekCount() %></td>
				<td><%=netCollaList.get(i).getLastWeekCount() %></td>
				<% 
				   riseD = netCollaList.get(i).getWeekRisePercent();
				   riseInt =  (int)(riseD * 100);
				   weekRisePer = String.valueOf(riseInt) + "%";
				%>
				<% if(riseD >= 0){ %>
				<td><%=weekRisePer %></td>
				<%}else if(riseD < 0){ %>
				<td style="color: red"><%=weekRisePer %></td>
				<%} %>
				<td><%=netCollaList.get(i).getCurtMonthCount() %></td>
				<td><%=netCollaList.get(i).getLastMonthCount() %></td>
				<td><%=netCollaList.get(i).getCurtQuarter() %></td>
			</tr>
			<%} %>
			
			<% 
			if(marketCollaList.size() != 0){
				curtWeekTotal += marketCollaList.get(0).getCurtWeekCount();
				lastWeekTotal += marketCollaList.get(0).getLastWeekCount();
				curtMonthTotal += marketCollaList.get(0).getCurtMonthCount();
				lastMonthTotal += marketCollaList.get(0).getLastMonthCount();
				weekRiseTotal += marketCollaList.get(0).getWeekRisePercent();
				curtCourtator += marketCollaList.get(0).getCurtQuarter();
			%>
			<tr>
				<td rowspan="<%=marketCollaList.size() %>">市场推荐</td>
				<td><%=marketCollaList.get(0).getName() %></td>
				<td><%=marketCollaList.get(0).getCurtWeekCount() %></td>
				<td><%=marketCollaList.get(0).getLastWeekCount() %></td>
				<% 
				   riseD = marketCollaList.get(0).getWeekRisePercent();
				   riseInt =  (int)(riseD * 100);
				   weekRisePer = String.valueOf(riseInt) + "%";
				%>
				<% if(riseD >= 0){ %>
				<td><%=weekRisePer %></td>
				<%}else if(riseD < 0){ %>
				<td style="color: red"><%=weekRisePer %></td>
				<%} %>
				<td><%=marketCollaList.get(0).getCurtMonthCount() %></td>
				<td><%=marketCollaList.get(0).getLastMonthCount() %></td>
				<td><%=marketCollaList.get(0).getCurtQuarter() %></td>
			</tr>
			<%}else{ 
				curtWeekTotal += 0;
				lastWeekTotal += 0;
				curtMonthTotal += 0;
				lastMonthTotal += 0;
				weekRiseTotal += 0;
				curtCourtator += 0;
			}
			%>
				
			<% 
				for(int i = 1; i < marketCollaList.size(); i++){ 
					curtWeekTotal += marketCollaList.get(i).getCurtWeekCount();
					lastWeekTotal += marketCollaList.get(i).getLastWeekCount();
					curtMonthTotal += marketCollaList.get(i).getCurtMonthCount();
					lastMonthTotal += marketCollaList.get(i).getLastMonthCount();
					weekRiseTotal += marketCollaList.get(i).getWeekRisePercent();
					curtCourtator += marketCollaList.get(i).getCurtQuarter();
			%>
			<tr>
				<td><%=marketCollaList.get(i).getName() %></td>
				<td><%=marketCollaList.get(i).getCurtWeekCount() %></td>
				<td><%=marketCollaList.get(i).getLastWeekCount() %></td>
				<% 
				   riseD = marketCollaList.get(i).getWeekRisePercent();
				   riseInt =  (int)(riseD * 100);
				   weekRisePer = String.valueOf(riseInt) + "%";
				%>
				<% if(riseD >= 0){ %>
				<td><%=weekRisePer %></td>
				<%}else if(riseD < 0){ %>
				<td style="color: red"><%=weekRisePer %></td>
				<%} %>
				<td><%=marketCollaList.get(i).getCurtMonthCount() %></td>
				<td><%=marketCollaList.get(i).getLastMonthCount() %></td>
				<td><%=marketCollaList.get(i).getCurtQuarter() %></td>
			</tr>
			<%} %>
			<tr>
				<td colspan="2">合计</td>
				<td><%=curtWeekTotal %></td>
				<td><%=lastWeekTotal %></td>
				<%
					String weekRiseTotalString = String.valueOf((int)(weekRiseTotal * 100)) + "%"; 
					if(weekRiseTotal >= 0){ 
				%>
				<td><%=weekRiseTotalString %></td>
				<%}else if(weekRiseTotal < 0){ %>
				<td style="color: red"><%=weekRiseTotalString %></td>
				<%} %>
				<td><%=curtMonthTotal %></td>
				<td><%=lastMonthTotal %></td>
				<td><%=curtCourtator %></td>
			</tr>
	</table>
</div>
<div id="addChannelUserDiv">
	<form id="addChannelUserForm" action="">
	<table>
		<tr>
			<td>用户名：</td>
			<td><input type="text" id="username" name="username"/></td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input type="password" id="password" name="password"/></td>
		</tr>
		<tr>
			<td>确认密码：</td>
			<td><input type="password" id="confirmPassword" name="confirmPassword"/></td>
		</tr>
		<tr>
			<td>渠道商名：</td>
			<td><input type="text" id="channelName" name="channelName"/></td>
		</tr>
		<tr>
			<td>合作类型：</td>
			<td>
				<select name="collaType">
					<option value="1" selected="selected">数据合作</option>
					<option value="2">网络合作</option>
					<option value="3">市场推荐</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><button id="submitAdd">确认添加</button></td>
		</tr>
	</table>
	</form>
</div>
<div id="dealInformationDiv">
	<div id="searchDiv">
		起始时间：<input name="beginDate" type="text" id="beginDate" onclick="WdatePicker()"> &nbsp;&nbsp;&nbsp;&nbsp;
		截止时间：<input name="endDate" type="text" id="endDate" onclick="WdatePicker()"> &nbsp;&nbsp;
		渠道商:<select name="channel" id="channel">
			<% for(int i = 0; i < channelList.size(); i++){ %>
				<option><%=channelList.get(i).getName() %></option>
			<%} %>
		</select>&nbsp;&nbsp;
		学生电话：<input name="stuContactTel" id="stuContactTel"/>&nbsp;&nbsp;
		<button id="searchButton">查询</button>
		<button id="exportExcel">导出</button>
	</div>
	<hr width="1500" color="black"/>	
	<br/>
	<div id="dealDiv">
		<table id="dealTable" style="text-align: center;">
			<thead>
				<tr> 
					<th>姓名</th>
					<th>班级名称</th>
					<th>学费</th>
					<th>渠道商</th>
					<th>所属部门</th>
					<th>返点比例</th>
					<th>佣金</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<th>姓名</th>
					<th>班级名称</th>
					<th>学费</th>
					<th>渠道商</th>
					<th>所属部门</th>
					<th>返点比例</th>
					<th>佣金</th>
				</tr>
			</tfoot>
		</table>
	</div>
</div>

<div id="customRebateDiv">
	<div id="customSearchDiv" style="border: 1px solid black">
		起始时间：<input name="beginDate" type="text" id="customBeginDate" onclick="WdatePicker()"> &nbsp;&nbsp;&nbsp;&nbsp;
		截止时间：<input name="endDate" type="text" id="customEndDate" onclick="WdatePicker()"> &nbsp;&nbsp;
		渠道商:<select name="channel" id="customChannel">
			<% for(int i = 0; i < channelList.size(); i++){ %>
				<option><%=channelList.get(i).getName() %></option>
			<%} %>
		</select>&nbsp;&nbsp;
		学生电话：<input name="stuContactTel" id="customStuContactTel"/>&nbsp;&nbsp;
		<button id="customSearchButton">查询</button>
		<button id="submitModifyRebate">修改返点比例</button>
	</div>
	<br/>
	<table id="customRebateTable" border="1" class="imagetable">
		<thead>
			<tr>
				<th>姓名</th>
				<th>班级名称</th>
				<th>学费</th>
				<th>渠道</th>
				<th>所属部门</th>
				<th>返佣比例</th>
				<th>佣金</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
		<tfoot>
			<tr>
				<th>姓名</th>
				<th>班级名称</th>
				<th>学费</th>
				<th>渠道</th>
				<th>所属部门</th>
				<th>返佣比例</th>
				<th>佣金</th>
			</tr>
		</tfoot>
	</table>
</div>
</body>
</html>