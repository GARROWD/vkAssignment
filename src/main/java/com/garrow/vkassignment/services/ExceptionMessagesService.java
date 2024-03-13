package com.garrow.vkassignment.services;

import com.garrow.vkassignment.utils.enums.ExceptionMessages;
import com.garrow.vkassignment.utils.exceptions.messages.GenericMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ExceptionMessagesService {
    private final MessageSource messages;

    public String getMessage(ExceptionMessages method, String... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(method.getValue(), args, locale);
    }

    public String getMessage(ExceptionMessages method) {
        Locale locale = LocaleContextHolder.getLocale();
        return messages.getMessage(method.getValue(), null, locale);
    }

    public GenericMessage getValidationMessage(String value, String field, String... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return new GenericMessage(messages.getMessage(value, args, locale), field);
    }

    public GenericMessage getValidationMessage(String value, String field) {
        Locale locale = LocaleContextHolder.getLocale();
        return new GenericMessage(messages.getMessage(value, null, locale), field);
    }
}