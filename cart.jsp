<%@ page import="com.example.productcatalog.model.OrderItem" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Order Cart</title>
</head>
<body>
    <h1>Your Cart</h1>
    <c:if test="${empty cart}">
        <p>Your cart is empty.</p>
    </c:if>
    <c:if test="${not empty cart}">
        <table border="1">
            <tr>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Total Price</th>
            </tr>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>${item.product.price}</td>
                    <td>${item.totalPrice}</td>
                </tr>
            </c:forEach>
        </table>
        <p>Total: ${cart.totalPrice}</p>
        <form action="PlaceOrderServlet" method="post">
            <input type="submit" value="Place Order">
        </form>
    </c:if>
</body>
</html>
