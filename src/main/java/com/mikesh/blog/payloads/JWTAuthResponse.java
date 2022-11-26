package com.mikesh.blog.payloads;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String token;
    private UserDTO userDTO;
}
