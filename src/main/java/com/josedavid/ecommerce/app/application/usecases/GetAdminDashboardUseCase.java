package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.AdminDashboardResponse;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.OrderRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.ProductRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAdminDashboardUseCase {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public GetAdminDashboardUseCase(
            OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public AdminDashboardResponse execute() {

        long totalOrders = orderRepository.count();

        long pendingOrders =
                orderRepository.countByStatus(OrderStatus.PENDING_PAYMENT);

        long paidOrders =
                orderRepository.countByStatus(OrderStatus.PAID);

        long cancelledOrders =
                orderRepository.countByStatus(OrderStatus.CANCELLED);

        return new AdminDashboardResponse(
            totalOrders,
            pendingOrders,
            paidOrders,
            cancelledOrders,
            orderRepository.getTotalRevenue(),
            userRepository.count(),
            productRepository.countByStock(0),
            productRepository.countByStockLessThanEqual(5)
        );
    }
}
