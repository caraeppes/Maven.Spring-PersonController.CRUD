package io.zipcoder.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/people/")
    public ResponseEntity<Person> createPerson(@RequestBody Person p) {
        return new ResponseEntity<>(personRepository.save(p), HttpStatus.CREATED);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        return new ResponseEntity<>(personRepository.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/people/")
    public ResponseEntity<List<Person>> getPersonList() {
        return new ResponseEntity<>((List<Person>) personRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/people/")
    public ResponseEntity<Person> updatePerson(@RequestBody Person p) {
        Person originalPerson = personRepository.findOne(p.getId());
        if (originalPerson == null) {
            return new ResponseEntity<>(personRepository.save(p), HttpStatus.CREATED);
        } else {
            originalPerson.setFirstName(p.getFirstName());
            originalPerson.setLastName(p.getLastName());
            return new ResponseEntity<>(personRepository.save(originalPerson), HttpStatus.OK);
        }
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        personRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
