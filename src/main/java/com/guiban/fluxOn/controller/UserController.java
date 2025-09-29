package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.responsibility.Responsibility;
import com.guiban.fluxOn.responsibility.ResponsibilityRepository;
import com.guiban.fluxOn.user.*;
import com.guiban.fluxOn.user.dto.UserWithSpecsAdminUpdateDTO;
import com.guiban.fluxOn.user.dto.UserWithSpecsUserUpdateDTO;
import com.guiban.fluxOn.userSpecs.PaymentMethodUser;
import com.guiban.fluxOn.userSpecs.UserSpecs;
import com.guiban.fluxOn.user.dto.UserResponseDTO;
import com.guiban.fluxOn.user.dto.UserWithSpecsResponseDTO;
import com.guiban.fluxOn.userSpecs.UserSpecsRepository;
import com.guiban.fluxOn.userSpecs.dto.UserSpecsRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.IllegalFormatException;
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
        try {
            List<UserResponseDTO> userList = userRepository.findAll().stream().map(UserResponseDTO::new).toList();

            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable UUID id) {
        try {
            List<UserWithSpecsResponseDTO> userList = userRepository.findById(id).stream()
                    .filter(user -> user.getUserSpecs() != null)
                    .filter(User::isActive)
                    .map(user -> new UserWithSpecsResponseDTO(user, user.getUserSpecs()))
                    .toList();

            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/usersWithSpecs")
    public ResponseEntity getAllUsersWithSpecs() {
        try {
            List<UserWithSpecsResponseDTO> userList = userRepository.findAll().stream()
                    .filter(user -> user.getUserSpecs() != null)
                    .filter(User::isActive)
                    .map(user -> new UserWithSpecsResponseDTO(user, user.getUserSpecs()))
                    .toList();
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/createUserSpecs")
    public ResponseEntity createUserSpecs(@RequestBody UserSpecsRegisterDTO data) {
        User user = userRepository.findById(data.userId()).orElse(null);
        Responsibility responsibility = responsibilityRepository.findById(data.responsibilityId()).orElse(null);

        if (user == null || responsibility == null) return ResponseEntity.status(404).body("Usuário ou Responsabilidade encontrado.");

        try {
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
            return ResponseEntity.ok("Especificações do usuário criadas com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PutMapping("/deactivateUser/{id}")
    public ResponseEntity deactivateUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) return ResponseEntity.status(404).body("Usuário não encontrado.");

        try {
            user.setActive(false);
            userRepository.save(user);
            return ResponseEntity.ok("Usuário desativado com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/activateUser/{id}")
    public ResponseEntity activateUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) return ResponseEntity.status(404).body("Usuário não encontrado.");

        try {
            user.setActive(true);
            userRepository.save(user);
            return ResponseEntity.ok("Usuário ativado com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PutMapping("/updateUserByUser/{id}")
    public ResponseEntity updateUserByUser(@PathVariable UUID id, @RequestBody UserWithSpecsUserUpdateDTO data) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) return ResponseEntity.status(404).body("Usuário não encontrado.");

        try {
            if (data.name() != null) user.setName(data.name());
            if (data.email() != null) user.setEmail(data.email());
            if (data.password() != null) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
                user.setPassword(encryptedPassword);
            }
            userRepository.save(user);

            UserSpecs userSpecs = user.getUserSpecs();
            if (userSpecs != null) {
                if (data.number() != null) userSpecs.setNumber(data.number());
                if (data.cpf() != null) userSpecs.setCpf(data.cpf());
                if (data.rg() != null) userSpecs.setRg(data.rg());
                if (data.paymentMethod() != null) userSpecs.setPaymentMethod(PaymentMethodUser.valueOf(data.paymentMethod()));
                if (data.paymentMethodDetails() != null) userSpecs.setPaymentMethodDetails(data.paymentMethodDetails());
                if (data.bank() != null) userSpecs.setBank(data.bank());
                if (data.agency() != null) userSpecs.setAgency(data.agency());
                if (data.account() != null) userSpecs.setAccount(data.account());
                userSpecsRepository.save(userSpecs);
            }

            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/updateUserByAdmin/{id}")
    public ResponseEntity updateUserByAdmin(@PathVariable UUID id, @RequestBody UserWithSpecsAdminUpdateDTO data) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        try {
            if (data.name() != null) user.setName(data.name());
            if (data.email() != null) user.setEmail(data.email());

            if (data.password() != null) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
                user.setPassword(encryptedPassword);
            }

            if (data.responsibilityId() != null) {
                Responsibility responsibility = responsibilityRepository.findById(data.responsibilityId()).orElse(null);
            }

            if (data.role() != null) user.setRole(UserRole.valueOf(data.role()));

            userRepository.save(user);

            UserSpecs userSpecs = user.getUserSpecs();
            if (userSpecs != null) {
                if (data.number() != null) userSpecs.setNumber(data.number());
                if (data.cpf() != null) userSpecs.setCpf(data.cpf());
                if (data.rg() != null) userSpecs.setRg(data.rg());
                if (data.paymentMethod() != null) userSpecs.setPaymentMethod(PaymentMethodUser.valueOf(data.paymentMethod()));
                if (data.paymentMethodDetails() != null) userSpecs.setPaymentMethodDetails(data.paymentMethodDetails());
                if (data.bank() != null) userSpecs.setBank(data.bank());
                if (data.agency() != null) userSpecs.setAgency(data.agency());
                if (data.account() != null) userSpecs.setAccount(data.account());
                userSpecsRepository.save(userSpecs);
            }

            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
