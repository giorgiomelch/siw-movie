package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String title;
	@NotNull
	@Min(1900)
	@Max(2023)
	private Integer year;
	private String urlImage;
	
	@Column(length=10000000)
	private String imageString;
	
	@OneToMany(mappedBy = "movie")
	private List<Review> reviews;
	@ManyToMany(mappedBy= "moviesActed")
	private List<Artist> actors;
	@ManyToOne
	private Artist director;
	
	private int suggestedPoints;
	
	
	public String getImageString() {
		return imageString;
	}


	public void setImageString(String imageString) {
		this.imageString = imageString;
	}


	public float getAverageReview() {
		 if (this.reviews.size() == 0) {
	            return 0.0f;
	        }
	     float sum = 0.0f;

	     for(Review review: reviews){
	           sum += review.getRating();
	        }
	     float average = sum / this.reviews.size();

	     return average;
	}
	
	
	public int getSuggestedPoints() {
		return suggestedPoints;
	}


	public void setSuggestedPoints(int suggestedPoints) {
		this.suggestedPoints = suggestedPoints;
	}
	public void increaseSuggestedPoints(){
		this.suggestedPoints= this.suggestedPoints+1;
	}
	public void decreaseSuggestedPoints(){
		this.suggestedPoints= this.suggestedPoints-1;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Artist> getActors() {
		return actors;
	}

	public void setActors(List<Artist> actors) {
		this.actors = actors;
	}

	public Artist getDirector() {
		return director;
	}

	public void setDirector(Artist director) {
		this.director = director;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, title, urlImage, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return Objects.equals(id, other.id) && Objects.equals(title, other.title)
				&& Objects.equals(urlImage, other.urlImage) && Objects.equals(year, other.year);
	}

	
}
