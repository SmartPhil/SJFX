package com.xdf.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.UserDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dao.impl.UserDaoImpl;
import com.xdf.dto.Opportunity;
import com.xdf.dto.User;

public class Action_ToKF extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String oppDataJsonString;
	private List<Opportunity> noAssignOppList;
	private List<User> userList;
	private List<String> curtUserManagements = new ArrayList<String>();
	
	public String toKf(){
		String management = ActionContext.getContext().getSession().get("management").toString();
		String[] managementArray = management.split(",");
		for (int i = 0; i < managementArray.length; i++) {
			curtUserManagements.add(managementArray[i]);
		}
		//�����ǰ�ͷ��������ܲ�������δ����,δ�ɵ���Ч���̻�
		OpportunityDao oppDao = new OpportunityDaoImpl();
		noAssignOppList = oppDao.getOpportunityByManagement(managementArray);
		for (Opportunity opportunity : noAssignOppList) {
			if(opportunity.getStuName() == null || "".equals(opportunity.getStuName())){
				opportunity.setStuName("��");
			}
			if(opportunity.getParentName() == null || "".equals(opportunity.getParentName())){
				opportunity.setParentName("��");
			}
			if(opportunity.getContactTel1() == null || "".equals(opportunity.getContactTel1())){
				opportunity.setContactTel1("��");
			}
			if(opportunity.getContactTel2() == null || "".equals(opportunity.getContactTel2())){
				opportunity.setContactTel2("��");
			}
			if(opportunity.getNeedCls() == null || "".equals(opportunity.getNeedCls())){
				opportunity.setNeedCls("��");
			}
			if(opportunity.getManagement() == null || "".equals(opportunity.getManagement())){
				opportunity.setManagement("��");
			}
			if(opportunity.getChannelName() == null || "".equals(opportunity.getChannelName())){
				opportunity.setChannelName("��");
			}
			if(opportunity.getChannelType() == null || "".equals(opportunity.getChannelType())){
				opportunity.setChannelType("��");
			}
			if(opportunity.getAssignEmployee() == null || "".equals(opportunity.getAssignEmployee())){
				opportunity.setAssignEmployee("��");
			}
			if(opportunity.getNoValidReason() == null || "".equals(opportunity.getNoValidReason())){
				opportunity.setNoValidReason("��");
			}
		}
		
		//��ȡ�������ڸò��ŵĿͷ���Ա
		UserDao userDao = new UserDaoImpl();
		userList = userDao.getUserByManagement(managementArray);
		
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOppDataJsonString() {
		return oppDataJsonString;
	}

	public void setOppDataJsonString(String oppDataJsonString) {
		this.oppDataJsonString = oppDataJsonString;
	}

	public List<Opportunity> getNoAssignOppList() {
		return noAssignOppList;
	}

	public void setNoAssignOppList(List<Opportunity> noAssignOppList) {
		this.noAssignOppList = noAssignOppList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<String> getCurtUserManagements() {
		return curtUserManagements;
	}

	public void setCurtUserManagements(List<String> curtUserManagements) {
		this.curtUserManagements = curtUserManagements;
	}
}


