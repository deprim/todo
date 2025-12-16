package com.deprim.todo.controller;

import com.deprim.todo.dto.TodoDTO;
import com.deprim.todo.dto.UserDTO;
import com.deprim.todo.model.Todo;
import com.deprim.todo.model.User;
import com.deprim.todo.service.TodoService;
import com.deprim.todo.service.UserService;
import com.deprim.todo.utils.TodoConverter;
import com.deprim.todo.utils.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    public final TodoService todoService;
    public final TodoConverter todoConverter;
    public final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public TodoController(TodoService todoService,
                          TodoConverter todoConverter,
                          UserService userService, UserConverter userConverter) {
        this.todoService = todoService;
        this.todoConverter = todoConverter;
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping
    public String index(Model model,
                        Principal principal){


        // Find all ToDo's and convert them to DTO's'
        List<Todo> todos = todoService.findAll();
        List<TodoDTO> todoDTOS = new ArrayList<>();

        for (Todo t : todos) {
            TodoDTO todoDTO = todoConverter.convertToDTO(t);
            todoDTOS.add(todoDTO);
        }

        // Who am I? Find a user that logged in.
        User currentUser = userService.findByUsername(principal.getName())
                        .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO currentUserDTO = userConverter.convertToDTO(currentUser);

        // Completed Tasks find
        Integer completed = todoService.findCompletedCount(currentUser.getUserId());
        Integer totalTodo = todoService.findAllUserTodo(currentUser.getUserId());


        model.addAttribute("todos", todoDTOS);
        model.addAttribute("user", currentUserDTO);
        model.addAttribute("completedCount", completed);
        model.addAttribute("totalCount", totalTodo);



        return "index";

    }

    @GetMapping("/new")
    public String newTodo(Model model){

        model.addAttribute("todo", new TodoDTO());
        model.addAttribute("isEdit", false);

        return "newTodo";
    }

    @PostMapping("/create")
    public String createTodo(@ModelAttribute TodoDTO todoDTO,
                             Principal principal){

        User currentUser = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        todoService.createTodo(currentUser, todoDTO);

        return "redirect:/todo";



    }

    @PostMapping("/{id}/toggle")
    public String toogleTodo(@PathVariable Long id){

        todoService.toogleTodo(id);

        return "redirect:/todo";


    }

    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id){

        todoService.deleteTodo(id);
        return "redirect:/todo";

    }



}
