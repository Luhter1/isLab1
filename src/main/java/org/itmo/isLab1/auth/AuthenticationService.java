package org.itmo.isLab1.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.itmo.isLab1.auth.dto.AuthenticationDto;
import org.itmo.isLab1.auth.dto.SignInUpDto;
import org.itmo.isLab1.users.User;
import org.itmo.isLab1.users.Role;
import org.itmo.isLab1.users.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     */
    @Transactional
    public AuthenticationDto signUp(SignInUpDto request) {
        var user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build();
        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new AuthenticationDto(jwt, user);
    }

    /**
     * Аутентификация пользователя
     */
    @Transactional
    public AuthenticationDto signIn(SignInUpDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        ));

        // var user = userService
        //     .userDetailsService()
        //     .loadUserByUsername(request.getUsername());

        var user = userService.getByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new AuthenticationDto(jwt, user);
    }
}
