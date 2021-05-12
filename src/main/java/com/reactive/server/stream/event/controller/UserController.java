package com.reactive.server.stream.event.controller;

import com.reactive.server.stream.event.model.User;
import com.reactive.server.stream.event.service.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public Flux<User> getAll() {
        return userService.findAll();
    }

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> streamUsers() {
        return userService.stream().log();
    }

    @GetMapping(path = "/{id}")
    public Mono<User> get(@PathVariable String id) {
        return userService.findById(id);
    }
}
