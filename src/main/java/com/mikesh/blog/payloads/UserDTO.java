package com.mikesh.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mikesh.blog.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email(message = "Invalid Email...")
    private String email;
    @NotNull
    @Size(min = 4, max =15)
    private String password;
    @NotEmpty
    private String about;

    private Set<RoleDTO> roles = new HashSet<>();

    private Set<CommentDTO> comments = new HashSet<>();

//    @JsonIgnore
//    public String getPassword(){ return this.password;}
//
//    @JsonProperty
//    public String setPassword(){ return this.password = password;}
}
