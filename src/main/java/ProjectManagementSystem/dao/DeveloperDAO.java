package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Developer;
import ProjectManagementSystem.model.Project;
import ProjectManagementSystem.model.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DeveloperDAO implements DAO<Developer, Integer> {
    private SessionFactory sessionFactory;
    private Developer developer;

    public DeveloperDAO( ) {
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
            List<Integer> maxID = session.createQuery("SELECT max(id) FROM Developer ").list();
            lasId = maxID.get(0);
        }
    }

    @Override
    public void save(Developer developer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(developer);
            transaction.commit();
        }
    }

    @Override
    public void update(Developer developer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            this.developer = session.get(Developer.class, developer.getId());
            this.developer.setFirstName(developer.getFirstName());
            this.developer.setLastName(developer.getLastName());
            this.developer.setExperience(developer.getExperience());
            this.developer.setProject(developer.getProject());
            this.developer.setSalary(developer.getSalary());
            this.developer.setSkills(developer.getSkills());
            session.update(this.developer);
            transaction.commit();
        }
    }


    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            developer = session.get(Developer.class, id);
            session.delete(developer);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Company with id " + id + " not exist");
        }
    }

    @Override
    public Developer findByID(Integer integer) {
        try (Session session = sessionFactory.openSession()) {
            developer = session.get(Developer.class, integer);
        }
        return developer;
    }

    @Override
    public Developer findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<Developer> developers = session.createQuery("from Developer where firstName like :name")
                    .setParameter("name", name).list();
            developer = developers.get(0);
        }
        return developer;
    }

    @Override
    public List<Developer> getAll( ) {
        List<Developer> list = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("from Developer").list();
            transaction.commit();
        }
        return list;
    }
}
