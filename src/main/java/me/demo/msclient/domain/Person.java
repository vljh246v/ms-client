package me.demo.msclient.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    @Builder
    public Person(Long id, String firstName, String lastName, int age){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

}
