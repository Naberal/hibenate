package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Developer;
import ProjectManagementSystem.model.Skill;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DeveloperDAO implements DAO<Developer, Integer> {
    private final String SAVE = "INSERT INTO developer(firstName, lastName, experianse, workOnProjectid, selery) " +
            "VALUES(?,?,?,?,?)";
    private final String SAVE_SKILLS = "INSERT INTO developersskills(developer, Skill) VALUES (?,?)";
    private final String UPDATE = "UPDATE developer SET firstName=?,lastName=?, experianse=?, workOnProjectid=?," +
            "selery=? WHERE iddeveloper=?";
    private final String DELETE = "DELETE FROM developer WHERE iddeveloper=?";
    private final String DELETE_SKILLS = "DELETE FROM developersskills WHERE developer = ?";
    private final String FIND_BY_ID = "SELECT * FROM developer WHERE iddeveloper = ?";
    private final String FIND_BY_NAME = "SELECT * FROM developer WHERE firstName = ? AND lastName=?";
    private final String GET_SKILLS = "SELECT * FROM skill " +
            "JOIN developersskills ON skill.idskill = developersskills.Skill " +
            "JOIN developer ON developersskills.developer = developer.iddeveloper " +
            "WHERE developer.iddeveloper=?";
    private final String GET_ALL = "SELECT * FROM developer";
    private final String COUNT = "SELECT COUNT(*) FROM developer";
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
    public void save(Developer developer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE);) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setInt(3, developer.getExperience());
            preparedStatement.setInt(4, developer.getProject().getId());
            preparedStatement.setDouble(5, developer.getSalary());
            preparedStatement.execute();
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(SAVE_SKILLS)) {
                for (Skill skill : developer.getSkills()) {
                    preparedStatement1.setInt(1, developer.getId());
                    preparedStatement1.setInt(2, skill.getId());
                }
                preparedStatement1.execute();
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Developer developer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setInt(3, developer.getExperience());
            preparedStatement.setInt(4, developer.getProject().getId());
            preparedStatement.setDouble(5, developer.getSalary());
            preparedStatement.setInt(6, developer.getId());
            preparedStatement.execute();
            try (PreparedStatement statement = connection.prepareStatement(DELETE_SKILLS)) {
                statement.setInt(1, developer.getId());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement(SAVE_SKILLS)) {
                for (Skill skill : developer.getSkills()) {
                    statement.setInt(1, developer.getId());
                    statement.setInt(2, skill.getId());
                    statement.addBatch();
                }
            }
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
            try (PreparedStatement statement = connection.prepareStatement(DELETE_SKILLS)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Developer developer;

    @Override
    public Developer findByID(Integer integer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            developer = developer(resultSet);
            resultSet.close();
            developer.setSkills(skills(connection, integer));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public Developer findByName(String name) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            developer = developer(resultSet);
            resultSet.close();
            developer.setSkills(skills(connection, developer.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public List<Developer> getAll( ) {
        List<Developer> list = new LinkedList<>();
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                list.add(developer = new Developer(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4),
                        new ProjectDAO().findByID(resultSet.getInt(5)), resultSet.getDouble(6)));
                developer.setSkills(skills(connection, developer.getId()));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Developer developer(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            developer = new Developer(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getInt(4),
                    new ProjectDAO().findByID(resultSet.getInt(5)), resultSet.getDouble(6));
        }
        return developer;
    }

    private List<Skill> skills(Connection connection, Integer integer) throws SQLException {
        List<Skill> skills = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_SKILLS)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setId(resultSet.getInt(2));
                skill.setName(resultSet.getString(1));
                skills.add(skill);
            }
            resultSet.close();
        }
        return skills;
    }
}
