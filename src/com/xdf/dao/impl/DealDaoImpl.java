package com.xdf.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.DealDao;
import com.xdf.dto.Deal;
import com.xdf.util.HibernateUtil;

public class DealDaoImpl implements DealDao {

	@Override
	public boolean insertDeal(Deal deal) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			Serializable s = session.save(deal);
			System.out.println("插入的结果为：" + s.toString()); 
			ts.commit();
			session.close();
			if(!"".equals(s.toString()) && s.toString() != null){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean deleteDealByOppId(int oppId) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "delete from Deal d where d.oppId = ?";
			Query query = session.createQuery(hql);
			query.setInteger(0, oppId);
			int deleteResult = query.executeUpdate();
			ts.commit();
			session.close();
			if(deleteResult != 0){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("删除关联详细成单数据失败：" + e.getMessage());
			ts.rollback();
			session.close();
			return false;
		}
	}

	@Override
	public List<Deal> getDealByOppId(List<Integer> oppIdList) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Deal d where oppId = ?";
			Query query = session.createQuery(hql);
			List<Deal> resultList = new ArrayList<Deal>();
			for (Integer oppId : oppIdList) {
				query.setInteger(0, oppId);
				List<Deal> dealList = (List<Deal>)query.list();
				resultList.addAll(dealList);
			}
			ts.commit();
			session.close();
			return resultList;
		} catch (Exception e) {
			System.out.println("按照商机Id查询成单详细信息失败：" + e.getMessage());
			ts.rollback();
			session.close();
			return null;
		}
	}

	@Override
	public List<Deal> getDealByDate(Date beginDate, Date endDate) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Deal d where d.beginDate > ? and d.beginDate < ?";
			Query query = session.createQuery(hql);
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(beginDate);
			query.setCalendar(0, beginCal);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			query.setCalendar(1, endCal);
			List<Deal> deals = query.list();
			ts.commit();
			session.close();
			return deals;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			return null;
		}
	}

	@Override
	public List<Deal> getDealByDateAndChannel(Date beginDate, Date endDate, String channelName) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Deal d where d.beginDate > ? and d.beginDate < ? and ";
			Query query = session.createQuery(hql);
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(beginDate);
			query.setCalendar(0, beginCal);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			query.setCalendar(1, endCal);
			List<Deal> deals = query.list();
			ts.commit();
			session.close();
			return deals;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			return null;
		}
	}

	@Override
	public List<Deal> getDealByBeginDate(Date beginDate) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Deal d where d.beginDate > ?";
			Query query = session.createQuery(hql);
			Calendar beginCalendar = Calendar.getInstance();
			beginCalendar.setTime(beginDate);
			query.setCalendar(0, beginCalendar);
			List<Deal> deals = (List<Deal>)query.list();
			ts.commit();
			session.close();
			return deals;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("按起始日期查询成单数据失败：" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Deal> getDealByEndDate(Date endDate) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Deal d where d.endDate < ?";
			Query query = session.createQuery(hql);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(endDate);
			query.setCalendar(0, endCalendar);
			List<Deal> deals = (List<Deal>)query.list();
			ts.commit();
			session.close();
			return deals;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("按截止日期查询成单数据失败：" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Deal> getAllDeal(Date beginDate , Date endDate , String contactTel) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			StringBuilder sb = new StringBuilder("from Deal d where 1=1");
			if(beginDate != null){
				sb.append(" and d.beginDate >:beginDate");
			}
			if(endDate != null){
				sb.append(" and d.beginDate <:endDate");
			}
			if(!"".equals(contactTel) && contactTel != null){
				sb.append(" and (d.opportunity.contactTel1 =:contactTel or d.opportunity.contactTel2 =:contactTel)");
			}
			String hql = sb.toString();
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
			if(!"".equals(contactTel) && contactTel != null){
				query.setString("contactTel", contactTel);
			}
			List<Deal> deals = (List<Deal>)query.list();
			ts.commit();
			session.close();
			return deals;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("查询所有成单数据失败：" + e.getMessage());
			return null;
		}
	}
}
