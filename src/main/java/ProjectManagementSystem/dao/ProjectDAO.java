package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Project;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProjectDAO implements DAO<Project, Integer> {
    private final String SAVE = "INSERT INTO projects(projectName, idcostomer, idcompanie, cost) VALUES(?,?,?,?)";
    private final String UPDATE = "UPDATE projects SET projectsName=?,idcostomer=?, idcompanie=?,cost=? " +
            "WHERE idproject=?";
    private final String DELETE = "DELETE FROM projects WHERE  idproject=?";
    private final String FIND_BY_ID = "SELECT * FROM projects WHERE  idproject = ?";
    private final String FIND_BY_NAME = "SELECT * FROM projects WHERE projectsName = ?";
    private final String GET_ALL = "SELECT * FROM projects";
    private final String COUNT = "SELECT COUNT(*) FROM projects";
    private DBConnection connection = new DBConnection();
    private int lastId;

    @Override
    public int getCount( ) {
        lastId();
        return lastId;
    }

    private void lastId( ) {
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.prepareStatement(COUNT)) {
            ResultSet resultSet = statement.executeQuery(COUNT);
            resultSet.next();
            lastId = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Project project) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setInt(2, project.getCustomer().getId());
            preparedStatement.setInt(3, project.getCompany().getId());
            preparedStatement.setDouble(4, project.getCost());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Project project) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setInt(2, project.getCustomer().getId());
            preparedStatement.setInt(3, project.getCompany().getId());
            preparedStatement.setDouble(4, project.getCost());
            preparedStatement.setInt(5, project.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Project project;

    @Override
    public Project findByID(Integer integer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            project = project(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public Project findByName(String name) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            project = project(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public List<Project> getAll( ) {
        List<Project> projects = new LinkedList<>();
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                projects.add(project = new Project(resultSet.getInt(1),
                        resultSet.getString(2),
                        new CompanyDAO().findByID(resultSet.getInt(4)),
                        new CustomerDAO().findByID(resultSet.getInt(3)),
                        resultSet.getDouble(5)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    private Project project(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            project = new Project(resultSet.getInt(1),
                    resultSet.getString(2),
                    new CompanyDAO().findByID(resultSet.getInt(4)),
                    new CustomerDAO().findByID(resultSet.getInt(3)),
                    resultSet.getDouble(5));
        }
        return project;
    }
}