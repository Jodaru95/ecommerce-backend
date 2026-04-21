package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.UpdateMeRequest;
import com.josedavid.ecommerce.app.application.dto.UserMeResponse;
import com.josedavid.ecommerce.app.application.usecases.GetMeUseCase;
import com.josedavid.ecommerce.app.application.usecases.UpdateMeUseCase;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class UserController {

    private final GetMeUseCase getMeUseCase;
    private final UpdateMeUseCase updateMeUseCase;

    public UserController(GetMeUseCase getMeUseCase, UpdateMeUseCase updateMeUseCase) {
        this.getMeUseCase = getMeUseCase;
        this.updateMeUseCase = updateMeUseCase;
    }

    @GetMapping
    public UserMeResponse getMe(Authentication auth) {
        return getMeUseCase.execute(auth.getName());
    }

    @PutMapping
    public UserMeResponse updateMe(
            Authentication auth,
            @Valid @RequestBody UpdateMeRequest request) {
        return updateMeUseCase.execute(auth.getName(), request);
    }
}