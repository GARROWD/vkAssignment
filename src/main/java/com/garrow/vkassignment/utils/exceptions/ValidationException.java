package com.garrow.vkassignment.utils.exceptions;

import com.garrow.vkassignment.utils.exceptions.generics.GenericExceptionWithDetails;
import com.garrow.vkassignment.utils.exceptions.messages.GenericMessage;
import java.util.Set;

public class ValidationException
        extends GenericExceptionWithDetails {
    public ValidationException(Set<GenericMessage> messages) {
        super(messages);
    }
}
