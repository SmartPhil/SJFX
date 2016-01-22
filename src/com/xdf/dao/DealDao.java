package com.xdf.dao;

import java.util.Date;
import java.util.List;

import com.xdf.dto.Deal;

public interface DealDao {
	/**
	 * insert a new deal into the deal table
	 * @param deal
	 * @return insert result
	 */
	public boolean insertDeal(Deal deal);
	
	/**
	 * delete the deal by opportunity id
	 * @param oppId
	 * @return delete result
	 */
	public boolean deleteDealByOppId(int oppId);
	
	/**
	 * get deals by opportunity id
	 * @param oppIdList
	 * @return List<Deal>
	 */
	public List<Deal> getDealByOppId(List<Integer> oppIdList);
	
	/**
	 * get deals by beginDate
	 * @param beginDate
	 * @param endDate
	 * @return List<Deal>
	 */
	public List<Deal> getDealByDate(Date beginDate,Date endDate);
	
	/**
	 * get deal by date and channel name
	 * @param beginDate
	 * @param endDate
	 * @param channelName
	 * @return List<Deal>
	 */
	public List<Deal> getDealByDateAndChannel(Date beginDate, Date endDate, String channelName);
	
	/**
	 * get deals by begin date
	 * @param beginDate
	 * @return List<Deal>
	 */
	public List<Deal> getDealByBeginDate(Date beginDate);
	
	/**
	 * get deals by end date
	 * @param endDate
	 * @return List<Deal>
	 */
	public List<Deal> getDealByEndDate(Date endDate);
	
	/**
	 * get all deals 
	 * @return List<Deal>
	 */
	public List<Deal> getAllDeal(Date beginDate , Date endDate , String contactTel);
}
