package com.pagos.identities.repositories;

import com.pagos.identities.models.Identity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdentityRepository extends MongoRepository<Identity, String> {
    Identity findIdentityByClientIdAndClientSecret(String clientId, String clientSecret);
}
