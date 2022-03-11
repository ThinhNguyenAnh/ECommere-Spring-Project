package com.employee.product;

import com.employee.cart.Cart;
import com.employee.user.User;
import com.employee.user.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(Integer id) throws Exception {
        Optional<Product> result = productRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new Exception("Error");
    }


    public double renderTotal(Cart cart){
        double total = 0;
        for (Product test : cart.getCart().values()) {
            total += (test.getQuantity() * test.getPrice());
        }
        return total;
    }

    public void updateProductQuantity(Integer id , int quantity) throws Exception {
        Product product = findById(id);
        product.setQuantity(quantity);
        productRepository.save(product);
    }

    public boolean checkQuantity(Cart cart,List<Product> product){
        boolean flag= true;
        for (Product test : cart.getCart().values()) {
            for (Product check : product) {
                if (check.getId() == (test.getId()) && check.getQuantity() < test.getQuantity()) {
                    flag = false;
                }
            }
        }
        return flag;
    }

}
