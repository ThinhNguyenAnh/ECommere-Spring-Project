package com.employee.cart;

import com.employee.product.Product;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;


public class Cart {
    public Map<Integer, Product> cart;

    public Cart() {
    }

    public Map<Integer, Product> getCart() {
        return cart;
    }

    public void setCart(Map<Integer, Product> cart) {
        this.cart = cart;
    }



    public void add(Product product) {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }
        if (this.cart.containsKey(product.getId())) {
            int currentQuanity = this.cart.get(product.getId()).getQuantity();
            product.setQuantity(currentQuanity + product.getQuantity());

        }
        cart.put(product.getId(), product);

    }

    public void delete(Integer id) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }

    public void update(String id, Product newProduct) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(id)) {
            this.cart.replace(Integer.parseInt(id), newProduct);
        }
    }

    public double checkTotal(){

        return 0;
    }

}
