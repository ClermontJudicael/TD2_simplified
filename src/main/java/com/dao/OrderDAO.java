package com.dao;

import com.DataSource;
import com.model.Order;
import com.model.TableNumber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrderDAO {
    private final DataSource dataSource;
    private final DishOrderDAO dishOrderDao;

    public OrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.dishOrderDao = new DishOrderDAO(dataSource);
    }

    public Order findById(Long orderId) {
        String query = "SELECT id, table_number, amount_paid, amount_due, customer_arrival_date_time FROM \"order\" WHERE id = ?";
        Order order = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order(
                        rs.getLong("id"),
                        TableNumber.valueOf(rs.getString("table_number")),
                        rs.getDouble("amount_paid"),
                        rs.getDouble("amount_due"),
                        rs.getTimestamp("customer_arrival_date_time").toInstant(),
                        new ArrayList<>()
                );
                // Récupérer les plats associés à la commande
                order.setDishOrderList(dishOrderDao.findByOrderId(orderId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}
