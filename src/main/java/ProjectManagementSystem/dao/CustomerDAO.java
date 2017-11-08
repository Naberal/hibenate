package ProjectManagementSystem.dao;

import ProjectManagementSystem.connection.DBConnection;
import ProjectManagementSystem.model.Customer;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDAO implements DAO<Customer, Integer> {
    private final String SAVE = "INSERT INTO customers(customerName) VALUES(?)";
    private final String UPDATE = "UPDATE customers SET customerName=? WHERE idcustomers=?";
    private final String DELETE = "DELETE FROM customers WHERE idcustomers=?";
    private final String FIND_BY_ID = "SELECT * FROM customers WHERE idcustomers = ?";
    private final String FIND_BY_NAME = "SELECT * FROM customers WHERE customerName = ?";
    private final String GET_ALL = "SELECT * FROM customers";
    private final String COUNT = "SELECT COUNT(*) FROM customers";
    private DBConnection connection = new DBConnection();
    private int lastID;

    @Override
    public int getCount( ) {
        lastId();
        return lastID;
    }

    private void lastId( ) {
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.prepareStatement(COUNT)) {
            ResultSet resultSet = statement.executeQuery(COUNT);
            resultSet.next();
            lastID = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Customer customer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setInt(2, customer.getId());
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

    Customer customer;

    @Override
    public Customer findByID(Integer integer) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, integer);
            ResultSet resultSet = preparedStatement.executeQuery();
            customer = customer(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer findByName(String name) {
        try (Connection connection = this.connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            customer = customer(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> getAll( ) {
        List<Customer> list = new LinkedList<>();
        try (Connection connection = this.connection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                list.add(customer = new Customer(resultSet.getInt(1), resultSet.getString(2)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Customer customer(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            customer = new Customer(resultSet.getInt(1), resultSet.getString(2));
        }
        return customer;
    }
}
