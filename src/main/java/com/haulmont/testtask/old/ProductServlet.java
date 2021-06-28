package com.haulmont.testtask.old;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET запрос");
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Book", 25));
        productList.add(new Product(2, "Magasine", 15));
        productList.add(new Product(3, "Table", 125));
        productList.add(new Product(4, "Plate", 35));
        productList.add(new Product(5, "Mirror", 80));
        productList.add(new Product(6, "Soap", 5));
        productList.add(new Product(7, "Flower", 10));
        productList.add(new Product(8, "Picture", 95));
        productList.add(new Product(9, "Phone", 25));
        productList.add(new Product(10, "Chair", 40));
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>List products</h1>");
        out.println("<br>");
        out.println("<div>");
        out.println("<div>ID Title Cost</div>");
        for (int i = 0; i < 10; i++) {
            Product p= productList.get(i);
            out.print(String.format("<div>%d %s %d</div>",p.getId(), p.getTitle(), p.getCost()));
            out.println("<br>");
        }
        out.println("</div>");
        out.println("</body></html>");
        out.close();
    }
}
