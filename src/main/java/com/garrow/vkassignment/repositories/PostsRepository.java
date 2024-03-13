package com.garrow.vkassignment.repositories;

import com.garrow.vkassignment.dto.PostPayload;
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
public class PostsRepository {
    // Services
    private final ExceptionMessagesService exceptionMessages;

    private final WebClient webClient;

    public Post getById(Long id)
            throws NotFoundException {
        return webClient.get()
                        .uri("/posts/{id}", id)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToMono(Post.class)
                        .block();
    }

    public void existsById(Long id)
            throws NotFoundException {
        webClient.get()
                 .uri("/posts/{id}", id)
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                 .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                 .bodyToMono(Post.class)
                 .block();
    }

    public Page<PostPayload.Request> getAll(Pageable pageable) {
        return webClient.get()
                        .uri("/posts?_start={start}&_limit={limit}", pageable.getPageNumber() * pageable.getPageSize(),
                             pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToFlux(PostPayload.Request.class)
                        .collectList()
                        .map(posts -> new PageImpl<>(posts, pageable, posts.size()))
                        .block();
    }

    public Post create(Post post) {
        return webClient.post()
                                    .uri("/posts")
                                    .bodyValue(post)
                                    .retrieve()
                                    .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                                    .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                                    .bodyToMono(Post.class)
                                    .block();
    }

    public Post update(Post post) {
        return webClient.put()
                 .uri("/posts/{id}", post.getId())
                 .body(BodyInserters.fromValue(post))
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                 .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                 .bodyToMono(Post.class)
                 .block();
    }

    public void deleteById(Long id)
            throws NotFoundException {
        webClient.delete()
                 .uri("/posts/{id}", id)
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                 .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                 .bodyToMono(Post.class)
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
