package com.packt.app.album;


import com.packt.app.genre.Genre;
import com.packt.app.genre.GenreList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class AlbumController {


	private AlbumService albumService;

	@Autowired
	public AlbumController(AlbumService albumService) {
		this.albumService = albumService;
	}

	@GetMapping("/albums")
	public Iterable<Album> getAlbums() {
		return albumService.getAlbums();
	}

}
