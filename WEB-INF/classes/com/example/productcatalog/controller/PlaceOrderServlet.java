package com.example.productcatalog.controller;

import com.example.productcatalog.model.Order;
import com.example.productcatalog.repository.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {
    private OrderRepository orderRepository = new OrderRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order cart = (Order) session.getAttribute("cart");

        if (cart != null) {
            try {
                orderRepository.saveOrder(cart);
                session.removeAttribute("cart"); // Clear session after order is placed
                response.sendRedirect("orderSuccess.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to place order.");
            }
        }
    }
}
