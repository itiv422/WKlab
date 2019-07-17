package com.wklab.itiv422.soap.controller;


import com.wklab.itiv422.soap.dto.GetFriendsByPersonAndYearRequest;
import com.wklab.itiv422.soap.dto.GetFriendsByPersonAndYearResponse;
import com.wklab.itiv422.soap.service.iface.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Web service endpoint
 */
@Endpoint
public class PersonController {
    private final IPersonService personService;

    /**
     * Person controller constructor
     *
     * @param personService the person service
     */
    @Autowired
    public PersonController(IPersonService personService) {
        this.personService = personService;
    }

    /**
     * Returns all of the user's friends for a given year
     *
     * @param request user id adn year wrapper
     * @return user list wrapper
     */
    @PayloadRoot(namespace = "http://itiv422.wklab.com/soap/dto", localPart = "getFriendsByPersonAndYearRequest")
    @ResponsePayload
    public GetFriendsByPersonAndYearResponse getFriendsByYear(@RequestPayload GetFriendsByPersonAndYearRequest request) {
        return personService.getFriendsByYear(request.getPersonId(), request.getYear());
    }

}
