package com.example.todo.controller;

import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.service.TodoService;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TodoController {
    
    @Autowired
    private TodoService todoService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        
        List<Todo> todos = todoService.findAllByUser(user);
        long completedCount = todoService.countCompletedTodos(user);
        long pendingCount = todoService.countPendingTodos(user);
        
        model.addAttribute("todos", todos);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("newTodo", new Todo());
        
        return "index";
    }
    
    @PostMapping("/todos")
    public String createTodo(@RequestParam String title, 
                           @RequestParam String description,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
            
            todoService.createTodo(title, description, user);
            redirectAttributes.addFlashAttribute("successMessage", "TODOが作成されました");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "TODOの作成に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/";
    }
    
    @PostMapping("/todos/{id}/update")
    public String updateTodo(@PathVariable Long id,
                           @RequestParam String title,
                           @RequestParam String description,
                           @RequestParam(defaultValue = "false") boolean completed,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
            
            todoService.updateTodo(id, title, description, completed, user);
            redirectAttributes.addFlashAttribute("successMessage", "TODOが更新されました");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "TODOの更新に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/";
    }
    
    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
            
            todoService.toggleComplete(id, user);
            redirectAttributes.addFlashAttribute("successMessage", "TODOの状態が変更されました");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "TODOの状態変更に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/";
    }
    
    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
            
            todoService.deleteTodo(id, user);
            redirectAttributes.addFlashAttribute("successMessage", "TODOが削除されました");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "TODOの削除に失敗しました: " + e.getMessage());
        }
        
        return "redirect:/";
    }
}
