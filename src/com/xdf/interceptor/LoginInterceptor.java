package com.xdf.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		System.out.println("hello world!!");
		Map<String, Object> session = arg0.getInvocationContext().getSession();
        if(session.containsKey("username")){
            return arg0.invoke();
        } else {
            return Action.LOGIN;
        }
	}
	
}
