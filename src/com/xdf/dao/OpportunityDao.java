package com.xdf.dao;

import java.util.Date;
import java.util.List;

import com.xdf.dto.Opportunity;

public interface OpportunityDao {
	/**
	 * insert a new opportunity into the database
	 * @param opp
	 * @return insert result (boolean)
	 */
	public boolean insertOpportunity(Opportunity opp);
	
	/**
	 * get all opportunity from database
	 * @return List<Opportunity>
	 */
	public List<Opportunity> getAllOpportunity();
	
	/**
	 * get opportunity which belong to the username'managementArray 
	 * @param username
	 * @param managementArr
	 * @return List<Opportunity>
	 */
	public List<Opportunity> getOpportunityByManagement(String[] managementArr);
	
	/**
	 * update opportunity's employee
	 * @param employeeUserName
	 * @param contactTel1
	 * @param contactTel2
	 * @return update result(boolean)
	 */
	public boolean updateEmployee(String employeeUserName,String contactTel1,String contactTel2);
	
	/**
	 * update opportunity's management
	 * @param management
	 * @param contactTel1
	 * @param contactTel2
	 * @return update result(boolean)
	 */
	public boolean updateManagement(String management,String contactTel1,String contactTel2);
	
	/**
	 * get opportunity by contactTel
	 * @param contactTel1
	 * @param contactTel2
	 * @return Opportunity
	 */
	public Opportunity getOpportunityByContact(String contactTel1,String contactTel2);
	
	/**
	 * update opportunity
	 * @param opp 
	 * @return update result
	 */
	public boolean update(Opportunity opp);
	
	/**
	 * get opportunities by assign employee user name
	 * @param username
	 * @return List<Opportunity>
	 */
	public List<Opportunity> getOpportunityByAssignEmployee(String username);
	
	/**
	 * get the count of opportunity
	 * @param channelName
	 * @return int
	 */
	public int getOppCountByChannelAndTime(String channelName,Date beginDate,Date endDate);
	
	/**
	 * get opportunities by begin date and end date
	 * @return List<Opportunity>
	 */
	public List<Opportunity> searchOpp(Date beginDate, Date endDate,String[] managementArr);
	
	/**
	 * mark the opportunity to invalid
	 * @param contactTel1
	 * @param contactTel2
	 * @param invalidReason
	 * @return mark result
	 */
	public boolean markToInvalid(String contactTel1,String contactTel2,String invalidReason);
	
	/**
	 * mark the opportunity to deal(nState from 0 to 1)
	 * @param contactTel1
	 * @param contactTel2
	 * @return mark result
	 */
	public boolean markToDeal(String contactTel1, String contactTel2);
	
	/**
	 * get current month Opportunity which has deal
	 * @param beginDate
	 * @param endDate
	 * @return List<Opportunity>
	 */
	public List<Opportunity> getCurtDealOpp(Date beginDate,Date endDate);
	
	/**
	 * get opportunity by id
	 * @param id
	 * @return Opportunity
	 */
	public Opportunity getOppById(int id);
	
	/**
	 * get opportunity by channel name and create date
	 * @param channelName
	 * @param beginDate
	 * @param endDate
	 * @return List<Opportunity>
	 */
	public List<Opportunity> getOppByChannelAndDate(String channelName, Date beginDate, Date endDate);
	
	/**
	 * get opportunity by student contact
	 * @param stuContactTel
	 * @return Opportunity
	 */
	public Opportunity getOppByContact(String stuContactTel);
	
	/**
	 * get this opportunity is old opportunity in six month
	 * @param opportunity
	 * @return boolean
	 */ 
	public boolean isOldOpp(Opportunity opportunity);
}
