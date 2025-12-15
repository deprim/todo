package com.deprim.todo.utils;

import com.deprim.todo.dto.TodoDTO;
import com.deprim.todo.model.Todo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public TodoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Todo convertToModel(TodoDTO todoDTO){
        return modelMapper.map(todoDTO, Todo.class);
    }

    public TodoDTO convertToDTO(Todo todo) {
        return modelMapper.map(todo, TodoDTO.class);
    }


}
