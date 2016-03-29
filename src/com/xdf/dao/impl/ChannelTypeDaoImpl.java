package com.xdf.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.ChannelTypeDao;
import com.xdf.dto.ChannelType;
import com.xdf.util.HibernateUtil;

/**
 * 渠道商类型数据库查询实现类
 * @author 30975
 *
 */
public class ChannelTypeDaoImpl implements ChannelTypeDao {
	
	@Override
	public List<ChannelType> getAllChannelType() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			String hql = "from ChannelType";
			Query query = session.createQuery(hql);
			List<ChannelType> typeList = (ArrayList<ChannelType>)query.list();
			ts.commit();
			session.close();
			return typeList;
		} catch (Exception e) {
			ts.rollback();
			session.close();
			System.out.println(e.getMessage());
			return null;
		}
	}
}
