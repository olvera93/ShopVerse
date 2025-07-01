package com.olvera.shopverseproducto.repository;

import com.olvera.shopverseproducto.model.LogEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<LogEvent, String> {
}
