package com.xdf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.GradeDao;
import com.xdf.dto.Area;
import com.xdf.dto.Grade;
import com.xdf.util.HibernateUtil;

public class GradeDaoImpl implements GradeDao {

	@Override
	public String getGradeByNum(String gradeNum) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Grade where gradeNum = ?";
			Query query = session.createQuery(hql);
			query.setString(0, gradeNum);
			List<Grade> gradeList = (ArrayList<Grade>)query.list();
			ts.commit();
			session.close();
			if(gradeList.size() > 0){
				return gradeList.get(0).getGrade();
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
	public List<Grade> getAll() {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from Grade";
			Query query = session.createQuery(hql);
			List<Grade> gradeList = (ArrayList<Grade>)query.list();
			ts.commit();
			session.close();
			return gradeList;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println(e.getMessage());
			return null;
		}
	}

}
