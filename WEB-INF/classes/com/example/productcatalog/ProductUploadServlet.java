package com.example.productcatalog;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.example.productcatalog.model.Product;
import com.example.productcatalog.repository.ProductRepository;

@WebServlet("/ProductUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5)
public class ProductUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductRepository productRepository = new ProductRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("productUploadForm.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");  // Mendapatkan file yang di-upload
        String fileName = filePart.getSubmittedFileName();
        
        if (filePart != null && fileName != null && filePart.getSize() > 0) {
            // Menyiapkan nama file cadangan dengan timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(0));
            String backupFileName = "product_data_" + timestamp + ".txt";

            // Mendapatkan path folder untuk menyimpan file di dalam WEB-INF/uploaded
            String uploadPath = getServletContext().getRealPath("/WEB-INF/uploaded/");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();  // Membuat folder jika belum ada
            }

            // Menyimpan file ke dalam folder yang sudah disiapkan
            File fileToSave = new File(uploadDir, backupFileName);
            try (InputStream fileContent = filePart.getInputStream();
                 OutputStream outStream = new FileOutputStream(fileToSave)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileContent.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            }

            // Memproses file dan menyimpan data ke database
            List<Product> products = parseProductFile(fileToSave);
            productRepository.saveProductsBatch(products); // Simpan batch ke database
			request.setAttribute("message", "File uploaded and data saved successfully!");

            // Redirect ke halaman dengan pesan konfirmasi
            request.getRequestDispatcher("productUploadForm.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "No file chosen.");
            request.getRequestDispatcher("productUploadForm.jsp").forward(request, response);
        }
    }

    // Helper method untuk mendapatkan nama file
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }

    // Helper method untuk parsing file menjadi list of Product objects
    private List<Product> parseProductFile(File file) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String type = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    products.add(new Product(id, name, type, price));
                }
            }
        }
        return products;
    }
}
