package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Company;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CompanyDAO implements DAO<Company, Integer> {
    private final String SAVE = "INSERT INTO companies(companieName) VALUES(?)";
    private final String UPDATE = "UPDATE companies SET companieName=? WHERE idcompanies=?";
    private final String DELETE = "DELETE FROM  companies WHERE idcompanies=?";
    private final String FIND_BY_ID = "SELECT * FROM  companies WHERE idcompanies = ?";
    private final String FIND_BY_NAME = "SELECT * FROM  companies WHERE companieName = ?";
    private final String GET_ALL = "SELECT * FROM  companies";
    private final String COUNT = "SELECT COUNT(*) FROM companies";
    private DBConnection connection = new DBConnection();
    private int lasId;

    @Override
    public int getCount( ) {
        lastId();
        return lasId;
    }

    private void lastId( ) {
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.prepareStatement(COUNT)) {
            ResultSet resultSet = statement.executeQuery(COUNT);
            resultSet.next();
            lasId = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Company company) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Company company) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getId());
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

    Company company;

    @Override
    public Company findByID(Integer integer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            company = company(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public Company findByName(String name) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            company = company(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public List<Company> getAll( ) {
        List<Company> list = new LinkedList<>();
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                list.add(company = new Company(resultSet.getInt("idcompanies"),
                        resultSet.getString("companieName")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Company company(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            company = new Company(resultSet.getInt("idcompanies"),
                    resultSet.getString("companieName"));
        }
        return company;
    }
}
