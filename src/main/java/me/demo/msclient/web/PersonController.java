package me.demo.msclient.web;


import me.demo.msclient.domain.Person;
import me.demo.msclient.service.PersonCounterService;
import me.demo.msclient.web.dto.PersonRequestDto;
import me.demo.msclient.web.dto.PersonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
public class PersonController {

    private List<Person> people = new ArrayList<>();
    private final PersonCounterService personCounterService;

    public PersonController(PersonCounterService personCounterService) {
        this.personCounterService = personCounterService;
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDto>> findAll(){
        return ResponseEntity.ok(personCounterService.findAll().stream()
                .map(PersonResponseDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable final String id){

        Person person = personCounterService.findById(id)
                .orElseGet(Person::new);

        if(Objects.isNull(person.getId()))
            return  ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(new PersonResponseDto(person));

    }

    @PostMapping
    public ResponseEntity<PersonResponseDto> add(@RequestBody final PersonRequestDto personRequestDto){

        final Person addPerson = Person.builder()
                .age(personRequestDto.getAge())
                .firstName(personRequestDto.getFirstName())
                .lastName(personRequestDto.getLastName())
                .build();

        return ResponseEntity.ok(new PersonResponseDto(personCounterService.add(addPerson)));
    }


    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestBody final PersonRequestDto personRequestDto){

        try {
            personCounterService.delete(personRequestDto.getId());
            return ResponseEntity.ok(true);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(false);
        }
    }
}
