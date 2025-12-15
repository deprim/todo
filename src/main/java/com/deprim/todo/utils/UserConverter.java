package com.deprim.todo.utils;

import com.deprim.todo.dto.UserDTO;
import com.deprim.todo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User covertToModel(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }


}
