package ProjectManagementSystem;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.view.View;


public class Run {
    public static void main(String[] args) {
        View view =new View();
        view.tableSwitch();

    }
}
