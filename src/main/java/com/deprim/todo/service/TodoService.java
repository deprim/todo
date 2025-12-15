package com.deprim.todo.service;

import com.deprim.todo.dto.TodoDTO;
import com.deprim.todo.dto.UserDTO;
import com.deprim.todo.model.Todo;
import com.deprim.todo.model.User;
import com.deprim.todo.reposotpry.TodoRepository;
import com.deprim.todo.utils.TodoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoConverter todoConverter;
    @Autowired
    public TodoService(TodoRepository todoRepository,
                       TodoConverter todoConverter) {
        this.todoRepository = todoRepository;
        this.todoConverter = todoConverter;
    }

    public List<Todo> findAll(){
        return todoRepository.findAll();
    }

    public Integer findCompleted(Long userId){
        Integer completed = 0;
        List<Todo> todos = todoRepository.findAll();
        for (Todo t : todos) {
            if(t.getUser().getUserId() == userId && t.getCompleted()){
                completed++;
            }
        }
        return completed;
    }

    public Integer findAllUserTodo(Long userId){
        Integer count = 0;
        List<Todo> todos = todoRepository.findAll();
        for (Todo t : todos) {
            if(t.getUser().getUserId() == userId){
                count++;
            }
        }
        return count;
    }

    public void createTodo(User user,
                           TodoDTO todoDTO){

        Todo todo = todoConverter.convertToModel(todoDTO);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setCompleted(false);
        todo.setUpdatedAt(LocalDateTime.now());
        todo.setUser(user);
        todoRepository.save(todo);



    }



}
