<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product Upload Servlet</title>
</head>
<body>
<form action="ProductUploadServlet" method="post" enctype="multipart/form-data">
    <label for="file">Upload Product Data (product_data.txt):</label>
    <input type="file" name="file" id="file" required />
    <button type="submit">Upload</button>
</form>

<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>

</body>
</html>