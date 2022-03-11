package com.employee.employeewebapp;

import com.employee.product.Product;
import com.employee.product.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repo;

    @Test
    public void addNewProduct(){
        Product product = new Product("Camera",1000,100);
        Product product2 = new Product("Television",2000,100);
        Product product3 = new Product("IPad",3000,100);

        repo.save(product);
        repo.save(product2);
        repo.save(product3);

    }
}
