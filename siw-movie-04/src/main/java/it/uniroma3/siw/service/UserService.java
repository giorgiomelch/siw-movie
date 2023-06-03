package it.uniroma3.siw.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Service
public class UserService {

	@Autowired private UserRepository userRepository;
	@Autowired CredentialsService credentialsService;
	

	@Transactional
	public void saveUser(User user) {
		this.userRepository.save(user);
	}
	@Transactional
	public void setSuggestedMovie(User user, Movie movie) {
		user.setSuggestedMovie(movie);
		this.userRepository.save(user);
	}
	public void addReview(User user, Review review) {
		user.getReviews().add(review);
		this.userRepository.save(user);		
	}
	public User getCurrentUser() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		return credentials.getUser();
	}
	
	//FARE TRAMITE QUERY
	public boolean multipleReviewOfMovieBySameUser(Movie movie){
		User user=this.getCurrentUser();
		for(Review userReview: user.getReviews()) {
			if(userReview.getMovie()==movie)
				return true;
		}
		return false;
	}
	@Transactional
	public void removeReviewAsscociationFromUser(Review review) {
		User user=this.userRepository.findAllByReviewsIsContaining(review).get(0);
		user.getReviews().remove(review);
		this.userRepository.save(user);
	}
	@Transactional
	public void resetAllToNullSuggestedMovie() {
		this.userRepository.updateAllSuggestedMovieToNull();
	}
}
