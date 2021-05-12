package com.reactive.server.stream.event;

import com.reactive.server.stream.event.model.Product;
import com.reactive.server.stream.event.model.User;
import com.reactive.server.stream.event.repository.ProductRepository;
import com.reactive.server.stream.event.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Stream;

@Component
@Slf4j
public class DataInitializer {

    @Bean
    CommandLineRunner start(UserRepository userRepository, ProductRepository productRepository) {

        log.info("Starting data initialization ...");

        return args -> {
            userRepository.deleteAll()
                    .subscribe(null, null, () -> productRepository.deleteAll()
                            .subscribe(null, null, () -> Flux.fromStream(usersInitializer())
                                    .flatMap(userRepository::save)
                                    .log()
                                    .subscribe(null, null, () -> userRepository.findAll()
                                            .map(this::productInitializer)
                                            .flatMap(productRepository::save)
                                            .log()
                                            .subscribe(
                                                    data -> log.info("Product {} added !", data.getName()),
                                                    error -> log.error("Error: {}", error.getMessage()),
                                                    () -> log.info("Done initialization !")
                                            )
                                    )
                            )
                    );
        };
    }

    private Stream<User> usersInitializer() {
        return Stream.of(
                new User(null, "user1"),
                new User(null, "user2"),
                new User(null, "user3"),
                new User(null, "user4")
        );
    }

    private Product productInitializer(User user) {
        final int randomPrice = new Random().nextInt(1000);
        final String[] names = "tv,laptop,computer,tablet,smartphone".split(",");
        final String randomName = names[new Random().nextInt(names.length)];

        return new Product(null, randomName, LocalDateTime.now(), randomPrice, user);
    }
}
