package com.garrow.vkassignment.repositories;

import com.garrow.vkassignment.dto.UserPayload;
import com.garrow.vkassignment.models.Album;
import com.garrow.vkassignment.models.Post;
import com.garrow.vkassignment.models.User;
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
public class UsersRepository {
    // Services
    private final ExceptionMessagesService exceptionMessages;

    private final WebClient webClient;

    public User getById(Long id)
            throws NotFoundException {
        return webClient.get()
                        .uri("/users/{id}", id)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToMono(User.class)
                        .block();
    }

    public void existsById(Long id)
            throws NotFoundException {
        webClient.get()
                 .uri("/users/{id}", id)
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                 .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                 .bodyToMono(User.class)
                 .block();
    }

    public Page<UserPayload.Request> getAll(Pageable pageable) {
        return webClient.get()
                        .uri("/users?_start={start}&_limit={limit}", pageable.getPageNumber() * pageable.getPageSize(),
                             pageable.getPageSize())
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToFlux(UserPayload.Request.class)
                        .collectList()
                        .map(posts -> new PageImpl<>(posts, pageable, posts.size()))
                        .block();
    }

    public Album getAlbumsById(Long id){
        return webClient.get()
                 .uri("/users/{id}/albums", id)
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                 .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                 .bodyToMono(Album.class)
                 .block();
    }

    public Post getPostsById(Long id){
        return webClient.get()
                        .uri("/users/{id}/albums", id)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToMono(Post.class)
                        .block();
    }

    public User create(User post) {
        return webClient.post()
                        .uri("/users")
                        .bodyValue(post)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToMono(User.class)
                        .block();
    }

    public User update(User post) {
        return webClient.put()
                        .uri("/users/{id}", post.getId())
                        .body(BodyInserters.fromValue(post))
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                        .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                        .bodyToMono(User.class)
                        .block();
    }

    public void deleteById(Long id)
            throws NotFoundException {
        webClient.delete()
                 .uri("/users/{id}", id)
                 .retrieve()
                 .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                 .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                 .bodyToMono(User.class)
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
