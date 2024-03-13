package com.garrow.vkassignment.utils.exceptions;

import com.garrow.vkassignment.utils.exceptions.generics.GenericException;

public class ApplicationStatusException
        extends GenericException {
    public ApplicationStatusException(String message) {
        super(message);
    }
}
