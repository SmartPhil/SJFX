<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>批量导入商机</title>
<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/uploadify/uploadify.css" rel="stylesheet"  type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	var t = $("#importBackInfoTab").DataTable({});
	
	$("#file_upload").uploadify({
		'swf' : '<%=request.getContextPath()%>/uploadify/uploadify.swf',  
	    'uploader' : '<%=request.getContextPath()%>/importExcelOpp.action',  
	    'cancelImg'      : '<%=request.getContextPath()%>/img/uploadify-cancel.png',  
	    'folder'         : 'UploadFiles',
	    'dataType'       : 'json',
	    'queueID'        : 'some_file_queue',  
	    'auto'           : false,  
	    'multi'          : true,  
	    'simUploadLimit' : 2,
	    'fileObjName' : 'file_upload',
	    'buttonText' : '选择数据文件',
	    'onUploadStart' : function(file) {
	        $("#file_upload").uploadify('settings','formData',{'fileName':file.name,'username':$("#usernameShow").text().split(":")[1]});
	    },
	    'onUploadSuccess' : function(file, data, response) {
			var a = JSON.parse(data);
	    	if(a.result == "success"){
	    		alert("上传成功！");
	    	}else if(a.result == "fail"){
	    		alert("上传失败！");
	    	}else if(a.result == "null"){
	    		alert("请不要上传空文件！");
	    	}    	
        }
	});	
	$("#importExcel").click(function(){
		$("#file_upload").uploadify("upload");
	});
});

</script>
</head>
<body>
<%
	Enumeration<String> enumeration = session.getAttributeNames();
	String username = "";
	boolean hasAttr = false;
	
	while(enumeration.hasMoreElements()){
		String attr = enumeration.nextElement();
		if("username".equals(attr)){
			hasAttr = true;
		}
	}
	PrintWriter printWriter = response.getWriter();
	if(!hasAttr){
		printWriter.print("<script>alert(\"请先登录！\")</script>");
		printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
	}else {
		//判断username是否有值
		username = session.getAttribute("username").toString();
		if("".equals(username) || username == null) {
			printWriter.print("<script>alert(\"请先登录！\")</script>");
			printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
		}else {
			String role = session.getAttribute("role").toString();
			if(!"4".equals(role)){
				printWriter.print("<script>alert(\"您没有权限访问该页面！\")</script>");
				printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
			}
		}
	}
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
  				<li role="presentation"><a href="<%=request.getContextPath()%>/channel/channelMain.jsp">录入商机</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/channel/channelDealInfo.jsp">查看成单情况</a></li>
  				<li role="presentation" class="active"><a href="#">批量导入</a></li>
			</ul>
		</div>
	</div>
</nav>
<div class="panel panel-primary" style="width: 80%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">上传商机</div>
  	<div class="panel-body">
    	<input type="file" name="file_upload" id="file_upload"/>
		<div id="some_file_queue"></div>
		<button id="importExcel" class="btn btn-primary">上传</button>
  	</div>
</div>

<div class="panel panel-primary" style="width: 80%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">下载模板</div>
  	<div class="panel-body">
    	<a href="downloadModal.action?num=0" class="btn btn-primary">下载模板</a>
  	</div>
</div>
</body>
</html>