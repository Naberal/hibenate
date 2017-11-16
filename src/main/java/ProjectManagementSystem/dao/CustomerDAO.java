package ProjectManagementSystem.dao;


import ProjectManagementSystem.model.Company;
import ProjectManagementSystem.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CustomerDAO implements DAO<Customer, Integer> {
    private SessionFactory sessionFactory;
    private Customer customer;

    public CustomerDAO( ) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    private int lasId;

    @Override
    public int getCount( ) {
        lastId();
        return lasId;
    }

    private void lastId( ) {
        try (Session session = sessionFactory.openSession()) {
            List<Integer> maxID = session.createQuery("SELECT max(id) FROM Customer ").list();
            lasId = maxID.get(0);
        }
    }

    @Override
    public void save(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        }
    }

    @Override
    public void update(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            this.customer = session.get(Customer.class, customer.getId());
            this.customer.setName(customer.getName());
            session.update(this.customer);
            transaction.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            customer = session.get(Customer.class, id);
            session.delete(customer);
            transaction.commit();
        }catch (Exception e) {
            System.out.println("Company with id " + id + " not exist");
        }
    }

    @Override
    public Customer findByID(Integer integer) {
        try (Session session = sessionFactory.openSession()) {
            customer = session.get(Customer.class, integer);
        }
        return customer;
    }

    @Override
    public Customer findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<Customer> customers = session.createQuery("from Customer where name like :name")
                    .setParameter("name", name).list();
            customer = customers.get(0);
        }
        return customer;
    }

    @Override
    public List<Customer> getAll( ) {
        List<Customer> list = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("from Customer").list();
            transaction.commit();
        }
        return list;
    }
}
