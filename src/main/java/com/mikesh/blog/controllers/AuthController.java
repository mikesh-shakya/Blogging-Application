package com.mikesh.blog.controllers;

import com.mikesh.blog.entities.User;
import com.mikesh.blog.exceptions.APIException;
import com.mikesh.blog.payloads.JWTAuthRequest;
import com.mikesh.blog.payloads.JWTAuthResponse;
import com.mikesh.blog.payloads.UserDTO;
import com.mikesh.blog.security.JWTTokenHelper;
import com.mikesh.blog.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserServices userServices;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JWTAuthResponse response = new JWTAuthResponse();
        response.setToken(token);
        response.setUserDTO(this.modelMapper.map((User) userDetails, UserDTO.class));
        return new ResponseEntity<JWTAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details...");
            throw new APIException("Invalid Username or password...");
        }
    }

    // create new User
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO){
        UserDTO createdUser = this.userServices.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateRole(@PathVariable Integer userId){
        UserDTO updateRole = this.userServices.updateUserRole(userId);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }
}
