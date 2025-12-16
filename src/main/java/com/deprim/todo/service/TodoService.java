package com.deprim.todo.service;

import com.deprim.todo.dto.TodoDTO;
import com.deprim.todo.model.Todo;
import com.deprim.todo.model.User;
import com.deprim.todo.reposotpry.TodoRepository;
import com.deprim.todo.reposotpry.UserRepository;
import com.deprim.todo.utils.TodoConverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoConverter todoConverter;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UserRepository userRepository;


    @Autowired
    public TodoService(TodoRepository todoRepository,
                       TodoConverter todoConverter,
                       ModelMapper modelMapper,
                       UserService userService, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.todoConverter = todoConverter;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    public List<Todo> findAll(Long id){

        User user = userService.findByUserId(id).orElseThrow();

        return todoRepository.findByUser(user);



    }

    public Integer findCompletedCount(Long userId){
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

    @Transactional
    public void createTodo(User user,
                           TodoDTO todoDTO){

        Todo todo = todoConverter.convertToModel(todoDTO);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setCompleted(false);
        todo.setUpdatedAt(LocalDateTime.now());
        todo.setUser(user);
        todoRepository.save(todo);



    }

    public Todo findById(Long id){
        return todoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void toogleTodo(Long id){
        Todo todo = todoRepository.findById(id).orElse(null);
        boolean currentStatus = todo.getCompleted();
        todo.setCompleted(!currentStatus);
        todoRepository.save(todo);
    }

    @Transactional
    public void deleteTodo(Long id){
        todoRepository.deleteById(id);
    }

    @Transactional
    public void editTodo(Long id,
                         TodoDTO todoDTO){

        Todo todo = findById(id);

        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());
        todo.setPriority(todoDTO.getPriority());
        todo.setDueDate(todoDTO.getDueDate());
        todo.setCompleted(todo.getCompleted());
        todoRepository.save(todo);

    }

    public List<Todo> findAllActive(Long id){

        User user = userService.findByUserId(id).orElseThrow();

        return todoRepository.findByUserAndCompletedIsFalse(user);

    }

    public List<Todo> findAllCompleted(Long id){

        User user = userService.findByUserId(id).orElseThrow();

        return todoRepository.findByUserAndCompletedIsTrue(user);

    }







}
