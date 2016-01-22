package com.xdf.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xdf.dao.ClientDao;
import com.xdf.dto.Client;
import com.xdf.util.HibernateUtil;

public class ClientDaoImpl implements ClientDao {

	@Override
	public Integer insertClient(Client client) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		Integer result = (Integer)session.save(client);
		ts.commit();
		session.close();
		return result;
	}
}
