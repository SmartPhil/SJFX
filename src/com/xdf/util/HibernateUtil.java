package com.xdf.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory=null;
    //使用线程局部模式
    private static ThreadLocal<Session> threadLocal=new ThreadLocal<Session>();
   
    static {
        sessionFactory=new Configuration().configure().buildSessionFactory();
    }
    
    //获取全新的全新的sesession
    public static Session openSession(){
        return sessionFactory.openSession();
    }
    //获取和线程关联的session
    public static Session getCurrentSession(){
        Session session=threadLocal.get();
        //判断是否得到
        if(session==null || !session.isOpen()){
            session=sessionFactory.openSession();
            //把session对象设置到 threadLocal,相当于该session已经和线程绑定
            threadLocal.set(session);
        }
        return session;
    }
}
