package me.demo.msclient.web;


import me.demo.msclient.domain.Person;
import me.demo.msclient.web.dto.PersonRequestDto;
import me.demo.msclient.web.dto.PersonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    private List<Person> people = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<PersonResponseDto>> findAll(){
        return ResponseEntity.ok(people.stream()
                .map(PersonResponseDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable final Long id){
        return people.stream()
                .filter(person -> person.getId().equals(id))
                .map(PersonResponseDto::new)
                .map(personResponseDto -> ResponseEntity.ok()
                        .body(personResponseDto))
                .findFirst()
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PersonResponseDto> add(@RequestBody final PersonRequestDto personRequestDto){

        final Person addPerson = Person.builder()
                .id((long) (people.size() + 1))
                .age(personRequestDto.getAge())
                .firstName(personRequestDto.getFirstName())
                .lastName(personRequestDto.getLastName())
                .build();

        people.add(addPerson);
        return ResponseEntity.ok(new PersonResponseDto(addPerson));
    }


    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody final PersonRequestDto personRequestDto){
        boolean deleteSuccessFlag = people.removeAll(people.stream()
                .filter(person -> person.getId().equals(personRequestDto.getId()))
                .collect(Collectors.toList()));

        if(deleteSuccessFlag)
            return ResponseEntity.ok(true);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(false);
    }
}
