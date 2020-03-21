package com.movies.rest.model;

import java.util.LinkedHashSet;

public class Movies {
	    private LinkedHashSet<Movie> movieList;

		public LinkedHashSet<Movie> getMovieList() {
			if(movieList == null)
				movieList =  new LinkedHashSet<Movie>();
			return movieList;
		}

		public void setMovieList(LinkedHashSet<Movie> movieList) {
			this.movieList = movieList;
		}
	    
	   
}
