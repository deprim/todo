package com.deprim.todo.controller;

import com.deprim.todo.dto.UserDTO;
import com.deprim.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/register")
@Controller
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String register(Model model) {

        model.addAttribute("user", new UserDTO());


        return "register";
    }

    @PostMapping("/process")
    public String registerProcess(@ModelAttribute("user") UserDTO userDTO){

        System.out.println("registerProcess called");
        System.out.println(userDTO);
        userService.registerUser(userDTO);
        System.out.println("User saved");
        return "redirect:/login";
    }

}
