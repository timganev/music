package com.packt.app.artist;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArtistRepository extends CrudRepository<Artist, Long> {

}
