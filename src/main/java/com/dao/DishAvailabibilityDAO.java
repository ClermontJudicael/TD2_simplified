package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.DataSource;
import com.model.DishAvailability;

public class DishAvailabibilityDAO {
    private DataSource dataSource;

    public DishAvailabibilityDAO(DataSource datasource) {
        this.dataSource = datasource;
    }

    public DishAvailabibilityDAO() {
        this.dataSource = new DataSource();
    }

        public List<DishAvailability> findAll() {
        List<DishAvailability> dishList = new ArrayList<>();
        String query = "SELECT id, name, available_stock FROM dish_availability";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Double availability = resultSet.getDouble("available_stock");

                dishList.add(new DishAvailability(name, availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving dish availability", e);
        }
        return dishList;
    }

    // find dish by name
    public DishAvailability findByName(String name) {
        String query = "SELECT id, name, available_stock FROM dish_availability WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return new DishAvailability(resultSet.getString("name"), resultSet.getDouble("available_stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStock(DishAvailability dish) {
        String query = "UPDATE dish_availability SET available_stock = ? WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setDouble(1, dish.getAvailability());
            statement.setString(2, dish.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
