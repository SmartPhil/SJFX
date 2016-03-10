<%@	page import="java.text.SimpleDateFormat"%>
<%@	page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.xdf.dto.Opportunity" %>
<%@ page import="com.xdf.dto.Management" %>
<%@ page import="com.xdf.dto.User" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上海新东方商机系统</title>
<link href="<%=request.getContextPath()%>/css/KFMain.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/uploadify/uploadify.css" rel="stylesheet"  type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/KFMain.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tableExport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.base64.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/DateFormate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>

</head>
<body>
<% 
	HttpSession sessions = request.getSession();
	Object username = sessions.getAttribute("username");
	Object channelname = sessions.getAttribute("channelName");
	Object channeltype = sessions.getAttribute("channelType");
	//所有管理部门
	List<Management> managementList = (List<Management>)sessions.getAttribute("managementList");
	//当前用户所管理的部门
	List<String> managementArray = (List<String>)request.getAttribute("curtUserManagements");
%>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">
        		<img alt="" src="<%=request.getContextPath()%>/image/logo.png">
      		</a>
		</div>
		<div class="collapse navbar-collapse" style="margin-left: auto;margin-right: auto;width: 70%;">
			<ul class="nav nav-pills">
				<li role="presentation"><a id="usernameShow" href="#">欢迎您:<%=username %></a></li>
  				<li role="presentation" class="active"><a href="#">未处理商机</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/tj.jsp">数据量统计</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/adduser.jsp">添加用户</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/queryOpp.jsp">查询数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/dealInfo.jsp">成单数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/importOpp.jsp">导入商机</a></li>
			</ul>
		</div>
	</div>
</nav>
<br/>
<div id="dataShowDiv" class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">未处理商机</div>
	<table style="width: 100%" aria-describedby="example_info" role="grid" cellspacing="0" width="100%" class="table table-striped table-bordered dataTable" id="mainTable">
		<thead>
			<tr>
				<th>学员姓名</th>
				<th>家长姓名</th>
				<th>联系方式1</th>
				<th>联系方式2</th>
				<th>需求课程</th>
				<th>所属部门</th>
				<th>渠道商</th>
				<th>渠道商类型</th>
				<th>创建日期</th>
			</tr>
		</thead>
		<% 
			List<Opportunity> oppList = (List<Opportunity>)request.getAttribute("noAssignOppList");
		%>
		<tbody>
			<% for(int i = 0; i < oppList.size(); i++){%>
			<tr>
				<td><%=oppList.get(i).getStuName() %></td>
				<td><%=oppList.get(i).getParentName() %></td>
				<td><%=oppList.get(i).getContactTel1() %></td>
				<td><%=oppList.get(i).getContactTel2() %></td>
				<td><%=oppList.get(i).getNeedCls() %></td>
				<td><%=oppList.get(i).getManagement() %></td>
				<td><%=oppList.get(i).getChannelName() %></td>
				<td><%=oppList.get(i).getChannelType() %></td>
				<td><%=oppList.get(i).getCreateDate() %></td>
			</tr>
			<% } %>
		</tbody>
		<tfoot>
			<tr>
				<th>学员姓名</th>
				<th>家长姓名</th>
				<th>联系方式1</th>
				<th>联系方式2</th>
				<th>需求课程</th>
				<th>所属部门</th>
				<th>渠道商</th>
				<th>渠道商类型</th>
				<th>创建日期</th>
			</tr>
		</tfoot>
	</table>
</div>

