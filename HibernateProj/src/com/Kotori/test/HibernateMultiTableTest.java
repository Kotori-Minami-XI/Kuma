package com.Kotori.test;

import com.Kotori.domain.Customer;
import com.Kotori.domain.Linkman;
import com.Kotori.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class HibernateMultiTableTest {

    /***
     * 通过双向维护添加关联
     */
    @Test
    public void testAddRelation(){
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer1 = new Customer();
        customer1.setCust_name("customer1");
        Customer customer2 = new Customer();
        customer2.setCust_name("customer2");
        Customer customer3 = new Customer();
        customer3.setCust_name("customer3");

        Linkman linkman1 = new Linkman();
        linkman1.setLink_name("linkman1");
        Linkman linkman2 = new Linkman();
        linkman2.setLink_name("linkman2");
        Linkman linkman3 = new Linkman();
        linkman3.setLink_name("linkman3");

        // 添加关系
        customer1.getLinkmans().add(linkman1);
        customer1.getLinkmans().add(linkman2);
        customer2.getLinkmans().add(linkman3);

        linkman1.setCustomer(customer1);
        linkman2.setCustomer(customer1);
        linkman3.setCustomer(customer2);

        // 双向保存，否则会有瞬时态异常
        // 如果想使用单向的保存，则需要配置 cascade="save-update"
        session.save(customer1);
        session.save(customer2);
        session.save(customer3);

        session.save(linkman1);
        session.save(linkman2);
        session.save(linkman3);

        transaction.commit();
    }

    /***
     * 测试多表查询
     */
    @Test
    public void testMultiTableQuery() {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Linkman linkman = session.get(Linkman.class, 1L);
        transaction.commit();

        // 需要去掉懒加载，否则报错
        System.out.println(linkman.getCustomer().getCust_name());
    }

    /***
     * 删除操作
     */
    @Test
    public void testDelete(){
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 使用默认删除（非级联删除）会让表中的外键断开，再删除元素
        // 所以需要配置 cascade="save-update,delete" 来使能级联删除（即关联对象也被删除）
        Customer customer = session.get(Customer.class, 1L);
        session.delete(customer);

        transaction.commit();
    }

    /***
     * 更新操作
     */
    @Test
    public void testUpdate(){
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Linkman linkman1 = session.get(Linkman.class, 1L);
        Customer customer3 = session.get(Customer.class, 3L);

        // 双向维护
        // 需要一方放弃维护外键，原则是谁拥有外键谁维护。不维护外键的一方需要配置inverse="true"
        // customer3.getLinkmans().add(linkman1);
        // linkman1.setCustomer(customer3);

        // 单向维护 注意linkman不要放弃维护外键否则改不了值
        linkman1.setCustomer(customer3);

        transaction.commit();
    }





}
