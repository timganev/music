package com.packt.app.genre;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GenreRepository extends CrudRepository<Genre, Integer> {

}
