package com.josedavid.ecommerce.app.application.usecases;

import com.josedavid.ecommerce.app.application.dto.AddressResponse;
import com.josedavid.ecommerce.app.application.dto.SaveAddressRequest;
import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.domain.entity.UserAddress;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserAddressRepository;
import com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveAddressUseCase {

    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;

    public SaveAddressUseCase(
            UserRepository userRepository,
            UserAddressRepository addressRepository
    ) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public AddressResponse execute(
            String username,
            SaveAddressRequest request
    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UserAddress address = new UserAddress();

        address.setUser(user);
        address.setFullName(request.getFullName());
        address.setAddressLine1(request.getAddressLine1());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());

        boolean first = addressRepository.findByUser(user).isEmpty();
        address.setIsDefault(first);

        addressRepository.save(address);

        return new AddressResponse(
                address.getId(),
                address.getFullName(),
                address.getAddressLine1(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry(),
                address.getIsDefault()
        );
    }
}
