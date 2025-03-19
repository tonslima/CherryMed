package com.cherry.med.controller;

import com.cherry.med.domain.user.AuthenticationDTO;
import com.cherry.med.domain.user.LoginResponseDTO;
import com.cherry.med.domain.user.RegisterDTO;
import com.cherry.med.domain.user.User;
import com.cherry.med.service.AuthenticationService;
import com.cherry.med.service.TokenService;
import com.cherry.med.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto) {
        var auth = authenticationService.authenticate(dto.login(), dto.password());
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO dto) {
        try {
            var userEntity = RegisterDTO.toEntity(dto);
            var user = userService.register(userEntity);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
