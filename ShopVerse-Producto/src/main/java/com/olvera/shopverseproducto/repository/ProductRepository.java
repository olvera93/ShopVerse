package com.olvera.shopverseproducto.repository;

import com.olvera.shopverseproducto.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByName(String name);

    Page<Product> findByCategory(String category, Pageable pageable);
}
