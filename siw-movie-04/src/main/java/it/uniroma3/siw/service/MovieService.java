package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.ArtistRepository;
import it.uniroma3.siw.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired private MovieRepository movieRepository;
	@Autowired private ArtistRepository artistRepository;
	@Autowired private ArtistService artistService;
	@Autowired private ReviewService reviewService;

	@Transactional
	public void createNewMovie(Movie movie, MultipartFile image) {
		try {
			String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
			movie.setImageString(base64Image);
			this.saveMovie(movie);
			} catch(IOException e) {}
	}

	public Movie findMovieById(Long id) {
		return this.movieRepository.findById(id).orElse(null);
	}

	public Iterable<Movie> findAllMovie(){
		return this.movieRepository.findAll();
	}

	public Movie saveMovie(Movie movie) {
		return this.movieRepository.save(movie);
	}

	public Movie saveDirectorToMovie(Long idMovie, Long idArtist) {
		Movie movie= this.findMovieById(idMovie);
		Artist director= this.artistRepository.findById(idArtist).orElse(null);
		if(movie!=null && director!=null) {
			movie.setDirector(director);
			director.getMoviesDirected().add(movie);
			artistRepository.save(director);
			this.saveMovie(movie);
			return movie;
		}
		return null;
	}

	public List<Movie> findByYear(int year) {
		return this.movieRepository.findByYear(year);
	}

	public Movie addActorToMovie(Long idMovie, Long idActor) {
		Movie movie=this.findMovieById(idMovie);
		Artist actor=this.artistRepository.findById(idActor).get();
		if(movie!=null && actor!=null) {
			movie.getActors().add(actor);
			actor.getMoviesActed().add(movie);
			this.artistRepository.save(actor);
			return this.saveMovie(movie);
		}
		return movie;
	}


	public Movie removeActorFromMovie(Long idMovie,Long idActor) {
		Movie movie=this.findMovieById(idMovie);
		Artist actor=this.artistRepository.findById(idActor).orElse(null);
		if(movie!=null && actor!=null) {
			movie.getActors().remove(actor);
			actor.getMoviesActed().remove(movie);
			this.artistRepository.save(actor);
			return this.saveMovie(movie);
		}
		return movie;
	}

	public Movie findMovieBySuggestedPoints() {
		return this.movieRepository.findBySuggestedPoints(this.movieRepository.getMaxSuggestedPoints()).get(0);
	}

	public void addReviewToMovie(Movie movie, Review review) {
		movie.getReviews().add(review);
		this.saveMovie(movie);
	}

	public void increaseSuggestedPoint(Long idMovie) {
		Movie movie=this.findMovieById(idMovie);
		movie.increaseSuggestedPoints();
		this.saveMovie(movie);
	}
	public void decreaseSuggestedPoint(Long idMovie) {
		Movie movie=this.findMovieById(idMovie);
		movie.decreaseSuggestedPoints();
		this.saveMovie(movie);
	}

	public boolean alreadyExists(Movie movie) {
		return movie.getTitle()!=null && movie.getYear()!=null
				&& movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear());
	}
	public void removeActorAssociationFromAllMovie(Long idMovie) {
		Movie movie=this.movieRepository.findById(idMovie).get();
		movie.setActors(null);
		this.movieRepository.save(movie);
	}
	public void delete(Long idMovie) {
		Movie movie= this.movieRepository.findById(idMovie).get();
		this.movieRepository.delete(movie);
	}
	@Transactional 
	public void removeArtistAssociationFromAllMovie(Long idactor) {
		Artist actor= this.artistRepository.findById(idactor).get();
		List<Movie> moviesActed=this.movieRepository.findAllByActorsIsContaining(actor);
		List<Movie> moviesDirected=this.movieRepository.findAllByDirector(actor);
		for(Movie movie :moviesActed) {
			movie.getActors().remove(actor);
			this.movieRepository.save(movie);
		}
		for(Movie movie :moviesDirected) {
			movie.setDirector(null);
			this.movieRepository.save(movie);
		}
	}

	public void removeReviewAssociationFromMovie(Review review) {
		Movie movie= this.movieRepository.findAllByReviewsIsContaining(review).get(0);
		movie.getReviews().remove(review);
		this.movieRepository.save(movie);	
	}

	@Transactional 
	public void resetAllToZeroSuggestedPoints() {
		this.movieRepository.updateAllSuggestedPointsToZero(0);
	}
	@Transactional 
	public Movie update(Long idMovie, Movie newMovie, MultipartFile image) {
		Movie movie = this.movieRepository.findById(idMovie).get();
		movie.setTitle(newMovie.getTitle());
		movie.setYear(newMovie.getYear());
		if(!image.isEmpty()) {			
			try {
				String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
				movie.setImageString(base64Image);
				} catch(IOException e) {}
			this.movieRepository.save(movie);
		}
		return movie;
	}

	public void deleteMovie(Long idMovie) {
		Movie movie=this.findMovieById(idMovie);		
		this.artistService.removeMovieAssociationFromAllActor(movie);
		this.removeActorAssociationFromAllMovie(idMovie);
		this.reviewService.removeMovieAssociationFromReview(movie);
		this.delete(idMovie);
	}

}
