package com.garrow.vkassignment.utils.exceptions.generics;

import com.garrow.vkassignment.utils.exceptions.messages.GenericMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenericExceptionWithDetails
        extends RuntimeException {
    private Set<GenericMessage> genericMessages;
}
