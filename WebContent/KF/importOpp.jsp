<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商机分销系统</title>
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
            //alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
           	alert(data);
            t.clear().draw(false);
			var data1 = eval("(" + data + ")");
			alert(data1.length);
			for (var i = 0; i < data1.length; i++) {
				var obj = [
                           data1[i].createTime,data1[i].stuName,data1[i].parentName,data1[i].contactTel1,data1[i].contactTel2,
				           data1[i].channelName,data1[i].channelType,data1[i].management,data1[i].comment,data1[i].grade,data1[i].degree,
				           data1[i].address,data1[i].keyword
				          ];
				t.row.add(obj).draw(false);
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
  				<li role="presentation"><a href="<%=request.getContextPath()%>/toKF.action">未处理商机</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/tj.jsp">数据量统计</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/adduser.jsp">添加用户</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/queryOpp.jsp">查询数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/dealInfo.jsp">成单数据</a></li>
  				<li role="presentation" class="active"><a href="#">导入商机</a></li>
			</ul>
		</div>
	</div>
</nav>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">上传商机</div>
  	<div class="panel-body">
    	<input type="file" name="file_upload" id="file_upload"/>
		<div id="some_file_queue"></div>
		<button id="importExcel" class="btn btn-primary">上传</button>
  	</div>
</div>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
  <div class="panel-heading">成单数据</div>
  <table id="importBackInfoTab" class="table table-striped table-bordered dataTable" style="width: 100%" aria-describedby="example_info" role="grid" cellspacing="0" width="100%">
  		<thead>
  			<tr>
  				<th>创建日期</th>
  				<th>学员姓名</th>
				<th>家长姓名</th>
				<th>联系方式1</th>
				<th>联系方式2</th>
				<th>渠道商</th>
				<th>渠道类型</th>
				<th>需求课程</th>
				<th>管理部门</th>
				<th>备注</th>
				<th>年级</th>
				<th>学位</th>
				<th>地址</th>
				<th>关键字</th>
  			</tr>
  		</thead>
  		<tbody>
  		</tbody>
  		<tfoot>
  			<tr>
  				<th>创建日期</th>
  				<th>学员姓名</th>
				<th>家长姓名</th>
				<th>联系方式1</th>
				<th>联系方式2</th>
				<th>渠道商</th>
				<th>渠道类型</th>
				<th>需求课程</th>
				<th>管理部门</th>
				<th>备注</th>
				<th>年级</th>
				<th>学位</th>
				<th>地址</th>
				<th>关键字</th>
  			</tr>
  		</tfoot>
  	</table>
</div>
</body>
</html>