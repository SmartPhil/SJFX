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
	
	@Override
	public String execute(){
		this.mimeType = context.getMimeType("download/channelModal.xlsx");
		return SUCCESS;
	}
	
	public InputStream getInStream(){
		this.inStream = context.getResourceAsStream("download/channelModal.xlsx");
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
}
