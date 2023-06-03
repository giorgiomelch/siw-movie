package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;

public interface MovieRepository extends CrudRepository<Movie,Long>{

	public List<Movie> findByYear(Integer year);

	public boolean existsByTitleAndYear(String title, Integer year);
	
	@Query(value = "SELECT max(suggestedPoints) FROM Movie")
	int getMaxSuggestedPoints();
	
	public List<Movie> findBySuggestedPoints(int i);	
	
	public List<Movie> findAllByActorsIsContaining(Artist actor);
	public List<Movie> findAllByDirector(Artist director);

	public List<Movie> findAllByReviewsIsContaining(Review review);

    @Query(value = "UPDATE Movie SET suggested_points = :zero")
    @Modifying
    int updateAllSuggestedPointsToZero(int zero);

}
