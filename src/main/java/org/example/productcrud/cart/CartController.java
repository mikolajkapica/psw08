package org.example.productcrud.cart;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.productcrud.product.Product;
import org.example.productcrud.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CartController {
    private final ProductService productService;

    @GetMapping("/cart")
    public String viewCart(HttpServletRequest request, Model model) {
        @AllArgsConstructor
        @Data
        class CartItem {
            private final Product product;
            private final Optional<Integer> quantity;
        }
        Cookie[] cookies = request.getCookies();
        List<CartItem> cartItems = Arrays.stream(cookies)
                .filter(cookie -> "cart".equals(cookie.getName()))
                .map(cookie -> {
                    Long productId = Long.valueOf(cookie.getAttribute("productId"));
                    Product product = productService.findById(productId);
                    Optional<Integer> productQuantity = Optional.ofNullable(cookie.getAttribute("productQuantity")).map(Integer::valueOf);
                    return new CartItem(product, productQuantity);
                }).toList();
        Double totalPrice = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity().orElse(1)).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "user/cart";
    }

    @PostMapping("/user/cart/add")
    public String addToCart(@RequestParam("productId") Long productId, HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", "productId=" + productId);
        response.addCookie(cookie);
        return "redirect:/user/cart";
    }

    @PostMapping("/user/cart/update")
    public String updateCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", "productId=" + productId + "&quantity=" + quantity);
        response.addCookie(cookie);
        return "redirect:/user/cart";
    }

    @PostMapping("/user/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/user/cart";
    }
}