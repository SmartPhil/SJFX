package com.xdf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.UserDao;
import com.xdf.dto.User;
import com.xdf.util.HibernateUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public User getUserByNameAndPsw(String username, String password) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from User where username = ? and password = ?";
			Query query = session.createQuery(hql);
			query.setString(0, username);
			query.setString(1, password);
			List<User> result = query.list();
			ts.commit();
			session.close();
			if(result.size() > 0){
				return result.get(0);
			}else {
				return null;
			}
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("错误：" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<User> getUserByManagement(String[] managements) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from User where management = ? and role = 3";
			Query query = session.createQuery(hql);
			List<User> result = new ArrayList<User>();
			for(int i = 0; i < managements.length; i++){
				query.setString(0, managements[i]);
				result.addAll(query.list());
			}
			ts.commit();
			session.close();
			return result;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("按部门查询用户出错：" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<User> getChannelUser() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from User where role = 4";
			Query query = session.createQuery(hql);
			List<User> result = query.list();
			ts.commit();
			session.close();
			return result;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println("查询渠道商用户出错：" + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean addUser(User user) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			Integer i = (Integer)session.save(user);
			ts.commit();
			session.close();
			if(i != 0 && i != null){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("保存渠道商用户出错：" + e.getMessage());
			ts.commit();
			session.close();
			return false;
		}
	}

	@Override
	public List<User> getUserByUserName(String username) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from User where username = ?";
			Query query = session.createQuery(hql);
			query.setString(0, username);
			List<User> userList = query.list();
			ts.commit();
			session.close();
			return userList;
		} catch (Exception e) {
			System.out.println("按用户名查询用户出错：" + e.getMessage());
			ts.rollback();
			session.close();
			return null;
		}
	}
}
