package com.xdf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.AreaDao;
import com.xdf.dto.Area;
import com.xdf.util.HibernateUtil;

public class AreaDaoImpl implements AreaDao {
	@Override
	public String getAreaByNum(String areaNum) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Area where areaNum = ?";
			Query query = session.createQuery(hql);
			query.setString(0, areaNum);
			List<Area> areaList = (ArrayList<Area>)query.list();
			ts.commit();
			session.close();
			if(areaList.size() > 0){
				return areaList.get(0).getArea();
			}else {
				return "";
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println(e.getMessage());
			return "";
		}
	}

	@Override
	public List<Area> getAll() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Area";
			Query query = session.createQuery(hql);
			List<Area> areaList = (ArrayList<Area>)query.list();
			ts.commit();
			session.close();
			return areaList;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println(e.getMessage());
			return null;
		}
	}
}
