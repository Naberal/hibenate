package ProjectManagementSystem.dao;

import ProjectManagementSystem.model.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CompanyDAO implements DAO<Company, Integer> {
    private SessionFactory sessionFactory;
    private Company company;

    public CompanyDAO( ) {
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
            List<Integer> maxID = session.createQuery("SELECT max(id) FROM Company ").list();
            lasId = maxID.get(0);
        }
    }

    @Override
    public void save(Company company) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }

    @Override
    public void update(Company company) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            this.company = session.get(Company.class, company.getId());
            this.company.setName(company.getName());
            session.update(this.company);
            transaction.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            session.delete(company);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Company with id " + id + " not exist");
        }
    }

    @Override
    public Company findByID(Integer integer) {
        try (Session session = sessionFactory.openSession()) {
            company = session.get(Company.class, integer);
            return company;
        }
    }

    @Override
    public Company findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<Company> companys = session.createQuery("from Company where name like :name")
                    .setParameter("name", name).list();
            company = companys.get(0);
        }
        return company;
    }

    @Override
    public List<Company> getAll( ) {
        List<Company> list = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("from Company").list();
            transaction.commit();
        }
        return list;
    }
}
