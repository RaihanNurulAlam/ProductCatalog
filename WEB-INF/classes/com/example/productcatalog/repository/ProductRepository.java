package com.example.productcatalog.repository;

import com.example.productcatalog.model.Product;
import com.example.productcatalog.model.Order;
import com.example.productcatalog.model.OrderItem;
import com.example.productcatalog.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // Method to get all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                products.add(new Product(id, name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Method to find a product by its ID
    public Product findProductById(int id) {
        Product product = null;
        String query = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    product = new Product(id, name, price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // Method to save an order in the database
    public void saveOrder(Order order) throws SQLException {
        String orderQuery = "INSERT INTO orders (total_price) VALUES (?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderItemStmt = conn.prepareStatement(orderItemQuery)) {

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
            throw e; // Ensure the exception is propagated
        }
    }

	public void saveProductsBatch(List<Product> products) {
		// TODO Auto-generated method stub
		
	}
}
