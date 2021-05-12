package com.reactive.server.stream.event.service;

import com.reactive.server.stream.event.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<User> save(User user);

    Flux<User> saveAll(List<User> users);

    Mono<Void> deleteById(String id);

    Mono<User> findById(String id);

    Flux<User> findAll();
}
