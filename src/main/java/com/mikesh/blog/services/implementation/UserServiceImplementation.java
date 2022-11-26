package com.mikesh.blog.services.implementation;

import com.mikesh.blog.configuration.AppConstants;
import com.mikesh.blog.entities.Role;
import com.mikesh.blog.entities.User;
import com.mikesh.blog.exceptions.ResourceNotFoundException;
import com.mikesh.blog.payloads.UserDTO;
import com.mikesh.blog.repositories.RoleRepository;
import com.mikesh.blog.repositories.UserRepository;
import com.mikesh.blog.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);

        //encoded Password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //role
        Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepository.save(user);
        return this.modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUserRole(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "User Id: ", userId));

        user.getRoles().add(this.roleRepository.findById(AppConstants.ADMIN_USER).get());
        User updatedRole = this.userRepository.save(user);
        return this.modelMapper.map(updatedRole,UserDTO.class);
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User User = DTOtoEntity(userDTO);
        User registeredUser = this.userRepository.save(User);
        return this.entityToDTO(registeredUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "CategoryId", userId));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepository.save(user);
        return this.entityToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        
        return this.entityToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
         List<User> users = this.userRepository.findAll();

        return users.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));

        this.userRepository.delete(user);
    }


    public User DTOtoEntity(UserDTO userDTO) {

        // Traditional way for conversion...
//        User user = new User();
//        user.setId(userDTO.getId());
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        user.setAbout(userDTO.getAbout());

        // Conversion using ModelMapper dependency...
        User user = this.modelMapper.map(userDTO, User.class);
        return user;
    }

    public UserDTO entityToDTO(User user) {

        // Conversion using ModelMapper dependency...
        UserDTO convertedUserDTO = this.modelMapper.map(user, UserDTO.class);
        return convertedUserDTO;

    }
}
