package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.user.*;
import com.guiban.fluxOn.user.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("manager")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        List<UserResponseDTO> userList = userRepository.findAll().stream().map(UserResponseDTO::new).toList();

        return ResponseEntity.ok(userList);
    }
}
