package com.Kotori.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static final SessionFactory sessionFactory;
    static {
        // 载入配置文件
        Configuration configure = new Configuration().configure();
        // 获取连接池
        sessionFactory = configure.buildSessionFactory();
    }

    // 获取一个连接对象
    public static Session openSession() {
        return sessionFactory.openSession();
    }

    // 获取当前线程的Session, 一般采用此种方式， 采用此种方式不需要调用close
    // 需要在核心配置文件中加入 <property name="current_session_context_class">thread</property>
    public static Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
