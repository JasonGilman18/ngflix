package com.ngflix_api.movie;

import java.util.ArrayList;
import java.util.HashMap;
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
    public HashMap<String, Object> getAllMovies() {
        HashMap<String, Object> response = new HashMap<>();
        List<HashMap<String, Object>> movies = new ArrayList<>();
        for(Movie movie : movieRepo.findAll()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", movie.id);
            data.put("name", movie.name);
            data.put("rating", movie.rating);
            movies.add(data);
        }
        response.put("movies", movies);
        return response;
    }

    @GetMapping(path = "/movies/{movie_id}")
    public HashMap<String, Object> getMovie(@PathVariable Integer movie_id) {
        HashMap<String, Object> response = new HashMap<>();
        try { 
            Movie movie = movieRepo.findById(movie_id).get();
            response.put("id", movie.id);
            response.put("name", movie.name);
            response.put("rating", movie.rating);
        }
        catch(NoSuchElementException e) {
            response.put("error", "could not fetch movie with id " + movie_id);
        }
        return response;
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
