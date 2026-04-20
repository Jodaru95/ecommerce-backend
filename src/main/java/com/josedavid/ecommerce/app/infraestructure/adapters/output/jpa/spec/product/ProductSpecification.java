package com.josedavid.ecommerce.app.infraestructure.adapters.output.jpa.spec.product;

import com.josedavid.ecommerce.app.domain.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null :
                        cb.like(cb.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> minPrice(BigDecimal minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null :
                        cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPrice(BigDecimal maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null :
                        cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
