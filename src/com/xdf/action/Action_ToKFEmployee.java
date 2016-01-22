package com.xdf.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xdf.dao.OpportunityDao;
import com.xdf.dao.impl.OpportunityDaoImpl;
import com.xdf.dto.Opportunity;

@SuppressWarnings("serial")
public class Action_ToKFEmployee extends ActionSupport {
	
	private List<Opportunity> oppList;
	
	public String toKFEmployee(){
		String userName = ActionContext.getContext().getSession().get("username").toString();
		OpportunityDao oppDao = new OpportunityDaoImpl();
		oppList = oppDao.getOpportunityByAssignEmployee(userName);
		for (Opportunity opportunity : oppList) {
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
		return SUCCESS;
	}

	public List<Opportunity> getOppList() {
		return oppList;
	}

	public void setOppList(List<Opportunity> oppList) {
		this.oppList = oppList;
	}
}
