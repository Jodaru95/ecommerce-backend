package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.usecases.LoginUseCase;
import com.josedavid.ecommerce.app.application.usecases.LogoutUseCase;
import com.josedavid.ecommerce.app.application.usecases.RefreshTokenUseCase;
import com.josedavid.ecommerce.app.application.usecases.RegisterUserUseCase;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.AuthResponse;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.LoginRequest;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.RefreshRequest;
import com.josedavid.ecommerce.app.infraestructure.adapters.input.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            LoginUseCase loginUseCase,
            RefreshTokenUseCase refreshTokenUseCase, LogoutUseCase logoutUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return registerUserUseCase.execute(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return loginUseCase.execute(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return refreshTokenUseCase.execute(
                request.getRefreshToken()
        );
    }

    @PostMapping("/logout")
    public String logout(
            @RequestBody RefreshRequest request
    ) {
        logoutUseCase.execute(
                request.getRefreshToken()
        );

        return "Logout correcto";
    }
}
