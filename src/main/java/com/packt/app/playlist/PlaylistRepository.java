package com.packt.app.playlist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

     Playlist findFirstByTitle(String title);


}
