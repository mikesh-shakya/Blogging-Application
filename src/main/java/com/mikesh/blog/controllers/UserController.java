package com.mikesh.blog.controllers;

import com.mikesh.blog.payloads.UserDTO;
import com.mikesh.blog.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServices userServices;

    // GET -  get all users...
    @GetMapping
    public List<UserDTO> getAllUsers(){
        return this.userServices.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Integer userId){
        return this.userServices.getUserById(userId);
    }

    // POST - create  user...
    @PostMapping("/")
    public ResponseEntity<UserDTO> signUpUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO registeredUserDTO = this.userServices.registerUser(userDTO);
        return new ResponseEntity<>(registeredUserDTO, HttpStatus.CREATED);
    }

    // PUT - update user...
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Integer userId){
        UserDTO updatedUserDTO = this.userServices.updateUser(userDTO,userId);
        return ResponseEntity.ok(updatedUserDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId){
        this.userServices.deleteUser(userId);
    }

}
