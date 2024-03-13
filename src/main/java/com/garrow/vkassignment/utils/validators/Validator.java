package com.garrow.vkassignment.utils.validators;

import com.garrow.vkassignment.services.ExceptionMessagesService;
import com.garrow.vkassignment.utils.exceptions.ValidationException;
import com.garrow.vkassignment.utils.exceptions.messages.GenericMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Validator {
    private final ExceptionMessagesService exceptionMessages;
    private final jakarta.validation.Validator validator;

    public void validate(Object object)
            throws ValidationException {
        Set<GenericMessage> messages =
                validator.validate(object).stream().map(violation -> exceptionMessages.getValidationMessage(
                        violation.getMessage(), violation.getPropertyPath().toString())).collect(Collectors.toSet());

        if(! messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }

    public boolean isValid(Object object) {
        return validator.validate(object).isEmpty();
    }
}
