<%@ page import="com.example.productcatalog.model.Product" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Catalog</title>
</head>
<body>
    <h1>Product Catalog</h1>
    <table border="1">
        <tr>
            <th>No</th>
            <th>Product Name</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        <c:forEach var="product" items="${productList}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>
                    <form action="CartServlet" method="post">
                        <input type="hidden" name="productId" value="${product.id}">
                        Quantity: <input type="number" name="quantity" value="1" min="1">
                        <input type="submit" value="Add to Cart">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
