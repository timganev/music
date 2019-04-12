package com.packt.app.artist;

import com.packt.app.album.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArtistRepository extends CrudRepository<Artist, Integer> {

}
