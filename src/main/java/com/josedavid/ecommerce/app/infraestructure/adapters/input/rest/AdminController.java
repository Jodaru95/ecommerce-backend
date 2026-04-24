package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.AdminDashboardResponse;
import com.josedavid.ecommerce.app.application.dto.TopProductResponse;
import com.josedavid.ecommerce.app.application.usecases.GetAdminDashboardUseCase;
import com.josedavid.ecommerce.app.application.usecases.GetTopProductsUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final GetAdminDashboardUseCase useCase;
    private final GetTopProductsUseCase getTopProductsUseCase;

    public AdminController(GetAdminDashboardUseCase useCase, GetTopProductsUseCase getTopProductsUseCase) {
        this.useCase = useCase;
        this.getTopProductsUseCase = getTopProductsUseCase;
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {
        return useCase.execute();
    }

    @GetMapping("/dashboard/top-products")
    public List<TopProductResponse> topProducts() {
        return getTopProductsUseCase.execute();
    }
}