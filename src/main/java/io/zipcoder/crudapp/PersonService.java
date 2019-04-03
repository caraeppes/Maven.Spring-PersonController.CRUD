package io.zipcoder.crudapp;

import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public Person create(Person person){
        return personRepository.save(person);
    }

    public Person read(Integer id){
        return personRepository.findOne(id);
    }

    public Iterable<Person> readAll(){
        return personRepository.findAll();
    }

    public Person update(Integer id, Person person){
        Person originalPerson = personRepository.findOne(id);
        originalPerson.setFirstName(person.getFirstName());
        originalPerson.setLastName(person.getLastName());
        return personRepository.save(originalPerson);
    }

    public Boolean delete(Integer id){
        personRepository.delete(id);
        return true;
    }
}