<div id="assignDiv">
	<table id="assignTable">
		<tr>
			<td>学员姓名</td>
			<td id="stuNameTd"></td>
			<td>家长姓名</td>
			<td id="parentNameTd"></td>
		</tr>
		<tr>
			<td>联系方式1</td>
			<td id="contactTel1Td"></td>
			<td>联系方式2</td>
			<td id="contactTel2Td"></td>
		</tr>
		<tr>
			<td>需求课程</td>
			<td id="needClsTd"></td>
			<td>所属部门</td>
			<td id="managementTd"></td>
		</tr>
		<tr>
			<td>渠道商</td>
			<td id="channelNameTd"></td>
			<td>渠道商类型</td>
			<td id="channelTypeTd"></td>
		</tr>
		<tr>
			<td>创建日期</td>
			<td id="createDateTd"></td>
			<td>是否有效</td>
			<td id="isValidTd">有效</td>
		</tr>
		<tr>
			<td>无效原因</td>
			<td id="noValidReason">无</td>
			<td>是否分配</td>
			<td id="isAssign">未分配</td>
		</tr>
		<tr>
			<td>分配员工</td>
			<td id="assignEmployee">
				<select id="assignEmployeeSelect" name="assignEmployee">
					<% List<User> userList = (List<User>)request.getAttribute("userList"); %>
					<% for(int i = 0; i < userList.size(); i++){ %>
					<option value="<%=userList.get(i).getUsername() %>"><%=userList.get(i).getName() %></option>
					<%} %>
				</select>
			</td>
			<td>操作</td>
			<td>
				<button id="btn_submitAssign">提交</button>
				<button id="btn_back1">返回</button>
			</td>
		</tr>
	</table>
