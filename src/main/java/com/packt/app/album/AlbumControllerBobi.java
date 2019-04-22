package com.packt.app.album;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumControllerBobi {


	private AlbumService albumService;

	private AlbumRepository albumRepository;

	@Autowired
	public AlbumControllerBobi(AlbumService albumService, AlbumRepository albumRepository) {
		this.albumService = albumService;
		this.albumRepository = albumRepository;
	}

	@RequestMapping("/albums")
	public Album getAlbums() {
		AlbumDTO albumDTO = new AlbumDTO();
		return albumRepository.findById(1).orElse(null);
	}
}
