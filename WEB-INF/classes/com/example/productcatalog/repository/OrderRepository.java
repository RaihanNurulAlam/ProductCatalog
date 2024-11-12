package com.example.productcatalog.repository;

import com.example.productcatalog.model.Order;
import com.example.productcatalog.model.OrderItem;
import com.example.productcatalog.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderRepository {

    public void saveOrder(Order order) throws SQLException {
        String insertOrderQuery = "INSERT INTO orders (total_price) VALUES (?)";
        String insertOrderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderItemStmt = conn.prepareStatement(insertOrderItemQuery)) {

            conn.setAutoCommit(false); // Start transaction

            // Insert order and get generated order ID
            orderStmt.setDouble(1, order.getTotalPrice());
            orderStmt.executeUpdate();
            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                // Insert order items
                for (OrderItem item : order.getItems()) {
                    orderItemStmt.setInt(1, orderId);
                    orderItemStmt.setInt(2, item.getProduct().getId());
                    orderItemStmt.setInt(3, item.getQuantity());
                    orderItemStmt.setDouble(4, item.getTotalPrice());
                    orderItemStmt.addBatch();
                }
                orderItemStmt.executeBatch();
                conn.commit(); // Commit transaction
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
