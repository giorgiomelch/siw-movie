package it.uniroma3.siw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired private ReviewRepository reviewRepository;
	@Autowired private MovieService movieService;
	@Autowired private UserService userService;
	
	@Transactional
	public void createNewReview(Review review, Long idMovie) {
		Movie movie=this.movieService.findMovieById(idMovie);
		review.setMovie(movie);
		this.movieService.addReviewToMovie(movie, review);
		User user=userService.getCurrentUser();
		this.userService.addReview(user, review);
		review.setUser(user);
		this.reviewRepository.save(review);
	}
	public Review findReviewById(Long id) {
		return this.reviewRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void removeMovieAssociationFromReview(Movie movie) {
		List<Review> reviewToDelete= this.reviewRepository.findAllByMovie(movie);
		for(Review review:reviewToDelete) {
			review.setMovie(null);
			this.reviewRepository.delete(review);
		}
	}
	
	public boolean isTitleLengthOverMax(Review review) {
		return review.getTitle()!=null && review.getTitle().length()>Review.getMaxLengthTitle();
	}
	public boolean isTextLengthOverMax(Review review) {
		return review.getText()!=null && review.getText().length()>Review.getMaxLengthText();
	}
	@Transactional
	public void deleteReview(Long idReview) {
		Review review=this.reviewRepository.findById(idReview).get();
		this.movieService.removeReviewAssociationFromMovie(review);
		this.userService.removeReviewAsscociationFromUser(review);
		this.reviewRepository.delete(review);
	}

}
