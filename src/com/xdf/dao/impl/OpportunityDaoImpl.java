package com.xdf.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.OpportunityDao;
import com.xdf.dto.Opportunity;
import com.xdf.util.HibernateUtil;

public class OpportunityDaoImpl implements OpportunityDao {

	@Override
	public boolean insertOpportunity(Opportunity opp) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			session.save(opp);
			ts.commit();
			session.close();
			return true;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("插入商机失败：" + e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Opportunity> getAllOpportunity() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity";
			Query query = session.createQuery(hql);
			List<Opportunity> resultList = query.list();
			ts.commit();
			session.close();
			return resultList;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("获取所有商机失败：" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Opportunity> getOpportunityByManagement(String[] managementArr) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity where management = ? and isValid = 1 and isAssign = 0 and state = 0";
			Query query = session.createQuery(hql);
			List<Opportunity> result = new ArrayList<Opportunity>();
			for(int i = 0; i < managementArr.length; i++){
				query.setString(0, managementArr[i]);
				List<Opportunity> result1 = query.list();
				result.addAll(result1);
			}
			ts.commit();
			session.close();
			return result;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("按照客服主管查询出错：" + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean updateEmployee(String employeeUserName,String contactTel1,String contactTel2) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "update Opportunity set assignEmployee = ?,isAssign = 1 where contactTel1 = ? and contactTel2 = ?";
			Query query = session.createQuery(hql);
			query.setString(0, employeeUserName);
			query.setString(1, contactTel1);
			query.setString(2, contactTel2);
			int result = query.executeUpdate();
			ts.commit();
			session.close();
			if(result == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("更新商机分配员工出错：" +  e.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateManagement(String management, String contactTel1, String contactTel2) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "update Opportunity set management = ?,assignEmployee = ?,isAssign = 0 where contactTel1 = ? and contactTel2 = ?";
			Query query = session.createQuery(hql);
			query.setString(0, management);
			query.setString(1, "");
			query.setString(2, contactTel1);
			query.setString(3, contactTel2);
			int result = query.executeUpdate();
			ts.commit();
			session.close();
			if(result == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("更新商机所属部门出错：" + e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Opportunity getOpportunityByContact(String contactTel1, String contactTel2) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity where contactTel1 = ? and contactTel2 = ?";
			Query query = session.createQuery(hql);
			query.setString(0, contactTel1);
			query.setString(1, contactTel2);
			List<Opportunity> resultList = query.list();
			ts.commit();
			session.close();
			if(resultList.size() == 1){
				return resultList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("按照联系方式查询商机出错：" + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean update(Opportunity opp) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			session.update(opp);
			ts.commit();
			session.close();
			return true;
		} catch (Exception e) {
			System.out.println("更新商机出错：" + e.getMessage());
			ts.rollback();
			session.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Opportunity> getOpportunityByAssignEmployee(String username) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity where assignEmployee = ? and isValid = 1 and isAssign = 1 and state = 0";
			Query query = session.createQuery(hql);
			query.setString(0, username);
			List<Opportunity> result = query.list();
			ts.commit();
			session.close();
			return result;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("通过已分配员工名查询商机出错：" + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getOppCountByChannelAndTime(String channelName,Date beginDate,Date endDate) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity where channelName = ? and createDate > ? and createDate < ?";
			Query query = session.createQuery(hql);
			query.setString(0, channelName);
			Calendar beginCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			beginCalendar.setTime(beginDate);
			endCalendar.setTime(endDate);
			query.setCalendar(1, beginCalendar);
			query.setCalendar(2, endCalendar);
			List<Opportunity> oppList = query.list();
			int result = oppList.size();
			ts.commit();
			session.close();
			return result;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("通过渠道商名查询商机出错：" + e.getMessage());
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Opportunity> searchOpp(Date beginDate, Date endDate,String[] managementArr) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		
		try {
			//String hql = "from Opportunity where createDate >= ? and createDate <= ? and management = ?";
			String hql = "from Opportunity where management =:management";
			if(beginDate != null){
				hql += " and createDate >=:beginDate";
			}
			if(endDate != null){
				hql += " and createDate <=:endDate";
			}
			Query query = session.createQuery(hql);
			if(beginDate != null){
				Calendar beginCalendar = Calendar.getInstance();
				beginCalendar.setTime(beginDate);
				query.setCalendar("beginDate", beginCalendar);
			}
			if(endDate != null){
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(endDate);
				query.setCalendar("endDate", endCalendar);
			}
			List<Opportunity> result = new ArrayList<Opportunity>();
			for (int i = 0; i < managementArr.length; i++) {
				query.setString("management", managementArr[i]);
				List<Opportunity> oppList = query.list();
				result.addAll(oppList);
			}
			ts.commit();
			session.close();
			return result;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			return null;
		}
	}

	@Override
	public boolean markToInvalid(String contactTel1, String contactTel2, String invalidReason) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "";
			if(contactTel2 == null || "".equals(contactTel2)){
				hql = "update Opportunity t set t.isValid = 0,t.noValidReason = ? where t.contactTel1 = ?";
			}else {
				hql = "update Opportunity t set t.isValid = 0,t.noValidReason = ? where t.contactTel1 = ? and t.contactTel2 = ?";
			}
			
			Query query = session.createQuery(hql);
			query.setString(0, invalidReason);
			query.setString(1, contactTel1);
			if(contactTel2 != null && !"".equals(contactTel2)){
				query.setString(2, contactTel2);
			}
			int i = query.executeUpdate();
			System.out.println(i);
			ts.commit();
			session.close();
			if(i == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println("标记为无效商机出错：" + e.getMessage());
			ts.rollback();
			session.close();
			return false;
		}
	}

	@Override
	public boolean markToDeal(String contactTel1, String contactTel2) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "";
			if(contactTel2 == null || "".equals(contactTel2)){
				hql = "update Opportunity t set t.state = 1 where t.contactTel1 = ?";
			}else {
				hql = "update Opportunity t set t.state = 1 where t.contactTel1 = ? and t.contactTel2 = ?";
			}
			
			Query query = session.createQuery(hql);
			query.setString(0, contactTel1);
			if(contactTel2 != null && !"".equals(contactTel2)){
				query.setString(2, contactTel2);
			}
			int i = query.executeUpdate();
			System.out.println(i);
			ts.commit();
			session.close();
			if(i == 1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println("标记为已成单出错：" + e.getMessage());
			ts.rollback();
			session.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Opportunity> getCurtDealOpp(Date beginDate, Date endDate) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity o where o.state = 1 and createDate > ? and createDate < ?";
			Query query = session.createQuery(hql);
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(beginDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			query.setCalendar(0, beginCal);
			query.setCalendar(1, endCal);
			List<Opportunity> oppList = (List<Opportunity>)query.list();
			ts.commit();
			session.close();
			return oppList;
		} catch (Exception e) {
			System.out.println("查询当月已成单商机出错：" + e.getMessage());
			ts.rollback();
			session.close();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Opportunity getOppById(int id) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity o where o.id = ?";
			Query query = session.createQuery(hql);
			query.setInteger(0, id);
			List<Opportunity> oppList = (List<Opportunity>)query.list();
			ts.commit();
			session.close();
			return oppList.get(0);
		} catch (Exception e) {
			System.out.println("按照商机id查询商机出错：" + e.getMessage());
			ts.rollback();
			session.close();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Opportunity> getOppByChannelAndDate(String channelName, Date beginDate, Date endDate) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity where channelName = ?";
			if(beginDate != null && endDate != null){
				hql += " and createDate > ? and createDate < ?";
			}else if(beginDate != null && endDate == null){
				hql += " and createDate > ?";
			}else if(beginDate == null && endDate != null){
				hql += " and createDate < ?";
			}
			Query query = session.createQuery(hql);
			query.setString(0, channelName);
			if(beginDate != null && endDate != null){
				Calendar beginCalendar = Calendar.getInstance();
				Calendar endCalendar = Calendar.getInstance();
				beginCalendar.setTime(beginDate);
				endCalendar.setTime(endDate);
				query.setCalendar(1, beginCalendar);
				query.setCalendar(2, endCalendar);
			}else if(beginDate != null && endDate == null){
				hql += " and createDate > ?";
				Calendar beginCalendar = Calendar.getInstance();
				beginCalendar.setTime(beginDate);
				query.setCalendar(1, beginCalendar);
			}else if(beginDate == null && endDate != null){
				hql += " and createDate < ?";
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(endDate);
				query.setCalendar(1, endCalendar);
			}
			List<Opportunity> oppList = query.list();
			ts.commit();
			session.close();
			return oppList;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("通过渠道商名查询商机出错：" + e.getMessage());
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Opportunity getOppByContact(String stuContactTel) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity where contactTel1 = ? or contactTel2 = ?";
			Query query = session.createQuery(hql);
			query.setString(0, stuContactTel);
			query.setString(1, stuContactTel);
			List<Opportunity> resultList = query.list();
			ts.commit();
			session.close();
			if(resultList.size() == 1){
				return resultList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("按照联系方式查询商机出错：" + e.getMessage());
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isOldOpp(Opportunity opportunity) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Opportunity where contactTel1 = ? or contactTel1 = ? or contactTel2 = ? or contactTel2 = ?";
			Query query = session.createQuery(hql);
			query.setString(0, opportunity.getContactTel1());
			query.setString(1, opportunity.getContactTel2());
			query.setString(2, opportunity.getContactTel1());
			query.setString(3, opportunity.getContactTel2());
			List<Opportunity> resultList = query.list();
			ts.commit();
			session.close();
			if(resultList.size() == 0){
				return false;
			}else{
				return true;
				/*Opportunity opp = resultList.get(0);
				Date createDate = opp.getCreateDate();
				Calendar createCalendar = Calendar.getInstance();
				createCalendar.setTime(createDate);
				long createMillis = createCalendar.getTimeInMillis();
				Calendar now = Calendar.getInstance();
				now.add(Calendar.MONTH, -6);
				long nowMillis = now.getTimeInMillis();
				if(createMillis > nowMillis){
					return true;
				}else {
					return false;
				}*/
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("按照联系方式查询商机出错：" + e.getMessage());
			return false;
		}
	}
}
