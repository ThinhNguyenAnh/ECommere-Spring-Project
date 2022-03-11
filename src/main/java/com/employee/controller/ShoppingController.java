package com.employee.controller;

import com.employee.cart.Cart;
import com.employee.config.CustomUserDetails;
import com.employee.order_entity.Order;
import com.employee.order_entity.OrderDetail;
import com.employee.order_entity.OrderDetailService;
import com.employee.order_entity.OrderService;
import com.employee.product.Product;
import com.employee.product.ProductService;
import com.employee.user.User;
import com.employee.user.UserNotFoundException;
import com.employee.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ShoppingController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;


    @GetMapping("/shopping")
    public String showListProduct(HttpSession session) {
        session.setAttribute("LIST_PRODUCT", productService.listAllProducts());

        return "shopping_page";
    }

    @GetMapping("/showCart")
    public String showCart() {
        return "cart";
    }

    @GetMapping("/shopping/add/{id}")
    public String addToCart(HttpSession session, @PathVariable("id") Integer id) throws Exception {
        Product product = productService.findById(id);
        product.setQuantity(1);

        Cart cart = (Cart) session.getAttribute("CART");
        if (cart == null) {
            cart = new Cart();
        }

        cart.add(product);

        session.setAttribute("CART", cart);
        session.setAttribute("TOTAL", productService.renderTotal(cart));

        return "cart";
    }

    @GetMapping("/shopping/delete/{id}")
    public String removeProductCart(HttpSession session, @PathVariable("id") Integer id) throws Exception {
        Cart cart = (Cart) session.getAttribute("CART");
        if (cart != null) {
            cart.delete(id);
            session.setAttribute("CART", cart);
            session.setAttribute("TOTAL", productService.renderTotal(cart));
        }
        return "redirect:/showCart";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) throws UserNotFoundException, Exception {

        Integer currentLoginUserId = userDetails.getUserID();
        User user = userService.get(currentLoginUserId);
        double total = (double) session.getAttribute("TOTAL");
        Cart cart = (Cart) session.getAttribute("CART");
        boolean checkQuantity = true;
        LocalDate localDate = LocalDate.now();
        List<Product> product = (List<Product>) session.getAttribute("LIST_PRODUCT");

        if (productService.checkQuantity(cart, product)) {

            Order order = new Order(user, String.valueOf(localDate), total);
            orderService.saveOrder(order);
            boolean flag = false;
            Order tmpOrder = orderService.getLastOrderId(user);

            for (Product product1 : cart.getCart().values()) {

                OrderDetail orderDetail = new OrderDetail(tmpOrder, productService.findById(product1.getId()), product1.getQuantity(), product1.getPrice());

                orderDetailService.save(orderDetail);

                for (Product tmp : product) {
                    if (tmp.getId() == product1.getId()) {
                        productService.updateProductQuantity(product1.getId(), tmp.getQuantity() - product1.getQuantity());
                        flag = true;
                        break;

                    }
                }
            }
            if (flag) {
                cart.getCart().clear();
                session.setAttribute("CART", cart);
                session.setAttribute("TOTAL", null);
                model.addAttribute("MESSAGE", "Successful checkout");
                return "cart";
            }

        } else {
            model.addAttribute("MESSAGE", "We don't have enough product for your purchase");
            return "cart";
        }


        return null;
    }


}

