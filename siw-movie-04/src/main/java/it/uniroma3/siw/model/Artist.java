package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Artist {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(nullable=false)
	@NotBlank
	private String name;
	@Column(nullable=false)
	@NotBlank
	private String surname;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;
	@Column(length=10000000)
	private String imageString;
	@ManyToMany
	private List<Movie> moviesActed;
	@OneToMany(mappedBy= "director")
	private List<Movie> moviesDirected;
	
	
	
	public Artist() {
		this.moviesActed = new LinkedList<>();
		this.moviesDirected = new LinkedList<>();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, id, name, surname);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artist other = (Artist) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}
	

	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Movie> getMoviesActed() {
		return moviesActed;
	}
	public void setMoviesActed(List<Movie> moviesActed) {
		this.moviesActed = moviesActed;
	}
	public List<Movie> getMoviesDirected() {
		return moviesDirected;
	}
	public void setMoviesDirected(List<Movie> moviesDirected) {
		this.moviesDirected = moviesDirected;
	}
	
	
}
