<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<!-- js -->
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	alert("I'm an add info!");
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
<script type="text/javascript">


</script>
</body>
</html>
