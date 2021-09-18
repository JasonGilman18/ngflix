package com.ngflix_api;

import com.ngflix_api.movie.Movie;
import com.ngflix_api.movie.MovieRepository;
import com.ngflix_api.person.Person;
import com.ngflix_api.person.PersonRepository;
import com.ngflix_api.person_movie.PersonMovie;
import com.ngflix_api.person_movie.PersonMovieRepository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NgflixApiApplication {

	@Autowired MovieRepository movieRepo;
	@Autowired PersonRepository personRepo;
	@Autowired PersonMovieRepository personMovieRepo;

	public static void main(String[] args) {
		SpringApplication.run(NgflixApiApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			//userRepository.save(new User("John"));
			//userRepository.save(new User("Rambo"));
			Movie[] seedMovies = new Movie[] {
				new Movie("Iron-Man", 7.8),
				new Movie("Iron-Man", 7.8),
				new Movie("Iron-Man 3", 6.4)
			};

			Person[] seedPeople = new Person[] {
				new Person("Robert", "Downey")
			};

			for(Movie m : seedMovies) {
				movieRepo.save(m);
			}

			for(Person p : seedPeople) {
				personRepo.save(p);
			}

			personMovieRepo.save(new PersonMovie(seedPeople[0], seedMovies[0]));
		};
	}
}
