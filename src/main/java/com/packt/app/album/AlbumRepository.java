package com.packt.app.album;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AlbumRepository extends CrudRepository<Album, Integer> {

    

}
