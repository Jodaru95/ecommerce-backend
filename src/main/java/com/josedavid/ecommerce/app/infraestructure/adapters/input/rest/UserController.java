package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.UpdateMeRequest;
import com.josedavid.ecommerce.app.application.dto.UserMeResponse;
import com.josedavid.ecommerce.app.application.usecases.GetMeUseCase;
import com.josedavid.ecommerce.app.application.usecases.UpdateMeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Perfil del usuario")
@RestController
@RequestMapping("/me")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final GetMeUseCase getMeUseCase;
    private final UpdateMeUseCase updateMeUseCase;

    public UserController(GetMeUseCase getMeUseCase, UpdateMeUseCase updateMeUseCase) {
        this.getMeUseCase = getMeUseCase;
        this.updateMeUseCase = updateMeUseCase;
    }

    @Operation(summary = "Obtener perfil del usuario autenticado")
    @GetMapping
    public UserMeResponse getMe(Authentication auth) {
        return getMeUseCase.execute(auth.getName());
    }

    @Operation(summary = "Actualizar perfil del usuario autenticado")
    @PutMapping
    public UserMeResponse updateMe(
            Authentication auth,
            @Valid @RequestBody UpdateMeRequest request) {
        return updateMeUseCase.execute(auth.getName(), request);
    }
}