package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.model.Movie;

public interface ArtistRepository extends CrudRepository<Artist,Long>{
	
	public boolean existsByNameAndSurnameAndDateOfBirth(String name, String surname, LocalDate date);
	
	public List<Artist> findAllByMoviesActedIsNotContaining(Movie movie);
	public List<Artist> findAllByMoviesActedIsContaining(Movie movie);
}
