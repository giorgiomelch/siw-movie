package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;

public interface UserRepository extends CrudRepository<User,Long>{

	public List<User> findAllByReviewsIsContaining(Review review);
	
	 @Query(value = "UPDATE User SET suggested_movie_id = NULL")
	 @Modifying
     int updateAllSuggestedMovieToNull();
}
