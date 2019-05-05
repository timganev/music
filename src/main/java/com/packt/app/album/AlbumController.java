package com.packt.app.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AlbumController {


	private AlbumService albumService;

	@Autowired
	public AlbumController(AlbumService albumService) {
		this.albumService = albumService;
	}

	@GetMapping("/albums")
	public ResponseEntity<Iterable<Album>> getAlbums() {
		if (albumService.getAlbums().isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Albums not found");
		}
		return new  ResponseEntity<>(albumService.getAlbums(), HttpStatus.OK);
	}


}
