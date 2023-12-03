package br.com.maintenancemanagerservice.repository;

import br.com.maintenancemanagerservice.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    UserDetails findByName(String name);

    void deleteByUsername(String name);
}
