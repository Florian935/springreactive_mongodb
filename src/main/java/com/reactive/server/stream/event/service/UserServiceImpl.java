package com.reactive.server.stream.event.service;

import com.reactive.server.stream.event.model.User;
import com.reactive.server.stream.event.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Flux<User> saveAll(List<User> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Flux<Long> stream() {
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1000));
        final Flux<User> eventUser = userRepository.findAll().repeat();

        return Flux.zip(interval, eventUser)
                .map(Tuple2::getT1).share();

    }


}
