package com.wklab.itiv422.soap.repository;


import com.wklab.itiv422.soap.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for working with the person
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    /**
     * Returns all of the user's friends for a given year
     * @param id the user id
     * @param Year the year
     * @return list of searched users
     */
    List<Person> findByFriendOfIdAndBirthdayYear(Long id, Long Year);
}
