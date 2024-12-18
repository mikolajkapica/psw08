package org.example.productcrud.users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.productcrud.product.Product;
import org.example.productcrud.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final ProductService productService;

    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "user/products";
    }

}