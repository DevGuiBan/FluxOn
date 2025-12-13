package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.domain.user.User;
import com.guiban.fluxOn.domain.user.UserRepository;
import com.guiban.fluxOn.domain.user.UserRole;
import com.guiban.fluxOn.infra.security.SecurityConfiguration;
import com.guiban.fluxOn.domain.responsibility.Responsibility;
import com.guiban.fluxOn.domain.responsibility.ResponsibilityRepository;
import com.guiban.fluxOn.domain.user.dto.UserWithSpecsAdminUpdateDTO;
import com.guiban.fluxOn.domain.user.dto.UserWithSpecsUserUpdateDTO;
import com.guiban.fluxOn.domain.userSpecs.PaymentMethodUser;
import com.guiban.fluxOn.domain.userSpecs.UserSpecs;
import com.guiban.fluxOn.domain.user.dto.UserResponseDTO;
import com.guiban.fluxOn.domain.user.dto.UserWithSpecsResponseDTO;
import com.guiban.fluxOn.domain.userSpecs.UserSpecsRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("manager")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecsRepository userSpecsRepository;

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserResponseDTO> userList = userRepository.findAll().stream().map(UserResponseDTO::new).toList();

            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
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
    public ResponseEntity<?> getAllUsersWithSpecs() {
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

    @GetMapping("/usersWithSpecsById/{id}")
    public ResponseEntity<?> getUserWithSpecsById(@PathVariable UUID id) {
        try {
            List<UserWithSpecsResponseDTO> userList = userRepository.findById(id).stream()
                    .filter(user -> user.getId() != null)
                    .map(user -> new UserWithSpecsResponseDTO(user, user.getUserSpecs()))
                    .toList();
            return ResponseEntity.ok(userList);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/deactivateUser/{id}")
    public ResponseEntity<?> deactivateUser(@PathVariable UUID id) {
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
    public ResponseEntity<?> activateUser(@PathVariable UUID id) {
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

    @PutMapping("/changeRole/{id}")
    public ResponseEntity<?> changeRole(@PathVariable UUID id, UserRole role) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null) return ResponseEntity.notFound().build();

        try {
            user.setRole(role);
            userRepository.save(user);
            return ResponseEntity.ok("Alteração feita com sucesso!");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @PutMapping("/updateUserByUser/{id}")
    public ResponseEntity<?> updateUserByUser(@PathVariable UUID id, @RequestBody UserWithSpecsUserUpdateDTO data) {
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
    public ResponseEntity<?> updateUserByAdmin(@PathVariable UUID id, @RequestBody UserWithSpecsAdminUpdateDTO data) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        if (data.name() != null) user.setName(data.name());
        if (data.email() != null) user.setEmail(data.email());
        if (data.password() != null) user.setPassword(new BCryptPasswordEncoder().encode(data.password()));
        if (data.role() != null) user.setRole(UserRole.valueOf(data.role()));
        userRepository.save(user);

        if (data.cpf() != null) {
            boolean cpfTaken = userSpecsRepository.findAll().stream()
                    .filter(spec -> spec.getCpf() != null)
                    .anyMatch(spec -> spec.getCpf().equals(data.cpf()) && !spec.getUser().getId().equals(user.getId()));
            if (cpfTaken) {
                return ResponseEntity.status(409).body("Já existe um usuário cadastrado com essas informações");
            }
        }

        UserSpecs userSpecs = user.getUserSpecs();
        if (userSpecs != null) {
            if (data.number() != null) userSpecs.setNumber(data.number());
            if (data.cpf() != null) userSpecs.setCpf(data.cpf());
            if (data.rg() != null) userSpecs.setRg(data.rg());
            if (data.salary() != null) userSpecs.setSalary(data.salary());
            if (data.paymentMethod() != null) userSpecs.setPaymentMethod(PaymentMethodUser.valueOf(data.paymentMethod()));
            if (data.paymentMethodDetails() != null) userSpecs.setPaymentMethodDetails(data.paymentMethodDetails());
            if (data.bank() != null) userSpecs.setBank(data.bank());
            if (data.agency() != null) userSpecs.setAgency(data.agency());
            if (data.account() != null) userSpecs.setAccount(data.account());
            if (data.responsibilityId() != null) {
                Responsibility responsibility = responsibilityRepository.findById(data.responsibilityId()).orElse(null);
                if (responsibility != null) userSpecs.setResponsibility(responsibility);
            }
            userSpecsRepository.save(userSpecs);
            return ResponseEntity.ok("Especificações atualizadas!");
        } else {
            if (data.responsibilityId() != null) {
                Responsibility responsibility = responsibilityRepository.findById(data.responsibilityId()).orElse(null);
                if (responsibility == null) {
                    return ResponseEntity.status(404).body("Responsabilidade não encontrada.");
                }

                if (data.cpf() != null) {
                    boolean cpfTaken = userSpecsRepository.findAll().stream()
                            .filter(spec -> spec.getCpf() != null)
                            .anyMatch(spec -> spec.getCpf().equals(data.cpf()) && !spec.getUser().getId().equals(user.getId()));
                    if (cpfTaken) {
                        return ResponseEntity.status(409).body("CPF já cadastrado para outro usuário.");
                    }
                }

                UserSpecs newSpecs = new UserSpecs(
                        user,
                        responsibility,
                        data.number(),
                        data.cpf(),
                        data.rg(),
                        data.salary(),
                        data.paymentMethod() != null ? PaymentMethodUser.valueOf(data.paymentMethod()) : null,
                        data.paymentMethodDetails(),
                        data.bank(),
                        data.agency(),
                        data.account()
                );
                userSpecsRepository.save(newSpecs);
                user.setUserSpecs(newSpecs);
                userRepository.save(user);
                return ResponseEntity.ok("Especificações cadastradas!");
            } else {
                return ResponseEntity.ok("Nenhuma especificação foi alterada.");
            }
        }
    }
}
