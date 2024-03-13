package com.garrow.vkassignment.utils.exceptions;

import com.garrow.vkassignment.utils.exceptions.generics.GenericException;

public class UnexpectedException extends GenericException {
    public UnexpectedException(String message){
        super(message);
    }
}
