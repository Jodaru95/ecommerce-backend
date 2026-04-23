package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.domain.entity.UserAddress;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserAddressRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteAddressUseCase {

    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;

    public DeleteAddressUseCase(
            UserRepository userRepository,
            UserAddressRepository addressRepository
    ) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public void execute(String username, Long id) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserAddress address = addressRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        addressRepository.delete(address);
    }
}
