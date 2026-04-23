package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.AddressResponse;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.domain.entity.UserAddress;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserAddressRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAddressesUseCase {

    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;

    public GetAddressesUseCase(
        UserRepository userRepository,
        UserAddressRepository addressRepository
    ) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public List<AddressResponse> execute(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return addressRepository.findByUser(user)
            .stream()
            .map(a -> new AddressResponse(
                    a.getId(),
                    a.getFullName(),
                    a.getAddressLine1(),
                    a.getCity(),
                    a.getPostalCode(),
                    a.getCountry(),
                    a.getIsDefault()
            ))
            .toList();
    }
}
