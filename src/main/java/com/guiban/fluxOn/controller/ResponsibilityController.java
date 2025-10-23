package com.guiban.fluxOn.controller;

import com.guiban.fluxOn.infra.security.TokenService;
import com.guiban.fluxOn.responsibility.Responsibility;
import com.guiban.fluxOn.responsibility.ResponsibilityRepository;
import com.guiban.fluxOn.responsibility.dto.ResponsibilityRegisterDTO;
import com.guiban.fluxOn.responsibility.dto.ResponsibilityResponseDTO;
import com.guiban.fluxOn.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("responsibility")
public class ResponsibilityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerResponsibility(@RequestBody @Validated ResponsibilityRegisterDTO data) {
        if(this.responsibilityRepository.findByName(data.name()) != null) return ResponseEntity.badRequest().build();

        Responsibility newResponsibility = new Responsibility(data.name());

        this.responsibilityRepository.save(newResponsibility);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/responsibilities")
    public ResponseEntity<?> getAllResponsibilities() {
        List<ResponsibilityResponseDTO> responsibilityList = responsibilityRepository.findAll().stream().map(ResponsibilityResponseDTO::new).toList();

        return ResponseEntity.ok(responsibilityList);
    }

    @DeleteMapping("/deleteResponsibility/{id}")
    public ResponseEntity<?> deleteResponsibilityById(@PathVariable UUID id) {
        return responsibilityRepository.findById(id).map(responsibility -> {
            boolean inUse = userRepository.existsByUserSpecsResponsibilityId(id);
            if(inUse) return ResponseEntity.status(409).body("Não é possível deletar: cargo associado a um ou mais usuários.");
            responsibilityRepository.delete(responsibility);
            return ResponseEntity.ok("Cargo deletado com sucesso.");
        }).orElseGet(() -> ResponseEntity.status(404).body("Cargo não encontrado"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateResponsibility(@RequestBody @Validated ResponsibilityRegisterDTO data, @PathVariable UUID id) {
        Responsibility responsibility = responsibilityRepository.findById(id).orElse(null);
        if(responsibility == null) {
            return ResponseEntity.notFound().build();
        } else {
            responsibility.setName(data.name());
            this.responsibilityRepository.save(responsibility);
        }

        return ResponseEntity.ok().build();
    }
}