</div>
<div id="moveDiv">
	<table id="moveTable">
		<tr>
			<td>学员姓名</td>
			<td id="move_stuNameTd"></td>
			<td>家长姓名</td>
			<td id="move_parentNameTd"></td>
		</tr>
		<tr>
			<td>联系方式1</td>
			<td id="move_contactTel1Td"></td>
			<td>联系方式2</td>
			<td id="move_contactTel2Td"></td>
		</tr>
		<tr>
			<td>需求课程</td>
			<td id="move_needClsTd"></td>
			<td>所属部门</td>
			<td id="move_managementTd">
				<select id="move_management" name="management">
					<% for(int i = 0; i < managementList.size(); i++){ %>
					<option value="<%=managementList.get(i).getManagementName() %>"><%=managementList.get(i).getManagementName() %></option>
					<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td>渠道商</td>
			<td id="move_channelNameTd"></td>
			<td>渠道商类型</td>
			<td id="move_channelTypeTd"></td>
		</tr>
		<tr>
			<td>创建日期</td>
			<td id="move_createDateTd"></td>
			<td>是否有效</td>
			<td id="move_isValidTd">有效</td>
		</tr>
		<tr>
			<td>无效原因</td>
			<td id="move_noValidReason">无</td>
			<td>是否分配</td>
			<td id="move_isAssign">未分配</td>
		</tr>
		<tr>
			<td>分配员工</td>
			<td id="move_assignEmployee">无</td>
			<td>操作</td>
			<td>
				<button id="btn_submitMove">流转</button>
				<button id="btn_back2">返回</button>
			</td>
		</tr>
	</table>
</div>
<div id="handleDiv">
	<div id="followContentDiv">
		<p>历史跟进记录</p>
		<table id="handleContentTable" border="1">
			<thead>
				<tr>
					<td>跟进时间</td>
					<td>跟进内容</td>
					<td>跟进人</td>
				</tr>
			</thead>
		</table>
	</div>
	<br/>
	<table id="handleTable">
		<tr>
			<td>学员姓名</td>
			<td id="handle_stuNameTd"></td>
			<td>家长姓名</td>
			<td id="handle_parentNameTd"></td>
		</tr>
		<tr>
			<td>联系方式1</td>
			<td id="handle_contactTel1Td"></td>
			<td>联系方式2</td>
			<td id="handle_contactTel2Td"></td>
		</tr>
		<tr>
			<td>需求课程</td>
			<td id="handle_needClsTd"></td>
			<td>所属部门</td>
			<td id="handle_managementTd"></td>
		</tr>
		<tr>
			<td>渠道商</td>
			<td id="handle_channelNameTd"></td>
			<td>渠道商类型</td>
			<td id="handle_channelTypeTd"></td>
		</tr>
		<tr>
			<td>创建日期</td>
			<td id="handle_createDateTd"></td>
			<td>是否有效</td>
			<td id="handle_isValidTd">有效</td>
		</tr>
		<tr>
			<td>无效原因</td>
			<td id="handle_noValidReason">无</td>
			<td>是否分配</td>
			<td id="handle_isAssign">未分配</td>
		</tr>
		<tr>
			<td colspan="2">分配员工</td>
			<td id="handle_assignEmployee" colspan="2">无</td>
		</tr>
		<tr>
			<td>处理时间</td>
			<% Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String nowString = sdf.format(now);
			%>
			<td id="followTime" colspan="3"><%=nowString %></td>
		</tr>
		<tr>
			<td>内容</td>
			<td colspan="3">
				<textarea id="followContent" rows="4" cols="20"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button id="btn_submitHandle">提交</button>
				<button id="btn_back3">返回</button>
			</td>
		</tr>
	</table>
</div>
<div id="dealDiv">
	<table id="dealTable" class="tablecss">
		<tr>
			<td>学员姓名</td>
			<td id="deal_stuNameTd"></td>
			<td>家长姓名</td>
			<td id="deal_parentNameTd"></td>
		</tr>
		<tr>
			<td>联系方式1</td>
			<td id="deal_contactTel1Td"></td>
			<td>联系方式2</td>
			<td id="deal_contactTel2Td"></td>
		</tr>
		<tr>
			<td>需求课程</td>
			<td id="deal_needClsTd"></td>
			<td>所属部门</td>
			<td id="deal_managementTd"></td>
		</tr>
		<tr>
			<td>渠道商</td>
			<td id="deal_channelNameTd"></td>
			<td>渠道商类型</td>
			<td id="deal_channelTypeTd"></td>
		</tr>
		<tr>
			<td>创建日期</td>
			<td id="deal_createDateTd"></td>
			<td>是否有效</td>
			<td id="deal_isValidTd">有效</td>
		</tr>
		<tr>
			<td>是否分配</td>
			<td id="deal_isAssign">未分配</td>
			<td>分配员工</td>
			<td id="deal_assignEmployee">无</td>
		</tr>
		<tr>
			<td>无效原因</td>
			<td colspan="3" id="deal_noValidReason">无</td>
		</tr>
		<tr>
			<td colspan="4">
				<button id="btn_submitDeal">标为已成单</button>
				<button id="btn_back5">返回</button>
			</td>
		</tr>
	</table>
</div>
<div id="markToInValidDiv">
	<table id="markTable" class="tablecss">
		<tr>
			<td>学员姓名</td>
			<td id="mark_stuNameTd"></td>
			<td>家长姓名</td>
			<td id="mark_parentNameTd"></td>
		</tr>
		<tr>
			<td>联系方式1</td>
			<td id="mark_contactTel1Td"></td>
			<td>联系方式2</td>
			<td id="mark_contactTel2Td"></td>
		</tr>
		<tr>
			<td>需求课程</td>
			<td id="mark_needClsTd"></td>
			<td>所属部门</td>
			<td id="mark_managementTd"></td>
		</tr>
		<tr>
			<td>渠道商</td>
			<td id="mark_channelNameTd"></td>
			<td>渠道商类型</td>
			<td id="mark_channelTypeTd"></td>
		</tr>
		<tr>
			<td>创建日期</td>
			<td id="mark_createDateTd"></td>
			<td>是否有效</td>
			<td id="mark_isValidTd">有效</td>
		</tr>
		<tr>
			<td>是否分配</td>
			<td id="mark_isAssign">未分配</td>
			<td>分配员工</td>
			<td id="mark_assignEmployee">无</td>
		</tr>
		<tr>
			<td>无效原因</td>
			<td colspan="3" id="mark_noValidReason">
				<select id="inValidReason">
					<option value="0">新东方学员</option>
					<option value="1">地区不符合</option>
					<option value="2">年龄不符合</option>
					<option value="3">无学习意向</option>
					<option value="4">空号错号</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button id="btn_submitMark">提交</button>
				<button id="btn_back4">返回</button>
			</td>
		</tr>
	</table>
</div>
<div id="operation_div">
	<button id="btn_assign">分配</button>
	<button id="btn_move">流转</button>
	<button id="btn_handle">处理</button>
	<button id="btn_martToDeal">标为已成单</button>
	<button id="btn_mark">标为无效</button>
	<button id="btn_cancle_operation">取消</button>
</div>
</body>
</html>