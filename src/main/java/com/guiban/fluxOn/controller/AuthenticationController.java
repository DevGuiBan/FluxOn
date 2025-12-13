package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.domain.user.User;
import com.guiban.fluxOn.domain.user.UserRepository;
import com.guiban.fluxOn.domain.user.UserRole;
import com.guiban.fluxOn.infra.security.SecurityConfiguration;
import com.guiban.fluxOn.infra.security.TokenService;
import com.guiban.fluxOn.domain.user.dto.AuthenticationDTO;
import com.guiban.fluxOn.domain.user.dto.LoginResponseDTO;
import com.guiban.fluxOn.domain.user.dto.RegisterDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        if(!user.isActive()) {
            return ResponseEntity.status(403).body("Usuário inativo.");
        }

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated RegisterDTO data) {
        if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword, true);
        newUser.setRole(UserRole.CLIENT);
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
