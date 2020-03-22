package com.movies.rest.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.movies.rest.dao.MovieDAO;
import com.movies.rest.model.Movie;
import com.movies.rest.model.Movies;
import com.movies.rest.util.TrieNode;


@RestController
@RequestMapping(path = "/movies")
public class MovieController {

	@Autowired
	private MovieDAO movieDao;

	@GetMapping(path = "/", produces = "application/json")
	public ResponseEntity<Object> getMovies() {
		Movies movies = movieDao.getAllMovies();
		return ResponseEntity.ok().body(movies);
	}

	@GetMapping(path = "/search/{prefix}/{limit}", produces = "application/json")
	public ResponseEntity<Object> searchMovies(@PathVariable("prefix") String prefix, @PathVariable("limit") Integer limit) {
		List<String> words = Collections.emptyList();
		if (movieDao.startsWith(prefix) == true) {
			TrieNode tn = movieDao.searchNode(prefix);
			movieDao.wordsFinderTraversal(tn, 0);
			words = movieDao.displayFoundWords(limit);
		} 
		return ResponseEntity.ok().body(words);
	}

	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addMovie(
			@RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
			@RequestBody Movie movie) throws Exception {
		
		if(StringUtils.hasText(movie.getMovie()) != false) {
			// add resource 
			movieDao.insert(movie.getMovie());
			movieDao.addMovie(movie);
			
			// Create resource location
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();

			// Send location in response
			return ResponseEntity.created(location).build();
		}
		
		else {
			 return ResponseEntity.noContent().build();
		}

		
	}
}
