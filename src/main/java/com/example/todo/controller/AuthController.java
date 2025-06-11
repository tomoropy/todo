package com.example.todo.controller;

import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "ユーザー名またはパスワードが間違っています");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "ログアウトしました");
        }
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @PostMapping("/signup")
    public String registerUser(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String confirmPassword,
                              RedirectAttributes redirectAttributes) {
        try {
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("errorMessage", "パスワードが一致しません");
                return "redirect:/signup";
            }
            
            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "ユーザー名とパスワードを入力してください");
                return "redirect:/signup";
            }
            
            if (password.length() < 6) {
                redirectAttributes.addFlashAttribute("errorMessage", "パスワードは6文字以上で入力してください");
                return "redirect:/signup";
            }
            
            userService.registerUser(username, password);
            redirectAttributes.addFlashAttribute("successMessage", "アカウントが作成されました。ログインしてください。");
            return "redirect:/login";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/signup";
        }
    }
}
