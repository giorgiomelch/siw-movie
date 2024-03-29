package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;

public interface ReviewRepository extends CrudRepository<Review,Long>{

	public boolean existsByMovieAndTitleAndText(Movie movie, String title, String text);
	public List<Review> findAllByMovie(Movie movie);

	public Boolean  existsByUserAndMovie(User credentials,Movie movie);
}

