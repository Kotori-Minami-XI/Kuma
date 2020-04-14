package com.Kotori.test;


import com.Kotori.domain.Customer;
import lombok.SneakyThrows;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


public class HibernateTest {
    @Test @SneakyThrows
    public void test() {
        // 载入配置文件
        Configuration configure = new Configuration().configure();
        // 获取连接池
        SessionFactory sessionFactory = configure.buildSessionFactory();
        // 获取连接对象
        Session session = sessionFactory.openSession();

        Customer customer = new Customer();
        customer.setCust_name("lz");
        customer.setCust_phone("1232132131");

        // 保存对象
        session.save(customer);

        //关闭连接对象和连接池
        session.close();
        sessionFactory.close();
    }
}
