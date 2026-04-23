package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.repository;

import com.josedavid.ecommerce.app.domain.entity.User;
import com.josedavid.ecommerce.app.domain.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findByUser(User user);
    Optional<UserAddress> findByIdAndUser(Long id, User user);
}
