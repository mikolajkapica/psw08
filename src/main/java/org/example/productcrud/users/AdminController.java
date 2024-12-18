package org.example.productcrud.users;

import lombok.AllArgsConstructor;
import org.example.productcrud.category.CategoryService;
import org.example.productcrud.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products";
    }
}
