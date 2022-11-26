package com.mikesh.blog.services;

import com.mikesh.blog.payloads.UserDTO;
import java.util.List;

public interface UserServices {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUserRole(Integer userId);
    UserDTO registerUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer userId);
}
