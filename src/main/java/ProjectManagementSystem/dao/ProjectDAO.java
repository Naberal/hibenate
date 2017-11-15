package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Project;
import ProjectManagementSystem.model.Skill;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProjectDAO implements DAO<Project, Integer> {
    private SessionFactory sessionFactory;
    private Project project;

    public ProjectDAO( ) {
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
            List<Integer> maxID = session.createQuery("SELECT max(id) FROM Project ").list();
            lasId = maxID.get(0);
        }
    }


    @Override
    public void save(Project project) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Project project) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            this.project = session.get(Project.class, project.getId());
            this.project.setName(project.getName());
            this.project.setCustomer(project.getCustomer());
            this.project.setCompany(project.getCompany());
            this.project.setCost(project.getCost());
            session.update(this.project);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            project = session.get(Project.class, id);
            session.delete(project);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Project findByID(Integer integer) {
        try (Session session = sessionFactory.openSession()) {
            project = session.get(Project.class, integer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public Project findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<Project> projects = session.createQuery("select p from Project p where p.name like: name")
                    .setParameter("name", name)
                    .list();
            project = projects.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public List<Project> getAll( ) {
        List<Project> projects = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            projects = session.createQuery("from Project").list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projects;
    }
}