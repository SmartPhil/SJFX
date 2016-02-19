<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/uploadify/uploadify.css" rel="stylesheet"  type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<!-- js -->
<script type="text/javascript">

$(document).ready(function(){
	//alert("I'm an add info!");
	$("#testInterface").click(function(){
		$.ajax({
			url : 'test.action',
			type : 'post',
			dataType : 'json',
			success : function(e){
				alert(e);
			},
			error : function(e){
				alert("失败！");
			}
		});	
	});
	
	
	
	$("#add").click(function(){
		var button = document.createElement("button");
		button.setAttribute("class", "test");
		button.appendChild(document.createTextNode("测试1"));
		document.getElementById("mainBox").appendChild(button);
	});
	
	$("#file_upload").uploadify({
		'swf' : '<%=request.getContextPath()%>/uploadify/uploadify.swf',  
	    'uploader' : '<%=request.getContextPath()%>/test.action',  
	    'cancelImg'      : '<%=request.getContextPath()%>/img/uploadify-cancel.png',  
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
	});
});
</script>

<title>test</title>
</head>
<body>
<div id="mainBox">  
<button id="testInterface">测试接口</button>
<button id="add">添加按钮</button>
<button class="test">测试</button>
</div>
<input type="file" name="file_upload" id="file_upload"/>
<div id="some_file_queue"></div>
<button id="importExcel" class="btn btn-primary">上传</button>
</body>
</html>
