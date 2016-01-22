/**
 * by phil guan
 */

$(document).ready(function(){
	$('#myButton').on('click', function () {
	    
	    
	});
	
	//登陆操作
	$("#btnLogin").click(function(){
		var $btn = $(this).button('loading');
		
		var userName = $("#username").val();
		var passWord = $("#password").val();
		
		if(userName == ""){
			alert("请输入用户名！");
			return;
		}
		if(passWord == ""){
			alert("请输入密码！");
			return;
		}
		$.ajax({
			url : "login.action",
			type : 'post',
			data : $("#loginForm").serialize(),
			success : function(data){
				var info = eval("("+data+")");
				var loginResult = info.loginResult;
				if(loginResult == "success"){
					var role = info.role;
					if(role == "1"){
						//市场人员登陆
						window.location.href = "toSC.action";
					}else if(role == "2"){
						//客服主管登陆
						window.location.href = "toKF.action";
					}else if(role == "3"){
						//客服人员登陆
						window.location.href = "toKFEmployee.action";
					}else if(role == "4"){
						//渠道主管登陆
						window.location.href = "channel/channelMain.jsp";
					}else if(role == "5"){
						//渠道人员登陆
						window.location.href = "channel/channelMain.jsp";
					}
					$btn.button('reset');
				}else{
					alert("登陆失败！用户名或密码错误！");
					$btn.button('reset');
				}
			},
			error : function(){
				alert("登陆失败！网络出错！请联系管理员！");
				$btn.button('reset');
			},
		});
	});
});
