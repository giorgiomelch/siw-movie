package it.uniroma3.siw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;
import it.uniroma3.siw.repository.ArtistRepository;

@Service
public class ArtistService {

	@Autowired private ArtistRepository artistRepository;
	
	@Transactional
	public boolean createNewArtist(Artist artist) {
		boolean res= false;
		if(!this.artistRepository.existsByNameAndSurnameAndDateOfBirth(artist.getName(), artist.getSurname(), artist.getDateOfBirth())) {
			this.artistRepository.save(artist);
			res=true;
			}
		return res;
	}
	
	public Iterable<Artist> findAllArtist(){
		return this.artistRepository.findAll();
	}
	
	public Artist findArtistById(Long idArtist) {
		return this.artistRepository.findById(idArtist).orElse(null);
	}
	
	public List<Artist> findAllArtistByMoviesActedIsContaining(Movie movie) {
		return this.artistRepository.findAllByMoviesActedIsContaining(movie);
	}
	public List<Artist> findAllArtistByMoviesActedIsNotContaining(Movie movie){
		return this.artistRepository.findAllByMoviesActedIsNotContaining(movie);
	}
	
	public boolean alreadyExists(Artist artist) {
		return artist.getName()!=null && artist.getSurname()!=null && artist.getDateOfBirth()!=null
				&& artistRepository.existsByNameAndSurnameAndDateOfBirth(
						artist.getName(), artist.getSurname(), artist.getDateOfBirth());
	}
	@Transactional
	public void removeMovieAssociationFromAllActor(Movie movie) {
		List<Artist> actors=this.artistRepository.findAllByMoviesActedIsContaining(movie);
		for(Artist actor:actors) {
			actor.getMoviesActed().remove(movie);
			this.artistRepository.save(actor);
		}
	}
	public void removeMovieAssociationFromActor(Long idActor) {
		Artist artist= this.artistRepository.findById(idActor).get();
		artist.setMoviesActed(null);
		artist.setMoviesDirected(null);
		this.artistRepository.save(artist);
}
	public void delete(Long idArtist) {
		Artist artist= this.artistRepository.findById(idArtist).get();
		this.artistRepository.delete(artist);
	}
}
