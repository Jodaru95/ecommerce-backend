package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.domain.entity.UserAddress;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserAddressRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SetDefaultAddressUseCase {

    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;

    public SetDefaultAddressUseCase(
            UserRepository userRepository,
            UserAddressRepository addressRepository
    ) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public void execute(String username, Long id) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        for (UserAddress a : addressRepository.findByUser(user)) {
            a.setIsDefault(false);
        }

        UserAddress selected = addressRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        selected.setIsDefault(true);

        addressRepository.save(selected);
    }
}
