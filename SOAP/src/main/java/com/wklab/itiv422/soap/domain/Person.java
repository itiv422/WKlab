package com.wklab.itiv422.soap.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Person {

    @Embedded
    Birthday birthday;
    String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany
    @JoinTable(name = "friends.xsd",
            joinColumns = @JoinColumn(name = "personId"),
            inverseJoinColumns = @JoinColumn(name = "friendId")
    )
    private List<Person> friends;

    @ManyToMany
    @JoinTable(name = "friends.xsd",
            joinColumns = @JoinColumn(name = "friendId"),
            inverseJoinColumns = @JoinColumn(name = "personId")
    )
    private List<Person> friendOf;
}
