package com.mikesh.blog.repositories;

import com.mikesh.blog.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
         Optional<User> actualResult = userRepository.findByEmail("sshakya219@gmail.com");
         assertNotNull(actualResult);
    }

    @Test
    void existById(){
        Boolean actualResult = userRepository.existsById(1);
        assertTrue(actualResult);
    }
}