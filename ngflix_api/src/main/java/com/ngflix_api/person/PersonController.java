package com.ngflix_api.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    
    @Autowired
    PersonRepository personRepo;

    @GetMapping(path = "/people")
    public HashMap<String, Object> getAllPeople() {
        HashMap<String, Object> response = new HashMap<>();
        List<HashMap<String, Object>> people = new ArrayList<>();
        for(Person p : personRepo.findAll()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", p.id);
            data.put("firstName", p.firstName);
            data.put("lastName", p.lastName);
            people.add(data);
        }
        response.put("people", people);
        return response;
    }

    @GetMapping(path = "/people/{person_id}")
    public HashMap<String, Object> getPerson(@PathVariable Integer person_id) {
        try {
            return personRepo.findById(person_id).get();
        }
        catch(NoSuchElementException e) {
            return null;
        }
    }

    @PostMapping(path = "/people/add")
    public HttpStatus addPerson(@RequestBody Person person) {
        try {
            personRepo.save(person);
            return HttpStatus.CREATED;
        }
        catch(IllegalArgumentException e) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    @DeleteMapping(path = "/people/{person_id}/delete")
    public HttpStatus deletePerson(@PathVariable Integer person_id) {
        try {
            Person person = personRepo.findById(person_id).get();
            personRepo.delete(person);
            return HttpStatus.ACCEPTED;
        }
        catch(IllegalArgumentException | NoSuchElementException e) {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }
}
