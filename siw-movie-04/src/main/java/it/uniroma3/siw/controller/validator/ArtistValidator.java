package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Artist;
import it.uniroma3.siw.service.ArtistService;

@Component
public class ArtistValidator implements Validator{

	@Autowired ArtistService artistService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Artist.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Artist artist=(Artist) o;
		if(this.artistService.alreadyExists(artist))
			errors.reject("artist.duplicate");
		
	}
	
	

}
