package com.packt.app.playlist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.management.Query;
import java.util.List;

import static com.packt.app.constants.Constants.QUEERY_RANDOM_TRACK;

@RepositoryRestResource
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

     Playlist findFirstByTitle(String title);



}
