package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.responsibility.Responsibility;
import com.guiban.fluxOn.responsibility.ResponsibilityRepository;
import com.guiban.fluxOn.user.*;
import com.guiban.fluxOn.user.userSpecs.PaymentMethodUser;
import com.guiban.fluxOn.user.userSpecs.UserSpecs;
import com.guiban.fluxOn.user.dto.UserResponseDTO;
import com.guiban.fluxOn.user.dto.UserWithSpecsResponseDTO;
import com.guiban.fluxOn.user.userSpecs.UserSpecsRepository;
import com.guiban.fluxOn.user.userSpecs.dto.UserSpecsRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("manager")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecsRepository userSpecsRepository;

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        List<UserResponseDTO> userList = userRepository.findAll().stream().map(UserResponseDTO::new).toList();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable UUID id) {
        List<UserWithSpecsResponseDTO> userList = userRepository.findById(id).stream()
                .filter(user -> user.getUserSpecs() != null)
                .filter(User::isActive)
                .map(user -> new UserWithSpecsResponseDTO(user, user.getUserSpecs()))
                .toList();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/usersWithSpecs")
    public ResponseEntity getAllUsersWithSpecs() {
        List<UserWithSpecsResponseDTO> userList = userRepository.findAll().stream()
                .filter(user -> user.getUserSpecs() != null)
                .filter(User::isActive)
                .map(user -> new UserWithSpecsResponseDTO(user, user.getUserSpecs()))
                .toList();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/createUserSpecs")
    public ResponseEntity createUserSpecs(@RequestBody UserSpecsRegisterDTO data) {
        User user = userRepository.findById(data.userId()).orElse(null);
        Responsibility responsibility = responsibilityRepository.findById(data.responsibilityId()).orElse(null);

        if (user == null || responsibility == null) {
            return ResponseEntity.badRequest().body("Usuário ou responsabilidade não encontrados.");
        }

        UserSpecs userSpecs = new UserSpecs(
                user,
                responsibility,
                data.number(),
                data.cpf(),
                data.rg(),
                data.salary(),
                PaymentMethodUser.valueOf(data.paymentMethod()),
                data.paymentMethodDetails(),
                data.bank(),
                data.agency(),
                data.account()
        );

        userSpecsRepository.save(userSpecs);
        user.setUserSpecs(userSpecs);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/deactivateUser/{id}")
    public ResponseEntity deactivateUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            user.setActive(false);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/activateUser/{id}")
    public ResponseEntity activateUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            user.setActive(true);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
    }

}
