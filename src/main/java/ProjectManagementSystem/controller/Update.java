package ProjectManagementSystem.controller;

import ProjectManagementSystem.dao.*;
import ProjectManagementSystem.model.*;
import ProjectManagementSystem.view.View;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Update {
    private Project project;
    private int id;
    private Developer developer;

    public void updateController(DAO dao) {
        try {
            if (dao.getClass().equals(CompanyDAO.class)) {
                dao.update(updateCompany());
            } else if (dao.getClass().equals(CustomerDAO.class)) {
                dao.update(updateCustomer());
            } else if (dao.getClass().equals(SkillDAO.class)) {
                dao.update(updateSkill());
            } else if (dao.getClass().equals(DeveloperDAO.class)) {
                dao.update(updateDeveloper());
            } else {
                dao.update(updateProject());
            }
        } catch (NullPointerException e) {
            System.err.println("thea are now searching value with id");
        }
    }

    private Project updateProject( ) {
        DAO dao = new ProjectDAO();
        System.out.println("Enter project Id");
        id = View.numScanner();
        if (dao.getCount() < id) {
            System.err.println("wrong id");
        } else {
            project = (Project) dao.findByID(id);
            project = newDataProject(project);

        }
        return project;
    }

    private Developer updateDeveloper( ) {
        DAO dao = new DeveloperDAO();
        System.out.println("Enter developer Id");
        id = View.numScanner();
        if (dao.getCount() < id) {
            System.err.println("wrong id");
        } else {
            developer = (Developer) dao.findByID(id);
            developer = newDataDeveloper(developer);
        }
        return developer;
    }


    private Skill updateSkill( ) {
        Skill skill = null;
        DAO dao = new SkillDAO();
        System.out.println("enter skill ID");
        id = View.numScanner();
        if (dao.getCount() < id) {
            System.err.println("wrong id");
        } else {
            skill = (Skill) dao.findByID(id);
            System.out.println("Enter new skill name");
            skill.setName(View.scanner.next());
        }
        return skill;
    }

    private Customer updateCustomer( ) {
        Customer customer = null;
        DAO dao = new CustomerDAO();
        System.out.println("enter customer ID");
        id = View.numScanner();
        if (dao.getCount() < id) {
            System.err.println("wrong id");
        } else {
            customer = (Customer) dao.findByID(id);
            System.out.println("Enter new customer name");
            customer.setName(View.scanner.next());
        }
        return customer;
    }

    private Company updateCompany( ) {
        Company company = null;
        DAO dao = new CompanyDAO();
        System.out.println("Enter company Id");
        id = View.numScanner();
        if (dao.getCount() < id ) {
            System.err.println("wrong id");
        } else {
            company = (Company) dao.findByID(id);
            System.out.println("Enter new company name");
            company.setName(View.scanner.next());
        }
        return company;
    }

    private Developer newDataDeveloper(Developer developer) {
        while (true) {
            System.out.println("what you want to updata?");
            System.out.println("1. FirstName");
            System.out.println("2. LastName");
            System.out.println("3. Experience ");
            System.out.println("4. Project");
            System.out.println("5. Salary");
            System.out.println("6. Skills");
            System.out.println("exit. different meaning");
            int operation = View.numScanner();
            if (operationsForUpDataDev(operation)) break;
        }
        return developer;
    }

    private boolean operationsForUpDataDev(int operation) {
        if (operation == 1) {
            System.out.println("Enter FirstName");
            developer.setFirstName(View.scanner.next());
        } else if (operation == 2) {
            System.out.println("Enter LastName");
            developer.setLastName(View.scanner.next());
        } else if (operation == 3) {
            System.out.println("Enter Experience");
            developer.setExperience(View.numScanner());
        } else if (operation == 4) {
            System.out.println("Enter Project (id)");
            developer.setProject(new ProjectDAO().findByID(View.numScanner()));
        } else if (operation == 5) {
            System.out.println("Enter Salary");
            developer.setSalary((double) View.numScanner());
        } else if (operation == 6) {
            developer.setSkills(skillsList());
        } else return true;
        return false;
    }

    private Project newDataProject(Project project) {
        while (true) {
            System.out.println("what you want to updata?");
            System.out.println("1. ProjectName");
            System.out.println("2. Company");
            System.out.println("3. customer ");
            System.out.println("4. cost");
            System.out.println("exit. different meaning");
            int operation = View.numScanner();
            if (operationsForUpDataProj(operation)) break;
        }
        return project;
    }

    private boolean operationsForUpDataProj(int operation) {
        if (operation == 1) {
            System.out.println("Enter ProjectName");
            project.setName(View.scanner.next());
        } else if (operation == 2) {
            System.out.println("Enter Company (id)");
            project.setCompany(new CompanyDAO().findByID(View.numScanner()));
        } else if (operation == 3) {
            System.out.println("Enter Customer");
            project.setCustomer(new CustomerDAO().findByID(View.numScanner()));
        } else if (operation == 4) {
            System.out.println("Enter Project (id)");
            project.setCost((double) View.numScanner());
        } else return true;
        return false;
    }

    private Set<Skill> skillsList( ) {
        DAO dao = new SkillDAO();
        Set<Skill> list = null;
        id = dao.getCount();
        System.out.println("Enter Skills(id)");
        while (View.scanner.hasNextInt()) {
            if (View.scanner.nextInt() > id) {
                System.err.println("wrong id");
                break;
            } else {
                list = new HashSet<>();
                list.add((Skill) dao.findByID(id));
                System.out.println("next one.(to exit. different meaning)");
            }
        }
        return list;
    }
}