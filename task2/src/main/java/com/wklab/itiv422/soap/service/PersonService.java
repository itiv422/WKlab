package com.wklab.itiv422.soap.service;


import com.wklab.itiv422.soap.domain.Person;
import com.wklab.itiv422.soap.dto.Birthday;
import com.wklab.itiv422.soap.dto.GetFriendsByPersonAndYearResponse;
import com.wklab.itiv422.soap.dto.PersonDto;
import com.wklab.itiv422.soap.exception.FriendsNotFoundException;
import com.wklab.itiv422.soap.repository.PersonRepository;
import com.wklab.itiv422.soap.service.iface.IPersonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for working with the user
 */
@Service
public class PersonService implements IPersonService {
    private final PersonRepository personRepository;

    /**
     * Person service constructor
     *
     * @param personRepository the person repository
     */
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public GetFriendsByPersonAndYearResponse getFriendsByYear(Long personId, Long year) {
        List<Person> friends = personRepository.findByFriendOfIdAndBirthdayYear(personId, year);
        GetFriendsByPersonAndYearResponse response = new GetFriendsByPersonAndYearResponse();
        for (Person person : friends) {
            PersonDto personDto = new PersonDto();
            personDto.setName(person.getName());
            Birthday birthday = new Birthday();
            birthday.setDay(person.getBirthday().getDay());
            birthday.setYear(person.getBirthday().getYear());
            birthday.setMonth(person.getBirthday().getMonth());
            personDto.setBirthday(birthday);
            response.getFriends().add(personDto);
        }
        if (response.getFriends().size() == 0) {
            throw new FriendsNotFoundException("Friend not found");
        }
        return response;
    }
}
