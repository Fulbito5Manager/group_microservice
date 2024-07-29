package com.api.group.exception;

public class GroupIsNotExistException extends RuntimeException {
    public GroupIsNotExistException(String message) {
        super(message);
    }
}
