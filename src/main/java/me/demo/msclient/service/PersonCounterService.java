package me.demo.msclient.service;

import me.demo.msclient.domain.Person;
import me.demo.msclient.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonCounterService {

    private final PersonRepository personRepository;

    public PersonCounterService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Optional<Person> findById(String id){
        return personRepository.findById(id);
    }

    public Person add(Person addPerson){
        return personRepository.save(addPerson);
    }

    public void delete(String id){
        personRepository.deleteById(id);
    }
}
