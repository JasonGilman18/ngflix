package com.ngflix_api.movie;

import java.util.List;
import java.util.NoSuchElementException;

import com.ngflix_api.person.Person;
import com.ngflix_api.person.PersonRepository;
import com.ngflix_api.person_movie.PersonMovie;
import com.ngflix_api.person_movie.PersonMovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private PersonMovieRepository personMovieRepo;

    @GetMapping(path = "/movies")
    public Map<String, List<Map<String, ?>>> getAllMovies() {
        
        for(Movie movie : movieRepo.findAll()) {

        }
    }

    @GetMapping(path = "/movies/{movie_id}")
    public Movie getMovie(@PathVariable Integer movie_id) {
        try { 
            return movieRepo.findById(movie_id).get();
        }
        catch(NoSuchElementException e) {
            return null;
        }
    }

    @PostMapping(path = "/movies/add")
    public HttpStatus addMovie(@RequestBody Movie movie) {
        try {
            movieRepo.save(movie);
            return HttpStatus.CREATED;
        }
        catch(IllegalArgumentException e) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    @DeleteMapping(path = "/movies/{movie_id}/delete")
    public HttpStatus deleteMovie(@PathVariable Integer movie_id) {
        try {
            Movie movie = movieRepo.findById(movie_id).get();
            movieRepo.delete(movie);
            return HttpStatus.ACCEPTED;
        }
        catch(IllegalArgumentException | NoSuchElementException e) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    @PostMapping(path = "/movies/{movie_id}/cast/add/{person_id}")
    public HttpStatus addCastMember(@PathVariable Integer movie_id, @PathVariable Integer person_id) {
        try {
            Movie movie = movieRepo.findById(movie_id).get();
            Person person = personRepo.findById(person_id).get();
            PersonMovie relation = new PersonMovie(person, movie);
            personMovieRepo.save(relation);
            movie.cast.add(relation);
            person.portfolio.add(relation);
            movieRepo.save(movie);
            personRepo.save(person);
            return HttpStatus.CREATED;
        }
        catch(NoSuchElementException e) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }
}
