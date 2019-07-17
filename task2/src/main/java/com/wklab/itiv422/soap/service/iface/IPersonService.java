package com.wklab.itiv422.soap.service.iface;


import com.wklab.itiv422.soap.dto.GetFriendsByPersonAndYearResponse;

/**
 * Service interface for working with the person
 */
public interface IPersonService {
    /**
     * Returns all of the user's friends for a given year
     *
     * @param personId the person id
     * @param year     the year
     * @return user list wrapper
     */
    GetFriendsByPersonAndYearResponse getFriendsByYear(Long personId, Long year);
}
