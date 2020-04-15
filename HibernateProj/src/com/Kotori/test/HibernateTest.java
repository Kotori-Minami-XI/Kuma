package com.Kotori.test;


import com.Kotori.domain.Customer;
import com.Kotori.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

public class HibernateTest {
    @Test
    public void testTransaction() {
        // 获取连接对象
        Session session = HibernateUtil.openSession();
        // 开启事务
        Transaction transaction = session.beginTransaction();

        // 对象变成 瞬时态
        Customer customer = new Customer();
        customer.setCust_name("lz");
        customer.setCust_phone("123");

        // 保存对象，对象变成 持久态
        session.save(customer);
        // 事务提交
        transaction.commit();
        //关闭连接对象和连接池 对象变成 游离态
        session.close();
    }


    /***
     * 查询某一条记录
     */
    @Test
    public void testQuery() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, 2L);
        System.out.println(customer);

        transaction.commit();
    }

    /***
     * 查询所有记录，采用HQL面向对象式的查询语句
     */
    @Test
    public void testQueryAll1(){
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from com.Kotori.domain.Customer");
        List<Customer> list = query.getResultList();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /***
     * 查询所有记录，采用SQL查询语句,不推荐
     */
    @Test
    public void testQueryAll2(){
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery query = session.createSQLQuery("select * from customer");

        List<Object[]> list = query.getResultList();
        for (Object[] objs : list) {
            System.out.println(Arrays.toString(objs));
        }

        transaction.commit();
    }

    /***
     * 直接创建对象修改，没有设置其他字段则会被设置成null,不推荐此方法
     */
    @Test
    public void testUpdate1() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_id(1L);
        customer.setCust_name("zy");

        session.update(customer);

        transaction.commit();
    }

    /***
     * 查询修改法 建议这种方法
     */
    @Test
    public void testUpdate2() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, 1L);
        customer.setCust_name("wc");

        session.update(customer);

        transaction.commit();
    }

    /***
     * 删除记录，直接创建对象后删除，不支持级联删除
     */
    @Test
    public void testDelete1() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_id(2L);

        session.delete(customer);

        transaction.commit();
    }

    /***
     * 删除记录，获取对象后删除，支持级联删除
     */
    @Test
    public void testDelete2() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, 2L);

        session.delete(customer);

        transaction.commit();
    }

    /***
     * 测试保存和更新
     * 没有设置id则为Insert操作
     * 设置了id则为update操作，id找不到就报错
     */
    @Test
    public void testSaveOrUpdate() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, 2L);

        session.delete(customer);

        transaction.commit();
    }


}
