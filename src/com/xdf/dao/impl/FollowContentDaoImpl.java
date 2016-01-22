package com.xdf.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.FollowContentDao;
import com.xdf.dto.FollowContent;
import com.xdf.util.HibernateUtil;

public class FollowContentDaoImpl implements FollowContentDao {

	@Override
	public Integer insertFollowContent(FollowContent followContent) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		Integer result = (Integer)session.save(followContent);
		ts.commit();
		session.close();
		return result;
	}

	@Override
	public List<FollowContent> getFollowContentById(int id) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from FollowContent where oppId = ?";
			Query query = session.createQuery(hql);
			query.setInteger(0, id);
			List<FollowContent> followConList = (List<FollowContent>) query.list();
			ts.commit();
			session.close();
			return followConList;
		} catch (Exception e) {
			System.out.println("查询跟进记录时出错：" + e.getMessage());
			ts.rollback();
			session.close();
			return null;
		}
	}
}
