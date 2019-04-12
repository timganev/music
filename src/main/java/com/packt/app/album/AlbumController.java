package com.packt.app.album;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumController {
	@Autowired
	private AlbumRepository repository;

	@RequestMapping("/album")
	public Iterable<Album> getCars() {
		return repository.findAll();
	}
}
