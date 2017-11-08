package ProjectManagementSystem.controller;

import ProjectManagementSystem.dao.*;
import ProjectManagementSystem.model.*;
import ProjectManagementSystem.view.View;

import java.util.LinkedList;
import java.util.List;

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
            System.out.println("");
        }
    }

    private Project updateProject( ) {
        System.out.println("Enter project Id");
        id = View.numScanner();
        if (new ProjectDAO().getCount() < id) {
            System.err.println("wrong id");
        } else {
            project = new ProjectDAO().findByID(id);
            project = newDataProject(project);

        }
        return project;
    }

    private Developer updateDeveloper( ) {
        System.out.println("Enter developer Id");
        id = View.numScanner();
        if (new DeveloperDAO().getCount() < id) {
            System.err.println("wrong id");
        } else {
            developer = new DeveloperDAO().findByID(id);
            developer = newDataDeveloper(developer);
        }
        return developer;
    }


    private Skill updateSkill( ) {
        Skill skill = null;
        System.out.println("enter skill ID");
        id = View.numScanner();
        if (new SkillDAO().getCount() < id) {
            System.err.println("wrong id");
        } else {
            skill = new SkillDAO().findByID(id);
            System.out.println("Enter new skill name");
            skill.setName(View.scanner.next());
        }
        return skill;
    }

    private Customer updateCustomer( ) {
        Customer customer = null;
        System.out.println("enter customer ID");
        id = View.numScanner();
        if (new CustomerDAO().getCount() < id) {
            System.err.println("wrong id");
        } else {
            customer = new CustomerDAO().findByID(View.numScanner());
            System.out.println("Enter new customer name");
            customer.setName(View.scanner.next());
        }
        return customer;
    }

    private Company updateCompany( ) {
        Company company = null;
        System.out.println("Enter company Id");
        id = View.numScanner();
        if (new CompanyDAO().getCount() < id) {
            System.err.println("wrong id");
        } else {
            company = new CompanyDAO().findByID(View.numScanner());
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

    private List<Skill> skillsList( ) {
        List<Skill> list = null;
        id = new SkillDAO().getCount();
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
}