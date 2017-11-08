package ProjectManagementSystem.view;

import ProjectManagementSystem.controller.Controller;
import ProjectManagementSystem.dao.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    public static Scanner scanner = new Scanner(System.in);

    public static int numScanner( ) {
        int num = 0;
        try {
            num = scanner.nextInt();
            if (num <= 0) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.err.println("enter the positive integer");
            scanner.next();
            num = numScanner();
        }
        return num;
    }

    public void tableSwitch( ) {
        while (true) {
            tablesList();
            int table = numScanner();
            if (table == 1) {
                switchOperation(new CompanyDAO());
            } else if (table == 2) {
                switchOperation(new CustomerDAO());
            } else if (table == 3) {
                switchOperation(new DeveloperDAO());
            } else if (table == 4) {
                switchOperation(new ProjectDAO());
            } else if (table == 5) {
                switchOperation(new SkillDAO());
            } else break;
        }
    }

    private void switchOperation(Object DAOClass) {
        Controller controller = new Controller();
        while (true) {
            operation();
            int operation = numScanner();
            if (operation == 1) {
                controller.save(DAOClass);
            } else if (operation == 2) {
                controller.update(DAOClass);
            } else if (operation == 3) {
                System.out.println("enter ID");
                controller.delete(numScanner(), DAOClass);
            } else if (operation == 4) {
                System.out.println("enter ID");
                controller.findByID(numScanner(), DAOClass);
            } else if (operation == 5) {
                System.out.println("enter Name");
                controller.findByName(scanner.next(), DAOClass);
            } else if (operation == 6) {
                controller.getAll(DAOClass);
            } else break;
        }
    }

    private static void tablesList( ) {
        System.out.println("select table");
        System.out.println("1: Company");
        System.out.println("2: Customer");
        System.out.println("3: Developer");
        System.out.println("4: Project");
        System.out.println("5: Skill");
        System.out.println("exit. different meaning");
    }

    private static void operation( ) {
        System.out.println(" now select operation");
        System.out.println("1. add new");
        System.out.println("2. update");
        System.out.println("3. delete");
        System.out.println("4. find by ID");
        System.out.println("5. find by name");
        System.out.println("6. show all");
        System.out.println("exit. different meaning");
    }


}
