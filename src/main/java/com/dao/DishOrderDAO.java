package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.DataSource;
import com.model.DishOrder;

/**
 * DAO pour gérer les plats commandés.
 */
public class DishOrderDAO {
    private final DataSource dataSource;

    public DishOrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<DishOrder> findByOrderId(Long orderId) {
        List<DishOrder> dishOrders = new ArrayList<>();
        String query = "SELECT id, name, quantity_to_order, price FROM dishOrder WHERE id_order = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                dishOrders.add(new DishOrder(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("quantity_to_order"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dishOrders;
    }
}
