package com.josedavid.ecommerce.app.infraestructure.adapters.input.rest;

import com.josedavid.ecommerce.app.application.dto.*;
import com.josedavid.ecommerce.app.application.usecases.*;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "Panel de administración")
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final GetAdminDashboardUseCase getAdminDashboardUseCase;
    private final GetTopProductsUseCase getTopProductsUseCase;
    private final GetMonthlySalesUseCase getMonthlySalesUseCase;
    private final GetAdminOrdersUseCase getAdminOrdersUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public AdminController(GetAdminDashboardUseCase getAdminDashboardUseCase, GetTopProductsUseCase getTopProductsUseCase, GetMonthlySalesUseCase getMonthlySalesUseCase, GetAdminOrdersUseCase getAdminOrdersUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.getAdminDashboardUseCase = getAdminDashboardUseCase;
        this.getTopProductsUseCase = getTopProductsUseCase;
        this.getMonthlySalesUseCase = getMonthlySalesUseCase;
        this.getAdminOrdersUseCase = getAdminOrdersUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @Operation(summary = "Obtener métricas generales del dashboard")
    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {
        return getAdminDashboardUseCase.execute();
    }

    @Operation(summary = "Obtener productos más vendidos")
    @GetMapping("/dashboard/top-products")
    public List<TopProductResponse> topProducts() {
        return getTopProductsUseCase.execute();
    }

    @Operation(summary = "Obtener ventas agrupadas por mes")
    @GetMapping("/dashboard/sales-by-month")
    public List<MonthlySalesResponse> salesByMonth() {
        return getMonthlySalesUseCase.execute();
    }

    @Operation(summary = "Actualizar estado de un pedido")
    @PutMapping("/orders/{id}/status")
    public void updateStatus(
        @PathVariable Long id,
        @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        updateOrderStatusUseCase.execute(id, request.getStatus());
    }

    @Operation(
        summary = "Listar pedidos con filtros y paginación",
        description = "Permite filtrar por estado, username, page y size"
    )
    @GetMapping("/orders")
    public Page<AdminOrderResponse> orders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String username
    ) {
        return getAdminOrdersUseCase.execute(page, size, status, username);
    }
}