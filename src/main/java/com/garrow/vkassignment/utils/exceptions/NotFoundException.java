package com.garrow.vkassignment.utils.exceptions;

import com.garrow.vkassignment.utils.exceptions.generics.GenericException;

public class NotFoundException
        extends GenericException {
    public NotFoundException(String message){
        super(message);
    }
}