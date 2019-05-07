package com.packt.app.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

}
