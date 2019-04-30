package com.packt.app.playlist;



import com.packt.app.track.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

     List<Playlist> findByTitle(String title);
     List<Playlist> findByUserId_Username(String username);

}
