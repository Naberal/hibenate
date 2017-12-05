package ProjectManagementSystem.controller;

import ProjectManagementSystem.dao.*;
import ProjectManagementSystem.model.*;
import ProjectManagementSystem.view.View;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Save {

    public void saveController(DAO dao) {
        try {
            if (dao.getClass().equals(CompanyDAO.class)) {
                dao.save(saveCompany());
            } else if (dao.getClass().equals(CustomerDAO.class)) {
                dao.save(saveCustomer());
            } else if (dao.getClass().equals(SkillDAO.class)) {
                dao.save(saveSkill());
            } else if (dao.getClass().equals(DeveloperDAO.class)) {
                dao.save(saveDeveloper());
            } else {
                dao.save(saveProject());
            }
        } catch (NullPointerException e) {
        }

    }

    private Company saveCompany() {
        System.out.println("Enter Name");
        Company company = new Company(View.scanner.next());
        Company company1 = new CompanyDAO().findByName(company.getName());
        if (company.getName().equals(company1.getName())) {
            System.out.println("it company already exist");
            return null;
        } else return company;
    }

    private Customer saveCustomer() {
        System.out.println("Enter Name");
        Customer customer = new Customer(View.scanner.next());
        Customer customer1 = new CustomerDAO().findByName(customer.getName());
        if (customer.getName().equals(customer1.getName())) {
            System.out.println("it customer already exist");
            return null;
        } else return customer;
    }

    private Skill saveSkill() {
        System.out.println("Enter skill");
        Skill skill = new Skill(View.scanner.next());
        Skill skill1 = new SkillDAO().findByName(skill.getName());
        if (skill.getName().equals(skill1.getName())) {
            System.out.println("it skill already exist");
            return null;
        } else return skill;
    }

    private Developer saveDeveloper() {
        Developer developer = new Developer();
        System.out.println("Enter FirstName");
        developer.setFirstName(View.scanner.next());
        System.out.println("Enter LastName");
        developer.setLastName(View.scanner.next());
        System.out.println("Enter Experience");
        developer.setExperience(View.numScanner());
        System.out.println("Enter Project(id)");
        int id = View.numScanner();
        while (true) {
            if (new ProjectDAO().getCount() < id) {
                System.out.println("wrong id");
                System.out.println("Enter again");
            } else {
                developer.setProject(new ProjectDAO().findByID(id));
                break;
            }
        }
        System.out.println("Enter Salary");
        developer.setSalary((double) View.numScanner());
        System.out.println("Enter Skills(id)");
        developer.setSkills(skillsList());
        Developer developer1=new DeveloperDAO().findByName(developer.getFirstName());
        if (developer.equals(developer1)) {
            System.out.println("it developer already exist");
            return null;
        } else
            return developer;
    }

    private List<Skill> skillsList() {
        List<Skill> list = null;
        int id = new SkillDAO().getCount();
        System.out.println("Enter Skills");
        while (View.scanner.hasNextInt()) {
            if (View.scanner.nextInt() > id) {
                System.err.println("wrong id");
                break;
            } else {
                list = new LinkedList<>();
                list.add(new SkillDAO().findByID(id));
                System.out.println("next one.(to exit. different meaning)");
            }
        }
        return list;
    }

    private Project saveProject() {
        Project project = new Project();
        System.out.println("Enter Name");
        project.setName(View.scanner.next());
        System.out.println("Enter Company (id)");
        project.setCompany(new CompanyDAO().findByID(View.numScanner()));
        System.out.println("Enter customer (id)");
        project.setCustomer(new CustomerDAO().findByID(View.numScanner()));
        System.out.println("Enter cost");
        project.setCost((double) View.numScanner());
        Project project1=new ProjectDAO().findByName(project.getName());
        if (project.equals(project1)) {
            System.out.println("it project already exist");
            return null;
        } else return project;
    }
}

