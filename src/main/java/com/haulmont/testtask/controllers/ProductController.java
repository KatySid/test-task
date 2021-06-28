package com.haulmont.testtask.controllers;

import com.haulmont.testtask.old.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/products")
public class ProductController {

    @GetMapping
    public List<Product> getProducts(){
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
        return productList;
    }
}
