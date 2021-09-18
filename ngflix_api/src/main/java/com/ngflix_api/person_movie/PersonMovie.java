package com.ngflix_api.person_movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ngflix_api.movie.Movie;
import com.ngflix_api.person.Person;

@Entity
@Table(name = "person_movies")
public class PersonMovie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;
    
    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    public PersonMovie() {}
    public PersonMovie(Person person, Movie movie) {
        this.person = person;
        this.movie = movie;
    }

    public Person getPerson() {return this.person;}
    public Movie getMovie(){return this.movie;}
}
