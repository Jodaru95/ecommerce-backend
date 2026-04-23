package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.AddressResponse;
import com.josedavid.ecommerce.app.application.dto.SaveAddressRequest;
import com.josedavid.ecommerce.app.application.usecases.DeleteAddressUseCase;
import com.josedavid.ecommerce.app.application.usecases.GetAddressesUseCase;
import com.josedavid.ecommerce.app.application.usecases.SaveAddressUseCase;
import com.josedavid.ecommerce.app.application.usecases.SetDefaultAddressUseCase;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final GetAddressesUseCase getAddressesUseCase;
    private final SaveAddressUseCase saveAddressUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;
    private final SetDefaultAddressUseCase setDefaultAddressUseCase;

    public AddressController(
            GetAddressesUseCase getAddressesUseCase,
            SaveAddressUseCase saveAddressUseCase,
            DeleteAddressUseCase deleteAddressUseCase,
            SetDefaultAddressUseCase setDefaultAddressUseCase
    ) {
        this.getAddressesUseCase = getAddressesUseCase;
        this.saveAddressUseCase = saveAddressUseCase;
        this.deleteAddressUseCase = deleteAddressUseCase;
        this.setDefaultAddressUseCase = setDefaultAddressUseCase;
    }

    @GetMapping
    public List<AddressResponse> getAll(Authentication auth) {
        return getAddressesUseCase.execute(auth.getName());
    }

    @PostMapping
    public AddressResponse save(
            Authentication auth,
            @Valid @RequestBody SaveAddressRequest request
    ) {
        return saveAddressUseCase.execute(auth.getName(), request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            Authentication auth,
            @PathVariable Long id
    ) {
        deleteAddressUseCase.execute(auth.getName(), id);
    }

    @PutMapping("/{id}/default")
    public void setDefault(
            Authentication auth,
            @PathVariable Long id
    ) {
        setDefaultAddressUseCase.execute(auth.getName(), id);
    }
}
