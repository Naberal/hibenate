package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Customer;
import ProjectManagementSystem.model.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SkillDAO implements DAO<Skill, Integer> {
    private SessionFactory sessionFactory;
    private Skill skill;

    public SkillDAO( ) {
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
            List<Integer> maxID = session.createQuery("SELECT max(id) FROM Skill ").list();
            lasId = maxID.get(0);
        }
    }

    @Override
    public void save(Skill skill) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(skill);
            transaction.commit();
        }
    }

    @Override
    public void update(Skill skill) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            this.skill = session.get(Skill.class, skill.getId());
            this.skill.setName(skill.getName());
            session.update(this.skill);
            transaction.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            skill = session.get(Skill.class, id);
            session.delete(skill);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Company with id " + id + " not exist");
        }
    }

    @Override
    public Skill findByID(Integer integer) {
        try (Session session = sessionFactory.openSession()) {
            skill = session.get(Skill.class, integer);
        }
        return skill;
    }

    @Override
    public Skill findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<Skill> skills = session.createQuery("from Skill where name like :name")
                    .setParameter("name", name).list();
            skill = skills.get(0);
        }
        return skill;
    }

    @Override
    public List<Skill> getAll( ) {
        List<Skill> list = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("from Skill").list();
            transaction.commit();
        }
        return list;
    }
}
      