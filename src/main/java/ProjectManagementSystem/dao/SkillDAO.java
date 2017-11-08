package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Skill;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SkillDAO implements DAO<Skill, Integer> {
    private final String SAVE = "INSERT INTO skill(skill) VALUES(?)";
    private final String UPDATE = "UPDATE skill SET skill=? WHERE idskill=?";
    private final String DELETE = "DELETE FROM skill WHERE idskill=?";
    private final String FIND_BY_ID = "SELECT * FROM skill WHERE idskill = ?";
    private final String FIND_BY_NAME = "SELECT * FROM skill WHERE skill = ?";
    private final String GET_ALL = "SELECT * FROM skill";
    private final String COUNT = "SELECT COUNT(*) FROM skill";
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
    public void save(Skill skill) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Skill skill) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setInt(2, skill.getId());
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

    Skill skill = new Skill();

    @Override
    public Skill findByID(Integer integer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            skill = skill(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public Skill findByName(String name) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            skill = skill(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public List<Skill> getAll( ) {
        List<Skill> list = new LinkedList<>();
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                list.add(skill = new Skill(resultSet.getString(1), resultSet.getInt(2)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Skill skill(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            skill = new Skill(resultSet.getString(1), resultSet.getInt(2));
        }
        return skill;
    }
}
      