package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.usecases.LoginUseCase;
import com.josedavid.ecommerce.app.application.usecases.LogoutUseCase;
import com.josedavid.ecommerce.app.application.usecases.RefreshTokenUseCase;
import com.josedavid.ecommerce.app.application.usecases.RegisterUserUseCase;
import com.josedavid.ecommerce.app.application.dto.AuthResponse;
import com.josedavid.ecommerce.app.application.dto.LoginRequest;
import com.josedavid.ecommerce.app.application.dto.RefreshRequest;
import com.josedavid.ecommerce.app.application.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Autenticación y gestión de tokens")
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

    @Operation(summary = "Registrar nuevo usuario")
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return registerUserUseCase.execute(request);
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Devuelve access token JWT y refresh token"
    )
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return loginUseCase.execute(request);
    }

    @Operation(summary = "Cerrar sesión y revocar refresh token")
    @PostMapping("/logout")
    public String logout(@RequestBody RefreshRequest request) {
        logoutUseCase.execute(request.getRefreshToken());
        return "Logout correcto";
    }

    @Operation(summary = "Renovar access token mediante refresh token")
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return refreshTokenUseCase.execute(request.getRefreshToken());
    }
}
