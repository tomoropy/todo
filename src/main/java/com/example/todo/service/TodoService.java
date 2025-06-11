package com.example.todo.service;

import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;
    
    public List<Todo> findAllByUser(User user) {
        return todoRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public List<Todo> findByUserAndCompleted(User user, boolean completed) {
        return todoRepository.findByUserAndCompletedOrderByCreatedAtDesc(user, completed);
    }
    
    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    public Todo createTodo(String title, String description, User user) {
        Todo todo = new Todo(title, description, user);
        return todoRepository.save(todo);
    }
    
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }
    
    public Todo updateTodo(Long id, String title, String description, boolean completed, User user) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            // ユーザーが所有者であることを確認
            if (!todo.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("このTODOを編集する権限がありません");
            }
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setCompleted(completed);
            return todoRepository.save(todo);
        }
        throw new RuntimeException("TODOが見つかりません");
    }
    
    public void deleteTodo(Long id, User user) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            // ユーザーが所有者であることを確認
            if (!todo.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("このTODOを削除する権限がありません");
            }
            todoRepository.delete(todo);
        } else {
            throw new RuntimeException("TODOが見つかりません");
        }
    }
    
    public void toggleComplete(Long id, User user) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            // ユーザーが所有者であることを確認
            if (!todo.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("このTODOを編集する権限がありません");
            }
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
        } else {
            throw new RuntimeException("TODOが見つかりません");
        }
    }
    
    public long countCompletedTodos(User user) {
        return todoRepository.countByUserAndCompleted(user, true);
    }
    
    public long countPendingTodos(User user) {
        return todoRepository.countByUserAndCompleted(user, false);
    }
}
