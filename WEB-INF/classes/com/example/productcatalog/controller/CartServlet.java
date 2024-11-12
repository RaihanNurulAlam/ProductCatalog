package com.example.productcatalog.controller;

import com.example.productcatalog.model.Order;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = productRepository.findProductById(productId);

        HttpSession session = request.getSession();
        Order cart = (Order) session.getAttribute("cart");
        if (cart == null) {
            cart = new Order();
            session.setAttribute("cart", cart);
        }
        cart.addItem(product, quantity);

        response.sendRedirect("cart.jsp"); // Redirect to cart view
    }
}
