package com.ngflix_api.person_movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonMovieRepository extends JpaRepository<PersonMovie, Integer> {
    
}
