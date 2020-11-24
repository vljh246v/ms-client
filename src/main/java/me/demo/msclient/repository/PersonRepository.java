package me.demo.msclient.repository;

import me.demo.msclient.domain.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    public List<Person> findByLastName(String lastName);
    public List<Person> findByAgeGreaterThan(int age);

}
