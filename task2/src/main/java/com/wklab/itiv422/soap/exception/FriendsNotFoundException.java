package com.wklab.itiv422.soap.exception;

/**
 * Runtime exception indicating that the requested users are not found
 */
public class FriendsNotFoundException extends RuntimeException {

    /**
     * Exception constructor
     * @param message the message
     */
    public FriendsNotFoundException(String message) {
        super(message);
    }
}
