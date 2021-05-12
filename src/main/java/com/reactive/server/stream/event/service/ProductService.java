package com.reactive.server.stream.event.service;

import com.reactive.server.stream.event.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {

    Mono<Product> save(Product product);

    Flux<Product> saveAll(List<Product> products);

    Mono<Void> deleteById(String id);

    Mono<Product> findById(String id);

    Flux<Product> findAll();
}
