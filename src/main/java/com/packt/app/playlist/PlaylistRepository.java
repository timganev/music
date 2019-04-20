package com.packt.app.playlist;



import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

}
