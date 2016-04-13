<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>有效性验证</title>
<!-- css文件 -->
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<!-- js文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#validOpp").click(function(){
		var $btn = $(this).button('loading');
		$.ajax({
			url : 'scValidOpp.action',
			type : 'post',
			data : {'username' : $("#usernameShow").html().split(':')[1]},
			datatype : 'json',
			timeout : 120*1000,
			success : function(e){
				var data = eval("(" + e + ")");
				var result = data.result;ssss
				if(result == "success"){
					alert("验证成功！");
				}else{
					alert("验证失败！");
				}
				
				$btn.button("reset");
			},
			error : function(e){
				alert("验证失败：网络出错，请联系管理员！");
				$btn.button("reset");
			}
		});
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
        		<img alt="" src="<%=request.getContextPath()%>/image/logo.png">
      		</a>
		</div>
		<div class="collapse navbar-collapse" style="margin-left: auto;margin-right: auto;width: 70%;">
			<ul class="nav nav-pills">
				<li role="presentation"><a id="usernameShow" href="#">欢迎您:<%=username %></a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/toSC.action">数据量统计</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/importOpp.jsp">导入商机</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/addChannel.jsp">添加渠道商</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/dealInfo.jsp">成单数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/searchopp.jsp">查询数据</a></li>
  				<li role="presentation" class="active"><a href="#">有效性验证</a></li>
			</ul>
		</div>
	</div>
</nav>
<br/>
<div class="panel panel-primary" style="width: 80%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">验证商机有效性</div>
  	<div class="panel-body">
    	<button id="validOpp" class="btn btn-primary" type="button" data-loading-text="验证中……">验证</button>
  	</div>
</div>
</body>
</html>