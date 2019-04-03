package io.zipcoder.crudapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping("/people/")
    public ResponseEntity<Person> createPerson(@RequestBody Person p) {
        return new ResponseEntity<>(personService.create(p), HttpStatus.CREATED);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Integer id) {
        return new ResponseEntity<>(personService.read(id), HttpStatus.OK);
    }

    @GetMapping("/people/")
    public ResponseEntity<Iterable<Person>> getPersonList() {
        return new ResponseEntity<>(personService.readAll(), HttpStatus.OK);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id, @RequestBody Person p) {
        if (personService.read(id) == null) {
            return createPerson(p);
        }
        return new ResponseEntity<>(personService.update(id, p), HttpStatus.OK);
    }

    @DeleteMapping("/people/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable Integer id) {
        return new ResponseEntity<>(personService.delete(id), HttpStatus.NOT_FOUND);
    }
}
