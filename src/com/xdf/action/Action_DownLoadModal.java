package com.xdf.action;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

public class Action_DownLoadModal extends ActionSupport implements ServletContextAware {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	private InputStream inStream;
	private String mimeType;
	private String num;
	
	@Override
	public String execute(){
		if("0".equals(num)){
			this.mimeType = context.getMimeType("download/channelModal.xlsx");
		}else if("1".equals(num)){
			this.mimeType = context.getMimeType("download/modal.xlsx");
		}
		return SUCCESS;
	}
	
	public InputStream getInStream(){
		if("0".equals(num)){
			this.inStream = context.getResourceAsStream("download/channelModal.xlsx");
		}else if("1".equals(num)){
			this.inStream = context.getResourceAsStream("download/modal.xlsx");
		}
		return this.inStream;
	}
	
	public String getMimeType(){
		return this.mimeType;
	}
	
	@Override
	public void setServletContext(ServletContext context) {
		// TODO Auto-generated method stub
		this.context = context;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
}
