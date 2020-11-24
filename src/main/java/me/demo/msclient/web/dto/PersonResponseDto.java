package me.demo.msclient.web.dto;

import lombok.Builder;
import lombok.Getter;
import me.demo.msclient.domain.Person;

@Getter
public class PersonResponseDto {

    private String id;
    private String firstName;
    private String lastName;
    private int age;

    @Builder
    public PersonResponseDto(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
    }
}
