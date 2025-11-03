package org.itmo.isLab1.auth;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.itmo.isLab1.auth.dto.AuthenticationDto;
import org.itmo.isLab1.auth.dto.SignInUpDto;
import org.itmo.isLab1.common.context.ApplicationLockBean;
import org.itmo.isLab1.common.entity.BaseController;;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController implements BaseController{
    private final ApplicationLockBean applicationLockBean;
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public AuthenticationDto signUp(@RequestBody @Valid SignInUpDto request) {
        applicationLockBean.getImportLock().lock();

        try{
            return authenticationService.signUp(request);
        }finally{
            applicationLockBean.getImportLock().unlock();
        }
    }

    @PostMapping("/sign-in")
    public AuthenticationDto signIn(@RequestBody @Valid SignInUpDto request) {
        return authenticationService.signIn(request);
    }
}