package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.CheckoutRequest;
import com.josedavid.ecommerce.app.domain.entity.*;
import com.josedavid.ecommerce.app.domain.enums.OrderStatus;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CheckoutUseCase {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final UserAddressRepository addressRepository;
    private final OrderRepository orderRepository;

    public CheckoutUseCase(
            UserRepository userRepository,
            CartRepository cartRepository,
            UserAddressRepository addressRepository,
            OrderRepository orderRepository
    ) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Long execute(String username, CheckoutRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Carrito vacío");
        }

        UserAddress address = addressRepository
                .findByIdAndUser(request.getAddressId(), user)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        // snapshot dirección
        order.setShippingFullName(address.getFullName());
        order.setShippingPhone(address.getPhone());
        order.setShippingAddressLine1(address.getAddressLine1());
        order.setShippingAddressLine2(address.getAddressLine2());
        order.setShippingCity(address.getCity());
        order.setShippingProvince(address.getProvince());
        order.setShippingPostalCode(address.getPostalCode());
        order.setShippingCountry(address.getCountry());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem item = new OrderItem();

            item.setOrder(order);
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setUnitPrice(cartItem.getProduct().getPrice());

            BigDecimal subtotal =
                    cartItem.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            item.setSubtotal(subtotal);

            order.getItems().add(item);

            total = total.add(subtotal);
        }

        order.setTotalPrice(total);

        orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return order.getId();
    }
}