package com.garrow.vkassignment.repositories;

import com.garrow.vkassignment.dto.PostPayload;
import com.garrow.vkassignment.models.Album;
import com.garrow.vkassignment.models.Post;
import com.garrow.vkassignment.services.ExceptionMessagesService;
import com.garrow.vkassignment.utils.enums.ExceptionMessages;
import com.garrow.vkassignment.utils.exceptions.NotFoundException;
import com.garrow.vkassignment.utils.exceptions.generics.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AlbumsRepository {
    // Services
    private final ExceptionMessagesService exceptionMessages;

    private final WebClient webClient;

    public Album getById(Long id)
            throws NotFoundException {
        return webClient.get()
                        .uri("/albums/{id}", id)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToMono(Album.class)
                        .block();
    }

    public void existsById(Long id)
            throws NotFoundException {
        webClient.get()
                 .uri("/albums/{id}", id)
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                 .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                 .bodyToMono(Album.class)
                 .block();
    }

    public Page<Album> getAll(Pageable pageable) {
        return webClient.get()
                        .uri("/albums?_start={start}&_limit={limit}", pageable.getPageNumber() * pageable.getPageSize(),
                             pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToFlux(Album.class)
                        .collectList()
                        .map(posts -> new PageImpl<>(posts, pageable, posts.size()))
                        .block();
    }



    private Mono<? extends Throwable> handle4xxError(ClientResponse clientResponse) {
        if(clientResponse.statusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return Mono.error(new NotFoundException(
                    exceptionMessages.getMessage(ExceptionMessages.NOT_FOUND)));
        }

        return Mono.error(new GenericException("Client error: " + clientResponse.statusCode()));
    }

    private Mono<? extends Throwable> handle5xxError(ClientResponse clientResponse) {

        return Mono.error(new GenericException("Server error: " + clientResponse.statusCode()));
    }
}
