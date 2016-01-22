package com.xdf.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.ManagementDao;
import com.xdf.dto.Management;
import com.xdf.util.HibernateUtil;

public class ManagementDaoImpl implements ManagementDao {

	@Override
	public List<Management> getManagementAll() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Management";
			Query query = session.createQuery(hql);
			List<Management> result = query.list();
			ts.commit();
			session.close();
			return result;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("查询管理部门出错：" + e.getMessage());
			return null;
		}
	}
}
