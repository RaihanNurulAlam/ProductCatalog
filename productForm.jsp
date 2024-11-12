<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Form</title>
</head>
<body>
    <h1>${product == null ? "Add Product" : "Edit Product"}</h1>

    <form action="ProductServlet" method="post">
        <input type="hidden" name="id" value="${product.id}" />

        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${product.name}" required /><br><br>

        <label for="type">Type:</label>
        <input type="text" id="type" name="type" value="${product.type}" required /><br><br>

        <label for="price">Price:</label>
        <input type="number" id="price" name="price" step="0.01" value="${product.price}" required /><br><br>

        <button type="submit" name="action" value="${product == null ? 'insert' : 'update'}">
            ${product == null ? "Save" : "Update"}
        </button>
    </form>
</body>
</html>
