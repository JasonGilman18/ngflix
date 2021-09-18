package com.ngflix_api.movie;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ngflix_api.person_movie.PersonMovie;

@Entity
@Table(name = "movies")
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    @OneToMany(mappedBy = "movie")
    public Set<PersonMovie> cast;

    public String name;
    public double rating;

    public Movie() {}
    public Movie(String name, double rating) {
        this.name = name;
        this.rating = rating;
    }
}
